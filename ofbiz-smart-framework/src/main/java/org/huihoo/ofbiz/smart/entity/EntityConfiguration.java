package org.huihoo.ofbiz.smart.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

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
  private Properties properties = new Properties();
  private Map<String, Dialect> dialects = new CaseInsensitiveMap<>();
  
  public EntityConfiguration() {}

  @Override
  public Properties getConfig() {
    try {
      InputStream in = getClass().getResourceAsStream("/entity.properties");
      if (in != null) {
        properties.load(in);
      }
      return properties;
    } catch (IOException e) {
      throw new EntityException(e);
    }
  }

  @Override
  public void refresh() {}



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
        throw new EntityException("Unkown dialect : "+dialect);
      }
      dialects.put(mm.getDbType(), dialect);
    }
    return dialect;
  }
}
