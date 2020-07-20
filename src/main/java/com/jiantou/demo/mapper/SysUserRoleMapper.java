package com.jiantou.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiantou.demo.model.SysUserRole;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}