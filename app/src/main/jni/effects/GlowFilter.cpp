//
// Created by toghrul on 02.08.2016.
//

#include "effects/GlowFilter.h"

#ifdef __cplusplus
extern "C" {
#endif
/*
GlowFilter::GlowFilter(cv::Mat *_mat)
{
    mat = _mat;
}
*/

GlowFilter::~GlowFilter()
{
    //matObj.release();
}


 //filter( BufferedImage src, BufferedImage dst ) {
void GlowFilter::process()
{
	cv::Mat dst = mat->clone();
	//dst.release();

 //   int width = mat.getWidth();
 //   int height = mat.getHeight();
    int width = dst.size().width;
    int height = dst.size().height;


 //   if ( dst == null )
 //       dst = createCompatibleDestImage( mat, null );

//    int[] inPixels = new int[width*height];
//    int[] outPixels = new int[width*height];


    int inPixels[width*height];
    int outPixels[width*height];
    //mat.getRGB( 0, 0, width, height, inPixels, 0, width );
    getRGB( 0, 0, width, height, inPixels, 0, width );

    if ( radius > 0 ) {
        convolveAndTranspose(kernel, inPixels, outPixels, width, height, alpha, alpha && premultiplyAlpha, false, CLAMP_EDGES);
        convolveAndTranspose(kernel, outPixels, inPixels, height, width, alpha, false, alpha && premultiplyAlpha, CLAMP_EDGES);
    }

    mat.getRGB( 0, 0, width, height, outPixels, 0, width );

    float a = 4*amount;

    int index = 0;
    for ( int y = 0; y < height; y++ ) {
        for ( int x = 0; x < width; x++ ) {
            int rgb1 = outPixels[index];
            int r1 = (rgb1 >> 16) & 0xff;
            int g1 = (rgb1 >> 8) & 0xff;
            int b1 = rgb1 & 0xff;

            int rgb2 = inPixels[index];
            int r2 = (rgb2 >> 16) & 0xff;
            int g2 = (rgb2 >> 8) & 0xff;
            int b2 = rgb2 & 0xff;

            r1 = PixelUtils.clamp( (int)(r1 + a * r2) );
            g1 = PixelUtils.clamp( (int)(g1 + a * g2) );
            b1 = PixelUtils.clamp( (int)(b1 + a * b2) );

            inPixels[index] = (rgb1 & 0xff000000) | (r1 << 16) | (g1 << 8) | b1;
            index++;
        }
    }

    dst.setRGB( 0, 0, width, height, inPixels, 0, width );
    return dst;
}


int* getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
    int yoff  = offset;
    int off;
    int   data = new int[nbands];

    if (rgbArray == null) {
        rgbArray = new int[offset+h*scansize];
    }

    for (int y = startY; y < startY+h; y++, yoff+=scansize) {
        off = yoff;
        for (int x = startX; x < startX+w; x++) {
            //rgbArray[off++] = colorModel.getRGB(raster.getDataElements(x, y,  data));

            rgbArray[off++] =(  (imgOut.at <cv::Vec3b> (i, j)[0] << 16)
                              | (imgOut.at <cv::Vec3b> (i, j)[1] << 8)
                              | (imgOut.at <cv::Vec3b> (i, j)[2] << 0));
        }
    }

    return rgbArray;
}

/*

return (getAlpha(inData) << 24)
             | (imgOut.at <cv::Vec3b> (i, j)[0] << 16)
             | (imgOut.at <cv::Vec3b> (i, j)[1] << 8)
             | (imgOut.at <cv::Vec3b> (i, j)[2] << 0);*/

#ifdef __cplusplus
}
#endif

