package org.huihoo.ofbiz.smart.entity;

import java.sql.Connection;
import java.sql.SQLException;

import org.huihoo.ofbiz.smart.base.pool.GenericPool.Validator;

public class JdbcConnectionValidator implements Validator<Connection> {

  @Override
  public boolean isValid(Connection t) {
    if (t == null) return false;
    try {
      return !t.isClosed();
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  public void postInvalidate(Connection t) {
    try {
      t.close();
    } catch (SQLException ingore) {

    }
  }

}
