package com.vienna.jaray.service.system;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysDict;
import com.vienna.jaray.model.system.CommonParamsModel;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统字典接口类
 */
public interface SysDictService {
    /**
     * 查询字典列表
     * @param commonParams 表格数据通用参数对象
     * @return 字典列表
     */
    ResponseResult findAll(CommonParamsModel commonParams);

    /**
     * 查询字典
     * @param id id
     * @return 字典信息
     */
    ResponseResult findById(String id);

    /**
     * 查询字典
     * @param name 名称
     * @return 字典信息
     */
    ResponseResult findByName(String name);

    /**
     * 添加字典
     * @param sysDict 字典对象
     * @return 添加结果
     */
    ResponseResult add(SysDict sysDict);

    /**
     * 删除字典
     * @param ids id数组
     * @return 删除结果
     */
    ResponseResult deleteByIds(String[] ids);

    /**
     * 更新字典
     * @param sysDict 字典对象
     * @return 更新结果
     */
    ResponseResult updateById(SysDict sysDict);

    /**
     * 查询字典
     * @param description 描述
     * @return 字典信息
     */
    ResponseResult findByDesc(String description);
}
