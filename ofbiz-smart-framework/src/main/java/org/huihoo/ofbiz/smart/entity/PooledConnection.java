package org.huihoo.ofbiz.smart.entity;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.huihoo.ofbiz.smart.base.pool.GenericPool;
import org.huihoo.ofbiz.smart.base.util.Log;

public class PooledConnection<C extends Connection> implements Connection {
  private static final String tag = PooledConnection.class.getName();
  private volatile C conn = null;
  private volatile boolean closed = false;
  private volatile GenericPool<Connection> pool;

  public PooledConnection(C c, GenericPool<Connection> pool) {
    super();
    this.conn = c;
    this.pool = pool;
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
  public synchronized void close() throws SQLException {
    boolean isClosedInternal = conn.isClosed();
    if(isClosedInternal){
      return ;
    }
    
    if (!closed) {
      try{
        pool.release(conn);
      }finally{
        //FIXME ??? 释放连接的时间，是否有必要设置自动提交事务 ???
        closed = true;
      }
    }
  }

  @Override
  public boolean isClosed() throws SQLException {
    boolean isClosedInternal = conn.isClosed();
    if(isClosedInternal){
      Log.i(tag, "Internal connection is closed.");
      return true;
    }
    
    if(closed){
      //FIXME 保险起见，再调用一次？
      close();
      Log.i(tag, "Internal connection [%s] is returned to pool.",toString());
      return true;
    }
    
    return false;
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
  public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
    return conn.createStatement(resultSetType, resultSetConcurrency);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
          throws SQLException {
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
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
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
  
  @Override
  public String toString(){
    String s = null;
    
    if(conn != null){
      try{
        if(conn.isClosed()){
          s = "connection is closed";
        }else{
          StringBuilder sb = new StringBuilder();
          sb.append(hashCode());
          DatabaseMetaData meta = conn.getMetaData();
          if(meta != null){
            sb.append(",URL=");
            sb.append(meta.getURL());
            sb.append(",Username=");
            sb.append(meta.getUserName());
            sb.append(",");
            sb.append(meta.getDriverName());
            s = sb.toString();
          }
        }
      }catch(SQLException ingore){
        
      }
    }
    
    if(s == null){
      s = super.toString();
    }
      
    return s;
  }
  
}
