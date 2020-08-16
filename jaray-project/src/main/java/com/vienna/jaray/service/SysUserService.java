package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysUser;
import com.vienna.jaray.model.system.CommonParamsModel;

public interface SysUserService {

    /**
     * 查询用户列表
     * @return
     */
    public ResponseResult findAll(CommonParamsModel commonParamsModel);

    /**
     * 查询用户
     * @param id
     * @return
     */
    public ResponseResult findById(String id);

    /**
     * 查询用户
     * @param name
     * @return
     */
    public ResponseResult findByName(String name);

    /**
     * 添加用户
     * @param sysUserEntity
     * @return
     */
    public ResponseResult add(SysUser sysUserEntity);

    /**
     * 删除用户
     * @param ids
     * @return
     */
    public ResponseResult deleteByIds(String[] ids);

    /**
     * 更新用户
     * @param sysUserEntity
     * @return
     */
    public ResponseResult updateById(SysUser sysUserEntity);
}
