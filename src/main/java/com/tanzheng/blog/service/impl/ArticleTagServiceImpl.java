package com.tanzheng.blog.service.impl;

import com.tanzheng.blog.entity.ArticleTag;
import com.tanzheng.blog.dao.ArticleTagDao;
import com.tanzheng.blog.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 文章标签服务
 *
 * @author shino
 * @date 2022/08/30
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTag> implements ArticleTagService {

}
