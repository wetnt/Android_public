package com.zhsk.bbktool;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BBK_Tool_Setting {

	// ====================================================================================
	public static long SoftRunCount;
	private static String SoftRunCountStr = "SoftRunCount";
	// ====================================================================================
	private static SharedPreferences preferences;
	private static Editor editor;

	// ====================================================================================
	public static long Init(final Activity act) {
		// ---------------------------------------------------------------------
		preferences = act.getSharedPreferences("phone", Context.MODE_PRIVATE);
		editor = preferences.edit();
		// ---------------------------------------------------------------------
		SoftRunCount = Read_Long(SoftRunCountStr, 0);
		Save_Long(SoftRunCountStr, SoftRunCount);
		d.s("SoftRunCount = " + SoftRunCount);
		// ---------------------------------------------------------------------
		return SoftRunCount;
		// ---------------------------------------------------------------------
	}

	public static void Save_Long(String key, long l) {
		editor = preferences.edit();
		editor.putLong(key, l);
		editor.commit();
	}

	public static long Read_Long(String key, long l) {
		long x = preferences.getLong(key, -9999);
		if (x == -9999)
			Save_Long(key, l);
		return preferences.getLong(key, l);
	}

	public static void Save_String(String key, String l) {
		editor = preferences.edit();
		editor.putString(key, l);
		editor.commit();
	}

	public static String Read_String(String key, String l) {
		String x = preferences.getString(key, "-9999");
		if (x.equals("-9999"))
			Save_String(key, l);
		return preferences.getString(key, l);
	}
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================

}
