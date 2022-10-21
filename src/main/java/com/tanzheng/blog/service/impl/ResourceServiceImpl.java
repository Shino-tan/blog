package com.tanzheng.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanzheng.blog.dao.ResourceDao;
import com.tanzheng.blog.dao.RoleResourceDao;
import com.tanzheng.blog.dto.ResourceDTO;
import com.tanzheng.blog.dto.LabelOptionDTO;
import com.tanzheng.blog.entity.Resource;
import com.tanzheng.blog.entity.RoleResource;
import com.tanzheng.blog.exception.BizException;
import com.tanzheng.blog.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.tanzheng.blog.service.ResourceService;
import com.tanzheng.blog.util.BeanCopyUtils;
import com.tanzheng.blog.vo.ConditionVO;
import com.tanzheng.blog.vo.ResourceVO;
import com.tanzheng.blog.constant.CommonConst;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源服务
 *
 * @author shino
 * @date 2022/08/28
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource> implements ResourceService {
    @javax.annotation.Resource
    private ResourceDao resourceDao;
    @javax.annotation.Resource
    private RoleResourceDao roleResourceDao;
    @javax.annotation.Resource
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;


    @Override
    public void saveOrUpdateResource(ResourceVO resourceVO) {
        // 更新资源信息
        Resource resource = BeanCopyUtils.copyObject(resourceVO, Resource.class);
        this.saveOrUpdate(resource);
        // 重新加载角色资源信息
        filterInvocationSecurityMetadataSource.clearDataSource();
    }

    @Override
    public void deleteResource(Integer resourceId) {
        // 查询是否有角色关联
        Integer count = roleResourceDao.selectCount(new LambdaQueryWrapper<RoleResource>()
                .eq(RoleResource::getResourceId, resourceId));
        if (count > 0) {
            throw new BizException("该资源下存在角色");
        }
        // 删除子资源
        List<Integer> resourceIdList = resourceDao.selectList(new LambdaQueryWrapper<Resource>()
                        .select(Resource::getId).
                        eq(Resource::getParentId, resourceId))
                .stream()
                .map(Resource::getId)
                .collect(Collectors.toList());
        resourceIdList.add(resourceId);
        resourceDao.deleteBatchIds(resourceIdList);
    }

    @Override
    public List<ResourceDTO> listResources(ConditionVO conditionVO) {
        // 查询资源列表
        List<Resource> resourceList = resourceDao.selectList(new LambdaQueryWrapper<Resource>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Resource::getResourceName, conditionVO.getKeywords()));
        // 获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resourceList);
        // 绑定模块下的所有接口
        List<ResourceDTO> resourceDTOList = parentList.stream().map(item -> {
            ResourceDTO resourceDTO = BeanCopyUtils.copyObject(item, ResourceDTO.class);
            List<ResourceDTO> childrenList = BeanCopyUtils.copyList(childrenMap.get(item.getId()), ResourceDTO.class);
            resourceDTO.setChildren(childrenList);
            childrenMap.remove(item.getId());
            return resourceDTO;
        }).collect(Collectors.toList());
        // 若还有资源未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Resource> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<ResourceDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, ResourceDTO.class))
                    .collect(Collectors.toList());
            resourceDTOList.addAll(childrenDTOList);
        }
        return resourceDTOList;
    }

    @Override
    public List<LabelOptionDTO> listResourceOption() {
        // 查询资源列表
        List<Resource> resourceList = resourceDao.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getId, Resource::getResourceName, Resource::getParentId)
                .eq(Resource::getIsAnonymous, CommonConst.FALSE));
        // 获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resourceList);
        // 组装父子数据
        return parentList.stream().map(item -> {
            List<LabelOptionDTO> list = new ArrayList<>();
            List<Resource> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .map(resource -> LabelOptionDTO.builder()
                                .id(resource.getId())
                                .label(resource.getResourceName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getResourceName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * 获取模块下的所有资源
     *
     * @param resourceList 资源列表
     * @return 模块资源
     */
    private Map<Integer, List<Resource>> listResourceChildren(List<Resource> resourceList) {
        return resourceList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Resource::getParentId));
    }

    /**
     * 获取所有资源模块
     *
     * @param resourceList 资源列表
     * @return 资源模块列表
     */
    private List<Resource> listResourceModule(List<Resource> resourceList) {
        return resourceList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
    }

}
