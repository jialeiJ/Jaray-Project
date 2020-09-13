package com.vienna.jaray.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: druid监控配置类
 */
@Configuration
public class DruidConfig {
    /**
     * 主要实现web监控的配置处理
     *
     * @return ServletRegistrationBean
     */
    @Bean
    @Primary
    public ServletRegistrationBean statViewServlet() {
        /** 表示进行druid监控的配置处理操作 */
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new StatViewServlet(), "/druid/*");
        servletRegistrationBean.addInitParameter("enabled", "true");
        /** 白名单 */
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1,192.168.1.104");
        /** 黑名单 */
        servletRegistrationBean.addInitParameter("deny", "192.168.202.234");
        /** 用户名 */
        servletRegistrationBean.addInitParameter("loginUsername", "root");
        /** 密码 */
        servletRegistrationBean.addInitParameter("loginPassword", "root");
        /** 是否可以重置数据源 */
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 监控
     * @return FilterRegistrationBean
     */
    @Bean
    @Primary
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        /** 所有请求进行监控处理 */
        filterRegistrationBean.addUrlPatterns("/*");
        /** 排除 */
        filterRegistrationBean.addInitParameter("exclusions", "/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
