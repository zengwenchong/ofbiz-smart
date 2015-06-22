package org.huihoo.ofbiz.smart.base;

import java.util.Properties;

public interface Configuration {

  public Properties getConfig();

  public void refresh();

}
