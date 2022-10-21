package com.tanzheng.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanzheng.blog.entity.ChatRecord;
import org.springframework.stereotype.Repository;

/**
 * 聊天记录
 *
 * @author shino
 * @date 2022/08/30
 */
@Repository
public interface ChatRecordDao extends BaseMapper<ChatRecord> {
}
