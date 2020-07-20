package com.jiantou.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiantou.demo.mapper.SysDeptMapper;
import com.jiantou.demo.model.SysDept;
import com.jiantou.demo.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Override
    public List<SysDept> queryList(Map<String, Object> params){
        return baseMapper.queryList(params);
    }

    @Override
    public List<Long> queryDetpIdList(Long parentId) {
        return baseMapper.queryDetpIdList(parentId);
    }

    @Override
    public List<Long> getSubDeptIdList(Long deptId){
        //部门及子部门ID列表
        List<Long> deptIdList = new ArrayList<>();

        //获取子部门ID
        List<Long> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        return deptIdList;
    }

    /**
     * 递归
     */
    private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
        for(Long deptId : subIdList){
            List<Long> list = queryDetpIdList(deptId);
            if(list.size() > 0){
                getDeptTreeList(list, deptIdList);
            }
            deptIdList.add(deptId);
        }
    }
}
