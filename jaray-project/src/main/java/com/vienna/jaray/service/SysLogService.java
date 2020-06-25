package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysDictEntity;
import com.vienna.jaray.entity.SysLogEntity;
import com.vienna.jaray.model.CommonParamsModel;

public interface SysLogService {

    /**
     * 查询日志列表
     * @return
     */
    public ResponseResult findAll(CommonParamsModel commonParamsModel);

    /**
     * 查询日志
     * @param id
     * @return
     */
    public ResponseResult findById(String id);

    /**
     * 查询日志
     * @param user_name
     * @return
     */
    public ResponseResult findByName(String user_name);

    /**
     * 添加日志
     * @param sysLogEntity
     * @return
     */
    public ResponseResult add(SysLogEntity sysLogEntity);

    /**
     * 删除日志
     * @param ids
     * @return
     */
    public ResponseResult deleteByIds(String[] ids);

    /**
     * 更新日志
     * @param sysLogEntity
     * @return
     */
    public ResponseResult updateById(SysLogEntity sysLogEntity);
}
