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

    public native static void nativePixelize(long address, int size);

    public native static void nativePixelate(long address, int size);

    public native static void nativeTest(long address);

    public native static void nativeOilPaint(long address, int intensity, int radius);

    public native static void nativeFishEye(long address);

    public static native void cartoonifyImage(int width, int height, byte[] yuv, int[] rgba, boolean sketchMode, boolean alienMode, boolean evilMode, boolean debugMode);

    public static native void cartoonifyImages(long address, boolean sketchMode, boolean alienMode, boolean evilMode);

    public static native void ShowPreview(int width, int height, byte[] yuv, int[] rgba);

    public native static void nativeMirror(long address);

    public native static void nativeFlip(long address);

    public native static void nativeStylization(long address, float sigma_s, float sigma_r);

}
