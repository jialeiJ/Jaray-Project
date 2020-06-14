package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;

public interface SysMenuService {
    /**
     * 查询左侧菜单信息
     * @return
     */
    public ResponseResult findLeftNav();
}
