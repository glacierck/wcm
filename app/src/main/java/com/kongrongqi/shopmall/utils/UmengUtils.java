package com.kongrongqi.shopmall.utils;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.me.UserSessionActivity;
import com.sina.weibo.sdk.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import org.apache.commons.pennycodec.binary.Base64;
import org.apache.commons.pennycodec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by penny on 2016/9/14 0014.
 */
public class UmengUtils {

    private static String TAG = "UmengUtils";
    public static Handler handler = new Handler();
    public static final String UPDATE_STATUS_ACTION = "com.kongrongqi.shopmall.action.UPDATE_STATUS";

    /**
     * 设置别名
     *
     * @param pushAgent
     * @param alias     别名 userId
     * @param alias     别名类型 "userId"
     */
    public static void addAlias(PushAgent pushAgent, String alias) {
        String aliasType = "userId";
        pushAgent.addAlias(alias, aliasType, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                UmLog.i(TAG, "isSuccess:" + isSuccess + "," + message);
            }
        });
    }

    /**
     * 删除别名
     *
     * @param pushAgent
     * @param alias     别名 userId
     * @param alias     别名类型 "userId"
     */
    public static void removeAlias(PushAgent pushAgent, String alias) {
        String aliasType = "userId";
        pushAgent.removeAlias(alias, aliasType, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                UmLog.i(TAG, "isSuccess:" + isSuccess + "," + message);
            }
        });
    }

    /**
     * 友盟统计  服务购买总量
     *
     * @param context
     * @param serviceType 服务类型 Constans类中
     * @param num
     */
    public static void uMengServiceBuyCount(Context context, String serviceType, String num) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", serviceType);
        map.put("quantity", num);
        MobclickAgent.onEvent(context, Constans.SERVICE_BUY_COUNT, map);
    }


    public static void starUmengActivity(Context context) {
        PushAgent.getInstance(context);
        PushAgent.getInstance(context).onAppStart();
    }

    //开启友盟统计debug
    public static void setDebugMode(boolean f) {
        MobclickAgent.setDebugMode(f);
    }

    ////友盟统计 session的统计
    public static void uMengOnResume(Context context) {
        MobclickAgent.onResume(context);
    }

    //友盟统计 session的统计
    public static void uMengOnPause(Context context) {
        MobclickAgent.onPause(context);
    }


    //友盟推送 初始化
    public static void initUmeng(Context context) {
        PushAgent mPushAgent = PushAgent.getInstance(context);
        mPushAgent.setDebugMode(true);
        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // sdk关闭通知声音
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

//		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Logger.e("dealWithCustomAction", "===============" + msg.toString());
                String lCustom = msg.custom;
                Logger.e("dealWithCustomAction", "===============" + lCustom);
                Intent intent = new Intent(context, UserSessionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        Logger.e("dealWithCustomMessage", "===============" + msg.toString());
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(context.getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(context.getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Logger.d("getNotification", "====================");
//                        Notification.Builder builder = new Notification.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                UmLog.i(TAG, "device token: " + deviceToken);
                context.sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                UmLog.i(TAG, "register failed: " + s + " " + s1);
                context.sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });
    }

}
