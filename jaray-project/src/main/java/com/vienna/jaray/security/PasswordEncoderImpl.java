package com.vienna.jaray.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 密码加密实现类
 *     Spring Security报错，报There is no PasswordEncoder mapped for the id "null"
 *     Spring Security 升级到5版本后密码支持多种加密格式
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
