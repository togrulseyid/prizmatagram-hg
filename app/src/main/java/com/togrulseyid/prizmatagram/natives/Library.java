package com.togrulseyid.prizmatagram.natives;

import android.graphics.Bitmap;

import com.togrulseyid.nativelibrary.BitmapFilter;

/**
 * Created by toghrul on 10.08.2016.
 */
public class Library {

    private Bitmap changeBitmap;
    private Bitmap originBitmap;

    public Library(Bitmap originBitmap) {
        this.originBitmap = originBitmap;
    }

    public Bitmap getBitmap() {
        return changeBitmap;
    }

    public void applyStyle(int styleNo) {
        switch (styleNo) {
            case BitmapFilter.AVERAGE_BLUR_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.AVERAGE_BLUR_STYLE, 5); // maskSize, must odd
                break;
            case BitmapFilter.GAUSSIAN_BLUR_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.GAUSSIAN_BLUR_STYLE, 1.2); // sigma
                break;
            case BitmapFilter.SOFT_GLOW_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.SOFT_GLOW_STYLE, 0.6);
                break;
            case BitmapFilter.LIGHT_STYLE:
                int width = originBitmap.getWidth();
                int height = originBitmap.getHeight();
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.LIGHT_STYLE, width / 3, height / 2, width / 2);
                break;
            case BitmapFilter.LOMO_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.LOMO_STYLE,
                        (originBitmap.getWidth() / 2.0) * 95 / 100.0);
                break;
            case BitmapFilter.NEON_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.NEON_STYLE, 200, 100, 50);
                break;
            case BitmapFilter.PIXELATE_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.PIXELATE_STYLE, 10);
                break;
            case BitmapFilter.MOTION_BLUR_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.MOTION_BLUR_STYLE, 10, 1);
                break;
            case BitmapFilter.OIL_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.OIL_STYLE, 5);
                break;
            default:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, styleNo);
                break;
        }
    }
}
