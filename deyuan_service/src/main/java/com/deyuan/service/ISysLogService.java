package com.deyuan.service;

import com.deyuan.pojo.SysLog;

import java.util.List;

public interface ISysLogService {

    public void save(SysLog sysLog);

    List<SysLog> findAll();
}
