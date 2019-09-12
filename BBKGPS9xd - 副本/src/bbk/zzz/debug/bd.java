﻿package bbk.zzz.debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import bbk.bbk.box.BBKSoft;
import bbk.sys.abc.BBKMsgBox;

public class bd {// BBKDebug

	// ====================================================================================
	// ####################################################################################
	// #############################程序Log显示############################################
	// ####################################################################################
	// ====================================================================================
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat poiTmFt = new SimpleDateFormat("yyyyMMdd_HHmmss");
	public static String DebugPathName = "Debug_.txt";
	private static Date timePoi = new Date(0);

	// ====================================================================================
	public static String MyInt() {
		// -------------------------------------------------------------------
		File debugfile = new File(BBKSoft.PathSets, "Debug_" + poiTmFt.format(timeNow()) + ".txt");
		d(debugfile.getPath(), false, false);
		// -------------------------------------------------------------------
		try {
			debugfile.createNewFile();
			DebugPathName = debugfile.getPath();
			debugfile.delete();
		} catch (IOException e) {
			d("!!! bbt file Create Error !!!", true, false);
		} // 测试创建文件
			// ---------------------------------------------------------------------------------------
		return "";
		// ---------------------------------------------------------------------------------------
	}

	// ====================================================================================
	public static void d(String str) {
		d(str, false, false);
	}

	public static void d(StringBuffer str, boolean MsgKey, boolean FileKey) {
		d(str, MsgKey, FileKey);
	}

	public static void d(String str, boolean MsgKey, boolean FileKey) {
		// -----------------------------------------------------------------------------------
		System.out.println("&&& " + str);
		// -----------------------------------------------------------------------------------
		if (MsgKey) {
			BBKMsgBox.tShow(str);
		}
		// -----------------------------------------------------------------------------------
		if (FileKey) {
			timePoi = new Date(System.currentTimeMillis());
			app(poiTmFt.format(timePoi) + " === " + str);
		}
		// -----------------------------------------------------------------------------------
	}

	public static void d(int str, boolean MsgKey, boolean FileKey) {
		d(str + "", MsgKey, FileKey);
	}

	// ====================================================================================
	// ####################################################################################
	// #############################程序Log显示############################################
	// ####################################################################################
	// ====================================================================================

	public static void app(String str) {
		// -----------------------------------------------------------------------
		try {
			// -------------------------------------------------------------------
			str = str + "\r\n";
			// -------------------------------------------------------------------
			FileWriter fw = new FileWriter(DebugPathName, true);
			fw.write(str);
			fw.flush();
			fw.close();
			// -------------------------------------------------------------------
		} catch (Exception e) {
		}
		// -----------------------------------------------------------------------
	}

	public static Date timeNow() {
		return new Date(System.currentTimeMillis());
	}

	// ====================================================================================
	// ####################################################################################
	// #############################程序Log显示############################################
	// ####################################################################################
	// ====================================================================================

	// ====================================================================================
	// ####################################################################################
	// #############################程序计时器#############################################
	// ####################################################################################
	// ====================================================================================
	public static Date t0 = new Date(System.currentTimeMillis());
	public static Date t1 = new Date(System.currentTimeMillis());
	public static long tt = 0;

	public static void tt0(boolean MsgKey, boolean FileKey) {
		t0 = new Date(System.currentTimeMillis());
		d("--------------t0----------------", MsgKey, FileKey);
	}

	public static void ttt(String str, boolean MsgKey, boolean FileKey) {
		t1 = new Date(System.currentTimeMillis());
		tt = t1.getTime() - t0.getTime();
		t0 = new Date(System.currentTimeMillis());
		d(str + " = " + tt, MsgKey, FileKey);
	}
	// ====================================================================================
	// ####################################################################################
	// #############################程序计时器#############################################
	// ####################################################################################
	// ====================================================================================

}
