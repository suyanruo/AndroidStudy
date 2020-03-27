package com.example.modulepluginlib

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 自定义gradle plugin
 * ref: https://blog.csdn.net/huachao1001/article/details/51810328
 */
public class MyPlugin implements Plugin<Project> {

    void apply(Project project) {
        System.out.println("========================");
        System.out.println("hello gradle plugin!");
        System.out.println("========================");
    }

}