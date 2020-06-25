package com.vienna.jaray.mapper;

import com.vienna.jaray.entity.SysDictEntity;
import com.vienna.jaray.entity.SysLogEntity;
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
    public List<SysLogEntity> findAll();

    /**
     * 查询日志
     * @param id
     * @return
     */
    public SysLogEntity findById(@Param("id")String id);

    /**
     * 查询日志
     * @param user_name
     * @return
     */
    public SysLogEntity findByName(@Param("user_name")String user_name);

    /**
     * 添加日志
     * @param sysLogEntity
     * @return
     */
    public int add(@Param("entity")SysLogEntity sysLogEntity);

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
    public int updateById(@Param("entity")SysLogEntity sysLogEntity);
}
