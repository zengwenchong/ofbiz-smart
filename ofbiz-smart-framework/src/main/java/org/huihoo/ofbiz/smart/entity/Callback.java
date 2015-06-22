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

  void beforeValidation(T m);

  void afterValidation(T m);

  void beforeCreate(T m);

  void afterCreate(T m);

  void beforeSave(T m);

  void afterSave(T m);

  void beforeUpdate(T m);

  void afterUpdate(T m);

  void beforeDelete(T m);

  void afterDelete(T m);
}
