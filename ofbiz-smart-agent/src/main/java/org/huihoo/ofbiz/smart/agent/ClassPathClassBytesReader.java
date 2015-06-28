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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * Implementation of ClassBytesReader based on URLClassLoader.
 */
public class ClassPathClassBytesReader implements ClassBytesReader {

  private final URL[] urls;

  public ClassPathClassBytesReader(URL[] urls) {
    this.urls = urls == null ? new URL[0] : urls;
  }

  public byte[] getClassBytes(String className, ClassLoader classLoader) {

    URLClassLoader cl = new URLClassLoader(urls, classLoader);

    String resource = className.replace('.', '/') + ".class";

    InputStream is = null;
    try {

      // read the class bytes, and define the class
      URL url = cl.getResource(resource);
      if (url == null) {
        return null;
      }

      is = url.openStream();
      return InputStreamTransform.readBytes(is);

    } catch (IOException e) {
      throw new RuntimeException("IOException reading bytes for " + className, e);

    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          throw new RuntimeException("Error closing InputStream for " + className, e);
        }
      }
      try {
        cl.close();
      } catch (IOException e) {
        throw new RuntimeException("Error closing URLClassLoader for " + className, e);
      }
    }
  }

}
