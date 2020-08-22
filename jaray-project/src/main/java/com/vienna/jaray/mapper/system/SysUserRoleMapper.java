package com.vienna.jaray.mapper.system;

import com.vienna.jaray.entity.system.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * @param user_id 用户id
     * @return
     */
    public SysUserRole findByUserId(@Param("user_id")String user_id);

    /**
     * 删除用户角色
     * @param ids
     * @return
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新用户角色 用户角色id
     * @param id
     * @return
     */
    public SysUserRole updateById(@Param("id")String id);
}
