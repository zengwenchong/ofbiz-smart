package org.huihoo.ofbiz.smart.base;

import java.util.Map;
import java.util.TreeMap;
/**
 * <p>
 *   不区分大小写的<code>Map</code>.
 * </p>
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 * @param <K>  键的类型
 * @param <V>  值的类型
 */
public class CaseInsensitiveMap<K, V> extends TreeMap<String, V> {
  private static final long serialVersionUID = 1L;

  public CaseInsensitiveMap() {
    super(String.CASE_INSENSITIVE_ORDER);
  }

  public CaseInsensitiveMap(Map<? extends String, V> m) {
    this();
    putAll(m);
  }
}
