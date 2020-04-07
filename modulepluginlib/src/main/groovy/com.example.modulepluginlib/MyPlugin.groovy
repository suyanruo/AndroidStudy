package com.example.modulepluginlib

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 自定义gradle plugin
 * ref: https://blog.csdn.net/huachao1001/article/details/51810328
 */
class MyPlugin implements Plugin<Project> {

    void apply(Project project) {
        System.out.println("hello gradle plugin!")

        // 获取Android扩展
        def android = project.extensions.getByType(AppExtension)
        // 注册Transform，其实就是添加了Task
        android.registerTransform(new InjectTransform(project))
        android.registerTransform(new AsmTransform(project))
        android.registerTransform(new LifecycleTransform(project))
    }
}