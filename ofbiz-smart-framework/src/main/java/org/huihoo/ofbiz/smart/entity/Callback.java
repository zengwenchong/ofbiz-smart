package org.huihoo.ofbiz.smart.entity;

/**
 * <p>
 * 回调接口。它定义了在{@link Model}的生命周期内的特定结点上可调用的方法。
 * </p>
 * <p>
 * 特定结点为：{@link Model}对象的创建，保存，更新，删除，验证，从数据库读取等。
 * </p>
 * 
 * @author huangbaihua
 * 
 * @param <T>
 */
public interface Callback<T extends Model> {

  public void beforeValidation(T m);

  public void afterValidation(T m);

  public void beforeCreate(T m);

  public void afterCreate(T m);

  public void beforeSave(T m);

  public void afterSave(T m);

  public void beforeUpdate(T m);

  public void afterUpdate(T m);

  public void beforeDelete(T m);

  public void afterDelete(T m);
}
