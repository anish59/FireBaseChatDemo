package com.firebasechatdemo.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Functions {

    public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    public static String getCurrentUtctime(SimpleDateFormat df) {
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(new Date());
    }

    public static String convertUtCcurrentTime(String UTCTime, SimpleDateFormat inputFormat, SimpleDateFormat outputFormat) throws ParseException {


        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = inputFormat.parse(UTCTime);

        outputFormat.setTimeZone(TimeZone.getDefault());
        return outputFormat.format(date);
    }
}
