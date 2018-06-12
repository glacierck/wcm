package com.kongrongqi.shopmall.modules.account.IView;

import com.kongrongqi.shopmall.base.IUI;

/**
 * 创建日期：2017/5/17 0017 on 17:34
 * 作者:penny
 */
public interface IAddAccountView extends IUI{
    void finishAct();


    void press123(int step);


    void addSuccessViewRefrash(int currentProgress);

    void finishAcResult();


}
