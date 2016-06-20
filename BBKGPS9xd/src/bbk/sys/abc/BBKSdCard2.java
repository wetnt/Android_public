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
	// String PhoneCard = BBKSdCard2.getPhoneCardPath();// �ֻ��ڲ�
	// String SDCard = BBKSdCard2.getSDCardPath();// �ֻ�����SDcard
	// String NormalSDCard = BBKSdCard2.getNormalSDCardPath();// Ĭ��SDcard
	// String SDCardPathEx = BBKSdCard2.getSDCardPathEx();// ����card
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
		getSDCardPathExternal(PathList);// ����card
		StringArrayAdd(PathList, BBKSdCard2.getPhoneCardPath());// �ֻ��ڲ�
		StringArrayAdd(PathList, BBKSdCard2.getSDCardPath());// �ֻ�����SDcard
		StringArrayAdd(PathList, BBKSdCard2.getNormalSDCardPath());// Ĭ��SDcard
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

	// �鿴���е�sd·��
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

	// ��ȡ���һ��SDcard
	public static String SdPathAll[];

	public static String getLastSdCardPath() {
		// -------------------------------------------------------
		String SDCardPath = "";
		// -------------------------------------------------------
		String SDCardPathEx = BBKSdCard2.getSDCardPathEx();// ����card
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

	// ��ȡ�ֻ������ڴ�·��
	public static String getPhoneCardPath() {
		return Environment.getDataDirectory().getPath();

	}

	// ��ȡsd��·�� ˫sd��ʱ�����ݡ����á���������ݴ洢λ��ѡ�񣬻�õ�������sd��������sd��
	public static String getNormalSDCardPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	// ��ȡsd��·�� ˫sd��ʱ����õ�������sd��
	public static String getSDCardPath() {
		String cmd = "cat /proc/mounts";
		Runtime run = Runtime.getRuntime();// �����뵱ǰ Java Ӧ�ó�����ص�����ʱ����
		BufferedInputStream in = null;
		BufferedReader inBr = null;
		try {
			Process p = run.exec(cmd);// ������һ��������ִ������
			in = new BufferedInputStream(p.getInputStream());
			inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				// �������ִ�к��ڿ���̨�������Ϣ
				Log.i("CommonUtil:getSDCardPath", lineStr);
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
					Log.e("CommonUtil:getSDCardPath", "����ִ��ʧ��!");
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

	// �鿴���е�sd·��
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

	// ��ȡ��ǰ·�������ÿռ�
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
