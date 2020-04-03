package com.example.modulepluginlib;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created on 2020-04-02.
 * ref: https://juejin.im/post/5cc3db486fb9a03202222154
 */
class TestMethodVisitor extends MethodVisitor {
  public TestMethodVisitor(MethodVisitor methodVisitor) {
    super(ASM7, methodVisitor);
  }

  @Override
  public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
    System.out.println("== TestMethodVisitor, owner = " + owner + ", name = " + name);
    //方法执行之前打印
    mv.visitLdcInsn(" before method exec");
    mv.visitLdcInsn(" [ASM 测试] method in " + owner + " ,name=" + name);
    mv.visitMethodInsn(INVOKESTATIC,
        "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
    mv.visitInsn(POP);

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

    //方法执行之后打印
    mv.visitLdcInsn(" after method exec");
    mv.visitLdcInsn(" method in " + owner + " ,name=" + name);
    mv.visitMethodInsn(INVOKESTATIC,
        "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
    mv.visitInsn(POP);
  }
}
