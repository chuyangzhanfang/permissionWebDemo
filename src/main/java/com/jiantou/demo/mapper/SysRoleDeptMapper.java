package com.jiantou.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiantou.demo.model.SysRoleDept;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {
    /**
     * 根据角色ID，获取部门ID列表
     */
    List<Long> queryDeptIdList(Long[] roleIds);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}