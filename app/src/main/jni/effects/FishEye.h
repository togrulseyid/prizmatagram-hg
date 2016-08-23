//
// Created by toghrul on 03.08.2016.
//

#ifndef PRIZMATAGRAM_FISHEYE_H
#define PRIZMATAGRAM_FISHEYE_H

#include "core/std_head.h"

class FishEye {

    public:
        FishEye(cv::Mat *m): mat(m){};
        ~FishEye();  // Destructor
        void process();
        float calc_shift(float x1,float x2,float cx,float k);
        float getRadialX(float x,float y,float cx,float cy,float k, bool scale, Vec4f props);
        float getRadialY(float x,float y,float cx,float cy,float k, bool scale, Vec4f props);
        //void fishEye(InputArray _src, OutputArray _dst, double Cx, double Cy, double k, bool scale = true);
        void fishEye(InputArray _src, OutputArray _dst, double Cx, double Cy, double k);

    private :
        cv::Mat *mat;
};

#endif //PRIZMATAGRAM_FISHEYE_H
