//
// Created by toghrul on 26.07.2016.
//

#ifndef PRIZMATAGRAM_PENCILSKETCH_H
#define PRIZMATAGRAM_PENCILSKETCH_H

#include "core/std_head.h"

// Convert the given photo into a cartoon-like or painting-like image.
// Set sketchMode to true if you want a line drawing instead of a painting.
// Set alienMode to true if you want alien skin instead of human.
// Set evilMode to true if you want an "evil" character instead of a "good" character.
// Set debugType to 1 to show where skin color is taken from, and 2 to show the skin mask in a new window (for desktop).
void cartoonifyImage(Mat srcColor, Mat dst, bool sketchMode, bool alienMode, bool evilMode, int debugType);


// Draw an anti-aliased face outline, so the user knows where to put their face.
void drawFaceStickFigure(Mat dst);


void changeFacialSkinColor(Mat smallImgBGR, Mat bigEdges, int debugType);
void removePepperNoise(Mat &mask);
void drawFaceStickFigure(Mat dst);


#endif //PRIZMATAGRAM_PENCILSKETCH_H
