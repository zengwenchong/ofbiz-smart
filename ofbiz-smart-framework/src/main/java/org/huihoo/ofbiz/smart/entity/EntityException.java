package org.huihoo.ofbiz.smart.entity;

import org.huihoo.ofbiz.smart.base.GenericException;

public class EntityException extends GenericException {
  private static final long serialVersionUID = 1L;


  public EntityException() {
    super();
  }

  public EntityException(Throwable nested) {
    super(nested);
  }

  public EntityException(String str) {
    super(str);
  }

  public EntityException(String str, Throwable nested) {
    super(str, nested);
  }
}
