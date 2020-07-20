package com.jiantou.demo.controller;

import com.jiantou.demo.annotation.SysLog;
import com.jiantou.demo.model.SysDept;
import com.jiantou.demo.model.SysUser;
import com.jiantou.demo.service.SysDeptService;
import com.jiantou.demo.service.SysUserRoleService;
import com.jiantou.demo.service.SysUserService;
import com.jiantou.demo.shiro.ShiroUtils;
import com.jiantou.demo.util.PageUtils;
import com.jiantou.demo.util.R;
import com.jiantou.demo.validator.Assert;
import com.jiantou.demo.validator.ValidatorUtils;
import com.jiantou.demo.validator.group.AddGroup;
import com.jiantou.demo.validator.group.UpdateGroup;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;


    @ApiOperation(value = "所有用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    @ApiOperation(value = "获取登录的用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R info(){
        return R.ok().put("user", getUser());
    }

    @ApiOperation(value = "修改登录用户密码")
    @SysLog("修改密码")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public R password(String password, String newPassword){
        Assert.isBlank(newPassword, "新密码不为能空");

        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if(!flag){
            return R.error("原密码不正确");
        }

        return R.ok();
    }

    @ApiOperation(value = "用户信息")
    @RequestMapping(value = "/info/{userId}", method = RequestMethod.GET)
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId){
        SysUser user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        //获取用户所在的部门名称
        SysDept sysDept = sysDeptService.getById(user.getDeptId());
        user.setDeptName(sysDept.getName());

        return R.ok().put("user", user);
    }

    @ApiOperation(value = "保存用户")
    @SysLog("保存用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUser user){
        ValidatorUtils.validateEntity(user, AddGroup.class);

        sysUserService.saveUser(user);

        return R.ok();
    }

    @ApiOperation(value = "修改用户")
    @SysLog("修改用户")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUser user){
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        sysUserService.update(user);

        return R.ok();
    }

    @ApiOperation(value = "删除用户")
    @SysLog("删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds){
        if(ArrayUtils.contains(userIds, 1L)){
            return R.error("系统管理员不能删除");
        }

        if(ArrayUtils.contains(userIds, getUserId())){
            return R.error("当前用户不能删除");
        }

        sysUserService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }


}
