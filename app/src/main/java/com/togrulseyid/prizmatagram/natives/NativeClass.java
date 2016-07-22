package com.togrulseyid.prizmatagram.natives;

/**
 * Created by toghrul on 25.06.2016.
 */
public class NativeClass {
//    PrizmaTagram\app\src\main>
//    javah -d jni -classpath ../../build/intermediates/classes/debug/ com.togrulseyid.prizmatagram.natives.NativeClass
    public native static String getStringFromNative();

    public native static void myNativeFunction(long address);

    public native static void nativeDithering(long address);

    public native static void nativeMosaic(long address, int gridSize);

    public native static void nativeTelevision(long address);

    public native static void nativePixelize(long address);

    public native static void nativeTest(long address);
}
