package com.kongrongqi.shopmall.modules.service.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.me.bean.JsonBean;
import com.kongrongqi.shopmall.modules.model.AddressModel;
import com.kongrongqi.shopmall.modules.service.IView.IEditAddressView;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.OptionsPickerUtil;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/22 0022 on 14:22
 * 作者:penny
 */
public class EditAddressPresenter extends BasePresenter<IEditAddressView> implements OptionsPickerView.OnOptionsSelectListener {

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_FAILED = 0x0002;
    private static final int MSG_LOAD_SUCCESS = 0x0003;
    public static boolean isSuccess = false;
    private LoadDataHandler mHandler;
    private Thread mThread;
    private String mProvince;
    private String mCity;
    private String mDistrict;
    public static final String TAG = "EditAddressPresenter";
    private Subscription mSubscription;

    public void test(){
         Observable.fromCallable(new Callable<Map>() {
            @Override
            public Map call() throws Exception {
                return OptionsPickerUtil.startLoadData(getContext());
            }
        })
        .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(TAG, "===onError==="+e.toString());
                    }

                    @Override
                    public void onNext(Map map) {
                        isSuccess= true;
                        options1Items = (ArrayList<JsonBean>) map.get("mProvince");
                        options2Items = (ArrayList<ArrayList<String>>) map.get("mCity");
                        options3Items = (ArrayList<ArrayList<ArrayList<String>>>) map.get("mDistrict");
                    }
                });
    }


    public void initJsonData() {
        mHandler = new LoadDataHandler();
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                startLoadData();
            }
        });
        mThread.start();
    }

    public void showPicker() {
        StyleDialog.showPikerView(getContext(),
                options1Items, options2Items, options3Items, this);
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        mProvince = options1Items.get(options1).getPickerViewText();
        mCity = options2Items.get(options1).get(options2);
        mDistrict = options3Items.get(options1).get(options2).get(options3);
        String address = StringUtils.jointAddress(mProvince, mCity, mDistrict, "");
        if (isSuccess) {
            getUI().chooseText(address);
        } else {
            getUI().showToast(getContext().getResources().getString(R.string.data_parse_error));
        }
    }

    /**
     *  新增 保存参数
     *
     * @param name
     * @param address
     * @param phone
     * @param addressDetail
     */
    public void save(String name,
                     String address,
                     String phone,
                     String addressDetail) {
        if (!TextUtils.isEmpty(name) &&!TextUtils.isEmpty(address) &&!TextUtils.isEmpty(phone) &&!TextUtils.isEmpty(addressDetail)) {
            if(StringUtils.checkPhone(phone)){

                AddressModel addressModel = getUI().getAddressModel();

                if(addressModel==null){
                     addressModel = new AddressModel();
                }
                addressModel.setUserId(getUserId());
                if(!TextUtils.isEmpty(mProvince)){
                    addressModel.setProvince(mProvince);
                }
                if(!TextUtils.isEmpty(mCity)){
                    addressModel.setCity(mCity);
                }
                if(!TextUtils.isEmpty(mDistrict)){
                    addressModel.setDistrict(mDistrict);
                }
                addressModel.setReceiver(name);
                addressModel.setReceivePhone(phone);
                addressModel.setAddress(addressDetail);
                requestSave(addressModel);
            }else{
                getUI().showToast("请填写正确的电话号码");
            }
        } else {
            getUI().showToast(getContext().getResources().getString(R.string.info_edit));
        }
    }

    /**
     * 开始请求
     *
     * @param name
     * @param address
     * @param phone
     * @param addressDetail
     */
    private void requestSave(AddressModel request) {
        if (isSuccess) {
            Subscription subscribe = HttpApiService.instance().editAddress(request)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<BaseResponse>(getUI(),false) {

                        @Override
                        public void onStart() {
                            super.onStart();
                            showDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            dismissDialog();
                        }
                        @Override
                        public void onNext(BaseResponse baseResponse) {
                            dismissDialog();
                            if (checkApiResponse(baseResponse)) {
                                Logger.d(TAG, "===onNext===");
                                dismissDialog();
                                if(isActivityExist()){
                                    getUI().transmit(request);
                                }
                            }
                        }
                    });
        } else {
            getUI().showToast(getContext().getResources().getString(R.string.info_edit));
        }

    }

    private static class LoadDataHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_FAILED:
                    isSuccess = false;
                    break;
                case MSG_LOAD_SUCCESS:
                    isSuccess = true;
                    break;
            }

        }
    }

    private void startLoadData() {
        String JsonData = StringUtils.getJson(getContext(), "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            detail = gson.fromJson(data.toString(), new TypeToken<List<JsonBean>>(){}.getType());
//            for (int i = 0; i < data.length(); i++) {
//                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
//                detail.add(entity);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if(mSubscription!=null){
            mSubscription.unsubscribe();
        }
        if (options1Items != null) {
            options1Items = null;
        }
        if (options2Items != null) {
            options1Items = null;
        }
        if (options3Items != null) {
            options1Items = null;
        }
    }
}
