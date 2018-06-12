package com.kongrongqi.shopmall.modules.task;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.model.CallModel;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.presenter.ServiceSuper;
import com.kongrongqi.shopmall.modules.task.IView.CallTxtView;
import com.kongrongqi.shopmall.modules.task.presenter.CallTxtPresenter;
import com.kongrongqi.shopmall.utils.ToastUtil;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class CallTxtActivity extends BaseMVPActivity<CallTxtPresenter> implements CallTxtView {

    private Button login_enter;
    private UserServiceParam param;
    private XRecyclerView mRecylerView;

    public static void lunch(Context context, UserServiceParam param) {
        Intent intent = new Intent(context, CallTxtActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constans.USER_SERVICE_PARAM, param);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_call_txt;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.call_txt_title);
    }

    @Override
    protected CallTxtPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new CallTxtPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return this;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        param = (UserServiceParam) mBundle.getSerializable(Constans.USER_SERVICE_PARAM);
        mRecylerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
        mRecylerView.setPullRefreshEnabled(false);
        getPresenter().setAdapter(mRecylerView);


        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallModel selectItem = getPresenter().getSelectItem();
//                if(selectItem==null || TextUtils.isEmpty(selectItem.getCallTxtInput())){
                if(selectItem==null){
                    ToastUtil.showMessage(CallTxtActivity.this,R.string.call_txt);
                    return;
                }
                param.setCallTxt(selectItem.getCallTxtInput());//设置打招呼语
                param.setCallType(selectItem.getCallType());//设置打招呼语
                if(param.getEntrance()== ServiceSuper.ACCOUNT){
                    getPresenter().checkSameService(param);
                }else{
                    UseServiceActivity.lunch(CallTxtActivity.this,param);
                }
            }
        });
//        login_enter = (Button) findViewById(R.id.login_enter);
//        login_enter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(TextUtils.isEmpty(remarks.getText().toString())){
//                    ToastUtil.showMessage(CallTxtActivity.this,R.string.call_txt);
//                    return;
//                }
//                param.setCallTxt(remarks.getText().toString());//设置打招呼语
//                if(param.getEntrance()== ServiceSuper.ACCOUNT){
//                    getPresenter().checkSameService(param);
//                }else{
//                    UseServiceActivity.lunch(CallTxtActivity.this,param);
//                }
//            }
//        });
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

}
