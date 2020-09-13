package com.vienna.jaray.config;

import com.vienna.jaray.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: WebMvc配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /** 添加拦截器，配置拦截器地址 */
        registry.addInterceptor(tokenInterceptor)
            /** 配置拦截 */
            //.addPathPatterns("/system/findLeftNav")
            /** 配置不拦截 */
            .excludePathPatterns("/")
            .excludePathPatterns("/dist/**")
            .excludePathPatterns("/main")
            .excludePathPatterns("/system/login")
            .excludePathPatterns("/druid/**")
            .excludePathPatterns("/**/*.html")
            .excludePathPatterns("/**/*.js")
            .excludePathPatterns("/**/*.css")
            .excludePathPatterns("/**/*.woff")
            .excludePathPatterns("/**/*.ttf");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dist/**")
                .addResourceLocations("classpath:/dist/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
