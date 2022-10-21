package com.tanzheng.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tanzheng.blog.dto.FriendLinkBackDTO;
import com.tanzheng.blog.dto.FriendLinkDTO;
import com.tanzheng.blog.util.PageUtils;
import com.tanzheng.blog.vo.ConditionVO;
import com.tanzheng.blog.vo.PageResult;
import com.tanzheng.blog.entity.FriendLink;
import com.tanzheng.blog.dao.FriendLinkDao;
import com.tanzheng.blog.service.FriendLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanzheng.blog.util.BeanCopyUtils;
import com.tanzheng.blog.vo.FriendLinkVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 友情链接服务
 *
 * @author xiaojie
 * @date 2022/08/30
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkDao, FriendLink> implements FriendLinkService {
    @Resource
    private FriendLinkDao friendLinkDao;

    @Override
    public List<FriendLinkDTO> listFriendLinks() {
        // 查询友链列表
        List<FriendLink> friendLinkList = friendLinkDao.selectList(null);
        return BeanCopyUtils.copyList(friendLinkList, FriendLinkDTO.class);
    }

    @Override
    public PageResult<FriendLinkBackDTO> listFriendLinkDTO(ConditionVO condition) {
        // 分页查询友链列表
        Page<FriendLink> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        Page<FriendLink> friendLinkPage = friendLinkDao.selectPage(page, new LambdaQueryWrapper<FriendLink>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), FriendLink::getLinkName, condition.getKeywords()));
        // 转换DTO
        List<FriendLinkBackDTO> friendLinkBackDTOList = BeanCopyUtils.copyList(friendLinkPage.getRecords(), FriendLinkBackDTO.class);
        return new PageResult<>(friendLinkBackDTOList, (int) friendLinkPage.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO) {
        FriendLink friendLink = BeanCopyUtils.copyObject(friendLinkVO, FriendLink.class);
        this.saveOrUpdate(friendLink);
    }

}
