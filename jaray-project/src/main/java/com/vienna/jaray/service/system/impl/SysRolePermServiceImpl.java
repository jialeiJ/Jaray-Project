package com.vienna.jaray.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRolePerm;
import com.vienna.jaray.mapper.system.SysRolePermMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.system.SysRolePermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月12日 13:58
 * @description: 系统角色权限服务实现类
 */
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
    public ResponseResult findByRid(String roleId) {
        SysRolePerm sysRolePerm = sysRolePermMapper.findByRid(roleId);
        return ResponseResult.success().add("sysRolePerm", sysRolePerm);
    }

    @Override
    public ResponseResult add(SysRolePerm sysRolePerm) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRolePermMapper.add(sysRolePerm);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysRolePerm", sysRolePerm);
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
    public ResponseResult updateByRid(SysRolePerm sysRolePerm) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRolePermMapper.updateByRid(sysRolePerm);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }
}