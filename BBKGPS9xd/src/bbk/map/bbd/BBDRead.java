package bbk.map.bbd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import bbk.map.bbd.BBD.Pt;

public class BBDRead {

	// ====================================================================================
	private byte[] bd = new byte[102400];

	public byte[] ReadBBDPicBytes(String Path, Pt P, boolean readold) {
		// --------------------------------------------------------
		try {
			// --------------------------------------------------------
			File fbd = null;
			RandomAccessFile fin = null;
			// --------------------------------------------------------
			fbd = new File(Path + P.bbd);
			if (!fbd.exists()) {// BBD存在检测
				return null;
			}
			// --------------------------------------------------------
			fin = new RandomAccessFile(Path + P.bbd, "r");
			if (P.f + 8 > fbd.length()) {// 文件头偏移检测
				fin.close();
				return null;
			}
			// --------------------------------------------------------
			fin.seek(P.f);
			// --------------------------------------------------------
			long s = 0, l = 0;
			if (readold) {
				// ----------------------------------------------------
				byte[] bufs = new byte[4];
				byte[] bufl = new byte[4];
				// ----------------------------------------------------
				if (fin.read(bufs) != 4) {
					fin.close();
					return null;
				}
				if (fin.read(bufl) != 4) {
					fin.close();
					return null;
				}
				// ----------------------------------------------------
				s = byteToInt(bufs);
				l = byteToInt(bufl);
				// ----------------------------------------------------
			} else {
				// ----------------------------------------------------
				s = fin.readInt();
				l = fin.readInt();
				// ----------------------------------------------------
			}
			// --------------------------------------------------------
			if (s < 80000) {// 文件长度检测
				fin.close();
				return null;
			}
			// --------------------------------------------------------
			if (l < 100) {// 文件长度检测
				fin.close();
				return null;
			}
			// --------------------------------------------------------
			if (s + l > fbd.length()) {// 偏移长度检测
				fin.close();
				return null;
			}
			// --------------------------------------------------------
			fin.seek(s);
			// --------------------------------------------------------
			try {// 文件流读取
				fin.read(bd, 0, (int) l);
			} catch (Exception e) {
				fin.close();
				return null;
			}
			// --------------------------------------------------------
			fin.close();
			fin = null;
			fbd = null;
			// --------------------------------------------------------
			return bd;
			// --------------------------------------------------------
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		// --------------------------------------------------------
		return null;
		// --------------------------------------------------------
	}

	// ====================================================================================
	// http://blog.csdn.net/veryitman/article/details/6430797
	// http://wenku.baidu.com/view/61a4823b580216fc700afd22.html
	// ====================================================================================
	public int byteToInt(byte[] buf) {
		// --------------------------------------------------------
		int l = ((buf[3] & 255) << 24) | ((buf[2] & 255) << 16) | ((buf[1] & 255) << 8) | ((buf[0] & 255) << 0);
		return l;
		// --------------------------------------------------------
	}

	// ====================================================================================
	public static long byteToLong(byte[] buffer) {
		long l = 0;
		// 最低位
		long l0 = buffer[7] & 0xff;
		long l1 = buffer[6] & 0xff;
		long l2 = buffer[5] & 0xff;
		long l3 = buffer[4] & 0xff;
		long l4 = buffer[3] & 0xff;
		long l5 = buffer[2] & 0xff;
		long l6 = buffer[1] & 0xff;
		// 最高位
		long l7 = buffer[0] & 0xff;
		// l0不变
		l1 <<= 8;
		l2 <<= 16;
		l3 <<= 24;
		l4 <<= 32;
		l5 <<= 40;
		l6 <<= 48;
		l7 <<= 56;
		l = l0 | l1 | l2 | l3 | l4 | l5 | l6 | l7;
		return l;
	}
	// ====================================================================================
}