package com.kongrongqi.shopmall.modules.service.presenter;

import android.app.Activity;
import android.content.Context;

import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.modules.task.FansBServiceActivity;
import com.kongrongqi.shopmall.modules.task.UseServiceActivity;
import com.kongrongqi.shopmall.utils.UserUtil;

/**
 * Created by Administrator on 2017/8/2 0002.
 *
 * 系统加友服务
 */
public class SystemAddFriends extends ServiceManage {

    public SystemAddFriends(Context context, ServiceBean serviceBean) {
        mContext=context;
        this.serviceBean=serviceBean;

        jumpPageForBuy= new Class[]{PayActivity.class};
        jumpPageForUser= new Class[]{FansBServiceActivity.class, UseServiceActivity.class};
        orderNumRequest= getOrderInfo(serviceBean);
    }

    /**
     * 购买服务  1 页面跳转  2.购买
     */
    @Override
    public void buyService() {
        String name = mContext.getClass().getSimpleName(); //获取当前页面 的名称

        for (int i=0;i<jumpPageForBuy.length;i++){
            String simpleName = jumpPageForBuy[i].getSimpleName();
            if(simpleName.equals(name) && i!=jumpPageForBuy.length-1){

            }
        }

    }

    /**
     * 使用服务
     */
    @Override
    public void userService() {

    }

//    private Class getJumpPageClass(){
//        String name = mContext.getClass().getSimpleName(); //获取当前页面 的名称
//        for (Class mClass : jumpPageForBuy){
//            String simpleName = mClass.getSimpleName();
//            if(simpleName.equals(name)){
//
//            }
//        }
//    }

    /**
     * 组装获取订单信息的bean
     *
     * @param model
     * @return
     */
    private OrderNumRequest getOrderInfo(ServiceBean model) {
        String accountId = UserUtil.getUserId(mContext);
        OrderNumRequest request = new OrderNumRequest();
        request.setName(model.getName());
//        request.setPrice(model.getPrice());
        request.setUserId(accountId);
        request.setContent(model.getContent());
        request.setContentName(model.getContentName());
        request.setDurationName(model.getDurationName());
        request.setId(model.getId());
        request.setType(model.getType());
        request.setDurationUnit(model.getDurationUnit());
        request.setDuration(model.getDuration());
        return request;
    }

}
