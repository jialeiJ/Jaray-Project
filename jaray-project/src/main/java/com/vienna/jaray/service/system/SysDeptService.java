package com.vienna.jaray.service.system;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysDept;
import com.vienna.jaray.model.system.CommonParamsModel;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统部门接口类
 */
public interface SysDeptService {
    /**
     * 查询部门列表
     * @param commonParams 表格数据通用参数对象
     * @return 部门列表
     */
    ResponseResult findAllTop(CommonParamsModel commonParams);

    /**
     * 查询部门
     * @param id id
     * @return 部门信息
     */
    ResponseResult findById(String id);

    /**
     * 查询部门
     * @param name 名称
     * @return 部门信息
     */
    ResponseResult findByName(String name);

    /**
     * 添加部门
     * @param sysDept 部门对象
     * @return 添加结果
     */
    ResponseResult add(SysDept sysDept);

    /**
     * 删除部门
     * @param ids id数组
     * @return 删除结果
     */
    ResponseResult deleteByIds(String[] ids);

    /**
     * 更新部门
     * @param sysDept 部门对象
     * @return 更新信息
     */
    ResponseResult updateById(SysDept sysDept);
}
