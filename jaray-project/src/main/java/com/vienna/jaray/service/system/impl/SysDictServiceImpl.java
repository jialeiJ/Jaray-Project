package com.vienna.jaray.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysDict;
import com.vienna.jaray.mapper.system.SysDictMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.model.system.SelectOptionsModel;
import com.vienna.jaray.service.system.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月12日 13:58
 * @description: 系统字典服务实现类
 */
@Service
public class SysDictServiceImpl implements SysDictService {
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysDict> sysDictList = sysDictMapper.findAll();
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(sysDictList);
        return ResponseResult.success().add("sysDicts", pageInfo);
    }

    @Override
    public ResponseResult findById(String id) {
        SysDict sysDict = sysDictMapper.findById(id);
        return ResponseResult.success().add("sysDict", sysDict);
    }

    @Override
    public ResponseResult findByName(String name) {
        SysDict sysDict = sysDictMapper.findByName(name);
        return ResponseResult.success().add("sysDict", sysDict);
    }

    @Override
    public ResponseResult add(SysDict sysDict) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysDictMapper.add(sysDict);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysDict", sysDict);
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
    public ResponseResult updateById(SysDict sysDictEntity) {
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