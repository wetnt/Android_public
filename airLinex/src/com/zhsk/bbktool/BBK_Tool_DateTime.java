package com.zhsk.bbktool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BBK_Tool_DateTime {

	public static final SimpleDateFormat poiTmFt = new SimpleDateFormat("yyyyMMdd_HHmmss");

	public static String Get_Now_DateTime_String() {
		return poiTmFt.format(Get_NowDate_Time());
	}

	public static Date Get_NowDate_Time() {
		return new Date(System.currentTimeMillis());
	}

	public static String Get_DateTime_String(Date date) {
		return poiTmFt.format(date);
	}

}
