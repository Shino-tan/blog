package com.tanzheng.blog.controller;


import com.tanzheng.blog.common.annotation.OptLog;
import com.tanzheng.blog.dto.FriendLinkBackDTO;
import com.tanzheng.blog.dto.FriendLinkDTO;
import com.tanzheng.blog.vo.ConditionVO;
import com.tanzheng.blog.vo.FriendLinkVO;
import com.tanzheng.blog.vo.PageResult;
import com.tanzheng.blog.service.FriendLinkService;
import com.tanzheng.blog.vo.*;
import com.tanzheng.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.tanzheng.blog.constant.OptTypeConst.SAVE_OR_UPDATE;
import static com.tanzheng.blog.constant.OptTypeConst.REMOVE;

/**
 * 友链控制器
 *
 * @author shino
 * @date 2022/08/29
 */
@Api(tags = "友链模块")
@RestController
public class FriendLinkController {
    @Resource
    private FriendLinkService friendLinkService;

    /**
     * 查看友链列表
     *
     * @return {@link Result <FriendLinkDTO>} 友链列表
     */
    @ApiOperation(value = "查看友链列表")
    @GetMapping("/links")
    public Result<List<FriendLinkDTO>> listFriendLinks() {
        return Result.ok(friendLinkService.listFriendLinks());
    }

    /**
     * 查看后台友链列表
     *
     * @param condition 条件
     * @return {@link Result<FriendLinkBackDTO>} 后台友链列表
     */
    @ApiOperation(value = "查看后台友链列表")
    @GetMapping("/admin/links")
    public Result<PageResult<FriendLinkBackDTO>> listFriendLinkDTO(ConditionVO condition) {
        return Result.ok(friendLinkService.listFriendLinkDTO(condition));
    }

    /**
     * 保存或修改友链
     *
     * @param friendLinkVO 友链信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或修改友链")
    @PostMapping("/admin/links")
    public Result<?> saveOrUpdateFriendLink(@Valid @RequestBody FriendLinkVO friendLinkVO) {
        friendLinkService.saveOrUpdateFriendLink(friendLinkVO);
        return Result.ok();
    }

    /**
     * 删除友链
     *
     * @param linkIdList 友链id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除友链")
    @DeleteMapping("/admin/links")
    public Result<?> deleteFriendLink(@RequestBody List<Integer> linkIdList) {
        friendLinkService.removeByIds(linkIdList);
        return Result.ok();
    }

}

