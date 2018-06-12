package com.kongrongqi.shopmall.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.kongrongqi.shopmall.R;

/**
 * 作者：luq on 2016/9/13 14:46
 * 功能：dp转px，px转sp
 */
public class CommonUtils {
    public static int dp2px(Context context, int dpValue) {
        // 获取密度比
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (density * dpValue + 0.5f);
        return px;
    }
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕尺寸
     *
     * @param activity Activity
     * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
     */
    public static int[] getScreenSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        // 有存储的SDCard
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public static int strToInt(String str) {
        int i = 0;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {

        }
        return i;
    }

    /**
     * 去掉特殊符号
     *
     * @param s
     * @return
     */
    public static String format(String s) {
        String str = s
                .replaceAll(
                        "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&;*（）——+|{}【】‘；：”“’。，、？|-]",
                        "");
        return str;
    }

    public static String formatlist(String s) {
        String str = s
                .replaceAll(
                        "[`~!@#$%^&*()+=|{}':;'\\[\\]<>/?~！@#￥%……&;*（）——+|{}【】‘；：”“’。，、？|-]",
                        "");
        return str;
    }

    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 判断软键盘是否弹出
     */
    public static boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    public static double convert(double value) {
        long i = Math.round(value * 100);
        double ret = i / 100.0;
        return ret;
    }

    public static String judgeDistanceUtils(int distance) {
        if (distance < 100) {
            return 100 + "米以内";
        } else if (distance > 10000) {
            return 10 + "千米以外";
        }
        return distance + "米";
    }


    public static String calculateDistance(double distance) {
        if (distance < 1000) {
            return distance + "米";
        } else {
            double newDouDidtance = distance / 1000;
            String strDistance = newDouDidtance + "KM";
            return strDistance;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static String getDataStrToMillis(Long now) {
        String format = "";
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(now);
            format = formatter.format(calendar.getTime());
        } catch (Exception e) {
            format = "";
        }
        return format;
    }

    /** edittext只能输入数值的时候做最大最小的限制 */
    public static void setRegion(final EditText edit, final int MIN_MARK, final int MAX_MARK) {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 0) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
//                        int num = Integer.parseInt(s.toString());
                        int num =StringUtils.getStrToInt(s.toString());
                        if (num > MAX_MARK) {
                            s = String.valueOf(MAX_MARK);
                            edit.setText(s);
                        } else if (num < MIN_MARK) {
                            s = String.valueOf(MIN_MARK);
                            edit.setText(s);
                        }
                        edit.setSelection(s.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.equals("")) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        int markVal = 0;
                        try {
                            markVal = Integer.parseInt(s.toString());
                        } catch (NumberFormatException e) {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK) {
                            edit.setText(String.valueOf(MAX_MARK));
                            edit.setSelection(String.valueOf(MAX_MARK).length());
                        }
                        return;
                    }
                }
            }
        });
    }

}
