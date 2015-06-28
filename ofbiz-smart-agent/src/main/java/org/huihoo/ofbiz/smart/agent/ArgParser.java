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

import java.util.HashMap;
/**
 * Helper to parse javaagent or ant string arguments.
 */
public class ArgParser {
  /*
   * Parse the args returning as name value pairs.
   */
  public static HashMap<String, String> parse(String args) {

    HashMap<String, String> map = new HashMap<String, String>();

    if (args != null) {
      String[] split = args.split(";");
      for (String nameValuePair : split) {
        String[] nameValue = nameValuePair.split("=");
        if (nameValue.length == 2) {
          map.put(nameValue[0].toLowerCase(), nameValue[1]);
        }
      }
    }

    return map;
  }
}
