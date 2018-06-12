package com.kongrongqi.shopmall.modules.me.IView;

import com.kongrongqi.shopmall.base.IUI;

/**
 * 新发票
 */
public interface IPostMailNewView extends IUI{
    void chooseText(String address,String province, String city, String district);
    void showToast(String toast);
    void finishAt();

    void spinnerChooseText(String pItem);
}
