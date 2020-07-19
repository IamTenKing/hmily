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

package org.dromara.hmily.core.bootstrap;

import org.dromara.hmily.common.config.HmilyConfig;
import org.dromara.hmily.core.helper.SpringBeanUtils;
import org.dromara.hmily.core.service.HmilyInitService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * hmily启动类，继承ApplicationContextAware，注入applicationContext到SpringBeanUtils单例中
 *
 * 首先在SpringBeanUtils中我们有了ApplicationContext（应用上下文）了，拥有了创建实例对象与注册对象获取注册对象的功能。
 * 通过spi创建了ObjectSerializer（序列化方式），HmilyCoordinatorRepository（日志数据操作类），并把HmilyCoordinatorRepository注册到应用上下文之中为单例对象。把它注册进去为单例对象的原因是因为HmilyCoordinatorRepository中存储了和数据库的连接仓库（datasource），还有日志表名等。
 * 接着我们根据配置信息创建了日志表的表名，生成数据库连接信息datasource等都存储在HmilyCoordinatorRepository之中。最后创建了日志表。
 * hmily bootstrap.
 * @author xiaoyu
 */
public class HmilyTransactionBootstrap extends HmilyConfig implements ApplicationContextAware {

    private final HmilyInitService hmilyInitService;

    //构造方法注入
    @Autowired
    public HmilyTransactionBootstrap(final HmilyInitService hmilyInitService) {
        this.hmilyInitService = hmilyInitService;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        //注入applicationContext
        SpringBeanUtils.getInstance().setCfgContext((ConfigurableApplicationContext) applicationContext);
        //开始启动流程
        start(this);
    }

    private void start(final HmilyConfig hmilyConfig) {
        //初始化配置
        hmilyInitService.initialization(hmilyConfig);
    }
}
