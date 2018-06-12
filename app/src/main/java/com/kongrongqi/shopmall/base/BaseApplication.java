package com.kongrongqi.shopmall.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;

import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.UmengUtils;

import java.util.Iterator;
import java.util.List;


/**
 * Created by penny on 2017/5/16 0016.
 */

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication sApplication;

    private static Context sContext;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();

        // 同步和判空操作仅仅是为了忽略静态代码检查工具报的警告
        synchronized (BaseApplication.class) {
            if (null == sApplication) {
                sApplication = BaseApplication.this;
            }
            if (null == sContext) {
                sContext = BaseApplication.this;
            }
        }

        App.getInstance().initApplication(this);
        registerActivityLifecycleCallbacks(mLifecycleCallbacks);

        //友盟初始化
        UmengUtils.initUmeng(this);
    }

    public static Context getContext() {
        return sContext;
    }

    public static Application getApplication() {
        return sApplication;
    }

    private ActivityLifecycleCallbacks mLifecycleCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Logger.d(activity.getComponentName()
                    .getShortClassName(), "执行onActivityCreated");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Logger.d(activity.getComponentName()
                    .getShortClassName(), "执行onActivityStarted");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Logger.d(activity.getComponentName()
                    .getShortClassName(), "执行onActivityResumed");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Logger.d(activity.getComponentName()
                    .getShortClassName(), "执行onActivityPaused");
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Logger.d(activity.getComponentName()
                    .getShortClassName(), "执行onActivityStopped");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Logger.d(activity.getComponentName()
                    .getShortClassName(), "执行onActivitySaveInstanceState");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            // TODO: 2017/4/1 在这里销毁Activity
            Logger.d(activity.getComponentName()
                    .getShortClassName(), "执行onActivityDestroyed");
        }
    };

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
