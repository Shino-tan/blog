package com.tanzheng.blog.dao;

import com.tanzheng.blog.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


/**
 * 用户信息
 *
 * @author shino
 * @date 2022/08/30
 */
@Repository
public interface UserInfoDao extends BaseMapper<UserInfo> {

}
