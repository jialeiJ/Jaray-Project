package com.vienna.jaray.mapper.system;

import com.vienna.jaray.entity.system.SysDict;
import com.vienna.jaray.model.system.SelectOptionsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统字典Mapper类
 */
@Mapper
@Repository
public interface SysDictMapper {
    /**
     * 查询字典列表
     * @return
     */
    public List<SysDict> findAll();

    /**
     * 查询字典
     * @param id
     * @return
     */
    public SysDict findById(@Param("id")String id);

    /**
     * 查询字典
     * @param name
     * @return
     */
    public SysDict findByName(@Param("name")String name);

    /**
     * 添加字典
     * @param sysDictEntity
     * @return
     */
    public int add(@Param("entity") SysDict sysDictEntity);

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
    public int updateById(@Param("entity") SysDict sysDictEntity);

    /**
     * 查询字典
     * @param description 描述
     * @return
     */
    public List<SelectOptionsModel> findByDesc(@Param("description")String description);
}
