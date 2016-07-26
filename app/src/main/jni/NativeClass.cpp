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
#include "effects/ParallelInPaint.cpp"
#include "effects/Kaleidoscope.h"
#include "effects/PencilSketch.cpp"

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
JNIEXPORT void JNICALL Java_com_togrulseyid_prizmatagram_natives_NativeClass_nativePixelize(JNIEnv* env, jclass obj, jlong mat_adress)
{
    cv::Mat& mat = *((cv::Mat*)mat_adress);
    //nativeTelevision(mat);

    //Pixelize pixelize(&mat);
    Pixelize pixelize(&mat, 40);
    pixelize.process();

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
