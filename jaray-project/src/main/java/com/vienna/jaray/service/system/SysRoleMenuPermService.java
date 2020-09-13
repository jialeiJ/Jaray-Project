package com.vienna.jaray.service.system;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRoleMenu;
import com.vienna.jaray.model.system.CommonParamsModel;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统角色菜单权限接口类
 */
public interface SysRoleMenuPermService {
    /**
     * 查询角色菜单权限列表
     * @param commonParams 表格数据通用参数对象
     * @return 角色菜单权限列表
     */
    ResponseResult findAll(CommonParamsModel commonParams);

    /**
     * 查询角色菜单权限
     * @param roleId
     * @return
     */
    ResponseResult findByRid(String roleId);

    /**
     * 添加角色菜单权限
     * @param sysRoleMenu 角色菜单对象
     * @return 添加结果
     */
    ResponseResult add(SysRoleMenu sysRoleMenu);

    /**
     * 删除角色菜单权限
     * @param ids 角色id数组
     * @return 删除结果
     */
    ResponseResult deleteByIds(String[] ids);

    /**
     * 更新角色菜单权限
     * @param roleId 角色id
     * @param menuPermId 菜单权限id
     * @return 更新结果
     */
    ResponseResult updateByRid(String roleId, String menuPermId);
}
