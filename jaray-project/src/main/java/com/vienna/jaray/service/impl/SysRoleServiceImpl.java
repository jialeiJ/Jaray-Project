package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysRoleEntity;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.mapper.SysRoleMapper;
import com.vienna.jaray.mapper.SysUserMapper;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.security.PasswordEncoderImpl;
import com.vienna.jaray.service.SysRoleService;
import com.vienna.jaray.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysRoleEntity> sysRoleEntityList = sysRoleMapper.findAll();
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(sysRoleEntityList);
        return ResponseResult.success().add("sysRoles", pageInfo);
    }

    @Override
    public ResponseResult findById(String id) {
        SysRoleEntity sysRoleEntity = sysRoleMapper.findById(id);
        return ResponseResult.success().add("sysRole", sysRoleEntity);
    }

    @Override
    public ResponseResult findByName(String name) {
        SysRoleEntity sysRoleEntity = sysRoleMapper.findByName(name);
        return ResponseResult.success().add("sysRole", sysRoleEntity);
    }

    @Override
    public ResponseResult add(SysRoleEntity sysRoleEntity) {
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
    public ResponseResult updateById(SysRoleEntity sysRoleEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRoleMapper.updateById(sysRoleEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }
}