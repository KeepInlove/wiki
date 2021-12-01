package com.gxy.wiki.controller;

import com.gxy.wiki.req.UserLoginReq;
import com.gxy.wiki.req.UserQueryReq;
import com.gxy.wiki.req.UserResetPasswordReq;
import com.gxy.wiki.req.UserSaveReq;
import com.gxy.wiki.resp.CommonResp;
import com.gxy.wiki.resp.PageResp;
import com.gxy.wiki.resp.UserLoginResp;
import com.gxy.wiki.resp.UserQueryResp;
import com.gxy.wiki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author GUO
 * @Classname UserController
 * @Description TODO
 * @Date 2021/10/24 16:43
 */
@RestController
@RequestMapping("/user")
public class  UserController {

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
        resp.setContent(userLoginResp);
        return resp;
    }
}
