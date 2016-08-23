package com.togrulseyid.prizmatagram.effects;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by toghrul on 03.08.2016.
 */
public abstract class TransformFilter {

    /**
     * Treat pixels off the edge as zero.
     */
    public final static int ZERO = 0;

    /**
     * Clamp pixels to the image edges.
     */
    public final static int CLAMP = 1;

    /**
     * Wrap pixels off the edge onto the oppsoite edge.
     */
    public final static int WRAP = 2;

    /**
     * Clamp pixels RGB to the image edges, but zero the alpha. This prevents gray borders on your image.
     */
    public final static int RGB_CLAMP = 3;

    /**
     * Use nearest-neighbout interpolation.
     */
    public final static int NEAREST_NEIGHBOUR = 0;

    /**
     * Use bilinear interpolation.
     */
    public final static int BILINEAR = 1;

    /**
     * The action to take for pixels off the image edge.
     */
    protected int edgeAction = RGB_CLAMP;

    /**
     * The type of interpolation to use.
     */
    protected int interpolation = BILINEAR;

    /**
     * The output image rectangle.
     */
    protected Rectangle transformedSpace;

    /**
     * The input image rectangle.
     */
    protected Rectangle originalSpace;

    /**
     * Set the action to perform for pixels off the edge of the image.
     *
     * @param edgeAction one of ZERO, CLAMP or WRAP
     * @see #getEdgeAction
     */
    public void setEdgeAction(int edgeAction) {
        this.edgeAction = edgeAction;
    }

    /**
     * Get the action to perform for pixels off the edge of the image.
     *
     * @return one of ZERO, CLAMP or WRAP
     * @see #setEdgeAction
     */
    public int getEdgeAction() {
        return edgeAction;
    }

    /**
     * Set the type of interpolation to perform.
     *
     * @param interpolation one of NEAREST_NEIGHBOUR or BILINEAR
     * @see #getInterpolation
     */
    public void setInterpolation(int interpolation) {
        this.interpolation = interpolation;
    }

    /**
     * Get the type of interpolation to perform.
     *
     * @return one of NEAREST_NEIGHBOUR or BILINEAR
     * @see #setInterpolation
     */
    public int getInterpolation() {
        return interpolation;
    }

    /**
     * Inverse transform a point. This method needs to be overriden by all subclasses.
     *
     * @param x   the X position of the pixel in the output image
     * @param y   the Y position of the pixel in the output image
     * @param out the position of the pixel in the input image
     */
    protected abstract void transformInverse(int x, int y, float[] out);


    public Bitmap filter(Bitmap src, Bitmap dst) {
        int width = src.getWidth();
        int height = src.getHeight();


        if (src == null) {
            Log.d("testA", "src ins is null");
        } else {
            Log.d("testA", "src ins is not null");
        }

        originalSpace = new Rectangle(0, 0, width, height);
        transformedSpace = new Rectangle(0, 0, width, height);
/*
        if (dst == null) {
            ColorModel dstCM = src.getColorModel();
            dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(transformedSpace.width, transformedSpace.height), dstCM.isAlphaPremultiplied(), null);
        }*/

        int[] inPixels = getRGB(src, 0, 0, width, height, null);

        if (interpolation == NEAREST_NEIGHBOUR)
            return filterPixelsNN(dst, width, height, inPixels, transformedSpace);

        int srcWidth = width;
        int srcHeight = height;
        int srcWidth1 = width - 1;
        int srcHeight1 = height - 1;
        int outWidth = transformedSpace.width;
        int outHeight = transformedSpace.height;
        int outX, outY;
        int[] outPixels = new int[outWidth];

        outX = transformedSpace.x;
        outY = transformedSpace.y;
        float[] out = new float[2];

        for (int y = 0; y < outHeight; y++) {
            for (int x = 0; x < outWidth; x++) {
                transformInverse(outX + x, outY + y, out);
                int srcX = (int) Math.floor(out[0]);
                int srcY = (int) Math.floor(out[1]);
                float xWeight = out[0] - srcX;
                float yWeight = out[1] - srcY;
                int nw, ne, sw, se;

                if (srcX >= 0 && srcX < srcWidth1 && srcY >= 0 && srcY < srcHeight1) {
                    // Easy case, all corners are in the image
                    int i = srcWidth * srcY + srcX;
                    nw = inPixels[i];
                    ne = inPixels[i + 1];
                    sw = inPixels[i + srcWidth];
                    se = inPixels[i + srcWidth + 1];
                } else {
                    // Some of the corners are off the image
                    nw = getPixel(inPixels, srcX, srcY, srcWidth, srcHeight);
                    ne = getPixel(inPixels, srcX + 1, srcY, srcWidth, srcHeight);
                    sw = getPixel(inPixels, srcX, srcY + 1, srcWidth, srcHeight);
                    se = getPixel(inPixels, srcX + 1, srcY + 1, srcWidth, srcHeight);
                }
                outPixels[x] = ImageMath.bilinearInterpolate(xWeight, yWeight, nw, ne, sw, se);
            }
            setRGB(dst, 0, y, transformedSpace.width, 1, outPixels);
        }
        return dst;
    }

