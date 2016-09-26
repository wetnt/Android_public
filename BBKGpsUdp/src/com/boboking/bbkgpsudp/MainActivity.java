package com.boboking.bbkgpsudp;

import com.zhsk.bbktool.BBK_Tool_Net;
import com.zhsk.bbktool.BBK_Tool_Setting;
import com.zhsk.bbktool.d;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import bbk.map.gps.BBKGps;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// --------------------------------------------------------
		d.init(MainActivity.this, "");
		ViewLoad();
		gps.GpsInt(MainActivity.this);
		// --------------------------------------------------------
		BBK_Tool_Setting.Init(MainActivity.this);
		WorkSetting_read();
		// --------------------------------------------------------
	}

	private static TextView fnp_info_txt;
	private static TextView fnp_host_send_times;
	private Button fnp_gpsopen_btn, fnp_host_send_btn;
	private static CheckBox fnp_host_auto_ckbox, fnp_host_force_ckbox, fnp_host_byte_ckbox, fnp_host_move_ckbox;

	private static EditText fnp_host_id_edit, fnp_host_ip_edit, fnp_host_pt_edit;
	private static EditText fnp_info_send_edit, fnp_info_receive_txt;

	private void ViewLoad() {
		// --------------------------------------------------------
		fnp_info_txt = (TextView) findViewById(R.id.fnp_info_txt);
		fnp_gpsopen_btn = (Button) findViewById(R.id.fnp_gpsopen_btn);
		// --------------------------------------------------------
		fnp_host_id_edit = (EditText) findViewById(R.id.fnp_host_id_edit);
		fnp_host_ip_edit = (EditText) findViewById(R.id.fnp_host_ip_edit);
		fnp_host_pt_edit = (EditText) findViewById(R.id.fnp_host_port_edit);
		// --------------------------------------------------------
		fnp_host_send_times = (TextView) findViewById(R.id.fnp_host_send_times);
		fnp_host_send_btn = (Button) findViewById(R.id.fnp_host_send_btn);
		fnp_host_force_ckbox = (CheckBox) findViewById(R.id.fnp_host_force_ckbox);
		fnp_host_auto_ckbox = (CheckBox) findViewById(R.id.fnp_host_auto_ckbox);
		fnp_host_byte_ckbox = (CheckBox) findViewById(R.id.fnp_host_byte_ckbox);
		fnp_host_move_ckbox = (CheckBox) findViewById(R.id.fnp_host_move_ckbox);
		// --------------------------------------------------------
		fnp_info_send_edit = (EditText) findViewById(R.id.fnp_info_send_edit);
		fnp_info_receive_txt = (EditText) findViewById(R.id.fnp_info_receive_txt);
		// --------------------------------------------------------
		fnp_gpsopen_btn.setOnClickListener(clickWork);
		fnp_host_send_btn.setOnClickListener(clickWork);
		// --------------------------------------------------------
		fnp_info_send_edit.setEnabled(false);
		fnp_info_send_edit.setTypeface(Typeface.MONOSPACE);//设置字体  

		//AssetManager mgr=getAssets();//得到AssetManager
		//Typeface tf=Typeface.createFromAsset(mgr, "fonts/ttf.ttf");//根据路径得到Typeface

		//textChat.setFont(SWTResourceManager.getFont("SimSun-ExtB", 14, SWT.NORMAL));
		// --------------------------------------------------------
		fnp_host_id_edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String ids = fnp_host_id_edit.getText().toString().trim();
				idl = Long.parseLong(ids.length() == 0 ? "0" : ids);
				BBK_Tool_Setting.Save_Long(KEY_IDSS, idl);
				xIDchange();
			}
		});
		fnp_host_ip_edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				ips = fnp_host_ip_edit.getText().toString();
				BBK_Tool_Setting.Save_String(KEY_IPSS, ips);
			}
		});
		fnp_host_pt_edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String pts = fnp_host_pt_edit.getText().toString().trim();
				prt = Integer.parseInt(pts.length() == 0 ? "0" : pts);
				prt = prt > BBK_Tool_Net.PORT_Max ? BBK_Tool_Net.PORT_Max : prt;
				BBK_Tool_Setting.Save_Long(KEY_PORT, prt);
			}
		});
		// --------------------------------------------------------
		BBK_Tool_Net.initNet(MainActivity.this);
		EditLock_Turn();
		forceInit();
		// --------------------------------------------------------
	}

	private OnClickListener clickWork = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// --------------------------------------------------------
			if (v == fnp_gpsopen_btn) {
				// gps.GpsOpen();
			} else if (v == fnp_host_send_btn) {
				UDP_send_data(true);
			}
		}
	};
	// ================================================================================

	public static BBKGps gps = new BBKGps();

	public static void GpsUpdate() {
		// --------------------------------------------------------
		try {
			fnp_info_txt.setText(gps.gm.g.a);
			// d.s(gps.gm.g.a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// --------------------------------------------------------
		try {
			fnp_info_send_edit.setText(BBKNetUDP.toLogString(idl, gps.gm.g));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// --------------------------------------------------------
		if (fnp_host_auto_ckbox.isChecked())
			UDP_send_data(false);
		// --------------------------------------------------------
	}

	private static void xIDchange() {
		// --------------------------------------------------------
		udp_send_times = 0;
		fnp_host_send_times.setText(udp_send_times + "");
		// --------------------------------------------------------
	}

	private static String ips = "";
	private static int prt = 38888;
	private static long idl = 10000;
	private static long updSendTime = System.currentTimeMillis();
	private static int udp_send_times = 0;

	public static void UDP_send_data(boolean force) {
		try {
			// --------------------------------------------------------
			if (!force && fnp_host_move_ckbox.isChecked() && !gps.gm.g.R)
				return;
			// --------------------------------------------------------
			if (fnp_host_byte_ckbox.isChecked()) {
				BBK_Tool_Net.UdpSend(ips, prt, BBKNetUDP.toBytes(idl, gps.gm.g));
			} else {
				BBK_Tool_Net.UdpSend(ips, prt, BBKNetUDP.toUdpString(idl, gps.gm.g).getBytes());
			}
			// --------------------------------------------------------
			udp_send_times++;
			fnp_host_send_times.setText(udp_send_times + "");
			updSendTime = System.currentTimeMillis();
			// --------------------------------------------------------
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
		}
	};

	@Override
	protected void onDestroy() {
		// ----------------------------------------------------
		gps.GpsClose();
		// ----------------------------------------------------
		TwinkHandler.removeCallbacks(TwinkRunnable); // 停止Timer
		// ----------------------------------------------------
		super.onDestroy();
	}

	// ========================================================================================
	// ========================================================================================
	// ========================================================================================
	public static String bytes2hex(byte[] bytes) {
		final String HEX = "0123456789abcdef";
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(HEX.charAt((b >> 4) & 0x0f));
			sb.append(HEX.charAt(b & 0x0f));
		}
		return sb.toString();
	}

	// ========================================================================================
	// ========================================================================================
	// ========================================================================================
	private final String KEY_IDSS = "key_idss";
	private final String KEY_IPSS = "key_ipip";
	private final String KEY_PORT = "key_port";

	private void WorkSetting_read() {
		// --------------------------------------------------------
		idl = BBK_Tool_Setting.Read_Long(KEY_IDSS, 10009);
		ips = BBK_Tool_Setting.Read_String(KEY_IPSS, "boboking.jios.org");
		prt = (int) BBK_Tool_Setting.Read_Long(KEY_PORT, 38888);
		prt = prt > BBK_Tool_Net.PORT_Max ? BBK_Tool_Net.PORT_Max : prt;
		// --------------------------------------------------------
		d.s(idl + " \t" + ips + " \t" + prt);
		// --------------------------------------------------------
		fnp_host_id_edit.setText(idl + "");
		fnp_host_ip_edit.setText(ips);
		fnp_host_pt_edit.setText(prt + "");
		// --------------------------------------------------------
	}

	// ========================================================================================
	// ========================================================================================
	// ========================================================================================
	private boolean EditLock = true;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			EditLock_Turn();
			return true;
		} else if (id == R.id.action_close) {
			dialog();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void EditLock_Turn() {
		EditLock = !EditLock;
		fnp_host_id_edit.setEnabled(EditLock);
		fnp_host_ip_edit.setEnabled(EditLock);
		fnp_host_pt_edit.setEnabled(EditLock);
		fnp_host_auto_ckbox.setEnabled(EditLock);
		fnp_host_force_ckbox.setEnabled(EditLock);
		fnp_info_receive_txt.setEnabled(EditLock);
		fnp_host_byte_ckbox.setEnabled(EditLock);
		fnp_host_move_ckbox.setEnabled(EditLock);
		// fnp_host_send_btn.setEnabled(EditLock);
	}

	// ========================================================================================
	// ========================================================================================
	// ========================================================================================
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				MainActivity.this.finish();
			}
		});
		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
			// dialog();
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			Toast.makeText(MainActivity.this, "Menu", Toast.LENGTH_SHORT).show();
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
			Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 拦截/屏蔽系统Home键
	// public void onAttachedToWindow() {
	// this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
	// super.onAttachedToWindow();

	// ========================================================================================
	// ========================================================================================
	// ========================================================================================
	public int GpsUpdTm = 5;// 5s间隔上传

	private void forceInit() {
		TwinkHandler.postDelayed(TwinkRunnable, GpsUpdTm * 1000); // 开始Timer
	}

	private Handler TwinkHandler = new Handler();
	private Runnable TwinkRunnable = new Runnable() {
		public void run() {
			// ------------------------------------------------------
			forceUDPSend();
			TwinkHandler.postDelayed(this, GpsUpdTm * 1000);
			// ------------------------------------------------------
		}
	};

	private void forceUDPSend() {
		// ----------------------------------------------------
		if (!fnp_host_force_ckbox.isChecked())
			return;
		if (System.currentTimeMillis() - updSendTime < 1500)
			return;
		// ----------------------------------------------------
		UDP_send_data(true);
		// ----------------------------------------------------
	}
	// =========================================================================================
	// =========================================================================================
	// =========================================================================================
}