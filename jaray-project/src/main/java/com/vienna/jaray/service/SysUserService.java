package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysUserEntity;

public interface SysUserService {
    /**
     * 查询用户信息
     * @return
     */
    public SysUserEntity findSysUser(String name);
}
