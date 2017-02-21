package net.paoding.rose.jade.dataaccess.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.paoding.rose.jade.annotation.SQLType;
import net.paoding.rose.jade.annotation.UseMaster;
import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.DataSourceHolder;
import net.paoding.rose.jade.statement.StatementMetaData;

import org.springframework.util.CollectionUtils;

/**
 * m/s模式下的数据源工厂对象
 * 
 * 在master-slave模式下的应用程序可以使用<br/>
 * {#link MasterSlaveDataSourceFactory}<br/>
 * 作为程序的{#link DataSourceFactory}实现入口<br/>
 * 即所有写操作使用master，所有读操作使用slave<br/>
 * <p>  
 * 
 */
public class MasterSlaveDataSourceFactory implements DataSourceFactory {

    private DataSourceFactory masters = new RandomDataSourceFactory();

    private DataSourceFactory slaves = new RandomDataSourceFactory();

    public MasterSlaveDataSourceFactory() {
    	
    }

    /**
     * 
     * @param master
     * @param slaves
     * @param queryFromMaster true代表允许从master数据源查询数据
     */
    public MasterSlaveDataSourceFactory(DataSource master, List<DataSource> slaves,
            boolean queryFromMaster) {
        if (queryFromMaster && !CollectionUtils.containsInstance(slaves, master)) {
            slaves = new ArrayList<DataSource>(slaves);
            slaves.add(master);
        }
        setSlaves(new RandomDataSourceFactory(slaves));
        setMasters(new SimpleDataSourceFactory(master));
    }

    //------------------

    /**
     * 
     * @param masters
     * @see RandomDataSourceFactory
     * @see SimpleDataSourceFactory
     */
    public void setMasters(DataSourceFactory masters) {
        this.masters = masters;
    }

    /**
     * 
     * @param slaves
     * @see RandomDataSourceFactory
     */
    public void setSlaves(DataSourceFactory slaves) {
        this.slaves = slaves;
    }

    @Override
    public DataSourceHolder getHolder(StatementMetaData metaData,
            Map<String, Object> runtimeProperties) {
    	
    	/**
    	 * 2015 by gtp
    	 * force to use master
    	 */
    	UseMaster useMaster = metaData.getMethod().getAnnotation(UseMaster.class);
    	if(useMaster!=null){
    		
    		return masters.getHolder(metaData, runtimeProperties);
    		
    	}else if (metaData.getSQLType() != SQLType.READ) {
    		
            return masters.getHolder(metaData, runtimeProperties);
            
        } else {
        	
            return slaves.getHolder(metaData, runtimeProperties);
        }
    }
}
