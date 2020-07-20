package com.jiantou.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiantou.demo.annotation.DataFilter;
import com.jiantou.demo.mapper.SysUserMapper;
import com.jiantou.demo.model.SysDept;
import com.jiantou.demo.model.SysUser;
import com.jiantou.demo.service.SysDeptService;
import com.jiantou.demo.service.SysUserRoleService;
import com.jiantou.demo.service.SysUserService;
import com.jiantou.demo.shiro.ShiroUtils;
import com.jiantou.demo.util.Constant;
import com.jiantou.demo.util.PageUtils;
import com.jiantou.demo.util.Query;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");

        IPage<SysUser> page = this.page(
                new Query<SysUser>().getPage(params),
                new QueryWrapper<SysUser>()
                        .like(StringUtils.isNotBlank(username),"username", username)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        for(SysUser sysUser : page.getRecords()){
            SysDept sysDeptEntity = sysDeptService.getById(sysUser.getDeptId());
            sysUser.setDeptName(sysDeptEntity.getName());
        }

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUser user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(user.getPassword(), salt));
        this.save(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUser user) {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            SysUser userEntity = this.getById(user.getUserId());
            user.setPassword(ShiroUtils.sha256(user.getPassword(), userEntity.getSalt()));
        }
        this.updateById(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }


    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUser user = new SysUser();
        user.setPassword(newPassword);
        return this.update(user,
                new QueryWrapper<SysUser>().eq("user_id", userId).eq("password", password));
    }

}
