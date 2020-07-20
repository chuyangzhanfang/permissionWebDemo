package com.jiantou.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiantou.demo.model.SysDept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

public interface SysDeptMapper extends BaseMapper<SysDept> {
    List<SysDept> queryList(Map<String, Object> params);

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);
}