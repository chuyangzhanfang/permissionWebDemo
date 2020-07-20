package com.jiantou.demo.mapper;

import com.jiantou.demo.model.SysMenu;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();
}