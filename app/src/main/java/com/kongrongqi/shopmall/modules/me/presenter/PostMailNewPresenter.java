package com.kongrongqi.shopmall.modules.me.presenter;

import android.content.res.Resources;
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
import com.kongrongqi.shopmall.modules.me.IView.IPostMailNewView;
import com.kongrongqi.shopmall.modules.me.IView.IPostMailView;
import com.kongrongqi.shopmall.modules.me.bean.JsonBean;
import com.kongrongqi.shopmall.modules.me.bean.UserOrderBill;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;
import com.kongrongqi.shopmall.utils.CommonUtils;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.OptionsPickerUtil;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.wedget.StyleDialog;
import com.kongrongqi.shopmall.wedget.materialspinner.MaterialSpinner;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 新发票
 */
public class PostMailNewPresenter extends BasePresenter<IPostMailNewView> implements MaterialSpinner.OnItemSelectedListener<String> {

    private static final String TAG = "PostMailNewPresenter";
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    public static boolean isSuccess = false;
    private String mProvince;
    private String mCity;
    private String mDistrict;

    public void showPicker() {
        StyleDialog.showPikerView(getContext(),
                options1Items, options2Items, options3Items, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mProvince = options1Items.get(options1).getPickerViewText();
                        mCity = options2Items.get(options1).get(options2);
                        mDistrict = options3Items.get(options1).get(options2).get(options3);
                        String address = StringUtils.jointAddress(mProvince, mCity, mDistrict, "");
                        if (isSuccess) {
                            getUI().chooseText(address, mProvince, mCity, mDistrict);
                        } else {
                            getUI().showToast(getContext().getResources().getString(R.string.data_parse_error));
                        }
                    }
                });
    }

    /**
     * 请求后台开发票
     *
     * @param userOrderBill
     */
    public void requestSubmit(UserOrderBill userOrderBill) {

        if (TextUtils.isEmpty(userOrderBill.getCompany())) {
            getUI().showToast("请填写公司抬头");
            return;
        }

        if (TextUtils.isEmpty(userOrderBill.getTaxpayerRegNo())) {//纳税人识别号
            getUI().showToast("请填写纳税人识别号");
            return;
        }else if(!StringUtils.checkPhone(userOrderBill.getTaxpayerRegNo())){
            getUI().showToast("纳税人识别号格式有误，请重新输入");
            return;
        }

        if (TextUtils.isEmpty(userOrderBill.getRegAddress())) {
            getUI().showToast("请填写注册地址");
            return;
        }

        if (TextUtils.isEmpty(userOrderBill.getRegPhone())) {
            getUI().showToast("请填写公司电话");
            return;
        }else if(!StringUtils.checkPhone(userOrderBill.getRegPhone())){
            getUI().showToast("公司电话有误，请重新输入");
            return;
        }

        if (TextUtils.isEmpty(userOrderBill.getOpeningBank())) {
            getUI().showToast("请填写开户行");
            return;
        }

        if (TextUtils.isEmpty(userOrderBill.getBank_account())) {
            getUI().showToast("请填写银行账号");
            return;
        }

        if (TextUtils.isEmpty(userOrderBill.getBillContent())) {
            getUI().showToast("请填写发票内容");
            return;
        }

        if (TextUtils.isEmpty(userOrderBill.getPrice())) {
            getUI().showToast("发票金额");
            return;
        }

        //纸质
        if (userOrderBill.getType() == 2 && TextUtils.isEmpty(userOrderBill.getReceiver())) {
            getUI().showToast("请填写收件人");
            return;
        }
        if (userOrderBill.getType() == 2 && TextUtils.isEmpty(userOrderBill.getReceivePhone())) {
            getUI().showToast("请填写收件人电话");
            return;
        } else {
            if (userOrderBill.getType() == 2 && !StringUtils.isMobileNum(userOrderBill.getReceivePhone())) {
                getUI().showToast("收件人电话有误，请重新输入");
                return;
            }
        }
        if (userOrderBill.getType() == 2 && TextUtils.isEmpty(userOrderBill.getAddress())) {
            getUI().showToast("请填写详细地址");
            return;
        }
        //电子发票
        if (userOrderBill.getType() == 1 && TextUtils.isEmpty(userOrderBill.getEmail())) {
            getUI().showToast("请填写邮箱");
            return;
        } else {
            if (userOrderBill.getType() == 1 && !StringUtils.isEmail(userOrderBill.getEmail())) {
                Logger.d(TAG, "邮箱不正确");
                getUI().showToast(getContext().getString(R.string.email_error));
                return;
            }
        }


        StyleDialog.showUserService(getContext(), "提示", "是否确认提交？", new StyleDialog.DialogUserServiceListener() {
            @Override
            public void onUser() {
                commitData(userOrderBill);
            }
        });
    }

    private void commitData(UserOrderBill userOrderBill) {
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
                        if (checkApiResponse(response, false)) {
                            Logger.d(TAG, "成功");
                            getUI().showToast("操作成功");
                            getUI().finishAt();
                        }
                    }
                });
    }


    /**
     * 获取订单id 已","分割
     *
     * @param ivoiceModels
     * @return
     */
    public String getOrderId(List<IvoiceModel> ivoiceModels) {
        String orderId = "";
        for (int i = 0; i < ivoiceModels.size(); i++) {
            if (i == ivoiceModels.size() - 1) {
                orderId += ivoiceModels.get(i).getId();
            } else {
                orderId += ivoiceModels.get(i).getId();
                orderId += ",";
            }
        }
        return orderId;
    }


    /**
     * 获取开票总价
     *
     * @param ivoiceModels
     * @return
     */
    public Double getOrderPrice(List<IvoiceModel> ivoiceModels) {
        BigDecimal price = new BigDecimal("0.00");
        for (int i = 0; i < ivoiceModels.size(); i++) {
            BigDecimal b1 = new BigDecimal(ivoiceModels.get(i).getPrice().toString());
            price = price.add(b1);
        }
        double f1 = price.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }


    /**
     * 获取选择地区初始化数据
     */
    public void getAddressJsonData() {
        Observable.fromCallable(new Callable<Map>() {
            @Override
            public Map call() throws Exception {
                return OptionsPickerUtil.startLoadData(getContext());
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(TAG, "===onError===" + e.toString());
                    }

                    @Override
                    public void onNext(Map map) {
                        isSuccess = true;
                        options1Items = (ArrayList<JsonBean>) map.get("mProvince");
                        options2Items = (ArrayList<ArrayList<String>>) map.get("mCity");
                        options3Items = (ArrayList<ArrayList<ArrayList<String>>>) map.get("mDistrict");
                    }
                });
    }

    public void initSpinner(MaterialSpinner pInvoice_content) {
        String[] lStringArray = getContext().getResources().getStringArray(R.array.spiner_list);
        pInvoice_content.setItems(lStringArray);
        pInvoice_content.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
        if (isActivityExist()) {
            getUI().spinnerChooseText(item);
        }
        Logger.d(TAG, item);
    }
}
