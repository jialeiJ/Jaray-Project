package com.vienna.jaray.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vienna.jaray.entity.SysMenuEntity;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysMenuMapper {
	
	public List<SysMenuEntity> findFirstLevelMenu();
	
	public List<SysMenuEntity> findNextSubSetMenu(@Param("parentId") int id);
	
}
