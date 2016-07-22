APP_STL := gnustl_static
APP_CPPFLAGS := -frtti -fexceptions -DANDROID
APP_ABI := all #armeabi armeabi-v7a x86 mips
APP_PLATFORM := android-24
#-std=c++11 -stdlib=libc++


APP_OPTIM := release



#NDK_TOOLCHAIN_VERSION   := 4.8
APP_CPPFLAGS += -std=c++11      #  Enable C++11. However, pthread, rtti and exceptions arent enabled
#APP_CPPFLAGS += -fopenmp        # OPENMP enable
#APP_CPPFLAGS := -fexceptions


APP_STL := stlport_static
#APP_STL := gnustl_static        # Instruct to use the static GNU STL implementation
#LOCAL_C_INCLUDES += ${ANDROID_NDK}/sources/cxx-stl/gnu-libstdc++/4.9/include