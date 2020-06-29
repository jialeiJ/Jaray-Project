package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysMenuEntity;
import com.vienna.jaray.mapper.SysMenuMapper;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public ResponseResult findLeftNav() {
        List<SysMenuEntity> menuEntityList = sysMenuMapper.findFirstLevelMenu();

        for (SysMenuEntity sysMenuEntity : menuEntityList) {
            List<SysMenuEntity> nextSubSetMenu = sysMenuMapper.findNextSubSetMenu(sysMenuEntity.getId());
            sysMenuEntity.setChildren(getMenuTree(nextSubSetMenu, sysMenuEntity.getId()));
        }
        return ResponseResult.success().add("leftMenu", menuEntityList);
    }

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysMenuEntity> menuEntityList = sysMenuMapper.findAll();
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(menuEntityList);
        return ResponseResult.success().add("sysMenus", pageInfo);
    }

    @Override
    public ResponseResult add(SysMenuEntity sysMenuEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysMenuMapper.add(sysMenuEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysMenu", sysMenuEntity);
        }
        return responseResult;
    }

    @Override
    public ResponseResult deleteByIds(String[] ids) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysMenuMapper.deleteByIds(ids);
        if(result > 0){
            responseResult = ResponseResult.success();
        }
        return responseResult;
    }

    @Override
    public ResponseResult updateById(SysMenuEntity sysMenuEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysMenuMapper.updateById(sysMenuEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysMenu", sysMenuEntity);
        }
        return responseResult;
    }

    @Override
    public ResponseResult findById(String id) {
        SysMenuEntity sysMenuEntity = sysMenuMapper.findById(id);
        return ResponseResult.success().add("sysMenu", sysMenuEntity);
    }

    /**
     * 递归取出所有关系树
     * @param nextSubSetMenuEntities
     * @param pid
     * @return
     */
    private List<SysMenuEntity> getMenuTree(List<SysMenuEntity> nextSubSetMenuEntities,String pid) {
        for (SysMenuEntity sysMenuEntity : nextSubSetMenuEntities) {
            List<SysMenuEntity> nextSubSetMenu = sysMenuMapper.findNextSubSetMenu(sysMenuEntity.getId());
            sysMenuEntity.setChildren(getMenuTree(nextSubSetMenu, sysMenuEntity.getId()));
        }
        return nextSubSetMenuEntities;
    }
}
