package com.kongrongqi.shopmall.wedget.guide;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;

/**
 * Created on 2017/7/12 0012.
 * by penny  Guide管理
 */

public class GuiBuilder {

    private static Activity sActivity;
    private boolean sIsFirst;

    private long mMinute = 0;
    private boolean mAccountIsShow;
    public static final int ACCOUNT = 0;
    public static final int DETAIL = 1;
    public static final int NOT_USE = 2;
    public static final int ME_VIEW = 3;
    public static final int SERVICE = 4;
    private boolean mAccountNavigation;
    private boolean mAccountImgList;
    private boolean mAccountListItem;
    private boolean mAccountRight;
    private boolean mDetail;
    private boolean mService;
    private boolean mNotUse;
    private boolean mMe;

    private GuiBuilder() {
    }

    private static final class Singleton {
        private static final GuiBuilder INSTANCE = new GuiBuilder();
    }

    public static GuiBuilder builder(Activity pActivity) {
        sActivity = pActivity;
        return Singleton.INSTANCE;
    }

    /**
     * 在列表中显示guide View
     *
     * @param pXrecyclerView
     * @param fillwidth      宽度是否充满
     * @param positon        高亮的索引值
     * @param res            图片
     * @param res_2
     * @param type           界面类型
     * @param style          高亮 style
     * @param pListener
     */
    public void showGuiRecyclerView(XRecyclerView pXrecyclerView,
                                    boolean fillwidth,
                                    int positon,
                                    int res,
                                    int res_2,
                                    int type,
                                    int style,
                                    OnDismissListener pListener) {
        if (type == ACCOUNT) {
            if (getAccountRight()) {
                showGuiRecyclerView(pXrecyclerView, fillwidth, positon, res, res_2, style, pListener);
            }
        } else if (type == SERVICE) {
            if (getServiceIsShow()) {
                showGuiRecyclerView(pXrecyclerView, fillwidth, positon, res, res_2, style, pListener);
            }
        } else if (type == NOT_USE) {
            if (getNotUseShow()) {
                showGuiRecyclerView(pXrecyclerView, fillwidth, positon, res, res_2, style, pListener);
            }
        }
    }

