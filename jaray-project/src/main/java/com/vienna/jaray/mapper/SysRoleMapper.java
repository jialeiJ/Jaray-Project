package com.vienna.jaray.mapper;

import com.vienna.jaray.entity.SysRoleEntity;
import com.vienna.jaray.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysRoleMapper {
    /**
     * 查询角色列表
     * @return
     */
    public List<SysRoleEntity> findAll();

    /**
     * 查询角色
     * @param id
     * @return
     */
    public SysRoleEntity findById(@Param("id")String id);

    /**
     * 查询角色
     * @param name
     * @return
     */
    public SysRoleEntity findByName(@Param("name")String name);

    /**
     * 添加角色
     * @param sysRoleEntity
     * @return
     */
    public int add(@Param("entity")SysRoleEntity sysRoleEntity);

    /**
     * 删除角色
     * @param ids
     * @return
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新角色
     * @param sysRoleEntity
     * @return
     */
    public int updateById(@Param("entity")SysRoleEntity sysRoleEntity);
}
