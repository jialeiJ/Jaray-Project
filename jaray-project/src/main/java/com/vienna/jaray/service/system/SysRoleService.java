package com.vienna.jaray.service.system;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRole;
import com.vienna.jaray.model.system.CommonParamsModel;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统角色接口类
 */
public interface SysRoleService {
    /**
     * 查询角色列表
     * @param commonParams 表格数据通用参数对象
     * @return 角色列表
     */
    ResponseResult findAll(CommonParamsModel commonParams);

    /**
     * 查询角色
     * @param id
     * @return
     */
    ResponseResult findById(String id);

    /**
     * 查询角色
     * @param name
     * @return
     */
    ResponseResult findByName(String name);

    /**
     * 添加角色
     * @param sysRoleEntity
     * @return
     */
    ResponseResult add(SysRole sysRoleEntity);

    /**
     * 删除角色
     * @param ids
     * @return
     */
    ResponseResult deleteByIds(String[] ids);

    /**
     * 更新角色
     * @param sysRoleEntity
     * @return
     */
    ResponseResult updateById(SysRole sysRoleEntity);
}
