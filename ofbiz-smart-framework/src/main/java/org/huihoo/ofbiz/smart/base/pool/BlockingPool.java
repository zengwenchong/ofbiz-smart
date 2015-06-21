package org.huihoo.ofbiz.smart.base.pool;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 基于阻塞的对象池接口
 * </p>
 * 
 * @author huangbaihua
 * 
 * @param <T> 池中的对象类型
 */
public interface BlockingPool<T> extends GenericPool<T> {

  /**
   * {@inheritDoc}
   */
  T get();

  /**
   * <p>
   * 从池中获取对象，如果有必要，等待指定时间至对象可用。
   * </p>
   * <p>
   * 该调用为阻塞调用，客户端线程会等待指定时间直到对象可用或超时发生。<br/>
   * 该调用实现了一种公平算法，它确保调用先到先服务(FCFS)。
   * </p>
   * <p>
   * 建议客户端对该调用抛出的<code>InterruptedException</code>有所处理。<br/>
   * 如果在等待对象可用的过程中，线程中断了，当前的实现是将线程的终端状态设置为<code>true</code>，并返回<code>NULL</code>
   * </p>
   * 
   * @param timeout 放弃之前等待的时间长度，以 unit 为时间单位
   * @param unit 确定如何解释 timeout 参数的 {@link TimeUnit}
   * @return 返回的 T 类型的对象
   * @throws InterruptedException
   */
  T get(long timeout, TimeUnit unit) throws InterruptedException;


}
