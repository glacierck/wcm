package com.kongrongqi.shopmall.modules.service.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.login.request.OrderInfoRequest;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.login.request.SaleListRequest;
import com.kongrongqi.shopmall.modules.model.ALiPayResponse;
import com.kongrongqi.shopmall.modules.model.AddressModel;
import com.kongrongqi.shopmall.modules.model.OrderInfoMode;
import com.kongrongqi.shopmall.modules.model.PayResultModel;
import com.kongrongqi.shopmall.modules.model.PayTypeUrlModel;
import com.kongrongqi.shopmall.modules.model.SaleModel;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.IView.IPayView;
import com.kongrongqi.shopmall.modules.service.adapter.SaleAdapter;
import com.kongrongqi.shopmall.modules.service.holder.RecycleViewDivider;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/20 0020 on 20:35
 * 作者:penny
 */
public class PayPresenter extends BasePresenter<IPayView> {
    /**
     * 订单支付成功 *
     */
    private static final String STATUS_SUCCESS = "9000";

    /**
     * 正在处理中 *
     */
    private static final String STATUS_PROCESSING = "8000";

    /**
     * 订单支付失败 *
     */
    private static final String STATUS_FAILED = "4000";

    /**
     * 用户中途取消 *
     */
    private static final String STATUS_CANCELED = "6001";

    /**
     * 网络连接出错 *
     */
    private static final String STATUS_NETWORK_ERROR = "6002";

    public static final String TAG = "PayPresenter";
    public static final int ALIPAY = 0;
    public static final int WECHATPAY = 1;
    private Subscription mPayUrlSubscribe;
    private List<PayTypeUrlModel> mPayTypeUrlModels; //0 支付宝 //1 微信
    private String mUserId;
    private Subscription mOrderSubscription;
    private Subscription mOrderInfoSubscribe;
    private OrderNumRequest result;
    private SaleAdapter mAdapter;
    private Subscription mSaleSubscription;
    private SaleModel mSaleModel;


    public void requestPay(BaseMVPActivity activity, int type, OrderNumRequest result) {
        this.result = result;
        if (result.getType() == 2 || result.getType() == 5) { //系统加友 账号托管 都需要验证有没有选择购买哪种优惠
            if (mSaleModel == null) {
                showLongToast(App.getInstance().getString(R.string.not_choose_commodity));
                return;
            } else {
                showHint(activity, type, result);
            }
        } else {
            showHint(activity, type, result);
        }
    }

    private void showHint(BaseMVPActivity pActivity, int pType, OrderNumRequest pResult) {
        StyleDialog.showHintDialog(getContext(), "您即将购买" + pResult.getName() + ",购买后将无法退还，是否确定？", new StyleDialog.DialogEnterListener() {
            @Override
            public void onEnter() {
                payment(pActivity, pType, result);
            }
        });
    }

    /**
     * 支付接口调用
     */
    public void payment(BaseMVPActivity activity, int type, OrderNumRequest result) {
        HttpApiService.instance().payTypeUrl()
                .doOnNext(new Action1<BaseResponse<List<PayTypeUrlModel>>>() {
                    @Override
                    public void call(BaseResponse<List<PayTypeUrlModel>> listBaseResponse) {
                        if (checkApiResponse(listBaseResponse)) {
                            mPayTypeUrlModels = listBaseResponse.getData();
                            Logger.d("支付 ", "获取回调地址");
                        }
                    }
                })
                .flatMap(new Func1<BaseResponse<List<PayTypeUrlModel>>, Observable<BaseResponse<OrderInfoMode>>>() {
                    @Override
                    public Observable<BaseResponse<OrderInfoMode>> call(BaseResponse<List<PayTypeUrlModel>> listBaseResponse) {
                        int aliPayOrWechat = aliPayOrWechat(type);
                        Logger.d(TAG, "orderInfo" + result.toString());
                        result.setPaymentType(aliPayOrWechat);
                        return HttpApiService.instance().orderInfo(result);
                    }
                })
                .map(new Func1<BaseResponse<OrderInfoMode>, OrderInfoMode>() {
                    @Override
                    public OrderInfoMode call(BaseResponse<OrderInfoMode> orderInfoModeBaseResponse) {
                        OrderInfoMode orderInfoMode = null;
                        if (checkApiResponse(orderInfoModeBaseResponse)) {
                            orderInfoMode = orderInfoModeBaseResponse.getData();
                            Logger.d("支付 ", "获取订单信息");
                        }
                        return orderInfoMode;
                    }
                })
                .map(new Func1<OrderInfoMode, OrderInfoRequest>() {
                    @Override
                    public OrderInfoRequest call(OrderInfoMode orderInfoMode) {
                        OrderInfoRequest orderInfoRequest = new OrderInfoRequest();
                        String payJumpUrl = mPayTypeUrlModels.get(type).getPayJumpUrl();
                        String notifyUrl = mPayTypeUrlModels.get(type).getNotifyUrl();
                        String accountId = getUserId();
                        orderInfoRequest.setUserId(accountId);
                        orderInfoRequest.setMoney(orderInfoMode.getPrice() + "");
                        orderInfoRequest.setNotifyUrl(notifyUrl);
                        orderInfoRequest.setPaymentConfigId(aliPayOrWechat(type));
                        orderInfoRequest.setOrderNo(orderInfoMode.getOrderNo());
                        orderInfoRequest.setUserOrderId(orderInfoMode.getId());
                        orderInfoRequest.setPayNotifyUrl(payJumpUrl);
                        return orderInfoRequest;
                    }
                })
                .flatMap(new Func1<OrderInfoRequest, Observable<BaseResponse<ALiPayResponse>>>() {
                    @Override
                    public Observable<BaseResponse<ALiPayResponse>> call(OrderInfoRequest orderInfoRequest) {
                        Logger.d("支付 ", "支付");
                        return HttpApiService.instance().orderSign(orderInfoRequest);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ALiPayResponse>>(getUI(), false) {

                    @Override
                    public void onStart() {
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
                        ToastUtil.showMessage(getContext(), "支付失败");
                    }

                    @Override
                    public void onNext(BaseResponse<ALiPayResponse> response) {
                        if (checkApiResponse(response, false)) {
                            String payInfo = response.getData().getAuthInfo();
                            ALIRunable runable = new ALIRunable(PayPresenter.this, activity, payInfo);
                            ExecutorService service = Executors.newSingleThreadExecutor();
                            service.execute(runable);
                            service.shutdown();
                        }
                    }
                });
    }


    public void requestAddress() {
        String accountId = getUserId();
        HttpApiService.instance().addressList(accountId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<AddressModel>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<AddressModel>> response) {
                        if (checkApiResponse(response) && response.getData()!=null && response.getData().size()>0) {
                            AddressModel data = response.getData().get(0);
                            if(data.getIsDefault()==1){
                                getUI().setDefault(data);
                            }
                        }
                    }
                });
    }





