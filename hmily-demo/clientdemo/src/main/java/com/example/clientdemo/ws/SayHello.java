package com.example.clientdemo.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author jt
 * @date 2019-7-5
 */

@WebService
public interface SayHello {

    @WebMethod
    public String sayHello(@WebParam(name = "name") String name);
}