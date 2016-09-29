package com.zhsk.bbktool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BBK_Tool_File {
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
		d.s("OldName==" + file.getPath());
		if (!file.exists())
			return false;
		// -----------------------------------------------------------------------------
		File newfile = new File(Path + NewName);
		d.s("newfile==" + newfile.getPath());
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
	public static String[] FileGetPathNameExte(String filePathName) {
		// -----------------------------------------------------------------------------
		String[] r = new String[3];
		int p1 = filePathName.lastIndexOf(File.separator);
		int p2 = filePathName.lastIndexOf(".");
		r[0] = filePathName.substring(0, p1);
		r[1] = filePathName.substring(p1 + 1, p2);
		r[2] = filePathName.substring(p2 + 1);
		return r;
		// -----------------------------------------------------------------------------
	}

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

	public static void DirDelete(String DirsPath) {
		File root = new File(DirsPath);
		FileDeleteAllFiles(root);
		root.delete();
	}

	public static void DirDeleteFiles(String DirsPath) {
		FileDeleteAllFiles(new File(DirsPath));
	}

	public static void FileDeleteAllFiles(File root) {
		File files[] = root.listFiles();
		if (files != null)
			for (File f : files) {
				if (f.isDirectory()) { // 判断是否为文件夹
					FileDeleteAllFiles(f);
					try {
						f.delete();
					} catch (Exception e) {
					}
				} else {
					if (f.exists()) { // 判断是否存在
						FileDeleteAllFiles(f);
						try {
							f.delete();
						} catch (Exception e) {
						}
					}
				}
			}
	}

	public static List<String> GetFiles(String Path, String Extension, boolean IsIterative, boolean IsFileName, String type) { // 搜索目录，扩展名，是否进入子文件夹，是否包含路径
		// ----------------------------------------------------
		List<String> dirsList = new ArrayList<String>();
		List<String> fileList = new ArrayList<String>();
		// ----------------------------------------------------
		File[] files = new File(Path).listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isFile()) {
				if (f.getPath().substring(f.getPath().length() - Extension.length()).toLowerCase(Locale.ENGLISH).equals(Extension)) // 判断扩展名					
					fileList.add(IsFileName ? f.getName() : f.getPath());
			} else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) // 忽略点文件（隐藏文件/文件夹）
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
