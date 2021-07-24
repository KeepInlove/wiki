package com.gxy.wiki.service;

import com.gxy.wiki.domain.Demo;
import com.gxy.wiki.mapper.DemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GUO
 * @Classname TestService
 * @Description TODO
 * @Date 2021/7/21 22:36
 */
@Service
public class DemoService {

    @Autowired
    private DemoMapper demoMapper;

    public List<Demo> list(){

        return demoMapper.selectByExample(null);
    }
}
