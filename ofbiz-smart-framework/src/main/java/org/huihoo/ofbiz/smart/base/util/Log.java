package org.huihoo.ofbiz.smart.base.util;

import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 日志帮助类
 * </p>
 * 
 * @author huangbaihua
 * @version 1.0
 * @since 1.0
 */
public final class Log {

  public static final Object[] emptyArgs = new Object[0];
  
  public static final int DEBUG = 0;
  public static final int TRACE = 1;
  public static final int INFO = 2;
  public static final int WARN = 3;
  public static final int ERROR = 4;
  
  
  
  public static void t(String tag,String msg){
    log(TRACE, null, tag, msg, emptyArgs);
  }
  
  public static void t(String tag,String formatter,Object... args){
    log(TRACE, null, tag, formatter, args);
  }
  
  public static void d(String tag,String msg){
    log(DEBUG, null, tag, msg, emptyArgs);
  }
  
  public static void d(String tag,String formatter,Object... args){
    log(DEBUG, null, tag, formatter, args);
  }
  
  public static void i(String tag,String msg){
    log(INFO, null, tag, msg, emptyArgs);
  }
  
  public static void i(String tag,String formatter,Object... args){
    log(INFO, null, tag, formatter, args);
  }
  
  public static void w(String tag,String msg){
    log(WARN, null, tag, msg, emptyArgs);
  }
  
  public static void w(String tag,String formatter,Object... args){
    log(WARN, null, tag, formatter, args);
  }
  
  public static void e(Throwable t,String tag,String msg){
    log(ERROR, t, tag, msg, emptyArgs);
  }
  
  public static void e(Throwable t,String tag,String formatter,Object... args){
    log(ERROR, t, tag, formatter, args);
  }

  protected static void log(int level, Throwable t, String tag, String msg, Object... args) {
    if (msg != null && args.length > 0) {
      StringBuilder sb = new StringBuilder();
      Formatter ft = new Formatter(sb);
      ft.format(msg, args);
      msg = ft.toString();
      ft.close();
    }

    Logger logger = LoggerFactory.getLogger(tag);

    switch (level) {
      case DEBUG:
        if(logger.isDebugEnabled()){
          if (t != null)
            logger.debug(msg, t);
          else
            logger.debug(msg);
        }
        break;
      case TRACE:
        if(logger.isTraceEnabled()){
          if (t != null)
            logger.trace(msg, t);
          else
            logger.trace(msg);
        }
        break;
      case INFO:
        if (t != null)
          logger.info(msg, t);
        else
          logger.info(msg);
        break;
      case WARN:
        if (t != null)
          logger.warn(msg, t);
        else
          logger.warn(msg);
        break;
      case ERROR:
        if (t != null)
          logger.error(msg, t);
        else
          logger.error(msg);
        break;
    }

  }

}
