//
// Created by toghrul on 26.07.2016.
//

#ifndef PRIZMATAGRAM_KALEIDOSCOPE_H
#define PRIZMATAGRAM_KALEIDOSCOPE_H

#include "core/std_head.h"

class Kaleidoscope {

    public:
      // This means that all of the functions below this(and any variables)
      //  are accessible to the rest of the program.
        //Kaleidoscope(cv::Mat *_mat);  // Constructor


        Kaleidoscope(cv::Mat *m):
        mat(m){}


        ~Kaleidoscope();  // Destructor

        void process();

    protected:
          // This means that all the variables under this, until a new type of restriction
          // is placed, will only be accessible to other functions in the class.
        int status;

    private :
        cv::Mat *mat;

};

#endif //PRIZMATAGRAM_KALEIDOSCOPE_H
