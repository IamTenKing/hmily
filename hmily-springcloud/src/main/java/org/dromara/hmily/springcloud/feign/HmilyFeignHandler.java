/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hmily.springcloud.feign;

import org.dromara.hmily.common.utils.StringUtils;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.common.bean.context.HmilyTransactionContext;
import org.dromara.hmily.common.bean.entity.HmilyInvocation;
import org.dromara.hmily.common.bean.entity.HmilyParticipant;
import org.dromara.hmily.common.enums.HmilyActionEnum;
import org.dromara.hmily.common.enums.HmilyRoleEnum;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.dromara.hmily.core.helper.SpringBeanUtils;
import org.dromara.hmily.core.service.executor.HmilyTransactionExecutor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * HmilyFeignHandler.
 *
 * @author xiaoyu
 */
public class HmilyFeignHandler implements InvocationHandler {

    //此handler通过HmilyFeignBeanPostProcessor启动后注入
    private InvocationHandler delegate;

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            final Hmily hmily = method.getAnnotation(Hmily.class);
            if (Objects.isNull(hmily)) {
                //没有hmily注解直接调用
                return this.delegate.invoke(proxy, method, args);
            }
            //有@hmily注解的
            try {
                //事务上下文
                final HmilyTransactionContext hmilyTransactionContext = HmilyTransactionContextLocal.getInstance().get();
                if (Objects.nonNull(hmilyTransactionContext)) {
                    //上下文不为空且role=3,本地服务标识内嵌RPC调用
                    if (hmilyTransactionContext.getRole() == HmilyRoleEnum.LOCAL.getCode()) {
                        hmilyTransactionContext.setRole(HmilyRoleEnum.INLINE.getCode());
                    }
                }
                final HmilyTransactionExecutor hmilyTransactionExecutor =
                        SpringBeanUtils.getInstance().getBean(HmilyTransactionExecutor.class);
                //反射调用远程方法
                final Object invoke = delegate.invoke(proxy, method, args);
                //构建一个事务参与者
                final HmilyParticipant hmilyParticipant = buildParticipant(hmily, method, args, hmilyTransactionContext);
                //如果是内嵌rpc调用的话
                if (hmilyTransactionContext.getRole() == HmilyRoleEnum.INLINE.getCode()) {
                    //缓存到本地
                    hmilyTransactionExecutor.registerByNested(hmilyTransactionContext.getTransId(),
                            hmilyParticipant);
                } else {
                    //添加到HmilyTransaction对象中的参与者list
                    hmilyTransactionExecutor.enlistParticipant(hmilyParticipant);
                }
                return invoke;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw throwable;
            }
        }
    }

    private HmilyParticipant buildParticipant(final Hmily hmily, final Method method, final Object[] args,
                                              final HmilyTransactionContext hmilyTransactionContext) {
        if (Objects.isNull(hmilyTransactionContext)
                || (HmilyActionEnum.TRYING.getCode() != hmilyTransactionContext.getAction())) {
            return null;
        }
        String confirmMethodName = hmily.confirmMethod();
        if (StringUtils.isBlank(confirmMethodName)) {
            confirmMethodName = method.getName();
        }
        String cancelMethodName = hmily.cancelMethod();
        if (StringUtils.isBlank(cancelMethodName)) {
            cancelMethodName = method.getName();
        }
        final Class<?> declaringClass = method.getDeclaringClass();
        HmilyInvocation confirmInvocation = new HmilyInvocation(declaringClass, confirmMethodName, method.getParameterTypes(), args);
        HmilyInvocation cancelInvocation = new HmilyInvocation(declaringClass, cancelMethodName, method.getParameterTypes(), args);
        return new HmilyParticipant(hmilyTransactionContext.getTransId(), confirmInvocation, cancelInvocation);
    }

    void setDelegate(InvocationHandler delegate) {
        this.delegate = delegate;
    }

}
