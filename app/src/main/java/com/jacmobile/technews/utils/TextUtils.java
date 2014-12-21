package com.jacmobile.technews.utils;

/**
 * Created by acorll on 12/20/14.
 */
public class TextUtils
{
    public static String cleanNewsDate(String date)
    {
        date = date.contains(" GMT") ?
                date.replace(" GMT", "") :
                date;
        return date.contains(" +0000") ?
                date.replace(" +0000", "") :
                date;
    }
}
