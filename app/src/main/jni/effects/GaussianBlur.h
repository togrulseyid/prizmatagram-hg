//
// Created by toghrul on 17.07.2016.
//

#ifndef PRIZMA_TAGRAM_GAUSSIANBLUR_H
#define PRIZMA_TAGRAM_GAUSSIANBLUR_H

#include <core/std_head.h>

class GaussianBlur  {

    public:
      // This means that all of the functions below this(and any variables)
      //  are accessible to the rest of the program.
        //Pixelize(cv::Mat *_mat);  // Constructor


        GaussianBlur(cv::Mat *m):  mat(m){}


        ~GaussianBlur();  // Destructor

        void process();

    protected:
          // This means that all the variables under this, until a new type of restriction
          // is placed, will only be accessible to other functions in the class.
        int status;

    private :
        cv::Mat *mat;

};



#endif //PRIZMA_TAGRAM_GAUSSIANBLUR_H
