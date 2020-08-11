package com.vienna.jaray.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vienna.jaray.common.Separator;
import com.vienna.jaray.entity.SysMenu;
import com.vienna.jaray.entity.SysRolePerm;
import com.vienna.jaray.entity.SysUser;
import com.vienna.jaray.entity.SysUserRole;
import com.vienna.jaray.mapper.SysMenuMapper;
import com.vienna.jaray.mapper.SysRolePermMapper;
import com.vienna.jaray.mapper.SysUserMapper;
import com.vienna.jaray.mapper.SysUserRoleMapper;
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

@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
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
		SysUser sysUserEntity = sysUserMapper.findByName(username);
		if("admin".equalsIgnoreCase(username)){
			List<SysMenu> sysMenuList = sysMenuMapper.findBtnAll();

			Set<String> permissions = sysMenuList.stream().map(SysMenu::getPerm).collect(Collectors.toSet());

			//这里使用自定义权限列表的方式初始化权限
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority> ();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			grantedAuthorities.add(new SimpleGrantedAuthority("delete"));
			grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
			// 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
			
			UserDetails user = new User(sysUserEntity.getName(), sysUserEntity.getPassword(), grantedAuthorities);//注意：大写P，Passw0rd
			return user;
		}else{
			SysUserRole sysUserRole = sysUserRoleMapper.findByUserId(sysUserEntity.getId());
			List<SysRolePerm> sysRolePermList = sysRolePermMapper.findByRids(sysUserEntity.getRole_id().split(Separator.COMMA_SEPARATOR_EN.getSeparator()));

			String[] rids = {};
			for(int i=0;i<sysRolePermList.size();i++){
				rids = ArrayUtils.addAll(rids, sysRolePermList.get(i).getPerm_id().split(Separator.COMMA_SEPARATOR_EN.getSeparator()));
			}

			List<SysMenu> sysMenuList = sysMenuMapper.findByIds(rids);
			Set<String> permissions = sysMenuList.stream().map(SysMenu::getPerm).collect(Collectors.toSet());

			//这里使用自定义权限列表的方式初始化权限
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority> ();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_NORMAL"));
			grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());


			UserDetails user = new User(sysUserEntity.getName(), sysUserEntity.getPassword(), grantedAuthorities);
	        return user;
		}
	}

}
