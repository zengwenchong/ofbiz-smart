package org.huihoo.ofbiz.smart.test;

import org.huihoo.ofbiz.smart.entity.EntityConfiguration;
import org.huihoo.ofbiz.smart.entity.ModelRegister;
import org.junit.Assert;
import org.junit.Test;

public class ModelRegisterTest {
  @Test
  public void testInit() {
    EntityConfiguration config = ModelRegister.me().getConfiguration();
    Assert.assertNotNull(config);
  }
}
