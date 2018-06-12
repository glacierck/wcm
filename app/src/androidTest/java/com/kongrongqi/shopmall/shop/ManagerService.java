package com.kongrongqi.shopmall.shop;

import com.kongrongqi.shopmall.Strategy;
import com.kongrongqi.shopmall.utils.Logger;

/**
 * Created on 2017/8/1 0001.
 * by penny
 */

public class ManagerService {

    private Strategy mStrategy;

    public ManagerService(Strategy pStrategy) {
        this.mStrategy = pStrategy;
    }

    public void useService() {
        Logger.d("使用服务","=======");
        mStrategy.useService();
    }

    public void buyService(){
        Logger.d("购买服务","========");
        mStrategy.buyService();
    }
}
