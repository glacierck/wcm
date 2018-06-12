package com.kongrongqi.shopmall.crashMonitor.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.utils.StatusBarUtil;


public class CrashBaseActivity extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    public void initToolBar(Toolbar toolbar, String title, int leftIcon) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(leftIcon);
        setSupportActionBar(toolbar);
    }

    public void initToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

}
