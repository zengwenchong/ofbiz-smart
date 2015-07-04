package org.huihoo.ofbiz.smart.agent;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnhanceModelAdapter extends ClassVisitor {
  private static final Logger log = LoggerFactory.getLogger(EnhanceModelAdapter.class);

  private String className;

  private boolean model;
  private boolean modelAbstract;
  private boolean enhanced;

  public EnhanceModelAdapter(int api, ClassVisitor cv) {
    super(Opcodes.ASM5, cv);
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName,
          String[] interfaces) {
    if ((access & Opcodes.ACC_INTERFACE) != 0) {
      throw new RuntimeException(name + " is an Interface");
    }

    className = name;
    if (Constants.MODEL.equals(superName)) {
      modelAbstract = true;
      model = true;
    }
    // Just only support JDK 1.7+
    super.visit(Opcodes.V1_7, access, name, signature, superName, interfaces);
  }

  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    return super.visitAnnotation(desc, visible);
  }

  @Override
  public void visitAttribute(Attribute attr) {
    super.visitAttribute(attr);
  }

  @Override
  public FieldVisitor visitField(int access, String name, String desc, String signature,
          Object value) {
    log.debug("visitField>" + desc + " " + name);
    return super.visitField(access, name, desc, signature, value);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature,
          String[] exceptions) {
    log.debug("visitMethod>" + desc + " " + name);
    if (Constants.MODEL_CLASS_METHOD_NAME.equals(name)) {
      enhanced = true;
    }
    MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
    return mv;
  }

  public String getClassName() {
    return className;
  }

  public boolean isModel() {
    return model;
  }



  public void setClassName(String className) {
    this.className = className;
  }

  public void setModel(boolean model) {
    this.model = model;
  }



  public boolean isEnhanced() {
    return enhanced;
  }

  public void setEnhanced(boolean enhanced) {
    this.enhanced = enhanced;
  }

  public boolean isModelAbstract() {
    return modelAbstract;
  }

  public void setModelAbstract(boolean modelAbstract) {
    this.modelAbstract = modelAbstract;
  }


}
