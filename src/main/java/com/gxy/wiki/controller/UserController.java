package com.gxy.wiki.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxy.wiki.req.UserLoginReq;
import com.gxy.wiki.req.UserQueryReq;
import com.gxy.wiki.req.UserResetPasswordReq;
import com.gxy.wiki.req.UserSaveReq;
import com.gxy.wiki.resp.CommonResp;
import com.gxy.wiki.resp.PageResp;
import com.gxy.wiki.resp.UserLoginResp;
import com.gxy.wiki.resp.UserQueryResp;
import com.gxy.wiki.service.UserService;
import com.gxy.wiki.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @author GUO
 * @Classname UserController
 * @Description TODO
 * @Date 2021/10/24 16:43
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class  UserController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req){
        CommonResp<PageResp<UserQueryResp>> resp=new CommonResp<>();
        PageResp<UserQueryResp> pageResp=userService.list(req);
        resp.setContent(pageResp);
       return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp=new CommonResp();
        userService.save(req);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp=new CommonResp();
        userService.delete(id);
        return resp;
    }

    @PostMapping("/resetPassword")
    public CommonResp resetPassword(@Valid @RequestBody UserResetPasswordReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp=new CommonResp();
        userService.resetPassword(req);
        return resp;
    }

    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserLoginResp> resp=new CommonResp();
        UserLoginResp userLoginResp= userService.login(req);
        //生成单点登录token,存入redis
        Long token = snowFlake.nextId();
        userLoginResp.setToken(token.toString());
        log.info("生成单点登录token:{},存入redis中",token);
        //将类存入redis需要序列化有两种方式:
        //1.userLoginResp implements Serializable
        //2.类转为json格式
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp),3600*24, TimeUnit.SECONDS);
        resp.setContent(userLoginResp);
        return resp;
    }
    @DeleteMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token){
        CommonResp resp=new CommonResp();
        Boolean b = redisTemplate.delete(token);
        log.info("删除redis中token:{},是否删除成功:{}",token,b);
        return resp;
    }

}
