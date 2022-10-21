package com.tanzheng.blog.strategy.context;

import com.tanzheng.blog.dto.UserInfoDTO;
import com.tanzheng.blog.enums.LoginTypeEnum;
import com.tanzheng.blog.strategy.SocialLoginStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 第三方登录策略上下文
 *
 * @author shino
 * @date 2022/08/28
 */
@Service
public class SocialLoginStrategyContext {

    @Resource
    private Map<String, SocialLoginStrategy> socialLoginStrategyMap;

    /**
     * 执行第三方登录策略
     *
     * @param data          数据
     * @param loginTypeEnum 登录枚举类型
     * @return {@link UserInfoDTO} 用户信息
     */
    public UserInfoDTO executeLoginStrategy(String data, LoginTypeEnum loginTypeEnum) {
        return socialLoginStrategyMap.get(loginTypeEnum.getStrategy()).login(data);
    }

}
