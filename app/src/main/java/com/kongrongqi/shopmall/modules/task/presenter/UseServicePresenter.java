package com.kongrongqi.shopmall.modules.task.presenter;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.ServiceDetailsActivity;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.login.request.RequestEmpty;
import com.kongrongqi.shopmall.modules.model.BindWechatAccountModel;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.modules.model.UnUseGrounpServiceModel;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.holder.RecycleViewDivider;
import com.kongrongqi.shopmall.modules.task.IView.IUserServiceView;
import com.kongrongqi.shopmall.modules.task.adapter.UseServiceAdapter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import org.greenrobot.eventbus.EventBus;

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
 * 创建日期：2017/5/23 0023 on 13:02
 * 作者:penny
 */
public class UseServicePresenter extends BaseLoadingPresenter<BindWechatAccountModel, IUserServiceView> {

    public static final String TAG = "UseServicePresenter";
    private List<BindWechatAccountModel> multiChooseModels;
    private BindWechatAccountModel mSingleModel;
    private Subscription mUseSubscribe;
    private UserServiceParam param;

    public void setAdapter(XRecyclerView recyclerView, UserServiceParam param) {
        this.param=param;
        setXRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new UseServiceAdapter(this, getContext(), param.getType());
        recyclerView.setAdapter(mSuperAdapter);
        recyclerView.refresh();
    }

