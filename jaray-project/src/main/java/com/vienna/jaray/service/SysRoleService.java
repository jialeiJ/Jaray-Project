package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRole;
import com.vienna.jaray.model.system.CommonParamsModel;

public interface SysRoleService {

    /**
     * 查询角色列表
     * @return
     */
    public ResponseResult findAll(CommonParamsModel commonParamsModel);

    /**
     * 查询角色
     * @param id
     * @return
     */
    public ResponseResult findById(String id);

    /**
     * 查询角色
     * @param name
     * @return
     */
    public ResponseResult findByName(String name);

    /**
     * 添加角色
     * @param sysRoleEntity
     * @return
     */
    public ResponseResult add(SysRole sysRoleEntity);

    /**
     * 删除角色
     * @param ids
     * @return
     */
    public ResponseResult deleteByIds(String[] ids);

    /**
     * 更新角色
     * @param sysRoleEntity
     * @return
     */
    public ResponseResult updateById(SysRole sysRoleEntity);
}
