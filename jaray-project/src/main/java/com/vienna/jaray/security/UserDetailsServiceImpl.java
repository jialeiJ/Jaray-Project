package com.vienna.jaray.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.entity.SysUserRoleEntity;
import com.vienna.jaray.mapper.SysUserMapper;
import com.vienna.jaray.mapper.SysUserRoleMapper;
import com.vienna.jaray.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("用户登录："+username);
		
		if (StringUtils.isEmpty(username)) {
			return null;
		}else if("admin".equalsIgnoreCase(username) ){
			SysUserEntity sysUserEntity = sysUserMapper.findByName(username);
			SysUserRoleEntity sysUserRoleEntity = sysUserRoleMapper.findByUserId(sysUserEntity.getId());
			//这里使用自定义权限列表的方式初始化权限
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority> ();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			grantedAuthorities.add(new SimpleGrantedAuthority("delete"));
			
	        //Set<String> permissions = sysUserService.findPermissions(used.getName());
			//List<GrantedAuthority> grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
//			// 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
			
			UserDetails user = new User(sysUserEntity.getName(), sysUserEntity.getPassword(), grantedAuthorities);//注意：大写P，Passw0rd
			return user;
		}else{
			SysUserEntity sysUserEntity = sysUserMapper.findByName(username);
			//这里使用自定义权限列表的方式初始化权限
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority> ();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_NORMAL"));
			UserDetails user = new User(sysUserEntity.getName(), sysUserEntity.getPassword(), grantedAuthorities);
	        return user;
		}
	}

}
