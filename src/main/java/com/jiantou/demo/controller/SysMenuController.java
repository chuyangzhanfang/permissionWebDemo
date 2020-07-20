package com.jiantou.demo.controller;

import com.jiantou.demo.annotation.SysLog;
import com.jiantou.demo.exception.RRException;
import com.jiantou.demo.model.SysMenu;
import com.jiantou.demo.service.SysMenuService;
import com.jiantou.demo.util.Constant;
import com.jiantou.demo.util.R;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统菜单
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "导航菜单")
    @RequestMapping(value = "/nav", method = RequestMethod.GET)
    public R nav(){
        List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId());
        return R.ok().put("menuList", menuList);
    }

    @ApiOperation(value = "所有菜单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("sys:menu:list")
    public List<SysMenu> list(){
        List<SysMenu> menuList = sysMenuService.list();
        for(SysMenu sysMenu : menuList){
            SysMenu parentMenu = sysMenuService.getById(sysMenu.getParentId());
            if(parentMenu != null){
                sysMenu.setParentName(parentMenu.getName());
            }
        }

        return menuList;
    }

    @ApiOperation(value = "选择菜单(添加、修改菜单)")
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @RequiresPermissions("sys:menu:select")
    public R select(){
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return R.ok().put("menuList", menuList);
    }

    @ApiOperation(value = "菜单信息")
    @RequestMapping(value = "/info/{menuId}", method = RequestMethod.GET)
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("menuId") Long menuId){
        SysMenu menu = sysMenuService.getById(menuId);
        return R.ok().put("menu", menu);
    }

    @ApiOperation(value = "保存菜单")
    @SysLog("保存菜单")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);

        sysMenuService.save(menu);

        return R.ok();
    }


    @ApiOperation(value = "修改菜单")
    @SysLog("修改菜单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);

        return R.ok();
    }

    @ApiOperation(value = "删除菜单")
    @SysLog("删除菜单")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("sys:menu:delete")
    public R delete(long menuId){
        if(menuId <= 31){
            return R.error("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
        if(menuList.size() > 0){
            return R.error("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);

        return R.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        if(StringUtils.isBlank(menu.getName())){
            throw new RRException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new RRException("上级菜单不能为空");
        }

        //菜单
        if(menu.getType() == Constant.MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getUrl())){
                throw new RRException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if(menu.getParentId() != 0){
            SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()){
            if(parentType != Constant.MenuType.CATALOG.getValue()){
                throw new RRException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
            if(parentType != Constant.MenuType.MENU.getValue()){
                throw new RRException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }
}
