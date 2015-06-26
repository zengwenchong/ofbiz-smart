package org.huihoo.ofbiz.smart.base.location;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>
 * 资源路径解析接口
 * </p>
 * 
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public interface LocationResolver {
  /**
   * <p>
   * 解析资源的路径
   * </p>
   * 
   * @param location 要解析的路径
   * @return 成功解析返回<code>java.net.URL</code>;否则返回<code>NULL</code>
   * @throws MalformedURLException
   */
  public URL resolveLocation(String location) throws MalformedURLException;
}
