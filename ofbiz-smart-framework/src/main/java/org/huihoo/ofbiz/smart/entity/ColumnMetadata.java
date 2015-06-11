package org.huihoo.ofbiz.smart.entity;

import java.io.Serializable;

/**
 * <p>
 * 表格列的元数据类，包括列的名称，类型，大小等.
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 */
public class ColumnMetadata implements Serializable {
  private static final long serialVersionUID = 1L;

  private final String columnName;
  private final String columnType;
  private final int size;

  public ColumnMetadata(String columnName, String columnType, int size) {
    this.columnName = columnName;
    this.columnType = columnType;
    this.size = size;
  }

  public String getColumnName() {
    return columnName;
  }

  public String getColumnType() {
    return columnType;
  }

  public int getSize() {
    return size;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[columnName=");
    builder.append(columnName);
    builder.append(", columnType=");
    builder.append(columnType);
    builder.append(", size=");
    builder.append(size);
    builder.append("]");
    return builder.toString();
  }


}
