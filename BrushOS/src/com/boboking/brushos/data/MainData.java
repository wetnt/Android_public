package com.boboking.brushos.data;

import java.util.Date;
import java.util.List;

import com.boboking.tools.BBKSettingTool;
import com.boboking.tools.d;

@SuppressWarnings("deprecation")
public class MainData {

	private int TenMinIndex_Now = -999;
	public int[] chartTenData = new int[144];
	public MinTenData temp = new MinTenData();

	public int[] chartNowData = new int[60];
	public int BrushSecond = 0;
	public static int BrushAccKey = 2;

	public MainData() {
		Data_New();
		Data_Clear();
	}

	private void Data_New() {
		for (int i = 0; i < chartTenData.length; i++) {
			chartTenData[i] = 0;
		}
		for (int i = 0; i < chartNowData.length; i++) {
			chartNowData[i] = 0;
		}
	}

	private void Data_Clear() {
		for (int i = 0; i < chartTenData.length; i++) {
			chartTenData[i] = 0;
		}
		for (int i = 0; i < chartNowData.length; i++) {
			chartNowData[i] = 0;
		}
	}

	public void Data_Set_Temp_First() {
		// ----------------------------------------------------
		TenMinIndex_Now = GetDayTenMinIndex(System.currentTimeMillis());
		// ----------------------------------------------------
		temp.set(System.currentTimeMillis(), TenMinIndex_Now, chartTenData[TenMinIndex_Now]);
		// ----------------------------------------------------
	}

	public void Data_AddNew(int a) {
		// ----------------------------------------------------
		a = Data_Small(a);
		// ----------------------------------------------------
		Data_Move_Front();
		chartNowData[chartNowData.length - 1] = a;
		// ----------------------------------------------------
		TenMinIndex_Now = GetDayTenMinIndex(System.currentTimeMillis());
		// ----------------------------------------------------
		if (temp.i == TenMinIndex_Now) {
			temp.a += a;
		} else {
			temp.set(System.currentTimeMillis(), TenMinIndex_Now, a);
		}
		chartTenData[TenMinIndex_Now] = temp.a;
		// ----------------------------------------------------
		d.s("MainData Data_AddNew = " + "\ttemp =" + temp.i + ",\t" + temp.a + ",\t" + temp.d);
		// ----------------------------------------------------
	}

	// ============================================================================
	// ============================================================================
	// ============================================================================
	private void Data_Move_Front() {// 向前移动数据
		for (int i = 0; i < chartNowData.length - 1; i++) {
			chartNowData[i] = chartNowData[i + 1];
		}
	}

	public int Data_Small(int k) {// 阈值控制
		k = k / 1000;
		if (k >= BrushAccKey) {
			BrushSecond++;
		} else {
			k = 0;
		}
		return k;
	}

	// ============================================================================
	// ============================================================================
	// ============================================================================
	public void GetDB_DayData_To_chartData(List<MinTenData> dList) {
		for (int i = 0; i < chartTenData.length; i++) {
			chartTenData[i] = 0;
		}
		for (int i = 0; i < dList.size(); i++) {
			chartTenData[dList.get(i).i] = dList.get(i).a;
		}
	}

	public void ChartData_Value_Check() {
		for (int i = 0; i < chartTenData.length; i++) {
			if (chartTenData[i] > 500)
				chartTenData[i] = 500;
		}
	}

	// ============================================================================
	// ============================================================================
	// ============================================================================
	public static int GetDayTenMinIndex(long l) {
		Date d = new Date(l);
		return d.getHours() * 6 + (d.getMinutes() - d.getMinutes() % 10) / 10;
	}

	public static long GetDayStartLong(long l) {
		Date d = new Date(l);
		Date t = new Date(d.getYear(), d.getMonth(), d.getDate(), 0, 0, 0);
		return t.getTime();
	}

	public static long GetDayEndedLong(long l) {
		Date d = new Date(l);
		Date t = new Date(d.getYear(), d.getMonth(), d.getDate(), 23, 59, 59);
		return t.getTime();
	}

	// ============================================================================
	// ============================================================================
	// ============================================================================
	public static void BrushAccKey_Turn() {
		// ----------------------------------------------------
		BrushAccKey++;
		if (BrushAccKey > 8)
			BrushAccKey = 0;
		// ----------------------------------------------------
		BBKSettingTool.Save_Long("BrushAccKey", BrushAccKey);
		d.m("BrushAccKey==" + BrushAccKey);
		// ----------------------------------------------------
	}
}
