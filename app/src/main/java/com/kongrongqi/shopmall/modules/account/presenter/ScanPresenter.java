package com.kongrongqi.shopmall.modules.account.presenter;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.IAccountView;
import com.kongrongqi.shopmall.modules.account.IView.IScanView;
import com.kongrongqi.shopmall.modules.account.ScanActivity;
import com.kongrongqi.shopmall.modules.account.TabFragment;
import com.kongrongqi.shopmall.modules.account.adapter.ContentPagerAdapter;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.Device_Wechat;
import com.kongrongqi.shopmall.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:penny
 */
public class ScanPresenter extends BasePresenter<IScanView> {

}
