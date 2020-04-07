package com.example.modulepluginlib.addlog;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created on 2020-04-02.
 * ref: https://juejin.im/post/5cc3db486fb9a03202222154
 */
public class TestMethodClassAdapter extends ClassVisitor implements Opcodes {
  public TestMethodClassAdapter(ClassVisitor classVisitor) {
    super(ASM7, classVisitor);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
    MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
    return (mv == null) ? null : new TestMethodVisitor(mv);
  }
}
