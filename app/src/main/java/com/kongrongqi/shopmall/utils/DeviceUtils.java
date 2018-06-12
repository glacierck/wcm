/*
 * 深圳市有信网络技术有限公司
 * Copyright (c) 2016 All Rights Reserved.
 */

package com.kongrongqi.shopmall.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * DeviceUtils
 * <ul>
 * <li>{@link #getBuildModel()} 获取设备Model，可以用来判断设备型号</li>
 * <li>{@link #getProcessorNumber()} 获取CPU的核数</li>
 * <li> {@link #getVersionRelease()}获取系统型号,以android_OS_开头，eg：android_OS_4.1</li>
 * <li> {@link #getSdkInt()}获取SDK版本号,14代表Android4.0</li>
 * <li>{@link #getImei(Context)}获取设备imei号</li>
 * <li>{@link #getMacAddress(Context)}获取mac地址</li>
 * <li> {@link #getManufacturer()}获取厂商信息</li>
 * </ul>
 * <ul>
 * <strong>Attentions:</strong>
 * <li>You should add <strong>android.permission.ACCESS_WIFI_STATE</strong> in
 * manifest</li>
 * <li>You should add <strong>android.permission.READ_PHONE_STATE</strong> in
 * manifest</li>
 * </ul>
 *
 * @author penny
 */
public class DeviceUtils {

    private static String mImei, mMacAddress, mUUID, mImsi;

    /**
     * 获取TelephonyManager
     *
     * @param context
     * @return
     */
    private static TelephonyManager getTM(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取设备Model，可以用来判断设备型号
     *
     * @return
     */
    public static String getBuildModel() {
        return Build.MODEL;
    }


    /**
     * 获取CPU的核数
     *
     * @return
     */
    public static int getProcessorNumber() {
        Runtime rt = Runtime.getRuntime();
        return rt.availableProcessors();
    }

    /**
     * 获取系统型号，eg：android_OS_4.1
     *
     * @return
     */
    public static String getVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取SDK版本号
     *
     * @return
     */
    public static int getSdkInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取imei号，如果可以获取到，直接返回，如果无法获取，或是获取到的不是imei，则返回null
     * <ul>
     * <strong>Attentions:</strong>
     * <li>You should add <strong>android.permission.READ_PHONE_STATE</strong>
     * in manifest</li>
     * </ul>
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        if (!TextUtils.isEmpty(mImei)) {
            return mImei;
        }
        mImei = getTM(context).getDeviceId();

        // 如果不是imei号，则返回空
        if (!isImei(mImei)) {
            mImei = null;
            return null;
        }
        return mImei;
    }

    /**
     * 判断是否是imei号
     *
     * @param imei
     * @return
     */
    private static boolean isImei(String imei) {
        if (TextUtils.isEmpty(imei)) {
            return false;
        }

        if (imei.length() < 15) {
            return false;
        }

        // 检查是否都是数字
        for (int i = 0; i < imei.length(); i++) {
            if (!Character.isDigit(imei.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 获取mac地址
     * <ul>
     * <strong>Attentions:</strong>
     * <li>You should add <strong>android.permission.ACCESS_WIFI_STATE</strong>
     * in manifest</li>
     * </ul>
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        if (!TextUtils.isEmpty(mMacAddress)) {
            return mMacAddress;
        }
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 获取屏幕分辨率
     * @param context
     * @return
     */
    private static DisplayMetrics displayMetrics = null;
    public static int getScreenWidth(Context context) {
        if (displayMetrics == null) {
            setDisplayMetrics(context.getResources().getDisplayMetrics());
        }
        return displayMetrics.widthPixels;
    }

    public static void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        displayMetrics = DisplayMetrics;
    }

    public static int getScreenHeight(Context context) {
        if (displayMetrics == null) {
            setDisplayMetrics(context.getResources().getDisplayMetrics());
        }
        return displayMetrics.heightPixels;
    }


    /**
     * 获取手机制造商信息
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取社标uuid
     *
     * @param context
     * @return
     */
    public static String getUUID(Context context) {
        if (!TextUtils.isEmpty(mUUID)) {
            return mUUID;
        }

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + getTM(context).getDeviceId();
        tmSerial = "" + getTM(context).getSimSerialNumber();
        androidId = ""
                + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        mUUID = deviceUuid.toString();
        return mUUID;
    }

    /**
     * 获取  iccid
     *
     * @param context
     * @return
     */
    public static String getICCID(Context context) {
        String iccid = null;
        try {
            iccid = getTM(context).getSimSerialNumber();
        } catch (Exception e) {

        }
        return iccid;
    }

    /**
     * Android ID
     *
     * @return
     */
    public static String getAndroidID(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidId)) {
            return "";
        }
        return androidId;
    }

    /**
     * Imsi
     *
     * @param context
     * @return
     */
    public static String getImsi(Context context) {
        if (!TextUtils.isEmpty(mImsi)) {
            return mImsi;
        }
        try {
            mImsi = getTM(context).getSubscriberId();
        } catch (Exception e) {
            //
        }
        if (TextUtils.isEmpty(mImsi)) {
            mImsi = null;
        }
        return mImsi;
    }

    /**
     * 运营商获取
     *
     * @param context
     * @return
     */
//    public static String getOperator(Context context) {
//        String imsinetwrok = null;
//        String simOperator = getTM(context).getSimOperator();
//        if (simOperator != null) {
//            if (simOperator.startsWith("46000") || simOperator.startsWith("46002") || simOperator.startsWith("46007")) {
//                //因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
//                imsinetwrok = LocalFormatUtil.format(context, R.string.string_platform_cmcc);
//            } else if (simOperator.startsWith("46001")) {
//                imsinetwrok = LocalFormatUtil.format(context, R.string.string_platform_cucc);
//            } else if (simOperator.startsWith("46003")) {
//                imsinetwrok = LocalFormatUtil.format(context, R.string.string_platform_ctcc);
//            }
//        }
//        return imsinetwrok;
//    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 返回当前程序版本
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = -1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
        }
        return versionCode;
    }

    /**
     * 获取手机号码
     *
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context) {
        return getTM(context).getLine1Number();
    }

    /**
     * 读取 meta-data
     */
    public static String getMetaData(Context c, String name) {
        String channelId = "";
        try {
            channelId = getChannel(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return channelId == null ? "" : channelId;
    }

    public static String getChannel(Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith("META-INF/channel")) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split("_");
        if (split != null && split.length >= 2) {
            return ret.substring(split[0].length() + 1);
        } else {
            return "";
        }

    }

    /**
     * SD卡大小
     */
    public static int getSdcardSize(Context context) {
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize;
                long totalBlocks;
                long availableBlocks;
                // 由于API18（Android4.3）以后getBlockSize过时并且改为了getBlockSizeLong
                // 因此这里需要根据版本号来使用那一套API
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    blockSize = stat.getBlockSizeLong();
                    totalBlocks = stat.getBlockCountLong();
                    availableBlocks = stat.getAvailableBlocksLong();
                } else {
                    blockSize = stat.getBlockSize();
                    totalBlocks = stat.getBlockCount();
                    availableBlocks = stat.getAvailableBlocks();
                }
                // 利用formatSize函数把字节转换为用户等看懂的大小数值单位
                int totalText = (int) (blockSize * totalBlocks);
                int availableText = (int) (blockSize * availableBlocks);
                return totalText;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 获取屏幕分辨率  resolution
     *
     * @param context
     * @return
     */
    public static String getResolution(Context context) {
        Display d = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = d.getWidth();
        int height = d.getHeight();
        return width + "x" + height;
    }

//    /**
//     *获取connecttype    要枚举？？？？
//     * @param context
//     * @return
//     */
//    public static int getConnectType(Context context){
//        int connectType = ConnectUtils.getConnectType(context);
//        return connectType;
//    }

    /**
     * 获取cpu信息
     *
     * @return
     */
    public static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            if (!TextUtils.isEmpty(str2)) {
                arrayOfString = str2.split("\\s+");
                for (int i = 2; i < arrayOfString.length; i++) {
                    cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
                }
            }
            str2 = localBufferedReader.readLine();
            if (!TextUtils.isEmpty(str2)) {
                arrayOfString = str2.split("\\s+");
                cpuInfo[1] += arrayOfString[2];
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return cpuInfo;
    }


    /**
     * UA设计：
     * <p/>
     * 参考get请求传参模式，以&分隔，以key＝value形式组装每一个参数,方便后期扩展
     * <p/>
     * 格式如  ua:key1=xxx&key2=xxx&key3=xxx
     * <p/>
     * 其中key定义如下
     * os:系统版本，如android传4.4.1.ios传9.1.1
     * imei: 手机识别码 android用imli值，iOS用udid/openudid
     * m:机型
     * s：屏幕尺寸，传w x h
     * c：平台，传1或2，1ios，2是android
     * vc：客户端版本号(versioncode)，如1,每次打包依次递增
     * vn：客户端版本号(versionname)，如V1.0.0,每次打包依次递增
     * n:    产品名。如hrs
     *
     * @param context
     * @return
     */
    public static String getUxinLiveUa(Context context) {
        String imei = getImei(context);
        String mode = getBuildModel();
        String osVersion = getVersionRelease();
        int appVsionCode = getAppVersionCode(context);
        String appVersionName = getAppVersionName(context);
        String screen = getResolution(context);

        StringBuffer sb = new StringBuffer();
        sb.append("os=");
        sb.append(osVersion);
        sb.append("&imei=");
        sb.append(imei);
        sb.append("&m=");
        sb.append(mode);
        sb.append("&s=");
        sb.append(screen);
        sb.append("&c=2");
        sb.append("&vc=");
        sb.append(appVsionCode);
        sb.append("&vn=");
        sb.append(appVersionName);
        sb.append("&n=hrs");
        return sb.toString();
    }

    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";


    /**
     * 华为rom
     * @return
     */
    public static boolean isEMUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_EMUI_VERSION_CODE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 小米rom
     * @return
     */
    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            /*String rom = "" + prop.getProperty(KEY_MIUI_VERSION_CODE, null) + prop.getProperty(KEY_MIUI_VERSION_NAME, null)+prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null);
            Log.d("Android_Rom", rom);*/
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 魅族rom
     * @return
     */
    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
}
