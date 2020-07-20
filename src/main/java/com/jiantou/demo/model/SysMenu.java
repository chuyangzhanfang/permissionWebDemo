package com.jiantou.demo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId
    private Long menuId;

    private Long parentId;

    /**
     * 父菜单名称
     */
    @TableField(exist=false)
    private String parentName;

    private String name;

    private String url;

    private String perms;

    private Integer type;

    private String icon;

    private Integer orderNum;

    /**
     * ztree属性
     */
    @TableField(exist=false)
    private Boolean open;

    @TableField(exist=false)
    private List<?> list;
}