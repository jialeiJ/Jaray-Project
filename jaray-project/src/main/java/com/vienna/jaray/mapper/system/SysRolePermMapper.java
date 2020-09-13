package com.vienna.jaray.mapper.system;

import com.vienna.jaray.entity.system.SysRolePerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统角色权限Mapper
 */
@Mapper
@Repository
public interface SysRolePermMapper {
    /**
     * 查询角色权限
     * @return
     */
    public List<SysRolePerm> findAll();

    /**
     * 查询角色权限
     * @param roleId 角色id
     * @return 角色权限
     */
    public SysRolePerm findByRid(@Param("roleId")String roleId);

    /**
     * 查询角色权限
     * @param roleIds 角色id数组
     * @return 角色权限
     */
    public List<SysRolePerm> findByRids(@Param("roleIds")String[] roleIds);

    /**
     * 添加角色权限
     * @param sysRolePerm 角色权限对象
     * @return 添加结果
     */
    public int add(@Param("entity") SysRolePerm sysRolePerm);

    /**
     * 删除角色权限
     * @param ids 角色id
     * @return 删除结果
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新角色权限
     * @param sysRolePerm 角色权限对象
     * @return 更新结果
     */
    public int updateByRid(@Param("entity") SysRolePerm sysRolePerm);
}
