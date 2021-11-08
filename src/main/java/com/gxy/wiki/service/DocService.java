package com.gxy.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxy.wiki.domain.Doc;
import com.gxy.wiki.domain.DocExample;
import com.gxy.wiki.mapper.DocMapper;
import com.gxy.wiki.req.DocQueryReq;
import com.gxy.wiki.req.DocSaveReq;
import com.gxy.wiki.resp.DocQueryResp;
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
 * @Classname DocService
 * @Description 电子书列表
 * @Date 2021/7/24 16:38
 */
@Service
@Slf4j
public class DocService {
    @Autowired
    private DocMapper docMapper;
    @Autowired
    private SnowFlake snowFlake;
    public PageResp<DocQueryResp> list(@Valid DocQueryReq req){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();

        PageHelper.startPage(req.getPage(),req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);
        PageInfo<Doc>pageInfo=new PageInfo<>(docList);
        log.info("总数:{}",pageInfo.getTotal());
        log.info("总页数:{}",pageInfo.getPages());

//        List<DocResp>respList=new ArrayList<>();
//        for (Doc doc:docList){
//            DocResp docResp=new DocResp();
//            BeanUtils.copyProperties(doc,docResp);
//            DocResp docResp = CopyUtil.copy(doc, DocResp.class);
//            respList.add(docResp);
//        }
        //拷贝工具类
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    public List<DocQueryResp> all(){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        //拷贝工具类
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);

        return respList;
    }
    /**
     * 保存
     */
    public void save(DocSaveReq req){
        Doc doc=CopyUtil.copy(req,Doc.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            docMapper.insertSelective(doc);
        }else {
            //更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }
    public void delete(List<String> ids) {
        //循环执行sql太多次,所以使用拼接sql
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }
}
