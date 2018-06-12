package com.kongrongqi.shopmall.modules.account.presenter;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.modules.account.IView.IAccountView;
import com.kongrongqi.shopmall.modules.account.IView.IAddServiceView;
import com.kongrongqi.shopmall.modules.account.adapter.AccountAdaper;
import com.kongrongqi.shopmall.modules.account.adapter.AddServiceAdaper;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:penny
 */
public class AddServicePresenter extends BasePresenter<IAddServiceView>{

    public void setAdapter(XRecyclerView xrecyclerView) {
        AddServiceAdaper adaper = new AddServiceAdaper(this, getContext());
        xrecyclerView.setAdapter(adaper);
    }

}
