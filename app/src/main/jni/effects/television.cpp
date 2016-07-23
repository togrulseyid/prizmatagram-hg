/*
 * television.c
 *
 *  Created on: 26 iyn 2016
 *      Author: toghrul
 */

#include "effects/television.h"

void television(cv::Mat &matIn) {

	cv::Mat matOut = matIn.clone();

	int height, width, step, channels;
	uchar *data, *data2;

	height = matIn.size().height;
	width = matIn.size().width;
	step = matIn.step;
	channels = matIn.channels();
	data = (uchar *) matOut.data;
	data2 = (uchar *) matIn.data;

	for (int x = 0; x < height; x++) {
		for (int y = 0; y < width; y += 3) {
			for (int k = 0; k < channels; k++) {

				for (int w = 0; w < 3; w++) {
					if (y + w < width) {
						data2[x * step + (y + w) * channels + k] += (data[x
								* step + (y + w) * channels + k] / 2);
					}
				}
			}
		}
	}
}
int valid_interval(int value) {
	if (value < 0)
		return 0;
	if (value > 255)
		return 255;
	return value;
}

void television2(cv::Mat &matIn) {

	cv::Mat matOut = matIn.clone();

	int height, width, step, channels;
	uchar *data, *data2;

	height = matIn.size().height;
	width = matIn.size().width;
	step = matIn.step;
	channels = matIn.channels();
	data2 = (uchar *) matIn.data;
	data = (uchar *) matOut.data;
	int distance = 10;

	///----------------------------

//	LOGD("channels: %d", channels);
//	int r, g, b;
	int color[4];
	for (int x = 0; x < height; x += distance) {
		for (int y = 0; y < width; y += 1) {

			color[0] = 0;
			color[1] = 0;
			color[2] = 0;
			color[3] = 0;

			for (int w = 0; w < 3; w++) {
				if (y + w < width) {
					color[0] += data[x * step + (y + w) * channels + 0] / 2;
					color[1] += data[x * step + (y + w) * channels + 1] / 2;
					color[2] += data[x * step + (y + w) * channels + 2] / 2;
//					color[3] += data[x * step + (y + w) * channels + 3] / 2;
//					LOGD("Color: %d" , data[x * step + (y + w) * channels + 3]);

//					data2[x * step + (y + w) * channels + 1]  += data[x * step + (y + w) * channels + 1] / 2;
//					data2[x * step + (y + w) * channels + 2]  += data[x * step + (y + w) * channels + 2] / 2;
//					data2[x * step + (y + w) * channels + 3]  += data[x * step + (y + w) * channels + 3] / 2;
//					data2[x * step + (y + w) * channels + 4]  += data[x * step + (y + w) * channels + 4] / 2;

				}
			}

			color[0] = valid_interval(color[0]);
			color[1] = valid_interval(color[1]);
			color[2] = valid_interval(color[2]);
			color[3] = valid_interval(color[3]);

			int w = 3;
			if (y + w < width) {
				data2[x * step + (y + 0) * channels + 0] = color[1];
				data2[x * step + (y + 0) * channels + 1] = 0;
				data2[x * step + (y + 0) * channels + 2] = 0;

				data2[x * step + (y + 1) * channels + 0] = 0;
				data2[x * step + (y + 1) * channels + 1] = color[0];
				data2[x * step + (y + 1) * channels + 2] = 0;

				data2[x * step + (y + 2) * channels + 0] = 0;
				data2[x * step + (y + 2) * channels + 1] = 0;
				data2[x * step + (y + 2) * channels + 2] = color[2];
			}
		}
	}
}

void television3(cv::Mat &matIn) {

	cv::Mat matOut = matIn.clone();

	int height, width, step, channels;
	uchar *data, *data2;

	height = matIn.size().height;
	width = matIn.size().width;
	step = matIn.step;
	channels = matIn.channels();
	data2 = (uchar *) matIn.data;
	data = (uchar *) matOut.data;
	int distance = 8;

	int color[4];
	for (int x = 0; x < height; x += distance) {
		for (int y = 0; y < width; y++) {
			for (int z = 0; z < channels; z++) {
				color[z] = 0;

				for (int w = 0; w < 3; w++) {
					if (y + w < width) {
						color[z] += data[x * step + (y + w) * channels + z] / 2;
					}
				}

				color[0] = valid_interval(color[0]);

				if (channels == 4) {
					for (int w = 0; w < 3; w++) {
						if (y + w < width) {
							if (w == 0) {
								data2[x * step + (y + w) * channels] = color[0];
								data2[x * step + (y + w) * channels + 1] = 0;
								data2[x * step + (y + w) * channels + 2] = 0;
								data2[x * step + (y + w) * channels + 3] = 0;
							} else if (w == 1) {
								data2[x * step + (y + w) * channels] = 0;
								data2[x * step + (y + w) * channels + 1] =
										color[1];
								data2[x * step + (y + w) * channels + 2] = 0;
								data2[x * step + (y + w) * channels + 3] = 0;
							} else if (w == 2) {
								data2[x * step + (y + w) * channels] = 0;
								data2[x * step + (y + w) * channels + 1] = 0;
								data2[x * step + (y + w) * channels + 2] =
										color[2];
								data2[x * step + (y + w) * channels + 3] = 0;
							}
						}
					}
				}
			}
		}
	}
}

