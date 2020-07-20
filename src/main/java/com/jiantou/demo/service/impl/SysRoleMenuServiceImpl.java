package com.jiantou.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiantou.demo.mapper.SysRoleMenuMapper;
import com.jiantou.demo.model.SysRoleMenu;
import com.jiantou.demo.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(new Long[]{roleId});

        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        for(Long menuId : menuIdList){
            SysRoleMenu sysRoleMenuEntity = new SysRoleMenu();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);

            this.save(sysRoleMenuEntity);
        }
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        return baseMapper.deleteBatch(roleIds);
    }
}
