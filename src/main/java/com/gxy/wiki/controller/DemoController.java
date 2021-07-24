package com.gxy.wiki.controller;

import com.gxy.wiki.domain.Demo;
import com.gxy.wiki.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author GUO
 * @Classname DemoController
 * @Description TODO
 * @Date 2021/7/24 15:29
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;
    @GetMapping("/list")
    public List<Demo> list(){
        return demoService.list();
    }
}
