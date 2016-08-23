//
// Created by toghrul on 02.07.2016.
//

#include "effects/Pixelate.h"

#ifdef __cplusplus
extern "C" {
#endif

Pixelate::~Pixelate() {
  // matObj.release();
}

void Pixelate::process() {
	cv::Mat imgOut = mat->clone();
	pixelate(imgOut, *mat, size);
	imgOut.release();

}

void Pixelate::pixelate(Mat& src, Mat& dst, int pixel_size) {
    try {
        // crear cv::Mat de salida, de igual tamano que la imagen src
        dst.create(src.rows, src.cols, src.type());

        Rect rect;

        for (int r = 0; r < src.rows; r += pixel_size)
        {
            for (int c = 0; c < src.cols; c += pixel_size)
            {
                rect.x = c;
                rect.y = r;
                rect.width = c + pixel_size < src.cols ? pixel_size : src.cols - c;
                rect.height = r + pixel_size < src.rows ? pixel_size : src.rows - r;

                // obtener el color promedio del area indicada
                Scalar color = mean(Mat(src, rect));

                // pintar el area indicada con el color obtenido
                rectangle(dst, rect, color, CV_FILLED);
            }
        }
    }
    catch (cv::Exception &ex) {
        cout << ex.what() << endl;
    }
}

void Pixelate::pixelate(Mat& src, Mat& dst, Rect roi, int pixel_size) {
    try {
        dst.create(src.rows, src.cols, src.type());

        Rect rect;

        for (int r = 0; r < src.rows; r += pixel_size)
        {
            for (int c = 0; c < src.cols; c += pixel_size)
            {
                rect.x = c;
                rect.y = r;
                rect.width = c + pixel_size < src.cols ? pixel_size : src.cols - c;
                rect.height = r + pixel_size < src.rows ? pixel_size : src.rows - r;

                // verificar si se encuentra dentro del ROI
                if (roi.contains(Point(c, r)) && roi.contains(Point(c + rect.width, r + rect.height))) {
                    Scalar color = mean(Mat(src, rect));
                    rectangle(dst, rect, color, CV_FILLED);
                }
                else {
                    // copiar sin modificar
                    Mat(src, rect).copyTo(Mat(dst, rect));
                }
            }
        }
    }
    catch (cv::Exception &ex) {
        cout << ex.what() << endl;
    }
}


#ifdef __cplusplus
}
#endif
