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
    jmethodID id = env->GetStaticMethodID(clazz, "staticMethodCalledByJni", "(Ljava/lang/String;)V");
    if (id == NULL) {

    }
    jstring msg = env->NewStringUTF("msg send by callJavaMethod in test.app");
    env->CallStaticVoidMethod(clazz, id, msg);

    jmethodID id2 = env->GetMethodID(clazz, "methodCalledByJni", "(Ljava/lang/String;)V");
    env->CallVoidMethod(thiz, id2, msg);

    //todo 调用非静态方法
//    jmethodID construct_id = env->NewObject(clazz, "getInstance", "()V");
//    jmethodID id2 = env->GetMethodID(clazz, "showCall", "()V");
//    jobject data_obj = env->NewObject(clazz, construct_id);
//    env->CallVoidMethod(data_obj, id2);

    printf("version: %d", env->GetVersion());
}

// 使用"Java + 包名 + 类名 + 方法名"静态注册方法
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

jstring change(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("Change from C++");
}

jint RegisterNatives(JNIEnv *env) {
    jclass clazz = env->FindClass("com/example/zj/androidstudy/jni/JniActivity");
    if (clazz == NULL) {
        return JNI_ERR;
    }
    JNINativeMethod methods_JniActivity[] = {
        {"change", "()Ljava/lang/String;", (void *) change}
    };
    return env->RegisterNatives(clazz, methods_JniActivity, sizeof(methods_JniActivity) / sizeof(methods_JniActivity[0]));
}

// 动态注册方法
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    jint result = RegisterNatives(env);
    return JNI_VERSION_1_6;
}

#ifdef __cplusplus
}
#endif