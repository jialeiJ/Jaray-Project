package com.vienna.jaray.service.impl;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysMenuEntity;
import com.vienna.jaray.mapper.SysMenuMapper;
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
        List<SysMenuEntity> menuEntities = sysMenuMapper.findFirstLevelMenu();

        for (SysMenuEntity sysMenuEntity : menuEntities) {
            List<SysMenuEntity> nextSubSetMenu = sysMenuMapper.findNextSubSetMenu(sysMenuEntity.getId());
            sysMenuEntity.setChildMenus(getMenuTree(nextSubSetMenu, sysMenuEntity.getId()));
        }
        return ResponseResult.success().add("menuEntities", menuEntities);
    }

    /**
     * 递归取出所有关系树
     * @param nextSubSetMenuEntities
     * @param pid
     * @return
     */
    private List<SysMenuEntity> getMenuTree(List<SysMenuEntity> nextSubSetMenuEntities,int pid) {
        for (SysMenuEntity sysMenuEntity : nextSubSetMenuEntities) {
            List<SysMenuEntity> nextSubSetMenu = sysMenuMapper.findNextSubSetMenu(sysMenuEntity.getId());
            sysMenuEntity.setChildMenus(getMenuTree(nextSubSetMenu, sysMenuEntity.getId()));
        }
        return nextSubSetMenuEntities;
    }
}
