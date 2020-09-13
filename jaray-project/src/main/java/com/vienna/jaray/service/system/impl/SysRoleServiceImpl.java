package com.vienna.jaray.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRole;
import com.vienna.jaray.mapper.system.SysRoleMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.system.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月12日 13:58
 * @description: 系统角色服务实现类
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysRole> sysRoleEntityList = sysRoleMapper.findAll();
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(sysRoleEntityList);
        return ResponseResult.success().add("sysRoles", pageInfo);
    }

    @Override
    public ResponseResult findById(String id) {
        SysRole sysRoleEntity = sysRoleMapper.findById(id);
        return ResponseResult.success().add("sysRole", sysRoleEntity);
    }

    @Override
    public ResponseResult findByName(String name) {
        SysRole sysRoleEntity = sysRoleMapper.findByName(name);
        return ResponseResult.success().add("sysRole", sysRoleEntity);
    }

    @Override
    public ResponseResult add(SysRole sysRoleEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRoleMapper.add(sysRoleEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysRole", sysRoleEntity);
        }
        return responseResult;
    }

    @Override
    public ResponseResult deleteByIds(String[] ids) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRoleMapper.deleteByIds(ids);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }

    @Override
    public ResponseResult updateById(SysRole sysRoleEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRoleMapper.updateById(sysRoleEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }
}