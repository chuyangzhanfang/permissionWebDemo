package com.jiantou.demo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @TableId
    private Long deptId;

    private Long parentId;

    private String name;

    private Integer orderNum;

    @TableLogic
    private Byte delFlag;

    /**
     * ztree属性
     */
    @TableField(exist=false)
    private Boolean open;
}