package com.vienna.jaray.service.system;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysUser;
import com.vienna.jaray.model.system.CommonParamsModel;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统用户接口类
 */
public interface SysUserService {
    /**
     * 查询用户列表
     *
     * @param commonParams 表格数据通用参数对象
     * @return 用户列表
     */
    ResponseResult findAll(CommonParamsModel commonParams);

    /**
     * 查询用户
     * @param id id
     * @return 用户信息
     */
    ResponseResult findById(String id);

    /**
     * 查询用户
     * @param name 用户名
     * @return 用户信息
     */
    ResponseResult findByName(String name);

    /**
     * 添加用户
     * @param sysUser 系统用户对象
     * @return 添加结果
     */
    ResponseResult add(SysUser sysUser);

    /**
     * 删除用户
     * @param ids id数组
     * @return 删除结果
     */
    ResponseResult deleteByIds(String[] ids);

    /**
     * 更新用户
     * @param sysUser 系统用户对象
     * @return 更新结果
     */
    ResponseResult updateById(SysUser sysUser);
}
