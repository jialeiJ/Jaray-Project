package com.vienna.jaray.service.system;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysLog;
import com.vienna.jaray.model.system.CommonParamsModel;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统日志接口类
 */
public interface SysLogService {
    /**
     * 查询日志列表
     * @param commonParams 表格数据通用参数对象
     * @return 日志列表
     */
    ResponseResult findAll(CommonParamsModel commonParams);

    /**
     * 查询日志
     * @param id id
     * @return 日志信息
     */
    ResponseResult findById(String id);

    /**
     * 查询日志
     * @param userName 用户名
     * @return 日志信息
     */
    ResponseResult findByName(String userName);

    /**
     * 添加日志
     * @param sysLog 日志对象
     * @return 添加结果
     */
    ResponseResult add(SysLog sysLog);

    /**
     * 删除日志
     * @param ids id数组
     * @return 删除结果
     */
    ResponseResult deleteByIds(String[] ids);

    /**
     * 更新日志
     * @param sysLog 日志对象
     * @return 更新结果
     */
    ResponseResult updateById(SysLog sysLog);
}
