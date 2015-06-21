package org.huihoo.ofbiz.smart.base.pool;

import java.util.List;

/**
 * <p>
 * 一般对象池接口 <br/>
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 * @param <T> 池中的对象类型
 */
public interface GenericPool<T> {
  /**
   * <p>
   * 从池中获取对象实例。 <br>
   * 该方法即可以是阻塞的也可以是非阻塞的，具体哪种方式完全取决于接口的实现。
   * </p>
   * <p>
   * 如果是阻塞的调用，当池可用时，会立即返回有效的对象；否则线程会阻塞等待，直到对象变得可用。
   * </p>
   * <p>
   * 如果是非阻塞的调用，不管对象是否可用，都会立即返回。如果对象可用，直接返回；否则返回<code>NULL</code>
   * </p>
   * 
   * @return T
   */
  T get();

  /**
   * 释放对象并将它返回到对象池中
   * 
   * @param t 要释放的对象
   */
  void release(T t);

  /**
   * 关闭对象池
   */
  void shutDown();
  
  /**
   * 获取池中当前所有的对象
   * @return 当有所有在池中的对象集合
   */
  List<T> getAllObject();
  
  /**
   * 池的统计信息
   */
  void staticsInfo();

  /**
   * <p>
   * 池对象验证器接口 它主要负责验证池中对象是否有效可用，如果已经失效，也便于之后对象池执行对象的清理。
   * </p>
   * 
   * @author huangbaihua
   * @version 1.0
   * @since 1.0
   * @param <T>
   */
  public static interface Validator<T> {
    /**
     * 检查对象是否有效可用
     * 
     * @param t 要检查的对象
     * @return 对象有效可用,返回<code>true</code>;否则返回<code>false</code>
     */
    public boolean isValid(T t);

    /**
     * <p>
     * 发布要失效清理的对象。
     * </p>
     * </p> 在正式丢弃对象之前，首先要对它进行清理操作。 </p>
     * <p>
     * 比如：在丢弃数据库连接对象时，对象池应该先关闭数据库连接。而该关闭操作就通过<code>postInvalidate</code>方法来完成。
     * </p>
     * 
     * @param t 要失效清理的对象
     */
    public void postInvalidate(T t);
  }

}
