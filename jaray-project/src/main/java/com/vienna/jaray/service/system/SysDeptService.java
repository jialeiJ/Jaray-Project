package com.vienna.jaray.service.system;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysDept;
import com.vienna.jaray.model.system.CommonParamsModel;

public interface SysDeptService {

    /**
     * 查询部门列表
     * @return
     */
    public ResponseResult findAllTop(CommonParamsModel commonParamsModel);

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
    public ResponseResult add(SysDept sysDeptEntity);

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
    public ResponseResult updateById(SysDept SysDeptEntity);
}
