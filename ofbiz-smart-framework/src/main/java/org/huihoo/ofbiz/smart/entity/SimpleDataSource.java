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
 *  一个简单的数据源实现 
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 */
public class SimpleDataSource implements DataSource {

  private volatile DataSource dataSource = null;

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
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public void commit() throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public void rollback() throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public void close() throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public boolean isClosed() throws SQLException {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public boolean isReadOnly() throws SQLException {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public String getCatalog() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency)
            throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
            int resultSetConcurrency) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public int getHoldability() throws SQLException {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
            int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Clob createClob() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
      // TODO Auto-generated method stub

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
      // TODO Auto-generated method stub

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public String getSchema() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
      // TODO Auto-generated method stub

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
      // TODO Auto-generated method stub
      return 0;
    }

  }
}
