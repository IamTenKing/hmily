package com.example.clientdemo.ws.client;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * @author jt
 * @date 2019-7-5
 */
public class Test {

    public static void main(String[] args){
        try {
//            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//            Client clientA = dcf.createClient("http://localhost:8080/services/sayGoodBye?wsdl");
//            Object[] resultA = clientA.invoke("sayGoodBye", "Joker");
//            System.out.println("resultA===>" + resultA[0]);
//
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client clientB = dcf.createClient("http://localhost:8080/services/sayHello?wsdl");
            Object[] resultB = clientB.invoke("sayHello", "Joker");
            System.out.println("resultB===>" + resultB[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
