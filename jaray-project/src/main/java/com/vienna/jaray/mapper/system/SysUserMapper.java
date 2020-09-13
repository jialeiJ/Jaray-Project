package com.vienna.jaray.mapper.system;

import com.vienna.jaray.entity.system.SysUser;
import com.vienna.jaray.model.system.CommonParamsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @Description: 系统用户Mapper
 */
@Mapper
@Repository
public interface SysUserMapper {
    /**
     * 查询用户列表
     * @param commonParams 表格数据通用参数对象
     * @return 用户列表
     */
    List<SysUser> findAll(@Param("common")CommonParamsModel commonParams);

    /**
     * 查询用户
     * @param id id
     * @return 用户信息
     */
    SysUser findById(@Param("id")String id);

    /**
     * 查询用户
     * @param name 用户名
     * @return 用户信息
     */
    SysUser findByName(@Param("name")String name);

    /**
     * 添加用户
     * @param sysUser 用户对象
     * @return 添加结果
     */
    int add(@Param("entity") SysUser sysUser);

    /**
     * 删除用户
     * @param ids id数组
     * @return 删除结果
     */
    int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新用户
     * @param sysUser 用户对象
     * @return 更新结果
     */
    int updateById(@Param("entity") SysUser sysUser);
}
