package com.example.websocket_demo.config;

import com.example.websocket_demo.interceptor.MyHandshakeInterceptor;
import com.example.websocket_demo.websocket.TestWebsocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author jt
 * @date 2019-6-15
 */

@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {


    @Autowired
    private MyHandshakeInterceptor myHandshakeInterceptor;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(testWebsocket(),"/ws").setAllowedOrigins("*").addInterceptors(myHandshakeInterceptor);

    }

    @Bean
    public WebSocketHandler testWebsocket(){
        return new TestWebsocket();
    }
}
