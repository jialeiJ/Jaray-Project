package com.vienna.jaray.service.impl;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysMenuEntity;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.mapper.SysMenuMapper;
import com.vienna.jaray.mapper.SysUserMapper;
import com.vienna.jaray.service.SysMenuService;
import com.vienna.jaray.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUserEntity findSysUser(String name) {
        return sysUserMapper.findSysUser(name);
    }
}