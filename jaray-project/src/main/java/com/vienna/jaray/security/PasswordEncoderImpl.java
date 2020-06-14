package com.vienna.jaray.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security报错，报There is no PasswordEncoder mapped for the id "null"
 * Spring Security 升级到5版本后密码支持多种加密格式
 * @author Administrator
 *
 */
public class PasswordEncoderImpl implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(rawPassword.toString());
	}

}
