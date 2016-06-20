package bbk.map.set;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bbkgps9xd.R;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import bbk.bbk.box.BBKSoft;
import bbk.map.bbd.BBDHttp;
import bbk.net.abc.BBKHttpGet;
import bbk.sys.abc.BBKSYS;
import bbk.uis.view.BBKLayView;
import bbk.zzz.debug.bd;

public class BBKGpsNetSet extends BBKLayView {
	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void LayInt(final Context ctxt) {
		// ------------------------------------------------------------------------------------------
		LayoutInt(ctxt, R.layout.main_set_gps, 0, 0, 0, 0);
		// ------------------------------------------------------------------------------------------
		ListSet();
		layshow(false);
		// ------------------------------------------------------------------------------------------
	}

	// ==================================================================================
	private String setpathname = BBKSoft.PathSets + "BBKRichfitA.txt";
	// ------------------------------------------------------
	private Button SoftSetTit;
	private ImageButton SoftSetExt;
	// ------------------------------------------------------
	private EditText SoftSetUser, SoftSetPass, SoftSetPhone;
	private EditText SoftSetHost, SoftSetPath;
	// ------------------------------------------------------
	private EditText SoftSetProxy, SoftSetPort, SoftSetTime;
	// ------------------------------------------------------
	private Button GpsUpdHand;
	private ToggleButton GpsUpdOpen;
	public EditText GpsUpdBack, GpsUpdTime;
	// ------------------------------------------------------
	private ToggleButton GpsGetOpen;
	private Button GpsGetHand;
	@SuppressWarnings("unused")
	private EditText GpsGetIDid, GpsGetLens, GpsGetBack, GpsGetTime;

