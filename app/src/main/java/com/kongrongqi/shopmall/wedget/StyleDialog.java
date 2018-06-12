package com.kongrongqi.shopmall.wedget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.modules.me.bean.JsonBean;
import com.kongrongqi.shopmall.modules.task.holder.IdHolder;
import com.kongrongqi.shopmall.utils.DensityUtil;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.StringUtils;

import java.util.ArrayList;


/**
 * Created by penny on 2016/9/22 0022.
 */
public class StyleDialog {

    public static void showSelectOprator(Context context,DialogEnterListener listener, DialogEnterListener listener2) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_select_oprator_dialog, null);
        Button ok = (Button) rootView.findViewById(R.id.again_confirm);//重新审核
        Button cancel = (Button) rootView.findViewById(R.id.add);//重新添加
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener2) {
                    listener2.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }


    /**
     * 重启服务
     * @param context
     * @param title
     * @param content
     * @param listener
     */
    public static void showRestartService(Context context, String title, String content, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_enter_restart_service_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button enter = (Button) rootView.findViewById(R.id.dialog_confirm);

        Button dialog_cancel = (Button) rootView.findViewById(R.id.dialog_cancel);

        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tv_msg = (TextView) rootView.findViewById(R.id.tv_msg);
        tv_title.setText(title);
        tv_msg.setText(content);

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }



    /**
     * 我要换号   空闲中-我要换号
     * @param context
     * @param title
     * @param content
     * @param listener
     */
    public static void showMeChangeDialogEnter2(Context context, DialogEnterListener listener,DialogEnterListener listener2) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_me_change_dialog2, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button dialog_confirm = (Button) rootView.findViewById(R.id.dialog_confirm);
        Button dialog_cancel = (Button) rootView.findViewById(R.id.dialog_cancel);

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener2) {
                    listener2.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }


    /**
     * 我要换号   服务中-我要换号
     * @param context
     * @param title
     * @param content
     * @param listener
     */
    public static void showMeChangeDialogEnter(Context context, DialogEnterListener listener,DialogEnterListener listener2) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_me_change_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button dialog_confirm = (Button) rootView.findViewById(R.id.dialog_confirm);
        Button dialog_cancel = (Button) rootView.findViewById(R.id.dialog_cancel);

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener2) {
                    listener2.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }

    /**
     * 详情-空闲中-我要使用
     * @param context
     * @param title
     * @param content
     * @param listener
     */
    public static void showServiceDialogEnter2(Context context, String title, String content, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_enter_service_datails_dialog2, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        Button enter = (Button) rootView.findViewById(R.id.dialog_confirm);
        Button dialog_cancel = (Button) rootView.findViewById(R.id.dialog_cancel);

        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tv_msg = (TextView) rootView.findViewById(R.id.tv_msg);
        tv_title.setText(title);
        tv_msg.setText(content);

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }


    /**
     * 详情-服务中-我要使用
     * @param context
     * @param title
     * @param content
     * @param listener
     */
    public static void showServiceDialogEnter(Context context, String title, String content, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_enter_service_datails_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button enter = (Button) rootView.findViewById(R.id.dialog_confirm);
        Button dialog_cancel = (Button) rootView.findViewById(R.id.dialog_cancel);

        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tv_msg = (TextView) rootView.findViewById(R.id.tv_msg);
        tv_title.setText(title);
        tv_msg.setText(content);

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }

    public static void showAccountLoginException4(Context context,boolean isChangeNumber,boolean isRecovery,DialogEnterListener listener, DialogEnterListener listener2,DialogEnterListener listener3) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_account_login_exception_4_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);


        Button again_confirm = (Button) rootView.findViewById(R.id.again_confirm);//重新审核
        Button add = (Button) rootView.findViewById(R.id.add);//重新添加
        Button recovery = (Button) rootView.findViewById(R.id.recovery);//恢复原号

        again_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        add.setVisibility(isChangeNumber?View.VISIBLE:View.GONE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener2.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        recovery.setVisibility(isRecovery?View.VISIBLE:View.GONE);

        recovery.setOnClickListener(new View.OnClickListener() {//恢复原号
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener3.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        window.setContentView(rootView);
    }



    /**
     *  审核登录失败 重新添加账号
     * @param context
     * @param listener
     */
    public static void showAccountLoginException5(Context context,boolean isChangeNumber,boolean isRecovery,DialogEnterListener listener, DialogEnterListener listener2,DialogEnterListener listener3) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_enter_account_shenhe_fail_5_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button again_confirm = (Button) rootView.findViewById(R.id.again_confirm);//重新审核
        Button add = (Button) rootView.findViewById(R.id.add);//重新添加
        Button recovery = (Button) rootView.findViewById(R.id.recovery);//恢复原号

        again_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        add.setVisibility(isChangeNumber?View.VISIBLE:View.GONE);
        add.setOnClickListener(new View.OnClickListener() {//重新添加
            @Override
            public void onClick(View v) {
                if (null != listener2) {
                    listener2.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        recovery.setVisibility(isRecovery?View.VISIBLE:View.GONE);

        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener3) {
                    listener3.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }


    /**
     *  6
     * @param context
     * @param listener
     * @param listener2
     * @param listener3
     */
    public static void showAuditFailure(Context context,boolean isChangeNumber,boolean isRecovery,DialogEnterListener listener, DialogEnterListener listener2,DialogEnterListener listener3,DialogEnterListener listener4) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_auditfailure_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);


        Button again_confirm = (Button) rootView.findViewById(R.id.again_confirm);//重新审核
        Button add = (Button) rootView.findViewById(R.id.add);//重新添加
        Button execution = (Button) rootView.findViewById(R.id.execution);//强制执行
        Button recovery = (Button) rootView.findViewById(R.id.recovery);//恢复原号


        again_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        add.setVisibility(isChangeNumber?View.VISIBLE:View.GONE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener2) {
                    listener2.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        execution.setOnClickListener(new View.OnClickListener() {////强制执行
            @Override
            public void onClick(View v) {
                if (null != listener3) {
                    listener3.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        recovery.setVisibility(isRecovery?View.VISIBLE:View.GONE);

        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener4) {
                    listener4.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        window.setContentView(rootView);
    }



    /**
     * 账号异常  7
     * @param context
     * @param listener
     */
    public static void showAccountException(Context context, String dialog_cancel,  String confirmText,DialogUserServiceListener listener, DialogEnterListener listener2) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_account_shenhe_fail_7_dialog, null);
        Button ok = (Button) rootView.findViewById(R.id.dialog_confirm);
        Button cancel = (Button) rootView.findViewById(R.id.dialog_cancel);
        if (!TextUtils.isEmpty(dialog_cancel)) {
            cancel.setText(dialog_cancel);
        }
        if (!TextUtils.isEmpty(confirmText)) {
            ok.setText(confirmText);
        }
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onUser();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener2) {
                    listener2.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }





    /**
     * 账号异常
     * @param context
     * @param listener
     */
    public static void showAccountException(Context context, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_enter_account_exception_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button enter = (Button) rootView.findViewById(R.id.dialog_login_enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }












    public static void showListView(Context context, String[] data) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_enter_account_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        XRecyclerView working_recylerView = (XRecyclerView) rootView.findViewById(R.id.working_recylerView);
        working_recylerView.setPullRefreshEnabled(false);
        working_recylerView.setLoadingMoreEnabled(false);
        working_recylerView.setLayoutManager(new LinearLayoutManager(context));
        working_recylerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_id, parent, false);
                return new IdHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                String s = data[position];
                IdHolder mHolder = (IdHolder) holder;
                mHolder.id.setText("ID：" + s);
            }

            @Override
            public int getItemCount() {
                return data.length;
            }
        });
        window.setContentView(rootView);
    }


    public static void showEnter(Context context, String title, String content, String okText, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_enter_replace_imei_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button enter = (Button) rootView.findViewById(R.id.dialog_login_enter);
        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tv_msg = (TextView) rootView.findViewById(R.id.tv_msg);
        tv_title.setText(title);
        tv_msg.setText(content);
        if (!TextUtils.isEmpty(okText)) {
            enter.setText(okText);
        }
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }


    public static void showEnter(Context context, String title, String content, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_enter_replace_imei_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button enter = (Button) rootView.findViewById(R.id.dialog_login_enter);
        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tv_msg = (TextView) rootView.findViewById(R.id.tv_msg);
        tv_title.setText(title);
        tv_msg.setText(content);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }


    public static void showUserService(Context context, String title, String content, String confirmText, String dialog_cancel, DialogUserServiceListener listener, DialogEnterListener listener2) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_buy_dialog, null);

        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tv_msg = (TextView) rootView.findViewById(R.id.tv_msg);
        Button ok = (Button) rootView.findViewById(R.id.dialog_confirm);
        Button cancel = (Button) rootView.findViewById(R.id.dialog_cancel);
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
//        if (!TextUtils.isEmpty(content)) {
        tv_msg.setText(content);
//        }
        if (!TextUtils.isEmpty(confirmText)) {
            ok.setText(confirmText);
        }
        if (!TextUtils.isEmpty(dialog_cancel)) {
            cancel.setText(dialog_cancel);
        }

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onUser();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener2) {
                    listener2.onEnter();
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        window.setContentView(rootView);
    }


    public static void showUserService(Context context, String title, String content, DialogUserServiceListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_buy_dialog, null);

        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tv_msg = (TextView) rootView.findViewById(R.id.tv_msg);
        TextView dialog_confirm = (TextView) rootView.findViewById(R.id.dialog_confirm);
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            tv_msg.setText(content);
        }


        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button ok = (Button) rootView.findViewById(R.id.dialog_confirm);
        Button cancel = (Button) rootView.findViewById(R.id.dialog_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onUser();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });


        window.setContentView(rootView);
    }


    public static void showEnterReplaceImei(Context context, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_enter_replace_imei_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
//        window.setLayout(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
        Button enter = (Button) rootView.findViewById(R.id.dialog_login_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        window.setContentView(rootView);
    }


    public static void showEnterAPP(Context context, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_enter_app_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 360);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button enter = (Button) rootView.findViewById(R.id.dialog_login_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        window.setContentView(rootView);
    }

    public static void showBuyService(Context context, int position, String serviceName, DialogClickListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_buy_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button ok = (Button) rootView.findViewById(R.id.dialog_confirm);
        Button cancel = (Button) rootView.findViewById(R.id.dialog_cancel);
        TextView msg = (TextView) rootView.findViewById(R.id.tv_msg);
        String str = StringUtils.jointStr("您即将购买", serviceName, "，是否确定?");
        msg.setText(str);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onBuy(position);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });


        window.setContentView(rootView);
    }


    public static void showCancelEditService(Context context, int position, DialogClickListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_cancel_edit_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
//        window.setLayout(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
        Button ok = (Button) rootView.findViewById(R.id.dialog_confirm);
        Button cancel = (Button) rootView.findViewById(R.id.dialog_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onBuy(position);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();

                }
            }
        });


        window.setContentView(rootView);
    }

    public static void showHintDialog(Context context, String contentText, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_hint_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        Button ok = (Button) rootView.findViewById(R.id.dialog_confirm);
        Button cancel = (Button) rootView.findViewById(R.id.dialog_cancel);
        TextView content = (TextView) rootView.findViewById(R.id.tv_msg);
        content.setText(contentText);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        Logger.d("styledialog", "=====dismiss=====");
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();

                }
            }
        });
        dialog.show();
        window.setContentView(rootView);
    }


    public static void showPikerView(Context context,
                                     ArrayList<JsonBean> jsonList,
                                     ArrayList<ArrayList<String>> list1,
                                     ArrayList<ArrayList<ArrayList<String>>> list2,
                                     OptionsPickerView.OnOptionsSelectListener listener) {

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, listener)
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        pvOptions.setPicker(jsonList, list1, list2);//三级选择器
        pvOptions.show();
    }



    public static void showPikerView(Context context,
                                     ArrayList<JsonBean> jsonList,
                                     ArrayList<ArrayList<String>> list1,
                                     OptionsPickerView.OnOptionsSelectListener listener) {

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, listener)
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(jsonList, list1);//二级选择器
        pvOptions.show();
    }




    public static void showAppUpdateDialog(Context context, String d_title, String c, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_update_app_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        TextView title = (TextView) rootView.findViewById(R.id.dialog_title);
        TextView content = (TextView) rootView.findViewById(R.id.dialog_content);
        Button update = (Button) rootView.findViewById(R.id.dialog_update);
        TextView wait_update = (TextView) rootView.findViewById(R.id.dialog_wait_update);
        title.setText(d_title);
        content.setText(c);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        wait_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        window.setContentView(rootView);
    }

    public static void showTokenDialog(Context context, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_token_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
//        window.setLayout(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
        Button enter = (Button) rootView.findViewById(R.id.dialog_login_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }

    public static void showInvoiceExplain(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_explain_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 280);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        Button comfirm = (Button) rootView.findViewById(R.id.dialog_login_enter);
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        window.setContentView(rootView);
    }


    public static void showWxLongSMSDialog(Context context, DialogEnterListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.layout_token_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtil.dip2px(context, 300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
//        window.setLayout(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
        Button enter = (Button) rootView.findViewById(R.id.dialog_login_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onEnter();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        window.setContentView(rootView);
    }



    public interface DialogEnterListener {
        void onEnter();
    }

    public interface DialogClickListener {
        void onBuy(int position);
    }

    public interface DialogUserServiceListener {
        void onUser();
    }

}
