package com.gxy.wiki.controller;

import com.gxy.wiki.req.EbookReq;
import com.gxy.wiki.resp.CommonResp;
import com.gxy.wiki.resp.EbookResp;
import com.gxy.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author GUO
 * @Classname EbookController
 * @Description TODO
 * @Date 2021/7/24 16:43
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Autowired
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req){
        CommonResp<List<EbookResp>> resp=new CommonResp();
        List<EbookResp>list=ebookService.list(req);
        resp.setContent(list);
       return resp;
    }
}
