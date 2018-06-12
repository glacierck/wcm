package com.frame.commonframe.viewtype;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.commonframe.R;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class BuilderProgress {
    //Empty  空页面

    private Button emptyStateButton_refrash; //刷新button
    private TextView empty_text;//空文子提示
    private ImageView empty_img;//空图片
    private View.OnClickListener onEmptyButtonClickListener;//空 button 点击 时间

    private ProgressActivity progressActivity;

    public BuilderProgress(ProgressActivity progressActivity) {
        this.progressActivity = progressActivity;
    }

    //显示 空刷新button
    public BuilderProgress setEmptyButtonIsShow(){
        progressActivity.setEmptyButtonIsShow();
        return this;
    }
    //刷新button 文字
    public BuilderProgress setEmptyButtonText(String emptyButtonText ){
        progressActivity.setEmptyButtonText(emptyButtonText);
        return this;
    }
    //空点击
    public BuilderProgress setEmptyButtonClickListener(View.OnClickListener onEmptyButtonClickListener ){
        progressActivity.setEmptyButtonClickListener(onEmptyButtonClickListener);
        return this;
    }

    //空文子提示
    public BuilderProgress setEmptyText(String emptyText ){
        progressActivity.setEmptyText(emptyText);
        return this;
    }
    //空图片
    public BuilderProgress setEmptyImage(int rImg){
        progressActivity.setEmptyImage(rImg);
        return this;
    }

    //错误 button 点击事件
    public BuilderProgress setErrorButtonClickListener(View.OnClickListener onErrorButtonClickListener ){
        progressActivity.setErrorButtonClickListener(onErrorButtonClickListener);
        return this;
    }

    public ProgressActivity create(){
        return progressActivity;
    }


}
