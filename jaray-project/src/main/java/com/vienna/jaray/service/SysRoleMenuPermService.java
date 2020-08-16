package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRoleMenu;
import com.vienna.jaray.model.system.CommonParamsModel;

public interface SysRoleMenuPermService {

    /**
     * 查询角色菜单权限列表
     * @return
     */
    public ResponseResult findAll(CommonParamsModel commonParamsModel);

    /**
     * 查询角色菜单权限
     * @param role_id
     * @return
     */
    public ResponseResult findByRid(String role_id);

    /**
     * 添加角色菜单权限
     * @param sysRoleMenuEntity
     * @return
     */
    public ResponseResult add(SysRoleMenu sysRoleMenuEntity);

    /**
     * 删除角色菜单权限
     * @param ids
     * @return
     */
    public ResponseResult deleteByIds(String[] ids);

    /**
     * 更新角色菜单权限
     * @param role_id
     * @param menu_perm_id
     * @return
     */
    public ResponseResult updateByRid(String role_id, String menu_perm_id);
}
