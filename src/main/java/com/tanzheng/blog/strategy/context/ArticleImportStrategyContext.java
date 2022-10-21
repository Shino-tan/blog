package com.tanzheng.blog.strategy.context;

import com.tanzheng.blog.enums.MarkdownTypeEnum;
import com.tanzheng.blog.strategy.ArticleImportStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 文章导入策略上下文
 *
 * @author linweiyuan
 * @date 2022/08/28
 */
@Service
public class ArticleImportStrategyContext {
    @Resource
    private Map<String, ArticleImportStrategy> articleImportStrategyMap;

    public void importArticles(MultipartFile file, String type) {
        articleImportStrategyMap.get(MarkdownTypeEnum.getMarkdownType(type)).importArticles(file);
    }
}
