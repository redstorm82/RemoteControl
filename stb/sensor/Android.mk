LOCAL_PATH := $(call my-dir)


#build shared library
include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
	sensors_4.0.c \
	SensorsServer.c

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)
	
LOCAL_SHARED_LIBRARIES := \
    libcutils liblog

LOCAL_MODULE := libsensor

LOCAL_CFLAGS += -Wall

include $(BUILD_SHARED_LIBRARY)


