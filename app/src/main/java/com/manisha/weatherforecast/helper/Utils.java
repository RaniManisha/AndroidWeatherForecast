package com.manisha.weatherforecast.helper;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by manisha on 07/04/2019.
 * Helper for dates class
 */

class Utils {

    /**
     * Given a string in our weather format, return a Date
     *
     * @param strDate
     * @return Date
     */
    @Nullable
    static Date getDate(String strDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = format.parse(strDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


}
