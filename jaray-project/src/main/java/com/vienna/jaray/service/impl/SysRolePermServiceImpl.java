package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRolePerm;
import com.vienna.jaray.mapper.SysRolePermMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.SysRolePermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRolePermServiceImpl implements SysRolePermService {
    @Autowired
    private SysRolePermMapper sysRolePermMapper;

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysRolePerm> sysRolePermList = sysRolePermMapper.findAll();
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(sysRolePermList);
        return ResponseResult.success().add("sysRolePermList", pageInfo);
    }

    @Override
    public ResponseResult findByRid(String role_id) {
        SysRolePerm sysRolePerm = sysRolePermMapper.findByRid(role_id);
        return ResponseResult.success().add("sysRolePerm", sysRolePerm);
    }

    @Override
    public ResponseResult add(SysRolePerm sysRolePermEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRolePermMapper.add(sysRolePermEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysRolePerm", sysRolePermEntity);
        }
        return responseResult;
    }

    @Override
    public ResponseResult deleteByIds(String[] ids) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRolePermMapper.deleteByIds(ids);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }

    @Override
    public ResponseResult updateByRid(SysRolePerm sysRolePermEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRolePermMapper.updateByRid(sysRolePermEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }
}