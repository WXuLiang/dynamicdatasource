package com.example.dynamicdatasource.commons.datasourceconfig;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)//在@Transactional事务标注的方法内切换数据源需要设置
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(targetDataSource)")
    public void setDataSourceKey(JoinPoint point, TargetDataSource targetDataSource) throws Throwable{
        //根据连接点所属的类实例,动态切换数据源
        logger.info("切换数据源为:{}",targetDataSource.value());
        DataSourceContextHolder.setDataSource(targetDataSource.value());
    }

    @After("@annotation(targetDataSource)")
    public void clearDataSourceType(JoinPoint point,TargetDataSource targetDataSource) {
        DataSourceContextHolder.clearDataSource();
    }
}
