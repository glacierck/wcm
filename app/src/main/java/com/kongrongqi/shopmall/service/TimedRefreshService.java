package com.kongrongqi.shopmall.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.utils.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时刷新页面
 */

public class TimedRefreshService extends Service {

    private ContainerActivity activity;
    private UpdateProgress updateProgress;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new TimedRefreshBuild();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        updateProgress = new UpdateProgress();
        updateProgress.run();
    }

    /**
     * @author Administrator
     *         线程用来改变seekbar进度的大小
     */
    class UpdateProgress implements Runnable {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }

    /**
     * 调用更新相应方法，更新进度条
     */
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            /**
             * 直接调用activity对象里面的方法
             */
            if (activity != null) {
                activity.timedRefresh();
            }
            if (handler != null)
                handler.postDelayed(updateProgress, Constans.TIMED_REFRESH);
        }
    };

    /**
     * @author Administrator
     *         使用类部类，返回当前service的实例，用于activity，调用service的各种方法
     */
    public class TimedRefreshBuild extends Binder {
        public TimedRefreshService getMyService() {
            return TimedRefreshService.this;
        }
    }

    /**
     * @param activity 初始化MainActivity对象
     */
    public void setMainActivity(ContainerActivity activity) {
        this.activity = activity;
    }

    public void stopService() {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        Logger.d("Service", "onDestroy");
        super.onDestroy();
        handler.removeCallbacks(updateProgress);//取消消息发送
        handler = null;
        updateProgress = null;
        activity = null;
    }
}
