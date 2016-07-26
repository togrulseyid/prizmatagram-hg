//
// Created by toghrul on 25.07.2016.
//

//
// Created by toghrul on 02.07.2016.
//

//#include "effects/ParallelInPaint.h"

#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/videoio/videoio.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/highgui.hpp"

#include <iostream>
#include <fstream>
#include <algorithm>
#include <ctype.h>

using namespace cv;
using namespace std;

#ifdef __cplusplus
extern "C" {
#endif

class ParallelInPaint: public ParallelLoopBody
{
private:
    Mat &imgSrc;
    Mat &dst;
    Mat &intensityLUT;
    bool verbose;
    int radius;
    int intensity;

public:
    ParallelInPaint(Mat& img, Mat &d,Mat &iLuminance,int r):
        imgSrc(img),
        dst(d),
        intensityLUT(iLuminance),
        radius(r),
        verbose(false)
    {}
    void Verbose(bool b){verbose=b;}
    virtual void operator()(const Range& range) const
    {

        int width = imgSrc.cols;
        if (verbose)
            cout << getThreadNum()<<"# :Start from row " << range.start << " to "  << range.end-1<<" ("<<range.end-range.start<<" loops)" << endl;
        vector<int> pixelIntensityCount;
        vector<Vec3f> pixelIntensityRGB;

        for(int y = range.start; y < range.end; y++)
        {
            Vec3b *vDst = (Vec3b *)dst.ptr(y);
            for (int x = 0; x < imgSrc.cols; x++,vDst++) //for each pixel
            {
				pixelIntensityCount.assign(256, 0);
				pixelIntensityRGB.assign(256, Vec3f(0, 0, 0));
				for (int yy = -radius; yy <= radius; yy++)
				{
					if (y+ yy >= 0  && y+yy < imgSrc.rows)
					{
						Vec3b *vPtr = (Vec3b *)imgSrc.ptr(y + yy) + x - radius;
						uchar *uc = intensityLUT.ptr(y + yy) + x - radius;
						for (int xx = -radius; xx <= radius; xx++, vPtr++, uc++)
						{
							if (x + xx >= 0 && x + xx < imgSrc.cols)
							{
								int intensityVal = *uc;
								pixelIntensityCount[intensityVal]++;
								pixelIntensityRGB[intensityVal] += *vPtr;

							}
						}
					}
				}
				vector<int>::iterator it;
				int pos = distance(pixelIntensityCount.begin(), max_element(pixelIntensityCount.begin(), pixelIntensityCount.end()));
				*vDst = pixelIntensityRGB[pos] / pixelIntensityCount[pos];
            }
        }

    }
    ParallelInPaint& operator=(const ParallelInPaint &) {
         return *this;
    };
};



#ifdef __cplusplus
}
#endif
