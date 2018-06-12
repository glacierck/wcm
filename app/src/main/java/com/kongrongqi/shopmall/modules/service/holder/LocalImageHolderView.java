package com.kongrongqi.shopmall.modules.service.holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.http.URLConstans;
import com.kongrongqi.shopmall.modules.account.NewDeviceServiceActivity;
import com.kongrongqi.shopmall.modules.me.AboutActivity;
import com.kongrongqi.shopmall.modules.me.FansActivity;
import com.kongrongqi.shopmall.modules.me.bean.Banner;
import com.kongrongqi.shopmall.modules.service.presenter.ServicePresenter;
import com.kongrongqi.shopmall.utils.GlideUtil.GlideUtils;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;

import java.util.List;

/**
 * 创建日期：2017/5/20 0020 on 18:33
 * 作者:penny
 */
public class LocalImageHolderView implements Holder<Banner> {
    public static final String TAG = "LocalImageHolderView";
    private final List<Banner> result;
    private ImageView imageView;
    protected Context mContext;
    protected ServicePresenter servicePresenter;

    public LocalImageHolderView(List<Banner> banerList, Context context, ServicePresenter servicePresenter) {
        this.result = banerList;
        this.servicePresenter=servicePresenter;
        this.mContext=context;
    }

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Banner banner) {
        Logger.d(TAG,"TAG:"+position+"data:"+banner);

        String url = ApiBean.instance().XHS_1 + "/" + banner.getImage();
        GlideUtils.getInstance().loadImage(context,imageView,url);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG,"click"+position);

                //1 跳url 内开   2 跳url 外开   3 掉内部app  4 跳外包app
                switch (banner.getType()){
                    case 1:
                        FansActivity.lunch(mContext,banner.getUrl());
                        break;
                    case 2:
                        aboutMe(banner.getUrl());
                        break;
                    case 3:
                        switch (banner.getUrl()){
                            case "newServiceCombo":
                                NewDeviceServiceActivity.lunch(mContext);
                                break;
                        }
                        break;
                    case 4:
                        break;
                }
//                switch (position){
//                    case 0://公司简介
//                        AboutActivity.lunch(mContext);
//                        break;
//                    case 1://新套餐
//                        NewDeviceServiceActivity.lunch(mContext);
////                        servicePresenter.getNewDeviceServiceInfo();
//                        break;
//                    case 2://发烧友
////                        aboutMe();
//                        FansActivity.lunch(mContext);
//                        break;
//                }
            }
        });
    }


    //    启动Android默认浏览器
    private void aboutMe(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        mContext.startActivity(intent);
    }


}
