package com.gxy.wiki.job;

import com.gxy.wiki.service.DocService;
import com.gxy.wiki.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author GUO
 * @Classname TestJob
 * @Description TODO
 * @Date 2021/12/5 15:53
 */
@Component
@Slf4j
public class DocJob {

    @Autowired
    DocService docService;
    @Resource
    private SnowFlake snowFlake;
    /**
     *
     * 每10秒更新电子信息
     */
    @Scheduled(cron = "5/10 * * * * ?")
    public void cron() {
        // 增加日志流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        log.info("---更新电子书下的文档数据开始---");
        long start = System.currentTimeMillis();
        docService.updateEbookInfo();
        log.info("---更新电子书下的文档数据结束,耗时:{}毫秒---",System.currentTimeMillis()-start);
    }
}
