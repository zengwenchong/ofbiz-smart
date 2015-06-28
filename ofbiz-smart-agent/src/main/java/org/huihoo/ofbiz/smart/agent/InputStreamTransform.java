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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.instrument.IllegalClassFormatException;



public class InputStreamTransform {
  private final Transformer transformer;
  private final ClassLoader classLoader;

  public InputStreamTransform(Transformer transformer, ClassLoader classLoader) {
    this.transformer = transformer;
    this.classLoader = classLoader;
  }



  /**
   * Transform a file.
   */
  public byte[] transform(String className, File file) throws IOException,
          IllegalClassFormatException {
    try {
      return transform(className, new FileInputStream(file));

    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Transform a input stream.
   */
  public byte[] transform(String className, InputStream is) throws IOException,
          IllegalClassFormatException {

    try {

      byte[] classBytes = readBytes(is);

      return transformer.transform(classLoader, className, null, null, classBytes);

    } finally {
      if (is != null) {
        is.close();
      }
    }
  }

  /**
   * Helper method to write bytes to a file.
   */
  public static void writeBytes(byte[] bytes, File file) throws IOException {
    writeBytes(bytes, new FileOutputStream(file));
  }

  /**
   * Helper method to write bytes to a OutputStream.
   */
  public static void writeBytes(byte[] bytes, OutputStream os) throws IOException {

    BufferedOutputStream bos = new BufferedOutputStream(os);

    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

    byte[] buf = new byte[1028];

    int len = 0;
    while ((len = bis.read(buf, 0, buf.length)) > -1) {
      bos.write(buf, 0, len);
    }

    bos.flush();
    bos.close();

    bis.close();
  }


  public static byte[] readBytes(InputStream is) throws IOException {

    BufferedInputStream bis = new BufferedInputStream(is);

    ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);

    byte[] buf = new byte[1028];

    int len = 0;
    while ((len = bis.read(buf, 0, buf.length)) > -1) {
      baos.write(buf, 0, len);
    }
    baos.flush();
    baos.close();
    return baos.toByteArray();
  }
}
