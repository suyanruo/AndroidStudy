package com.example.zj.androidstudy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NotProguard, Means not proguard something, like class, method, field<br/>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2015-08-07
 * {@link "https://blog.csdn.net/yaya_soft/article/details/48054819"}
 * <p>
 *   NotProguard 是个编译时注解，不会对运行时性能有任何影响。可修饰类、方法、构造函数、属性
 * </p>
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface NotProguard {

}