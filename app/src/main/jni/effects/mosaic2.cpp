/*
 * mosaic.cpp
 *
 *  Created on: 26 iyn 2016
 *      Author: toghrul
 */

#include "effects/mosaic.h"

//class Mosaic {
//	const int SQUARES = 1;
//	const int TRIANGLES = 2;
//
//public:
//	Mosaic();
//	~Mosaic();
//	Mosaic(int, int, int);
//	void process(cv::Mat &matIn, cv::Mat &matOut, int width, int shape, bool border);
//
//private :
//	int width = 6;
//	int shape = SQUARES;
//	bool border = true;
//};
//
//Mosaic::Mosaic (void) {
//  width = 6;
//  shape = SQUARES;
//  border = true;
//}
//
//Mosaic::~Mosaic(void) {
////	destructor
//}
//
//Mosaic::Mosaic (int _width, int _shape, bool _border) {
//  width = _width;
//  shape = _shape;
//  border = _border;
//}
//


const int SQUARES = 1;
const int TRIANGLES = 2;
int width = 6;
int shape = SQUARES;
bool border = true;


void process(cv::Mat &matIn, cv::Mat &matOut, int width, int shape, bool border) {

//	Graphics l_graphics = imageOut.getBufferedImage().getGraphics();
//	performanceMeter.enableProgressBar("Mosaic", imageIn.getHeight()*width);

	if (shape == SQUARES) {
		squaresMosaic(width, border, matIn, matOut);
	} else if (shape == TRIANGLES) {
		trianglesMosaic(width, border, matIn, matOut);
	}

//	imageOut.updateColorArray();
//	performanceMeter.finish();
}

void squaresMosaic(int width, bool border, cv::Mat &imageIn, cv::Mat &image) {
	cv::Scalar l_color;

	for (int y = 0; y < imageIn.size().height; y += width) {
		for (int x = 0; x <imageIn.size().width; x += width) {
			l_color = getSquareColor(x, y, imageIn);
//			graphics.setColor(l_color);
//			graphics.fillRect((int) (x), (int) (y), (int) ((width)), (int) ((width)));
//			cvRectangle(image, cvPoint(100,100), cvPoint(200,200), cvScalar(0,0,0), 1);
			rectangle( image,
					   cv::Point( x, y ),
					   cv::Point( width, width),
					   l_color, //Scalar( 0, 0, 0 ),// black color
					   -1,
					   8 );

			if (border) {
				rectangle(image, Point(x, y), Point(width, width),
						Scalar(0, 0, 0), // black color
						1,  //
						4  //
						);
			}
		}
//		performanceMeter.stepsFinished(image.getWidth());
	}
}

