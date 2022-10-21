package com.tanzheng.blog.dao;

import com.tanzheng.blog.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


/**
 * 留言
 *
 * @author shino
 * @date 2022/08/30
 */
@Repository
public interface MessageDao extends BaseMapper<Message> {

}
