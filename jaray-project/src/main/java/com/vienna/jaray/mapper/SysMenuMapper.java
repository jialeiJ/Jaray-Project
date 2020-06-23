package com.vienna.jaray.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vienna.jaray.entity.SysMenuEntity;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysMenuMapper {

	/**
	 * 查询一级菜单
	 * @return
	 */
	public List<SysMenuEntity> findFirstLevelMenu();

	/**
	 * 查询子菜单
	 * @param id 父菜单id
	 * @return
	 */
	public List<SysMenuEntity> findNextSubSetMenu(@Param("parentId")String id);

	/**
	 * 查询菜单列表
	 * @return
	 */
	public List<SysMenuEntity> findAll();

	/**
	 * 添加菜单
	 * @param sysMenuEntity 菜单对象
	 * @return
	 */
	public int add(@Param("entity")SysMenuEntity sysMenuEntity);

	/**
	 * 删除菜单
	 * @param ids 菜单ids
	 * @return
	 */
	public int deleteByIds(@Param("ids")String[] ids);

	/**
	 * 更新菜单
	 * @param sysMenuEntity 菜单对象
	 * @return
	 */
	public int updateById(@Param("entity")SysMenuEntity sysMenuEntity);

	/**
	 * 查询菜单
	 * @param id 菜单id
	 * @return
	 */
	public SysMenuEntity findById(@Param("id")String id);
	
}
