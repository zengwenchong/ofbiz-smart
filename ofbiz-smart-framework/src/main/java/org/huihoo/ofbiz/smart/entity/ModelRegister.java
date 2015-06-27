package org.huihoo.ofbiz.smart.entity;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.huihoo.ofbiz.smart.C;
import org.huihoo.ofbiz.smart.base.CaseInsensitiveMap;
import org.huihoo.ofbiz.smart.base.location.FlexibleLocation;
import org.huihoo.ofbiz.smart.base.util.CommUtil;
import org.huihoo.ofbiz.smart.base.util.Log;
import org.huihoo.ofbiz.smart.entity.annotation.Table;

public class ModelRegister implements C {
  private static final String tag = ModelRegister.class.getName();
  private static final ModelRegister INSTANCE = new ModelRegister();
  private final EntityConfiguration configuration = new EntityConfiguration();
  private final List<String> modelClassNames = new ArrayList<String>();
  private final Map<String, List<Class<? extends Model>>> modelClassesMap = new ConcurrentHashMap<>();
  private final Map<String,ModelMeta> modelMetaTableMap = new CaseInsensitiveMap<>();
  private final Map<Class<? extends Model>,ModelMeta> modelMetaClassMap = new ConcurrentHashMap<>();
  
  private ModelRegister() {
    init();
  }


  public static ModelRegister me() {
    return INSTANCE;
  }


  public EntityConfiguration getConfiguration() {
    return configuration;
  }

  public ModelMeta getModelMeta(String table){
    return modelMetaTableMap.get(table);
  }
  
  public ModelMeta getModelMeta(Class<? extends Model> modelClass){
    return modelMetaClassMap.get(modelClass);
  }
  
  public String getTableName(Class<? extends Model> modelClass){
    ModelMeta mm = getModelMeta(modelClass);
    return mm == null ? null : mm.getTableName();
  }

  protected synchronized void init() {
    Map<String, DataSource> dataSources = configuration.getDataSources();
    Set<Entry<String, DataSource>> entrySet = dataSources.entrySet();
    Iterator<Entry<String, DataSource>> iter = entrySet.iterator();
    while (iter.hasNext()) {
      Entry<String, DataSource> it = iter.next();
      String dataSourceName = it.getKey();
      Log.i(tag, "Mapping models from datasource : " + dataSourceName);
      DataSource ds = it.getValue();
      Connection con = null;
      try {
        con = ds.getConnection();
        if (con == null) {
          Log.w(tag, "Cannot get connection from datasource : " + dataSourceName);
          continue;
        }

        DatabaseMetaData databaseMetaData = con.getMetaData();
        String databaseProductName = con.getMetaData().getDatabaseProductName();
        loadModels(dataSourceName, databaseProductName);
        
        registerModels(dataSourceName, getModelClasses(dataSourceName), databaseProductName);
        
        for (String className : modelClassNames) {
          Class<? extends Model> mClazz = getModelClass(dataSourceName, className);
          if(mClazz == null){
            throw new EntityException("Cannot find model class :" + className);
          }
          Table tableAnno = mClazz.getAnnotation(Table.class);
          String table = null;
          if(tableAnno == null){
            String classSimpleName = className.substring(className.lastIndexOf(".") + 1);
            table = CommUtil.underscore(classSimpleName);
          }else{
            table = tableAnno.value();
          }
          Map<String, ColumnMetadata> colMeta = buildColumnMeta(databaseMetaData, table);
          Log.d(tag, colMeta + "");
        }

      } catch (SQLException e) {
        Log.w(tag, "Cannot get connection from datasource : " + it.getKey());
      } finally {
        if (con != null) {
          try {
            con.close();
          } catch (SQLException e) {}
        }
      }
    }
  }
  
  private List<Class<? extends Model>> getModelClasses(String dataSourceName){
    return  modelClassesMap.get(dataSourceName);
  }
  
  private Class<? extends Model> getModelClass(String dataSourceName,String className){
    List<Class<? extends Model>> modelClasses = getModelClasses(dataSourceName);
    if(modelClasses != null){
      for (Class<? extends Model> clazz : modelClasses) {
        if(clazz.getName().equals(className)){
          return clazz;
        }
      }
    }
    return null;
  }

