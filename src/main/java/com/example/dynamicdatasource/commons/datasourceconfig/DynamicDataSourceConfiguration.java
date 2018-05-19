package com.example.dynamicdatasource.commons.datasourceconfig;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfiguration {

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${spring.datasource.druid.type}")
    private Class<? extends DataSource> type;


    @Bean(name = "dataSourceOne")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.db1")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().type(type).build();
    }

    @Bean(name = "dataSourceTwo")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db2")
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create().type(type).build();
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dataSource(@Qualifier("dataSourceOne") DataSource dataSourceOne, @Qualifier("dataSourceTwo") DataSource dataSourceTwo) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSourceOne);
        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap(4);
        dsMap.put("dataSourceOne", dataSourceOne);
        dsMap.put("dataSourceTwo", dataSourceTwo);
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
        return template;
    }
    @Bean
    public PlatformTransactionManager txManager(DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
