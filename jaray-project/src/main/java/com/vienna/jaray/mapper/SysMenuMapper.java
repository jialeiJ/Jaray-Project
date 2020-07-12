package com.vienna.jaray.mapper;

import java.util.List;

import com.vienna.jaray.model.SelectOptionsModel;
import com.vienna.jaray.model.TreeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vienna.jaray.entity.SysMenuEntity;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysMenuMapper {

	/**
	 * 查询菜单列表
	 * @return
	 */
	public List<SysMenuEntity> findAll();

	/**
	 * 查询菜单按钮列表
	 * @return
	 */
	public List<SysMenuEntity> findBtnAll();

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

	/**
	 * 查询菜单
	 * @param ids 菜单id数组
	 * @return
	 */
	public List<SysMenuEntity> findByIds(@Param("ids")String[] ids);

	/**
	 * 查询所有目录菜单
	 * @return
	 */
	public List<SelectOptionsModel> findAllDir();

	/**
	 * 查询菜单
	 * @param menu_perms 用户菜单权限
	 * @return
	 */
	public List<SysMenuEntity> findByPerm(@Param("menu_perms")String[] menu_perms);
	
}
