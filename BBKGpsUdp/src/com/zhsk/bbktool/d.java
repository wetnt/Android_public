package com.zhsk.bbktool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class d {// BBKDebug

	// ====================================================================================
	// ####################################################################################
	// #############################程序Log显示############################################
	// ####################################################################################
	// ====================================================================================
	public static String DebugPathName = "Debug_.txt";
	private static Date timePoi = new Date(0);
	private static String myPath = "";

	// ====================================================================================
	public static void init(Activity _act, String path) {
		// -------------------------------------------------------------------
		act = _act;
		myPath = path.length() > 0 ? path : Environment.getExternalStorageDirectory().getPath() + "/!BBK_Data/";
		s("myPath=" + myPath);
		BBK_Tool_File.CheckMakeDir(myPath);
		// -------------------------------------------------------------------
		File debugfile = new File(myPath, "Debug_" + BBK_Tool_DateTime.Get_Now_DateTime_String() + ".txt");
		outPut(debugfile.getPath(), false, false);
		// -------------------------------------------------------------------
		try {
			debugfile.createNewFile();
			DebugPathName = debugfile.getPath();
			debugfile.delete();
		} catch (IOException e) {
			outPut("!!! bbt file Create Error !!!", true, false);
		}// 测试创建文件
			// ---------------------------------------------------------------------------------------
	}

	// ====================================================================================
	public static void s(int str) {
		outPut(str + "", false, false);
	}

	public static void s(long str) {
		outPut(str + "", false, false);
	}

	public static void s(int[] list) {
		String str = "";
		for (int i = 0; i < list.length; i++) {
			str += list[i] + ",";
		}
		outPut(str + "", false, false);
	}

	public static void s(String str) {
		outPut(str, false, false);
	}

	public static void sl(int i) {
		outPut("------------------------------------------" + i, false, false);
	}

	public static void s(StringBuffer str, boolean MsgKey, boolean FileKey) {
		outPut(str.toString(), MsgKey, FileKey);
	}

	public static void s(int num, boolean MsgKey, boolean FileKey) {
		outPut(num + "", MsgKey, FileKey);
	}

	public static void s(String str, boolean MsgKey, boolean FileKey) {
		outPut(str, false, false);
	}

	// ====================================================================================
	public static void slist(String[] s ) {
		String str = "";
		for (int i = 0; i < s.length; i++) {
			str += s[i] + ",";
		}
		outPut(str + "", false, false);
	}
	
	public static void slist(Float... array) {
		String str = "";
		List<Float> list = Arrays.asList(array);
		for (int i = 0; i < list.size(); i++) {
			str += list.get(i) + ",";
		}
		outPut(str + "", false, false);
	}

	public static void slist(Integer... array) {
		String str = "";
		List<Integer> list = Arrays.asList(array);
		for (int i = 0; i < list.size(); i++) {
			str += list.get(i) + ",";
		}
		outPut(str + "", false, false);
	}

	// ====================================================================================
	public static void outPut(String str, boolean MsgKey, boolean FileKey) {
		// -----------------------------------------------------------------------------------
		System.out.println("&&& " + str);
		// -----------------------------------------------------------------------------------
		if (MsgKey) {
			m(str);
		}
		// -----------------------------------------------------------------------------------
		if (FileKey) {
			timePoi = new Date(System.currentTimeMillis());
			app(BBK_Tool_DateTime.Get_DateTime_String(timePoi) + " === " + str);
		}
		// -----------------------------------------------------------------------------------
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
		outPut("--------------t0----------------", MsgKey, FileKey);
	}

	public static void ttt(String str, boolean MsgKey, boolean FileKey) {
		t1 = new Date(System.currentTimeMillis());
		tt = t1.getTime() - t0.getTime();
		t0 = new Date(System.currentTimeMillis());
		outPut(str + " = " + tt, MsgKey, FileKey);
	}

	// ====================================================================================
	// ####################################################################################
	// #############################程序计时器#############################################
	// ####################################################################################
	// ====================================================================================

	private static Activity act;
	private static Toast toast;

	public static void m(String str) {
		if (toast == null) {
			toast = Toast.makeText((Context) act, str, Toast.LENGTH_SHORT);
		} else {
			// toast.cancel();
			toast.setText(str);
		}
		d.s(str);
		toast.show();
	}

}
