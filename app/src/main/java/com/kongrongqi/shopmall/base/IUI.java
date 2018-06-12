package com.kongrongqi.shopmall.base;

import android.content.Context;

/**
 * MVP的View层协议.
 *
 * @author penny
 */
public interface IUI extends IUIState {
    /**
     * dismissWaitingDialogIfShowing:如果加载对话框正在显示，则dismiss掉它. <br/>
     */
    void dismissWaitingDialogIfShowing();
    /**
     * showWaitingDialog:显示正在加载对话框. <br/>
     */
    void showWaitingDialog();

    void showLoading();
    void showContent();
    void showEmpty();
    void showError();
    Context getContext();

}
