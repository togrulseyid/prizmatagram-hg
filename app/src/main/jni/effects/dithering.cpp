/*
 * Dithering.h
 *
 *  Created on: 26 iyn 2016
 *      Author: toghrul
 */

#include "effects/dithering.h"

const int DOT_AREA = 5;

int arrDither[] = { 167,200,230,216,181,
                    94,72,193,242,232,
                    36,52,222,167,200,
                    181,126,210,94,72,
                    232,153,111,36,52
                  };

int dithering(cv::Mat &mat) {
	int height, width, step, channels;
	uchar *data;
	int k, l_x, l_y, l_grayIntensity;

	height    = mat.size().height;
	width     = mat.size().width;
	step      = mat.step;
	channels  = mat.channels();
	data      = (uchar *)mat.data;

	for (int a_x = 0; a_x < height; a_x += DOT_AREA) {
		for (int a_y = 0; a_y < width; a_y += DOT_AREA) {
			for (k = 0; k < channels; k++) {
				for (int x = 0; x < DOT_AREA * DOT_AREA; x++) {
					l_x = x % DOT_AREA;
					l_y = x / DOT_AREA;

					if (a_x + l_x < height && a_y + l_y < width) {
						l_grayIntensity = 255-data[(a_x+l_x) * step + (a_y+l_y) * channels + k];

						if (l_grayIntensity > arrDither[x]) {
							data[(a_x + l_x) * step + (a_y + l_y) * channels + k] = 0;
						} else {
							data[(a_x + l_x) * step + (a_y + l_y) * channels + k] = 255;
						}
					}
				}
			}
		}
	}

	return -1;
}


int dithering(cv::Mat &mat, int DOT_AREA) {
	int height, width, step, channels;
	uchar *data;
	int k, l_x, l_y, l_grayIntensity;

	height    = mat.size().height;
	width     = mat.size().width;
	step      = mat.step;
	channels  = mat.channels();
	data      = (uchar *)mat.data;

	for (int a_x = 0; a_x < height; a_x += DOT_AREA) {
		for (int a_y = 0; a_y < width; a_y += DOT_AREA) {
			for (k = 0; k < channels; k++) {
				for (int x = 0; x < DOT_AREA * DOT_AREA; x++) {
					l_x = x % DOT_AREA;
					l_y = x / DOT_AREA;

					if (a_x + l_x < height && a_y + l_y < width) {
						l_grayIntensity = 255-data[(a_x+l_x) * step + (a_y+l_y) * channels + k];

						if (l_grayIntensity > arrDither[x]) {
							data[(a_x + l_x) * step + (a_y + l_y) * channels + k] = 0;
						} else {
							data[(a_x + l_x) * step + (a_y + l_y) * channels + k] = 255;
						}
					}
				}
			}
		}
	}

	return -1;
}


