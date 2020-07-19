package com.vienna.jaray.mapper;

import com.vienna.jaray.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysRoleMenuMapper {
    /**
     * 查询角色菜单
     * @return
     */
    public List<SysRoleMenu> findAll();

    /**
     * 查询角色菜单
     * @param role_id
     * @return
     */
    public SysRoleMenu findByRid(@Param("role_id")String role_id);

    /**
     * 查询角色菜单
     * @param role_ids
     * @return
     */
    public List<SysRoleMenu> findByRids(@Param("role_ids")String[] role_ids);

    /**
     * 添加角色菜单
     * @param sysRoleMenuEntity
     * @return
     */
    public int add(@Param("entity") SysRoleMenu sysRoleMenuEntity);

    /**
     * 删除角色菜单
     * @param ids
     * @return
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新角色菜单
     * @param sysRoleMenuEntity
     * @return
     */
    public int updateByRid(@Param("entity") SysRoleMenu sysRoleMenuEntity);
}
