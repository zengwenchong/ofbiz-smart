package org.huihoo.ofbiz.smart.entity.dialect;

import org.huihoo.ofbiz.smart.entity.ModelMeta;

/**
 * <p>
 * 数据库方言抽象类。
 * </p>
 * <p>
 *  定义了构造符合SQL标准的语句的通用方法。各数据库实现厂商如有不同，需要重写对应的方法。
 * </p>
 * 
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public abstract class Dialect {

  public String selectStarFrom(String table) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM ").append(table);
    return sb.toString();
  }

  public String selectStarFrom(String table, String where) {
    if (where == null) return selectStarFrom(table);

    String s = selectStarFrom(table);
    s += " " + where;
    return s;
  }

  public String selectStarByAnd(String table, String... params) {
    if (params.length == 0) return selectStarFrom(table);

    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM ").append(table);
    sb.append("WHERE ");
    for (int i = 0; i < params.length; i++) {
      String param = params[i];
      sb.append(param);
      sb.append(" = ? ");
      if (i < params.length - 1) sb.append("AND ");
    }
    return sb.toString();
  }

  public String selectCount(String table, String where) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT COUNT(*) FROM ").append(table).append(" ");
    sb.append(where);
    return sb.toString();
  }

  public String insert(ModelMeta modelMeta,boolean includeId) {
    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO ");
    sb.append(modelMeta.getTableName()).append(" ");
    sb.append("(");
    if(includeId){
      sb.append(modelMeta.getIdName()).append(",");
    }
    //TODO 
    modelMeta.getColumnMetadata();
    sb.append(") ");
    sb.append("VALUES ");
    sb.append("(");
    sb.append(")");
    return sb.toString();
  }

  public String update(ModelMeta modelMeta) {
    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO ");
    sb.append(modelMeta.getTableName()).append(" ");
    return sb.toString();
  }
}
