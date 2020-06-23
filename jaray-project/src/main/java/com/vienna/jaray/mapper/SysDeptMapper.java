package com.vienna.jaray.mapper;

import com.vienna.jaray.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysDeptMapper {
    /**
     * 查询部门列表
     * @return
     */
    public List<SysDeptEntity> findAll();

    /**
     * 查询部门
     * @param id
     * @return
     */
    public SysDeptEntity findById(@Param("id")String id);

    /**
     * 查询部门
     * @param name
     * @return
     */
    public SysDeptEntity findByName(@Param("name")String name);

    /**
     * 添加部门
     * @param sysDeptEntity
     * @return
     */
    public int add(@Param("entity")SysDeptEntity sysDeptEntity);

    /**
     * 删除部门
     * @param ids
     * @return
     */
    public int deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新部门
     * @param sysDeptEntity
     * @return
     */
    public int updateById(@Param("entity")SysDeptEntity sysDeptEntity);
}
