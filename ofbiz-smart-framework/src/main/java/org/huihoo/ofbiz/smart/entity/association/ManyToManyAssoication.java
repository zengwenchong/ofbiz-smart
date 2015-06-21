package org.huihoo.ofbiz.smart.entity.association;

/**
 * <p>
 * 多对多关联
 * </p>
 * 
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public class ManyToManyAssoication extends Association {
  private static final long serialVersionUID = 1L;
  /** 源表的外键名称 */
  private final String sourceFkName;
  /** 目标表的外键名称 */
  private final String targetFkName;
  /** 中间表 */
  private final String join;
  /**
   * 构造方法
   * @param source       源表
   * @param target       目标表
   * @param sourceFkName 源表的外键
   * @param targetFkName 目标表的外键
   * @param join         中间表
   */
  public ManyToManyAssoication(String source, String target, String sourceFkName,
          String targetFkName, String join) {
    super(source, target);
    this.sourceFkName = sourceFkName;
    this.targetFkName = targetFkName;
    this.join = join;
  }
  
  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append(getSource());
    sb.append("  >----------<  ");
    sb.append(getTarget());
    sb.append(", type: ");
    sb.append("many-to-many");
    sb.append(", join:");
    sb.append(join);
    return sb.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (other == null || !other.getClass().equals(getClass())) {
      return false;
    }

    ManyToManyAssoication otherAss = (ManyToManyAssoication) other;

    return otherAss.getSource().equalsIgnoreCase(getSource())
            && otherAss.getTarget().equalsIgnoreCase(getTarget())
            && otherAss.getSourceFkName().equalsIgnoreCase(getSourceFkName())
            && otherAss.getTargetFkName().equalsIgnoreCase(getTargetFkName())
            && otherAss.getJoin().equalsIgnoreCase(getJoin());
  }

  public String getSourceFkName() {
    return sourceFkName;
  }

  public String getTargetFkName() {
    return targetFkName;
  }

  public String getJoin() {
    return join;
  }
}
