package com.gxy.wiki.controller;

import com.gxy.wiki.req.EbookQueryReq;
import com.gxy.wiki.req.EbookSaveReq;
import com.gxy.wiki.resp.CommonResp;
import com.gxy.wiki.resp.EbookQueryResp;
import com.gxy.wiki.resp.PageResp;
import com.gxy.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public CommonResp list(@Valid EbookQueryReq req){
        CommonResp<PageResp<EbookQueryResp>> resp=new CommonResp<>();
        PageResp<EbookQueryResp> pageResp=ebookService.list(req);
        resp.setContent(pageResp);
       return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody EbookSaveReq req){
        CommonResp resp=new CommonResp();
        ebookService.save(req);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp=new CommonResp();
        ebookService.delete(id);
        return resp;
    }
}
