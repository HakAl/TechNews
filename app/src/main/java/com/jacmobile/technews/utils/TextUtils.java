package com.jacmobile.technews.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

    //parse description for any image or video links
    public static String getImageUrl(String description)
    {
        if (description.contains("<img ")) {
            String img = description.substring(description.indexOf("<img "));
            img = img.substring(img.indexOf("src=") + 5);
            int indexOf = img.indexOf("\"");
            img = img.substring(0, indexOf);
            return img;
        }
        return "";
    }

    final static SimpleDateFormat dateFormats[] = new SimpleDateFormat[] {
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US),
            new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("EEE, d MMM yy HH:mm z", Locale.US),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm z", Locale.US),
            new SimpleDateFormat("EEE d MMM yy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("EEE d MMM yy HH:mm z", Locale.US),
            new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("EEE d MMM yyyy HH:mm z", Locale.US),
            new SimpleDateFormat("d MMM yy HH:mm z", Locale.US),
            new SimpleDateFormat("d MMM yy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("d MMM yyyy HH:mm z", Locale.US),
            new SimpleDateFormat("d MMM yyyy HH:mm:ss z", Locale.US),

            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()),
            new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z", Locale.getDefault()),
            new SimpleDateFormat("EEE, d MMM yy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.getDefault()),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("EEE d MMM yy HH:mm:ss z", Locale.getDefault()),
            new SimpleDateFormat("EEE d MMM yy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss z", Locale.getDefault()),
            new SimpleDateFormat("EEE d MMM yyyy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("d MMM yy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("d MMM yy HH:mm:ss z", Locale.getDefault()),
            new SimpleDateFormat("d MMM yyyy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("d MMM yyyy HH:mm:ss z", Locale.getDefault()),
    };

    static Date parseDate(String date) {
        for (SimpleDateFormat format : dateFormats) {
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                return format.parse(date);
            } catch (ParseException e) {
            }

            // try it again in english
            try {
                SimpleDateFormat enUSFormat = new SimpleDateFormat(format.toPattern(), Locale.US);
                return enUSFormat.parse(date);
            } catch (ParseException e) {
            }
        }

        return null;
    }
}
