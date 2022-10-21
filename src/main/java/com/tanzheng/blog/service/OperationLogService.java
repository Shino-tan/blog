package com.tanzheng.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tanzheng.blog.dto.OperationLogDTO;
import com.tanzheng.blog.vo.PageResult;
import com.tanzheng.blog.entity.OperationLog;
import com.tanzheng.blog.vo.ConditionVO;

/**
 * 操作日志服务
 *
 * @author shino
 * @date 2022/08/29
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 查询日志列表
     *
     * @param conditionVO 条件
     * @return 日志列表
     */
    PageResult<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);

}
