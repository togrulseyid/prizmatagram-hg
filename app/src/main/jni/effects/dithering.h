/*
 * Dithering.h
 *
 *  Created on: 26 iyn 2016
 *      Author: toghrul
 */

#ifndef DITHERING_H_
#define DITHERING_H_

#include "core/std_head.h"

int dithering(cv::Mat &mat);
int dithering(cv::Mat &mat, int DOT_AREA);

#endif /* DITHERING_H_ */
