package com.vienna.jaray.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vienna.jaray.common.Separator;
import com.vienna.jaray.entity.system.SysMenu;
import com.vienna.jaray.entity.system.SysRolePerm;
import com.vienna.jaray.entity.system.SysUser;
import com.vienna.jaray.mapper.system.SysMenuMapper;
import com.vienna.jaray.mapper.system.SysRolePermMapper;
import com.vienna.jaray.mapper.system.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 用户详情服务实现类
 */
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final String USERNAME_ADMIN = "admin";
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRolePermMapper sysRolePermMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("用户登录："+username);
		
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		SysUser sysUser = sysUserMapper.findByName(username);
		if(USERNAME_ADMIN.equalsIgnoreCase(username)){
			// 1、管理员查询所有权限菜单对象
			List<SysMenu> sysMenuList = sysMenuMapper.findBtnAll();

			// 2、获取所有权限
			Set<String> permissions = sysMenuList.stream().map(SysMenu::getPerm).collect(Collectors.toSet());

			//这里使用自定义权限列表的方式初始化权限
			List<GrantedAuthority> grantedAuthorities = new ArrayList<> ();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			grantedAuthorities.add(new SimpleGrantedAuthority("delete"));
			// 3、生成权限对象集合
			grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());

			// 4、赋予用户权限,注意：大写P，Passw0rd
			return new User(sysUser.getName(), sysUser.getPassword(), grantedAuthorities);
		}else{
			// 1、获取角色
			List<SysRolePerm> sysRolePermList = sysRolePermMapper.findByRids(sysUser.getRoleId().split(Separator.COMMA_SEPARATOR_EN.getSeparator()));

			// 2、获取用户所有角色的权限id
			String[] pids = {};
			for (SysRolePerm sysRolePerm : sysRolePermList) {
				pids = ArrayUtils.addAll(pids, sysRolePerm.getPermId().split(Separator.COMMA_SEPARATOR_EN.getSeparator()));
			}

			// 3、获取用户所有角色的所有权限菜单对象
			List<SysMenu> sysMenuList = sysMenuMapper.findByIds(pids);

			// 4、获取所有权限
			Set<String> permissions = sysMenuList.stream().map(SysMenu::getPerm).collect(Collectors.toSet());

			//这里使用自定义权限列表的方式初始化权限
			List<GrantedAuthority> grantedAuthorities = new ArrayList<> ();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_NORMAL"));

			// 5、生成权限对象集合
			grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());

			// 6、赋予用户权限
			return new User(sysUser.getName(), sysUser.getPassword(), grantedAuthorities);
		}
	}
}
