package com.jiantou.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiantou.demo.model.SysUser;
import com.jiantou.demo.util.PageUtils;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 保存用户
     */
    void saveUser(SysUser user);

    /**
     * 修改用户
     */
    void update(SysUser user);

    /**
     * 修改密码
     * @param userId       用户ID
     * @param password     原密码
     * @param newPassword  新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);
}
