//
// Created by toghrul on 03.08.2016.
//

#include "ExposureSequence.h"

#include "cartoonifier/cartoon.h"
#include "cartoonifier/ImageUtils.h" // Handy functions for debugging OpenCV images, by Shervin Emami.


ExposureSequence::~ExposureSequence()
{
    //matObj.release();
}

void ExposureSequence::process()
{
	cv::Mat imgOut = mat->clone();


	bool sketchMode=false;
	bool alienMode = false;
	bool evilMode = false;
	bool debugType = false;

    Mat cameraFrame = imgOut;

    Mat displayedFrame = Mat(cameraFrame.size(), CV_8UC3);
    // Run the cartoonifier filter using the selected mode.
    cartoonifyImage(cameraFrame, displayedFrame,
        sketchMode, // Set to true if you want to see line drawings instead of paintings.
        alienMode, // Set to true if you want to change the skin color of the character to an alien color.
        evilMode, // Set to true if you want an evil "bad" character instead of a "good" character.
        debugType);



    LOGD("displayedFrame catdi");
    Mat image = displayedFrame;
    for (int y = 0; y < displayedFrame.rows; y++)
    {
        for (int x = 0; x < displayedFrame.cols; x++)
        {
            // get pixel
            Vec3b color = image.at<Vec3b>(Point(x, y));

            //cout << (int)color(0) <<"-"<< (int)color[1] << "-" << (int)color[2] <<endl;


            for (int i = 250; i >=0; i = i - 10) {
                if (color[0]>i && color[0] < i+10) {
                    color[0] = i; //0 10 20 30 40 50 60 70 80 90 100 110 120 130 140 150 160 170 180 190 200 210 220 230 240 250
                }


                if (color[1]>i && color[1] < i + 10) {
                    color[1] = i; //0 10 20 30 40 50 60 70 80 90 100 110 120 130 140 150 160 170 180 190 200 210 220 230 240 250
                }

                if (color[2]>i && color[2] < i + 10) {
                    color[2] = i; //0 10 20 30 40 50 60 70 80 90 100 110 120 130 140 150 160 170 180 190 200 210 220 230 240 250
                }
            }

            // set pixel
            image.at<Vec3b>(Point(x, y)) = color;
        }
    }

   	*mat = image;
    //imgOut = displayedFrame;



//    stylization(Mat src, Mat dst, float sigma_s=60, float sigma_r=0.45f)
//    stylization(image, *mat, 60, 0.45f);

}