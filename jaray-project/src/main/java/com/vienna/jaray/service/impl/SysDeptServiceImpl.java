package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysDept;
import com.vienna.jaray.mapper.SysDeptMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.SysDeptService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public ResponseResult findAllTop(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        if(commonParamsModel != null){
            PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        }

        List<SysDept> topSysDeptList = sysDeptMapper.findAllTop();
        List<SysDept> subSysDeptList = sysDeptMapper.findAllSub();
        topSysDeptList.forEach(sysDept -> {
            List<SysDept> nextSysDepts = subSysDeptList.stream().filter(subSysDept -> subSysDept.getParent_id().equals(sysDept.getId())).collect(Collectors.toList());
            sysDept.setChildren(nextSysDepts);


            sysDept.setChildren(getDeptChildren(nextSysDepts, subSysDeptList));
        });
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(topSysDeptList);
        return ResponseResult.success().add("sysDepts", pageInfo);
    }

    @Override
    public ResponseResult findById(String id) {
        SysDept sysDeptEntity = sysDeptMapper.findById(id);
        return ResponseResult.success().add("sysDept", sysDeptEntity);
    }

    @Override
    public ResponseResult findByName(String name) {
        SysDept sysDeptEntity = sysDeptMapper.findByName(name);
        return ResponseResult.success().add("sysDept", sysDeptEntity);
    }

    @Override
    public ResponseResult add(SysDept sysDeptEntity) {
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
    public ResponseResult updateById(SysDept sysDeptEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysDeptMapper.updateById(sysDeptEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }

    /**
     * 递归取出所有关系树
     * @param nextSysDepts
     * @param subSysDeptList
     * @return
     */
    private List<SysDept> getDeptChildren(List<SysDept> nextSysDepts, List<SysDept> subSysDeptList) {
        nextSysDepts.forEach(nextSysDept -> {
            List<SysDept> subSysDepts = subSysDeptList.stream().filter(subSysDept -> subSysDept.getParent_id().equals(nextSysDept.getId())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(subSysDepts)){
                nextSysDept.setChildren(subSysDepts);
            }
        });
        return nextSysDepts;
    }
}