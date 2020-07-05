package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysDictEntity;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.model.CommonParamsModel;

public interface SysDictService {

    /**
     * 查询字典列表
     * @return
     */
    public ResponseResult findAll(CommonParamsModel commonParamsModel);

    /**
     * 查询字典
     * @param id
     * @return
     */
    public ResponseResult findById(String id);

    /**
     * 查询字典
     * @param name
     * @return
     */
    public ResponseResult findByName(String name);

    /**
     * 添加字典
     * @param sysDictEntity
     * @return
     */
    public ResponseResult add(SysDictEntity sysDictEntity);

    /**
     * 删除字典
     * @param ids
     * @return
     */
    public ResponseResult deleteByIds(String[] ids);

    /**
     * 更新字典
     * @param sysDictEntity
     * @return
     */
    public ResponseResult updateById(SysDictEntity sysDictEntity);

    /**
     * 查询字典
     * @param description 描述
     * @return
     */
    public ResponseResult findByDesc(String description);
}