	// ------------------------------------------------------
	public void ListSet() {
		// ==================================================================================
		SoftSetTit = (Button) BBKLay.findViewById(R.id.SoftSetTit);
		SoftSetExt = (ImageButton) BBKLay.findViewById(R.id.SoftSetExt);
		// ---------------------------------------------------------------------
		SoftSetUser = (EditText) BBKLay.findViewById(R.id.SoftSetUser);
		SoftSetPass = (EditText) BBKLay.findViewById(R.id.SoftSetPass);
		SoftSetPhone = (EditText) BBKLay.findViewById(R.id.SoftSetPhone);
		// ---------------------------------------------------------------------
		SoftSetHost = (EditText) BBKLay.findViewById(R.id.SoftSetHost);
		SoftSetPath = (EditText) BBKLay.findViewById(R.id.SoftSetPath);
		// ---------------------------------------------------------------------
		SoftSetProxy = (EditText) BBKLay.findViewById(R.id.SoftSetProxy);
		SoftSetPort = (EditText) BBKLay.findViewById(R.id.SoftSetPort);
		SoftSetTime = (EditText) BBKLay.findViewById(R.id.SoftSetTime);
		// ---------------------------------------------------------------------
		SoftSetTit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				// --------------------------------------------
			}
		});
		SoftSetExt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				SetSave();
				layshow(false);
				// --------------------------------------------
			}
		});
		// ---------------------------------------------------------------------
		GpsGetSet();
		// ---------------------------------------------------------------------
		setpathname = BBKSoft.PathSets + "BBKMapNets.txt";
		SetLoad();
		// ==================================================================================
		TwinkHandler.postDelayed(TwinkRunnable, GpsUpdTm * 1000); // 开始Timer
		// ==================================================================================
	}

	public void GpsGetSet() {
		// ---------------------------------------------------------------------
		GpsUpdHand = (Button) BBKLay.findViewById(R.id.GpsUpHand);
		GpsUpdOpen = (ToggleButton) BBKLay.findViewById(R.id.GpsUpOpen);
		GpsUpdTime = (EditText) BBKLay.findViewById(R.id.GpsUpTime);
		GpsUpdBack = (EditText) BBKLay.findViewById(R.id.GpsUpBack);
		// ---------------------------------------------------------------------
		GpsUpdOpen.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// --------------------------------------------
				GpsUpdRunKey = isChecked;
				// --------------------------------------------
			}
		});
		GpsUpdHand.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				BBKSoft.myGpsUpd.GpsUpRunThread();
				// --------------------------------------------
			}
		});
		// ---------------------------------------------------------------------
		GpsGetHand = (Button) BBKLay.findViewById(R.id.GpsGetHand);
		GpsGetOpen = (ToggleButton) BBKLay.findViewById(R.id.GpsGetOpen);
		GpsGetTime = (EditText) BBKLay.findViewById(R.id.GpsGetTime);
		GpsGetBack = (EditText) BBKLay.findViewById(R.id.GpsGetBack);
		GpsGetIDid = (EditText) BBKLay.findViewById(R.id.GpsGetIDid);
		GpsGetLens = (EditText) BBKLay.findViewById(R.id.GpsGetLens);
		// ---------------------------------------------------------------------
		GpsGetOpen.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				GpsGetRunKey = isChecked;
			}
		});
		GpsGetHand.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				BBKSoft.myGpsGet.GpsLogGetThread();
				// --------------------------------------------
			}
		});
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void SetLoad() {
		// ----------------------------------------------
		GetJsonValue();
		SetTrueValue();
		SetViewValue();
		// ----------------------------------------------
	}

	public void SetSave() {
		// ----------------------------------------------
		GetViewValue();
		SetTrueValue();
		SetViewValue();
		SetJsonValue();
		// ----------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

	public boolean GetJsonValue() {
		// ---------------------------------------------------------------------
		String softStr = BBKSYS.FileRead(setpathname);
		bd.d("BBKGpsNetSet.MySoftRead.pathname = " + setpathname, false, true);
		bd.d("BBKGpsNetSet.MySoftRead.softStr = " + softStr, false, true);
		// ---------------------------------------------------------------------
		if (softStr == null || softStr.length() < 10)
			return false;
		// ---------------------------------------------------------------------
		JSONObject sets = null;
		try {
			// ----------------------------------------------
			sets = new JSONObject(softStr);
			// ----------------------------------------------
		} catch (JSONException ex) {
			bd.d("BBKGpsNetSet.SetJsonValue.JSON = " + setpathname, false, true);
			return false;
		}
		// ---------------------------------------------------------------------
		UserName = setsgetString(sets, "UserName");
		UserPass = setsgetString(sets, "UserPass");
		UsePhone = setsgetString(sets, "UsePhone");
		UserIDid = setsgetString(sets, "UserIDid");
		// ----------------------------------------------
		GpsUpdTs = setsgetString(sets, "GpsUpdTm");
		GpsGetTs = setsgetString(sets, "GpsGetTm");
		GpsGetID = setsgetString(sets, "GpsGetID");
		GpsGetLN = setsgetString(sets, "GpsGetLN");
		// ----------------------------------------------
		GpsWHost = setsgetString(sets, "GpsWHost");
		GpsWPath = setsgetString(sets, "GpsWPath");
		// ----------------------------------------------
		NetProxyss = setsgetString(sets, "PrxyHost");
		NetPxySort = setsgetString(sets, "PrxyPort");
		NetPxyTime = setsgetString(sets, "PrxyTime");
		// ---------------------------------------------------------------------
		return true;
		// ---------------------------------------------------------------------
	}

	public boolean SetJsonValue() {
		// ---------------------------------------------------------------------
		bd.d("BBKGpsNetSet.MySoftSave.pathname = " + setpathname, false, true);
		// ----------------------------------------------------
		try {
			// ------------------------------------------------
			JSONObject sets = new JSONObject();
			// ------------------------------------------------
			sets.put("UserName", UserName);
			sets.put("UserPass", UserPass);
			sets.put("UsePhone", UsePhone);
			sets.put("UserIDid", UserIDid);
			// ------------------------------------------------
			sets.put("GpsUpdTm", GpsUpdTm);
			sets.put("GpsGetTm", GpsGetTm);
			sets.put("GpsGetID", GpsGetID);
			sets.put("GpsGetLN", GpsGetLN);
			// ------------------------------------------------
			sets.put("GpsWHost", GpsWHost);
			sets.put("GpsWPath", GpsWPath);
			// ------------------------------------------------
			sets.put("PrxyHost", BBKHttpGet.NetProxy);
			sets.put("PrxyPort", BBKHttpGet.NetPorts);
			sets.put("PrxyTime", BBKHttpGet.NetTimes);
			// ------------------------------------------------
			String sss = sets.toString();
			bd.d("BBKGpsNetSet.SetJsonValue.JSON = " + sss, false, true);
			BBKSYS.FileSave(setpathname, sss);
			// ------------------------------------------------
		} catch (JSONException ex) {
			bd.d("BBKGpsNetSet.SetJsonValue.JSONO = " + ex.toString(), false, true);
			throw new RuntimeException(ex);
		}
		return true;
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	//
	// ===============================================================
	public final String gbCode = "UTF-8";// "GB2312";
	// ===============================================================
	public String UserName = "testname", UserPass = "123456 ";
	public String UserIDid = "0", UsePhone = "";
	// ===============================================================
	public boolean GpsUpdRunKey = false, GpsGetRunKey = false;
	public int GpsUpdTm = 5;// 5s间隔上传
	public int GpsGetTm = 5;// 5s间隔上传
	public String GpsUpdTs = GpsUpdTm + "";
	public String GpsGetTs = GpsGetTm + "";
	// ---------------------------------------------------------------
	public String GpsGetID = "10000";
	public String GpsGetLN = "10";
	// ===============================================================
	public String GpsWHost = "www.boboking.com/v";
	public String GpsWPath = "gs.php?";
	// ===============================================================
	public String NetProxyss = "";
	public String NetPxySort = "0";
	public String NetPxyTime = "300";

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

	private void GetViewValue() {
		// ---------------------------------------------------------------------
		UserName = SoftSetUser.getText().toString();
		UserPass = SoftSetPass.getText().toString();
		UsePhone = SoftSetPhone.getText().toString();
		UserIDid = "0";
		// ---------------------------------------------------------------------
		GpsUpdTs = GpsUpdTime.getText().toString();
		GpsGetTs = GpsGetTime.getText().toString();
		GpsUpdTm = IntegerparseInt(GpsUpdTs);
		GpsGetTm = IntegerparseInt(GpsGetTs);
		if (GpsUpdTm < 10)
			GpsUpdTm = 10;
		if (GpsGetTm < 10)
			GpsGetTm = 10;
		GpsGetID = GpsGetIDid.getText().toString();
		GpsGetLN = GpsGetLens.getText().toString();
		// ---------------------------------------------------------------------
		GpsWHost = SoftSetHost.getText().toString();
		GpsWPath = SoftSetPath.getText().toString();
		// ---------------------------------------------------------------------
		NetProxyss = SoftSetProxy.getText().toString();
		NetPxySort = SoftSetPort.getText().toString();
		NetPxyTime = SoftSetTime.getText().toString();
		// ---------------------------------------------------------------------
	}

	private void SetViewValue() {
		// ---------------------------------------------------------------------
		SoftSetUser.setText(UserName);
		SoftSetPass.setText(UserPass);
		SoftSetPhone.setText(UsePhone);
		// ---------------------------------------------------------------------
		GpsUpdTime.setText(GpsUpdTm + "");
		GpsGetTime.setText(GpsGetTm + "");
		GpsGetIDid.setText(GpsGetID);
		GpsGetLens.setText(GpsGetLN);
		// ---------------------------------------------------------------------
		SoftSetHost.setText(GpsWHost);
		SoftSetPath.setText(GpsWPath);
		// ---------------------------------------------------------------------
		SoftSetProxy.setText(BBKHttpGet.NetProxy);
		SoftSetPort.setText(BBKHttpGet.NetPorts + "");
		SoftSetTime.setText(BBKHttpGet.NetTimes + "");
		// ---------------------------------------------------------------------
	}

	private void SetTrueValue() {
		// ---------------------------------------------------------------------
		GpsUpdTm = IntegerparseInt(GpsUpdTs);
		GpsGetTm = IntegerparseInt(GpsGetTs);
		if (GpsUpdTm < 10)
			GpsUpdTm = 10;
		if (GpsGetTm < 10)
			GpsGetTm = 10;
		// ---------------------------------------------------------------------
		BBKHttpGet.NetProxy = NetProxyss;
		BBKHttpGet.NetPorts = IntegerparseInt(NetPxySort);
		BBKHttpGet.NetTimes = IntegerparseInt(NetPxyTime);
		if (BBKHttpGet.NetTimes < 30)
			BBKHttpGet.NetTimes = 30;
		// ---------------------------------------------------------------------
		BBDHttp.BBDHProxy(BBKHttpGet.NetProxy, BBKHttpGet.NetPorts, BBKHttpGet.NetTimes);
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

	private int IntegerparseInt(String key) {
		// -------------------------------------------------------
		if (key.length() <= 0) {
			return 0;
		} else {
			try {
				int kkk = Integer.parseInt(key);
				return kkk;
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		// -------------------------------------------------------
	}

	public String setsgetString(JSONObject sets, String key) {
		// ----------------------------------------------
		String s = "";
		try {
			// ----------------------------------------------
			s = sets.getString(key);
			// ----------------------------------------------
		} catch (JSONException e) {
		}
		return s;
		// ----------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	// ====================================================================================
	// ####################################################################################
	// #############################GpsUpload##############################################
	// ####################################################################################
	// ====================================================================================
	private Handler TwinkHandler = new Handler();
	private Runnable TwinkRunnable = new Runnable() {
		public void run() {
			// ------------------------------------------------------
			TwinkRun();
			// ------------------------------------------------------
			TwinkHandler.postDelayed(this, GpsUpdTm * 1000);
			// ------------------------------------------------------
		}

	};

	private void TwinkRun() {
		// ------------------------------------------------------
		if (GpsUpdRunKey)
			BBKSoft.myGpsUpd.GpsUpRunThread();
		// ------------------------------------------------------
		if (GpsGetRunKey)
			BBKSoft.myGpsGet.GpsLogGetThread();
		// ------------------------------------------------------
	}

	// TwinkHandler.postDelayed(TwinkRunnable,BBKSoft.myNetSet.GpsUpNetTime); //
	// 开始Timer
	// TwinkHandler.removeCallbacks(TwinkRunnable); //停止Timer
	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

	// private Timer TwinkTimer = new Timer();
	// private TimerWork myTask = new TimerWork();// 初始化我们的任务
	//
	// private void TimerStart() {
	// TwinkTimer.schedule(myTask, 0, BBKSoft.myNetSet.GpsUpNetTime);
	// // timer.schedule(myTask, 500, 3000);//
	// // 在5秒后执行此任务,每次间隔60秒,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
	// // timer.cancel();// 使用这个方法退出任务
	// }
	//
	// private class TimerWork extends TimerTask {
	// public void run() {
	// // ------------------------------------------------------
	// handlerTimer.post(RunnableTimer);
	// // ------------------------------------------------------
	// }
	// }
	//
	// private Handler handlerTimer = new Handler();
	// private Runnable RunnableTimer = new Runnable() {
	// public void run() {
	// // ------------------------------------------------------
	// if (GpsUpdRunKey) {
	// BBKSoft.myGpsUpd.GpsUpRun();
	// }
	// // ------------------------------------------------------
	// }
	// };

	// private void GpsUpdThread() {
	// // ===============================================================
	// new Thread() {
	// public void run() {
	// try {
	// // ===============================================================
	// GpsUpRun();
	// // ===============================================================
	// } catch (Exception e) {
	// BBKDebug.ddd("BBKNetGpsUpd.GpsUpdThread()== " + e.toString());
	// }
	// // ===============================================================
	// }
	// }.start();
	// // ===============================================================
	// }

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

	// private void SetLoadFirst() {
	// // ---------------------------------------------------------------------
	// SoftSetUser.setText(BBKSoft.myNetGps.UserName);
	// SoftSetPass.setText(BBKSoft.myNetGps.UserPass);
	// SoftSetPhone.setText("");
	// // --------------------------------------------
	// SoftSetHost.setText(BBKSoft.myNetGps.WebHost);
	// SoftSetPath.setText(BBKSoft.myNetGps.WebPath);
	// // --------------------------------------------
	// SoftSetProxy.setText(BBKSoft.proxyIP);
	// SoftSetPort.setText(BBKSoft.proxySort + "");
	// SoftSetTime.setText(BBKSoft.downTimeOut + "");
	// // ---------------------------------------------------------------------
	// }

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

	public interface IControlDataBinder {
		void SetValue(View e, Object value, String format);

		Object GetValue(View e);
	}

	public class EditTextDataBinder implements IControlDataBinder {

		@Override
		public void SetValue(View e, Object value, String format) {
			// TODO Auto-generated method stub
			EditText control = (EditText) e;
			if (format == null || format.equals("")) {
				control.setText(value.toString());
			} else {
				control.setText(String.format(format, value));
			}
		}

		@Override
		public Object GetValue(View e) {
			// TODO Auto-generated method stub
			EditText control = (EditText) e;
			return control.getText().toString();
		}

	}

	public class TextViewDataBinder implements IControlDataBinder {

		@Override
		public void SetValue(View e, Object value, String format) {
			// TODO Auto-generated method stub
			TextView control = (TextView) e;
			if (format == null || format.equals("")) {
				control.setText(value.toString());
			} else {
				control.setText(String.format(format, value));
			}
		}

		@Override
		public Object GetValue(View e) {
			// TODO Auto-generated method stub
			TextView control = (TextView) e;
			return control.getText().toString();
		}

	}
	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
}
