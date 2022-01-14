package com.gxy.wiki.service;

import com.gxy.wiki.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author GUO
 * @Classname WsService
 * @Description TODO
 * @Date 2022/1/14 11:07
 */
@Service
public class WsService {
    @Resource
    private WebSocketServer webSocketServer;

    @Async
    public void sendInfo(String message,String logId){
        MDC.put("LOG_ID",logId);
        webSocketServer.sendInfo(message);
    }
}
