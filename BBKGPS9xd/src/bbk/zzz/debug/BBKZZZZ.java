package bbk.zzz.debug;

public class BBKZZZZ {

	// =========================================================================================
	// public static Lay_type ListAdds(String dirPath, String ext) {
	// //
	// -----------------------------------------------------------------------------
	// double filesize = 0;
	// String strFile = "";
	// File[] files;
	// //
	// -----------------------------------------------------------------------------
	// String sDStateString = Environment.getExternalStorageState();
	// //
	// -----------------------------------------------------------------------------
	// if (sDStateString.equals(Environment.MEDIA_MOUNTED)) {
	// try {
	// //
	// -----------------------------------------------------------------------------
	// Lay_type bbp = new Lay_type();
	// //
	// -----------------------------------------------------------------------------
	// File sdPath = new File(dirPath);
	// files = sdPath.listFiles(getFileExtensionFilter(ext));
	// //
	// -----------------------------------------------------------------------------
	// Arrays.sort(files);
	// // Arrays.sort(files, new FileUtils.CompratorBySize());
	// // Arrays.sort(files, new FileUtils.CompratorByLastModified());
	// //
	// -----------------------------------------------------------------------------
	// int fl = files.length;
	// File[] filet = new File[fl];
	// for (int i = 0; i < fl; i++) {
	// filet[fl - i - 1] = files[i];
	// }
	// files = filet;
	// // Log.d("BBKSYS", "===41");
	// //
	// -----------------------------------------------------------------------------
	// if (files.length > 0) {
	// //
	// -----------------------------------------------------------------------------
	// StringBuffer name, pics, strs;
	// //
	// -----------------------------------------------------------------------------
	// for (File file : files) {
	// // ---------------------------------------------------------------------
	// if (file.length() > 0) {
	// filesize = (int) file.length() * 100 / 1024;
	// strFile = filesize / 100 + " KB";
	// // -----------------------------------------------------------------
	// name = new StringBuffer(file.getName().replace(ext, ""));
	// pics = new StringBuffer("0");
	// strs = new StringBuffer(strFile);
	// poi_type p = new poi_type(name, pics, strs, 0, 0, 0, null);
	// bbp.pois.add(p);
	// // -----------------------------------------------------------------
	// } else {
	// // -----------------------------------------------------------------
	// try {
	// file.delete();
	// } catch (Exception e) {
	// }
	// // -----------------------------------------------------------------
	// }
	// }
	// //
	// -------------------------------------------------------------------------
	// }
	// //
	// -------------------------------------------------------------------------
	// return bbp;
	// //
	// -------------------------------------------------------------------------
	// } catch (Exception e) {
	// return null;
	// }
	// }
	// //
	// -----------------------------------------------------------------------------
	// return null;
	// //
	// -----------------------------------------------------------------------------
	// }

	// -----------------------------------------------------------------------------
	// File[] zipFiles = sdPath.listFiles(getFileExtensionFilter(".zip"));
	// Collections.sort(zipFiles);
	// public static FilenameFilter getFileExtensionFilter(String extension) {
	// final String _extension = extension;
	// return new FilenameFilter() {
	// public boolean accept(File file, String name) {
	// boolean ret = name.endsWith(_extension);
	// return ret;
	// }
	// };
	// }

	// ====================================================================================
	// ####################################################################################
	// ##############################加权递推平均滤波法#####################################
	// ####################################################################################
	// ====================================================================================
	// 加权递推平均滤波法
	// private static final int FILTER_N = 12;// MAX=12;
	// private static int coe[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }; //
	// 加权系数表

	private static final int FILTER_N = 5;// MAX=12;
	private static int coe[] = { 2, 6, 10, 14, 18 }; // 加权系数表

	private static int filter_buf[] = new int[FILTER_N + 1];

	private static int sum_coe() {
		int rad = 0;
		for (int i = 0; i < FILTER_N; i++) {
			rad += coe[i];
		}
		return rad;
		// return (FILTER_N + 1) * FILTER_N / 2;
		// private static int sum_coe = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8;
		// + 9 + 10 + 11 + 12; // 加权系数和
	}

	public static int Filter(int nowAD) {
		int i;
		int filter_sum = 0;
		filter_buf[FILTER_N] = nowAD;
		for (i = 0; i < FILTER_N; i++) {
			filter_buf[i] = filter_buf[i + 1]; // 所有数据左移，低位仍掉
			filter_sum += filter_buf[i] * coe[i];
		}
		filter_sum /= sum_coe();
		return filter_sum;
	}
	// ====================================================================================
	// ####################################################################################
	// ##############################加权递推平均滤波法#####################################
	// ####################################################################################
	// ====================================================================================

}