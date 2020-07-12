package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.common.Separator;
import com.vienna.jaray.common.SysMenuConfig;
import com.vienna.jaray.entity.SysMenuEntity;
import com.vienna.jaray.entity.SysRoleMenuEntity;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.entity.SysUserRoleEntity;
import com.vienna.jaray.mapper.SysMenuMapper;
import com.vienna.jaray.mapper.SysRoleMenuMapper;
import com.vienna.jaray.mapper.SysUserMapper;
import com.vienna.jaray.mapper.SysUserRoleMapper;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.model.SelectOptionsModel;
import com.vienna.jaray.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public ResponseResult findLeftNav(String user_id) {
        // 1、查询出所有菜单及用户菜单权限、查询所有菜单按钮权限
        List<SysMenuEntity> menuList = sysMenuMapper.findAll();
        List<SysMenuEntity> btnList = sysMenuMapper.findBtnAll();

        List<SysMenuEntity> noMenuList = new ArrayList<>();
        if(!StringUtils.isEmpty(user_id)){
            SysUserEntity sysUser = sysUserMapper.findById(user_id);
            String roleIds = sysUser.getRole_id();
            String[] roleIdArr = roleIds.split(Separator.COMMA_SEPARATOR_EN.getSeparator());

            SysUserRoleEntity userRole = sysUserRoleMapper.findByUserId(user_id);
            List<SysRoleMenuEntity> sysRoleMenuList =  sysRoleMenuMapper.findByRids(roleIdArr);
            StringBuffer menuStringBuffer = new StringBuffer("");

            for(int i=0;i<sysRoleMenuList.size();i++){
                if(i == (sysRoleMenuList.size()-1)){
                    menuStringBuffer.append(sysRoleMenuList.get(i).getMenu_id());
                }else{
                    menuStringBuffer.append(sysRoleMenuList.get(i).getMenu_id()).append(Separator.COMMA_SEPARATOR_EN.getSeparator());
                }
            }

            String menu_perm = menuStringBuffer.toString();
            String[] menu_perms = {};
            if(!StringUtils.isEmpty(menu_perm)){
                menu_perms = menu_perm.split(Separator.COMMA_SEPARATOR_EN.getSeparator());
            }
            noMenuList = sysMenuMapper.findByPerm(menu_perms);
        }

        // 2、获取顶层菜单
        List<SysMenuEntity> firstLevelMenuList = menuList.stream()
                .filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals(SysMenuConfig.TOP_DIR_ID)).collect(Collectors.toList());
        firstLevelMenuList.removeAll(noMenuList);

        // 3、遍历顶层菜单，递归为其子菜单赋值
        List<SysMenuEntity> finalNoMenuList = noMenuList;
        firstLevelMenuList.forEach(firstLevelMenu -> {
            List<SysMenuEntity> nextSubSetMenu = menuList.stream()
                    .filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals(firstLevelMenu.getId())).collect(Collectors.toList());
            nextSubSetMenu.removeAll(finalNoMenuList);

            List<SysMenuEntity> perms = btnList.stream().filter(btn -> btn.getParent_id().equals(firstLevelMenu.getId())).collect(Collectors.toList());

            if(perms.size() > 0 && firstLevelMenu.getType() == SysMenuConfig.MENU_FLAG) {
                firstLevelMenu.setPerms(perms);
                firstLevelMenu.setChildren(perms);
            }

            if(firstLevelMenu.getType() == SysMenuConfig.DIR_FLAG){
                firstLevelMenu.setChildren(getMenuTree(nextSubSetMenu, firstLevelMenu.getId(), menuList, finalNoMenuList, btnList));
            }
        });

        return ResponseResult.success().add("leftMenu", firstLevelMenuList);
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
     * @param nextSubSetMenuList
     * @param pid
     * @return
     */
    private List<SysMenuEntity> getMenuTree(List<SysMenuEntity> nextSubSetMenuList,String pid, List<SysMenuEntity> menuList, List<SysMenuEntity> noMenuList, List<SysMenuEntity> btnList) {
        nextSubSetMenuList.forEach(nextSubSetMenuEntity -> {
            List<SysMenuEntity> nextSubSetMenu = menuList.stream()
                    .filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals(nextSubSetMenuEntity.getId())).collect(Collectors.toList());
            nextSubSetMenu.removeAll(noMenuList);

            List<SysMenuEntity> perms = btnList.stream().filter(btn -> btn.getParent_id().equals(nextSubSetMenuEntity.getId())).collect(Collectors.toList());

            if(perms.size() > 0 && nextSubSetMenuEntity.getType() == SysMenuConfig.MENU_FLAG) {
                nextSubSetMenuEntity.setPerms(perms);
                nextSubSetMenuEntity.setChildren(perms);
            }

            if(nextSubSetMenuEntity.getType() == SysMenuConfig.DIR_FLAG) {
                nextSubSetMenuEntity.setChildren(getMenuTree(nextSubSetMenu, nextSubSetMenuEntity.getId(), menuList, noMenuList, btnList));
            }

        });

        return nextSubSetMenuList;
    }

}
