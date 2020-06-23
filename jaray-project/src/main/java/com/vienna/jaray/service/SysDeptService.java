package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysDeptEntity;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.model.CommonParamsModel;

public interface SysDeptService {

    /**
     * 查询部门列表
     * @return
     */
    public ResponseResult findAll(CommonParamsModel commonParamsModel);

    /**
     * 查询部门
     * @param id
     * @return
     */
    public ResponseResult findById(String id);

    /**
     * 查询部门
     * @param name
     * @return
     */
    public ResponseResult findByName(String name);

    /**
     * 添加部门
     * @param sysDeptEntity
     * @return
     */
    public ResponseResult add(SysDeptEntity sysDeptEntity);

    /**
     * 删除部门
     * @param ids
     * @return
     */
    public ResponseResult deleteByIds(String[] ids);

    /**
     * 更新部门
     * @param SysDeptEntity
     * @return
     */
    public ResponseResult updateById(SysDeptEntity SysDeptEntity);
}
