LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

# OpenCV
OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=SHARED
#OPENCV_LIB_TYPE:=STATIC
OPENCVROOT:= C:/Users/toghrul/PrizmaTagram/OpenCV-android-sdk/
include ${OPENCVROOT}/sdk/native/jni/OpenCV.mk


LOCAL_MODULE    := PrizmaTagramN
LOCAL_SRC_FILES := NativeClass.cpp \
                    effects/dithering.cpp \
                    effects/GaussianBlur.cpp \
                    effects/mosaic.cpp \
                    effects/mosaic2.cpp \
                    effects/Pixelize.cpp \
                    effects/television.cpp \

#LOCAL_C_INCLUDES += $(LOCAL_PATH)
LOCAL_LDLIBS     += -llog -ldl

include $(BUILD_SHARED_LIBRARY)
