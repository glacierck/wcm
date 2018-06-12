package com.kongrongqi.shopmall.modules.account.presenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.AccountFragment;
import com.kongrongqi.shopmall.modules.account.IView.IAccountView;
import com.kongrongqi.shopmall.modules.account.TabFragment;
import com.kongrongqi.shopmall.modules.account.adapter.AccountAdaper;
import com.kongrongqi.shopmall.modules.account.adapter.ContentPagerAdapter;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.Device_Wechat;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.model.LoginModel;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:penny
 */
public class AccountPresenter extends BasePresenter<IAccountView> {

    private List<Device> tabIndicators;
    private List<TabFragment> tabFragments;
    private ContentPagerAdapter adaper;

    public ContentPagerAdapter getPageAdaper(){
        return adaper;
    }


    public void setAdapter(ViewPager viewPager, FragmentManager fm, List<TabFragment> tabFragments, List<Device> tabIndicators) {

        this.tabIndicators = tabIndicators;
        this.tabFragments = tabFragments;
        adaper = new ContentPagerAdapter(this, fm,tabFragments,tabIndicators);
        viewPager.setAdapter(adaper);
    }

    public void bindData(BaseResponse<Device_Wechat> baseResponse) {
        List<Device> userDeviceList = baseResponse.getData().getUserDeviceList();
        if (userDeviceList == null || userDeviceList.size() <= 0) {
            getUI().showEmpty();
            getUI().invisibleMoreEq(true);
            return;
        }
        getUI().invisibleMoreEq(false);
        getUI().showContent();
        tabIndicators.clear();
        tabIndicators.addAll(userDeviceList);

        if(getUI().getRefrashType()== AccountFragment.GET_CACHE){//添加设备
            for (int i=0;i<tabIndicators.size();i++){
                if(i==tabIndicators.size()-1){ //最后一个
                    Bundle args = new Bundle();
                    args.putSerializable(TabFragment.DEVICE,tabIndicators.get(i));
                    tabFragments.add(TabFragment.newInstance(args));
                }
            }
            getUI().setRefrashType(AccountFragment.GET_CACHE_NOE);
        }else{
            tabFragments.clear();
            for (Device device : tabIndicators){
                Bundle args = new Bundle();
                args.putSerializable(TabFragment.DEVICE,device);
                tabFragments.add(TabFragment.newInstance(args));
            }
        }
        getUI().notifyDataSetChangedAdapter();
    }

    /**
     * 获取设备列表数据
     */
    public void getAccountData() {
        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        HttpApiService.instance().getAccountNumberInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<Device_Wechat>>(getUI(),true) {

                    @Override
                    public void onNext(BaseResponse<Device_Wechat> baseResponse) {
                        if(checkApiResponse(baseResponse)){
                            bindData(baseResponse);
                        }
                    }
                });
    }

    @Override
    public void refreshView() {
        super.refreshView();
        getAccountData();
    }



}
