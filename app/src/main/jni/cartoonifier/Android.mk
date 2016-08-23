#/****************************************************************************
#*   Cartoonifier, for Android.
#*****************************************************************************
#*   by Shervin Emami, 5th Dec 2012 (shervin.emami@gmail.com)
#*   http://www.shervinemami.info/
#*****************************************************************************
#*   Ch1 of the book "Mastering OpenCV with Practical Computer Vision Projects"
#*   Copyright Packt Publishing 2012.
#*   http://www.packtpub.com/cool-projects-with-opencv/book
#****************************************************************************/


LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

OPENCV_LIB_TYPE:=STATIC
OPENCV_INSTALL_MODULES:=on

# Path to OpenCV.mk file, which is generated when you build OpenCV for Android.
# include C:\OpenCV\android\build\OpenCV.mk
# include ~/OpenCV/android/build/OpenCV.mk

OPENCVROOT:= C:/Users/toghrul/PrizmaTagram/OpenCV-android-sdk/
include ${OPENCVROOT}/sdk/native/jni/OpenCV.mk


LOCAL_MODULE    := cartoonifier
LOCAL_LDLIBS +=  -llog -ldl

# Since we have source + headers files in an external folder, we need to show where they are.
LOCAL_SRC_FILES := jni_part.cpp
LOCAL_SRC_FILES += cartoon.cpp
LOCAL_SRC_FILES += ImageUtils_0.7.cpp
#LOCAL_C_INCLUDES += $(LOCAL_PATH)/../../Cartoonifier_Desktop


include $(BUILD_SHARED_LIBRARY)
