//
// Created by toghrul on 02.07.2016.
//

#ifndef PRIZMA_TAGRAM_PIXELATE_H
#define PRIZMA_TAGRAM_PIXELATE_H

#include "core/std_head.h"

class Pixelate
{
public:
    // This means that all of the functions below this(and any variables)
    //  are accessible to the rest of the program.
    // Pixelize(cv::Mat *_mat);  // Constructor

    Pixelate(cv::Mat *m, int size):
        mat(m), size(size){}

    ~Pixelate(); // Destructor

    void process();

    void pixelate(Mat& src, Mat& dst, int pixel_size);
    void pixelate(Mat& src, Mat& dst, Rect roi, int pixel_size) ;

protected:
    // This means that all the variables under this, until a new type of
    // restriction
    // is placed, will only be accessible to other functions in the class.
    int status;

private:
    cv::Mat* mat;
    int size = 1;
};

#endif // PRIZMA_TAGRAM_PIXELATE_H
