package com.kongrongqi.shopmall.initialize;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.kongrongqi.shopmall.utils.UmengUtils;
import com.kongrongqi.shopmall.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created on 2017/7/13 0013.
 * by penny
 */

public class InitializeService extends IntentService {
    public static final String ACTION_INIT = "action_init";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InitializeService(String name) {
        super(name);
    }

    public static void start(Context pContext) {
        Intent lIntent = new Intent(pContext, InitializeService.class);
        lIntent.setAction(ACTION_INIT);
        pContext.startService(lIntent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String lAction = intent.getAction();
            if (lAction.equals(ACTION_INIT)) {
                performInit();
            }
        }
    }

    private void performInit() {
        UmengUtils.initUmeng(this.getApplicationContext());
        initAutoLayout(this.getApplicationContext());
    }

    private void initAutoLayout(Context context) {
        AutoLayoutConifg.getInstance()
                .useDeviceSize()
                .init(context);
    }
}
