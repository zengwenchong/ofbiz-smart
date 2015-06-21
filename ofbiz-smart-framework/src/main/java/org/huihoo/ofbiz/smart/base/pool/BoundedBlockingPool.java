package org.huihoo.ofbiz.smart.base.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.huihoo.ofbiz.smart.C;
import org.huihoo.ofbiz.smart.base.util.Log;

/**
 * <p>
 * 有界阻塞对象池实现<br/>
 * 有界：即对象池容量是有限的。
 * </p>
 * 
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 * @param <T> 池中的对象类型
 */
public class BoundedBlockingPool<T> extends AbstractPool<T> implements BlockingPool<T> {
  private static final String tag = BoundedBlockingPool.class.getName();
  /** 对象池的大小 */
  private int size;
  /** 对象验证接口 */
  private Validator<T> validator;
  /** 对象创建工厂 */
  private ObjectFactory<T> objectFactory;
  /** 对象池 */
  private BlockingQueue<T> objectPool;
  /** 后台执行线程，负责回收对象 */
  private ExecutorService executor = Executors.newCachedThreadPool();
  /** 对象池是否关闭的标志，默认为<code>false</code> */
  private volatile boolean isShutDown = false;

  /**
   * 构造方法
   * @param size 指定的池大小
   * @param validator 对象验证接口
   * @param objectFactory 对象创建工厂
   */
  public BoundedBlockingPool(int size, Validator<T> validator, ObjectFactory<T> objectFactory) {
    this.size = size;
    this.validator = validator;
    this.objectFactory = objectFactory;
    this.objectPool = new LinkedBlockingDeque<>(size);

    initPool();
  }

  /**
   * 清理池中的资源
   */
  private void clearResources() {
    for (T t : objectPool) {
      validator.postInvalidate(t);
    }
  }
  
  /**
   * 初始化对象池
   */
  private void initPool() {
    for (int i = 0; i < size; i++)
      objectPool.add(objectFactory.createNew());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void shutDown() {
    isShutDown = true;
    executor.shutdown();
    clearResources();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T get() {
    if (isShutDown) {
      Log.w(tag, "The object pool is already shutdown.");
      throw new IllegalStateException("The object pool is already shutdown.");
    }

    T t = null;

    try {
      t = objectPool.take();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    return t;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T get(long timeout, TimeUnit unit) throws InterruptedException {
    if (isShutDown) {
      Log.w(tag, "The object pool is already shutdown.");
      throw new IllegalStateException("The object pool is already shutdown.");
    }

    T t = null;

    try {
      t = objectPool.poll(timeout, unit);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    return t;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleInvalidReturn(T t) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void returnToPool(T t) {
    if (validator.isValid(t)) {
      executor.submit(new ObjectReturner<T>(objectPool, t));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isValid(T t) {
    return validator.isValid(t);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void staticsInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append("Pool Statics Info:").append(C.LINE_SEPARATOR);
    sb.append(String.format("Pool size [%s]",size)).append(C.LINE_SEPARATOR);
    sb.append(String.format("Object factory [%s]",objectFactory)).append(C.LINE_SEPARATOR);
    sb.append(String.format("Object validator [%s]",validator)).append(C.LINE_SEPARATOR);
    sb.append("object in the pool:").append(C.LINE_SEPARATOR);
    for(T t : objectPool){
      sb.append(t.toString()).append(C.LINE_SEPARATOR);
    }
    sb.append(">>>>>>>>>>>>>>>>>>End<<<<<<<<<<<<<<<<<<");
    Log.i(tag, sb.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<T> getAllObject() {
    List<T> objectList = new ArrayList<>();
    if(isShutDown)
      return objectList;
    
    for(T t : objectPool){
      objectList.add(t);
    }
    return objectList;
  }
}
