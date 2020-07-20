package com.jiantou.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiantou.demo.model.SysRoleMenu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Long roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}