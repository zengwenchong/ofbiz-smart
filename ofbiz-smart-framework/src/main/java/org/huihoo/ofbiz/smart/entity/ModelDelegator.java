package org.huihoo.ofbiz.smart.entity;

import org.huihoo.ofbiz.smart.base.util.Log;

public class ModelDelegator {
  private static final String tag = ModelDelegator.class.getName();

  public static <T extends Model> T create(Class<T> clazz, Object... namesAndValues) {
    try {
      T model = clazz.newInstance().set(namesAndValues);
      model.save();
      return model;
    } catch (InstantiationException e) {
      Log.e(e, tag, "ModelDelegator.create() exception :" + e.getMessage());
      throw new EntityException(e);
    } catch (IllegalAccessException e) {
      Log.e(e, tag, "ModelDelegator.create() exception :" + e.getMessage());
      throw new EntityException(e);
    }
  }
}
