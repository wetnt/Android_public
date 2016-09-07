package com.boboking.view.columnchart;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.SubcolumnValue;
import android.app.Activity;

import com.boboking.tools.d;

public class BBKChartNow extends BBKColumnChartView {

	private final static int[] xNumberLists = new int[] { 0, 10, 20, 30, 40, 50, 59 };// X横轴数据
	private final static String[] xLable = new String[] { "0", "10", "20", "30", "40", "50", "60" };// X横轴数据
	private final static int[] yNumberLists = new int[] { 1, 4, 7, 10 };// Y横轴数据
	// private final static String[] yLable = new String[] { "轻微", "达标", "良好",
	// "优秀" };// Y横轴数据
	private final static String[] yLable = new String[] { "", "", "", "" };// Y横轴数据

	public BBKChartNow(Activity act, int RecID) {
		Chart_Init(act, RecID, //
				chartData, new ValueTouchListener(),//
				xNumberLists, xLable, yNumberLists, yLable//
		);
	}

	// 柱状图监听器
	private class ValueTouchListener implements ColumnChartOnValueSelectListener {
		@Override
		public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
			// generateLineData(value.getColor(), 100);
			d.s(columnIndex + "x" + subcolumnIndex + "=" + value);
		}

		@Override
		public void onValueDeselected() {
			// generateLineData(ChartUtils.COLOR_GREEN, 0);
		}
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

	private int chartData[] = { //
	//
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, // 10
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, // 20
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, // 30
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, // 40
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, // 50
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, // 60
	}; // 40

	public void Chart_Data_Flash_New() {
		for (int i = 0; i < 50; i++) {
			int id = (int) (0 + Math.random() * chartData.length);
			int vl = (int) (0 + Math.random() * 15);
			Chart_Data_Change_One(id, vl);
		}
		Chart_Data_Flash();
	}

	public void Chart_Data_Flash_Random() {
		Chart_Data_Random_All(0, 18);
		Chart_Data_Flash();
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
}
