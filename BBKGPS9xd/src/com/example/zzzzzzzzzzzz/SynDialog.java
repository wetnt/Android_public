package com.example.zzzzzzzzzzzz;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class SynDialog extends Dialog {

	private Handler mHandler;
	protected Object result;

	public SynDialog(Context context) {
		super(context);
		onCreate();
	}

	public abstract void onCreate();

	public void finishDialog() {
		dismiss();
		mHandler.sendEmptyMessage(0);
	}

	static class SynHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			throw new RuntimeException();
		}
	}

	public Object showDialog() {
		super.show();
		try {
			Looper.getMainLooper();
			mHandler = new SynHandler();
			Looper.loop();
		} catch (Exception e) {
		}
		return result;
	}

}
