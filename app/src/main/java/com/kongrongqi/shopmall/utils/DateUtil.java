package com.kongrongqi.shopmall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 创建日期：2017/6/8 0008 on 15:42
 * 作者:penny
 */
public class DateUtil {

    public static String dataToTime(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
}
