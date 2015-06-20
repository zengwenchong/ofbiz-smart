package org.huihoo.ofbiz.smart.entity;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;


import org.huihoo.ofbiz.smart.base.pool.GenericPool;
import org.huihoo.ofbiz.smart.base.util.Log;

public class PooledDataSource<C extends Connection> implements DataSource, AutoCloseable {
  private static final String tag = PooledDataSource.class.getName();

  private final GenericPool<Connection> pool;

  private PrintWriter out;

  public PooledDataSource(GenericPool<Connection> pool) {
    this.pool = pool;
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return out;
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    this.out = out;
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    throw new UnsupportedOperationException("Login timeout is not supported.");
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    throw new UnsupportedOperationException("Login timeout is not supported.");
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new SQLException("PooledDatasource is not a wrapper.");
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {

    return false;
  }

  @Override
  public void close() throws Exception {
    try {
      pool.shutDown();
    } catch (Exception e) {
      throw new SQLException(e);
    }

  }

  @Override
  public Connection getConnection() throws SQLException {
    try {
      Connection conn = pool.get();
      if (conn == null) return null;
      return new PooledConnection<Connection>(conn, pool);
    } catch (Exception e) {
      Log.e(e, tag, "Unable to get a connection from pool.");
      throw new SQLException("Unable to get a connection from pool.", e);
    }
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    throw new UnsupportedOperationException();
  }

}
