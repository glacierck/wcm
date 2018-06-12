package com.kongrongqi.shopmall.modules.login.presenter;

import com.kongrongqi.shopmall.modules.login.request.AccountBean;
import com.kongrongqi.shopmall.utils.FileUtils;
import com.kongrongqi.shopmall.utils.DISPath;
import com.kongrongqi.shopmall.utils.Logger;

/**
 * 创建日期：2017/6/2 0002 on 20:13
 * 作者:penny
 */
public class LoginInfoManager {
    private AccountBean mAccountBean;
    private String mDataPath;

    private static LoginInfoManager mInstance;

    private LoginInfoManager() {
        mDataPath = DISPath.PATH_BASE + "/login";
    }

    public static LoginInfoManager getInstance() {
        if (mInstance == null) {
            Logger.e("LoginInfoManager","getInstance==null");
            mInstance = new LoginInfoManager();
        }
        mInstance.initAccount();
        return mInstance;
    }

    private void initAccount() {
        Logger.e("LoginInfoManager","initAccount before"+(mAccountBean==null));
        mAccountBean = (AccountBean) FileUtils.readObjectFromFile(mDataPath);
        Logger.e("LoginInfoManager","initAccount"+(mAccountBean==null));

    }

    public void saveAccountInfo(AccountBean accountBean) {
        Logger.e("saveAccountInfo",accountBean.toString());
        Logger.e("saveAccountInfo",mDataPath);
        mAccountBean = accountBean;
        FileUtils.writeObjectToFile(mDataPath,accountBean);
    }

    public String getToken(){
        if(mAccountBean == null){
            return null;
        }
        return mAccountBean.getToken();
    }

    public AccountBean getmAccountBean(){
        Logger.d("getmAccountBean",""+(mAccountBean==null));
        return mAccountBean;
    }

    public void clearAccount(){
        FileUtils.deleteFile(mDataPath);
        mAccountBean  = null;
        mInstance = null;
    }
}
