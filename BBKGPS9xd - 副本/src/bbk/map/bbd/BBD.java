package bbk.map.bbd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BBD {

	// ====================================================================================
	public boolean MapDownYes = true;

	public boolean MapDownKeyClick(int key) {
		if (key == -1) {
			MapDownYes = !MapDownYes;
		} else if (key == 1) {
			MapDownYes = true;
		} else if (key == 0) {
			MapDownYes = false;
		}
		return MapDownYes;
	}

	// ====================================================================================

	// ====================================================================================
	public Bitmap ReadAllDirsPic(Pt p, boolean downkey) {
		// ----------------------------------------------------------------
		// Image img = null;
		Bitmap img = null;
		// ----------------------------------------------------------------
		for (int i = 0; i < BBDDirPaths.size(); i++) {
			// ------------------------------------------------------------
			try {
				// --------------------------------------------------------
				byte[] bd = myBBDR.ReadBBDPicBytes(BBDDirPaths.get(i), p, true);
				// --------------------------------------------------------
				if (bd != null) {
					// ByteArrayInputStream bais = new ByteArrayInputStream(bd);
					// img = new Image(null, bais);
					// --------------------------------------------------------
					img = BitmapFactory.decodeByteArray(bd, 0, bd.length);
					// --------------------------------------------------------
				} else {
					img = null;
				}
				// --------------------------------------------------------
			} catch (Exception e) {
			}
			// ------------------------------------------------------------
			if (img != null) {
				i = 999999;
				return img;
			}
			// ------------------------------------------------------------
		}
		// ----------------------------------------------------------------
		if (downkey && MapDownYes) {
			myBBDH.MapTempFileDown(p);
		}
		return null;
		// ----------------------------------------------------------------
	}

	public void MapPicDownPt(Pt p) {
		myBBDH.MapTempFileDown(p);
	}

	// ====================================================================================
	private String tempPath;
	private List<String> BBDDirPaths;
	// ====================================================================================
	private static BBDRead myBBDR = new BBDRead();
	private static BBDHttp myBBDH = new BBDHttp();

	// ====================================================================================

	// ====================================================================================
	public void BBDInt(String filePath) {
		// ---------------------------------------------------
		tempPath = filePath;
		BBDPathDirMake(tempPath);
		// ---------------------------------------------------
		BBDDirPaths = getFileDirs(filePath);
		myBBDH.BBDHInt(filePath);
		// ---------------------------------------------------
	}

	public void BBDExit() {
		// ---------------------------------------------------
		myBBDH.BBDExit();
		// ---------------------------------------------------
	}

	private void BBDPathDirMake(String filePath) {
		// ---------------------------------------------------
		File mapFileDir = new File(filePath);
		if (!mapFileDir.exists()) {
			mapFileDir.mkdirs();
		}
		mapFileDir = null;
		// ---------------------------------------------------
	}

	// ====================================================================================
	private List<String> getFileDirs(String filePath) {// 多目录路径获取
		// ----------------------------------------------
		List<String> ret = new ArrayList<String>();
		ret.add(filePath);
		// ----------------------------------------------
		try {
			// ----------------------------------------------
			File f = new File(filePath);
			File[] files = f.listFiles();
			// ----------------------------------------------
			if (files != null) {
				int fileslength = files.length;
				for (int i = 0; i < fileslength; i++) {
					if (files[i].isDirectory()) {
						ret.add(files[i].getPath() + "/");
					}
				}
			}
			// ----------------------------------------------
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
		// ----------------------------------------------
	}

	// ====================================================================================

	// public final static String picExtension = ".png";
	// public final static String bbdExtension = ".bbd";

	// P点定义==========================================================
	public static class Pt {
		// ----------------------------------------------------------
		private final static String picExtension = ".png";
		private final static String bbdExtension = ".bbd";
		// ----------------------------------------------------------
		public int x, y, z, t, a, b, xd, yd, sq, f;
		public String nme, url, dir, bbd, bbdname, fname;

		public Pt(int XX, int YY, int ZZ, int TT) {
			// ------------------------------------------------------
			SetPt(XX, YY, ZZ, TT);
			// ------------------------------------------------------
		}

		public void SetPt(int XX, int YY, int ZZ, int TT) {
			// ------------------------------------------------------
			x = XX;
			y = YY;
			z = ZZ;
			t = TT;
			// ------------------------------------------------------
			sq = 100;
			xd = (int) (Math.floor(x / sq));
			yd = (int) (Math.floor(y / sq));
			a = x % sq;
			b = y % sq;
			f = (a + b * sq) * 8;
			// ------------------------------------------------------
			fname = t + "_" + z + "_" + x + "_" + y;
			bbdname = t + "_" + z + "_" + xd + "_" + yd;
			url = "x=" + x + "&y=" + y + "&z=" + z;
			dir = bbdname + "/";
			bbd = bbdname + bbdExtension;
			nme = fname + picExtension;
			// ------------------------------------------------------
		}
		// ----------------------------------------------------------
	}
	// ====================================================================================
}