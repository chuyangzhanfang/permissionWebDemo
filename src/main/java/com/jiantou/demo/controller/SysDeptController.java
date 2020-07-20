package com.jiantou.demo.controller;

import com.jiantou.demo.model.SysDept;
import com.jiantou.demo.service.SysDeptService;
import com.jiantou.demo.util.Constant;
import com.jiantou.demo.util.R;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 部门管理
 *
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
    @Autowired
    private SysDeptService sysDeptService;

    @ApiOperation(value = "部门列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("sys:dept:list")
    public List<SysDept> list(){
        List<SysDept> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        return deptList;
    }

    @ApiOperation(value = "选择部门(添加、修改菜单)")
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @RequiresPermissions("sys:dept:select")
    public R select(){
        List<SysDept> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        //添加一级部门
        if(getUserId() == Constant.SUPER_ADMIN){
            SysDept root = new SysDept();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }

        return R.ok().put("deptList", deptList);
    }

    @ApiOperation(value = "上级部门Id(管理员则为0)")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @RequiresPermissions("sys:dept:list")
    public R info(){
        long deptId = 0;
        if(getUserId() != Constant.SUPER_ADMIN){
            List<SysDept> deptList = sysDeptService.queryList(new HashMap<String, Object>());
            Long parentId = null;
            for(SysDept sysDept : deptList){
                if(parentId == null){
                    parentId = sysDept.getParentId();
                    continue;
                }

                if(parentId > sysDept.getParentId().longValue()){
                    parentId = sysDept.getParentId();
                }
            }
            deptId = parentId;
        }
        return R.ok().put("deptId", deptId);
    }

}
