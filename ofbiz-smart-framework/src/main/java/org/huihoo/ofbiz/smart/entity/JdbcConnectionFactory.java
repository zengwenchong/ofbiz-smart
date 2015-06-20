package org.huihoo.ofbiz.smart.entity;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.huihoo.ofbiz.smart.base.pool.ObjectFactory;
import org.huihoo.ofbiz.smart.base.util.Log;

public class JdbcConnectionFactory implements ObjectFactory<Connection> {

  private final Driver driver;
  private final String connectUri;
  private final Properties props;

  public JdbcConnectionFactory(Driver driver, String connectUri, Properties props) {
    this.driver = driver;
    this.connectUri = connectUri;
    this.props = props;
  }

  @Override
  public Connection createNew() {
    try {
      return driver.connect(connectUri, props);
    } catch (SQLException e) {
      Log.e(e, getClass().getName(), "Unable to get sql connection.");
      return null;
    }
  }

}
