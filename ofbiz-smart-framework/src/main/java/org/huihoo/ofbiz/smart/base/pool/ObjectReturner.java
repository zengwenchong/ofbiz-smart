package org.huihoo.ofbiz.smart.base.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;


/**
 * <p>
 *  该接口定义了对象如何返回到池中
 * </p>
 * <p>
 *  将使用过后的对象重新加到队列中(即重新放回到池中),达到对象重用的目的.
 * </p>
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 * @param <T> 要返回的对象类型
 */
public class ObjectReturner<T> implements Callable<Void> {
  /** 接收返回对象的队列 */
  private final BlockingQueue<T> queue;
  /** 要接收的对象 */
  private final T t;

  public ObjectReturner(BlockingQueue<T> queue, T t) {
    this.queue = queue;
    this.t = t;
  }

  @Override
  public Void call() throws Exception {
    while (true) {
      try {
        queue.put(t);
        break;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    return null;
  }

}
