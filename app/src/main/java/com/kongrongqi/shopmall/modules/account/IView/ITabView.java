package com.kongrongqi.shopmall.modules.account.IView;

import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;

import java.util.List;

/**
 * 创建日期：2017/5/17 0017 on 17:34
 * 作者:penny
 */
public interface ITabView extends IUI{

    void notifyDataSetChangedAdapter();

    /**
     * 替换按钮 切换
     */
    void replace();


    /**
     * 添加帐号
     */
    void addAccount(DeviceWechat deviceWechat,int optType,String title);

    /**
     * 购买号槽
     */
    void buySlot(OrderNumRequest orderNumRequest);

    /**
     * 底部红色文字 购买新服务
     */
    void buyNewService();

    /**
     * 未使用服务
     */
    void userService(DeviceWechat deviceWechat);

    /**
     * 查看服务详情
     */
    void lookServiceDetails(DeviceWechat deviceWechat);

    void deviceErroy();

    void refrashIview();

}
