package com.gxy.wiki.controller;

import com.gxy.wiki.req.CategoryQueryReq;
import com.gxy.wiki.req.CategorySaveReq;
import com.gxy.wiki.resp.CommonResp;
import com.gxy.wiki.resp.CategoryQueryResp;
import com.gxy.wiki.resp.PageResp;
import com.gxy.wiki.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author GUO
 * @Classname CategoryController
 * @Description TODO
 * @Date 2021/7/24 16:43
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req){
        CommonResp<PageResp<CategoryQueryResp>> resp=new CommonResp<>();
        PageResp<CategoryQueryResp> pageResp=categoryService.list(req);
        resp.setContent(pageResp);
       return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req){
        CommonResp resp=new CommonResp();
        categoryService.save(req);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp=new CommonResp();
        categoryService.delete(id);
        return resp;
    }
}
