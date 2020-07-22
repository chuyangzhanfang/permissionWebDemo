package com.jiantou.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiantou.demo.model.SysLogEntity;
import com.jiantou.demo.util.PageUtils;

import java.util.Map;

public interface SysLogService extends IService<SysLogEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
