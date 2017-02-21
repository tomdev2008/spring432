package net.paoding.rose.jade.dataaccess.datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.DataSourceHolder;
import net.paoding.rose.jade.statement.StatementMetaData;

/**
 * 从预设的一系列的DataSource随机提供一个，在m/s或多数据源情况下使用
 * 
 */
public class RandomDataSourceFactory implements DataSourceFactory {

    private Random random = new Random();

    private List<DataSourceHolder> dataSources = Collections.emptyList();

    public RandomDataSourceFactory() {
    	
    }

    public void addDataSource(DataSource dataSource) {
        if (this.dataSources.size() == 0) {
            this.dataSources = new ArrayList<DataSourceHolder>(dataSources);
        }
        this.dataSources.add(new DataSourceHolder(dataSource));
    }

    public RandomDataSourceFactory(List<DataSource> dataSources) {
        this.setDataSources(dataSources);
    }

    public void setDataSources(List<DataSource> dataSources) {
        this.dataSources = new ArrayList<DataSourceHolder>(dataSources.size());
        for (DataSource dataSource : dataSources) {
            this.dataSources.add(new DataSourceHolder(dataSource));
        }
    }

    @Override
    public DataSourceHolder getHolder(StatementMetaData metaData,
            Map<String, Object> runtimeProperties) {
    	
    	/*
    	 * 随机取的方式，是特别不合适的，并且在其中有些机器掉了的情况下
    	 * 也无法做到服务状态的感知，这里需要改的2点是:
    	 * 1.负载均衡:保证各个机器服务的均衡性，并且能加权限
    	 * 2.失败迁移和状态自动感知:掉线时自动移除，上线时能自动感知
    	 * 3.服务预警:超出服务能力设置，提供预警功能
    	 * 4.上述功能是为了提供可靠服务的，但是代价得控制在5%以内
    	 */
        if (dataSources.size() == 0) {
            return null;
        }
        int index = random.nextInt(dataSources.size()); // 0.. size
        return dataSources.get(index);
    }

}
