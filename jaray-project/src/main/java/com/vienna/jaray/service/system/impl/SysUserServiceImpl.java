package com.vienna.jaray.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysDept;
import com.vienna.jaray.entity.system.SysRole;
import com.vienna.jaray.entity.system.SysUser;
import com.vienna.jaray.mapper.system.SysDeptMapper;
import com.vienna.jaray.mapper.system.SysRoleMapper;
import com.vienna.jaray.mapper.system.SysUserMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.system.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jaray
 * @date 2020年09月12日 13:58
 * @description: 系统用户服务实现类
 */
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
        List<SysUser> sysUserEntityList = sysUserMapper.findAll(commonParamsModel);

        //
        List<String> deptIdList = sysUserEntityList.stream().map(SysUser::getDeptId).collect(Collectors.toList());
        List<SysDept> sysDeptList = sysDeptMapper.findByIds(deptIdList);

        List<String> roleIdList = sysUserEntityList.stream().map(SysUser::getRoleId).collect(Collectors.toList());
        List<SysRole> sysRoleList = sysRoleMapper.findByIds(roleIdList);

        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(sysUserEntityList);
        return ResponseResult.success().add("sysUsers", pageInfo).add("sysDeptList", sysDeptList).add("sysRoleList", sysRoleList);
    }

    @Override
    public ResponseResult findById(String id) {
        SysUser sysUserEntity = sysUserMapper.findById(id);
        return ResponseResult.success().add("sysUser", sysUserEntity);
    }

    @Override
    public ResponseResult findByName(String name) {
        SysUser sysUserEntity = sysUserMapper.findByName(name);
        return ResponseResult.success().add("sysUser", sysUserEntity);
    }

    @Override
    public ResponseResult add(SysUser sysUserEntity) {
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
    public ResponseResult updateById(SysUser sysUserEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysUserMapper.updateById(sysUserEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }
}