    private void showGuiRecyclerView(final XRecyclerView pXrecyclerView,
                                     final boolean fillwidth,
                                     int positon,
                                     final int res,
                                     final int res_2,
                                     final int style,
                                     OnDismissListener pListener) {
        ViewTreeObserver lTreeObserver = pXrecyclerView.getViewTreeObserver();
        lTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int lCount = pXrecyclerView.getAdapter().getItemCount();
                Logger.d("GuiBuilder","recyclerView"+lCount);
                if(lCount == 0){
                    return;
                }
                View lChildAt = pXrecyclerView.getChildAt(positon);
                App.getInstance().getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (lChildAt != null) {
                            GuideView.builder(sActivity)
                                    .setFillWidth(fillwidth)
                                    .addHighLightGuidView(lChildAt, true, res, res_2)
                                    .setHighLightStyle(style)
                                    .setOnDismissListener(new GuideView.OnDismissListener() {
                                        @Override
                                        public void onDismiss() {
                                            if (pListener != null) {
                                                pListener.onDismiss();
                                            }
                                        }
                                    })
                                    .show();
                        }
                    }
                }, mMinute);
                if (lChildAt != null) {
                    pXrecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    public void showGuiRecyclerViewChild(XRecyclerView pXrecyclerView,
                                         boolean fillwidth,
                                         int res,
                                         int res_2,
                                         int style,
                                         int type,
                                         OnDismissListener pListener) {
        if (type == DETAIL) {
            if (getDetailIsShow()) {
                ViewTreeObserver lTreeObserver = pXrecyclerView.getViewTreeObserver();
                lTreeObserver.addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                int lChildCount = pXrecyclerView.getLayoutManager().getItemCount();
                                View lViewByPosition = pXrecyclerView.getLayoutManager().findViewByPosition(lChildCount);
                                View lView = lViewByPosition.findViewById(R.id.time_status);
                                App.getInstance()
                                        .getHandler()
                                        .postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (lView != null) {
                                                    GuideView.builder(sActivity)
                                                            .addHighLightGuidView(lView, true, res, res_2)
                                                            .setTouchOutsideDismiss(true)
                                                            .setFillWidth(fillwidth)
                                                            .setHighLisghtPadding(60)
                                                            .setHighLightStyle(style)
                                                            .setOnDismissListener(new GuideView.OnDismissListener() {
                                                                @Override
                                                                public void onDismiss() {
                                                                    if (pListener != null) {
                                                                        pListener.onDismiss();
                                                                    }
                                                                }
                                                            })
                                                            .show();
                                                }
                                            }
                                        }, mMinute);
                                if (lView != null) {
                                    pXrecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                }
                            }
                        });
            }
        }
    }

    /**
     * 连续显示GUIDE
     *
     * @param pView
     * @param fillwidth
     * @param res
     * @param res2
     * @param style
     * @param type
     * @param pListener
     */
    public void showGuiComboView(View pView,
                                 boolean fillwidth,
                                 int res,
                                 int res2,
                                 int style,
                                 int type,
                                 OnDismissListener pListener) {
        if (type == ACCOUNT) {
            boolean lAccountIsShow = getAccountRight();
            if (lAccountIsShow) {
                showGuiComboView(pView, fillwidth, res, res2, style, pListener);
            }
        }
    }

    private void showGuiComboView(View pView, boolean fillwidth, int res, int res2, int style, final OnDismissListener pListener) {
        GuideView.builder(sActivity)
                .addHighLightGuidView(
                        pView,
                        fillwidth,
                        res,
                        res2)
                .setHighLightStyle(style)
                .setOnDismissListener(new GuideView.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (pListener != null) {
                            pListener.onDismiss();
                        }
                    }
                })
                .show();
    }

    /**
     * 普通显示
     *
     * @param v
     * @param fillwidth
     * @param res
     * @param res2
     * @param style
     * @param type
     * @param pListener
     */
    public void showGuiView(View v, boolean fillwidth, int res, int res2, int style, int type, final OnDismissListener pListener) {
        if (type == DETAIL) {
            if (getDetailIsShow()) {
                showGuiView(v, fillwidth, res, res2, style, pListener);
            }
        } else if (type == ME_VIEW) {
            if (getMeIsShow()) {
                showGuiView(v, fillwidth, res, res2, style, pListener);
            }
        }
    }

    private void showGuiView(View v, boolean fillwidth, int res, int res2, int style, final OnDismissListener pListener) {
        GuideView.builder(sActivity)
                .addHighLightGuidView(v, fillwidth, res, res2)
                .setFillWidth(true)
                .setHighLightStyle(style)
                .setOnDismissListener(new GuideView.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (pListener != null) {
                            pListener.onDismiss();
                        }
                    }
                })
                .show();
    }

    /**
     * 设置时间
     *
     * @param pMinute
     * @return
     */
    public GuiBuilder setMinute(long pMinute) {
        mMinute = pMinute;
        return this;
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    /**
     * 存储初始化值
     */
    public void isFirstLogin() {
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_DETAIL, sIsFirst);
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_ACCOUNT_RIGHT, sIsFirst);
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_ACCOUNT_NAVIGATION, sIsFirst);
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_ACCOUNT_LIST, sIsFirst);
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_ACCOUNT_LIST_ITEM, sIsFirst);
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_ME, sIsFirst);
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_NOT_USE, sIsFirst);
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_SERVICE, sIsFirst);
    }

    public boolean isAccountNavigation() {
        mAccountNavigation = SPUtils.getBoolean(App.getInstance().getContext(), Constans.GUIDE_ACCOUNT_NAVIGATION, false);
        return mAccountNavigation;
    }

    public void setAccountNavigation(boolean pAccountNavigation) {
        mAccountNavigation = pAccountNavigation;
    }

    public boolean isAccountImgList() {
        mAccountImgList = SPUtils.getBoolean(App.getInstance().getContext(), Constans.GUIDE_ACCOUNT_LIST, false);
        return mAccountImgList;
    }

    public void setAccountImgList(boolean pAccountImgList) {
        mAccountImgList = pAccountImgList;
    }

    public boolean isAccountListItem() {
        mAccountListItem = SPUtils.getBoolean(App.getInstance().getContext(), Constans.GUIDE_ACCOUNT_LIST_ITEM, false);
        return mAccountListItem;
    }

    public void setAccountListItem(boolean pAccountListItem) {
        mAccountListItem = pAccountListItem;
    }

    public void setFirstRegister(boolean isFirst) {
        sIsFirst = isFirst;
        isFirstLogin();
    }

    public boolean getAccountRight() {
        mAccountRight = SPUtils.getBoolean(App.getInstance().getContext(), Constans.GUIDE_ACCOUNT_RIGHT, false);
        return mAccountRight;
    }

    public boolean getDetailIsShow() {
        mDetail = SPUtils.getBoolean(App.getInstance().getContext(), Constans.GUIDE_DETAIL, false);
        return mDetail;
    }

    public boolean getServiceIsShow() {
        mService = SPUtils.getBoolean(App.getInstance().getContext(), Constans.GUIDE_SERVICE, false);
        return mService;
    }

    public boolean getNotUseShow() {
        mNotUse = SPUtils.getBoolean(App.getInstance().getContext(), Constans.GUIDE_NOT_USE, false);
        return mNotUse;
    }

    public boolean getMeIsShow() {
        mMe = SPUtils.getBoolean(App.getInstance().getContext(), Constans.GUIDE_ME, false);
        return mMe;
    }

    public void setAccountRight(boolean pAccountRight) {
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_ACCOUNT_RIGHT, pAccountRight);
        mAccountRight = pAccountRight;
    }

    public void setDetail(boolean pDetail) {
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_DETAIL, pDetail);
        mDetail = pDetail;
    }

    public void setService(boolean pService) {
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_SERVICE, pService);
        mService = pService;
    }

    public void setNotUse(boolean pNotUse) {
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_NOT_USE, pNotUse);
        mNotUse = pNotUse;
    }

    public void setMe(boolean pMe) {
        SPUtils.putBoolean(App.getInstance().getContext(), Constans.GUIDE_ME, pMe);
        mMe = pMe;
    }
}
