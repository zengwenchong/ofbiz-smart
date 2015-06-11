package org.huihoo.ofbiz.smart.entity.association;

import java.io.Serializable;

/**
 * <p>
 * 表关联类，它定义了表之间的关联关系 
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 */
public class Association implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** 要关联的表的名称 */
  private final String source;

  /** 目标关联表（通常指有外键引用的那张表）的名称。 */
  private final String target;

  
  public Association(String source, String target) {
    this.source = source;
    this.target = target;
  }

  public String getSource() {
    return source;
  }

  public String getTarget() {
    return target;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

}
