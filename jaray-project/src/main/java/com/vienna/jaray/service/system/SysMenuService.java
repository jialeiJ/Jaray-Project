package com.vienna.jaray.service.system;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysMenu;
import com.vienna.jaray.model.system.CommonParamsModel;
import org.apache.ibatis.annotations.Param;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统菜单接口类
 */
public interface SysMenuService {
    /**
     * 查询左侧菜单信息
     * @param userId 用户id
     * @return 左侧菜单信息
     */
    ResponseResult findLeftNav(String userId);

    /**
     * 查询菜单Tree列表
     * @param commonParams 表格数据通用参数对象
     * @return 菜单Tree列表
     */
    ResponseResult findTreeAll(CommonParamsModel commonParams);

    /**
     * 查询菜单平铺列表
     * @param commonParams 表格数据通用参数对象
     * @return 菜单平铺列表
     */
    ResponseResult findAll(CommonParamsModel commonParams);

    /**
     * 添加菜单
     * @param sysMenu 菜单对象
     * @return 添加结果
     */
    ResponseResult add(@Param("entity") SysMenu sysMenu);

    /**
     * 删除菜单
     * @param ids 菜单ids
     * @return 删除结果
     */
    ResponseResult deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新菜单
     * @param sysMenu 菜单对象
     * @return 更新结果
     */
    ResponseResult updateById(@Param("entity") SysMenu sysMenu);

    /**
     * 查询菜单
     * @param id 菜单id
     * @return 菜单信息
     */
    ResponseResult findById(@Param("id")String id);
}
