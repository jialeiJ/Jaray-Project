package com.vienna.jaray.mapper.system;

import com.vienna.jaray.entity.system.SysRolePerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * @param role_id
     * @return
     */
    public SysRolePerm findByRid(@Param("role_id")String role_id);

    /**
     * 查询角色权限
     * @param role_ids
     * @return
     */
    public List<SysRolePerm> findByRids(@Param("role_ids")String[] role_ids);

    /**
     * 添加角色权限
     * @param sysRolePermEntity
     * @return
     */
    public int add(@Param("entity") SysRolePerm sysRolePermEntity);

    /**
     * 删除角色权限
     * @param ids
     * @return
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新角色权限
     * @param sysRolePermEntity
     * @return
     */
    public int updateByRid(@Param("entity") SysRolePerm sysRolePermEntity);
}
