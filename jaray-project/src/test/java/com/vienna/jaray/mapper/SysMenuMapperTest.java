package com.vienna.jaray.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.vienna.jaray.common.ResponseResult;
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
		// 1、查询出所有菜单
		List<SysMenuEntity> menuEntityList = sysMenuMapper.findAll();

		// 2、获取顶层菜单
		List<SysMenuEntity> firstLevelMenuEntityList = menuEntityList.stream()
				.filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals("M0")).collect(Collectors.toList());

		// 3、遍历顶层菜单，递归为其子菜单赋值
		firstLevelMenuEntityList.forEach(firstLevelMenuEntity -> {
			List<SysMenuEntity> nextSubSetMenu = menuEntityList.stream()
					.filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals(firstLevelMenuEntity.getId())).collect(Collectors.toList());
			firstLevelMenuEntity.setChildren(getMenuTree(nextSubSetMenu, firstLevelMenuEntity.getId(), menuEntityList));
		});

		log.info("{}", new Gson().toJson(firstLevelMenuEntityList));
    }
    
	/**
	 * 递归取出所有关系树
	 * @param nextSubSetMenuEntityList
	 * @param pid
	 * @param menuEntityList
	 * @return
	 */
	private List<SysMenuEntity> getMenuTree(List<SysMenuEntity> nextSubSetMenuEntityList,String pid, List<SysMenuEntity> menuEntityList) {
		nextSubSetMenuEntityList.forEach(nextSubSetMenuEntity -> {
			List<SysMenuEntity> nextSubSetMenu = menuEntityList.stream()
					.filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals(nextSubSetMenuEntity.getId())).collect(Collectors.toList());

			nextSubSetMenuEntity.setChildren(getMenuTree(nextSubSetMenu, nextSubSetMenuEntity.getId(), menuEntityList));
		});

		return nextSubSetMenuEntityList;
    }

}
