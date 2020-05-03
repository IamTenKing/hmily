package com.example.clientdemo.ws.impl;

import com.example.clientdemo.ws.SayHello;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;

/**
 * @author jt
 * @date 2019-7-5
 */
@WebService
public class SayHelloImpl implements SayHello {



    @Override
    public String sayHello(String name) {
        //User user = userService.findOne(Long.valueOf(id));
        return "Hello:" + name;
    }
}