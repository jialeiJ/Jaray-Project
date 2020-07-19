package com.vienna.jaray.mapper;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vienna.jaray.entity.SysMenu;

@Slf4j
@SpringBootTest
public class SysMenuMapperTest {
	@Autowired
	private SysMenuMapper sysMenuMapper;
//	@Autowired
//	private SysMenuPermMapper sysMenuPermMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Test
	public void testFindMenu() throws Exception {
//		String user_id = null;
//		// 1、查询出所有菜单及用户菜单权限、查询所有菜单按钮权限
//		List<SysMenuEntity> menuList = sysMenuMapper.findAll();
//		List<SysMenuEntity> menuPermList = sysMenuPermMapper.findAll();
//
//		List<SysMenuEntity> noMenuList = new ArrayList<>();
//		if(!StringUtils.isEmpty(user_id)){
//			SysUserEntity sysUser = sysUserMapper.findById(user_id);
//			String menu_perm = sysUser.getMenu_perm();
//			String[] menu_perms = {};
//			if(!StringUtils.isEmpty(menu_perm)){
//				menu_perms = menu_perm.split(Separator.COMMA_SEPARATOR_EN.getSeparator());
//			}
//			noMenuList = sysMenuMapper.findByPerm(menu_perms);
//		}
//
//		// 2、获取顶层菜单
//		List<SysMenuEntity> firstLevelMenuList = menuList.stream()
//				.filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals("M0000000000000")).collect(Collectors.toList());
//		firstLevelMenuList.removeAll(noMenuList);
//
//		// 3、遍历顶层菜单，递归为其子菜单赋值
//		List<SysMenuEntity> finalNoMenuList = noMenuList;
//		firstLevelMenuList.forEach(firstLevelMenu -> {
//			List<SysMenuEntity> nextSubSetMenu = menuList.stream()
//					.filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals(firstLevelMenu.getId())).collect(Collectors.toList());
//			nextSubSetMenu.removeAll(finalNoMenuList);
//
//			List<SysMenuEntity> perms = menuPermList.stream().filter(menuPerm -> menuPerm.getParent_id().equals(firstLevelMenu.getId())).collect(Collectors.toList());
//			if(perms.size() > 0){
//				firstLevelMenu.setChildren(perms);
//			}
//
//			firstLevelMenu.setChildren(getMenuTree(nextSubSetMenu, firstLevelMenu.getId(), menuList, finalNoMenuList, menuPermList));
//		});
//
//		log.info("{}", new Gson().toJson(firstLevelMenuList));
    }
    
	/**
	 * 递归取出所有关系树
	 * @param nextSubSetMenuList
	 * @param pid
	 * @param menuList
	 * @return
	 */
	private List<SysMenu> getMenuTree(List<SysMenu> nextSubSetMenuList, String pid, List<SysMenu> menuList, List<SysMenu> noMenuList, List<SysMenu> menuPermList) {
//		nextSubSetMenuList.forEach(nextSubSetMenuEntity -> {
//			List<SysMenuEntity> nextSubSetMenu = menuList.stream()
//					.filter(sysMenuEntity -> sysMenuEntity.getParent_id().equals(nextSubSetMenuEntity.getId())).collect(Collectors.toList());
//			nextSubSetMenu.removeAll(noMenuList);
//
//			List<SysMenuEntity> perms = menuPermList.stream().filter(menuPerm -> menuPerm.getParent_id().equals(nextSubSetMenuEntity.getId())).collect(Collectors.toList());
//			if(perms.size() > 0){
//				nextSubSetMenuEntity.setChildren(perms);
//			}
//
//			nextSubSetMenuEntity.setChildren(getMenuTree(nextSubSetMenu, nextSubSetMenuEntity.getId(), menuList, noMenuList, menuPermList));
//
//		});

		return nextSubSetMenuList;
	}

}
