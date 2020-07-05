package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.common.Separator;
import com.vienna.jaray.entity.SysMenuEntity;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.mapper.SysMenuMapper;
import com.vienna.jaray.mapper.SysUserMapper;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.model.SelectOptionsModel;
import com.vienna.jaray.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResponseResult findLeftNav(String user_id) {
        // 1、查询出所有菜单及用户菜单权限
        List<SysMenuEntity> menuEntityList = sysMenuMapper.findAll();
        List<SysMenuEntity> noMenuEntityList = new ArrayList<>();
        if(!StringUtils.isEmpty(user_id)){
            SysUserEntity sysUser = sysUserMapper.findById(user_id);
            String menu_perm = sysUser.getMenu_perm();
            String[] menu_perms = {};
            if(!StringUtils.isEmpty(menu_perm)){
                menu_perms = menu_perm.split(Separator.COMMA_SEPARATOR_EN.getSeparator());
            }
            noMenuEntityList = sysMenuMapper.findByPerm(menu_perms);
        }

        // 2、获取顶层菜单
        List<SysMenuEntity> firstLevelMenuEntityList = menuEntityList.stream()
                .filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals("M0000000000000")).collect(Collectors.toList());
        firstLevelMenuEntityList.removeAll(noMenuEntityList);

        // 3、遍历顶层菜单，递归为其子菜单赋值
        List<SysMenuEntity> finalNoMenuEntityList = noMenuEntityList;
        firstLevelMenuEntityList.forEach(firstLevelMenuEntity -> {
            List<SysMenuEntity> nextSubSetMenu = menuEntityList.stream()
                    .filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals(firstLevelMenuEntity.getId())).collect(Collectors.toList());
            nextSubSetMenu.removeAll(finalNoMenuEntityList);
            firstLevelMenuEntity.setChildren(getMenuTree(nextSubSetMenu, firstLevelMenuEntity.getId(), menuEntityList, finalNoMenuEntityList));
        });

        return ResponseResult.success().add("leftMenu", firstLevelMenuEntityList);
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

    @Override
    public ResponseResult findAllDir() {
        List<SelectOptionsModel> selectOptionsModelList = sysMenuMapper.findAllDir();
        return ResponseResult.success().add("selectOptions", selectOptionsModelList);
    }

    /**
     * 递归取出所有关系树
     * @param nextSubSetMenuEntityList
     * @param pid
     * @return
     */
    private List<SysMenuEntity> getMenuTree(List<SysMenuEntity> nextSubSetMenuEntityList,String pid, List<SysMenuEntity> menuEntityList, List<SysMenuEntity> noMenuEntityList) {
        nextSubSetMenuEntityList.forEach(nextSubSetMenuEntity -> {
            List<SysMenuEntity> nextSubSetMenu = menuEntityList.stream()
                    .filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals(nextSubSetMenuEntity.getId())).collect(Collectors.toList());
            nextSubSetMenu.removeAll(noMenuEntityList);
            nextSubSetMenuEntity.setChildren(getMenuTree(nextSubSetMenu, nextSubSetMenuEntity.getId(), menuEntityList, noMenuEntityList));
        });

        return nextSubSetMenuEntityList;
    }

}
