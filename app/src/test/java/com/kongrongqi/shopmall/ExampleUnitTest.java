package com.kongrongqi.shopmall;

import android.util.Log;

import com.google.gson.Gson;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.model.SMSModel;
import com.kongrongqi.shopmall.modules.model.ServiceListModel;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.zhy.autolayout.utils.L;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    //@Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void  gsonDoubleToStr(){
        String gsonStr = "{\"id\":\"5\",\"dataStatus\":0,\"sn\":1,\"status\":1,\"type\":2,\"attr1\":null,\"attr2\":null,\"attr3\":null,\"content\":\"我没有数据灌粉\",\"contentName\":\"服务内容\",\"duration\":\"1\",\"durationName\":\"服务周期\",\"durationUnit\":\"天\",\"name\":\"邀友\",\"owner\":\"yy\",\"price\":0.01,\"sysId\":null,\"createTime\":1495708353000,\"updateTime\":1497944240000}";

        Gson gson = new Gson();
        ServiceListModel serviceListModel = gson.fromJson(gsonStr, ServiceListModel.class);
        serviceListModel.toString();
    }

    @Test
    public void test(){
        requestRegisterCode("13545213172");
    }

    /**
     * 获取注册验证码
     *
     * @param phone
     */
    private void requestRegisterCode(final String phone) {

        HttpApiService.instance().test(phone)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<Boolean>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e("test","开始");
                    }

                    @Override
                    public void onCompleted() {
                        Log.e("test","完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("test","异常");
                    }

                    @Override
                    public void onNext(BaseResponse<Boolean> smsReponseBaseResponse) {
                        Log.e("test",smsReponseBaseResponse.getData().toString());
                    }
                });
    }




}