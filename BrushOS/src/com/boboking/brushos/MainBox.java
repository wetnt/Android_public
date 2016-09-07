package com.boboking.brushos;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.boboking.brushos.data.MainData;
import com.boboking.brushos.sqlite.BBKSQLite_Works;
import com.boboking.tools.BBKFileTool;
import com.boboking.tools.BBKSettingTool;
import com.boboking.tools.BBKUiTool;
import com.boboking.tools.d;
import com.boboking.tools.bluetooth.BBKBlueTooth;
import com.boboking.tools.bluetooth.BBKMPU6050_Data;
import com.boboking.view.columnchart.BBKChartDay;
import com.boboking.view.columnchart.BBKChartNow;

public class MainBox extends Activity {

	public Activity act;
	private String storagePath = "/BrushOS/";
	public String myPath = android.os.Environment.getExternalStorageDirectory().getPath() + storagePath;
	public BBKBlueTooth bt;
	public BBKSQLite_Works sql;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// ----------------------------------------------------
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart_main);
		// ----------------------------------------------------
		act = this;
		// ----------------------------------------------------
		myPath = android.os.Environment.getExternalStorageDirectory().getPath() + storagePath;
		BBKFileTool.CheckMakeDir(myPath);
		d.init(act, myPath);
		// ----------------------------------------------------
		chartDay = new BBKChartDay(act, R.id.chartDay);
		chartNow = new BBKChartNow(act, R.id.chartNow);
		// ----------------------------------------------------
		TxtSet(act);
		ImageButtonSet(act);
		// ----------------------------------------------------
		BBKSettingTool.Start(act);
		// ----------------------------------------------------
		bt = new BBKBlueTooth(MainBox.this);
		// ----------------------------------------------------
		sql = new BBKSQLite_Works();
		sql.CreateDB(MainBox.this, myPath + "main.db");
		sql.GetTodayData();
		// ----------------------------------------------------
		MainData.BrushAccKey = (int) BBKSettingTool.Read_Long("BrushAccKey", 2);
		mainData.GetDB_DayData_To_chartData(sql.dayTab.dayList);
		mainData.ChartData_Value_Check();// 范围检查防止超值
		mainData.Data_Set_Temp_First();
		// ----------------------------------------------------
		chartNow.Chart_Data_Set(mainData.chartNowData);
		chartNow.Chart_Data_Flash();
		chartDay.Chart_Data_Set(mainData.chartTenData);
		chartDay.Chart_Data_Flash();
		// ----------------------------------------------------
		AllData_Flash();
		// ----------------------------------------------------
		SecTimerHandler.postDelayed(SecTimerRunnable, 1000); // 开始timer
		// ----------------------------------------------------
	}

	// =========================================================================================
	// =========================================================================================
	// =========================================================================================
	private MainData mainData = new MainData();
	private Handler SecTimerHandler = new Handler();
	// SecTimerHandler.postDelayed(SecTimerRunnable,1000); //开始timer
	// SecTimerHandler.removeCallbacks(SecTimerRunnable); //停止Timer
	private Runnable SecTimerRunnable = new Runnable() {
		public void run() {
			// ----------------------------------------------------
			AllData_Flash();
			SecTimerHandler.postDelayed(SecTimerRunnable, 1000);
			// ----------------------------------------------------
		}
	};

	private void AllData_Flash() {
		// ----------------------------------------------------
		BBKMPU6050_Data.Update_Sum_Data();// 传感器获取每秒的振幅之和
		// ----------------------------------------------------
		mainData.Data_AddNew(BBKMPU6050_Data.angleSumInt_XYZ);
		TxtShow(mainData.BrushSecond);
		// ----------------------------------------------------
		sql.UpdateDayData(mainData.temp);
		sql.GetTodayData();
		// ----------------------------------------------------
		mainData.GetDB_DayData_To_chartData(sql.dayTab.dayList);
		mainData.ChartData_Value_Check();// 范围检查防止超值
		// ----------------------------------------------------
		chartNow.Chart_Data_Set(mainData.chartNowData);
		chartNow.Chart_Data_Flash();
		chartDay.Chart_Data_Set(mainData.chartTenData);
		chartDay.Chart_Data_Flash();
		// ----------------------------------------------------
	}

	// =========================================================================================
	// =========================================================================================
	// =========================================================================================
	private static BBKChartDay chartDay;
	private BBKChartNow chartNow;

	// =========================================================================================
	// =========================================================================================
	// =========================================================================================
	private TextView flashLong;

	private void TxtSet(Activity act) {
		flashLong = (TextView) act.findViewById(R.id.chartDuration);
	}

	public void TxtShow(double f) {
		f = Math.floor(f / 6.0) / 10.0;
		flashLong.setText(f + "");
	}

	private ImageView chartShare;
	private static ImageView chartOnline;
	private ImageView chartExit;
	private ImageView chartDayRew, chartDayFfs;

	private void ImageButtonSet(Activity act) {
		// ----------------------------------------------------
		chartShare = (ImageView) act.findViewById(R.id.chartShare);
		chartOnline = (ImageView) act.findViewById(R.id.chartOnline);
		chartExit = (ImageView) act.findViewById(R.id.chartExit);
		chartDayRew = (ImageView) act.findViewById(R.id.chartDayRew);
		chartDayFfs = (ImageView) act.findViewById(R.id.chartDayFfs);
		// ----------------------------------------------------
		chartExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SecTimerHandler.removeCallbacks(SecTimerRunnable);
				//bt.BBKBlueTooth_DestroySYS();
				finish();
			}
		});
		// ----------------------------------------------------
		chartDayRew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				chartDay.Chart_Data_Flash_Random();
			}
		});
		chartDayFfs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				chartNow.Chart_Data_Flash_New();
			}
		});
		// ----------------------------------------------------
		chartShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainData.BrushAccKey_Turn();
			}
		});
		// ----------------------------------------------------
	}

	public static void BlueTooth_OnOffline_Set(boolean key) {
		chartOnline.setBackgroundResource(key ? android.R.drawable.presence_online : android.R.drawable.presence_offline);
	}

	// ================================================================================
	// ================================================================================
	// ================================================================================
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ----------------------------------------------------
		bt.BBKBlueTooth_DestroySYS();
		sql.CloseDB();
		SecTimerHandler.removeCallbacks(SecTimerRunnable); // 停止Timer
		// ----------------------------------------------------
		// ----------------------------------------------------
		BBKUiTool.unbindDrawables(findViewById(R.id.chartRootBox));
		System.gc();
		// ----------------------------------------------------
	}
	// ================================================================================
	// ================================================================================
	// ================================================================================
}
