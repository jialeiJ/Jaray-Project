package com.vienna.jaray.mapper;

import com.vienna.jaray.entity.system.SysLog;
import com.vienna.jaray.model.system.CommonParamsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysLogMapper {
    /**
     * 查询日志列表
     * @return
     */
    public List<SysLog> findAll(@Param("common")CommonParamsModel commonParamsModel);

    /**
     * 查询日志
     * @param id
     * @return
     */
    public SysLog findById(@Param("id")String id);

    /**
     * 查询日志
     * @param user_name
     * @return
     */
    public SysLog findByName(@Param("user_name")String user_name);

    /**
     * 添加日志
     * @param sysLogEntity
     * @return
     */
    public int add(@Param("entity") SysLog sysLogEntity);

    /**
     * 删除日志
     * @param ids
     * @return
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新日志
     * @param sysLogEntity
     * @return
     */
    public int updateById(@Param("entity") SysLog sysLogEntity);
}
