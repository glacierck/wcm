package com.kongrongqi.shopmall.modules.service;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.IView.IProductView;
import com.kongrongqi.shopmall.modules.service.presenter.ProductDetailPresenter;
import com.kongrongqi.shopmall.modules.service.presenter.ServiceSuper;

/**
 * 创建日期：2017/5/22 0022 on 09:44
 * 作者:penny
 */
public class ProductDetailActivity extends BaseMVPActivity<ProductDetailPresenter> implements IProductView {

    private ServiceBean mListData;
    private Button buy_service;
    private TextView content;
    private TextView zhouqi;
    private TextView price;
    private TextView range;
    private TextView explain;
    private TextView notice;
    private LinearLayout ll_range;

    public static void lunch(Context context, ServiceBean serviceBean) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(Constans.PRODUCT_DETAIL, serviceBean);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_product;
    }

    @Override
    protected ProductDetailPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ProductDetailPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return ProductDetailActivity.this;
    }

    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(mListData.getName());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mListData = (ServiceBean) getIntent().getSerializableExtra(Constans.PRODUCT_DETAIL);

        buy_service = (Button) findViewById(R.id.buy_service);
//        getPresenter().getServiceDetailHtml(mListData.getType());
        //服务内容
        content = (TextView) findViewById(R.id.content);
        //服务周期
        zhouqi = (TextView) findViewById(R.id.zhouqi);
        //服务价格
        price = (TextView) findViewById(R.id.price);
        //服务范围
        range = (TextView) findViewById(R.id.range);

        ll_range = (LinearLayout) findViewById(R.id.ll_range);
        //服务说明
        explain = (TextView) findViewById(R.id.explain);
        //注意事项
        notice = (TextView) findViewById(R.id.notice);

        ServiceBean detailServiceBean = new ServiceSuper(this, mListData).getServiceSuper().getDetailServiceBean();

        content.setText(detailServiceBean.getContent());//服务内容
        if (detailServiceBean.isJoin()) {
            ll_range.setVisibility(View.GONE);
        }
        zhouqi.setText(detailServiceBean.getValidDay() + "天");//服务周期
        price.setText(detailServiceBean.getPrice() + "/" + detailServiceBean.getDurationUnit());//服务价格
        if (detailServiceBean.getType() == Constans.FANSB) {
            ll_range.setVisibility(View.GONE);
            notice.setVisibility(View.INVISIBLE);
        }
        range.setText(detailServiceBean.getAttr1());//服务范围
        explain.setText(detailServiceBean.getExplain());//服务说明
        notice.setText(detailServiceBean.getNotice());

        if (detailServiceBean.getStatus() == 1) {
            buy_service.setText(detailServiceBean.getButtonText());
            buy_service.setEnabled(true);
            buy_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().BuyService(mListData);
                }
            });
        } else {
            buy_service.setText(getString(R.string.wait_hope));
            buy_service.setEnabled(false);
            buy_service.setBackgroundResource(R.drawable.not_buy_bj);
        }
    }

    @Override
    public void finishAt() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return false;
    }

}
