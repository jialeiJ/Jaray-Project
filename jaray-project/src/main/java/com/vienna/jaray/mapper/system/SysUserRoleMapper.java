package com.vienna.jaray.mapper.system;

import com.vienna.jaray.entity.system.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统用户角色Mapper
 */
@Mapper
@Repository
public interface SysUserRoleMapper {
    /**
     * 查询用户角色列表
     * @return
     */
    public List<SysUserRole> findAll();

    /**
     * 添加用户角色
     * @param sysUserRoleEntity 用户角色对象
     * @return
     */
    public SysUserRole add(@Param("entity") SysUserRole sysUserRoleEntity);

    /**
     * 查询用户角色
     * @param userId 用户id
     * @return 用户角色
     */
    public SysUserRole findByUserId(@Param("userId")String userId);

    /**
     * 删除用户角色
     * @param ids 用户id数组
     * @return 删除结果
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新用户角色
     * @param id 用户角色id
     * @return 更新结果
     */
    public SysUserRole updateById(@Param("id")String id);
}
