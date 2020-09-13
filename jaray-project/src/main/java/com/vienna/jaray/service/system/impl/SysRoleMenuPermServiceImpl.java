package com.vienna.jaray.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.common.Separator;
import com.vienna.jaray.common.SysMenuConfig;
import com.vienna.jaray.entity.system.SysMenu;
import com.vienna.jaray.entity.system.SysRoleMenu;
import com.vienna.jaray.entity.system.SysRolePerm;
import com.vienna.jaray.mapper.system.SysMenuMapper;
import com.vienna.jaray.mapper.system.SysRoleMenuMapper;
import com.vienna.jaray.mapper.system.SysRolePermMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.system.SysRoleMenuPermService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jaray
 * @date 2020年09月12日 13:58
 * @description: 系统角色菜单权限服务实现类
 */
@Service
public class SysRoleMenuPermServiceImpl implements SysRoleMenuPermService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysRolePermMapper sysRolePermMapper;

    @Override
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        //设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.findAll();
        //取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(sysRoleMenuList);
        return ResponseResult.success().add("sysRoleMenuList", pageInfo);
    }

    @Override
    public ResponseResult findByRid(String roleId) {
        // 1、查询角色权限
        SysRolePerm sysRolePerm = sysRolePermMapper.findByRid(roleId);
        List<String> sysPermPidList = new ArrayList<>();
        if (sysRolePerm != null) {
            // 2、获取权限父ID
            String[] permIds = sysRolePerm.getPermId().split(Separator.COMMA_SEPARATOR_EN.getSeparator());
            List<SysMenu> sysPermList = sysMenuMapper.findByIds(permIds);
            sysPermPidList = sysPermList.stream().filter(sysMenu -> sysMenu.getType() == SysMenuConfig.BTN_FLAG).map(SysMenu::getParentId).collect(Collectors.toList());
        }

        SysRoleMenu sysRoleMenu = sysRoleMenuMapper.findByRid(roleId);
        if (sysRoleMenu != null) {
            String[] menuIds = sysRoleMenu.getMenuId().split(Separator.COMMA_SEPARATOR_EN.getSeparator());
            List<SysMenu> sysMenuList = sysMenuMapper.findByIds(menuIds);
            List<String> sysMenuIdList = sysMenuList.stream().filter(sysMenu -> sysMenu.getType() == SysMenuConfig.MENU_FLAG).map(SysMenu::getId).collect(Collectors.toList());
            sysMenuIdList.removeAll(sysPermPidList);
            sysRoleMenu.setMenuId(StringUtils.join(sysMenuIdList.toArray(), Separator.COMMA_SEPARATOR_EN.getSeparator()));
        }

        return ResponseResult.success().add("sysRoleMenu", sysRoleMenu).add("sysRolePerm", sysRolePerm);
    }

    @Override
    public ResponseResult add(SysRoleMenu sysRoleMenuEntity) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRoleMenuMapper.add(sysRoleMenuEntity);
        if(result > 0){
            responseResult = ResponseResult.success().add("sysRoleMenu", sysRoleMenuEntity);
        }
        return responseResult;
    }

    @Override
    public ResponseResult deleteByIds(String[] roleIds) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRoleMenuMapper.deleteByIds(roleIds);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public ResponseResult updateByRid(String roleId, String menuPermId) {
        ResponseResult responseResult = ResponseResult.fail();

        List<SysMenu> sysMenuList = sysMenuMapper.findByIds(menuPermId.split(Separator.COMMA_SEPARATOR_EN.getSeparator()));

        List<SysMenu> menuPermList = sysMenuList.stream().filter(sysMenu -> sysMenu.getType() == SysMenuConfig.BTN_FLAG).collect(Collectors.toList());
        String[] permIds = menuPermList.stream().map(SysMenu::getId).toArray(String[]::new);
        String permIdStr = StringUtils.join(permIds, Separator.COMMA_SEPARATOR_EN.getSeparator());

        SysRolePerm sysRolePerm = sysRolePermMapper.findByRid(roleId);
        int updateRolePermResult;
        if (sysRolePerm == null){
            sysRolePerm = new SysRolePerm();
            sysRolePerm.setRoleId(roleId);
            sysRolePerm.setPermId(permIdStr);
            updateRolePermResult = sysRolePermMapper.add(sysRolePerm);
        } else {
            sysRolePerm.setRoleId(roleId);
            sysRolePerm.setPermId(permIdStr);
            updateRolePermResult = sysRolePermMapper.updateByRid(sysRolePerm);
        }


        List<SysMenu> menuList = sysMenuList.stream().filter(sysMenu -> (sysMenu.getType() == SysMenuConfig.DIR_FLAG || sysMenu.getType() == SysMenuConfig.MENU_FLAG)).collect(Collectors.toList());
        String[] menuIds = menuList.stream().map(SysMenu::getId).toArray(String[]::new);
        String menuIdStr = StringUtils.join(menuIds, Separator.COMMA_SEPARATOR_EN.getSeparator());

        SysRoleMenu sysRoleMenu = sysRoleMenuMapper.findByRid(roleId);
        int updateRoleMenuResult;
        if (sysRoleMenu == null) {
            sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuIdStr);
            updateRoleMenuResult = sysRoleMenuMapper.add(sysRoleMenu);
        } else {
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuIdStr);
            updateRoleMenuResult = sysRoleMenuMapper.updateByRid(sysRoleMenu);
        }

        if(updateRoleMenuResult > 0 && updateRolePermResult > 0){
            responseResult = ResponseResult.success();
        }
        
        return responseResult;
    }
}