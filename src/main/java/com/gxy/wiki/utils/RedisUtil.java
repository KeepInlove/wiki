package com.gxy.wiki.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author GUO
 * @Classname RedisUtil
 * @Description TODO
 * @Date 2021/12/5 12:53
 */
@Component
@Slf4j
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * true：不存在，放一个KEY
     * false：已存在
     * @param key
     * @param second
     * @return
     */
    public boolean validateRepeat(String key, long second) {
        if (redisTemplate.hasKey(key)) {
            log.info("key已存在：{}", key);
            return false;
        } else {
            log.warn("key不存在，放入：{}，过期 {} 秒", key, second);
            //存入redis(key=key,value=key)
            redisTemplate.opsForValue().set(key, key, second, TimeUnit.SECONDS);
            return true;
        }
    }
}
