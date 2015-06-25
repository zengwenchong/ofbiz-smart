package org.huihoo.ofbiz.smart.entity;


import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

public interface DataSourceCreator<K, V extends DataSource> {
  public Map<K, V> createDataSources(Properties config);
}
