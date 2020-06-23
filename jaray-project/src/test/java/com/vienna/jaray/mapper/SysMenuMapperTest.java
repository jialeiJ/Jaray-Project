package com.vienna.jaray.mapper;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.vienna.jaray.entity.SysMenuEntity;

@Slf4j
@SpringBootTest
public class SysMenuMapperTest {
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Test
	public void testFindMenu() throws Exception {
		List<SysMenuEntity> menuEntities = sysMenuMapper.findFirstLevelMenu();
		
		for (SysMenuEntity sysMenuEntity : menuEntities) {
			List<SysMenuEntity> nextSubSetMenu = sysMenuMapper.findNextSubSetMenu(sysMenuEntity.getId());
			sysMenuEntity.setChildMenus(getMenuTree(nextSubSetMenu, sysMenuEntity.getId()));
		}
		log.info("{}", new Gson().toJson(menuEntities));
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
			sysMenuEntity.setChildMenus(getMenuTree(nextSubSetMenu, sysMenuEntity.getId()));
		}
        return nextSubSetMenuEntities;
    }

}
