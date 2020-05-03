package com.example.clientdemo.ws.config;

import com.example.clientdemo.ws.impl.SayGoodByeImpl;
import com.example.clientdemo.ws.impl.SayHelloImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author jt
 * @date 2019-7-5
 */
@Configuration
public class CxfConfig {


    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint endpoint() {
        // 绑定要发布的服务实现类
        EndpointImpl endpoint = new EndpointImpl(this.springBus(), new SayHelloImpl());
        // 接口访问地址
        endpoint.publish("/sayHello");
        return endpoint;
    }

    @Bean
    public Endpoint findUserInfo() {
        // 绑定要发布的服务实现类
        EndpointImpl endpoint = new EndpointImpl(this.springBus(), new SayGoodByeImpl());
        // 接口访问地址
        endpoint.publish("/sayGoodBye");
        return endpoint;
    }
}
