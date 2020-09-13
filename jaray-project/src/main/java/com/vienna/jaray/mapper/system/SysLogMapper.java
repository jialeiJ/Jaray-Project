package com.vienna.jaray.mapper.system;

import com.vienna.jaray.entity.system.SysLog;
import com.vienna.jaray.model.system.CommonParamsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统日志Mapper类
 */
@Mapper
@Repository
public interface SysLogMapper {
    /**
     * 查询日志列表
     * @param commonParams 表格数据通用参数对象
     * @return 日志列表
     */
    public List<SysLog> findAll(@Param("common")CommonParamsModel commonParams);

    /**
     * 查询日志
     * @param id id
     * @return 日志信息
     */
    public SysLog findById(@Param("id")String id);

    /**
     * 查询日志
     * @param userName 用户名
     * @return 日志信息
     */
    public SysLog findByName(@Param("userName")String userName);

    /**
     * 添加日志
     * @param sysLog 日志对象
     * @return 添加结果
     */
    public int add(@Param("entity") SysLog sysLog);

    /**
     * 删除日志
     * @param ids id数组
     * @return 删除结果
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新日志
     * @param sysLog 日志对象
     * @return 更新结果
     */
    public int updateById(@Param("entity") SysLog sysLog);
}
