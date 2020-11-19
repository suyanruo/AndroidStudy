//
// Created by 张健 on 2020/11/19.
//
#include <jni.h>
#include <stdio.h>

#ifdef __cplusplus
extern "C" {
#endif

void callJavaMethod(JNIEnv *env, jobject thiz) {
    jclass clazz = env->FindClass("com/example/zj/androidstudy/jni/JniActivity");
    if (clazz == NULL) {
        return;
    }
    jmethodID id = env->GetStaticMethodID(clazz, "methodCalledByJni", "(Ljava/lang/String;)V");
    if (id == NULL) {

    }
    jstring msg = env->NewStringUTF("msg send by callJavaMethod in test.app");
    env->CallStaticVoidMethod(clazz, id, msg);

    //todo 调用非静态方法
//    jmethodID construct_id = env->NewObject(clazz, "getInstance", "()V");
//    jmethodID id2 = env->GetMethodID(clazz, "showCall", "()V");
//    jobject data_obj = env->NewObject(clazz, construct_id);
//    env->CallVoidMethod(data_obj, id2);
}

jstring Java_com_example_zj_androidstudy_jni_JniActivity_get(JNIEnv *env, jobject thiz) {
    printf("invoke get in c++\n");
    callJavaMethod(env, thiz);
    return env->NewStringUTF("Hello from JNI!");
}

void Java_com_example_zj_androidstudy_jni_JniActivity_set(JNIEnv *env, jobject thiz, jstring string) {
    printf("invoke set from c++\n");
    char* str = (char*)env->GetStringUTFChars(string, NULL);
    printf("%s\n", str);
    env->ReleaseStringUTFChars(string, str);
}

#ifdef __cplusplus
}
#endif