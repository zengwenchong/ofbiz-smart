package org.huihoo.ofbiz.smart.entity.association;

/**
 * <p>
 * 该关联表示 源表(源实体)属于指定的目标表(目标实体)。<br/>
 * 即可以是<b> 一对一关联 </b>中的任意一方，也可以是<b> 多对一关联 </b>中的多方。
 * </p>
 * 
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public class BelongsToAssociation extends Association {
  private static final long serialVersionUID = 1L;
  /** 源表的外键名称 */
  private final String fkName;
  /**
   * 构造方法
   * @param source  属于关联中的源表
   * @param target  属于关联的目标表
   * @param fkName  属于关联中的源表外键名称
   */
  public BelongsToAssociation(String source, String target, String fkName) {
    super(source, target);
    this.fkName = fkName;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null || !other.getClass().equals(getClass())) {
      return false;
    }

    BelongsToAssociation otherAss = (BelongsToAssociation) other;

    return otherAss.fkName.equals(fkName) 
            && otherAss.getSource().equals(getSource())
              && otherAss.getTarget().equals(getTarget());
  }



  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getSource());
    builder.append("  +");
    builder.append(getFkName());
    builder.append("  >----------  ");
    builder.append(getTarget());
    builder.append(", type: ");
    builder.append("belongs-to");
    return builder.toString();
  }



  public String getFkName() {
    return fkName;
  }
}
