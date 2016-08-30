package com.zhsk.bbktool;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.os.Environment;

public class BBK_Tool_Path {
	// ================================================================================
	// ================================================================================
	// ================================================================================
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat poiTmFt = new SimpleDateFormat("yyyyMMdd_HHmmss");
	// ================================================================================
	// private static String StoragePath = "/!ZHSK/mPaint/";
	private static String StoragePath = "/!ZHSK/";
	public static String myPath = Environment.getExternalStorageDirectory().getPath() + StoragePath;

	// public static String nowPath = myPath + poiTmFt.format(new
	// Date(System.currentTimeMillis())) + "/";

	public static void init() {
		// --------------------------------------------------------
		myPath = Environment.getExternalStorageDirectory().getPath() + StoragePath;
		// nowPath = myPath + poiTmFt.format(new
		// Date(System.currentTimeMillis())) + "/";
		BBK_Tool_File.CheckMakeDir(myPath);
		// BBKFileTool.CheckMakeDir(nowPath);
		// --------------------------------------------------------
	}
}
