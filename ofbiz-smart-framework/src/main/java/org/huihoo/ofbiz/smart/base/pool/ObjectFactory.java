package org.huihoo.ofbiz.smart.base.pool;

/**
 * <p>
 * 对象工厂接口.负责对象的创建
 * </p>
 * 
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 * @param <T> 要创建的对象类型
 */
public interface ObjectFactory<T> {
  /**
   * 创建新的对象
   * 
   * @return 创建的对象
   */
  public T createNew();
}
