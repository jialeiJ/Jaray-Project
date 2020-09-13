package com.vienna.jaray.service.system;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRolePerm;
import com.vienna.jaray.model.system.CommonParamsModel;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统角色权限接口类
 */
public interface SysRolePermService {
    /**
     * 查询角色权限列表
     * @param commonParams 表格数据通用参数对象
     * @return 角色权限列表
     */
    ResponseResult findAll(CommonParamsModel commonParams);

    /**
     * 查询角色权限
     * @param roleId 角色id
     * @return 角色权限信息
     */
    ResponseResult findByRid(String roleId);

    /**
     * 添加角色权限
     * @param sysRolePerm 角色权限对象
     * @return 添加结果
     */
    ResponseResult add(SysRolePerm sysRolePerm);

    /**
     * 删除角色权限
     * @param ids id数组
     * @return 删除结果
     */
    ResponseResult deleteByIds(String[] ids);

    /**
     * 更新角色权限
     * @param sysRolePerm 角色权限对象
     * @return 更新结果
     */
    ResponseResult updateByRid(SysRolePerm sysRolePerm);
}
