package com.gxy.wiki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author GUO
 * @Classname SpringMvcConfig
 * @Description TODO
 * @Date 2021/8/1 21:58
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
//    //注入拦截器
//    @Autowired
//    LogInterceptor logInterceptor;
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(logInterceptor)
//                .addPathPatterns("/**").excludePathPatterns("/login");
//    }
}
