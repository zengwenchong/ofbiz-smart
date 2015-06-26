package org.huihoo.ofbiz.smart.base.location;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>
 * 类型为类路径的资源解析类
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 */
public class ClasspathLocationResolver implements LocationResolver {

  @Override
  public URL resolveLocation(String location) throws MalformedURLException {
    return resolveLocation(location, null);
  }


  public URL resolveLocation(String location, ClassLoader loader) {
    // 根据资源路径获取 资源的跟路径
    String baseLocation = FlexibleLocation.stripLocationType(location);
    if (baseLocation.charAt(0) == '/') {
      baseLocation = baseLocation.substring(1);
    }
    return fromResource(baseLocation, loader);
  }



  public static URL fromResource(String resourceName, ClassLoader loader) {
    // 如果loader为空，loader指定为当前线程的ClassLoader
    if (loader == null) {
      try {
        loader = Thread.currentThread().getContextClassLoader();
      } catch (SecurityException e) {
        ClasspathLocationResolver clr = new ClasspathLocationResolver();
        loader = clr.getClass().getClassLoader();
      }
    }
    // 第一次尝试获取一般资源，如果获取成功，直接返回。
    URL url = loader.getResource(resourceName);
    if (url != null) {
      return url;
    }
    // 第二次尝试获取属性配置文件，如果获取成功，直接返回。
    String propertiesResourceName = null;
    if (!resourceName.endsWith(".properties")) {
      propertiesResourceName = resourceName.concat(".properties");
      url = loader.getResource(propertiesResourceName);
      if (url != null) {
        return url;
      }
    }
    // 第三次尝试获取系统类路径中的资源文件，如果获取成功，直接返回。
    url = ClassLoader.getSystemResource(resourceName);
    if (url != null) {
      return url;
    }
    // 第四次尝试获取系统类路径中的属性配置文件，如果获取成功，直接返回。
    if (propertiesResourceName != null) {
      url = ClassLoader.getSystemResource(propertiesResourceName);
      if (url != null) {
        return url;
      }
    }
    // 第五次尝试从本地文件中获取，如果获取成功，直接返回。
    url = fromFilename(resourceName);
    if (url != null) {
      return url;
    }
    // 最后一次尝试从指定的Url中获取，如果获取成功，返回。
    url = fromUrlString(resourceName);
    return url;
  }

  /**
   * <p>
   * 从本地文件中构造<code>URL</code>对象
   * </p>
   * 
   * @param filename 指定的本地文件名
   * @return 成功获取，返回<code>URL</code>;否则返回<code>NULL</code>
   */
  public static URL fromFilename(String filename) {
    if (filename == null) return null;
    File file = new File(filename);
    URL url = null;

    try {
      if (file.exists()) url = file.toURI().toURL();
    } catch (java.net.MalformedURLException e) {
      e.printStackTrace();
      url = null;
    }
    return url;
  }

  /**
   * <p>
   * 从指定的Url字符串构造<code>URL</code>对象
   * </p>
   * 
   * @param filename 指定的本地文件名
   * @return 成功获取，返回<code>URL</code>;否则返回<code>NULL</code>
   */
  public static URL fromUrlString(String urlString) {
    URL url = null;
    try {
      url = new URL(urlString);
    } catch (MalformedURLException e) {}

    return url;
  }
}
