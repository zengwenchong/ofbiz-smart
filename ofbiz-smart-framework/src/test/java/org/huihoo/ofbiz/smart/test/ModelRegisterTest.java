package org.huihoo.ofbiz.smart.test;

import org.avaje.agentloader.AgentLoader;
import org.huihoo.ofbiz.smart.entity.EntityConfiguration;
import org.huihoo.ofbiz.smart.entity.ModelRegister;
import org.huihoo.ofbiz.smart.test.model.Customer;
import org.junit.Assert;
import org.junit.Test;

public class ModelRegisterTest {
  @Test
  public void testInit() {
    AgentLoader.loadAgentFromClasspath("ofbiz-smart-agent","debug=1;packages=org.huihoo.ofbiz.smart.test.model.**");
    EntityConfiguration config = ModelRegister.me().getConfiguration();
    Assert.assertNotNull(config);
    Customer customer = new Customer();
    customer.save();
  }
}
