package com.example.dynamicdatasource.commons.datasourceconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {

    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDataSource(String DataSource) {
        log.debug("切换到{}数据源", DataSource);
        contextHolder.set(DataSource);
    }

    // 获取数据源名
    public static String getDataSource() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDataSource() {
        contextHolder.remove();
    }
}
