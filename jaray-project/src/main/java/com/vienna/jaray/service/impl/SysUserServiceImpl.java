package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysDeptEntity;
import com.vienna.jaray.entity.SysRoleEntity;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.mapper.SysDeptMapper;
import com.vienna.jaray.mapper.SysRoleMapper;
import com.vienna.jaray.mapper.SysUserMapper;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.security.PasswordEncoderImpl;
import com.vienna.jaray.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysUserEntity> sysUserEntityList = sysUserMapper.findAll();

        //
        List<String> deptIdList = sysUserEntityList.stream().map(SysUserEntity::getDept_id).collect(Collectors.toList());
        List<SysDeptEntity> sysDeptList = sysDeptMapper.findByIds(deptIdList);

        List<String> roleIdList = sysUserEntityList.stream().map(SysUserEntity::getRole_id).collect(Collectors.toList());
        List<SysRoleEntity> sysRoleList = sysRoleMapper.findByIds(roleIdList);

        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(sysUserEntityList);
        return ResponseResult.success().add("sysUsers", pageInfo).add("sysDeptList", sysDeptList).add("sysRoleList", sysRoleList);
    }

    @Override
    public ResponseResult findById(String id) {
        SysUserEntity sysUserEntity = sysUserMapper.findById(id);
        return ResponseResult.success().add("sysUser", sysUserEntity);
    }

    @Override
    public ResponseResult findByName(String name) {
        SysUserEntity sysUserEntity = sysUserMapper.findByName(name);
        return ResponseResult.success().add("sysUser", sysUserEntity);
    }

    @Override
    public ResponseResult add(SysUserEntity sysUserEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        sysUserEntity.setPassword(passwordEncoder.encode("000000"));
        int result = sysUserMapper.add(sysUserEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysUser", sysUserEntity);
        }
        return responseResult;
    }

    @Override
    public ResponseResult deleteByIds(String[] ids) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysUserMapper.deleteByIds(ids);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }

    @Override
    public ResponseResult updateById(SysUserEntity sysUserEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysUserMapper.updateById(sysUserEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }
}