void television4_dots(cv::Mat &matIn) {

	cv::Mat matOut = matIn.clone();

	int height, width, step, channels;
	uchar *data, *data2;

	height = matIn.size().height;
	width = matIn.size().width;
	step = matIn.step;
	channels = matIn.channels();
	data2 = (uchar *) matIn.data;
	data = (uchar *) matOut.data;
	int distance = 5;

	int color[4];
	for (int x = 0; x < height; x += distance) {
		for (int y = 0; y < width; y += distance) {
			for (int z = 0; z < channels; z++) {
				color[z] = 0;

				for (int w = 0; w < 3; w++) {
					if (y + w < width) {
						color[z] += data[x * step + (y + w) * channels + z] / 2;
					}
				}

				color[0] = valid_interval(color[0]);

				if (channels == 4) {
					for (int w = 0; w < 3; w++) {
						if (y + w < width) {
							if (w == 0) {
								data2[x * step + (y + w) * channels] = color[0];
								data2[x * step + (y + w) * channels + 1] = 0;
								data2[x * step + (y + w) * channels + 2] = 0;
								data2[x * step + (y + w) * channels + 3] = 0;
							} else if (w == 1) {
								data2[x * step + (y + w) * channels] = 0;
								data2[x * step + (y + w) * channels + 1] =
										color[1];
								data2[x * step + (y + w) * channels + 2] = 0;
								data2[x * step + (y + w) * channels + 3] = 0;
							} else if (w == 2) {
								data2[x * step + (y + w) * channels] = 0;
								data2[x * step + (y + w) * channels + 1] = 0;
								data2[x * step + (y + w) * channels + 2] =
										color[2];
								data2[x * step + (y + w) * channels + 3] = 0;
							}
						}
					}
				}
			}
		}
	}
}



/**
 * Native Television Effect
 * */

double wavelength = 40;
double intensity = 0.5;
double decolorisation = 0.7;

void nativeTelevision(cv::Mat &img) {

//	LOGD("Image Type: %d\n", img.type());
//	cv::Mat imgOut = cv::Mat::zeros(img.size(), img.type());

	cv::Mat imgOut = img.clone();

//	LOGD("img.rows: %d - img.cols: %d - img.cols + img.rows: %d\n", img.rows, img.cols, (img.cols + img.rows));

	int width = imgOut.size().width;
	int height = imgOut.size().height;

//	for (int i = 0; i < img.rows; i++) {
//		for (int j = 0; j < img.cols + img.rows; j++) { //

	for (int i = 0; i < imgOut.size().height; ++i) {
		for (int j = 0; j < imgOut.size().width/2*3; ++j) {

			// desaturate the image
			double meanColor = (imgOut.at <cv::Vec3b> (i, j)[0] + imgOut.at<cv::Vec3b> (i, j)[1] + imgOut.at<cv::Vec3b> (i, j)[3]) / 3.0;
			cv::Vec3b newColor;
			newColor[0] = (1 - decolorisation) * imgOut.at <cv::Vec3b> (i, j)[0] + decolorisation * meanColor;
			newColor[1] = (1 - decolorisation) * imgOut.at <cv::Vec3b> (i, j)[1] + decolorisation * meanColor;
			newColor[2] = (1 - decolorisation) * imgOut.at <cv::Vec3b> (i, j)[2] + decolorisation * meanColor;

			// boost the blue channel
			double coeff = 0.5 + sin((2 * M_PI * i) / wavelength) / 2.0;
			newColor[0] = newColor[0] + intensity * coeff * (255 - newColor[0]);

			img.at < cv::Vec3b > (i, j) = newColor;
		}
	}

//	LOGD("Oldu bitdi nativeTelevision");

	imgOut.release();


}


