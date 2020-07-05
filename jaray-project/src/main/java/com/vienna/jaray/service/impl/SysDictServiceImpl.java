package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysDictEntity;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.mapper.SysDictMapper;
import com.vienna.jaray.mapper.SysUserMapper;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.model.SelectOptionsModel;
import com.vienna.jaray.security.PasswordEncoderImpl;
import com.vienna.jaray.service.SysDictService;
import com.vienna.jaray.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictServiceImpl implements SysDictService {
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysDictEntity> sysDictEntityList = sysDictMapper.findAll();
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(sysDictEntityList);
        return ResponseResult.success().add("sysDicts", pageInfo);
    }

    @Override
    public ResponseResult findById(String id) {
        SysDictEntity sysDictEntity = sysDictMapper.findById(id);
        return ResponseResult.success().add("sysDict", sysDictEntity);
    }

    @Override
    public ResponseResult findByName(String name) {
        SysDictEntity sysDictEntity = sysDictMapper.findByName(name);
        return ResponseResult.success().add("sysDict", sysDictEntity);
    }

    @Override
    public ResponseResult add(SysDictEntity sysDictEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysDictMapper.add(sysDictEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysDict", sysDictEntity);
        }
        return responseResult;
    }

    @Override
    public ResponseResult deleteByIds(String[] ids) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysDictMapper.deleteByIds(ids);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }

    @Override
    public ResponseResult updateById(SysDictEntity sysDictEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysDictMapper.updateById(sysDictEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }

    @Override
    public ResponseResult findByDesc(String description) {
        List<SelectOptionsModel> selectOptionsModelList = sysDictMapper.findByDesc(description);
        return ResponseResult.success().add("selectOptions", selectOptionsModelList);
    }
}