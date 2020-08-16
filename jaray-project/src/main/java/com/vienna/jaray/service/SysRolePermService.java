package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRolePerm;
import com.vienna.jaray.model.system.CommonParamsModel;

public interface SysRolePermService {

    /**
     * 查询角色权限列表
     * @return
     */
    public ResponseResult findAll(CommonParamsModel commonParamsModel);

    /**
     * 查询角色权限
     * @param role_id
     * @return
     */
    public ResponseResult findByRid(String role_id);

    /**
     * 添加角色权限
     * @param sysRolePermEntity
     * @return
     */
    public ResponseResult add(SysRolePerm sysRolePermEntity);

    /**
     * 删除角色权限
     * @param ids
     * @return
     */
    public ResponseResult deleteByIds(String[] ids);

    /**
     * 更新角色权限
     * @param sysRolePermEntity
     * @return
     */
    public ResponseResult updateByRid(SysRolePerm sysRolePermEntity);
}