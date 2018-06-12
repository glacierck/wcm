package com.kongrongqi.shopmall.modules.account;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class AccountScanActivity extends ScanActivity{


    public static void lunch(Context context) {
        Intent intent = new Intent(context, AccountScanActivity.class);
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
