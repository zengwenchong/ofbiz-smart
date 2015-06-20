package org.huihoo.ofbiz.smart.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.huihoo.ofbiz.smart.entity.SimpleDataSource;
import org.junit.Assert;
import org.junit.Test;

public class SimpleDataSourceTest {
  
  @Test
  public void testInit(){
      SimpleDataSource dataSource = new SimpleDataSource();
      
      dataSource.setDriverClassName("org.h2.Driver");
      dataSource.setUrl("jdbc:h2:mem:tests;DB_CLOSE_DELAY=-1");
      dataSource.setConnectionProperties("username=sa;password=");
      dataSource.setMaxConnections(10);
      
      Connection conn = null;
      
      try {
        conn = dataSource.getConnection();
        
        Assert.assertNotNull(conn);
        
        conn.createStatement().executeQuery("select 1");
        
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
      
      
      try {
        dataSource.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
  
}
