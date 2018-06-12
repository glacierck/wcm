package com.kongrongqi.shopmall.crashMonitor;

import android.content.Context;
import android.content.Intent;

import com.kongrongqi.shopmall.crashMonitor.ui.activity.CrashListActivity;
import com.kongrongqi.shopmall.crashMonitor.ui.activity.CrashShowActivity;

/**
 * 创建日期：2017/6/8 0008 on 15:13
 * 作者:penny
 */
public class CrashMonitor {
    public static void init(Context context, boolean isDebug) {
        ExceptionHandler crash = ExceptionHandler.getInstance();
        crash.init(context, isDebug);
    }

    public static void startCrashListPage(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), CrashListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }


    public static void startCrashShowPage(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), CrashShowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }
}
