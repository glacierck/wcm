package com.kongrongqi.shopmall.modules.login.presenter;

import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.modules.login.Iview.IAgreementView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 创建日期：2017/5/17 0017 on 14:00
 * 作者:penny
 */
public class AgreementPresenter extends BasePresenter<IAgreementView> {


    public void readText() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String text = "";
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(getContext().getAssets().open("agreenment.txt")));
                    String line;
                    StringBuffer sb = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    text = sb.toString();
                    if(isActivityExist()){
                        getUI().showTextAgreement(text);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
