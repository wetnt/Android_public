package com.boboking.joysticks;

import com.boboking.tools.BBKUiTool;
import com.boboking.tools.d;
import com.boboking.tools.bluetooth.BBKBlueTooth;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// ----------------------------------------------------
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ----------------------------------------------------
		act = MainActivity.this;
		viewLoad();
		// ----------------------------------------------------
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// ================================================================================
	// ================================================================================
	// ================================================================================
	public Activity act;
	private String storagePath = "/Joysticks/";
	public String myPath = android.os.Environment.getExternalStorageDirectory().getPath() + storagePath;
	public BBKBlueTooth bt;

	// ================================================================================
	// ================================================================================
	// ================================================================================
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ----------------------------------------------------
		bt.BBKBlueTooth_DestroySYS();
		// SecTimerHandler.removeCallbacks(SecTimerRunnable); // 停止Timer
		// ----------------------------------------------------
		BBKUiTool.unbindDrawables(findViewById(R.id.root));
		System.gc();
		// ----------------------------------------------------
	}

	// ================================================================================
	// ================================================================================
	// ================================================================================
	public static TextView txt_info;
	public EditText txt_send;
	public Button btn_send;
	public static EditText txt_recv;
	public static Button btn_a;
	public Button btn_b;
	public Button btn_c;
	public Button btn_d;
	public Button btn_e;
	public Button btn_f;
	public ImageView btn_joysticks;

	public void viewLoad() {
		// ----------------------------------------------------
		txt_info = (TextView) act.findViewById(R.id.txt_info);
		txt_send = (EditText) act.findViewById(R.id.txt_send);
		btn_send = (Button) act.findViewById(R.id.btn_send);
		txt_recv = (EditText) act.findViewById(R.id.txt_recv);
		// ----------------------------------------------------
		btn_a = (Button) act.findViewById(R.id.btn_a);
		btn_b = (Button) act.findViewById(R.id.btn_b);
		btn_c = (Button) act.findViewById(R.id.btn_c);
		btn_d = (Button) act.findViewById(R.id.btn_d);
		btn_e = (Button) act.findViewById(R.id.btn_e);
		btn_f = (Button) act.findViewById(R.id.btn_f);
		// ----------------------------------------------------
		btn_joysticks = (ImageView) act.findViewById(R.id.btn_joysticks);
		// ----------------------------------------------------
		bt = new BBKBlueTooth(act);
		// ----------------------------------------------------
		btn_a.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bt.BBKBlueTooth_FindDevices();// 查找设备;
			}
		});
		btn_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String sendStr = txt_send.getText().toString();
				bt.BBKBlueTooth_SendString(sendStr);
			}
		});
		btn_f.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rStr = "";
			}
		});
		// ----------------------------------------------------		
		btn_b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {//前进
				cStr = "$FFFF7F7F7F7F7F*\n";
				bt.BBKBlueTooth_SendString(cStr);
			}
		});
		btn_c.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {//后退
				cStr = "$00007F7F7F7F7F*\n";
				bt.BBKBlueTooth_SendString(cStr);
			}
		});
		btn_d.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {//左转
				cStr = "$00FF7F7F7F7F7F*\n";
				bt.BBKBlueTooth_SendString(cStr);
			}
		});
		btn_e.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {//右转
				cStr = "$FF007F7F7F7F7F*\n";
				bt.BBKBlueTooth_SendString(cStr);
			}
		});
		// ----------------------------------------------------	
	}
	public static void BlueTooth_OnOffline_Set(boolean key) {
		btn_a.setBackgroundColor(key ?Color.CYAN:Color.RED );
		d.s("BlueKey = " + key);
	}
	
	public static String rStr = "";	
	public static String cStr = "$FFFF7F7F7F7F7F*\n";
	public static void BlueTooth_Receive(final String s) {
		txt_info.post(new Runnable(){
			@Override
			public void run() {
				rStr+=s;
				txt_recv.setText(rStr);
			}});		
	}
	// ================================================================================
	// ================================================================================
	// ================================================================================
}
