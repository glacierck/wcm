package com.kongrongqi.shopmall.modules.login.presenter;

import com.google.gson.Gson;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.TabFragment;
import com.kongrongqi.shopmall.modules.account.bean.Device_Wechat;
import com.kongrongqi.shopmall.modules.login.Iview.IWelcomeView;
import com.kongrongqi.shopmall.modules.model.PictureModel;
import com.kongrongqi.shopmall.utils.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/6/2 0002 on 19:54
 * 作者:penny
 */
public class WelcomePresenter extends BasePresenter<IWelcomeView> {

    private Subscription mSubscribe;

    public void requestSplashPic() {
        mSubscribe = HttpApiService.instance().splash(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<List<String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("splash","=========="+e.toString());
                    }

                    @Override
                    public void onNext(BaseResponse<List<String>> response) {
                        if(checkApiResponse(response)){
                            Logger.d("splash","===========");
                            List<String> data = response.getData();
                            if(isActivityExist()){
                                getUI().showSplashPic(data);
                            }
                        }
                    }
                });
    }

    public void getNewConfig(){
        ApiBean.getApiBean(getContext());
    }



    @Override
    public void onUIDestory() {
        releaseSubscription(mSubscribe);
        super.onUIDestory();
    }
}
