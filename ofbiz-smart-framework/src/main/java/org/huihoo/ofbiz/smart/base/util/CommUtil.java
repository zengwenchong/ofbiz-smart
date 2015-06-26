package org.huihoo.ofbiz.smart.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.huihoo.ofbiz.smart.C;

public class CommUtil {

  public static boolean isNotEmpty(Object value) {
    return !isEmpty(value);
  }

  @SuppressWarnings("unchecked")
  public static boolean isEmpty(Object value) {
    if (value == null) {
      return true;
    }
    if (value instanceof String) {
      return ((String) value).length() == 0;
    }
    if (value instanceof Object[]) {
      return ((Object[]) value).length == 0;
    }
    if (value instanceof Collection) {
      return ((Collection<? extends Object>) value).size() == 0;
    }
    if (value instanceof Map) {
      return ((Map<? extends Object, ? extends Object>) value).size() == 0;
    }
    if (value instanceof CharSequence) {
      return ((CharSequence) value).length() == 0;
    }
    if (value instanceof Boolean) {
      return false;
    }
    if (value instanceof Number) {
      return false;
    }
    if (value instanceof Character) {
      return false;
    }
    if (value instanceof java.util.Date) {
      return false;
    }
    return false;
  }

  /**
   * <p>
   * 将骆驼命名形式的字符串转换成下划线形式字符串
   * </p>
   * <p>
   * 比如： OrderHeader 转换后为: order_header
   * </p>
   * 
   * @param camelString 要转换的骆驼命名形式的字符串
   * @return 转换过后的字符串
   */
  public static String underscore(String camelString) {
    // 1.首先找到字符串中所有大写字母的索引位置并保存
    List<Integer> upperIdx = new ArrayList<Integer>();
    byte[] bytes = camelString.getBytes();
    for (int i = 0; i < bytes.length; i++) {
      byte b = bytes[i];
      if (b >= 65 && b <= 90) {
        upperIdx.add(i);
      }
    }
    // 2.在大写字母之前插入下划线
    StringBuilder b = new StringBuilder(camelString);
    for (int i = upperIdx.size() - 1; i >= 0; i--) {
      Integer index = upperIdx.get(i);
      if (index != 0) {
        b.insert(index, "_");
      }
    }
    // 3.转换成小写并返回
    return b.toString().toLowerCase();
  }


  public static String readContent(InputStream is) {
    StringBuilder sb = new StringBuilder();
    try {
      InputStreamReader isr = new InputStreamReader(is, C.UTF_8);
      BufferedReader br = new BufferedReader(isr);
      String line = br.readLine();
      while (line != null) {
        sb.append(line).append(C.LINE_SEPARATOR);
        line = br.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }
}
