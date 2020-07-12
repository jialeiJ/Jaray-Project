package com.vienna.jaray.mapper;

import com.vienna.jaray.entity.SysRoleEntity;
import com.vienna.jaray.entity.SysRolePermEntity;
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
    public List<SysRolePermEntity> findAll();

    /**
     * 查询角色权限
     * @param role_id
     * @return
     */
    public SysRolePermEntity findByRid(@Param("role_id")String role_id);

    /**
     * 添加角色权限
     * @param sysRolePermEntity
     * @return
     */
    public int add(@Param("entity")SysRolePermEntity sysRolePermEntity);

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
    public int updateByRid(@Param("entity")SysRolePermEntity sysRolePermEntity);
}
