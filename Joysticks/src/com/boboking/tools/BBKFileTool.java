package com.boboking.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BBKFileTool {

	// =========================================================================================
	public static boolean FileSave(String PathName, String filestr) {
		// -----------------------------------------------------------------------
		try {
			FileWriter fw = new FileWriter(PathName, false);
			fw.write(filestr);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			d.s("BBKSYS.FileSave.ERROR " + e.toString(), false, true);
			return false;
		}
		// -----------------------------------------------------------------------
	}

	// =========================================================================================
	public static String FileRead(String PathName) {
		// -----------------------------------------------------------------------------
		String str = null;
		// -----------------------------------------------------------------------------
		try {
			File file = new File(PathName);
			FileInputStream fins = new FileInputStream(file);
			ByteArrayOutputStream bouts = new ByteArrayOutputStream();
			// -----------------------------------------------------------------------------
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = fins.read(buf)) != -1) {
				bouts.write(buf, 0, len);
			}
			// -----------------------------------------------------------------------------
			byte[] data = bouts.toByteArray();
			// -----------------------------------------------------------------------------
			fins.close();
			bouts.close();
			// -----------------------------------------------------------------------------
			str = new String(data);
			// -----------------------------------------------------------------------------
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// -----------------------------------------------------------------------------
		return str;
		// -----------------------------------------------------------------------------
	}

	// =========================================================================================
	public static boolean FileReName(String Path, String OldName, String NewName) {
		// -----------------------------------------------------------------------------
		File file = new File(Path + OldName);
		if (!file.exists())
			return false;
		// -----------------------------------------------------------------------------
		File newfile = new File(Path + NewName);
		return file.renameTo(newfile);
		// -----------------------------------------------------------------------------
	}

	// =========================================================================================
	public static boolean FileDel(String Path, String Name) {
		// -----------------------------------------------------------------------------
		File file = new File(Path + Name);
		if (!file.exists())
			return false;
		// -----------------------------------------------------------------------------
		return file.delete();
		// -----------------------------------------------------------------------------
	}

	// =========================================================================================
	public static String FileGetExte(String fileNameString) {
		// -----------------------------------------------------------------------------
		int start = fileNameString.lastIndexOf(".") + 1;
		int end = fileNameString.length();
		String endNameString = fileNameString.substring(start, end);
		endNameString = endNameString.toLowerCase(Locale.US);
		// -----------------------------------------------------------------------------
		return endNameString;
		// -----------------------------------------------------------------------------
	}

	public static void CheckMakeDir(String DirPath) {
		// ----------------------------------------------------
		File FileDir = new File(DirPath);
		if (!FileDir.exists()) {
			FileDir.mkdirs();
			d.s("BBKSdCard.CheckMakeDir = " + DirPath, false, true);
		}
		// ----------------------------------------------------
		FileDir = null;
		// ----------------------------------------------------
	}

	public static List<String> GetFiles(String Path, String Extension, boolean IsIterative, boolean IsFileName, String type) { // ����Ŀ¼����չ�����Ƿ�������ļ��У��Ƿ����·��
		// ----------------------------------------------------
		List<String> dirsList = new ArrayList<String>();
		List<String> fileList = new ArrayList<String>();
		// ----------------------------------------------------
		File[] files = new File(Path).listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isFile()) {
				if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)) // �ж���չ��
					fileList.add(IsFileName ? f.getName() : f.getPath());
			} else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) // ���Ե��ļ��������ļ�/�ļ��У�
			{
				dirsList.add(IsFileName ? f.getName() : f.getPath());
				if (IsIterative)
					GetFiles(f.getPath(), Extension, IsIterative, IsFileName, type);
			}
		}
		// ----------------------------------------------------
		if (type.toLowerCase(Locale.ENGLISH).indexOf("dir") < 0)
			return fileList;
		else
			return dirsList;
		// ----------------------------------------------------
	}

}
