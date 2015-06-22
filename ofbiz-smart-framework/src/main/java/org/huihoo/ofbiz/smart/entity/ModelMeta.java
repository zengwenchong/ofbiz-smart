package org.huihoo.ofbiz.smart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.huihoo.ofbiz.smart.C;
import org.huihoo.ofbiz.smart.base.util.CommUtil;
import org.huihoo.ofbiz.smart.entity.annotation.Id;
import org.huihoo.ofbiz.smart.entity.annotation.IdGenerator;
import org.huihoo.ofbiz.smart.entity.annotation.Table;
import org.huihoo.ofbiz.smart.entity.association.Association;


/**
 * <p>
 * {@link Model}的元数据
 * </p>
 * 
 * @author  huangbaihua
 * @version 1.0
 * @since   1.0
 */
public class ModelMeta implements Serializable {
  private static final long serialVersionUID = 1L;
  /** 模型Class */
  private final Class<? extends Model> modelClass;
  /** 未包含ID的属性集 */
  private Set<String> attributeNamesNoId;
  /** 关联关系集合 */
  private List<Association> associations = new ArrayList<>();
  /** 主键名称 */
  private final String idName;
  /** 表名称 */
  private final String tableName;
  /** 数据库类型 */
  private final String dbType;
  /** 数据库名称 */
  private final String dbName;
  /** ID生成器代码 */
  private final String idGeneratorCode;
  

  public ModelMeta(String dbName, Class<? extends Model> modelClass, String dbType) {
    this.modelClass = modelClass;
    this.dbName = dbName;
    this.dbType = dbType;
    this.tableName = getTableName(modelClass);
    this.idName = getIdName(modelClass);
    this.idGeneratorCode = getIdGeneratorCode(modelClass);
  }

  private String getIdName(Class<? extends Model> modelClass) {
    Id idAnno = modelClass.getAnnotation(Id.class);
    return idAnno == null ? "id" : idAnno.value();
  }

  private String getTableName(Class<? extends Model> modelClass) {
    Table tableAnno = modelClass.getAnnotation(Table.class);
    return tableAnno == null ? CommUtil.underscore(modelClass.getSimpleName()) : tableAnno.value();
  }

  private String getIdGeneratorCode(Class<? extends Model> modelClass) {
    IdGenerator idGeneratorAnno = modelClass.getAnnotation(IdGenerator.class);
    return idGeneratorAnno == null ? null : idGeneratorAnno.value();
  }
  
  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("Model["+modelClass+"] meta info:");
    sb.append("dbName="+dbName);
    sb.append(",dbType="+dbType);
    sb.append(",tableName="+tableName);
    sb.append(",idName="+idName);
    sb.append(",idGeneratorCode="+idGeneratorCode);
    sb.append(",attributeNamesNoId="+attributeNamesNoId);
    sb.append(",associations:{");
    for (Association assoc:associations) {
      sb.append(assoc.toString()).append(C.LINE_SEPARATOR);
    }
    sb.append("}");
    return sb.toString();
  }
}
