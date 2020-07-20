package com.jiantou.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiantou.demo.model.SysRoleDept;

import java.util.List;

public interface SysRoleDeptService extends IService<SysRoleDept> {
    void saveOrUpdate(Long roleId, List<Long> deptIdList);

    /**
     * 根据角色ID，获取部门ID列表
     */
    List<Long> queryDeptIdList(Long[] roleIds) ;

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}
