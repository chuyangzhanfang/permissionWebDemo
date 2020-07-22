package com.jiantou.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiantou.demo.model.SysLogEntity;

import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 *
 * @author jialin
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {

}