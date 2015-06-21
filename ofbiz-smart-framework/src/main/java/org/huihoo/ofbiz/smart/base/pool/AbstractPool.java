package org.huihoo.ofbiz.smart.base.pool;

/**
 * <p>
 *  抽象池接口,它实现了{@link GenericPool}接口。<br/>
 *  它定义了对象池实现方需要额外实现的接口。
 *  <ul>
 *      <li>{@link AbstractPool#isValid(Object)}</li>
 *      <li>{@link AbstractPool#returnToPool(Object)}</li>
 *      <li>{@link AbstractPool#handleInvalidReturn(Object)}</li>
 *  </ul>
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 * @param <T> 池中的对象类型
 */
public abstract class AbstractPool<T> implements GenericPool<T> {
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void release(T t) {
    if (isValid(t)) {
      returnToPool(t);
    } else {
      handleInvalidReturn(t);
    }
  }
  /**
   * <p>
   *    对于要返回池中，无效的对象进行处理
   * </p>
   * @param t 要处理的对象
   */
  protected abstract void handleInvalidReturn(T t);
  
  /**
   * <p>
   *    将对象返回到池中，达到重用对象的目的
   * </p>
   * @param t 要返回池中的对象
   */
  protected abstract void returnToPool(T t);

  /**
   * <p>
   *    验证池中对象的有效性
   * </p>
   * @param t 要验证的池对象
   * @return 如果对象有效可用,返回<code>true</code>;否则，返回<code>false</code>
   */
  protected abstract boolean isValid(T t);
}
