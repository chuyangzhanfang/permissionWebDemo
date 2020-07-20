package com.jiantou.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiantou.demo.model.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<SysMenu> getUserMenuList(Long userId);

    /**
     * 删除
     */
    void delete(Long menuId);
}
