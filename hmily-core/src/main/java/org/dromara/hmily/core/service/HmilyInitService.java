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
package org.dromara.hmily.core.service;


import org.dromara.hmily.common.config.HmilyConfig;

/**
 * @author xiaoyu
 */
//@FunctionalInterface，函数式接口@FunctionalInterface
//所谓的函数式接口，当然首先是一个接口，然后就是在这个接口里面只能有一个抽象方法。
// 该注解不是必须的，如果一个接口符合"函数式接口"定义，那么加不加该注解都没有影响
@FunctionalInterface
public interface HmilyInitService {

    /**
     * hmily init.
     *
     * @param hmilyConfig {@linkplain HmilyConfig}
     */
    void initialization(HmilyConfig hmilyConfig);
}
