
package com.kongrongqi.shopmall.wedget;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;


public class ProgressDialog extends Dialog {

	public ProgressDialog(Context context) {

		super(context);
	}

	public ProgressDialog(Context context, int theme) {

		super(context, theme);
	}

	/**
	 * 设置背景开启动画
	 * @author luq
	 * @time 2016/6/29 13:48
	 */
	public void setImage() {
		ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
		// 获取ImageView上的动画背景
		AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
		// 开始动画
		spinner.start();
	}

	/**
	 * 给Dialog设置提示信息
	 * @author luq
	 * @time 2016/6/29 13:48
	 */
	public void setMessage(CharSequence message) {
		if (message != null && message.length() > 0) {
			findViewById(R.id.message).setVisibility(View.VISIBLE);
			TextView txt = (TextView) findViewById(R.id.message);
			txt.setText(message);
			txt.invalidate();
		}
	}

	/**
	 * 弹出dialog
	 * @param context
	 * @return
	 */
	public static ProgressDialog createDialog(Context context) {
		ProgressDialog dialog = new ProgressDialog(context, R.style.Custom_Progress);
		dialog.setTitle("");
		dialog.setContentView(R.layout.layout_progress);
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return dialog;
	}
}
