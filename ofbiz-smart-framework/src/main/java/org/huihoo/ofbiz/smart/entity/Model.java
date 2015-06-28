package org.huihoo.ofbiz.smart.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.huihoo.ofbiz.smart.base.CaseInsensitiveMap;
import org.huihoo.ofbiz.smart.base.CaseInsensitiveSet;
import org.huihoo.ofbiz.smart.entity.association.Association;

/**
 * <p>
 * 实体基础类.
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 */
public abstract class Model implements Externalizable {
  private final static String tag = Model.class.getName();
  /** 实体的关联集合 */
  private List<Association> associations = new ArrayList<>();
  /** 实体的属性 */
  private Map<String, Object> attributes = new CaseInsensitiveMap<>();
  /** 被操作或修改的属性集合 */
  private final Set<String> modifiedAttributeNames = new CaseInsensitiveSet();
  /** 回调集合 */
  private final List<Callback<Model>> callbacks = new ArrayList<>();
  
  private ModelMeta modelMeta;
  
  
  public Model(){
  }
  
  //===============================================================
  // 1.各种属性的setter和getter
  // 2.属性设置的帮助方法
  //===============================================================
  @SuppressWarnings("unchecked")
  private <T extends Model> T setRaw(String attrName,Object value){
    attributes.put(attrName, value);
    modifiedAttributeNames.add(attrName);
    return (T) this;
  }
  /**
   * 获取主键的值
   * @return  主键的值
   */
  public Object getId(){
    //TODO
    return null;
  }
  /**
   * 获取主键的名称
   * @return 主键的名称
   */
  public String getIdName(){
    return null;
  }
  public Integer getInterger(String attrName){
    return null;
  }
  public Long getLong(String attrName){
    return null;
  }
  public long getLongId(String attrName){
    return 0L;
  }
  public Float getFloat(String attrName){
    return 0F;
  }
  public Double getDouble(String attrName){
    return 0D;
  }
  public Short getShort(String attrName){
    return 0;
  }
  public String getString(String attrName){
    return "";
  }
  public Boolean getBoolean(String attrName){
    return false;
  }
  public byte[] getBytes(String attrName){
    return null;
  }
  public BigDecimal getBigDecimal(String attrName){
    return BigDecimal.ZERO;
  }
  public Date getDate(String attrName){
    return null;
  }
  public Time getTime(String attrName){
    return null;
  }
  public Timestamp getTimeStamp(String attrName){
    return null;
  }
  
  
  public void setId(Object id){
    
  }
  public void setIdName(String idName){
    
  }
  public void setShort(String attrName,Object value){
    
  }
  public void setInteger(String attrName,Object value){
    
  }
  public void setLong(String attrName,Object value){
    
  }
  public void setBoolean(String attrName,Object value){
    
  }
  public void setTime(String attrName,Object value){
    
  }
  public void setTimeStamp(String attrName,Object value){
    
  }
  public void setDate(String attrName,Object value){
    
  }
  public void setBigDecimal(String attrName,Object value){
    
  }
  
  public <T extends Model> T set(Object... namesAndValues) {
    return (T) this;
  }
  /**
   * <p>
   *    获取<code>Model</code>的实现类,该方法要求运行时动态转换原来的<code>Model</code>实现类，在该方法中加入实际的<code>Model</code>实现类
   * </p>
   * <p>
   *    
   * </p>
   * @return  成功转换返回真正的<code>Model</code>实现类;如果未转换，抛出<code>EntityException</code>
   */
  private static <T extends Model> Class<T> modelClass() {
    throw new EntityException("Faild to determine Model class name,models have been instrumented?");
  }
  
  //===============================================================
  //                    Model持久化相关方法
  //===============================================================
  public boolean save(){
    return false;
  }
  
  
  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
  }
  
  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    return sb.toString();
  }
}
