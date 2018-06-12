package com.kongrongqi.shopmall.utils.permission;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.kongrongqi.shopmall.modules.login.LoginActivity;
import com.kongrongqi.shopmall.utils.Logger;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * 创建日期：2017/6/6 0006 on 14:34
 * 作者:penny
 */
public class PermissionManager {

    private static final int REQUEST_SINGLE_CODE_PERMISSION_ACT = 100;
    public static final int REQUEST_MORE_CODE_PERMISSION_ACT = 101;
    private static final int REQUEST_SINGLE_CODE_PERMISSION_FRG = 102;
    private static final int REQUEST_MORE_CODE_PERMISSION_FRG = 103;

    private FragmentActivity sActivity;

    public static PermissionManager getInstance() {
        return PermissionManager.Singleton.INSTANCE;
    }

    private PermissionManager() {

    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_SINGLE_CODE_PERMISSION_ACT: {

                    break;
                }
                case REQUEST_MORE_CODE_PERMISSION_ACT: {

                    break;
                }
                case REQUEST_SINGLE_CODE_PERMISSION_FRG: {
                    break;
                }

                case REQUEST_MORE_CODE_PERMISSION_FRG: {

                    break;
                }
            }

        }


        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            int requestCodeSetting = 0;
            switch (requestCode) {
                case REQUEST_SINGLE_CODE_PERMISSION_ACT: {
                    requestCodeSetting = REQUEST_SINGLE_CODE_PERMISSION_ACT;
                    break;
                }
                case REQUEST_MORE_CODE_PERMISSION_ACT: {
                    requestCodeSetting = REQUEST_MORE_CODE_PERMISSION_ACT;
                    break;
                }
                case REQUEST_SINGLE_CODE_PERMISSION_FRG: {
                    requestCodeSetting = REQUEST_SINGLE_CODE_PERMISSION_FRG;
                    break;
                }

                case REQUEST_MORE_CODE_PERMISSION_FRG: {
                    requestCodeSetting = REQUEST_MORE_CODE_PERMISSION_FRG;

                    break;
                }
            }

            if (AndPermission.hasAlwaysDeniedPermission(sActivity, deniedPermissions)) {
                Logger.d("PermissionManager", "onFailed:===失败");
                AndPermission.defaultSettingDialog(sActivity, requestCodeSetting).show();
            }
        }
    };


    public boolean hasPermission(Context context, String... permission) {
        if (AndPermission.hasPermission(context, permission))
            return false;
        else
            return true;
    }

    /**
     * 单个权限
     *
     * @param activity
     * @param permission
     */
    public void inActivityPermission(AppCompatActivity activity, String permission) {
        sActivity = activity;
        AndPermission.with(activity)
                .requestCode(REQUEST_SINGLE_CODE_PERMISSION_ACT)
                .permission(permission)
                .callback(permissionListener)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        //弹框自定义
                        Logger.d("PermissionManager", "showRequestPermissionRationale===弹框回调");
                        AndPermission.rationaleDialog(activity, rationale)
                                .show();
                    }
                })
                .start();

    }


    /**
     * 多个权限
     *
     * @param activity
     * @param permission
     */
    public void inActivityPermission(LoginActivity activity, String... permission) {
        sActivity = activity;
        AndPermission.with(activity)
                .requestCode(REQUEST_MORE_CODE_PERMISSION_ACT)
                .permission(permission)
                .callback(permissionListener)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        //弹框自定义
                        Logger.d("PermissionManager", "showRequestPermissionRationale===弹框回调");
                        AndPermission.rationaleDialog(activity, rationale)
                                .show();
                    }
                })
                .start();

    }

    /**
     * fragmen 单个权限
     *
     * @param fragment
     * @param permission
     */
    public void inFragmentPermission(Fragment fragment, String permission) {
        sActivity = fragment.getActivity();
        AndPermission.with(fragment)
                .requestCode(REQUEST_SINGLE_CODE_PERMISSION_FRG)
                .permission(permission)
                .callback(permissionListener)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {

                    }
                })
                .start();

    }

    /**
     * 多个权限
     *
     * @param activity
     * @param permission
     */
    public void inFragmentPermission(Fragment fragment, String... permission) {
        sActivity = fragment.getActivity();
        AndPermission.with(fragment)
                .requestCode(REQUEST_MORE_CODE_PERMISSION_FRG)
                .permission(permission)
                .callback(permissionListener)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {

                    }
                })
                .start();

    }


    private static final class Singleton {
        private static final PermissionManager INSTANCE = new PermissionManager();
    }
}
