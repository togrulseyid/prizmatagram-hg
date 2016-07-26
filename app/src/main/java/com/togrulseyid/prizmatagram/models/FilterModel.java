package com.togrulseyid.prizmatagram.models;

import java.io.Serializable;

/**
 * Created by toghrul on 25.07.2016.
 */
public class FilterModel implements Serializable {
    public FilterModel() {
    }

    public FilterModel(int id, String image) {
        this.id = id;
        this.image = image;
    }

    public int id;
    public String image;
}
