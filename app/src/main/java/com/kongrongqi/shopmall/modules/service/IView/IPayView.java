package com.kongrongqi.shopmall.modules.service.IView;

import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.model.AddressModel;
import com.kongrongqi.shopmall.modules.model.SaleModel;

/**
 * 创建日期：2017/5/20 0020 on 20:36
 * 作者:penny
 */
public interface IPayView extends IUI {
    void jumpNotUse();

    void finishAt();

    void jumpAccount();

    void showSystemAddFriendUI();

    void showCommonUI();

    void updatePrice(SaleModel pSaleModel);

    void jumpAccountNotUse();

    void setDefault(AddressModel addressModel);
}
