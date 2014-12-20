package com.jacmobile.technews.utils;

/**
 * Created by acorll on 12/20/14.
 */
public class TextUtils
{
    public static String formatNewsDate(String date)
    {
        return date.contains(" GMT") ?
                date.replace(" GMT", "") :
                date;
    }
}
