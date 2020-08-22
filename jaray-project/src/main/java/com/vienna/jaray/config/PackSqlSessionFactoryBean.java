package com.vienna.jaray.config;

import com.vienna.jaray.common.Separator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 适配mybatis实体类别名使用通配符
 */
@Slf4j
public class PackSqlSessionFactoryBean extends SqlSessionFactoryBean {
    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    @Override
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DEFAULT_RESOURCE_PATTERN;

        // 将加载多个绝对匹配的所有Resource
        // 将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分
        // 然后进行遍历模式匹配
        try {
            List<String> result = new ArrayList<String>();
            Resource[] resources = resolver.getResources(typeAliasesPackage);
            if(resources != null && resources.length > 0) {
                MetadataReader metadataReader = null;
                for(Resource resource: resources) {
                    if(resource.isReadable()) {
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        try{
                            result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                        } catch (ClassNotFoundException e) {
                            log.error("加载异常", e);
                        }
                    }
                }
            }
            if(result.size() > 0) {
                super.setTypeAliasesPackage(StringUtils.join(result.toArray(), Separator.COMMA_SEPARATOR_EN.getSeparator()));
            }else{
                log.warn("参数typeAliasesPackage：[{}]未找到任何包", typeAliasesPackage);
            }
        }catch (IOException e) {
            log.error("加载异常", e);
        }
    }
}
