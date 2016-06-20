package bbk.sys.abc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;
import bbk.zzz.debug.bd;

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
	// ##############################SD��·������##########################################
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
			bd.d("BBKSdCard.BBKSdCardSet = SD Card ������", false, true);
			return false;
		} else {
			bd.d("BBKSdCard.BBKSdCardSet = SD Card ����", false, true);
		}
		// ----------------------------------------------------
		try {
			SdPath = Environment.getExternalStorageDirectory().getCanonicalPath();
			bd.d("BBKSdCard.BBKSdCardSet.getCanonicalPath = " + SdPath, false, true);
		} catch (IOException e) {
			bd.d("BBKSdCard.BBKSdCardSet.getCanonicalPath.err = " + e.toString(), false, true);
		}
		// ----------------------------------------------------
		return true;
		// ----------------------------------------------------
	}

	@SuppressWarnings("unused")
	private static boolean GetExtSdCardPath(String path) {
		// ----------------------------------------------------
		File fs = new File(path);
		String ps = fs.getParent();
		// ----------------------------------------------------
		bd.d("BBKSdCard.getParent = " + ps, false, true);
		// ----------------------------------------------------
		fs = new File(ps);
		// ----------------------------------------------------
		File files[] = fs.listFiles();
		for (int i = 0; i < files.length; i++) {// ����Ŀ¼�����е��ļ�
			// ----------------------------------------------------
			ps = files[i].getPath();
			bd.d("BBKSdCard.getParent = " + ps, false, true);
			if (CheckExtSdCardPaths(ps)) {
				SdPath = ps;
				bd.d("BBKSdCard.getParent = " + SdPath, false, true);
				return true;
			}
			if (i == files.length - 1) {
				SdPath = ps;
				bd.d("BBKSdCard.getParent = " + SdPath, false, true);
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
			bd.d("BBKSdCard.CheckMakeDir = " + DirPath, false, true);
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
	// ##############################����·������##########################################
	// ####################################################################################
	// ====================================================================================

	/**
	 * ��ȡ����SD��·��
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getSDCardPath() {
		String cmd = "cat /proc/mounts";
		Runtime run = Runtime.getRuntime();// �����뵱ǰ Java Ӧ�ó�����ص�����ʱ����
		try {
			Process p = run.exec(cmd);// ������һ��������ִ������
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				// �������ִ�к��ڿ���̨�������Ϣ
				bd.d("BBKSdCard.getSDCardPath = " + lineStr, false, true);
				if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
					String[] strArray = lineStr.split(" ");
					if (strArray != null && strArray.length >= 5) {
						String result = strArray[1].replace("/.android_secure", "");
						return result;
					}
				}
				// ��������Ƿ�ִ��ʧ�ܡ�
				if (p.waitFor() != 0 && p.exitValue() == 1) {
					// p.exitValue()==0��ʾ����������1������������
					bd.d("BBKSdCard.getSDCardPath.159 = " + "����ִ��ʧ��!", false, true);
				}
			}
			inBr.close();
			in.close();
		} catch (Exception e) {
			bd.d("BBKSdCard.getSDCardPath.159 = " + e.toString(), false, true);
			return Environment.getExternalStorageDirectory().getPath();
		}
		return Environment.getExternalStorageDirectory().getPath();
	}

	public static void gettttt() {
		try {
			// -------------------------------------------------------
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			String mount = new String();
			// -------------------------------------------------------
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				// -------------------------------------------------------
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;
				// -------------------------------------------------------
				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat("*" + columns[1] + "\n");
					}
				} else if (line.contains("fuse")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat(columns[1] + "\n");
					}
				}

			}

			String columns[] = mount.split("\n");

			for (int i = 0; i < columns.length; i++) {
				bd.d(columns[i], false, false);
			}

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
}
