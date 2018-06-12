package com.kongrongqi.shopmall.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.LoadingDialog;


/**
 * 所有Fragment的基类.
 * @author penny
 */
public abstract class BaseFragment extends Fragment implements IUI {

    /**
     * 调用Fragment的startActivityForResult的child *
     */
    private BaseFragment startActivityForResultChild = null;

    private boolean isPaused;
    private boolean isStoped;
    private boolean isDestoryed;
    private boolean isDetached;
    private boolean isHidden;
    private Dialog mWaitingDialog = null;
    private boolean isVisibleToUser = false;
    public Activity mActivty;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        isDetached = false;
        this.mActivty = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDestoryed = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        isStoped = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPaused = false;
    }

    @Override
    public void onPause() {
        isPaused = true;
        super.onPause();
    }

    @Override
    public void onStop() {
        isStoped = true;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        isDestoryed = true;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        isDetached = true;
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 一定不要干掉这段代码
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * setStartActivityForResultChild:设置调用Fragment的startActivityForResult的child. <br/>
     *
     * @param child
     */
    public void setStartActivityForResultChild(BaseFragment child) {
        this.startActivityForResultChild = child;
    }

    public BaseFragment getStartActivityForResultChild(){
        return startActivityForResultChild;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        isHidden = hidden;
        super.onHiddenChanged(hidden);
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public boolean isStoped() {
        return isStoped;
    }

    @Override
    public boolean isDestoryed() {
        return isDestoryed;
    }

    public boolean isFragmentDetached() {
        return isDetached;
    }

    @Override
    public boolean isFragmentHidden() {
        return isHidden;
    }

    @Override
    public boolean isVisibleToUser() {
        return isVisibleToUser;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * 显示等待对话框
     */
    public void showWaitingDialog() {
        dismissWaitingDialogIfShowing();
        if (!isDetached() && !isDestoryed() && !isHidden() && getActivity() != null) {
            LoadingDialog.showInfoMessage(getContext(), getResources().getString(R.string.wait));
        }
    }

    /**
     * 如果等待dialog正在显示，则dismiss掉它
     */
    public void dismissWaitingDialogIfShowing() {
        if (!isDetached() && !isDestoryed()){
            LoadingDialog.dismiss();
        }
    }


    @Override
    public Context getContext() {
        return getActivity();
    }
}
