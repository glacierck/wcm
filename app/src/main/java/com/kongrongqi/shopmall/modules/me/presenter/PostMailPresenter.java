package com.kongrongqi.shopmall.modules.me.presenter;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.me.IView.IPostMailView;
import com.kongrongqi.shopmall.modules.me.bean.JsonBean;
import com.kongrongqi.shopmall.modules.me.bean.UserOrderBill;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/20 0020 on 14:59
 * 作者:penny
 */
public class PostMailPresenter extends BasePresenter<IPostMailView> implements OptionsPickerView.OnOptionsSelectListener {

    private static final int MSG_LOAD_FAILED = 0x0002;
    private static final int MSG_LOAD_SUCCESS = 0x0003;
    private Thread mThread;
    private LoadDataHandler mHandler;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    public static boolean isSuccess = false;

    public static final String TAG = "PostMailPresenter";

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
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    public void showPikerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), this)
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();




    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
//        String text = options1Items.get(options1).getPickerViewText() +
//                options2Items.get(options1).get(options2) +
//                options3Items.get(options1).get(options2).get(options3);
        String province  = options1Items.get(options1).getPickerViewText();
        String city  = options2Items.get(options1).get(options2);
        String district  = options3Items.get(options1).get(options2).get(options3);

        String text = province+city+district;

        if (isSuccess) {
            getUI().chooseText(text,province,city,district);
        } else {
            getUI().showToast(getContext().getResources().getString(R.string.data_parse_error));
        }
    }

    /**
     * 请求后台开发票
     * @param userOrderBill
     */
    public void requestSubmit(UserOrderBill userOrderBill) {

        if(TextUtils.isEmpty(userOrderBill.getCompany())){
            getUI().showToast("请填写公司抬头");
            return;
        }

        if(TextUtils.isEmpty(userOrderBill.getBillContent())){
            getUI().showToast("发票内容");
            return;
        }

//        if(TextUtils.isEmpty(userOrderBill.getPrice())){
//            getUI().showToast("请填写公司抬头");
//            return;
//        }

        if(TextUtils.isEmpty(userOrderBill.getReceiver())){
            getUI().showToast("请填写收件人姓名");
            return;
        }

        if(TextUtils.isEmpty(userOrderBill.getReceivePhone())){
            getUI().showToast("请填写收件电话");
            return;
        }

        if(TextUtils.isEmpty(userOrderBill.getProvince())){
            getUI().showToast("请填写地区");
            return;
        }

        if(TextUtils.isEmpty(userOrderBill.getAddress())){
            getUI().showToast("请填写详细地址");
            return;
        }

        String accountId = getUserId();

        userOrderBill.setUserId(accountId);
        String infoGson = new Gson().toJson(userOrderBill);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        HttpApiService.instance().addOrderBill(requestBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(),false) {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<String> response) {
                        dismissDialog();
                        if (checkApiResponse(response)) {
                            Logger.d(TAG,"成功");
                            getUI().showToast("操作成功");
                            getUI().finishAt();
                        }
                    }
                });
    }

    /**
     * 获取订单id 已","分割
     * @param ivoiceModels
     * @return
     */
    public String getOrderId(List<IvoiceModel> ivoiceModels){
        String orderId = "";
        for(int i=0;i<ivoiceModels.size();i++){
            if(i==ivoiceModels.size()-1){
                orderId+=ivoiceModels.get(i).getId();
            }else{
                orderId+=ivoiceModels.get(i).getId();
                orderId+=",";
            }
        }
        return orderId;
    }


    /**
     * 获取订单id 已","分割
     * @param ivoiceModels
     * @return
     */
    public Double getOrderPrice(List<IvoiceModel> ivoiceModels){
        Double orderPrice = 0.0;
        for(int i=0;i<ivoiceModels.size();i++){
           orderPrice+= Double.parseDouble(ivoiceModels.get(i).getPrice());
        }
        return orderPrice;
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

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if(options1Items != null){
            options1Items = null;
        }
        if(options2Items != null){
            options1Items = null;
        }
        if(options3Items != null){
            options1Items = null;
        }

    }
}
