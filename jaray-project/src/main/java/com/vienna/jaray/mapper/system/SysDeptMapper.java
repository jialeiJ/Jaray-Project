package com.vienna.jaray.mapper.system;

import com.vienna.jaray.entity.system.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysDeptMapper {
    /**
     * 查询顶级部门列表
     * @return
     */
    public List<SysDept> findAllTop();

    /**
     * 查询子部门列表
     * @return
     */
    public List<SysDept> findAllSub();

    /**
     * 查询部门
     * @param id
     * @return
     */
    public SysDept findById(@Param("id")String id);

    /**
     * 查询部门
     * @param  idList id集合
     * @return
     */
    public List<SysDept> findByIds(@Param("idList")List<String> idList);

    /**
     * 查询部门
     * @param name
     * @return
     */
    public SysDept findByName(@Param("name")String name);

    /**
     * 添加部门
     * @param sysDeptEntity
     * @return
     */
    public int add(@Param("entity") SysDept sysDeptEntity);

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
    public int updateById(@Param("entity") SysDept sysDeptEntity);
}
