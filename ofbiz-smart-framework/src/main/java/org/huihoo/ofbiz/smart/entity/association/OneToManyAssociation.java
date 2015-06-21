package org.huihoo.ofbiz.smart.entity.association;

/**
 * <p>
 * 一对多关联
 * </p>
 * 
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public class OneToManyAssociation extends Association {
  private static final long serialVersionUID = 1L;
  /** 目标表的外键名称 */
  private final String fkName;
  /**
   * 构造方法
   * 
   * @param source 一对多关联中的一方，即源表
   * @param target 一对多关联中的多方，即目标表
   * @param fkName 多方的外键名称，即目标表的名称
   */
  public OneToManyAssociation(String source, String target, String fkName) {
    super(source, target);
    this.fkName = fkName;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getSource());
    sb.append(" ----------< ");
    sb.append(getTarget());
    sb.append(",type: ");
    sb.append(" has-many");
    return sb.toString();
  }

  public String getFkName() {
    return fkName;
  }


  @Override
  public boolean equals(Object other) {

    if (other == null || !other.getClass().equals(getClass())) {
      return false;
    }

    OneToManyAssociation otherAss = (OneToManyAssociation) other;

    return otherAss.fkName.equals(fkName) 
            && otherAss.getSource().equals(getSource())
            && otherAss.getTarget().equals(getTarget());
  }


}
