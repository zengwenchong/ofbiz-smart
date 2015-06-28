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



  public EnhanceModelAdapter(int api, ClassVisitor cv) {
    super(Opcodes.ASM5, cv);
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName,
          String[] interfaces) {
    log.debug("visit>" + ((access & Opcodes.ACC_INTERFACE)) + " " + version + " " + access + " "
            + superName + " " + name);
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
    return super.visitMethod(access, name, desc, signature, exceptions);
  }
}
