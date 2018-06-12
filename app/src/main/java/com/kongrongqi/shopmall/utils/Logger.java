package com.kongrongqi.shopmall.utils;

import android.util.Log;

import com.kongrongqi.shopmall.global.App;


/**
 * Created by penny on 2016/5/18 0018.
 */
public class Logger {

    public static boolean isShowLog = App.getInstance().isDebug();

    /**
     *打印info日志
     * @param objTag 可以是任意的对象，如果是Class对象，则获取类名为tag，如果是String则直接使用为tag，如果是其它对象，则获取Class再获取类名
     * @param msg
     */
    public static void i(Object objTag, String msg) {
        if (!isShowLog && objTag != null) {
            return;
        }

        String tag;

        // 把objTag转换为String
        if (objTag instanceof Class) {
            tag = ((Class) objTag).getSimpleName();
        } else if (objTag instanceof String) {
            tag = (String) objTag;
        } else {
            tag = objTag.getClass().getSimpleName();
        }
        Log.i(tag,"=========info==start=============");
        Log.i(tag, msg);
        Log.i(tag,"=========info==end===============");
    }

    /**
     * 打印error日志
     * @param objTag 可以是任意的对象，如果是Class对象，则获取类名为tag，如果是String则直接使用为tag，如果是其它对象，则获取Class再获取类名
     * @param msg
     */
    public static void e(Object objTag, String msg) {
        if (!isShowLog && objTag != null) {
            return;
        }

        String tag;

        // 把objTag转换为String
        if (objTag instanceof Class) {
            tag = ((Class) objTag).getSimpleName();
        } else if (objTag instanceof String) {
            tag = (String) objTag;
        } else {
            tag = objTag.getClass().getSimpleName();
        }

        Log.e(tag, msg);
    }


    /**
     * 打印d日志
     * @param objTag 可以是任意的对象，如果是Class对象，则获取类名为tag，如果是String则直接使用为tag，如果是其它对象，则获取Class再获取类名
     * @param msg
     */
    public static void d(Object objTag, String msg) {
        if (!isShowLog && objTag != null) {
            return;
        }

        String tag;

        // 把objTag转换为String
        if (objTag instanceof Class) {
            tag = ((Class) objTag).getSimpleName();
        } else if (objTag instanceof String) {
            tag = (String) objTag;
        } else {
            tag = objTag.getClass().getSimpleName();
        }

        Log.i(tag, msg);
    }

}
