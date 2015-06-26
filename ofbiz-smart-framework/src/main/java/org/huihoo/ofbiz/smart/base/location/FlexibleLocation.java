package org.huihoo.ofbiz.smart.base.location;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.huihoo.ofbiz.smart.base.util.CommUtil;


/**
 * <p>
 *  支持各种类型资源路径解析的解析类
 * </p>
 * <p>
 *  1. http,https,ftp,jar,file等类型用{@link StandardUrlLocationResolver}来解析<br/>
 *  2. classpath类型用<code>{@link ClasspathLocationResolver}</code>来解析
 * </p>
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public class FlexibleLocation {
  public static final String tag = FlexibleLocation.class.getName();
  private static final Map<String, LocationResolver> locationResolvers;
  
  /**
   * 1. 以下协议(http,https,ftp,jar,file)用<code>StandardUrlLocationResolver</code>来解析
   */
  static {
    Map<String, LocationResolver> resolverMap = new HashMap<String, LocationResolver>(8);
    
    LocationResolver standardUrlResolver = new StandardUrlLocationResolver();
    resolverMap.put("http", standardUrlResolver);
    resolverMap.put("https", standardUrlResolver);
    resolverMap.put("ftp", standardUrlResolver);
    resolverMap.put("jar", standardUrlResolver);
    resolverMap.put("file", standardUrlResolver);
    // 类路径解析
    resolverMap.put("classpath", new ClasspathLocationResolver());
    locationResolvers = Collections.unmodifiableMap(resolverMap);
  }

  /**
   * <p>
   * 将指定路径解析成<code>URL</code>对象
   * </p>
   * <p>
   * 路径格式为 : {locationType}://location/path/file.ext
   * </p>
   * 
   * @param location 要解析的资源路径
   * @return 代表资源路径的<code>URL</code>对象
   * @throws MalformedURLException
   */
  public static URL resolveLocation(String location) throws MalformedURLException {
    return resolveLocation(location, null);
  }

  public static URL resolveLocation(String location, ClassLoader loader)
          throws MalformedURLException {
    if (CommUtil.isEmpty(location)) {
      return null;
    }
    String locationType = getLocationType(location);
    LocationResolver resolver = locationResolvers.get(locationType);
    if (resolver != null) {
      if (loader != null && resolver instanceof ClasspathLocationResolver) {
        ClasspathLocationResolver cplResolver = (ClasspathLocationResolver) resolver;
        return cplResolver.resolveLocation(location, loader);
      } else {
        return resolver.resolveLocation(location);
      }
    } else {
      throw new MalformedURLException("Unknown location type: " + locationType);
    }
  }

  /**
   * <p>
   * 根据指定的资源路径获取路径的类型
   * </p>
   * <p>
   * 一般来说：所有的路径类型均在路径中字符:之前,比如:http://1.txt;jar://a.jar等
   * </p>
   * 
   * @param location 指定的资源路径
   * @return 返回路径的类型
   */
  public static String getLocationType(String location) {
    int colonIndex = location.indexOf(":");
    if (colonIndex > 0) {
      return location.substring(0, colonIndex);
    } else {
      return "classpath";
    }
  }

  /**
   * <p>
   * 去掉资源路径中的资源类型字符串
   * </p>
   * 
   * @param location 指定的资源路径
   * @return 去掉资源类型的资源路径
   */
  public static String stripLocationType(String location) {
    if (CommUtil.isEmpty(location)) {
      return "";
    }
    StringBuilder strippedSoFar = new StringBuilder(location);

    int colonIndex = strippedSoFar.indexOf(":");
    if (colonIndex == 0) {// 如果字符串第一个字符即为冒号，删除之
      strippedSoFar.deleteCharAt(0);
    } else if (colonIndex > 0) {// 删除冒号(含)之前的字符串
      strippedSoFar.delete(0, colonIndex + 1);
    }
    // 如果含有 // 则删除第一个 /
    while (strippedSoFar.charAt(0) == '/' && strippedSoFar.charAt(1) == '/') {
      strippedSoFar.deleteCharAt(0);
    }
    return strippedSoFar.toString();
  }

}
