package org.huihoo.ofbiz.smart.entity.validation;

import org.huihoo.ofbiz.smart.entity.Model;

public interface Validator<T extends Model> {
  public void validate(T m);
}
