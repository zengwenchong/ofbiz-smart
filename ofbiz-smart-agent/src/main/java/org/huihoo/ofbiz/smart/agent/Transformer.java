package org.huihoo.ofbiz.smart.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Transformer implements ClassFileTransformer,Opcodes {
  private static final Logger log = LoggerFactory.getLogger(Transformer.class);
  private IgnoreClassHelper ignoreClassHelper;

  public static void premain(String agentArgs, Instrumentation inst) {
    Transformer t = new Transformer("", agentArgs);
    inst.addTransformer(t);
    log.info("premain loading Transformer with args:" + agentArgs);
  }

  public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {
    Transformer t = new Transformer("", agentArgs);
    inst.addTransformer(t);
    log.info("premain loading Transformer with args:" + agentArgs);
  }

  public Transformer(String extraClassPath, String agentArgs) {
    this(parseClassPaths(extraClassPath), agentArgs);
  }

  public Transformer(URL[] extraClassPath, String agentArgs) {
    this(new ClassPathClassBytesReader(extraClassPath), agentArgs);
  }

  public Transformer(ClassBytesReader r, String agentArgs) {
    ignoreClassHelper = new IgnoreClassHelper(agentArgs);
  }

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
          ProtectionDomain protectionDomain, byte[] classfileBuffer)
          throws IllegalClassFormatException {
    try{
      if(ignoreClassHelper.isIgnoreClass(className)){
        log.debug("ingore class : " + className);
        return null;
      }
      ClassWriter cw = new ClassWriter(0);
      ClassVisitor cv = new EnhanceModelAdapter(Opcodes.ASM5, cw);
      ClassReader cr = new ClassReader(classfileBuffer);
      cr.accept(cv, 0);
      
      String parentCName = "org/huihoo/ofbiz/smart/entity/Model";
      String convertCName = className.replaceAll("\\.", "/");
      String cType = "L"+convertCName+";";
      MethodVisitor mv = null;
      mv = cw.visitMethod(ACC_PROTECTED, "modelClass", "()Ljava/lang/Class;", "<T:L"+parentCName+";>()Ljava/lang/Class<TT;>;", null);
      mv.visitCode();
      mv.visitLdcInsn(Type.getType(cType));
      mv.visitInsn(ARETURN);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
      
      return cw.toByteArray();
    }catch(Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public static URL[] parseClassPaths(String extraClassPath) {
    if (extraClassPath == null) {
      return new URL[0];
    }

    String[] stringPaths = extraClassPath.split(";");
    return UrlPathHelper.convertToUrl(stringPaths);
  }

}
