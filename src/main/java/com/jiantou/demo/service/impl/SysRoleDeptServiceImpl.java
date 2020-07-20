package com.jiantou.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiantou.demo.mapper.SysRoleDeptMapper;
import com.jiantou.demo.model.SysRoleDept;
import com.jiantou.demo.service.SysRoleDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements SysRoleDeptService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        //先删除角色与部门关系
        deleteBatch(new Long[]{roleId});

        if(deptIdList.size() == 0){
            return ;
        }

        //保存角色与部门关系
        for(Long deptId : deptIdList){
            SysRoleDept sysRoleDept = new SysRoleDept();
            sysRoleDept.setDeptId(deptId);
            sysRoleDept.setRoleId(roleId);

            this.save(sysRoleDept);
        }
    }

    @Override
    public List<Long> queryDeptIdList(Long[] roleIds) {
        return baseMapper.queryDeptIdList(roleIds);
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        return baseMapper.deleteBatch(roleIds);
    }
}
