package com.gxy.wiki.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author GUO
 * @Classname DemoServiceTest
 * @Description TODO
 * @Date 2021/11/12 12:11
 */
@SpringBootTest
@Slf4j
class DemoServiceTest {

    @Test
    public void test(){
        log.debug("debug...");
        log.info("info...");
        String name="guo";
        String password="123456";
        log.info("name:"+name+"password:"+password);
        log.info("-----");
        log.info("name:{},password:{}",name,password);
        log.error("error...");
    }
}