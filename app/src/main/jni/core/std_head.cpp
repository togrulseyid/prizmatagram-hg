#include "core/std_head.h"

/*
 * std_head.cpp
 *
 *  Created on: 26 iyn 2016
 *      Author: toghrul
 */

void toGrayscale(cv::Mat &mat) {
	cv::Mat gray;
	cv::cvtColor(mat, gray, CV_RGB2GRAY);
	cv::cvtColor(gray, mat, CV_GRAY2RGB);
	gray.release();
}


cv::Mat shiftFrame(cv::Mat frame, int pixels, Direction direction) {
	//create a same sized temporary Mat with all the pixels flagged as invalid (-1)
	cv::Mat temp = cv::Mat::zeros(frame.size(), frame.type());

	switch (direction) {
	case (ShiftUp):
		frame(cv::Rect(0, pixels, frame.cols, frame.rows - pixels)).copyTo(temp(cv::Rect(0, 0, temp.cols, temp.rows - pixels)));
		break;
	case (ShiftRight):
		frame(cv::Rect(0, 0, frame.cols - pixels, frame.rows)).copyTo(temp(cv::Rect(pixels, 0, frame.cols - pixels, frame.rows)));
		break;
	case (ShiftDown):
		frame(cv::Rect(0, 0, frame.cols, frame.rows - pixels)).copyTo(temp(cv::Rect(0, pixels, frame.cols, frame.rows - pixels)));
		break;
	case (ShiftLeft):
		frame(cv::Rect(pixels, 0, frame.cols - pixels, frame.rows)).copyTo(temp(cv::Rect(0, 0, frame.cols - pixels, frame.rows)));
		break;
	default:
		std::cout << "Shift direction is not set properly" << std::endl;
	}

	return temp;
}
