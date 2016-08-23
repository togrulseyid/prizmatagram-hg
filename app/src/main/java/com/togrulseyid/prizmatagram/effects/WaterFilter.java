package com.togrulseyid.prizmatagram.effects;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by toghrul on 03.08.2016.
 */
public class WaterFilter extends TransformFilter{

    private float wavelength = 16;
    private float amplitude = 10;
    private float phase = 0;
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private float radius = 50;

    private float radius2 = 0;
    private float icentreX;
    private float icentreY;

    public WaterFilter() {
        setEdgeAction(CLAMP);
    }

    /**
     * Set the radius of the effect.
     *
     * @param radius the radius
     * @min-value 0
     * @see #getRadius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * Get the radius of the effect.
     *
     * @return the radius
     * @see #setRadius
     */
    public float getRadius() {
        return radius;
    }

    public Bitmap filter(Bitmap src, Bitmap dst) {
        icentreX = src.getWidth() * centreX-50;
        icentreY = src.getHeight() * centreY-50;
        if (radius == 0)
            radius = Math.min(icentreX, icentreY);
        radius2 = radius * radius;



        if (src == null) {
            Log.d("testA", "src is null");
        } else {
            Log.d("testA", "src is not null");
        }
        return super.filter(src, dst);
    }

    protected void transformInverse(int x, int y, float[] out) {
        float dx = x - icentreX;
        float dy = y - icentreY;
        float distance2 = dx * dx + dy * dy;
        if (distance2 > radius2) {
            out[0] = x;
            out[1] = y;
        } else {
            float distance = (float) Math.sqrt(distance2);
            float amount = amplitude * (float) Math.sin(distance / wavelength * ImageMath.TWO_PI - phase);
            amount *= (radius - distance) / radius;
            if (distance != 0)
                amount *= wavelength / distance;
            out[0] = x + dx * amount;
            out[1] = y + dy * amount;
        }
    }

    public String toString() {
        return "Distort/Water Ripples...";
    }

}
