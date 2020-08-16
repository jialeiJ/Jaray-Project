package com.vienna.jaray.config;

import com.vienna.jaray.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，配置拦截器地址
        registry.addInterceptor(tokenInterceptor)
            //.addPathPatterns("/system/findLeftNav") // 配置拦截
            .excludePathPatterns("/") // 配置不拦截
            .excludePathPatterns("/dist/**") // 配置不拦截
            .excludePathPatterns("/main") // 配置不拦截
            .excludePathPatterns("/system/login") // 配置不拦截
            .excludePathPatterns("/**/*.html") // 配置不拦截
            .excludePathPatterns("/**/*.js") // 配置不拦截
            .excludePathPatterns("/**/*.css") // 配置不拦截
            .excludePathPatterns("/**/*.woff") // 配置不拦截
            .excludePathPatterns("/**/*.ttf"); // 配置不拦截
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
