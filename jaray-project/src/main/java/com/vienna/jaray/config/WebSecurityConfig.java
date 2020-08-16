package com.vienna.jaray.config;

import com.vienna.jaray.security.JwtAuthenticationAccessDeniedHandler;
import com.vienna.jaray.security.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vienna.jaray.security.JwtAuthenticationFilter;
import com.vienna.jaray.security.UserDetailsServiceImpl;

/**
 * 添加安全配置类， 继承 WebSecurityConfigurerAdapter，
 * 配置URL验证策略和相关过滤器以及自定义的登录验证组件等
 *
 */
@Configuration
@EnableWebSecurity
//Spring Security注解默认是关闭，可以通过在配置类添加以下注解开启
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 跨域预检请求
			.antMatchers("/**/*.html", "/**/*.js", "/**/*.css", "/**/*.woff", "/**/*.ttf", "**/favicon.ico").permitAll() // 静态资源
            .antMatchers("/webjars/**").permitAll() // web jars
            .antMatchers("/druid/**").permitAll() // SQL监控（druid）
            .antMatchers("/").permitAll() // 登录页面
			.antMatchers("/index.html").permitAll() // vue打包文件
			.antMatchers("/main").permitAll() // vue打包文件
            .antMatchers("/system/login").permitAll() // 登录请求
			.antMatchers("/system/refreshToken").permitAll() // 刷新token
			.antMatchers("/system/captcha.jpg**").permitAll() // 验证码
            .antMatchers("/swagger-ui.html").permitAll() // swagger
            .antMatchers("/swagger-resources").permitAll()
            .antMatchers("/v2/api-docs").permitAll()
            .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
            .antMatchers("/actuator/**").permitAll() // 服务监控
            .anyRequest().authenticated()
			// 不需要session
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 其他所有请求需要身份认证
        // 退出登录处理器
        //http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
		// 禁用缓存
		http.headers().cacheControl();
        // 登录及认证过滤器
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

		//添加自定义未授权和未登录结果返回
		http.exceptionHandling()
			.accessDeniedHandler(new JwtAuthenticationAccessDeniedHandler())
			.authenticationEntryPoint(new JwtAuthenticationEntryPoint());
	}
	
	/**
	 * 	验证密码加密机制，
	 * 	手动初始化了IUserDetailsService、AuthenticationProvider
	 * 	另外还需要使用重写父类方法configure(AuthenticationManagerBuilder auth)
	 * 	auth.userDetailsService(getUserDetailsService());
	 */
	@Bean
	public UserDetailsServiceImpl getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder()	{
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 使用自定义身份验证组件
		auth.userDetailsService(getUserDetailsService())
			//BCryptPasswordEncoder加密方式，无需传入盐值
			.passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/",
				"/dist/**",
				"/main",
				"/system/captcha.jpg**",
				"/system/login",
				"/system/refreshToken",
				"/druid/**",
				"/actuator/**",
				"/v2/**",
				"/webjars/**",
				"/webjars/springfox-swagger-ui/**",
				"/swagger-resources/configuration/ui/**",
				"/swagger-resources/**",
				"/swagger-resources/configuration/security",
				"/swagger-ui.html"
		);
	}
	
	@Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
