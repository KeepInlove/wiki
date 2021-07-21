package com.gxy.wiki.service;

import com.gxy.wiki.domain.Test;
import com.gxy.wiki.mapper.TestMapper;
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
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public List<Test> list(){
        return testMapper.list();
    }
}
