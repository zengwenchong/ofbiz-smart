package org.huihoo.ofbiz.smart.entity;


import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

/**
 * <p>
 * 数据源创建接口
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 * @param <K>
 * @param <V>
 */
public interface DataSourceCreator<K, V extends DataSource> {
  /**
   * 创建数据源
   * 
   * @param config 数据源属性配置
   * @return 创建成功之后的数据源<code>Map</code>,其中键为数据源的名称，值为<code>javax.sql.DataSource</code>接口实例
   */
  public Map<K, V> createDataSources(Properties config);
}
