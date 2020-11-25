package com.example.modulepluginlib

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.example.modulepluginlib.transform.*

/**
 * 自定义gradle plugin
 * ref: https://blog.csdn.net/huachao1001/article/details/51810328
 */
class MyPlugin implements Plugin<Project> {

    void apply(Project project) {
        println("========================")
        System.out.println("hello gradle plugin!")

        // 获取Android扩展
        def android = project.extensions.getByType(AppExtension)
        /**
         * 注册Transform，其实就是添加了Task。
         * 每一个Transform都对应一个TransformTask和TransformStream，通过输入输出隐式的构成Task依赖关系。开发者
         * 自定义的Transform按照注册顺序执行。
         */
        android.registerTransform(new InjectTransform(project))
        android.registerTransform(new AsmTransform(project))
        android.registerTransform(new LifecycleTransform(project))
    }
}