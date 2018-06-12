package com.kongrongqi.shopmall.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {


	private static Handler handler = new Handler(Looper.getMainLooper());

	private static Toast toast = null;

	private static Object synObj = new Object();

	public static void showMessage(final Context act, final String msg) {

		showMessage(
				act,
				msg,
				Toast.LENGTH_SHORT
				   );
	}

	public static void showMessageCenter(final Context act, final String msg) {

		toast = Toast.makeText(
				act,
				msg,
				Toast.LENGTH_SHORT
							  );
		toast.setGravity(
				Gravity.CENTER,
				0,
				0
						);
		toast.show();
	}


	public static void showMessage(final Context act, final int msg) {

		showMessage(
				act,
				msg,
				Toast.LENGTH_SHORT
				   );
	}

	public static void showMessage(final Context act, final String msg, final int len) {

		new Thread(new Runnable() {

			public void run() {

				handler.post(new Runnable() {

					@Override
					public void run() {

						synchronized (synObj) {

							if (toast != null) {
								toast.cancel();
							}
							if (act == null) {
								return;
							}
							toast = Toast.makeText(
									act,
									msg,
									len
												  );
							toast.show();
						}
					}
				});
			}
		}).start();
	}


	public static void showMessage(final Context act, final int msg, final int len) {

		new Thread(() -> {

			handler.post(() -> {

				synchronized (synObj) {
					if (toast != null) {
						toast.cancel();
					}
					toast = Toast.makeText(
							act,
							msg,
							len
										  );
					toast.show();
				}
			});

		}).start();
	}
}
