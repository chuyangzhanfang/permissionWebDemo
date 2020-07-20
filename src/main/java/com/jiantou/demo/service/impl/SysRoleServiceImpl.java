package com.jiantou.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiantou.demo.annotation.DataFilter;
import com.jiantou.demo.mapper.SysRoleMapper;
import com.jiantou.demo.model.SysDept;
import com.jiantou.demo.model.SysRole;
import com.jiantou.demo.service.*;
import com.jiantou.demo.util.Constant;
import com.jiantou.demo.util.PageUtils;
import com.jiantou.demo.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String)params.get("roleName");

        IPage<SysRole> page = this.page(
                new Query<SysRole>().getPage(params),
                new QueryWrapper<SysRole>()
                        .like(StringUtils.isNotBlank(roleName),"role_name", roleName)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        for(SysRole sysRole : page.getRecords()){
            SysDept sysDept = sysDeptService.getById(sysRole.getDeptId());
            if(sysDept != null){
                sysRole.setDeptName(sysDept.getName());
            }
        }

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRole role) {
        role.setCreateTime(new Date());
        this.save(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRole role) {
        this.updateById(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与部门关联
        sysRoleDeptService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }

}
