package com.kongrongqi.shopmall.modules.service.IView;

import com.kongrongqi.shopmall.base.IUI;

/**
 * 创建日期：2017/5/25 0025 on 18:21
 * 作者:penny
 */
public interface ISelectPackageView extends IUI{
    void finishAct();

    void spinnerChooseText(String pItem);

    void chooseText(String address, String mProvince, String text);
}
