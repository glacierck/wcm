package com.kongrongqi.shopmall.modules.me.IView;

import com.kongrongqi.shopmall.base.IUI;

/**
 * 创建日期：2017/5/20 0020 on 14:59
 * 作者:penny
 */
public interface IPostMailView extends IUI{
    void showToast(String string);

    void chooseText(String text,String province,String city,String district);

    void finishAt();
}
