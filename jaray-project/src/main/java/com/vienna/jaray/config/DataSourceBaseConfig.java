package com.vienna.jaray.config;

import com.vienna.jaray.common.Separator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 数据源公共配置类
 */
@Slf4j
public class DataSourceBaseConfig {
    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

     String setTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        String[] typeAliasesPackages = typeAliasesPackage.split(Separator.COMMA_SEPARATOR_EN.getSeparator());
        List<String> list = new ArrayList<>();
        String result = "";
        for(int i=0;i<typeAliasesPackages.length;i++){
            typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(typeAliasesPackages[i]) + "/" + DEFAULT_RESOURCE_PATTERN;

            //将加载多个绝对匹配的所有Resource
            //将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分
            //然后进行遍历模式匹配
            try {
                Resource[] resources =  resolver.getResources(typeAliasesPackage);
                if(resources != null && resources.length > 0){
                    MetadataReader metadataReader = null;
                    for(Resource resource : resources){
                        if(resource.isReadable()){
                            metadataReader =  metadataReaderFactory.getMetadataReader(resource);
                            try {
                                list.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(list.size() > 0) {
            log.info("{}", StringUtils.join(list.toArray(), ","));
            result = StringUtils.join(list.toArray(), Separator.COMMA_SEPARATOR_EN.getSeparator());
        }else{
            log.warn("参数setTypeAliasesPackage:"+typeAliasesPackage+"，未找到任何包");
        }
        return result;
    }
}
