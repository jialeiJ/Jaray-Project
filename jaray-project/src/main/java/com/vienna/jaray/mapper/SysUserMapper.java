package com.vienna.jaray.mapper;

import com.vienna.jaray.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserMapper {
    public SysUserEntity findSysUser(@Param("name")String name);
}
