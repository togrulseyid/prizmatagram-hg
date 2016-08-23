package com.togrulseyid.prizmatagram.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

/**
 * Created by toghrul on 25.07.2016.
 */
public class FilterModel implements Serializable {
    private Bitmap bitmap;
    private Bitmap currentBitmap;
    private String source;

    public FilterModel() {
    }

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    public FilterModel(String source) {
        this.source = source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getSource() {
        return source;
    }

    public Bitmap getSrcBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inScaled = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(source, options);
    }

    public Bitmap getBitmapX() {
        return bitmap;
    }
}