    /***
     * 支付类型
     *
     * @param type
     * @return
     */
    private int aliPayOrWechat(int type) {
        if (type == ALIPAY) {
            return 11;
        } else if (type == WECHATPAY) {
            return 8;
        }
        Logger.d(TAG, "type:" + type);
        return 0;
    }

    /**
     * 根据商品显示 不同布局
     *
     * @param type
     */
    public void showDifferentUI(int type) {
        if (type == Constans.SYSTEM_ADD_FRIEND
                || type == Constans.TRUSTEESHIP_ACCOUNT) { //系统加友
            if (isActivityExist())
                getUI().showSystemAddFriendUI();
        } else {
            if (isActivityExist())
                getUI().showCommonUI();
        }
    }

    /**
     * 打折列表
     *
     * @param pType
     * @param pRecyclerView
     */
    public void showSaleListView(int pType, XRecyclerView pRecyclerView) {
        pRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pRecyclerView.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, 2, getContext().getResources().getColor(R.color.cl_1a000000)));
        pRecyclerView.setLoadingMoreEnabled(false);
        pRecyclerView.setPullRefreshEnabled(false);
        mAdapter = new SaleAdapter(getContext(), this, pType);
        SaleListRequest lRequest = new SaleListRequest();
        lRequest.setServerType(pType);
        requestSaleList(lRequest, mAdapter, pRecyclerView);
    }

    /**
     * 打折请求
     *
     * @param pRequest
     * @param lAdapter
     * @param pRecyclerView
     */
    private void requestSaleList(SaleListRequest pRequest, final SaleAdapter lAdapter, XRecyclerView pRecyclerView) {
        mSaleSubscription = HttpApiService.instance().saleList(pRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<List<SaleModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("==onError==", "==================");
                    }

                    @Override
                    public void onNext(BaseResponse<List<SaleModel>> pListBaseResponse) {
                        if (checkApiResponse(pListBaseResponse)) {
                            List<SaleModel> lSaleModelList = pListBaseResponse.getData();
                            Collections.reverse(lSaleModelList);
                            lAdapter.setDatas(lSaleModelList);
                            pRecyclerView.setAdapter(lAdapter);
                        }
                    }
                });
    }

    public void choose(SaleModel pSaleModel) {
        mSaleModel = pSaleModel;
        if (pSaleModel == null) {
            List lDatas = mAdapter.getDatas();
            int lSize = lDatas.size();
            for (int i = 0; i < lSize; i++) {
                SaleModel lO = (SaleModel) lDatas.get(i);
                lO.setCheck(false);
            }
            mAdapter.notifyAllUI();
        }
        if (isActivityExist()) {
            getUI().updatePrice(pSaleModel);
        }
    }


    /**
     * 支付线程
     */
    static class ALIRunable implements Runnable {

        private final BaseMVPActivity mActivity;
        private final String mPayInfo;
        private final PayPresenter mPresenter;

        public ALIRunable(PayPresenter presenter, BaseMVPActivity activity, String payInfo) {
            this.mActivity = activity;
            this.mPayInfo = payInfo;
            this.mPresenter = presenter;
        }

        @Override
        public void run() {
            // 构造PayTask 对象
            PayTask alipay = new PayTask(mActivity);
            // 调用支付接口，获取支付结果
            String result = alipay.pay(mPayInfo, true);
            Logger.i(TAG, "支付结果：" + result.toString());
            PayResultModel resultModel = convertResult(result);
            App.getInstance().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    mPresenter.payResultResponse(resultModel);
                }
            });
        }

        private PayResultModel convertResult(String result) {
            PayResultModel resultModel = new PayResultModel();
            String[] resultParams = result.split(";");
            for (String resultParam : resultParams) {
                if (resultParam.startsWith("resultStatus")) {
                    resultModel.setResultStatus(getValue(resultParam, "resultStatus"));
                }
                if (resultParam.startsWith("result")) {
                    resultModel.setResult(getValue(resultParam, "result"));
                }
                if (resultParam.startsWith("memo")) {
                    resultModel.setMemo(getValue(resultParam, "memo"));
                }
            }

            resultModel.setPayResultStatus(convertResutStatus(resultModel.getResultStatus()));
            return resultModel;

        }

        private String getValue(String content, String key) {
            String prefix = key + "={";
            return content.substring(content.indexOf(prefix) + prefix.length(),
                    content.lastIndexOf("}"));
        }

        /**
         * 转换alipay支付状态为通用支付状态
         *
         * @param alipayResult
         * @return
         */
        private PayResultModel.PayResultStatus convertResutStatus(String alipayResult) {
            if (TextUtils.equals(STATUS_SUCCESS, alipayResult)) {
                return PayResultModel.PayResultStatus.SUCCESS;
            } else if (TextUtils.equals(STATUS_FAILED, alipayResult)) {
                return PayResultModel.PayResultStatus.FAILED;
            } else if (TextUtils.equals(STATUS_CANCELED, alipayResult)) {
                return PayResultModel.PayResultStatus.CANCELED;
            } else if (TextUtils.equals(STATUS_NETWORK_ERROR, alipayResult)) {
                return PayResultModel.PayResultStatus.NETWORK_ERROR;
            } else if (TextUtils.equals(STATUS_PROCESSING, alipayResult)) {
                return PayResultModel.PayResultStatus.PROCESSING;
            }
            return null;
        }
    }

    /**
     * 根据状态 显示支付结果
     *
     * @param resultModel 结果类
     */
    private void payResultResponse(PayResultModel resultModel) {
        switch (resultModel.getPayResultStatus()) {
            case SUCCESS:
                use(result);//使用服务弹框
                break;
            case CANCELED:
                showLongToast(getContext().getString(R.string.pay_cancel));
//                choose(null);
                break;
            case FAILED:
                showLongToast(getContext().getString(R.string.pay_error));
//                choose(null);
                break;
        }
    }

    /**
     * 使用
     */
    public void use(OrderNumRequest result) {
        if (result.getType() == 1) { // 号槽
            getUI().jumpAccount();
        } else if (result.getType() == 11 || result.getType() == 12) {//套餐
            if (isActivityExist())
                StyleDialog.showEnter(getContext(), "购买成功", "我们正在为您紧急备货中，请耐心等待", new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {
                        getUI().jumpAccount();
                    }
                });
        } else { //其他服务
            if (isActivityExist()) {
                String content = "该服务将于" + result.getExpireDays() + "天后失效，是否即刻使用？";
                if (result.getType() == 2) {//系统服务
                    if (mSaleModel != null) {
                        useOrCancel(result, content);
                    }
                } else {
                    useOrCancel(result, content);
                }
            }
        }
    }

    private void useOrCancel(final OrderNumRequest result, String pContentText) {

        StyleDialog.showUserService(getContext(), "购买成功", pContentText, "确定", "取消"
                , new StyleDialog.DialogUserServiceListener() {
                    @Override
                    public void onUser() {//确定
                        dialogUseService(result);
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//取消
                        if(result.getEntrance()==ServiceSuper.ACCOUNT){
                            getUI().jumpAccountNotUse();
                        }else{
                            getUI().jumpNotUse();
                        }
                    }
                });
    }

    /**
     * item点击事件
     *
     * @param position      点击索引
     * @param notUseAdapter adpter
     * @param type          2灌粉服务A   3灌粉服务B  4入群服务  5养号服务
     */
    private void dialogUseService(OrderNumRequest result) {
        UserServiceParam userServiceParam = new UserServiceParam();
        userServiceParam.setType(result.getType());
        userServiceParam.setUserId(getUserId());
        userServiceParam.setDeviceWechatId(result.getDeviceWechatId());
        userServiceParam.setWechatNo(result.getWechatNo());
        userServiceParam.setEntrance(result.getEntrance());//进程入口
        ServiceSuper serviceSuper = new ServiceSuper(getContext(), userServiceParam).getServiceSuperUser();
        serviceSuper.useService();
        getUI().finishAt();
    }

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mPayUrlSubscribe);
        releaseSubscription(mOrderSubscription);
        releaseSubscription(mOrderInfoSubscribe);
        releaseSubscription(mSaleSubscription);
    }


}
