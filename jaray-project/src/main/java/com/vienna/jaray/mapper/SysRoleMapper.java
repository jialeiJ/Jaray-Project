package com.vienna.jaray.mapper;

import com.vienna.jaray.entity.SysRole;
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
    public List<SysRole> findAll();

    /**
     * 查询角色
     * @param id
     * @return
     */
    public SysRole findById(@Param("id")String id);

    /**
     * 查询角色
     * @param idList id集合
     * @return
     */
    public List<SysRole> findByIds(@Param("idList")List<String> idList);

    /**
     * 查询角色
     * @param name
     * @return
     */
    public SysRole findByName(@Param("name")String name);

    /**
     * 添加角色
     * @param sysRoleEntity
     * @return
     */
    public int add(@Param("entity") SysRole sysRoleEntity);

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
    public int updateById(@Param("entity") SysRole sysRoleEntity);
}
