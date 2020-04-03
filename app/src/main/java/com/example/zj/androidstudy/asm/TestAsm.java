package com.example.zj.androidstudy.asm;

import android.util.Log;

/**
 * Created on 2020-04-02.
 * 用于测试ASM动态添加代码到TestAsm类中。可以在build/intermediates/transforms目录下查看插入结果
 */
public class TestAsm {
  public static void testAsm() {
    Log.i("ASM", "test");
  }
}
