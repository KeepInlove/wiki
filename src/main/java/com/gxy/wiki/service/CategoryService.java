package com.gxy.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxy.wiki.domain.Category;
import com.gxy.wiki.domain.CategoryExample;
import com.gxy.wiki.mapper.CategoryMapper;
import com.gxy.wiki.req.CategoryQueryReq;
import com.gxy.wiki.req.CategorySaveReq;
import com.gxy.wiki.resp.CategoryQueryResp;
import com.gxy.wiki.resp.PageResp;
import com.gxy.wiki.utils.CopyUtil;
import com.gxy.wiki.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import java.util.List;

/**
 * @author GUO
 * @Classname CategoryService
 * @Description 电子书列表
 * @Date 2021/7/24 16:38
 */
@Service
@Slf4j
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SnowFlake snowFlake;
    public PageResp<CategoryQueryResp> list(@Valid CategoryQueryReq req){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();

        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category>pageInfo=new PageInfo<>(categoryList);
        log.info("总数:{}",pageInfo.getTotal());
        log.info("总页数:{}",pageInfo.getPages());

//        List<CategoryResp>respList=new ArrayList<>();
//        for (Category category:categoryList){
//            CategoryResp categoryResp=new CategoryResp();
//            BeanUtils.copyProperties(category,categoryResp);
//            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//            respList.add(categoryResp);
//        }
        //拷贝工具类
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    public List<CategoryQueryResp> all(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        //拷贝工具类
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        return respList;
    }
    /**
     * 保存
     */
    public void save(CategorySaveReq req){
        Category category=CopyUtil.copy(req,Category.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insertSelective(category);
        }else {
            //更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
