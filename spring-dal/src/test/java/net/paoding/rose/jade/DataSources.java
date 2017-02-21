package net.paoding.rose.jade;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSources {

    public static Map<String, DataSource> instances = new HashMap<String, DataSource>();

    /**
     * 创建一个新的、唯一的DataSource实例
     * 
     * @return
     */
    public static DataSource createUniqueDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
       
        dataSource.setUrl("jdbc:mysql://192.168.8.242:3306/gtp?useUnicode=true&characterEncoding=utf-8");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("mdb");
        dataSource.setPassword("123456");
        
        return dataSource;
    }
}
