package com.togrulseyid.prizmatagram.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;

/**
 * Created by toghrul on 25.07.2016.
 */
public class Utils {

    public static final String IDENTIFIER_STRING = "string";
    public static final String IDENTIFIER_COLOR = "color";
    public static final String IDENTIFIER_DRAWABLE = "drawable";

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * Load Resource String by Id
     *
     * @param context Context
     * @param resName  resource name
     * @return Resource ID
     */
    public static int getResId(Context context, String resName, String type) {
        try {
            Resources r = context.getResources();
            int drawableId = r.getIdentifier(resName, type, context.getPackageName());
            return drawableId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * get uri to drawable or any other resource type if u wish
     * @param context - context
     * @param drawableId - drawable res id
     * @return - uri
     */
    public static final Uri getUriToDrawable(@NonNull Context context, @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

}
