package org.huihoo.ofbiz.smart.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * <p>
 * 实体基础类.
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 */
public abstract class Model implements Externalizable {
  

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    // TODO
    
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    // TODO

  }

}
