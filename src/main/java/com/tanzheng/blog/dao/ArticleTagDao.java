package com.tanzheng.blog.dao;

import com.tanzheng.blog.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


/**
 * 文章标签
 *
 * @author shino
 * @date 2022/08/30
 */
@Repository
public interface ArticleTagDao extends BaseMapper<ArticleTag> {

}
