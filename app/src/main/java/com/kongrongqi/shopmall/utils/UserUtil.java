package com.kongrongqi.shopmall.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.login.request.AccountBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class UserUtil {

    public static String getUserId(Context context) {
        String accountId = null;
        AccountBean accountBean = LoginInfoManager.getInstance().getmAccountBean();
        if (accountBean != null) {
            try {
                accountId = accountBean.getAccountId();
                return accountId;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return SPUtils.getString(context, Constans.USER_ID, "");
            }
        } else {
            accountId = SPUtils.getString(context, Constans.USER_ID, "");
            return accountId;
        }
    }


    /**
     * 判断某个界面是否在前台
     * @param activity
     * @return
     */
    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }


}
