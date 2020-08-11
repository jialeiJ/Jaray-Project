package com.vienna.jaray.config;

import com.vienna.jaray.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，配置拦截器地址
        registry.addInterceptor(tokenInterceptor)
//            .addPathPatterns("/system/findLeftNav") // 配置拦截
            .excludePathPatterns("/system/login"); // 配置不拦截
    }
}
