/*
 * 深圳市有信网络技术有限公司
 * Copyright (c) 2016 All Rights Reserved.
 */

package com.kongrongqi.shopmall.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * 删除，复制，全部删除
 * penny
 */
public class FileUtils {

    public static String getSystemPhotoPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
    }

    public static void deleteFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            deleteFile(new File(path));
        }
    }

    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    public static boolean mkDir(File file) {
        while (!file.getParentFile().exists()) {
            mkDir(file.getParentFile());
        }
        return file.mkdir();
    }


    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void deleteFileEx(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFileEx(f);
                }
                file.delete();
            }
        }
    }

    /***
     * crash Logo
     *
     * @param context
     * @return
     */
    public static String getCrashLogPath(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        String path = DISPath.PATH_BASE + File.separator + "crashLogs";
        return path;
    }

    public static List<File> getFileList(File file) {
        List<File> mFileList = new ArrayList<>();
        File[] fileArray = file.listFiles();
        if (fileArray == null || fileArray.length <= 0) {
            return mFileList;
        }
        for (File f : fileArray) {
            if (f.isFile()) {
                mFileList.add(f);
            }
        }
        return mFileList;
    }

    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件夹下的文件排序
     *
     * @param dir
     * @return
     */
    public static List<String> sort(final String dir) {
        if (TextUtils.isEmpty(dir)) {
            return null;
        }
        List<String> paths = new ArrayList<String>();
        File file = new File(dir);
        if (file == null) {
            return paths;
        }
        if (file.exists() && file.isDirectory() && file.listFiles().length > 0) {
            final File[] childs = file.listFiles();
            for (File child : childs) {
                paths.add(child.getAbsolutePath());
            }
        }
        if (paths != null && paths.size() > 1) {
            Collections.sort(paths, Collections.reverseOrder());
        }
        return paths;
    }

    /**
     * @param basePath DISPath.PATH_BASE
     * @param fileName
     * @param str
     */
    public static void writeFile(String basePath, String fileName, String str) {
        try {
            File file = new File(basePath, fileName);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(str);
            bw.flush();
            fw.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param basePath DISPath.PATH_BASE
     * @param fileName
     * @return
     */
    public static String readFile(String basePath, String fileName) {
        StringBuilder result = new StringBuilder();
        FileReader reader = null;
        BufferedReader buf = null;
        try {
            reader = new FileReader(new File(basePath, fileName));
            buf = new BufferedReader(reader);
            String line = null;
            while ((line = buf.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (buf != null) {
                    buf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();

    }

    public static String readFileByLines(String fileName) {
        StringBuilder builder = new StringBuilder();
        File file = new File(fileName);
        if (!file.exists()) {
            return "";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));//new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                line++;
                //tempString = new String(tempString.getBytes("gb2312"), "utf-8");
                while (tempString.startsWith("\t")) {
                    tempString = tempString.replace("\t", "");
                }
                while (tempString.endsWith("\t")) {
                    tempString = tempString.replace("\t", "");
                }
                while (tempString.endsWith("\r")) {
                    tempString = tempString.replace("\r", "");
                }
                builder.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return builder.toString();
    }


    /**
     * 将JSON gZip压缩成文件
     *
     * @param content
     * @param path
     * @return
     * @throws IOException
     * @author: xiaozhenhua
     * @data:2013-9-11 下午3:36:49
     */
    public static boolean compress(String content, String path) throws IOException {
        if (TextUtils.isEmpty(content)) {
            return false;
        }
        FileOutputStream os = new FileOutputStream(path);
        GZIPOutputStream gz = new GZIPOutputStream(os);
        byte[] strbt = content.getBytes("utf-8");
        byte[] buffer = new byte[1024];
        int len = 0;
        int strlen = strbt.length;
        while (len < strlen) {
            if (strlen < buffer.length) {
                gz.write(strbt, 0, strlen);
            } else {
                if ((strlen - len) < buffer.length) {
                    gz.write(strbt, len, strlen - len);
                } else {
                    gz.write(strbt, len, buffer.length);
                }
            }
            len += buffer.length;
        }
        gz.close();
        os.close();
        return true;
    }

    /**
     * 将二进制数据写入文件
     *
     * @param path
     * @author: xiaozhenhua
     * @data:2012-7-28 上午10:21:06
     */
    public static void writeFile(byte[] content, String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (content != null) {
            try {
                FileOutputStream os = new FileOutputStream(file);
                os.write(content);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写文件
     *
     * @param fileName
     * @param obj
     */
    public static void writeObjectToFile(String fileName, Object obj) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        File file = new File(fileName);
        FileOutputStream out = null;
        ObjectOutputStream objOut = null;

        try {
            out = new FileOutputStream(file);
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
        } finally {
            CloseableUtils.close(objOut);
            CloseableUtils.close(out);
        }
    }

    /**
     * 写文件
     *
     * @param fileName
     * @return
     */
    public static Object readObjectFromFile(String fileName) {
        Logger.d("file:",fileName);
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        Object temp = null;
        File file = new File(fileName);
        FileInputStream in = null;
        ObjectInputStream objIn = null;
        try {
            in = new FileInputStream(file);
            objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
//            System.out.println("read object success!");
        } catch (IOException e) {
//            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.close(in);
            CloseableUtils.close(objIn);
        }
        return temp;
    }


//    public static ContentValues videoContentValues = null;
//    public static String createFinalPath(Context context) {
//        long dateTaken = System.currentTimeMillis();
//        String title = Constants.FILE_START_NAME + dateTaken;
//        String filename = title + Constants.VIDEO_EXTENSION;
//        String filePath = genrateFilePath(context, String.valueOf(dateTaken), true, null);
//
//        ContentValues values = new ContentValues(7);
//        values.put(MediaStore.Video.Media.TITLE, title);
//        values.put(MediaStore.Video.Media.DISPLAY_NAME, filename);
//        values.put(MediaStore.Video.Media.DATE_TAKEN, dateTaken);
//        values.put(MediaStore.Video.Media.MIME_TYPE, "video/3gpp");
//        values.put(MediaStore.Video.Media.DATA, filePath);
//        videoContentValues = values;
//
//        return filePath;
//    }

//    private static String genrateFilePath(Context context, String uniqueId, boolean isFinalPath, File tempFolderPath) {
//        String fileName = Constants.FILE_START_NAME + uniqueId + Constants.VIDEO_EXTENSION;
//        String dirPath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + Constants.VIDEO_FOLDER;
//        if (isFinalPath) {
//            File file = new File(dirPath);
//            if (!file.exists() || !file.isDirectory())
//                file.mkdirs();
//        } else
//            dirPath = tempFolderPath.getAbsolutePath();
//        String filePath = dirPath + "/" + fileName;
//        return filePath;
//    }
//
//    public static String createImagePath(Context context) {
//        long dateTaken = System.currentTimeMillis();
//        String title = Constants.FILE_START_NAME + dateTaken;
//        String filename = title + Constants.IMAGE_EXTENSION;
//
//        String dirPath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + Constants.IMAGE_FOLDER;
//        File file = new File(dirPath);
//        if (!file.exists() || !file.isDirectory())
//            file.mkdirs();
//        String filePath = dirPath + "/" + filename;
//        return filePath;
//    }


    public static File createCacheImagePath() {
        File sd = Environment.getExternalStorageDirectory();
        String path = sd.getPath() + File.separator + "glide" + File.separator;
        File file = new File(path);
        if (!file.exists())
            file.mkdir();

        return file;
    }


}
