package bbk.sys.abc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import bbk.zzz.debug.bd;

@SuppressLint("SimpleDateFormat")
public class BBKSYS {

	// =========================================================================================
	// =========================================================================================
	public static final SimpleDateFormat tfmt = new SimpleDateFormat("yyyyMMdd_HHmmss");
	public static final String poiTmFmt = "yyyyMMdd_HHmmss";
	public static final SimpleDateFormat poiTmFt = new SimpleDateFormat(poiTmFmt);
	public static final String bbtTmFts = "yyyyMMddHHmmss";
	public static final SimpleDateFormat bbtTmFt = new SimpleDateFormat(bbtTmFts);

	// =========================================================================================
	public static String PoiFirstName() {
		Date dt = new Date(System.currentTimeMillis());
		return tfmt.format(dt);
	}

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
			bd.d("BBKSYS.FileSave.ERROR " + e.toString(), false, true);
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

	// =========================================================================================
	// public Date StrToDate(String str) {
	// Log.d("dsf", str);
	// // --------------------------------------------
	// Date tt = new Date(System.currentTimeMillis());
	// if (str.length() != 15) {
	// return tt;
	// }
	// // --------------------------------------------
	// String yys = str.substring(0, 4);
	// String mms = str.substring(4, 6);
	// String dds = str.substring(6, 8);
	// String hhs = str.substring(9, 11);
	// String mss = str.substring(11, 13);
	// String sss = str.substring(13, 15);
	// // Log.d("dsf", str);
	// // Log.d("dsf", yys);
	// // Log.d("dsf", mms);
	// // Log.d("dsf", dds);
	// // Log.d("dsf", hhs);
	// // Log.d("dsf", mss);
	// // Log.d("dsf", sss);
	// // --------------------------------------------
	// tt.setYear(Integer.parseInt(yys));
	// tt.setMonth(Integer.parseInt(mms));
	// tt.setDate(Integer.parseInt(dds));
	// tt.setHours(Integer.parseInt(hhs));
	// tt.setMinutes(Integer.parseInt(mss));
	// tt.setSeconds(Integer.parseInt(sss));
	// // --------------------------------------------
	// return tt;
	// // --------------------------------------------
	// }

	// =========================================================================================
	// =========================================================================================
	// =========================================================================================
	public static String bbtDirPath, bbtFileName, bbtPathName, log, bbtGpsStr;
	public static final String bbtExtension = ".bbt";
	public static final String bbpExtension = ".bbp";

	// =====================================================================================
	public static String bbtName(String bbtDir) {
		// ---------------------------------------------------------------------------------
		bbtDirPath = bbtDir;
		// ---------------------------------------------------------------------------------
		File bbtFileDir = new File(bbtDirPath);
		if (!bbtFileDir.exists()) {
			bbtFileDir.mkdirs();
		}
		// -------------------------------------------------------------------
		Date startdate = new Date(System.currentTimeMillis());
		bbtFileName = poiTmFt.format(startdate);
		// -------------------------------------------------------------------
		File bbtf = new File(bbtDirPath, bbtFileName + bbtExtension);
		// -------------------------------------------------------------------
		try {
			bbtf.createNewFile();
			bbtPathName = bbtf.getPath();
			bbtf.delete();
		} catch (IOException e) {
			bd.d("BBKSYS.bbtName.createNewFile.ERROR = " + e.toString(), false, true);
		} // 测试创建文件
			// ---------------------------------------------------------------------------------------
		return "";
		// ---------------------------------------------------------------------------------------
	}

	public static void appendSaveBBT(double wd, double jd, double hb, Date dt) {
		// -----------------------------------------------------------------------
		try {
			// -------------------------------------------------------------------
			// 113.00382,28.17377,63,20120216171600
			// -------------------------------------------------------------------
			bbtGpsStr = bbtTmFt.format(dt);
			log = jd + "," + wd + "," + hb + "," + bbtTmFt.format(dt) + "\r\n";
			// -------------------------------------------------------------------
			FileWriter fw = new FileWriter(bbtPathName, true);
			fw.write(log);
			fw.flush();
			fw.close();
			// -------------------------------------------------------------------
		} catch (Exception e) {
		}
		// -----------------------------------------------------------------------
	}

