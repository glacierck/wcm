package com.kongrongqi.shopmall.modules.service.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.me.bean.JsonBean;
import com.kongrongqi.shopmall.modules.model.FansModel;
import com.kongrongqi.shopmall.modules.model.IndustryModel;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.IView.ISelectPackageView;
import com.kongrongqi.shopmall.modules.service.IView.ISelectServiceCtypeView;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.modules.service.adapter.SelectServiceCtypeAdapter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.OptionsPickerUtil;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;
import com.kongrongqi.shopmall.wedget.materialspinner.MaterialSpinner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/25 0025 on 18:21
 * 作者:penny
 */
public class SelectPackagePresenter extends BasePresenter<ISelectPackageView> {
    public static final String TAG = "JoinGrounpPresenter";

    private Subscription mSubscribe;
    private ServiceBean serviceBean;
    private ArrayList<JsonBean> options1Items;
    private ArrayList<ArrayList<String>> options2Items;
    private ArrayList<ArrayList<ArrayList<String>>> options3Items;
    private String mProvince;
    private String mCity;
    private String mDistrict;
    private boolean isSuccess;


    public void showPicker() {
        StyleDialog.showPikerView(getContext(),
                options1Items, options2Items, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mProvince = options1Items.get(options1).getPickerViewText();
                        mCity = options2Items.get(options1).get(options2);
                        if (isSuccess) {
                            String address = StringUtils.jointAddress(mProvince, mCity, "", "");
                            String province = StringUtils.replaceAddress(mProvince,"省");
                            String city = StringUtils.replaceAddress(mCity,"市");
                            getUI().chooseText(address,province,city);
                        } else {
                            ToastUtil.showMessage(getContext(), getContext().getResources().getString(R.string.data_parse_error));
                        }
                    }
                });
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


    private void initSpinner(MaterialSpinner pInvoice_content, List<String> industryModels) {
//        String[] lStringArray = getContext().getResources().getStringArray(R.array.spiner_list);
        pInvoice_content.setItems(industryModels);
        pInvoice_content.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if (isActivityExist()) {
                    getUI().spinnerChooseText(item);
                }
            }
        });
    }

    public void queryPageForUserDict(MaterialSpinner pInvoice_content) {
        HttpApiService.instance().queryPageForUserDict("fans_industry")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<IndustryModel>>>(getUI(), false) {
                    @Override
                    public void onStart() {
                        super.onStart();
//                        showDialog();
                    }

                    @Override
                    public void onCompleted() {
//                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
//                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<List<IndustryModel>> response) {
//                        dismissDialog();
                        if (checkApiResponse(response, false)) {
                            List<IndustryModel> data = response.getData();
                            initSpinner(pInvoice_content, getIndustryStr(data));
                        }
                    }
                });
    }


    public List<String> getIndustryStr(List<IndustryModel> industryModels) {
        List<String> list = new ArrayList<>();
        list.add("");
        for (IndustryModel industryModel : industryModels) {
            list.add(industryModel.getDictText());
        }
        return list;
    }


    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mSubscribe);
    }


}
