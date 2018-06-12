package com.kongrongqi.shopmall.modules.login.Iview;

import com.kongrongqi.shopmall.base.IUI;

/**
 * 创建日期：2017/5/17 0017 on 13:16
 * 作者:penny
 */
public interface IRegisterView extends IUI{
    void showCountTime(boolean b, String timeStr);

    void finishAct();

    void showToast(String string);
}