	// =========================================================================================
	public static String StringToUTF8(String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		String xmString = "";
		String xmlUTF8 = "";
		try {
			xmString = new String(sb.toString().getBytes("UTF-8"));
			xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
			System.out.println("utf-8 编码：" + xmlUTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return xmlUTF8;
	}

	// =========================================================================================
	public static String gbToUtf8(String str) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);
			if (s.charAt(0) > 0x80) {
				byte[] bytes = s.getBytes("Unicode");
				String binaryStr = "";
				for (int j = 2; j < bytes.length; j += 2) {
					// the first byte
					String hexStr = getHexString(bytes[j + 1]);
					String binStr = getBinaryString(Integer.valueOf(hexStr, 16));
					binaryStr += binStr;
					// the second byte
					hexStr = getHexString(bytes[j]);
					binStr = getBinaryString(Integer.valueOf(hexStr, 16));
					binaryStr += binStr;
				}
				// convert unicode to utf-8
				String s1 = "1110" + binaryStr.substring(0, 4);
				String s2 = "10" + binaryStr.substring(4, 10);
				String s3 = "10" + binaryStr.substring(10, 16);
				byte[] bs = new byte[3];
				bs[0] = Integer.valueOf(s1, 2).byteValue();
				bs[1] = Integer.valueOf(s2, 2).byteValue();
				bs[2] = Integer.valueOf(s3, 2).byteValue();
				String ss = new String(bs, "UTF-8");
				sb.append(ss);
			} else {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	private static String getHexString(byte b) {
		String hexStr = Integer.toHexString(b);
		int m = hexStr.length();
		if (m < 2) {
			hexStr = "0" + hexStr;
		} else {
			hexStr = hexStr.substring(m - 2);
		}
		return hexStr;
	}

	private static String getBinaryString(int i) {
		String binaryStr = Integer.toBinaryString(i);
		int length = binaryStr.length();
		for (int l = 0; l < 8 - length; l++) {
			binaryStr = "0" + binaryStr;
		}
		return binaryStr;
	}

	// =========================================================================================

	// =========================================================================================
	// ===========================================================================================
	public static class DFSX_type {
		// --------------------------------------------
		public int d = 0, f = 0;// 度分
		public double s = 0.0;// 秒
		public double ddd = 0.0;// DD.DDDDD

		// --------------------------------------------
		public DFSX_type(double _ddd) {
			// --------------------------------------------
			this.ddd = _ddd;// dfs2d(d, f, s);
			// --------------------------------------------
			d = (int) (Math.floor(ddd));
			f = (int) (Math.floor(ddd * 60f - d * 60f));
			s = Math.floor((ddd * 3600f - d * 3600f - f * 60f) * 100) / 100;
			// --------------------------------------------
		}

		// --------------------------------------------
		public void DFStoDD(double dx, double fx, double sx) {
			ddd = dx + fx / 60f + sx / 3600f;
			ddd = getDouble(ddd, 5);
		}
		// -----------------------------------
		// --------------------------------------------
	}

	// ===========================================================================================

	// ===========================================================================================
	public static class FileUtils {
		static class CompratorByLastModified implements Comparator<Object> {
			public int compare(Object o1, Object o2) {
				File file1 = (File) o1;
				File file2 = (File) o2;
				long diff = file1.lastModified() - file2.lastModified();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;
			}

			public boolean equals(Object obj) {
				return true; // 简单做法
			}
		}

		static class CompratorBySize implements Comparator<Object> {
			public int compare(Object o1, Object o2) {
				File file1 = (File) o1;
				File file2 = (File) o2;
				long diff = file1.length() - file2.length();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;
			}

			public boolean equals(Object obj) {
				return true; // 简单做法
			}
		}

	}

	// ===========================================================================================
	public static double getDouble(double a, int n) {
		// -----------------------------------
		int Z = 100000; // (int)Math.pow(10,N);
		// -----------------------------------
		if (n == 1) {
			Z = 10;
		} else if (n == 2) {
			Z = 100;
		} else if (n == 3) {
			Z = 1000;
		} else if (n == 4) {
			Z = 10000;
		} else if (n == 5) {
			Z = 100000;
		} else if (n == 6) {
			Z = 1000000;
		}
		// -----------------------------------
		double a2 = a * Z;
		int b = (int) a2;
		double c = (double) b;
		double d = c / Z;
		// -----------------------------------
		return d;
		// -----------------------------------
	}

	// ===========================================================================================

}
