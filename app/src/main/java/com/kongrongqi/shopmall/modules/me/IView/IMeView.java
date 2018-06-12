package com.kongrongqi.shopmall.modules.me.IView;

import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.account.bean.Me;
import com.kongrongqi.shopmall.utils.NotifyUtil;

/**
 * 创建日期：2017/5/17 0017 on 17:21
 * 作者:penny
 */
public interface IMeView extends IUI{
    void bindData(Me me);

    NotifyUtil startNotifyProgress();

    void setVersionPoint(int hasUpdate);
}
