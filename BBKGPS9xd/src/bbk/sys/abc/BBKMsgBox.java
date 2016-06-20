package bbk.sys.abc;

import com.example.bbkgps9xd.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import bbk.bbk.box.BBKAbout;
import bbk.bbk.box.BBKSoft;
import bbk.uis.view.BBKLayView;

public class BBKMsgBox extends BBKLayView {

	public ProgressBar BarWait;

	// =====================================================================================
	public void MsgBoxInt(final Context context) {
		LayoutInt(context, R.layout.main_msgbox, 0, 0, 0, 0);
		BarWait = (ProgressBar) bbkAct.findViewById(R.id.BarWait);
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################AlertDialog###########################################
	// ####################################################################################
	// ====================================================================================
	private static Toast toast;

	public static void tShow(String str) {
		if (toast == null) {
			toast = Toast.makeText(BBKSoft.bbkContext, str, Toast.LENGTH_SHORT);
		} else {
			// toast.cancel();
			toast.setText(str);
		}
		toast.show();
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################MsgShow###############################################
	// ####################################################################################
	// ====================================================================================
	public static void MsgYesNo(String Ask, String Yes, String No, OnClickListener YesLs) {
		// ---------------------------------------------------------------------
		new AlertDialog.Builder(BBKSoft.bbkContext)// 注册
				.setTitle(BBKAbout.SoftTitle)// 标题
				.setMessage(Ask)// 内容
				.setPositiveButton(Yes, YesLs)// DialogInterface.BUTTON_POSITIVE://-1确定的
				.setNegativeButton(No, YesLs)// DialogInterface.BUTTON_NEGATIVE://-2否定的
				// .setNeutralButton(Em,YesLs)//DialogInterface.BUTTON_NEUTRAL://-3中性的
				.show();// 显示
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	public static void MsgOK(String Ask) {
		// ---------------------------------------------------------------------
		OnClickListener YesRun = new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		};
		// ---------------------------------------------------------------------
		new AlertDialog.Builder(BBKSoft.bbkContext)// 注册
				.setTitle(BBKAbout.SoftTitle)// 标题
				.setMessage(Ask)// 内容
				.setPositiveButton("OK", YesRun)// -1确定的
				.show();// 显示
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################AlertDialog###########################################
	// ####################################################################################
	// ====================================================================================

	// ====================================================================================
	// ####################################################################################
	// ##############################WaitShow##############################################
	// ####################################################################################
	// ====================================================================================
	public void WaitShow(boolean see) {
		WaitView = see;
		Wait_Handler.post(Wait_Runnable);
	}

	public void WaitSet(boolean key) {
		// ----------------------------------------------
		WaitView = key;
		WaitSet();
		// ----------------------------------------------
	}

	private void WaitSet() {
		// ----------------------------------------------
		if (WaitView) {
			BarWait.setVisibility(View.VISIBLE);
		} else {
			BarWait.setVisibility(View.GONE);
		}
		// ----------------------------------------------
	}

	private boolean WaitView = false;
	private Handler Wait_Handler = new Handler();
	private Runnable Wait_Runnable = new Runnable() {
		public void run() {
			WaitSet();
		}
	};
	// ====================================================================================
	// ####################################################################################
	// ##############################WaitShow##############################################
	// ####################################################################################
	// ====================================================================================

}