package org.huihoo.ofbiz.smart.entity;

public class ModelRegister {
  private final static String tag = ModelRegister.class.getName();
  private final static ModelRegister INSTANCE = new ModelRegister();
  private final EntityConfiguration configuration = new EntityConfiguration();


  private ModelRegister() {

  }


  public static ModelRegister me() {
    return INSTANCE;
  }


  public EntityConfiguration getConfiguration() {
    return configuration;
  }
}
