package com.kongrongqi.shopmall.crashMonitor;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.crashMonitor.ui.activity.CrashListActivity;
import com.kongrongqi.shopmall.utils.FileUtils;
import com.kongrongqi.shopmall.utils.NotifyUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * 创建日期：2017/6/8 0008 on 15:14
 * 作者:penny
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static ExceptionHandler sInstance = new ExceptionHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;
    private boolean isDebug = false;

    private ExceptionHandler() {
    }

    public static ExceptionHandler getInstance() {
        return sInstance;
    }

    public void init(Context context,boolean isDebug){
        init(context);
        this.isDebug = isDebug;
    }
    public void init(Context context) {
        this.mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            this.dumpExceptionToSDCard(e);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        e.printStackTrace();
        if (this.isDebug) {
            CrashMonitor.startCrashShowPage(mContext);
        }

        if (this.mDefaultCrashHandler != null) {
            this.mDefaultCrashHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
            System.exit(10);
        }
    }


    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        if(!Environment.getExternalStorageState().equals("mounted")) {
            Log.w("Crash", "sdcard unmounted,skip dump exception");
        } else {
            File dir = new File(FileUtils.getCrashLogPath(this.mContext));
            if(!dir.exists()) {
                dir.mkdirs();
            }

            long current = System.currentTimeMillis();
            String version = "";

            try {
                PackageManager time = this.mContext.getPackageManager();
                PackageInfo file = time.getPackageInfo(this.mContext.getPackageName(), 1);
                version = "V" + file.versionName + "_";
            } catch (Exception var10) {
                ;
            }

            String time1 = (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format(new Date(current));
            time1 = "T" + time1;
            File file1 = new File(dir, "CrashLog_" + version + time1.trim() + ".txt");
            if(!file1.exists()) {
                file1.createNewFile();
            }

            try {
                PrintWriter e = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
                e.println(time1);
                this.dumpPhoneInfo(e);
                e.println();
                ex.printStackTrace(e);
                if(this.isDebug) {
                    this.notify_log(time1.trim(), Log.getStackTraceString(ex));
                }

                e.close();
            } catch (Exception var9) {
                Log.e("Crash", "dump crash info failed：" + var9.toString());
            }

        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = this.mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(this.mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        pw.print("Model: ");
        pw.println(Build.MODEL);
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    private void notify_log(String title, String content) {
        //设置想要展示的数据内容
        Intent intent = new Intent(mContext, CrashListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(mContext,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.logo;
        String ticker = "Crash通知";
        //实例化工具类，并且调用接口
        NotifyUtil notify2 = new NotifyUtil(mContext, 1);
        notify2.notify_normail_moreline(pIntent, smallIcon, ticker, title, content, true, true, false);
    }

}
