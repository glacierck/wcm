package com.kongrongqi.shopmall.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;

import com.kongrongqi.shopmall.global.App;

import java.io.File;

/**
 * 创建日期：2017/6/2 0002 on 20:14
 * 作者:penny
 */
public class DISPath {

    /**
     * 标示获取直播缓存目录(sdcard/AboutLive)
     */
    private static final int TYPE_LIVE_CACHE = 0x01;

    /**
     * 表示获取非直播缓存目录(sdcard/downloads-0)
     */
    private static final int TYPE_NOT_LIVE_CACHE = 0x02;

    /**
     * 标示获取内部的data/data/的直播缓存目录(data/data/AboutLive)
     */
    private static final int TYPE_DATA_CACHE = 0x03;


    /**
     * 本机是否存在sd卡
     */
    private boolean PATH_SD_EXISTST = isExistSdPath();


    /**
     * 直播根目录
     */
    public static final String PATH_BASE = getPath();

    /**
     * 日志保存目录
     */
    public static final String PATH_LOG = getPath(TYPE_LIVE_CACHE, "log");

    /**
     * 音频文件缓存目录
     */
    public static final String PATH_AUDIO = getPath(TYPE_LIVE_CACHE, "audio");

    /**
     * 图片缓存目录
     */
    public static final String PATH_IMAGE = getPath(TYPE_LIVE_CACHE, "image");


    /**
     * 组件uGo的日志目录
     */
    public static final String PATH_UGO_LOG = getPath(PATH_LOG, "UGo");


    /**
     * 直播内部缓存根目录名
     */
    private static final String CACHE_LIVE_DIR = "AboutKRQ";


    /**
     * 对应一些下载的第三方缓存目录
     */
    private static final String CACHE_NOT_LIVE_DIR = "downloads-0";


    /**
     * 返回base目录
     *
     * @return
     */
    private static String getPath() {
        File dir = getBasePath(TYPE_LIVE_CACHE);
        if (dir != null) {
            return dir.getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * 返回basePath/childDir指定路径
     *
     * @param childDir
     * @return
     */
    private static String getPath(final int type, final String childDir) {
        File dir = getPathEx(type, childDir);
        if (dir != null) {
            return dir.getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * 返回basePath/childDir指定路径
     *
     * @param childDir
     * @return
     */
    private static File getPathEx(final int type, final String childDir) {
        final File file = getBasePath(type);
        if (file != null) {
            final File child = new File(file, childDir);
            return mkdirs(child) ? child : null;
        }
        return null;
    }

    /**
     * 返回由parent+name构造的路径
     *
     * @param parent
     * @param name
     * @return
     */
    private static String getPath(String parent, String name) {
        if (!TextUtils.isEmpty(parent) && !TextUtils.isEmpty(name)) {
            File file = new File(parent, name);
            if (mkdirs(file)) {
                return file.getAbsolutePath();
            } else {
                return "";
            }
        }
        return "";
    }

    /**
     * 创建目录
     *
     * @param dir
     * @return
     */
    private static boolean mkdirs(File dir) {
        if (dir != null) {
            if (!dir.exists()) {
                final boolean mk = dir.mkdirs();
                if (!mk) {
                    Logger.d("KRQPath", "mkdirs: " + dir.getAbsolutePath() + ", perform is failed!!");
                }
                return mk;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private static File getBasePath(final int type) {
        File file = null;
        if (type != TYPE_DATA_CACHE) {
            final String name = (type == TYPE_LIVE_CACHE ? CACHE_LIVE_DIR : CACHE_NOT_LIVE_DIR);
            file = getOuterPath(name);
            if (file == null) {
                file = getInnerPath(name);
            }
        } else {
            file = getInnerPath(CACHE_LIVE_DIR);
        }
        return file;
    }

    /**
     * 获取/data/data对应目录
     *
     * @return
     */
    private static File getInnerPath(final String name) {
        @SuppressLint("SdCardPath") File file = new File("/data/data/" + PackageUtils.getPackageName(App.getInstance().getContext()), name);
        if (!mkdirs(file)) {
            return null;
        }
        return file;
    }

    /**
     * 本机是否存在sd卡
     */
    public static boolean isExistSdPath() {
        return getOuterPath(CACHE_LIVE_DIR) != null;
    }

    /**
     * 获取SD卡的保存文件路径。部分手机自带SD卡，自带的SD卡文件夹命名为sdcard-ext或其它，用系统自带方法无法检测出
     * 返回格式为 "/mnt/sdcard/AboutYX/" 或 "/mnt/sdcard-ext/AboutYX/"
     *
     * @return
     */
    private static File getOuterPath(final String child) {
        File file = null;
        final String state = Environment.getExternalStorageState();
        //酷派4.2.2系统报空指针错误
        File tmpFile = null;
        try {
            tmpFile = Environment.getExternalStorageDirectory();
        } catch (Exception e) {
        }
        if (tmpFile == null) {
            return null;
        }

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            file = new File(tmpFile, child);
            if (!mkdirs(file)) {
                return null;
            }
        } else {
            File mntFile = new File("/mnt");
            File[] mntFileList = mntFile.listFiles();
            if (mntFileList != null) {
                for (int i = 0; i < mntFileList.length; i++) {
                    String mmtFilePath = mntFileList[i].getAbsolutePath();
                    String sdPath = tmpFile.getAbsolutePath();
                    if (!mmtFilePath.equals(sdPath) && mmtFilePath.contains(sdPath)) {
                        file = new File(mmtFilePath, child);
                        if (!mkdirs(file)) {
                            return null;
                        }
                        break;
                    }
                }
            }
        }

        return file;
    }
}
