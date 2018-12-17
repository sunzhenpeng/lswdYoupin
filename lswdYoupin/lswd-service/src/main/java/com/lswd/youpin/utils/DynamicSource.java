package com.lswd.youpin.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by H61M-K on 2017/6/2.
 */
public class DynamicSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        // 在进行DAO操作前，通过上下文环境变量，获得数据源的类型
        return DataSourceHandle.getDataSourceType();
    }
}
