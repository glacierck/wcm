package com.kongrongqi.shopmall.modules.service.IView;

import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.login.request.EditAddressRequest;
import com.kongrongqi.shopmall.modules.model.AddressModel;

/**
 * 创建日期：2017/5/22 0022 on 14:22
 * 作者:penny
 */
public interface IEditAddressView extends IUI{
    void showToast(String string);

    void chooseText(String text);

    void transmit(AddressModel request);

    AddressModel getAddressModel();
}
