package com.gxy.wiki.controller;

import com.gxy.wiki.req.DocQueryReq;
import com.gxy.wiki.req.DocSaveReq;
import com.gxy.wiki.resp.DocQueryResp;
import com.gxy.wiki.resp.CommonResp;
import com.gxy.wiki.resp.PageResp;
import com.gxy.wiki.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author GUO
 * @Classname DocController
 * @Description TODO
 * @Date 2021/7/24 16:43
 */
@RestController
@RequestMapping("/doc")
public class DocController {

    @Autowired
    private DocService docService;

    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req){
        CommonResp<PageResp<DocQueryResp>> resp=new CommonResp<>();
        PageResp<DocQueryResp> pageResp=docService.list(req);
        resp.setContent(pageResp);
       return resp;
    }
    @GetMapping("/all")
    public CommonResp all(){
        CommonResp<List<DocQueryResp>> resp=new CommonResp<>();
        List<DocQueryResp> list=docService.all();
        resp.setContent(list);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req){
        CommonResp resp=new CommonResp();
        docService.save(req);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp=new CommonResp();
        docService.delete(id);
        return resp;
    }
}
