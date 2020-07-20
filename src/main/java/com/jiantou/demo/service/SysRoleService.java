package com.jiantou.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiantou.demo.model.SysRole;
import com.jiantou.demo.util.PageUtils;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {

    PageUtils queryPage(Map<String, Object> params);

    void saveRole(SysRole role);

    void update(SysRole role);

    void deleteBatch(Long[] roleIds);

}
