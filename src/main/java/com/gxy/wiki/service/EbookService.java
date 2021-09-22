package com.gxy.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxy.wiki.domain.Ebook;
import com.gxy.wiki.domain.EbookExample;
import com.gxy.wiki.mapper.EbookMapper;
import com.gxy.wiki.req.EbookReq;
import com.gxy.wiki.resp.EbookResp;
import com.gxy.wiki.resp.PageResp;
import com.gxy.wiki.utils.CopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author GUO
 * @Classname EbookService
 * @Description 电子书列表
 * @Date 2021/7/24 16:38
 */
@Service
@Slf4j
public class EbookService {
    @Autowired
    private EbookMapper ebookMapper;
    public PageResp<EbookResp> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook>pageInfo=new PageInfo<>(ebookList);
        log.info("总数:{}",pageInfo.getTotal());
        log.info("总页数:{}",pageInfo.getPages());

//        List<EbookResp>respList=new ArrayList<>();
//        for (Ebook ebook:ebookList){
//            EbookResp ebookResp=new EbookResp();
//            BeanUtils.copyProperties(ebook,ebookResp);
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }


        //拷贝工具类
        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);

        PageResp<EbookResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }
}