    /**
     * 获取we_chat账号列表
     *
     * @param b
     */
    private void requestData(boolean isRefresh) {
        RequestEmpty requestEmpty = new RequestEmpty();
        String userId = getUserId();
        requestEmpty.setUserId(userId);
        mSuperSubscription = HttpApiService.instance().bindWechatAccount(getUserId(),param.getType(),getToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<BindWechatAccountModel>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<BindWechatAccountModel>> listBaseResponse) {
                        if (checkApiResponse(listBaseResponse)) {
                            List<BindWechatAccountModel> data = listBaseResponse.getData();
                            refreshOrLoadMore(data, isRefresh);
                        }
                    }
                });
    }

    public void buyAccount() {
        getUI().buyAccount();
    }
    /**
     * 更新UI
     */
    public void updateUI() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mSuperAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 使用服务
     */
    public void useService() {
        if ( param.getType()== 0) { //没有多选
//            if ( param.getType()== 3 || param.getType()==6) { //灌粉B 多选
            useMultiService();
        } else {//灌粉A 群组 养号
            useSingleService();
        }
    }

    /**
     * use multi Service
     */
    private void useMultiService() {
        if (null == multiChooseModels || multiChooseModels.size() == 0) {
            if (isActivityExist()) {
                showLongToast(getContext().getString(R.string.current_no_choose_acccount));
            }
        } else {
            StringBuffer wechatBuffer = new StringBuffer();
            for (int i = 0; i < multiChooseModels.size(); i++) {
                BindWechatAccountModel model = multiChooseModels.get(i);
                wechatBuffer.append(model.getWechatNo()).append(",");
            }
//            showErroyDevice(wechatBuffer.toString());
            showErroyDevice(multiChooseModels.get(0));
        }
    }

    /**
     * use single service
     */
    private void useSingleService() {
        if (mSingleModel == null) {
            if (isActivityExist()) {
                showLongToast(getContext().getString(R.string.current_no_choose_acccount));
            }
        } else {
            showErroyDevice(mSingleModel);
        }
    }






    /**
     * 异常设备弹框
     */
    public void showErroyDevice(BindWechatAccountModel no) {

        StringBuilder sb  = new StringBuilder();
        sb.append("您即将选择"+no.getWechatNo()+"执行服务，");

        if(no.getIsSameTypeService()==1){//如果又相同类型服务
            sb.append("次日将暂停"+no.getSameTypeServiceName()+"，是否确定？");
        }else if(no.getState()==9){//账号登出
            sb.append("执行时系统会自动登录该账号。");
        }else{
            sb.append("请确认该账号已在设备上登录。");
        }

        StyleDialog.showUserService(getContext(), "注意", sb.toString(), "确定","取消", new StyleDialog.DialogUserServiceListener() {
            @Override
            public void onUser() {
                if (param.getType() == 0) { //没有多选
//                    if (param.getType() == 3 || param.getType()==6) { //灌粉B,C 多选
                    useWechatAccount(null, multiChooseModels, false);
                } else {//灌粉A 群组 养号
                    useWechatAccount(mSingleModel, null, true);
                }
            }
        },null);
    }

    /**
     * 选中微信账号使用服务
     *
     * @param singleModel
     * @param multiChooseModels
     * @param isSingle
     */
    private void useWechatAccount(BindWechatAccountModel singleModel,List<BindWechatAccountModel> mutilChooseModels,boolean isSingle) {
        String id = null;
        String wechatAccount = null;
        if (isSingle) {//single
            id = singleModel.getId();
            wechatAccount = singleModel.getWechatNo();
            getRequestMap(singleModel, null, isSingle);
        } else {//multi
            id = mutilChooseModels.get(0).getId();
            wechatAccount = mutilChooseModels.get(0).getWechatNo();
            getRequestMap(null, mutilChooseModels, isSingle);
        }
        String infoGson = new Gson().toJson(param);
        Logger.d(TAG, "info_Gson:" + infoGson);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        String finalWechatAccount = wechatAccount;
        String finalId = id;
        mUseSubscribe = HttpApiService.instance().useService(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(),false) {

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
                    public void onNext(BaseResponse<String> baseResponse) {
                        if (checkApiResponse(baseResponse,false)) {
                            DeviceWechat deviceWechat = new DeviceWechat();
                            deviceWechat.setId(finalId);
                            deviceWechat.setWechatNo(finalWechatAccount);
                            ServiceDetailsActivity.lunch(getContext(), deviceWechat);
                        }
                        dismissDialog();
                    }
                });
    }

    private void getRequestMap(BindWechatAccountModel singleModel, List<BindWechatAccountModel> multiChooseModels,boolean isSingle) {
        if (isSingle) {
            getRequestSingle(singleModel);
        } else {
            getRequestMulti(multiChooseModels);
        }
    }

    /**
     * 多个账号
     * @param mutilChooseModels
     * @return
     */
    private void getRequestMulti(List<BindWechatAccountModel> mutilChooseModels) {
        StringBuffer idBuffer = new StringBuffer();
        StringBuffer wechatBuffer = new StringBuffer();
        for (int i = 0; i < mutilChooseModels.size(); i++) {
            BindWechatAccountModel model = mutilChooseModels.get(i);
            idBuffer.append(model.getId()).append(",");
            wechatBuffer.append(model.getWechatNo()).append(",");
        }
        param.setWechatNo(wechatBuffer.toString());
        param.setDeviceWechatId(idBuffer.toString());
    }

    /**
     * 单个账号
     *
     * @param singleModel
     * @return
     */
    private void getRequestSingle(BindWechatAccountModel singleModel) {
        param.setWechatNo(singleModel.getWechatNo());
        param.setDeviceWechatId(singleModel.getId());
    }



    /**
     * item 单选
     *
     * @param currentP
     */
    public void singleChoose(BindWechatAccountModel currentP) {
        if (currentP == null) {
            Logger.d(TAG, "currentP == null");
            return;
        }
        Logger.d(TAG, currentP.toString());
        mSingleModel = currentP;
    }

    /**
     * item 多选
     *
     * @param storageList 选中的集合
     */
    public void multiChooseListData(List<BindWechatAccountModel> storageList) {
        if (storageList == null) {
            Logger.d(TAG, "storageList == null");
            if (multiChooseModels != null && multiChooseModels.size() != 0) {
                multiChooseModels.clear();
            }
            return;
        }
        multiChooseModels = storageList;
    }


    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mSuperSubscription);
        releaseSubscription(mUseSubscribe);
    }

    @Override
    public void onRefreshList() {
        requestData(true);
    }

    @Override
    public void onLoadMoreList() {

    }
}
