APP_STL := gnustl_static
APP_CPPFLAGS := -frtti -fexceptions -fopenmp -DANDROID
APP_ABI := armeabi-v7a
APP_PLATFORM := android-24
APP_CPPFLAGS += -std=c++11      #  Enable C++11. However, pthread, rtti and exceptions arent enabled