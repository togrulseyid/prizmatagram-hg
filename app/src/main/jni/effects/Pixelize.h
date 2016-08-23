//
// Created by toghrul on 02.07.2016.
//

#ifndef PRIZMA_TAGRAM_PIXELIZE_H
#define PRIZMA_TAGRAM_PIXELIZE_H

#include "core/std_head.h"

class Pixelize {

    public:
      // This means that all of the functions below this(and any variables)
      //  are accessible to the rest of the program.
        Pixelize(cv::Mat *m, int size):
        mat(m), cols(size){}

        ~Pixelize();  // Destructor

        void process();
        cv::Mat pixelate(IplImage* &img, int size);

    protected:
          // This means that all the variables under this, until a new type of restriction
          // is placed, will only be accessible to other functions in the class.
        int status;

    private :
        cv::Mat *mat;
        int cols = 5;

};

#endif //PRIZMA_TAGRAM_PIXELIZE_H
