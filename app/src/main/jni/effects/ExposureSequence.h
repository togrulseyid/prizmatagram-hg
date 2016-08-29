//
// Created by toghrul on 24.08.2016.
//

#ifndef PRIZMATAGRAM_EXPOSURESEQUENCE_H
#define PRIZMATAGRAM_EXPOSURESEQUENCE_H

#include "core/std_head.h"

class ExposureSequence {

    public:
        ExposureSequence(cv::Mat *m): mat(m){};
        ~ExposureSequence();  // Destructor
        void process();

    private :
        cv::Mat *mat;
};

#endif //PRIZMATAGRAM_EXPOSURESEQUENCE_H
