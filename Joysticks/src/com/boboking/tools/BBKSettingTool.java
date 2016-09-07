package com.boboking.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BBKSettingTool {

	// ====================================================================================
	public static long SoftRunCount;
	private static String SoftRunCountStr = "SoftRunCount";
	// ====================================================================================
	private static SharedPreferences preferences;
	private static Editor editor;

	// ====================================================================================
	public static long Start(final Activity act) {
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
		editor.putLong(key, l);
		editor.commit();
	}

	public static long Read_Long(String key, long l) {
		long x = preferences.getLong(key, -9999);
		if (x == -9999)
			Save_Long(key, l);
		return preferences.getLong(key, l);
	}
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================

}
