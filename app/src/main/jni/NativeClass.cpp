/*
 * NativeClass.cpp
 *
 *  Created on: 26 iyn 2016
 *      Author: toghrul
 */

#include "com_togrulseyid_prizmatagram_NativeClass.h"
#include "effects/dithering.h"
#include "effects/television.h"
//#include <effects/mosaic2.h>
#include "effects/mosaic.h"
#include "effects/Pixelize.h"
#include "effects/Pixelate.h"
#include "effects/ParallelInPaint.cpp"
#include "effects/Kaleidoscope.h"
#include "effects/PencilSketch.cpp"
#include "effects/FishEye.h"

#include "cartoonifier/cartoon.h"
#include "cartoonifier/ImageUtils.h" // Handy functions for debugging OpenCV images, by Shervin Emami.



#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_getStringFromNative(JNIEnv* env, jclass obj)
{
    return env->NewStringUTF("Hello from JNI");
}

/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    myNativeFunction
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_myNativeFunction(JNIEnv* env, jclass obj, jlong mat_adress)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);

    int channels = mat.channels();

    int nRows = mat.rows;
    int nCols = mat.cols * channels;

    if (mat.isContinuous()) {
        nCols *= nRows;
        nRows = 1;
    }

    int i, j;
    uchar* p;
    for (i = 0; i < nRows; ++i) {
        p = mat.ptr<uchar>(i);
        for (j = 0; j < nCols; ++j) {
            p[j] = 255 - p[j];
        }
    }

    //LOGD("channels=%i\n", channels);
}

/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    nativeDithering
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativeDithering(JNIEnv* env, jclass obj, jlong mat_adress)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);
    //	toGrayscale(mat);

    dithering(mat, 10);
}

/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    nativeMosaic
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativeMosaic(JNIEnv* env, jclass obj, jlong mat_adress, jint gridSize)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);
    cv::Mat m = mosaic(mat, gridSize);
    mat = m;
    m.release();
    //LOGD("Java_com_togrulseyid_projectx_NativeClass_nativeDithering");
}

/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    nativeTelevision
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativeTelevision(JNIEnv* env, jclass obj, jlong mat_adress)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);
    nativeTelevision(mat);
}

/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    nativePixelize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativePixelize(JNIEnv* env, jclass obj, jlong mat_adress, jint size)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);
    //nativeTelevision(mat);


    LOGD("Geldi getdi size %d", size);
    Pixelize pixelize(&mat, size);
    pixelize.process();

}

/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    nativePixelate
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativePixelate(JNIEnv* env, jclass obj, jlong mat_adress, jint size)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);
    //nativeTelevision(mat);

    Pixelate pixelate(&mat, size);
    pixelate.process();

}

/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    nativeOilPaint
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativeOilPaint(JNIEnv* env, jclass obj, jlong mat_adress, jint intensity, jint radius)
{
    cv::Mat& im = *((cv::Mat*)mat_adress);
    Mat  intensityLUT(im.rows,im.cols,CV_8UC1);
    Mat  rgbLUT(im.rows,im.cols,CV_8UC3);
    Mat dst(im.rows,im.cols,CV_8UC3);
    //int intensity = 20;
    //int radius = 5;

//    while (true)
//    {
        //v>>im;
        cvtColor(im, intensityLUT, CV_RGB2GRAY);
        intensityLUT = intensityLUT *(intensity/255.);

        ParallelInPaint x(im, dst, intensityLUT, radius);
        parallel_for_( Range(0, im.rows), x, getNumThreads());

        im = dst;
        //imshow("Result", dst);
        /*
        char c= waitKey(1);
        switch (c){
        case 'I':
            intensity++;
            if (intensity>255)
                intensity=255;
            break;
        case 'i':
            intensity--;
            if (intensity==0)
                intensity=1;
        case 'R':
            radius++;
            if (radius>255)
                radius=255;
            break;
        case 'r':
            radius--;
            if (radius==-1)
                radius=0;
        }
        if (c>32)
            cout << "Intensity =" << intensity << "\tradius = " << radius << "\n";
            */
//    }

}


/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    nativeOilPaint
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativeFishEye(JNIEnv* env, jclass obj, jlong mat_adress, jint intensity, jint radius)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);

    FishEye fishEye(&mat);
    fishEye.process();


}


