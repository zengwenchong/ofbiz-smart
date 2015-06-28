/**
 * EbeanORM
 * 
 * Copyright 2012 Authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.huihoo.ofbiz.smart.agent;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UrlPathHelper {
  private static final String PROTOCAL_PREFIX = "file:";
  

  /**
   * Convert string paths into URL class paths.
   */
  public static URL[] convertToUrl(String[] paths){
      ArrayList<URL> list = new ArrayList<URL>();
      for (int i = 0; i < paths.length; i++) {
          URL url = convertToUrl(paths[i]);
          if (url != null){
              list.add(url);
          }
      }
      return list.toArray(new URL[list.size()]);
  }
  
  /**
   * Convert string path into URL class path.
   */
  public static URL convertToUrl(String path) {
      if (isEmpty(path)){
          return null;
      }
      try {
          return new URL(PROTOCAL_PREFIX + convertUrlString(path));
      } catch (MalformedURLException e) {
          throw new RuntimeException(e);
      }
  }
  
  /**
   * Convert a string path to be used in URL class path entry.
   */
  public static String convertUrlString(String classpath) {
      
      if (isEmpty(classpath)) {
          return "";
      }
      
      classpath = classpath.trim();
      if (classpath.length() < 2){
          return "";
      }
      if (classpath.charAt(0) != '/' && classpath.charAt(1) == ':'){
          // add leading slash for windows platform
          // assuming drive letter path
          classpath = "/"+classpath;
      }
      if (!classpath.endsWith("/")) {
          File file = new File(classpath);
          if (file.exists() && file.isDirectory()) {
              classpath = classpath.concat("/");
          }
      }
      return classpath;
  }
  
  private static boolean isEmpty(String s){
      return s == null || s.trim().length() ==0;
  }
}
