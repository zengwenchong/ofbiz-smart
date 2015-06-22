package org.huihoo.ofbiz.smart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 */
public class ModelMeta implements Serializable {
  private static final long serialVersionUID = 1L;
  /** 模型Class */
  private final Class<? extends Model> modelClass;
  /** 列的元数据集 */
  private Map<String, ColumnMetadata> columnMetadata;
  /** 未包含ID的属性集 */
  private Set<String> attributeNamesNoId;
  /** 关联关系集 */
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



  // ========================================================
  // getter and setter
  // ========================================================
  public Class<? extends Model> getModelClass() {
    return modelClass;
  }

  public Set<String> getAttributeNamesNoId() {
    return attributeNamesNoId;
  }

  public List<Association> getAssociations() {
    return Collections.unmodifiableList(associations);
  }

  public String getIdName() {
    return idName;
  }

  public String getTableName() {
    return tableName;
  }

  public String getDbType() {
    return dbType;
  }

  public String getDbName() {
    return dbName;
  }

  public String getIdGeneratorCode() {
    return idGeneratorCode;
  }

  public void setAttributeNamesNoId(Set<String> attributeNamesNoId) {
    this.attributeNamesNoId = attributeNamesNoId;
  }

  public void setAssociations(List<Association> associations) {
    this.associations = associations;
  }

  public Map<String, ColumnMetadata> getColumnMetadata() {
    if (columnMetadata == null || columnMetadata.isEmpty())
      throw new EntityException("Failed to find table: " + getTableName());
    return Collections.unmodifiableMap(columnMetadata);
  }

  public void setColumnMetadata(Map<String, ColumnMetadata> columnMetadata) {
    this.columnMetadata = columnMetadata;
  }


  public Set<String> getAttributeNames() {
    if (columnMetadata == null || columnMetadata.isEmpty())
      throw new EntityException("Failed to find table: " + getTableName());
    return Collections.unmodifiableSet(columnMetadata.keySet());
  }

  public boolean hasAttribute(String attribute) {
    return columnMetadata != null && columnMetadata.containsKey(attribute);
  }
  
  /**
   * 检查属性或关联是否存在
   * @param attributeOrAssociation 要检查的属性和关联名称
   */
  public void checkAttributeOrAssociation(String attributeOrAssociation) {
    //如果属性不存在，检查是否为关联
    if (!hasAttribute(attributeOrAssociation)) {
      boolean contains = false;
      for (Association association : associations) {
        if (association.getTarget().equalsIgnoreCase(attributeOrAssociation)) {
          contains = true;
          break;
        }
      }
      if(!contains){
        StringBuilder sb = new StringBuilder();
        sb.append("Attribute : '");
        sb.append(attributeOrAssociation);
        sb.append("' is not defined in model: '");
        sb.append(getModelClass()+",");
        sb.append(" and also,did not find an association by the same name,available attributes:");
        sb.append(getAttributeNames());
        throw new IllegalArgumentException(sb.toString());
      }
    }
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ModelMeta[" + modelClass + "]:");
    sb.append("dbName=" + dbName);
    sb.append(",dbType=" + dbType);
    sb.append(",tableName=" + tableName);
    sb.append(",idName=" + idName);
    sb.append(",idGeneratorCode=" + idGeneratorCode);
    sb.append(",attributeNamesNoId=" + attributeNamesNoId);
    sb.append(",associations:{");
    for (Association assoc : associations) {
      sb.append(assoc.toString()).append(C.LINE_SEPARATOR);
    }
    sb.append("}");
    return sb.toString();
  }
}
