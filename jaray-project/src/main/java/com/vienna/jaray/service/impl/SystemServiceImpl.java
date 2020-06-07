package com.vienna.jaray.service.impl;

import com.vienna.jaray.common.ResponseResultMsg;
import com.vienna.jaray.entity.SysMenuEntity;
import com.vienna.jaray.mapper.SysMenuMapper;
import com.vienna.jaray.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public ResponseResultMsg findLeftNav() {
        List<SysMenuEntity> menuEntities = sysMenuMapper.findFirstLevelMenu();

        for (SysMenuEntity sysMenuEntity : menuEntities) {
            List<SysMenuEntity> nextSubSetMenu = sysMenuMapper.findNextSubSetMenu(sysMenuEntity.getId());
            sysMenuEntity.setChildMenus(getMenuTree(nextSubSetMenu, sysMenuEntity.getId()));
        }
        return ResponseResultMsg.success().add("menuEntities", menuEntities);
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
