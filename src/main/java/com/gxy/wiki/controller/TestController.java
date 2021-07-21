package com.gxy.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GUO
 * @Classname TestController
 * @Description TODO
 * @Date 2021/7/21 15:50
 */
@RestController //返回JSON字符串 =@ResponseBody+@Controller
//@ResponseBody 返回json对象
//@Controller 返回一个页面
public class TestController {
    /**
     * GET,POST,PUT,DELETE
     * /user?id=1
     * /user/1
     *
     * @return
     */
    @GetMapping("/hello")
//    @PostMapping
//    @PutMapping
//    @DeleteMapping
//    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        return "hello API";
    }
    @PostMapping("/helloPost")
    public String helloPost(String name){
        return "hello API!!Post:"+name;
    }
}
