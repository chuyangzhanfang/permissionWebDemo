package com.jiantou.demo.controller;

import com.jiantou.demo.annotation.SysLog;
import com.jiantou.demo.model.SysRole;
import com.jiantou.demo.service.SysRoleDeptService;
import com.jiantou.demo.service.SysRoleMenuService;
import com.jiantou.demo.service.SysRoleService;
import com.jiantou.demo.util.PageUtils;
import com.jiantou.demo.util.R;
//import com.jiantou.demo.validator.ValidatorUtils;
import com.jiantou.demo.validator.ValidatorUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author jialin
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    @ApiOperation(value = "角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }

    @ApiOperation(value = "角色列表2")
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @RequiresPermissions("sys:role:select")
    public R select(){
        List<SysRole> list = sysRoleService.list();

        return R.ok().put("list", list);
    }

    @ApiOperation(value = "角色信息")
    @RequestMapping(value = "/info/{roleId}", method = RequestMethod.GET)
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId){
        SysRole role = sysRoleService.getById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        //查询角色对应的部门
        List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
        role.setDeptIdList(deptIdList);

        return R.ok().put("role", role);
    }

    @ApiOperation(value = "保存角色")
    @SysLog("保存角色")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRole role){
        ValidatorUtils.validateEntity(role);

        sysRoleService.saveRole(role);

        return R.ok();
    }

    @ApiOperation(value = "修改角色")
    @SysLog("修改角色")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRole role){
        ValidatorUtils.validateEntity(role);

        sysRoleService.update(role);

        return R.ok();
    }

    @ApiOperation(value = "删除角色")
    @SysLog("删除角色")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds){
        sysRoleService.deleteBatch(roleIds);

        return R.ok();
    }
}
