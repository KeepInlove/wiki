package com.gxy.wiki;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.env.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@Slf4j
@SpringBootApplication
@MapperScan("com.gxy.wiki.mapper")
@EnableScheduling
@EnableAsync
public class WikiApplication {

    private static final Logger log = LoggerFactory.getLogger(WikiApplication.class);
    public static void main(String[] args) {
        SpringApplication app=new SpringApplication(WikiApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("数据提供启动成功！！地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

}
