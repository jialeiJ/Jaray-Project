package com.vienna.jaray.mapper;

import com.vienna.jaray.entity.SysDictEntity;
import com.vienna.jaray.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysDictMapper {
    /**
     * 查询字典列表
     * @return
     */
    public List<SysDictEntity> findAll();

    /**
     * 查询字典
     * @param id
     * @return
     */
    public SysDictEntity findById(@Param("id")String id);

    /**
     * 查询字典
     * @param name
     * @return
     */
    public SysDictEntity findByName(@Param("name")String name);

    /**
     * 添加字典
     * @param sysDictEntity
     * @return
     */
    public int add(@Param("entity")SysDictEntity sysDictEntity);

    /**
     * 删除字典
     * @param ids
     * @return
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新字典
     * @param sysDictEntity
     * @return
     */
    public int updateById(@Param("entity")SysDictEntity sysDictEntity);
}
