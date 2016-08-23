//
// Created by toghrul on 02.08.2016.
//

#ifndef PRIZMATAGRAM_GAUSSIANFILTER_H
#define PRIZMATAGRAM_GAUSSIANFILTER_H

#include "core/std_head.h"

class GaussianFilter {

    public:
      // This means that all of the functions below this(and any variables)
      //  are accessible to the rest of the program.
        //Kaleidoscope(cv::Mat *_mat);  // Constructor

        GaussianFilter(cv::Mat *m):
        mat(m){}

        ~GaussianFilter();  // Destructor

        void process();
        int* getRGB(int startX, int startY, int w, int h, int* rgbArray, int offset, int scansize);

    protected:
          // This means that all the variables under this, until a new type of restriction
          // is placed, will only be accessible to other functions in the class.
        int status;

    private :
        cv::Mat *mat;
        float amount = 0.5f;
        float radius = 2.0f;

};
#endif //PRIZMATAGRAM_GAUSSIANFILTER_H
