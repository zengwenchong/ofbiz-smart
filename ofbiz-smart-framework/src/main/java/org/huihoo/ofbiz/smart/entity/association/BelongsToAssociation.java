package org.huihoo.ofbiz.smart.entity.association;

/**
 * <p>
 * 该关联表示 源表(源实体)属于指定的目标表(目标实体).
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 */
public class BelongsToAssociation extends Association {
  private static final long serialVersionUID = 1L;

  /** 源表的外键名称 */
  private final String fkName;

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

    return otherAss.fkName.equals(fkName) && otherAss.getSource().equals(getSource())
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
