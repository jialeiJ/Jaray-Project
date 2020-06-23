package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysDeptEntity;
import com.vienna.jaray.mapper.SysDeptMapper;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysDeptEntity> sysDeptEntityList = sysDeptMapper.findAll();
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(sysDeptEntityList);
        return ResponseResult.success().add("sysDepts", pageInfo);
    }

    @Override
    public ResponseResult findById(String id) {
        SysDeptEntity sysDeptEntity = sysDeptMapper.findById(id);
        return ResponseResult.success().add("sysDept", sysDeptEntity);
    }

    @Override
    public ResponseResult findByName(String name) {
        SysDeptEntity sysDeptEntity = sysDeptMapper.findByName(name);
        return ResponseResult.success().add("sysDept", sysDeptEntity);
    }

    @Override
    public ResponseResult add(SysDeptEntity sysDeptEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysDeptMapper.add(sysDeptEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysDept", sysDeptEntity);
        }
        return responseResult;
    }

    @Override
    public ResponseResult deleteByIds(String[] ids) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysDeptMapper.deleteByIds(ids);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }

    @Override
    public ResponseResult updateById(SysDeptEntity sysDeptEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysDeptMapper.updateById(sysDeptEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }
}