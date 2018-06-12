package com.kongrongqi.shopmall.modules.service.IView;

import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.model.AddressModel;

/**
 * 创建日期：2017/5/22 0022 on 13:29
 * 作者:penny
 */
public interface IReceiverAddressView extends IUI{
    void showAddress(AddressModel addressModel, String address);
}
