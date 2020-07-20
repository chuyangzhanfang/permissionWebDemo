package com.jiantou.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiantou.demo.mapper.SysUserRoleMapper;
import com.jiantou.demo.model.SysUserRole;
import com.jiantou.demo.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        this.remove(new QueryWrapper<SysUserRole>().eq("user_id", userId));

        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }

        //保存用户与角色关系
        for(Long roleId : roleIdList){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);

            this.save(sysUserRole);
        }
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return baseMapper.queryRoleIdList(userId);
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        return baseMapper.deleteBatch(roleIds);
    }

}
