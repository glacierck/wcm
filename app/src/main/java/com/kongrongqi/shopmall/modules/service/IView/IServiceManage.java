package com.kongrongqi.shopmall.modules.service.IView;

import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;

/**
 * 服务 接口
 */
public interface IServiceManage {
    /**
     * 购买服务
     */
    void buyService();

    /**
     * 使用服务
     */
    void userService();

    /**
     *  购买服务 页面跳转
     */
    void jumpPageForBuy();

    /**
     *  使用服务 页面跳转
     */
    void jumpPageForUser();

    /**
     * 得到购买参数
     */
    OrderNumRequest getBuyParam();

    /**
     * 得到使用参数
     */
    void getUserParam();
}
