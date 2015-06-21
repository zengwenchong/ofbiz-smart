package org.huihoo.ofbiz.smart.entity;

import java.sql.Connection;
import java.sql.SQLException;

import org.huihoo.ofbiz.smart.base.pool.GenericPool.Validator;

/**
 * <p>
 *  JDBC连接验证类，负责连接有效性的验证。<br/>
 *  如果连接已经失效，则将连接关闭。
 * </p>
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public class JdbcConnectionValidator implements Validator<Connection> {
  /**
   * 验证连接是否有效
   * @param t 待验证的连接
   * @return {@link Boolean} 有效的连接返回<code>true</code>;否则返回<code>false</code>
   */
  @Override
  public boolean isValid(Connection t) {
    if (t == null) return false;
    try {
      return !t.isClosed();
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * 失效连接
   * @param t 待验证的连接
   */
  @Override
  public void postInvalidate(Connection t) {
    try {
      t.close();
    } catch (SQLException ingore) {
    }
  }

}
