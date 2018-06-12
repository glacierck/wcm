package com.kongrongqi.shopmall.modules.service.presenter;

import android.text.TextUtils;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UpLoadExcelModel;
import com.kongrongqi.shopmall.modules.service.IView.IUpLoadView;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.modules.service.ServiceSetActivity;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/31 0031 on 16:22
 * 作者:penny
 */
public class UpLoadPresenter extends BasePresenter<IUpLoadView> {


    @Override
    public void onUIStart() {
        super.onUIStart();
        getUploadUrl();
    }

    /**
     * 得到上传需灌粉数据URL【购买精准邀友-购买】
     */
    public void getUploadUrl() {
        String accountId = getUserId();
        HttpApiService.instance().getUploadUrl(accountId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(), false) {
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
                    public void onNext(BaseResponse<String> response) {
                        dismissDialog();
                        if (checkApiResponse(response)) {
                            getUI().bindUrl(response.getData());
                        }
                    }
                });
    }

    public void requestUpLoadData(ServiceBean bean) {
        String accountId = getUserId();
        HttpApiService.instance().isUploadExcel(3, accountId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UpLoadExcelModel>>(getUI(), false) {
                    @Override
                    public void onStart() {
                        showDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        ToastUtil.showMessage(getContext(),"网络异常，请重试");
                        super.onError(e);
                    }

                    @Override
                    public void onNext(BaseResponse<UpLoadExcelModel> response) {
                        if (checkApiResponse(response)) {
                            dismissDialog();
                            if (response.getData().getTotal() > 0) {
//                                OrderNumRequest orderInfo = getOrderInfo(response.getData(), bean);
//                                PayActivity.lunch(getContext(), orderInfo, false);
                                ServiceSetActivity.lunch(getContext(),response.getData(), bean);
                            } else {
                                showLongToast(getContext().getString(R.string.not_upload_data));
                            }
                        }
                    }
                });
    }

    private OrderNumRequest getOrderInfo(UpLoadExcelModel model, ServiceBean pBean) {
        String accountId = getUserId();
        OrderNumRequest request = new OrderNumRequest();

        request.setEntrance(pBean.getEntrance());//在哪个入口购买服务
        request.setDeviceWechatId(model.getDeviceWechatId());
        request.setWechatNo(model.getWechatNo());

        request.setName(model.getName());
        request.setPrice(model.getPrice() + "");
        request.setUserId(accountId);
        request.setContent(model.getContent());
        request.setContentName(model.getContentName());
        request.setId(model.getId());
        request.setType(pBean.getType());
        request.setDuration(model.getDuration());
        int duration = StringUtils.getStrToInt(TextUtils.isEmpty(model.getDuration()) ? "0" : model.getDuration());
        request.setExpireDays(duration + pBean.getValidDay() + "");
        request.setDurationUnit(model.getDurationUnit());
        request.setDurationFansNum(model.getDurationFansNum());
        return request;
    }
}
