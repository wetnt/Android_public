package bbk.sys.abc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import bbk.zzz.debug.bd;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class BBKSdCard2 {

	// public static void Test() {
	//
	// String PhoneCard = BBKSdCard2.getPhoneCardPath();// 手机内部
	// String SDCard = BBKSdCard2.getSDCardPath();// 手机内置SDcard
	// String NormalSDCard = BBKSdCard2.getNormalSDCardPath();// 默认SDcard
	// String SDCardPathEx = BBKSdCard2.getSDCardPathEx();// 所有card
	//
	// long PhoneCardSize = BBKSdCard2.getAvailableSize(PhoneCard);
	// long SDCardSize = BBKSdCard2.getAvailableSize(SDCard);
	// long NormalSDCardSize = BBKSdCard2.getAvailableSize(NormalSDCard);
	//
	// BBKDebug.d(PhoneCard + "=" + PhoneCardSize, false, false);
	// BBKDebug.d(SDCard + "=" + SDCardSize, false, false);
	// BBKDebug.d(NormalSDCard + "=" + NormalSDCardSize, false, false);
	//
	// BBKDebug.d("--------------------", false, false);
	//
	// String columns[] = SDCardPathEx.split("\n");
	// for (int i = 0; i < columns.length; i++) {
	// BBKDebug.d(columns[i], false, false);
	// }
	// }

	private static String[] PathListMB;
	private static ArrayList<String> PathList = new ArrayList<String>();

	public static String[] GetAllSdPath() {
		bd.d("GetAllSdPath", false, false);
		// ---------------------------------------------------------------------
		PathList.clear();
		getSDCardPathExternal(PathList);// 所有card
		StringArrayAdd(PathList, BBKSdCard2.getPhoneCardPath());// 手机内部
		StringArrayAdd(PathList, BBKSdCard2.getSDCardPath());// 手机内置SDcard
		StringArrayAdd(PathList, BBKSdCard2.getNormalSDCardPath());// 默认SDcard
		Collections.sort(PathList);
		// ---------------------------------------------------------------------
		PathListMB = new String[PathList.size()];
		for (int i = 0; i < PathList.size(); i++) {
			String x = PathList.get(i) + " " + (int) (BBKSdCard2.getAvailableSize(PathList.get(i)) / 1048576) + "MB";
			bd.d(x, false, false);
			PathListMB[i] = x;
		}
		// ---------------------------------------------------------------------
		return PathListMB;
		// ---------------------------------------------------------------------
	}

	// 查看所有的sd路径
	private static void getSDCardPathExternal(ArrayList<String> list) {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;
				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						StringArrayAdd(list, columns[1]);
					}
				} else if (line.contains("fuse")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						StringArrayAdd(list, columns[1]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean StringArrayAdd(ArrayList<String> list, String str) {
		try {
			// -------------------------------------------------------
			boolean isInString = false;
			// -------------------------------------------------------
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).endsWith(str))
					isInString = true;
			}
			// -------------------------------------------------------
			if (isInString)
				return true;
			// -------------------------------------------------------
			list.add(str);
			return true;
			// -------------------------------------------------------
		} catch (Exception e) {
			return false;
		}
	}

	// 获取最后一个SDcard
	public static String SdPathAll[];

	public static String getLastSdCardPath() {
		// -------------------------------------------------------
		String SDCardPath = "";
		// -------------------------------------------------------
		String SDCardPathEx = BBKSdCard2.getSDCardPathEx();// 所有card
		SdPathAll = SDCardPathEx.split("\n");
		// -------------------------------------------------------
		for (int i = 0; i < SdPathAll.length; i++) {
			bd.d(SdPathAll[i], false, false);
		}
		// -------------------------------------------------------
		if (SdPathAll.length > 1)
			SDCardPath = SdPathAll[SdPathAll.length - 1];
		// -------------------------------------------------------
		return SDCardPath;
		// -------------------------------------------------------
	}

	// 获取手机自身内存路径
	public static String getPhoneCardPath() {
		return Environment.getDataDirectory().getPath();

	}

	// 获取sd卡路径 双sd卡时，根据”设置“里面的数据存储位置选择，获得的是内置sd卡或外置sd卡
	public static String getNormalSDCardPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	// 获取sd卡路径 双sd卡时，获得的是外置sd卡
	public static String getSDCardPath() {
		String cmd = "cat /proc/mounts";
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
		BufferedInputStream in = null;
		BufferedReader inBr = null;
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			in = new BufferedInputStream(p.getInputStream());
			inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				// 获得命令执行后在控制台的输出信息
				Log.i("CommonUtil:getSDCardPath", lineStr);
				if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
					String[] strArray = lineStr.split(" ");
					if (strArray != null && strArray.length >= 5) {
						String result = strArray[1].replace("/.android_secure", "");
						return result;
					}
				}
				// 检查命令是否执行失败。
				if (p.waitFor() != 0 && p.exitValue() == 1) {
					// p.exitValue()==0表示正常结束，1：非正常结束
					Log.e("CommonUtil:getSDCardPath", "命令执行失败!");
				}
			}
		} catch (Exception e) {
			Log.e("CommonUtil:getSDCardPath", e.toString());
			// return Environment.getExternalStorageDirectory().getPath();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (inBr != null) {
					inBr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Environment.getExternalStorageDirectory().getPath();
	}

	// 查看所有的sd路径
	public static String getSDCardPathEx() {
		String mount = new String();
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;
				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						// mount = mount.concat("*" + columns[1] + "\n");
					}
				} else if (line.contains("fuse")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat(columns[1] + "\n");
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mount;
	}

	// 获取当前路径，可用空间
	public static long getAvailableSize(String path) {
		try {
			File base = new File(path);
			StatFs stat = new StatFs(base.getPath());
			long nAvailableCount = stat.getBlockSize() * ((long) stat.getAvailableBlocks());
			return nAvailableCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
