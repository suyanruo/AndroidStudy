LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := jni-test
LOCAL_SRC_FILES := test.cpp \
                   LogUtil.h

LOCAL_LDLIBS:=-L$(SYSROOT)/usr/lib -llog

# 设置支持C++ 11
LOCAL_CFLAGS += -std=c++11

include $(BUILD_SHARED_LIBRARY)