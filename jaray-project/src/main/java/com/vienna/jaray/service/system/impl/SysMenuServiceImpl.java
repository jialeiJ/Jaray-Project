package com.vienna.jaray.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.common.Separator;
import com.vienna.jaray.common.SysMenuConfig;
import com.vienna.jaray.entity.system.SysMenu;
import com.vienna.jaray.entity.system.SysRoleMenu;
import com.vienna.jaray.entity.system.SysRolePerm;
import com.vienna.jaray.entity.system.SysUser;
import com.vienna.jaray.mapper.system.SysMenuMapper;
import com.vienna.jaray.mapper.system.SysRoleMenuMapper;
import com.vienna.jaray.mapper.system.SysRolePermMapper;
import com.vienna.jaray.mapper.system.SysUserMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.system.SysMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysRolePermMapper sysRolePermMapper;

    @Override
    public ResponseResult findLeftNav(String user_id) {
        // 1、查询出所有菜单及用户菜单权限、查询所有菜单按钮权限
        List<SysMenu> menuList = sysMenuMapper.findAll();
        List<SysMenu> btnList = sysMenuMapper.findBtnAll();

        List<SysMenu> noMenuList = new ArrayList<>();
        List<SysMenu> permList = new ArrayList<>();
        if(StringUtils.isNotEmpty(user_id)){
            // 查询用户信息
            SysUser sysUser = sysUserMapper.findById(user_id);
            // 获取用户角色id并转为数组
            String roleIds = sysUser.getRole_id();
            String[] roleIdArr = roleIds.split(Separator.COMMA_SEPARATOR_EN.getSeparator());

            // 查询角色菜单
            List<SysRoleMenu> sysRoleMenuList =  sysRoleMenuMapper.findByRids(roleIdArr);
            StringBuffer menuStringBuffer = new StringBuffer("");
            // 遍历角色菜单集合获取菜单权限
            for(int i=0;i<sysRoleMenuList.size();i++){
                if(i == (sysRoleMenuList.size()-1)){
                    menuStringBuffer.append(sysRoleMenuList.get(i).getMenu_id());
                }else{
                    menuStringBuffer.append(sysRoleMenuList.get(i).getMenu_id()).append(Separator.COMMA_SEPARATOR_EN.getSeparator());
                }
            }
            String menu_perm = menuStringBuffer.toString();
            // 获取菜单权限数组
            String[] menu_perms = {};
            if(StringUtils.isNotEmpty(menu_perm)){
                menu_perms = menu_perm.split(Separator.COMMA_SEPARATOR_EN.getSeparator());
            }
            // 获取无权限的菜单
            noMenuList = sysMenuMapper.findNoPermByIds(menu_perms);

            // 查询角色权限
            List<SysRolePerm> sysRolePermList = sysRolePermMapper.findByRids(roleIdArr);
            StringBuffer permStringBuffer = new StringBuffer("");
            // 遍历角色菜单集合获取菜单权限
            for(int i=0;i<sysRolePermList.size();i++){
                if(i == (sysRolePermList.size()-1)){
                    permStringBuffer.append(sysRolePermList.get(i).getPerm_id());
                }else{
                    permStringBuffer.append(sysRolePermList.get(i).getPerm_id()).append(Separator.COMMA_SEPARATOR_EN.getSeparator());
                }
            }
            String role_perm = permStringBuffer.toString();
            // 获取角色权限数组
            String[] role_perms = {};
            if(StringUtils.isNotEmpty(role_perm)){
                role_perms = role_perm.split(Separator.COMMA_SEPARATOR_EN.getSeparator());
            }
            // 获取角色权限的菜单
            permList = sysMenuMapper.findPermByIds(role_perms);
        }else{
            permList = sysMenuMapper.findBtnAll();
        }

        // 2、获取顶层菜单
        List<SysMenu> firstLevelMenuList = menuList.stream()
                .filter(sysMenuEntity -> sysMenuEntity.getParent_id() == null).collect(Collectors.toList());
        // 移除无权限的顶层菜单
        firstLevelMenuList.removeAll(noMenuList);

        // 3、遍历顶层菜单，递归为其子菜单赋值
        List<SysMenu> finalNoMenuList = noMenuList;
        List<SysMenu> finalPermList = permList;
        firstLevelMenuList.forEach(firstLevelMenu -> {
            // 获取子菜单
            List<SysMenu> nextSubSetMenu = menuList.stream()
                    .filter(sysMenuEntity -> firstLevelMenu.getId().equals(sysMenuEntity.getParent_id())).collect(Collectors.toList());
            // 移除无权限的子菜单
            nextSubSetMenu.removeAll(finalNoMenuList);

            // 如果是菜单获取菜单相应的权限
            if(firstLevelMenu.getType() == SysMenuConfig.MENU_FLAG) {
                List<SysMenu> allPerms = btnList.stream().filter(btn -> btn.getParent_id().equals(firstLevelMenu.getId())).collect(Collectors.toList());

                List<SysMenu> perms = allPerms.stream().filter(btn -> finalPermList.contains(btn)).collect(Collectors.toList());

                firstLevelMenu.setPerms(perms);
                firstLevelMenu.setChildren(allPerms);
            }

            // 如果是目录则递归赋值子菜单
            if(firstLevelMenu.getType() == SysMenuConfig.DIR_FLAG){
                firstLevelMenu.setChildren(getMenuTree(nextSubSetMenu, menuList, finalNoMenuList, finalPermList, btnList));
            }
        });

        return ResponseResult.success().add("leftMenu", firstLevelMenuList);
    }

    @Override
    public ResponseResult findTreeAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysMenu> topSysMenuList = sysMenuMapper.findAllTopDir();
        List<SysMenu> subSysMenuList = sysMenuMapper.findAll();
        List<SysMenu> sysMenuList = sysMenuMapper.findAllMenu();

        topSysMenuList.forEach(sysMenu -> {
            List<SysMenu> nextSysMenus = subSysMenuList.stream().filter(subSysMenu -> sysMenu.getId().equals(subSysMenu.getParent_id())).collect(Collectors.toList());
            sysMenu.setChildren(nextSysMenus);

            sysMenu.setChildren(getMenuChildren(nextSysMenus, subSysMenuList, sysMenuList));
        });

        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(topSysMenuList);
        return ResponseResult.success().add("sysMenus", pageInfo);
    }

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysMenu> menuEntityList = sysMenuMapper.findAll();
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(menuEntityList);
        return ResponseResult.success().add("sysMenus", pageInfo);
    }

    @Override
    public ResponseResult add(SysMenu sysMenuEntity) {
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
    public ResponseResult updateById(SysMenu sysMenuEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        if(sysMenuEntity.getType() == SysMenuConfig.DIR_FLAG){
            sysMenuEntity.setUrl("");
        }
        int result = sysMenuMapper.updateById(sysMenuEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysMenu", sysMenuEntity);
        }
        return responseResult;
    }

    @Override
    public ResponseResult findById(String id) {
        SysMenu sysMenu = sysMenuMapper.findById(id);
        return ResponseResult.success().add("sysMenu", sysMenu);
    }

    /**
     * 递归取出所有关系树
     * @param nextSubSetMenuList
     * @param menuList
     * @param noMenuList
     * @param btnList
     * @return
     */
    private List<SysMenu> getMenuTree(List<SysMenu> nextSubSetMenuList, List<SysMenu> menuList, List<SysMenu> noMenuList, List<SysMenu> finalPermList, List<SysMenu> btnList) {
        nextSubSetMenuList.forEach(nextSubSetMenuEntity -> {
            List<SysMenu> nextSubSetMenu = menuList.stream()
                    .filter(sysMenuEntity -> nextSubSetMenuEntity.getId().equals(sysMenuEntity.getParent_id())).collect(Collectors.toList());
            nextSubSetMenu.removeAll(noMenuList);

            List<SysMenu> allPerms = btnList.stream().filter(btn -> btn.getParent_id().equals(nextSubSetMenuEntity.getId())).collect(Collectors.toList());

            List<SysMenu> perms = allPerms.stream().filter(btn -> finalPermList.contains(btn)).collect(Collectors.toList());

            if(perms.size() > 0 && nextSubSetMenuEntity.getType() == SysMenuConfig.MENU_FLAG) {
                nextSubSetMenuEntity.setPerms(perms);
                nextSubSetMenuEntity.setChildren(allPerms);
            }

            if(nextSubSetMenuEntity.getType() == SysMenuConfig.DIR_FLAG) {
                nextSubSetMenuEntity.setChildren(getMenuTree(nextSubSetMenu, menuList, noMenuList, finalPermList, btnList));
            }

        });

        return nextSubSetMenuList;
    }

    /**
     * 递归取出所有关系树
     * @param nextSysMenus
     * @param subSysMentList
     * @param sysMenuList
     * @return
     */
    private List<SysMenu> getMenuChildren(List<SysMenu> nextSysMenus, List<SysMenu> subSysMentList, List<SysMenu> sysMenuList) {
        nextSysMenus.forEach(nextSysMenu -> {
            List<SysMenu> subSysDepts = subSysMentList.stream().filter(subSysMenu -> nextSysMenu.getId().equals(subSysMenu.getParent_id())).collect(Collectors.toList());

            if(CollectionUtils.isEmpty(subSysDepts)){
                List<SysMenu> sysMenus = sysMenuList.stream().filter(sysMenu -> nextSysMenu.getId().equals(sysMenu.getParent_id())).collect(Collectors.toList());
                nextSysMenu.setChildren(sysMenus);
            }else{
                nextSysMenu.setChildren(subSysDepts);
            }
        });
        return nextSysMenus;
    }

}
