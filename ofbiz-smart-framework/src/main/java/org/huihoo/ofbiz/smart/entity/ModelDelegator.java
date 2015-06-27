package org.huihoo.ofbiz.smart.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.huihoo.ofbiz.smart.base.util.Log;

public class ModelDelegator {
  private static final String tag = ModelDelegator.class.getName();

  public static <T extends Model> T create(Class<T> clazz, Object... namesAndValues) {
    try {
      Constructor<T> c = clazz.getDeclaredConstructor();
      c.setAccessible(true);
      T model = c.newInstance(clazz).set(namesAndValues);
      model.save();
      return model;
    } catch (InstantiationException e) {
      Log.e(e, tag, e.getMessage());
      throw new EntityException(e);
    } catch (IllegalAccessException e) {
      Log.e(e, tag, e.getMessage());
      throw new EntityException(e);
    } catch (IllegalArgumentException e) {
      Log.e(e, tag, e.getMessage());
      throw new EntityException(e);
    } catch (InvocationTargetException e) {
      Log.e(e, tag, e.getMessage());
      throw new EntityException(e);
    } catch (NoSuchMethodException e) {
      Log.e(e, tag, e.getMessage());
      throw new EntityException(e);
    } catch (SecurityException e) {
      Log.e(e, tag, e.getMessage());
      throw new EntityException(e);
    }
  }
}
