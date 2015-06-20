package org.huihoo.ofbiz.smart.entity;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * <p>
 * 一个简单的数据源实现
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 */
public class SimpleDataSource implements DataSource {

  public static final String TAG = SimpleDataSource.class.getName();

  private volatile DataSource dataSource = new SimpleDataSource();

  private volatile PrintWriter logWriter = new PrintWriter(new OutputStreamWriter(System.out,StandardCharsets.UTF_8));

  private String driverClassName = null;

  private Driver driver = null;

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return createDataSource().getLogWriter();
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    createDataSource().setLogWriter(out);
    this.logWriter = out;
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    throw new UnsupportedOperationException("Not supported by SimpleDataSource");
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    throw new UnsupportedOperationException("Not supported by SimpleDataSource");
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new SQLException("SimpleDataSource is not a wrapper.");
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }

  @Override
  public Connection getConnection() throws SQLException {
    return createDataSource().getConnection();
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    throw new UnsupportedOperationException("Not supported by SimpleDataSource");
  }


  protected DataSource createDataSource() {
    
    return null;
  }



  static class PooledConnection<C extends Connection> implements Connection {

    private volatile C conn = null;
    private volatile boolean closed = false;

    public PooledConnection(C c) {
      super();
      this.conn = c;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
      if (iface.isAssignableFrom(getClass())) {
        return iface.cast(this);
      } else if (iface.isAssignableFrom(conn.getClass())) {
        return iface.cast(conn);
      } else {
        return conn.unwrap(iface);
      }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
      if (iface.isAssignableFrom(getClass())) {
        return true;
      } else if (iface.isAssignableFrom(conn.getClass())) {
        return true;
      } else {
        return conn.isWrapperFor(iface);
      }
    }

    @Override
    public Statement createStatement() throws SQLException {
      return conn.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
      return conn.prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
      return conn.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
      return conn.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
      conn.setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
      return conn.getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
      conn.commit();
    }

    @Override
    public void rollback() throws SQLException {
      conn.rollback();
    }

    @Override
    public void close() throws SQLException {
      conn.close();
    }

    @Override
    public boolean isClosed() throws SQLException {
      return conn.isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
      return conn.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
      conn.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
      return conn.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
      conn.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
      return conn.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
      conn.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
      return conn.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
      return conn.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
      conn.clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency)
            throws SQLException {
      return conn.createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
            int resultSetConcurrency) throws SQLException {
      return conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
      return conn.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
      return conn.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
      conn.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
      conn.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
      return conn.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
      return conn.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
      return conn.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
      conn.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
      conn.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
      return conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
            int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      return conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
      return conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SQLException {
      return conn.prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
      return conn.prepareStatement(sql, columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
      return conn.prepareStatement(sql, columnNames);
    }

    @Override
    public Clob createClob() throws SQLException {
      return conn.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
      return conn.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
      return conn.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
      return conn.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
      return conn.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
      conn.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
      conn.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
      return conn.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
      return conn.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      return conn.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
      return conn.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
      conn.setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException {
      return conn.getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
      conn.abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
      conn.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
      return conn.getNetworkTimeout();
    }

  }
}
