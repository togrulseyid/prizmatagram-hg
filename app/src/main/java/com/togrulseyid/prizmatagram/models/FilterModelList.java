package com.togrulseyid.prizmatagram.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by toghrul on 25.07.2016.
 */
public class FilterModelList implements Serializable {
    private List<FilterModel> filterModels;
    private String bitmap;

    public FilterModelList() {

    }

    public FilterModelList(List<FilterModel> filterModels, String bitmap) {
        this.filterModels = filterModels;
        this.bitmap = bitmap;
    }

    public List<FilterModel> getFilterModels() {
        return filterModels;
    }

    public void setFilterModels(List<FilterModel> filterModels) {
        this.filterModels = filterModels;
    }

    public Bitmap getBitmap() {
//        Bitmap bitmap = BitmapFactory.decodeFile(bitmap);
        return BitmapFactory.decodeFile(bitmap);
//        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }
}