void trianglesMosaic(int width, bool border, cv::Mat &imageIn, cv::Mat &image){
	cv::Scalar l_colorT1;
	cv::Scalar l_colorT2;
	int t = -1;
	bool l_aux = true;

	if (((imageIn.size().width / width) % 2 == 0 && imageIn.size().width % width == 0)
			|| ((imageIn.size().width / width) % 2 == 1
					&& imageIn.size().width % width != 0)) {
		l_aux = false;
	}

	for (int y = 0; y < imageIn.size().height; y += width) {
		for (int x = 0; x < imageIn.size().height; x += width) {
			if (t == -1) {
				l_colorT1 = getTriangleColor(x, y, 0, imageIn);
				l_colorT2 = getTriangleColor(x, y, 1, imageIn);

//				graphics.setColor(l_colorT1);
//				graphics.fillPolygon(new int[] { x, x + width, x }, new int[] {y, y, y + width }, 3);
//				fillPoly(Mat& img, const Point** pts, const int* npts, int ncontours, const Scalar& color, int lineType=8, int shift=0, Point offset=Point() );
				Point rook_points[1][3];
				rook_points[0][0] = Point(y, x);
				rook_points[0][1] = Point(y, x + width);
				rook_points[0][2] = Point(y + width, x);

				const Point* ppt[1] = { rook_points[0] };
				int npt[] = { 3 };
				fillPoly( image, ppt, npt, 1, l_colorT1, 8 );


				if (border) {
//					graphics.setColor(Color.black);
//					graphics.drawPolygon(new int[] { x, x + width, x }, new int[] { y, y, y + width }, 3);

//					cvPolyLine(CvArr* img, CvPoint** pts, const int* npts, int contours, int is_closed, CvScalar color, int thickness=1, int line_type=8, int shift=0 )
					int lineWidth = 1;
					int isCurveClosed = 1;
					int nCurves = 2;
					cv::polylines(image, ppt, npt, nCurves, isCurveClosed, Scalar(0, 0, 0), lineWidth);
				}

//				graphics.setColor(l_colorT2);
//				graphics.fillPolygon(new int[] { x + width, x + width, x }, new int[] { y, y + width, y + width }, 3);

				Point rook_points2[1][3];
				rook_points2[0][0] = Point(y, x + width);
				rook_points2[0][1] = Point(y + width, x + width);
				rook_points2[0][2] = Point(y + width, x);

				const Point* ppt2[1] = { rook_points2[0] };
				int npt2[] = { 3 };
				fillPoly( image, ppt2, npt2, 1, l_colorT2, 8 );

				if (border) {
//					graphics.setColor(Color.black);
//					graphics.drawPolygon(new int[] { x + width, x + width, x }, new int[] { y, y + width, y + width }, 3);
					int lineWidth = 1;
					int isCurveClosed = 1;
					int nCurves = 2;
					cv::polylines(image, ppt2, npt2, nCurves, isCurveClosed, Scalar(0, 0, 0), lineWidth);

				}
			} else {
				l_colorT1 = getTriangleColor(x, y, 2, imageIn);
				l_colorT2 = getTriangleColor(x, y, 3, imageIn);

//				graphics.setColor(l_colorT1);
//				graphics.fillPolygon(new int[] { x, x + width, x + width }, new int[] { y, y, y + width }, 3);

				Point rook_points[1][3];
				rook_points[0][0] = Point(y, x);
				rook_points[0][1] = Point(y, x + width);
				rook_points[0][2] = Point(y + width, x + width);

				const Point* ppt[1] = { rook_points[0] };
				int npt[] = { 3 };
				fillPoly( image, ppt, npt, 1, l_colorT1, 8 );

				if (border) {
//					graphics.setColor(Color.black);
//					graphics.drawPolygon(new int[] { x, x + width, x + width }, new int[] { y, y, y + width }, 3);
					int lineWidth = 1;
					int isCurveClosed = 1;
					int nCurves = 2;
					cv::polylines(image, ppt, npt, nCurves, isCurveClosed, Scalar(0, 0, 0), lineWidth);
				}


//				graphics.setColor(l_colorT2);
//				graphics.fillPolygon(new int[] { x, x + width, x }, new int[] {y, y + width, y + width }, 3);
//				if (border) {
//					graphics.setColor(Color.black);
//					graphics.drawPolygon(new int[] { x, x + width, x }, new int[] { y, y + width, y + width }, 3);
//				}
				Point rook_points2[1][3];
				rook_points2[0][0] = Point(y, x);
				rook_points2[0][1] = Point(y + width, x + width);
				rook_points2[0][2] = Point(y + width, x);
				const Point* ppt2[1] = { rook_points2[0] };
				int npt2[] = { 3 };
				fillPoly( image, ppt2, npt2, 1, l_colorT2, 8 );

				if (border) {
					int lineWidth = 1;
					int isCurveClosed = 1;
					int nCurves = 2;
					cv::polylines(image, ppt2, npt2, nCurves, isCurveClosed, Scalar(0, 0, 0), lineWidth);

				}
			}
//			performanceMeter.stepsFinished(image.getWidth());
			t *= -1;
		}
		if (l_aux) {
			t *= -1;
		}
	}
}

