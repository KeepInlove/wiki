package com.gxy.wiki.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author GUO
 * @Classname TestMapper
 * @Description TODO
 * @Date 2021/7/21 22:23
 */
public interface DocMapperCust {
    public void increaseViewCount(@Param("id") Long id);

   public void increaseVoteCount(@Param("id") Long id);
}
