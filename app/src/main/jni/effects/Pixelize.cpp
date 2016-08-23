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

    IplImage* img;
    img = cvCreateImage(cvSize(imgOut.cols,imgOut.rows),8,3);
    IplImage ipltemp=imgOut;
    cvCopy(&ipltemp,img);


    *mat = pixelate(img, cols);

	imgOut.release();
}



cv::Mat Pixelize::pixelate(IplImage* &img, int size) {

	if(size > 0) {

    LOGD("Geldi getdix %d", size);
		for( int y=0; y<img->height; y++ ) {
			for( int x=0; x<img->width; x++ ) { //traverse each pixel
				for(int c=0; c<3; c++) { //access color channel
					int xPixel = x-(x%size);
					int yPixel = y-(y%size);
					img->imageData[(y*img->width+x)*3+c] = img->imageData[(yPixel*img->width+xPixel)*3+c]; //replace with pixel img at adjustment value.
				}
			}
		}
	}

	cv::Mat m = cv::cvarrToMat(img);  // default additional arguments: don't copy data.
    return m;
}


#ifdef __cplusplus
}
#endif
