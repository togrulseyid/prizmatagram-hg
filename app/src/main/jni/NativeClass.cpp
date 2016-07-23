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
//#include "core/std_head.h"


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

    LOGD("channels=%i\n", channels);
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
    LOGD("Java_com_togrulseyid_projectx_NativeClass_nativeDithering");
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
    nativeTelevision(mat);
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

    //Pixelize pixelize(&mat);
    Pixelize pixelize(&mat, 40);
    pixelize.process();

    //NativeLog

    LOGE("Salam");
    LOGW("Salam");
    LOGI("Salam");
    LOGD("Salam");
}

#ifdef __cplusplus
}
#endif
