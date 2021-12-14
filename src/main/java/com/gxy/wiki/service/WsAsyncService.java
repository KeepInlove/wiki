package com.gxy.wiki.service;

import com.gxy.wiki.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author GUO
 * @Classname WsAsyncService
 * @Description TODO
 * @Date 2021/12/15 0:27
 */

@Service
public class WsAsyncService {


    @Autowired
    private WebSocketServer webSocketServer;
    @Async
    public void sendInfo(String message){
        webSocketServer.sendInfo(message);
    }

}
