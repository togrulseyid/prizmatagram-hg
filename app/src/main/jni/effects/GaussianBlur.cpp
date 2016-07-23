//
// Created by toghrul on 17.07.2016.
//

#include "effects/GaussianBlur.h"

#ifdef __cplusplus
extern "C" {
#endif
/*
GaussianBlur::GaussianBlur(cv::Mat *_mat)
{
    mat = _mat;
}
*/
GaussianBlur::~GaussianBlur()
{
    //matObj.release();
}


void GaussianBlur::process()
{
	cv::Mat imgOut = mat->clone();

  //  cv::GaussianBlur(mat, imgOut, cv::Size(51,3), 80, 3);

	imgOut.release();
}

#ifdef __cplusplus
}
#endif
