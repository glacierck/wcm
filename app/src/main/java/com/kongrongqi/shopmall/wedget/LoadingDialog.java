package com.kongrongqi.shopmall.wedget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class LoadingDialog {

    private static ProgressDialog dialog;
    private static Context context;
    private static SimpleHUDCallback callback;

    public static int dismissDelay = LoadingDialog.DISMISS_DELAY_SHORT;
    public static final int DISMISS_DELAY_SHORT = 2000;
    public static final int DISMISS_DELAY_MIDIUM = 4000;
    public static final int DISMISS_DELAY_LONG = 6000;

    /**
     * 显示加载框
     *
     * @time 2016/6/29 13:57
     */
    public static void showLoadingMessage(Context context, String msg,
                                          boolean cancelable, SimpleHUDCallback callback) {
        LoadingDialog.callback = callback;
        showLoadingMessage(context, msg, cancelable);
    }


    public static void showLoadingMessage(Context context, String msg,
                                          boolean cancelable) {
        dismiss(); // 进度图片
        setDialog(context, msg, cancelable);
        show();
    }

    /**
     * 显示错误信息
     *
     * @time 2016/6/29 14:00
     */

    public static void showErrorMessage(Context context, String msg,
                                        SimpleHUDCallback callback) {
        LoadingDialog.callback = callback;
        showErrorMessage(context, msg);
    }

    public static void showErrorMessage(Context context, String msg) {
        dismiss();
        setDialog(context, msg, true);
        if (dialog != null) {
            // dialog.show();
            show();
            dismissAfterSeconds();
        }
    }

    /**
     * 显示成功信息
     *
     * @time 2016/6/29 14:00
     */
    public static void showSuccessMessage(Context context, String msg,
                                          SimpleHUDCallback callback) {
        LoadingDialog.callback = callback;
        showSuccessMessage(context, msg);
    }

    public static void showSuccessMessage(Context context, String msg) {
        dismiss();
        setDialog(context, msg, true);
        if (dialog != null) {
            // dialog.show();
            show();
            dismissAfterSeconds();
        }
    }

    public static void showInfoMessage(Context context, String msg,
                                       SimpleHUDCallback callback) {
        LoadingDialog.callback = callback;
        showInfoMessage(context, msg);
    }

    public static void showInfoMessage(Context context, String msg) {
        dismiss();
        setDialog(context, msg, true);
        if (dialog != null) {
            // dialog.show();
            show();
            dismissAfterSeconds();
        }
    }


    private static void setDialog(Context ctx, String msg,
                                  boolean cancelable) {
        context = ctx;
        if (!isContextValid())
            return;
        // 返回dialog对象
        dialog = ProgressDialog.createDialog(ctx);
        dialog.setMessage(msg);
        dialog.setImage();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(cancelable); // back键是否可dimiss对话框
    }

    public static void show() {
        if (dialog != null) {
            try {
                dialog.show();
            } catch (Exception e) {
                dialog = null;
                e.printStackTrace();
            }
        }
    }

    public static void dismiss() {
        if (isContextValid() && dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dialog = null;
            }
        }
    }

    /**
     * 计时关闭对话框
     */
    private static void dismissAfterSeconds() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(dismissDelay);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                dismiss();
                if (LoadingDialog.callback != null) {
                    callback.onSimpleHUDDismissed();
                    callback = null;
                }
            }
        }
    };

    /**
     * 判断parent view是否还存在 若不存在不能调用dismis，或setDialog等方法
     *
     * @return
     */
    private static boolean isContextValid() {
        if (context == null)
            return false;
        if (context instanceof Activity) {
            Activity act = (Activity) context;
            if (act.isFinishing())
                return false;
        }
        return true;
    }

    public interface SimpleHUDCallback {
        void onSimpleHUDDismissed();
    }
}
