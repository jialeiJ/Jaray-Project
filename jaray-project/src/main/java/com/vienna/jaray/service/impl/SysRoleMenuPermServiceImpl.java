package com.vienna.jaray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.common.Separator;
import com.vienna.jaray.common.SysMenuConfig;
import com.vienna.jaray.entity.system.SysMenu;
import com.vienna.jaray.entity.system.SysRoleMenu;
import com.vienna.jaray.entity.system.SysRolePerm;
import com.vienna.jaray.mapper.SysMenuMapper;
import com.vienna.jaray.mapper.SysRoleMenuMapper;
import com.vienna.jaray.mapper.SysRolePermMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.SysRoleMenuPermService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseResult findByRid(String role_id) {
        // 1、查询角色权限
        SysRolePerm sysRolePerm = sysRolePermMapper.findByRid(role_id);
        // 2、获取权限父ID
        String[] permIds = sysRolePerm.getPerm_id().split(Separator.COMMA_SEPARATOR_EN.getSeparator());
        List<SysMenu> sysPermList = sysMenuMapper.findByIds(permIds);
        List<String> sysPermPidList = sysPermList.stream().filter(sysMenu -> sysMenu.getType() == SysMenuConfig.BTN_FLAG).map(sysMenu -> sysMenu.getParent_id()).collect(Collectors.toList());


        SysRoleMenu sysRoleMenu = sysRoleMenuMapper.findByRid(role_id);
        String[] menuIds = sysRoleMenu.getMenu_id().split(Separator.COMMA_SEPARATOR_EN.getSeparator());
        List<SysMenu> sysMenuList = sysMenuMapper.findByIds(menuIds);
        List<String> sysMenuIdList = sysMenuList.stream().filter(sysMenu -> sysMenu.getType() == SysMenuConfig.MENU_FLAG).map(sysMenu -> sysMenu.getId()).collect(Collectors.toList());
        sysMenuIdList.removeAll(sysPermPidList);
        sysRoleMenu.setMenu_id(StringUtils.join(sysMenuIdList.toArray(), Separator.COMMA_SEPARATOR_EN.getSeparator()));


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
    public ResponseResult deleteByIds(String[] role_ids) {
        ResponseResult responseResult = ResponseResult.fail();
        int result = sysRoleMenuMapper.deleteByIds(role_ids);
        if(result > 0){
            responseResult = ResponseResult.success().add("result", result);
        }
        return responseResult;
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public ResponseResult updateByRid(String role_id, String menu_perm_id) {
        ResponseResult responseResult = ResponseResult.fail();

        List<SysMenu> sysMenuList = sysMenuMapper.findByIds(menu_perm_id.split(Separator.COMMA_SEPARATOR_EN.getSeparator()));

        List<SysMenu> menuPermList = sysMenuList.stream().filter(sysMenu -> sysMenu.getType() == SysMenuConfig.BTN_FLAG).collect(Collectors.toList());
        List<String> permIdList = menuPermList.stream().map(menu -> menu.getId()).collect(Collectors.toList());
        String permIds = StringUtils.join(permIdList.toArray(), Separator.COMMA_SEPARATOR_EN.getSeparator());

        SysRolePerm sysRolePermEntity = new SysRolePerm();
        sysRolePermEntity.setRole_id(role_id);
        sysRolePermEntity.setPerm_id(permIds);
        int updateRolePermResult = sysRolePermMapper.updateByRid(sysRolePermEntity);

        List<SysMenu> menuList = sysMenuList.stream().filter(sysMenu -> (sysMenu.getType() == SysMenuConfig.DIR_FLAG || sysMenu.getType() == SysMenuConfig.MENU_FLAG)).collect(Collectors.toList());
        List<String> menuIdList = menuList.stream().map(menu -> menu.getId()).collect(Collectors.toList());
        String menuIds = StringUtils.join(menuIdList.toArray(), Separator.COMMA_SEPARATOR_EN.getSeparator());
        SysRoleMenu sysRoleMenuEntity = new SysRoleMenu();
        sysRoleMenuEntity.setRole_id(role_id);
        sysRoleMenuEntity.setMenu_id(menuIds);
        int updateRoleMenuResult = sysRoleMenuMapper.updateByRid(sysRoleMenuEntity);

        if(updateRoleMenuResult > 0 && updateRolePermResult > 0){
            responseResult = ResponseResult.success();
        }
        
        return responseResult;
    }
}