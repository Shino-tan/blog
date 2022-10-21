package com.tanzheng.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanzheng.blog.entity.OperationLog;
import org.springframework.stereotype.Repository;


/**
 * 操作日志
 *
 * @author shino
 * @date 2022/08/30
 */
@Repository
public interface OperationLogDao extends BaseMapper<OperationLog> {
}
