package bbk.sys.abc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class SynSelectDialog {

	int dialogResult;
	public static Handler mHandler;
	public Context context;

	public static boolean showComfirmDialogBool(Activity context, String title, String msg) {
		mHandler = new MyHandler();
		return new SynSelectDialog(context, title, msg).getResult() == 1;
	}

	public static boolean showComfirmDialogBool(Activity context, String title, String[] items) {
		mHandler = new MyHandler();
		return new SynSelectDialog(context, title, items).getResult() == 1;
	}

	public static int showComfirmDialogSelect(Activity context, String title, String msg) {
		mHandler = new MyHandler();
		return new SynSelectDialog(context, title, msg).getResult();
	}

	public static int showComfirmDialogSelect(Activity context, String title, String[] items) {
		mHandler = new MyHandler();
		return new SynSelectDialog(context, title, items).getResult();
	}

	// ===========================================================================================
	// ###########################################################################################
	// ===========================================================================================
	public SynSelectDialog(Activity context, String title, String msg) {
		// ----------------------------------------------------------------------------------------
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		// ----------------------------------------------------------------------------------------
		// dialogBuilder.setPositiveButton("确定", new DialogButtonOnClick(1));
		// dialogBuilder.setNegativeButton("取消", new DialogButtonOnClick(0));
		// ----------------------------------------------------------------------------------------
		dialogBuilder.setMessage(msg);// 不能与setSingleChoiceItems同时使用
		// ----------------------------------------------------------------------------------------
		dialogBuilder.setTitle(title).create().show();// 显示Dialog;
		// ----------------------------------------------------------------------------------------
		// 系统线程会阻塞
		try {
			Looper.loop();
		} catch (Exception e) {
		}
		// ----------------------------------------------------------------------------------------
	}

	public SynSelectDialog(Activity context, String title, String[] items) {
		// ----------------------------------------------------------------------------------------
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		// ----------------------------------------------------------------------------------------
		dialogBuilder.setSingleChoiceItems(//
				items, //
				0, //
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialogResult = which;
						Message m = mHandler.obtainMessage();
						mHandler.sendMessage(m);
						dialog.dismiss();
					}
				}//
		);
		// ----------------------------------------------------------------------------------------
		dialogBuilder.setTitle(title).create().show();// 显示Dialog;
		// ----------------------------------------------------------------------------------------
		// 系统线程会阻塞
		try {
			Looper.loop();
		} catch (Exception e) {
		}
		// ----------------------------------------------------------------------------------------
	}

	public int getResult() {
		return dialogResult;
	}

	private static class MyHandler extends Handler {
		public void handleMessage(Message mesg) {
			throw new RuntimeException();
		}
	}

	@SuppressWarnings("unused")
	private final class DialogButtonOnClick implements OnClickListener {
		int type;

		public DialogButtonOnClick(int type) {
			this.type = type;
		}

		/**
		 * 用户点击确定或取消按钮后,设置点击按钮,发送消息; mHandler收到消息后抛出RuntimeException()异常;
		 * 阻塞会消失,主线程继续执行
		 */
		public void onClick(DialogInterface dialog, int which) {
			SynSelectDialog.this.dialogResult = type;
			Message m = mHandler.obtainMessage();
			mHandler.sendMessage(m);
		}
	}

}

// boolean isOk =
// SynSelectDialog.showComfirmDialogBool(MainActivity.this,"提示","你好吗?");
// if(isOk){...}