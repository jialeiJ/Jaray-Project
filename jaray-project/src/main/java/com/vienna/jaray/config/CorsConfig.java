package com.vienna.jaray.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 跨域配置类
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许任何的HEAD头部
        corsConfiguration.addAllowedHeader("*");
        // 允许任何域名
        corsConfiguration.addAllowedOrigin("*");
        // 允许任何方法
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(168000L);
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }
}
