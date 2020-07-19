package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysLog;
import com.vienna.jaray.mapper.SysLogMapper;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysLog> sysLogEntityList = sysLogMapper.findAll();
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(sysLogEntityList);
        return ResponseResult.success().add("sysLogs", pageInfo);
    }

    @Override
    public ResponseResult findById(String id) {
        SysLog sysLogEntity = sysLogMapper.findById(id);
        return ResponseResult.success().add("sysLog", sysLogEntity);
    }

    @Override
    public ResponseResult findByName(String user_name) {
        SysLog sysLogEntity = sysLogMapper.findByName(user_name);
        return ResponseResult.success().add("sysLog", sysLogEntity);
    }

    @Override
    public ResponseResult add(SysLog sysLogEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysLogMapper.add(sysLogEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysLog", sysLogEntity);
        }
        return responseResult;
    }

    @Override
    public ResponseResult deleteByIds(String[] ids) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysLogMapper.deleteByIds(ids);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }

    @Override
    public ResponseResult updateById(SysLog sysLogEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysLogMapper.updateById(sysLogEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }
}