package com.kongrongqi.shopmall.modules.service.presenter;

import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.ServiceListModel;
import com.kongrongqi.shopmall.modules.service.IView.IProductView;
import com.kongrongqi.shopmall.modules.service.JoinGrounpActivty;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.modules.service.SelectServiceCTypeActivty;
import com.kongrongqi.shopmall.modules.service.UpLoadActivity;
import com.kongrongqi.shopmall.modules.service.adapter.ServiceAdaper;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/22 0022 on 09:44
 * 作者:penny
 */
public class ProductDetailPresenter extends BasePresenter<IProductView> {


    public void BuyService(ServiceBean serviceBean) {
//        StyleDialog.showBuyService(getContext(), 0, serviceBean.getName(), new StyleDialog.DialogClickListener() {
//            @Override
//            public void onBuy(int position) {
//
//            }
//        });
        lunchDifferentUI(serviceBean);
    }


    /**
     * 跳转到不同的界面
     *
     * @param type
     * @param model
     */
    private void lunchDifferentUI(ServiceBean model) {
        ServiceSuper serviceSuper = new ServiceSuper(getContext(), model).getServiceSuper();
        serviceSuper.buyService();
        getUI().finishAt();
    }

//    /**
//     * 组装获取订单信息的bean
//     *
//     * @param model
//     * @return
//     */
//    private OrderNumRequest getOrderInfo(ServiceBean model) {
//        String accountId = getUserId();
//        OrderNumRequest request = new OrderNumRequest();
//        request.setName(model.getName());
//        request.setPrice(model.getPrice());
//        request.setUserId(accountId);
//        request.setContent(model.getContent());
//        request.setContentName(model.getContentName());
//        request.setDurationName(model.getDurationName());
//        request.setId(model.getId());
//        request.setType(model.getType());
//        request.setDurationUnit(model.getDurationUnit());
//        request.setDuration(model.getDuration());
//        return request;
//    }

}
