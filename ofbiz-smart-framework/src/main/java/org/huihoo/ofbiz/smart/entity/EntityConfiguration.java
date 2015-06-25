package org.huihoo.ofbiz.smart.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.huihoo.ofbiz.smart.base.CaseInsensitiveMap;
import org.huihoo.ofbiz.smart.base.Configuration;
import org.huihoo.ofbiz.smart.entity.dialect.Dialect;
import org.huihoo.ofbiz.smart.entity.dialect.H2Dialect;
import org.huihoo.ofbiz.smart.entity.dialect.MSSQLDialect;
import org.huihoo.ofbiz.smart.entity.dialect.MySQLDialect;
import org.huihoo.ofbiz.smart.entity.dialect.OracleDialect;
import org.huihoo.ofbiz.smart.entity.dialect.PostgreSQLDialect;
import org.huihoo.ofbiz.smart.entity.dialect.SQLiteDialect;


public class EntityConfiguration implements Configuration {
  private final Properties properties = new Properties();
  private final Map<String, Dialect> dialects = new CaseInsensitiveMap<>();
  private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
  private DataSourceCreator<String,DataSource> dataSourceCreator = null;
  
  public EntityConfiguration() {
    init();
  }

  @SuppressWarnings("unchecked")
  protected void init(){
    try {
      InputStream in = getClass().getResourceAsStream("/entity.properties");
      if (in != null) {
        properties.load(in);
      }
      
      dataSourceCreator = (DataSourceCreator<String,DataSource>) 
                              Class.forName(properties.getProperty("datasource.creator.class")).newInstance();
      dataSources.putAll(dataSourceCreator.createDataSources(properties));
    } catch (IOException e) {
      throw new EntityException(e);
    } catch (InstantiationException e) {
      throw new EntityException(e);
    } catch (IllegalAccessException e) {
      throw new EntityException(e);
    } catch (ClassNotFoundException e) {
      throw new EntityException(e);
    }
  }
  
  
  
  @Override
  public Properties getConfig() {
    return properties;
  }

  @Override
  public void refresh() {
    // TODO
  }

  public DataSource getDataSource(String name) {
    Set<String> keySet = dataSources.keySet();
    for (String key : keySet) {
      if (key.equals(name)) {
        return dataSources.get(name);
      }
    }
    throw new EntityException("Cannot find datasource : " + name);
  }

  public DataSource getDefaultDataSource() {
    String name = properties.getProperty("datasource.default");
    return getDataSource(name);
  }

  public Dialect getDialect(ModelMeta mm) {
    Dialect dialect = dialects.get(mm.getDbType());
    if (dialect == null) {
      if (mm.getDbType().equalsIgnoreCase("Oracle")) {
        dialect = new OracleDialect();
      } else if (mm.getDbType().equalsIgnoreCase("MySQL")) {
        dialect = new MySQLDialect();
      } else if (mm.getDbType().equalsIgnoreCase("PostgreSQL")) {
        dialect = new PostgreSQLDialect();
      } else if (mm.getDbType().equalsIgnoreCase("h2")) {
        dialect = new H2Dialect();
      } else if (mm.getDbType().equalsIgnoreCase("Microsoft SQL Server")) {
        dialect = new MSSQLDialect();
      } else if (mm.getDbType().equalsIgnoreCase("SQLite")) {
        dialect = new SQLiteDialect();
      } else {
        throw new EntityException("Unkown dialect : " + dialect);
      }
      dialects.put(mm.getDbType(), dialect);
    }
    return dialect;
  }
}
