//
// Created by toghrul on 02.07.2016.
//

#include "effects/Pixelize.h"

#ifdef __cplusplus
extern "C" {
#endif
/*
Pixelize::Pixelize(cv::Mat *_mat)
{
    mat = _mat;
}
*/
Pixelize::~Pixelize()
{
    //matObj.release();
}


void Pixelize::process()
{
	cv::Mat imgOut = mat->clone();

	int width = imgOut.size().width;
	int height = imgOut.size().height;

    double meanColor;
    cv::Vec3b newColor;
    double coeff;


	//LOGD("qaqa uzun oldu ee %d\n\n", (imgOut.size().height*imgOut.size().width/2*3));

//    #pragma omp parallel
//    {

        //int numThread = omp_get_num_threads();
        //omp_set_num_threads(4);

       // LOGD("numThread=%d\n\n", numThread);

      //  #pragma omp for
        for (int i = 0; i < imgOut.size().height; ++i) {
            for (int j = 0; j < imgOut.size().width/2*3; ++j) {

                // desaturate the image
                meanColor = (imgOut.at <cv::Vec3b> (i, j)[0] + imgOut.at<cv::Vec3b> (i, j)[1] + imgOut.at<cv::Vec3b> (i, j)[3]) / 3.0;
                newColor[0] = (1 - decolorisation) * imgOut.at <cv::Vec3b> (i, j)[0] + decolorisation * meanColor;
                newColor[1] = (1 - decolorisation) * imgOut.at <cv::Vec3b> (i, j)[1] + decolorisation * meanColor;
                newColor[2] = (1 - decolorisation) * imgOut.at <cv::Vec3b> (i, j)[2] + decolorisation * meanColor;

                // boost the blue channel
                 coeff = 0.5 + sin((2 * M_PI * i) / wavelength) / 2.0;
                newColor[0] = newColor[0] + intensity * coeff * (255 - newColor[0]);

                mat->at < cv::Vec3b > (i, j) = newColor;
            }
        }
//    }

	LOGD("qaqa uzun oldu ee %d %d ", imgOut.size().height, imgOut.size().width);

	imgOut.release();
}

#ifdef __cplusplus
}
#endif
