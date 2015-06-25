package org.huihoo.ofbiz.smart.entity;


import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.huihoo.ofbiz.smart.C;

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
      String driver = config.getProperty("datasource."+dName+".driver");
      String username = config.getProperty("datasource."+dName+".username");
      String password = config.getProperty("datasource."+dName+".password");
      String url = config.getProperty("datasource."+dName+".url");
      if(C.DEF_DATASOURCE_PROVIDER.equals(provider)){
        SimpleDataSource ds = new SimpleDataSource();
        ds.setDriverClassName(driver);
        ds.setUserName(username);
        ds.setPassword(password);
        ds.setUrl(url);
        dataSources.put(dName, ds);
      }
    }
    
    return dataSources;
  }

}
