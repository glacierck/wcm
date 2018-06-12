package com.kongrongqi.shopmall.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.TextView;

import org.apache.commons.pennycodec.binary.Base64;
import org.apache.commons.pennycodec.digest.DigestUtils;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by penny on 2016/9/14 0014.
 */
public class StringUtils {


    /**
     * \
     * 验证密码 ^([A-Za-z]|[\u4E00-\u9FA5])+$
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^[0-9\\+\\-\\s]{7,20}$");
        Matcher matcher = p.matcher(phone);
        return matcher.matches();
    }

    /**
     * \
     * 验证密码 ^([A-Za-z]|[\u4E00-\u9FA5])+$
     *
     * @param passWord
     * @return
     */
    public static boolean checkUserName(String passWord) {
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^([\\u4E00-\\u9FA5]){2,10}$");
        Matcher matcher = p.matcher(passWord);
        return matcher.matches();
    }


    /**
     * \
     * 验证密码 ^([A-Za-z]|[\u4E00-\u9FA5])+$
     *
     * @param code
     * @return
     */
    public static boolean checkcCode(String code) {
        Pattern p = Pattern.compile("^[0-9]{4}$");
        Matcher matcher = p.matcher(code);
        return matcher.matches();
    }

    /**
     * \
     * //纳税人识别号
     *
     * @param passWord
     * @return
     */
    public static boolean checkTaxpayer(String taxpayer) {

        Pattern p = Pattern.compile("^[0-9A-Za-z]{10,30}$");
        Matcher matcher = p.matcher(taxpayer);
        return matcher.matches();
    }


    /**
     * \
     * 验证密码 ^([A-Za-z]|[\u4E00-\u9FA5])+$
     *
     * @param passWord
     * @return
     */
    public static boolean checkPassWord(String passWord) {
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Pattern p = Pattern.compile("^[0-9A-Za-z]{6,20}$");
        Matcher matcher = p.matcher(passWord);
        return matcher.matches();
    }


    //13 14 15 17 18
    public static boolean isMobileNum(String num) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher matcher = p.matcher(num);
        return matcher.matches();
    }

    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 下划线
     *
     * @param view
     */
    public static void underLineText(TextView view) {
        view.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    public static String encryption(String pwd) {
//        String sha256Hex = DigestUtils.sha256Hex(pwd.trim());
        String sha256Hex = DigestUtils.md5Hex(pwd.trim().getBytes());
        String base64Pwd = Base64.encodeBase64String(sha256Hex.getBytes());
        return base64Pwd;
    }

    public static String jointStr(String start, String end) {
        StringBuffer sb = null;
        if (sb == null) {
            sb = new StringBuffer();
        }
        sb.append(start + "-");
        sb.append(" ");//空格
        sb.append(end);
        return sb.toString();
    }


    public static String jointStr(String start, String end, String end1) {
        StringBuffer sb = null;
        if (sb == null) {
            sb = new StringBuffer();
        }
        sb.append(start + ":");
        sb.append(" ");//空格
        sb.append(end);
        sb.append(end1 == null ? "" : end1);
        return sb.toString();
    }

    public static String jointAddress(String province, String city, String district, String address) {
        StringBuffer sb = null;
        if (sb == null) {
            sb = new StringBuffer();
        }
        sb.append(province == null ? "" : province);
        sb.append(city == null ? "" : city);//空格
        sb.append(district == null ? "" : district);
        sb.append(address == null ? "" : address);
        return sb.toString();
    }


    public static String replaceAddress(String address,String del) {
        String province1 = "";
        try {
            province1 = address.replaceAll(del,"");
        }catch (Exception e){
            province1= "";
        }
        return province1;
    }

    public static String jointAddress(String category, String address, String sex) {
        StringBuffer sb = null;
        if (sb == null) {
            sb = new StringBuffer();
        }
        if (TextUtils.isEmpty(category)) {
            sb.append("");
        } else {
            sb.append(category);
        }

        if (TextUtils.isEmpty(address)) {
            sb.append("");
        } else {
            if (!TextUtils.isEmpty(sb.toString())) sb.append(",");
            sb.append(address);
        }
        if (TextUtils.isEmpty(sex)) {
            sb.append("");
        } else {
            if (!TextUtils.isEmpty(sb.toString())) sb.append(",");
            sb.append(sex);
        }
        return sb.toString();
    }


    public static String joint(String category, String address, String sex) {
        StringBuffer sb = null;
        if (sb == null) {
            sb = new StringBuffer();
        }
        if (TextUtils.isEmpty(category)) {
            sb.append("");
        } else {
            sb.append(category);
        }

        if (TextUtils.isEmpty(address)) {
            sb.append("");
        } else {
            if (!TextUtils.isEmpty(sb.toString())) sb.append("-");
            sb.append(address);
        }
        if (TextUtils.isEmpty(sex)) {
            sb.append("");
        } else {
            if (!TextUtils.isEmpty(sb.toString())) sb.append("-");
            sb.append(sex);
        }
        return sb.toString();
    }


    public static String jointStr(String start) {
        StringBuffer sb = null;
        if (sb == null) {
            sb = new StringBuffer();
        }
        sb.append("¥");
        sb.append(start);
        return sb.toString();
    }

    public static String RMB(float price) {
        StringBuffer sb = null;
        if (sb == null) {
            sb = new StringBuffer();
        }
        sb.append("¥ ");
        sb.append(price);
        sb.append(".00");
        return sb.toString();
    }

    public static boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }


    //最大值
    public static float getStrToFloat(String max) {
        float maxf = 0.0f;
        try {
            maxf = Float.parseFloat(max);
        } catch (Exception e) {
            maxf = 0.0f;
        }
        return maxf;
    }

    //最大值
    public static int getStrToInt(String max) {
        int maxf = 0;
        try {
            maxf = Integer.parseInt(max);
        } catch (Exception e) {
            maxf = 0;
        }
        return maxf;
    }

    //最大值
    public static String[] getStrsplitArray(String wechatNos) {
        if (!TextUtils.isEmpty(wechatNos)) return wechatNos.split(",");
        return new String[0];
    }

    public static boolean isEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        Logger.d("===", matcher.matches() + "");
        return matcher.matches();
    }

    public static String valueString(String pDay, int pSaleDay) {
        if (TextUtils.isEmpty(pDay) && pSaleDay == 0) {
            return null;
        } else {
            int failureTime = Integer.parseInt(pDay) + pSaleDay;
            Logger.d("stringUtil", "failureTime" + failureTime);
            return String.valueOf(failureTime);
        }
    }
}
