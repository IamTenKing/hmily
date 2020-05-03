package com.example.websocket_demo.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author jt
 * @date 2019-6-15
 */
@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {



    /**
     * 握手之前，若返回false，则不建立链接
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param attributes
     * @return
     * @throws Exception
     */

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        //将用户id放入socket处理器的会话(WebSocketSession)中
        attributes.put("uid", 1001);
        System.out.println("开始握手。。。。。。。");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("握手成功啦。。。。。。");
    }
}
