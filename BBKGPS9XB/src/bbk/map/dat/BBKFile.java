package bbk.map.dat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import bbk.bbk.box.BBKSoft;
import bbk.map.data.shapefile.BBKShapeToBBKLay;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.sys.abc.BBKMsgBox;
import bbk.sys.abc.BBKSYS;
import bbk.uis.view.BBKListView;
import bbk.zzz.debug.BBKDebug;

public class BBKFile {

	// =========================================================================================
	public static File BBKLoadFile;
	public static String BBKLoadFilePathName = "";
	public static String BBKLoadFileExtension = "";

	// =========================================================================================
	public static void BBKFileAdd(File file) {
		// -----------------------------------------------------
		BBKLoadFile = file;
		BBKLoadFilePathName = file.getPath();
		BBKLoadFileExtension = BBKSYS.FileGetExte(BBKLoadFilePathName);
		// -----------------------------------------------------
		BBKFileRun(file, BBKLoadFilePathName, BBKLoadFileExtension);
		// -----------------------------------------------------
	}

	public static void BBKFileRun(File file, String pathname, String Exte) {
		// -----------------------------------------------------
		boolean loadkey = false;
		BBKDebug.d(pathname, false, false);
		BBKDebug.d(Exte, false, false);
		// -----------------------------------------------------
		if (Exte.equals("bbt")) {
			BBKSoft.myLays.laytmp = BBKBBT.BBTtoLay_type(pathname, false);
			loadkey = true;
		}
		// -----------------------------------------------------
		if (Exte.equals("bjs")) {
			String pn = pathname.replace(".bjs", "");
			BBKSoft.myLays.LayLoad(BBKSoft.myLays.laytmp, pn);
			loadkey = true;
		}
		// -----------------------------------------------------
		if (Exte.equals("kml")) {
			// int n = BBKSoft.myLays.laytmp.pois.size();
			// BBKDebug.d(n, false, false);
			// loadkey =true;
		}
		// -----------------------------------------------------
		if (Exte.equals("shp")) {
			// -----------------------------------------------------
			String fpn = pathname.replace(".shp", "");
			BBKSoft.myLays.laytmp = BBKShapeToBBKLay.BBKShapeFileLoad(fpn, true);
			loadkey = true;
			// -----------------------------------------------------
		}
		// -----------------------------------------------------
		if (loadkey) {
			// -----------------------------------------------------
			ListViewShow(BBKSoft.myLays.laytmp, pathname);
			BBKSoft.MapToLayCenter(BBKSoft.myLays.laytmp);
			BBKSoft.MapFlash(true);
			// -----------------------------------------------------
		} else {
			// -----------------------------------------------------
			String t = "	真抱歉，\"" + Exte + "\" 此格式不支持！\r\n\r\n" + pathname;
			BBKMsgBox.tShow(t);
			// -----------------------------------------------------
		}
		// -----------------------------------------------------
	}

	private static void ListViewShow(Lay_type lay, String pathname) {
		// ---------------------------------------------------------
		ArrayList<HashMap<String, Object>> lt;
		lt = BBKListView.BBKLayToArrayList(lay, true, true, false);
		BBKSoft.myList.ListLayAdd(lt, pathname);
		// ---------------------------------------------------------
	}
	// =========================================================================================
}