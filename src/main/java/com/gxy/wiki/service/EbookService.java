package com.gxy.wiki.service;

import com.github.pagehelper.PageHelper;
import com.gxy.wiki.domain.Ebook;
import com.gxy.wiki.domain.EbookExample;
import com.gxy.wiki.mapper.EbookMapper;
import com.gxy.wiki.req.EbookReq;
import com.gxy.wiki.resp.EbookResp;
import com.gxy.wiki.utils.CopyUtil;
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
public class EbookService {
    @Autowired
    private EbookMapper ebookMapper;
    public List<EbookResp>list(EbookReq req){
        PageHelper.startPage(1,3);
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

//        List<EbookResp>respList=new ArrayList<>();
//        for (Ebook ebook:ebookList){
//            EbookResp ebookResp=new EbookResp();
//            BeanUtils.copyProperties(ebook,ebookResp);
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }
        //拷贝工具类
        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);

        return respList;
    }
}