  private Map<String, ColumnMetadata> buildColumnMeta(DatabaseMetaData databaseMetaData,
          String table) throws SQLException {
    String[] names = table.split("\\.", 3);
    String schema = null;
    String tableName;
    switch (names.length) {
      case 1:
        tableName = names[0];
        break;
      case 2:
        schema = names[0];
        tableName = names[1];
        if (CommUtil.isEmpty(schema) || CommUtil.isEmpty(tableName)) {
          throw new EntityException("Invalid table name : " + table);
        }
        break;
      default:
        throw new EntityException("Invalid table name : " + table);
    }

    ResultSet rs = databaseMetaData.getColumns(null, schema, tableName, null);
    String dbProduct = databaseMetaData.getDatabaseProductName().toLowerCase();
    Map<String, ColumnMetadata> columns = getColumns(rs, dbProduct);
    rs.close();
    // 如果表格列元数据为空，再试一下表名大写，Oracle表名用的大写
    if (CommUtil.isEmpty(columns)) {
      rs = databaseMetaData.getColumns(null, schema, tableName.toUpperCase(), null);
      columns = getColumns(rs, dbProduct);
      rs.close();
    }
    // 如果还是为空，再试一下表名小写
    if (CommUtil.isEmpty(columns)) {
      rs = databaseMetaData.getColumns(null, schema, tableName.toLowerCase(), null);
      columns = getColumns(rs, dbProduct);
      rs.close();
    }

    if (CommUtil.isNotEmpty(columns)) {
      Log.i(tag, "Get metadata from table :" + table);
    } else {
      Log.w(tag, "Failed to get metadata from table :" + table);
    }

    return columns;
  }

  private Map<String, ColumnMetadata> getColumns(ResultSet rs, String dbProduct)
          throws SQLException {
    Map<String, ColumnMetadata> columns = new CaseInsensitiveMap<>();
    while (rs.next()) {
      if ("h2".equals(dbProduct) && "INFORMATION_SCHEMA".equals(rs.getString("TABLE_SCHEM"))) {
        continue; // skip h2 INFORMATION_SCHEMA table columns.
      }
      String colName = rs.getString("TYPE_NAME");
      String typeName = rs.getString("COLUMN_NAME");
      int colSize = rs.getInt("COLUMN_SIZE");
      ColumnMetadata cm = new ColumnMetadata(colName, typeName,colSize);
      columns.put(cm.getColumnName(), cm);
    }
    return columns;
  }

  @SuppressWarnings("unchecked")
  private void loadModels(String dataSourceName, String databaseProductName) {
    try {
      String modelBasePkg = configuration.getConfig().getProperty(MODEL_BASE_PACKAGE_NAME);
      if (CommUtil.isNotEmpty(modelBasePkg)) {
        URL pkgUrl = FlexibleLocation.resolveLocation(modelBasePkg.replaceAll("\\.", "/"));
        if (pkgUrl == null) {
          Log.w(tag, "Model base package path is not exists.");
          return;
        }
        File classesDir = new File(pkgUrl.getFile());
        if (classesDir.exists()) {
          Log.i(tag, "Model classes file path : " + classesDir + " exists.");
        }

        File files[] = classesDir.listFiles(new FilenameFilter() {
          public boolean accept(File dir, String name) {
            return name.endsWith(".class") && name.indexOf("package-info") == -1;
          }
        });

        if (files != null) {
          List<Class<? extends Model>> modelClasses = new ArrayList<>();
          for (File file : files) {
            int current = classesDir.getCanonicalPath().length();
            String fileName = file.getCanonicalPath().substring(++current);
            String classSimpleName =
                    fileName.replace(File.separatorChar, '.').substring(0, fileName.length() - 6);
            String className = modelBasePkg + "." + classSimpleName;
            Log.i(tag, "Model class found : " + className);
            modelClasses.add((Class<? extends Model>) Class.forName(className));
            modelClassNames.add(className);
          }
          modelClassesMap.put(dataSourceName, modelClasses);
        }
      }

      String modelClassesFile = configuration.getConfig().getProperty(MODEL_CLASSES_FILE_NAME);
      if (CommUtil.isNotEmpty(modelClassesFile)) {
        String[] classesFileToken = modelClassesFile.split(";");
        for (String f : classesFileToken) {
          URL fUrl = FlexibleLocation.resolveLocation(f);
          if (fUrl != null) {
            // TODO
          }
        }
      }
    } catch (MalformedURLException e) {
      Log.e(e, tag, e.getMessage());
    } catch (IOException e) {
      Log.e(e, tag, e.getMessage());
    } catch (ClassNotFoundException e) {
      Log.e(e, tag, e.getMessage());
    }
  }

  private void addModelMeta(ModelMeta mm,Class<? extends Model> modelClass){
    modelMetaClassMap.put(modelClass, mm);
    modelMetaTableMap.put(mm.getTableName(), mm);
  }
  
  private void registerModels(String dataSourceName,List<Class<? extends Model>> modelClasses, String dbType){
    for (Class<? extends Model> modelClass : modelClasses) {
      ModelMeta mm = new ModelMeta(dataSourceName, modelClass, dbType);
      addModelMeta(mm, modelClass);
      Log.i(tag, "Registered model : "+modelClass);
    }
  }

}
