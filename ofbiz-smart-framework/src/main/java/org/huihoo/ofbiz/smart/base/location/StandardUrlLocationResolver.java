package org.huihoo.ofbiz.smart.base.location;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>
 * 标准资源路径解析器。
 * </p>
 * <p>
 * 它主要利用<code>java.net.URL</code>对象将位置构造为标准的<code>URL</code>实例
 * </p>
 * 
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public class StandardUrlLocationResolver implements LocationResolver {

  @Override
  public URL resolveLocation(String location) throws MalformedURLException {
    return new URL(location);
  }

}
