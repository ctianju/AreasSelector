package com.mobile.areasselectapp;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 获取屏幕的宽高
 */
public class DensityUtil {

    public static int[] getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    public static int getScreenHeight(Context context) {
        return getScreenSize(context)[1];
    }

    public static int getScreenWidth(Context context) {
        return getScreenSize(context)[0];
    }

}