    final private int getPixel(int[] pixels, int x, int y, int width, int height) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            switch (edgeAction) {
                case ZERO:
                default:
                    return 0;
                case WRAP:
                    return pixels[(ImageMath.mod(y, height) * width) + ImageMath.mod(x, width)];
                case CLAMP:
                    return pixels[(ImageMath.clamp(y, 0, height - 1) * width) + ImageMath.clamp(x, 0, width - 1)];
                case RGB_CLAMP:
                    return pixels[(ImageMath.clamp(y, 0, height - 1) * width) + ImageMath.clamp(x, 0, width - 1)] & 0x00ffffff;
            }
        }
        return pixels[y * width + x];
    }

    protected Bitmap filterPixelsNN(Bitmap dst, int width, int height, int[] inPixels, Rectangle transformedSpace) {
        int outWidth = transformedSpace.width;
        int outHeight = transformedSpace.height;
        int srcX, srcY;
        int[] outPixels = new int[outWidth];

        int[] rgb = new int[4];
        float[] out = new float[2];

        for (int y = 0; y < outHeight; y++) {
            for (int x = 0; x < outWidth; x++) {
                transformInverse(transformedSpace.x + x, transformedSpace.y + y, out);
                srcX = (int) out[0];
                srcY = (int) out[1];
                // int casting rounds towards zero, so we check out[0] < 0, not srcX < 0
                if (out[0] < 0 || srcX >= width || out[1] < 0 || srcY >= height) {
                    int p;
                    switch (edgeAction) {
                        case ZERO:
                        default:
                            p = 0;
                            break;
                        case WRAP:
                            p = inPixels[(ImageMath.mod(srcY, height) * width) + ImageMath.mod(srcX, width)];
                            break;
                        case CLAMP:
                            p = inPixels[(ImageMath.clamp(srcY, 0, height - 1) * width) + ImageMath.clamp(srcX, 0, width - 1)];
                            break;
                        case RGB_CLAMP:
                            p = inPixels[(ImageMath.clamp(srcY, 0, height - 1) * width) + ImageMath.clamp(srcX, 0, width - 1)] & 0x00ffffff;
                    }
                    outPixels[x] = p;
                } else {
                    int i = width * srcY + srcX;
                    rgb[0] = inPixels[i];
                    outPixels[x] = inPixels[i];
                }
            }
            setRGB(dst, 0, y, transformedSpace.width, 1, outPixels);
        }
        return dst;
    }


    public void setRGB(Bitmap bitmap, int x, int y, int width, int height, int[] pixels) {

        bitmap.setPixels(pixels, 0, width, x, y, width, height);
    }

    public int[] getRGB(Bitmap image, int x, int y, int width, int height, int[] pixels) {

        pixels = new int[width * height];
        image.getPixels(pixels, 0, width, 0, 0, width, height);

        int R, G, B, index;

        for (int yy = 0; yy < height; yy++) {
            for (int xx = 0; xx < width; xx++) {
                index = yy * width + xx;
                R = (pixels[index] >> 16) & 0xff;     //bitwise shifting
                G = (pixels[index] >> 8) & 0xff;
                B = pixels[index] & 0xff;

                //R,G.B - Red, Green, Blue
                //to restore the values after RGB modification, use
                //next statement
                pixels[index] = 0xff000000 | (R << 16) | (G << 8) | B;
            }
        }
        return pixels;
    }
}