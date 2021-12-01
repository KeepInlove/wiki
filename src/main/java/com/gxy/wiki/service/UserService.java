package com.gxy.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxy.wiki.domain.User;
import com.gxy.wiki.domain.UserExample;
import com.gxy.wiki.exception.BusinessException;
import com.gxy.wiki.exception.BusinessExceptionCode;
import com.gxy.wiki.mapper.UserMapper;
import com.gxy.wiki.req.UserLoginReq;
import com.gxy.wiki.req.UserQueryReq;
import com.gxy.wiki.req.UserResetPasswordReq;
import com.gxy.wiki.req.UserSaveReq;
import com.gxy.wiki.resp.PageResp;
import com.gxy.wiki.resp.UserLoginResp;
import com.gxy.wiki.resp.UserQueryResp;
import com.gxy.wiki.utils.CopyUtil;
import com.gxy.wiki.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author GUO
 * @Classname UserService
 * @Description 电子书列表
 * @Date 2021/7/24 16:38
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SnowFlake snowFlake;
    public PageResp<UserQueryResp> list(UserQueryReq req){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
        PageHelper.startPage(req.getPage(),req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo<User>pageInfo=new PageInfo<>(userList);
        log.info("总数:{}",pageInfo.getTotal());
        log.info("总页数:{}",pageInfo.getPages());

        //拷贝工具类
        List<UserQueryResp> respList = CopyUtil.copyList(userList, UserQueryResp.class);
        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }
    /**
     * 保存
     */
    public void save(UserSaveReq req){
        User user=CopyUtil.copy(req,User.class);
        if (ObjectUtils.isEmpty(req.getId())){
            User userDB = selectByLoginName(req.getLoginName());
            boolean empty = ObjectUtils.isEmpty(userDB);
            if (empty){
                //新增
                user.setId(snowFlake.nextId());
                userMapper.insertSelective(user);
            }else {
           //用户名已存在
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }else {
            //更新
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }
    //修改密码
    public void resetPassword(UserResetPasswordReq req){
        User user=CopyUtil.copy(req,User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    //按登录名查询用户
    public User selectByLoginName(String loginName){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        boolean empty = CollectionUtils.isEmpty(userList);
        return empty?null:userList.get(0);
    }

    //登录
    public UserLoginResp login(UserLoginReq req) {
        User userDb = selectByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(userDb)){
            //用户名不存在
            log.info("用户名不存在,{}",req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }else {
            if (userDb.getPassword().equals(req.getPassword())){
                //登录成功
                UserLoginResp userLoginResp=CopyUtil.copy(userDb,UserLoginResp.class);
                return userLoginResp;
            }else {
                //密码错误
                log.info("密码错误,输入密码:{},数据库密码:{}",req.getPassword(),userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }

    }
}
