LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

OPENCV_LIB_TYPE:=SHARED

# OpenCV
OPENCVROOT:= C:/Users/toghrul/PrizmaTagram/OpenCV-android-sdk/
OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=SHARED
include ${OPENCVROOT}/sdk/native/jni/OpenCV.mk


LOCAL_MODULE    := prizmaTagramN
LOCAL_SRC_FILES := NativeClass.cpp \
                    effects/dithering.cpp \
                    effects/GaussianBlur.cpp \
                    effects/mosaic.cpp \
                    effects/mosaic2.cpp \
                    effects/Pixelize.cpp \
                    effects/television.cpp \

LOCAL_LDLIBS += -llog -ldl
#LOCAL_CPP_FEATURES += exceptions
APP_CPPFLAGS := -frtti -fexceptions
#LOCAL_CFLAGS += -std=c++11

LOCAL_CFLAGS += -fopenmp
LOCAL_LDFLAGS += -fopenmp


LOCAL_CPP_FEATURES := exceptions
LOCAL_CPP_FEATURES := rtti features



include $(BUILD_SHARED_LIBRARY)
