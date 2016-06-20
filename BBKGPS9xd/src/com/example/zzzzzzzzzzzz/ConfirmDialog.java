package com.example.zzzzzzzzzzzz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ConfirmDialog {
	int dialogResult;
	public static Handler mHandler;
	public Context context;

	public static boolean showComfirmDialog(Activity context, String title, String msg) {
		mHandler = new MyHandler();
		return new ConfirmDialog(context, title, msg).getResult() == 1;
	}

	public ConfirmDialog(Activity context, String title, String msg) {
		// ��ʾDialog;
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setPositiveButton("ȷ��", new DialogButtonOnClick(1));
		dialogBuilder.setNegativeButton("ȡ��", new DialogButtonOnClick(0));
		dialogBuilder.setTitle(title).setMessage(msg).create().show();
		// ϵͳ�̻߳�����
		try {
			Looper.loop();
		} catch (Exception e) {
		}
	}

	public int getResult() {
		return dialogResult;
	}

	private static class MyHandler extends Handler {
		public void handleMessage(Message mesg) {
			throw new RuntimeException();
		}
	}

	private final class DialogButtonOnClick implements OnClickListener {
		int type;

		public DialogButtonOnClick(int type) {
			this.type = type;
		}

		/**
		 * �û����ȷ����ȡ����ť��,���õ����ť,������Ϣ; mHandler�յ���Ϣ���׳�RuntimeException()�쳣;
		 * ��������ʧ,���̼߳���ִ��
		 */
		public void onClick(DialogInterface dialog, int which) {
			ConfirmDialog.this.dialogResult = type;
			Message m = mHandler.obtainMessage();
			mHandler.sendMessage(m);
		}
	}

}
