package bbk.sys.abc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;
import bbk.zzz.debug.BBKDebug;

public class BBKSdCard {

	// ====================================================================================
	private static String SdPath = "";
	private static String extSd[] = { "sdcard0", "sdcard1", "sdcard2", "extSdCard", "external_sd" };

	public static String BBKSoftPathRead() {
		String sss = BBKSYS.FileRead(FirstPathName());
		if (sss == null || sss.length() == 0) {
			sss = android.os.Environment.getExternalStorageDirectory().getPath();
			sss += "/!BBK/";
			CheckMakeDir(sss);
		}
		return sss;
	}

	public static void BBKSoftPathSave(String softpath) {
		String sss = FirstPathName();
		BBKSYS.FileSave(sss, softpath);
	}

	private static String FirstPathName() {
		SdPath = android.os.Environment.getExternalStorageDirectory().getPath();
		CheckMakeDir(SdPath);
		return SdPath;
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################SD卡路径设置##########################################
	// ####################################################################################
	// ====================================================================================
	public static String BBKSdCardGet() {
		// ----------------------------------------------------
		BBKSdCardSet();
		SdPath = android.os.Environment.getExternalStorageDirectory().getPath();
		// GetExtSdCardPath(SdPath);
		return SdPath;
		// ----------------------------------------------------
	}

	private static boolean BBKSdCardSet() {
		// ----------------------------------------------------
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			BBKDebug.d("BBKSdCard.BBKSdCardSet = SD Card 不可用", false, true);
			return false;
		} else {
			BBKDebug.d("BBKSdCard.BBKSdCardSet = SD Card 可用", false, true);
		}
		// ----------------------------------------------------
		try {
			SdPath = Environment.getExternalStorageDirectory().getCanonicalPath();
			BBKDebug.d("BBKSdCard.BBKSdCardSet.getCanonicalPath = " + SdPath, false, true);
		} catch (IOException e) {
			BBKDebug.d("BBKSdCard.BBKSdCardSet.getCanonicalPath.err = " + e.toString(), false, true);
		}
		// ----------------------------------------------------
		return true;
		// ----------------------------------------------------
	}

	private static boolean GetExtSdCardPath(String path) {
		// ----------------------------------------------------
		File fs = new File(path);
		String ps = fs.getParent();
		// ----------------------------------------------------
		BBKDebug.d("BBKSdCard.getParent = " + ps, false, true);
		// ----------------------------------------------------
		fs = new File(ps);
		// ----------------------------------------------------
		File files[] = fs.listFiles();
		for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
			// ----------------------------------------------------
			ps = files[i].getPath();
			BBKDebug.d("BBKSdCard.getParent = " + ps, false, true);
			if (CheckExtSdCardPaths(ps)) {
				SdPath = ps;
				BBKDebug.d("BBKSdCard.getParent = " + SdPath, false, true);
				return true;
			}
			if (i == files.length - 1) {
				SdPath = ps;
				BBKDebug.d("BBKSdCard.getParent = " + SdPath, false, true);
				return true;
			}
			// ----------------------------------------------------
		}
		// ----------------------------------------------------
		return false;
		// ----------------------------------------------------
	}

	private static boolean CheckExtSdCardPaths(String path) {
		// ----------------------------------------------------
		for (int i = 0; i < extSd.length; i++) {
			// ----------------------------------------------------
			if (path.indexOf(extSd[i]) > -1) {
				SdPath = path;
				return true;
			}
			// ----------------------------------------------------
		}
		// ----------------------------------------------------
		return false;
		// ----------------------------------------------------
	}

	public static void CheckMakeDir(String DirPath) {
		// ----------------------------------------------------
		File FileDir = new File(DirPath);
		if (!FileDir.exists()) {
			FileDir.mkdirs();
			BBKDebug.d("BBKSdCard.CheckMakeDir = " + DirPath, false, true);
		}
		// ----------------------------------------------------
		FileDir = null;
		// ----------------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	public static String BBKDataPath(Context ctx) {
		// ----------------------------------------------------
		File fileRoot = ctx.getFilesDir();// +"\";
		SdPath = fileRoot.getPath();// + "/";
		return SdPath;
		// ----------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################程序路径设置##########################################
	// ####################################################################################
	// ====================================================================================

	/**
	 * 获取外置SD卡路径
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getSDCardPath() {
		String cmd = "cat /proc/mounts";
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				// 获得命令执行后在控制台的输出信息
				BBKDebug.d("BBKSdCard.getSDCardPath = " + lineStr, false, true);
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
					BBKDebug.d("BBKSdCard.getSDCardPath.159 = " + "命令执行失败!", false, true);
				}
			}
			inBr.close();
			in.close();
		} catch (Exception e) {
			BBKDebug.d("BBKSdCard.getSDCardPath.159 = " + e.toString(), false, true);
			return Environment.getExternalStorageDirectory().getPath();
		}
		return Environment.getExternalStorageDirectory().getPath();
	}
}
