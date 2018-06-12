package com.kongrongqi.shopmall.modules.me;

import android.content.Context;
import android.content.Intent;

import com.kongrongqi.shopmall.modules.account.ReplaceIMEIActivity;
import com.kongrongqi.shopmall.modules.account.ScanActivity;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class MeIMEIScanActivity extends ScanActivity{


    public static void lunch(Context context) {
        Intent intent = new Intent(context, MeIMEIScanActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void scanQRCodeSuccess(String result) {
        Intent intent = new Intent();
        intent.putExtra(ReplaceIMEIActivity.MACHINE_CODE,result);
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
