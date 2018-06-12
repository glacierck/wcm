package com.kongrongqi.shopmall.modules.account.IView;

import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.holder.AccountInfoHeaderBntHolder;
import com.kongrongqi.shopmall.modules.account.holder.AccountInfoHeaderBntHolder2;

/**
 * 创建日期：2017/5/17 0017 on 17:34
 * 作者:penny
 */
public interface IServiceDatailsView extends IUI{

   void startAddServiceTab();

   void refrashIview();
   /**
    * 添加帐号
    */
   void addAccount(DeviceWechat deviceWechat, int optType, String title);

   AccountInfoHeaderBntHolder getAccountInfoHeader();

   AccountInfoHeaderBntHolder2 getAccountInfoHeader2();

}