/*
 * Modify the camera image using the Cartoonifier filter.
 * Class:     com_togrulseyid_prizmatagram_natives_NativeClass
 * Method:    cartoonifyImage
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_cartoonifyImage(JNIEnv* env, jobject,
        jint width, jint height, jbyteArray yuv, jintArray bgra,
        jboolean sketchMode, jboolean alienMode, jboolean evilMode, jboolean debugMode)
{
    //START_TIMING(CartoonifyImage);

    // Get native access to the given Java arrays.
    jbyte* _yuv  = env->GetByteArrayElements(yuv, 0);
    jint*  _bgra = env->GetIntArrayElements(bgra, 0);

    // Input color format (from camera):
    // "myuv" is the color image in the camera's native NV21 YUV 420 "semi-planar" format, which means
    // the first part of the array is the grayscale pixel array, followed by a quarter-sized pixel
    // array that is the U & V color channels interleaved. So if we just want to access a grayscale
    // image, we can get it directly from the 1st part of a YUV420sp semi-planar image without any
    // conversions. But if we want a color image (eg: BGRA color format that is recommended for OpenCV
    // on Android), then we must convert the color format using cvtColor().
    Mat myuv(height + height/2, width, CV_8UC1, (unsigned char *)_yuv); // Wrapper around the _yuv data.
    Mat mgray(height, width, CV_8UC1, (unsigned char *)_yuv); // Also a wrapper around the _yuv data.

    // Output color format (for display):
    // "mbgra" is the color image to be displayed on the Android device, in BGRA format (ie: OpenCV's
    // default BGR which is RGB but in the opposite byte order, but with an extra 0 byte on the end
    // of each pixel, so that each pixel is stored as Blue, Green, Red, 0). You can either do all
    // your processing in OpenCV's default BGR format and then convert your final output from BGR to
    // BGRA before display on the screen, or ideally you can ensure your image processing code can
    // handle BGRA format instead of or in addition to BGR format. This is particularly important if
    // you try to access pixels directly in the image!
    Mat mbgra(height, width, CV_8UC4, (unsigned char *)_bgra);

    // Convert the color format from the camera's YUV420sp semi-planar format to a regular BGR color image.
    Mat mbgr(height, width, CV_8UC3);   // Allocate a new image buffer.
    cvtColor(myuv, mbgr, CV_YUV420sp2BGR);


    //--- Beginning of custom C/C++ image processing with OpenCV ---//
    Mat displayedFrame(mbgra.size(), CV_8UC3);

    // Use debug type 1 (for mobile) if debug mode is enabled, since we can't show popup GUI windows.
    int debugType = 0;
    if (debugMode)
        debugType = 1;

    // Do the C/C++ image processing.
    cartoonifyImage(mbgr, displayedFrame, sketchMode, alienMode, evilMode, debugType);

    // Convert back from OpenCV's BGR format to Android's BGRA format, unless if we can handle BGRA in our code.
    cvtColor(displayedFrame, mbgra, CV_BGR2BGRA);
    //--- End of custom C/C++ image processing with OpenCV ---//


    // Release the native lock we placed on the Java arrays.
    env->ReleaseIntArrayElements(bgra, _bgra, 0);
    env->ReleaseByteArrayElements(yuv, _yuv, 0);

   // STOP_TIMING(CartoonifyImage);
    // Print the timing info.
   // SHOW_TIMING(CartoonifyImage, "CartoonifyImage");
}

/*
 * Just show the plain camera image without modifying it.
 * Class:     com_togrulseyid_prizmatagram_natives_NativeClass
 * Method:    ShowPreview
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_ShowPreview(JNIEnv* env, jobject,
        jint width, jint height, jbyteArray yuv, jintArray bgra)
{
    // Get native access to the given Java arrays.
    jbyte* _yuv  = env->GetByteArrayElements(yuv, 0);
    jint*  _bgra = env->GetIntArrayElements(bgra, 0);

    // Prepare a cv::Mat that points to the YUV420sp data.
    Mat myuv(height + height/2, width, CV_8UC1, (uchar *)_yuv);
    // Prepare a cv::Mat that points to the BGRA output data.
    Mat mbgra(height, width, CV_8UC4, (uchar *)_bgra);

    // Convert the color format from the camera's
    // NV21 "YUV420sp" format to an Android BGRA color image.
    cvtColor(myuv, mbgra, CV_YUV420sp2BGRA);

    // OpenCV can now access/modify the BGRA image if we want ...


    // Release the native lock we placed on the Java arrays.
    env->ReleaseIntArrayElements(bgra, _bgra, 0);
    env->ReleaseByteArrayElements(yuv, _yuv, 0);
}




/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    nativeFlip
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativeFlip(JNIEnv* env, jclass obj, jlong mat_adress)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);

    cv::Mat src = mat.clone();
    cv::flip(src, mat, 1);

}


/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    nativeMirror
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativeMirror(JNIEnv* env, jclass obj, jlong mat_adress)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);
    //ColorMap colorMap(&mat);
    //colorMap.process();

}

/*
 * Class:     com_togrulseyid_test_NativeClass
 * Method:    nativeTest
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativeTest(JNIEnv* env, jclass obj, jlong mat_adress)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);
    //dithering(mat);
    //toGrayscale(mat);
    //nativeTelevision(mat);

 //   Kaleidoscope kaleidoscope(&mat);
 //   kaleidoscope.process();


    // Set to true if you want to see line drawings instead of paintings.
    bool m_sketchMode = false;
    // Set to true if you want to change the skin color of the character to an alien color.
    bool m_alienMode = false;
    // Set to true if you want an evil "bad" character instead of a "good" character.
    bool m_evilMode = false;

    int debugType = 0;


    Mat cameraFrame = mat;

    Mat displayedFrame = Mat(cameraFrame.size(), CV_8UC3);
    // Run the cartoonifier filter using the selected mode.
    cartoonifyImage(cameraFrame, displayedFrame, m_sketchMode, m_alienMode, m_evilMode, debugType);

    mat = displayedFrame;


}



#ifdef __cplusplus
}
#endif
