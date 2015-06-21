package org.huihoo.ofbiz.smart.entity;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.huihoo.ofbiz.smart.base.pool.ObjectFactory;
import org.huihoo.ofbiz.smart.base.util.Log;
/**
 * <p>
 *  JDBC连接工厂，负责创建新的连接。
 * </p>
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public class JdbcConnectionFactory implements ObjectFactory<Connection> {
  /** JDBC驱动 */
  private final Driver driver;
  /** 连接地址字符串 */
  private final String connectUri;
  /** 连接属性配置 注意：至少保证属性配置里含有 username和password两个属性*/
  private final Properties props;

  public JdbcConnectionFactory(Driver driver, String connectUri, Properties props) {
    this.driver = driver;
    this.connectUri = connectUri;
    this.props = props;
  }
  
  /**
   * 创建连接
   * @return {@link Connection}
   */
  @Override
  public Connection createNew() {
    try {
      return driver.connect(connectUri, props);
    } catch (SQLException e) {
      Log.e(e, getClass().getName(), "Unable to get a connection.");
      return null;
    }
  }
  
  @Override
  public String toString(){
    return "JdbcConnectionFactory["+hashCode()+"]";
  }

}
