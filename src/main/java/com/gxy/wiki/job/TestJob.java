package com.gxy.wiki.job;

import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author GUO
 * @Classname TestJob
 * @Description TODO
 * @Date 2021/12/5 15:53
 */
//@Component
//@Slf4j
public class TestJob {

    @Scheduled(fixedRate = 5000)
    public void simple()throws InterruptedException{
        SimpleDateFormat format=new SimpleDateFormat("mm:ss");
        String dateString    = format.format(new Date());
//        Thread.sleep(2000);
//        log.info("每隔5秒钟执行一次:{}",dateString);
    }
    /**
     * 自定义cron表达式跑批
     * 只有等上一次执行完成，下一次才会在下一个时间点执行，错过就错过
     */
    @Scheduled(cron = "*/2 * * * * ?")
    public void cron() throws InterruptedException {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss SSS");
        String dateString = formatter.format(new Date());
        Thread.sleep(1500);
//        log.info("每隔2秒钟执行一次： {}", dateString);
    }
}
