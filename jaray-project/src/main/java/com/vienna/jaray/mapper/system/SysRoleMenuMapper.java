package com.vienna.jaray.mapper.system;

import com.vienna.jaray.entity.system.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统角色菜单Mapper类
 */
@Mapper
@Repository
public interface SysRoleMenuMapper {
    /**
     * 查询角色菜单
     * @return 角色菜单
     */
    public List<SysRoleMenu> findAll();

    /**
     * 查询角色菜单
     * @param roleId 角色id
     * @return 角色菜单
     */
    public SysRoleMenu findByRid(@Param("roleId")String roleId);

    /**
     * 查询角色菜单
     * @param roleIds 角色id数组
     * @return
     */
    public List<SysRoleMenu> findByRids(@Param("roleIds")String[] roleIds);

    /**
     * 添加角色菜单
     * @param sysRoleMenu 角色菜单对象
     * @return 添加结果
     */
    public int add(@Param("entity") SysRoleMenu sysRoleMenu);

    /**
     * 删除角色菜单
     * @param ids 角色id数组
     * @return
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新角色菜单
     * @param sysRoleMenu 角色菜单对象
     * @return 更新结果
     */
    public int updateByRid(@Param("entity") SysRoleMenu sysRoleMenu);
}
