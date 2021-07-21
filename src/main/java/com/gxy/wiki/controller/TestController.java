package com.gxy.wiki.controller;

import com.gxy.wiki.domain.Test;
import com.gxy.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author GUO
 * @Classname TestController
 * @Description 测试接口
 * @Date 2021/7/21 15:50
 */
@RestController //返回JSON字符串 =@ResponseBody+@Controller
//@ResponseBody 返回json对象
//@Controller 返回一个页面
public class TestController {

    @Autowired
    private TestService testService;
    @Value("${test.hello}")
    private String testHello;
    /**
     * GET,POST,PUT,DELETE
     * /user?id=1
     * /user/1
     *
     * @return
     */
//    @PostMapping
//    @PutMapping
//    @DeleteMapping
//    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @GetMapping("/hello")
    public String hello(){
        return "hello API! "+testHello;
    }
    @GetMapping("/list")
    public List<Test> list(){
        return testService.list();
    }
    @PostMapping("/helloPost")
    public String helloPost(String name){
        return "hello API!!Post:"+name;
    }
}
