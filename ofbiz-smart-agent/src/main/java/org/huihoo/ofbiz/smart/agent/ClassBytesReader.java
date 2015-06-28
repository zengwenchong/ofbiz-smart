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
/**
 * Interface for reading class bytes as part of enhancement.
 * <p>
 * Used to parse inheritance objects when enhancing a given class.
 * </p>
 */
public interface ClassBytesReader {
  /**
   * Return the class bytes for a given class.
   */
  public byte[] getClassBytes(String className, ClassLoader classLoader);
}
