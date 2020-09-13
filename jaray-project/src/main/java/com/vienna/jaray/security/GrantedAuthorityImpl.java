package com.vienna.jaray.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 授权认证实现类
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
