package com.example.clientdemo.ws.impl;

import com.example.clientdemo.ws.SayGoodBye;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @author jt
 * @date 2019-7-5
 */

@WebService
public class SayGoodByeImpl implements SayGoodBye {
    @Override
    public String sayGoodBye(String name) {

        return "bye:" + name;
    }
}