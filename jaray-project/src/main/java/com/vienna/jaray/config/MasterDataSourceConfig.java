package com.vienna.jaray.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.vienna.jaray.common.Separator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 主数据源配置类
 */
@Slf4j
@Configuration
public class MasterDataSourceConfig extends DataSourceBaseConfig {
    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    @Autowired
    private Environment env;

    @Value("${spring.datasource.master.type}")
    private String dataSourceType;
    @Value("${spring.datasource.master.url}")
    private String url;
    @Value("${spring.datasource.master.username}")
    private String username;
    @Value("${spring.datasource.master.password}")
    private String password;

    /**
     * 主数据源
     * 返回master数据库的数据源
     * @return
     */
    @Bean(name="masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        //具体配置
        try {
            dataSource.setFilters("stat,wall,slf4j");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return dataSource;
        //return DataSourceBuilder.create().type(dataSourceType).build();

    }

    /**
     * 返回master数据库的会话工厂
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        String typeAliasesPackage = env.getProperty("mybatis.type-aliases-package");
        String configLocation = env.getProperty("mybatis.config-location");
        String mapperLocations = env.getProperty("mybatis.mapper-locations");
        bean.setDataSource(dataSource);
        //bean.setTypeAliasesPackage(typeAliasesPackage); // 单数据源
        bean.setTypeAliasesPackage(setTypeAliasesPackage(typeAliasesPackage));
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocation));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return bean.getObject();
    }

    /**
     * 返回master数据库的会话模板
     * @param sessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sessionFactory) throws  Exception{
        return  new SqlSessionTemplate(sessionFactory);
    }

    /**
     * 返回master数据库的事务
     * @param dataSource
     * @return
     */
    @Bean(name = "masterDataTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("masterDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

//    private String setTypeAliasesPackage(String typeAliasesPackage) {
//        ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
//        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
//        String[] typeAliasesPackages = typeAliasesPackage.split(Separator.COMMA_SEPARATOR_EN.getSeparator());
//        List<String> list = new ArrayList<>();
//        String result = "";
//        for(int i=0;i<typeAliasesPackages.length;i++){
//            typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
//                    ClassUtils.convertClassNameToResourcePath(typeAliasesPackages[i]) + "/" + DEFAULT_RESOURCE_PATTERN;
//
//            //将加载多个绝对匹配的所有Resource
//            //将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分
//            //然后进行遍历模式匹配
//            try {
//                Resource[] resources =  resolver.getResources(typeAliasesPackage);
//                if(resources != null && resources.length > 0){
//                    MetadataReader metadataReader = null;
//                    for(Resource resource : resources){
//                        if(resource.isReadable()){
//                            metadataReader =  metadataReaderFactory.getMetadataReader(resource);
//                            try {
//                                list.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
//                            } catch (ClassNotFoundException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if(list.size() > 0) {
//            log.info("{}", StringUtils.join(list.toArray(), ","));
//            result = StringUtils.join(list.toArray(), Separator.COMMA_SEPARATOR_EN.getSeparator());
//        }else{
//            log.warn("参数setTypeAliasesPackage:"+typeAliasesPackage+"，未找到任何包");
//        }
//        //logger.info("d");
//        return result;
//    }
}
