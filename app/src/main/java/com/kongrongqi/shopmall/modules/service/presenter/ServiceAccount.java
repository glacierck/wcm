package com.kongrongqi.shopmall.modules.service.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.TabFragment;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.GrooveInfo;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/26 0026.
 * 号槽
 */

 public class ServiceAccount extends ServiceSuper {

    public ServiceAccount(Context context, ServiceBean serviceBean) {
        super(context, serviceBean);
    }

    public ServiceAccount(Context context, UserServiceParam userServiceParam) {
        super(context,userServiceParam);
    }

    @Override
    public void buyService() {

    }

    @Override
    public void useService() {

    }
}
