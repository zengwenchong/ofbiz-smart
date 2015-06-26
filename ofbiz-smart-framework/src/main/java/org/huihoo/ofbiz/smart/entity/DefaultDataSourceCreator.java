package org.huihoo.ofbiz.smart.entity;


import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.huihoo.ofbiz.smart.C;
/**
 * <p>
 *  默认数据源创建{@link DataSourceCreator}接口实现
 * </p>
 * <p>
 *  根据数据源属性配置，生成多个<code>{@link SimpleDataSource}</code>数据源实例
 * </p>
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public class DefaultDataSourceCreator implements DataSourceCreator<String, DataSource> {

  @Override
  public Map<String, DataSource> createDataSources(Properties config) {
    Map<String, DataSource> dataSources = new LinkedHashMap<>();
    Set<String> dataSourceNames = new HashSet<>();
    
    Set<Object> keySet = config.keySet();
    for(Object key : keySet){
      String k = (String) key;
      if(k.startsWith("datasource") && k.indexOf("default") == -1){
        String[] kToken = k.split("\\.");
        String dName = kToken[1];
        dataSourceNames.add(dName);
      }
    }
    
    for (String dName : dataSourceNames) {
      String provider = config.getProperty("datasource."+dName+".provider");
      if(C.DEF_DATASOURCE_PROVIDER.equals(provider)){
        String driver = config.getProperty("datasource."+dName+".driver");
        String username = config.getProperty("datasource."+dName+".username");
        String password = config.getProperty("datasource."+dName+".password");
        String url = config.getProperty("datasource."+dName+".url");
        int maxConnections = Integer.valueOf(config.getProperty("datasource."+dName+".maxConnections", "16"));
        int minConnections = Integer.valueOf(config.getProperty("datasource."+dName+".minConnections", "16"));
        
        SimpleDataSource ds = new SimpleDataSource();
        ds.setDriverClassName(driver);
        ds.setUserName(username);
        ds.setPassword(password);
        ds.setUrl(url);
        ds.setMaxConnections(maxConnections);
        ds.setMaxConnections(minConnections);
        dataSources.put(dName, ds);
      }
    }
    return dataSources;
  }
}
