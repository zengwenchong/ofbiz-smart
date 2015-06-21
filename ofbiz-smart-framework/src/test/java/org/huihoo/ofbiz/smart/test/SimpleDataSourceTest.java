package org.huihoo.ofbiz.smart.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import org.huihoo.ofbiz.smart.base.util.Log;
import org.huihoo.ofbiz.smart.entity.SimpleDataSource;
import org.junit.Assert;
import org.junit.Test;

public class SimpleDataSourceTest {  
  private final static String tag = SimpleDataSourceTest.class.getName();
  
  @Test
  public void testIt() throws InterruptedException{
      SimpleDataSource dataSource = new SimpleDataSource();
      dataSource.setDriverClassName("org.h2.Driver");
      dataSource.setUrl("jdbc:h2:mem:tests;DB_CLOSE_DELAY=-1");
      dataSource.setUserName("sa");
      dataSource.setPassword("");
      dataSource.setMaxConnections(10);
      
      Connection conn = null;
      try {
        conn = dataSource.getConnection();        
        Assert.assertNotNull(conn);        
        Statement stmt = conn.createStatement();
        
        stmt.executeUpdate("CREATE TABLE TEST_MEM(ID INT PRIMARY KEY,NAME VARCHAR(255));");        
        stmt.executeUpdate("INSERT INTO TEST_MEM(ID,NAME) VALUES(1000,'Peter.Huangbaihua');");
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM TEST_MEM");
        while(rs.next()){
          Log.i(tag, ""+rs.getLong(1));
        }
        
        boolean isClosed = conn.isClosed();
        Assert.assertEquals(false, isClosed);
        
        List<Connection> conns = dataSource.getAllActiveConns();
        Log.i(tag, "当前正在使用的连接，是不可用的。所以当前连接数量应该是"+(dataSource.getMaxConnections()-1));
        Assert.assertEquals(dataSource.getMaxConnections()-1, conns.size());
        dataSource.info(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } finally{
        if(conn != null){
          try {
            conn.close();
          } catch (SQLException e) {
          }
        }
      }
      
      //注意： 连接关闭后，连接对象并不是同步就返回到数据源中，此处休眠一下，确保连接对象已经返回到数据源中了。
      Thread.sleep(300);
      
      List<Connection> conns = dataSource.getAllActiveConns();
      Log.i(tag, "当前正在使用的连接，已经关闭，返回到数据源中。所以当前连接数量应该是"+(dataSource.getMaxConnections()));
      Assert.assertEquals(dataSource.getMaxConnections(), conns.size());
      dataSource.info();      
      try {
        boolean isClosed = conn.isClosed();
        Assert.assertEquals(true, isClosed);
        dataSource.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
}
