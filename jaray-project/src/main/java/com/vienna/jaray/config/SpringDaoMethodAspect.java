package com.vienna.jaray.config;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: druid的spring监控配置
 */
@Slf4j
@Configuration
public class SpringDaoMethodAspect {
    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        DruidStatInterceptor dsInterceptor = new DruidStatInterceptor();
        return dsInterceptor;
    }

    @Bean
    @Scope("prototype")
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPatterns(setPatterns("com.vienna.jaray.mapper.**"));
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor, JdkRegexpMethodPointcut druidStatPointcut) {
        DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
        defaultPointAdvisor.setPointcut(druidStatPointcut);
        defaultPointAdvisor.setAdvice(druidStatInterceptor);
        return defaultPointAdvisor;
    }

    private String[] setPatterns(String pattern) {
        ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(pattern) + "/" + DEFAULT_RESOURCE_PATTERN;

        //将加载多个绝对匹配的所有Resource
        //将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分
        //然后进行遍历模式匹配
        String[] result = new String[0];
        try {
            Set<String> set = new TreeSet<>();
            Resource[] resources =  resolver.getResources(pattern);
            if(resources != null && resources.length > 0){
                MetadataReader metadataReader = null;
                for(Resource resource : resources){
                    if(resource.isReadable()){
                        metadataReader =  metadataReaderFactory.getMetadataReader(resource);
                        try {
                            set.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName() + ".*");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if(set.size() > 0) {
                log.info("{}", StringUtils.join(set.toArray(), ","));
                result = new String[set.size()];
                set.toArray(result);
            }else{
                log.warn("参数patterns:"+pattern+"，未找到任何包");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
