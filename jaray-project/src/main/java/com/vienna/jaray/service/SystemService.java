package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResultMsg;

public interface SystemService {
    /**
     * 查询左侧菜单信息
     * @return
     */
    public ResponseResultMsg findLeftNav();
}
