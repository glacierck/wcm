package com.kongrongqi.shopmall.shop;

import com.kongrongqi.shopmall.Strategy;
import com.kongrongqi.shopmall.utils.Logger;

/**
 * Created on 2017/8/1 0001.
 * by penny
 */

public class ServiceB implements Strategy{
    @Override
    public void useService() {
        Logger.d("ServiceB","=========");
    }

    @Override
    public void buyService() {
        Logger.d("ServiceB","===============");

    }
}
