package com.jiantou.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiantou.demo.model.SysUser;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserByUsername(String username);

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);
}