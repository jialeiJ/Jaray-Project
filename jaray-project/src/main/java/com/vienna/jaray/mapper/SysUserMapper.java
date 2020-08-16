package com.vienna.jaray.mapper;

import com.vienna.jaray.entity.system.SysUser;
import com.vienna.jaray.model.system.CommonParamsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysUserMapper {
    /**
     * 查询用户列表
     * @return
     */
    public List<SysUser> findAll(@Param("common")CommonParamsModel commonParamsModel);

    /**
     * 查询用户
     * @param id
     * @return
     */
    public SysUser findById(@Param("id")String id);

    /**
     * 查询用户
     * @param name
     * @return
     */
    public SysUser findByName(@Param("name")String name);

    /**
     * 添加用户
     * @param sysUserEntity
     * @return
     */
    public int add(@Param("entity") SysUser sysUserEntity);

    /**
     * 删除用户
     * @param ids
     * @return
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新用户
     * @param sysUserEntity
     * @return
     */
    public int updateById(@Param("entity") SysUser sysUserEntity);
}