Scalar getSquareColor(int aX, int aY, cv::Mat &image) {
//	Scalar( a, b, c )
//	We would be defining a BGR color such as: Blue = a, Green = b and Red = c

	int l_red = -1;
	int l_green = -1;
	int l_blue = -1;

	for (int y = 0; y < width; y++) {
		for (int x = 0; x < width; x++) {
			if (aX + x > 0 && aX + x < image.size().width && aY + y > 0
					&& aY + y < image.size().height) {
				if (l_red == -1) {
//					l_red = image.getIntComponent0(aX + x, aY + y);
//					l_green = image.getIntComponent1(aX + x, aY + y);
//					l_blue = image.getIntComponent2(aX + x, aY + y);
					//					BGR
					l_blue = image.at < cv::Vec3b > (aY + y, aX + x)[0];
					l_green = image.at < cv::Vec3b > (aY + y, aX + x)[1];
					l_red = image.at < cv::Vec3b > (aY + y, aX + x)[2];
				} else {
//					l_red = (l_red + image.getIntComponent0(aX + x, aY + y))/ 2;
//					l_green = (l_green + image.getIntComponent1(aX + x, aY + y))/ 2;
//					l_blue = (l_blue + image.getIntComponent2(aX + x, aY + y))/ 2;
					l_red = (l_red + image.at < cv::Vec3b > (aY + y, aX + x)[2])/ 2;
					l_green = (l_green + image.at < cv::Vec3b > (aY + y, aX + x)[1]) / 2;
					l_blue = (l_blue + image.at < cv::Vec3b > (aY + y, aX + x)[0]) / 2;

				}
			}
		}
	}
//	return cv::Scalar(l_red, l_green, l_blue);
//	Scalar( B,G,R )
	return cv::Scalar(l_blue, l_green, l_red, 255);
}

Scalar getTriangleColor(int aX, int aY, int tringlePos, cv::Mat &image) {
	int l_red = -1;
	int l_green = -1;
	int l_blue = -1;

	int l_xInitial = 0;
	int l_xOffSet = 0;
	int l_xOffSetInc = 0;
	int l_xInitalInc = 0;

	switch (tringlePos) {
	case 0:
		l_xInitial = 1;
		l_xOffSet = width;
		l_xOffSetInc = -1;
		l_xInitalInc = 0;
		break;
	case 1:
		l_xInitial = width - 1;
		l_xOffSet = width;
		l_xOffSetInc = 0;
		l_xInitalInc = -1;
		break;
	case 2:
		l_xInitial = 1;
		l_xOffSet = width;
		l_xOffSetInc = 0;
		l_xInitalInc = 1;
		break;
	case 3:
		l_xInitial = 1;
		l_xOffSet = 1;
		l_xOffSetInc = 1;
		l_xInitalInc = 0;
		break;

	}

	int x = l_xInitial;
	int y = 0;

	for (int w = 0; w < width - 1; w++) {
		while (x < l_xOffSet) {
			if (aX + x > 0 && aX + x < image.rows && aY + y > 0
					&& aY + y < image.cols) {
				if (l_red == -1) {
//					l_red = image.getIntComponent0(aX + x, aY + y);
//					l_green = image.getIntComponent1(aX + x, aY + y);
//					l_blue = image.getIntComponent2(aX + x, aY + y);

//					BGR
					l_blue = image.at < cv::Vec3b > (aY + y, aX + x)[0];
					l_green = image.at < cv::Vec3b > (aY + y, aX + x)[1];
					l_red = image.at < cv::Vec3b > (aY + y, aX + x)[2];

				} else {
//					l_red = (image.getIntComponent0(aX + x, aY + y))/ 2;
//					l_green = (l_green + image.getIntComponent1(aX + x, aY + y))/ 2;
//					l_blue = (l_blue + image.getIntComponent2(aX + x, aY + y))/ 2;

					l_red = (l_red + image.at < cv::Vec3b > (aY + y, aX + x)[2])/ 2;
					l_green = (l_green + image.at < cv::Vec3b > (aY + y, aX + x)[1]) / 2;
					l_blue = (l_blue + image.at < cv::Vec3b > (aY + y, aX + x)[0]) / 2;
				}
			}
			x++;
		}
		l_xInitial += l_xInitalInc;
		l_xOffSet += l_xOffSetInc;
		x = l_xInitial;
		y++;

	}
	if (l_red == -1)
		l_red = 0;
	if (l_green == -1)
		l_green = 0;
	if (l_blue == -1)
		l_blue = 0;
//	return new Color(l_red, l_green, l_blue);
	// BGR
//	return cv::Scalar(l_red, l_green, l_blue, 255);
	return cv::Scalar(l_blue, l_green, l_red, 255);
}

