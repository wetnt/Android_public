package bbk.map.bbd;

import android.os.Handler;
import bbk.bbk.box.BBKSoft;
import bbk.map.abc.BBKMap;
import bbk.map.abc.BBKMap.MapPoiWJ;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.sys.abc.BBKMsgBox;
import bbk.zzz.debug.bd;

public class BBKScreenDown {

	public static void DownScreenMapThead() {
		// ----------------------------------------------------
		int mz = BBKMap.mapzm;
		if (mz < 14) {
			BBKMsgBox.tShow(mz + "级别太大，下载数据量过大！\r\n 请放大地图到14级别以上！");
			return;
		}
		// ----------------------------------------------------
		new Thread() {
			public void run() {
				// --------------------------------------------
				BBKSoft.myTime.TimerStop();
				BBKSoft.myMsg.WaitShow(true);
				DownScreenMap();
				// BBKSoft.SoftThreadExit();
				BBKSoft.myMsg.WaitShow(false);
				// --------------------------------------------
			}
		}.start();
		// ----------------------------------------------------
	}

	// ====================================================================================
	public static double downP, downW, dongJ;
	public static int downZ;

	public static void DownScreenMap() {
		// ---------------------------------------------
		if (BBKSoft.myLays.laytmp == null)
			BBKSoft.myLays.laytmp = new Lay_type();
		BBKSoft.myLays.laytmp.line.clear();
		if (BBKSoft.myLays.laytmp.line.size() < 1)
			BBKSoft.myLays.laytmp.line.add(new line_type());
		// ---------------------------------------------
		double cw = BBKSoft.myMaps.mapPt.w;
		double cj = BBKSoft.myMaps.mapPt.j;
		bd.d("BBKScreenDown.DownScreenMap() cw = " + cw + ", cj = " + cj, false, true);
		// ---------------------------------------------
		int ax = 0, ay = 0;
		int cx = BBKMap.MapW;
		int cy = BBKMap.MapH;
		// ---------------------------------------------
		MapPoiWJ awj = BBKMap.GetWJByPoint(ax, ay);
		MapPoiWJ cwj = BBKMap.GetWJByPoint(cx, cy);
		bd.d("BBKScreenDown.DownScreenMap() awj.w = " + awj.w + ", awj.j = " + awj.j, false, true);
		bd.d("BBKScreenDown.DownScreenMap() cwj.w = " + cwj.w + ", cwj.j = " + cwj.j, false, true);
		// ---------------------------------------------
		int z0 = BBKSoft.myMaps.ZoomN;
		int z1 = BBKSoft.myMaps.mapZoom.length;
		int m0 = BBKSoft.myMaps.mapZoom[z0];
		double zn = 0, pz = 0;
		// ---------------------------------------------
		for (int z = z0 + 1; z < z1; z++) {
			// ---------------------------------------------
			downZ = BBKSoft.myMaps.mapZoom[z];
			zn = 1 * Math.pow(2, downZ - m0);
			pz = (zn + 1) * (zn + 1);
			// ---------------------------------------------
			for (int y = 0; y <= zn; y++) {
				for (int x = 0; x <= zn; x++) {
					// ---------------------------------------------
					downW = awj.w - y * (awj.w - cwj.w) / zn;
					dongJ = awj.j + x * (cwj.j - awj.j) / zn;
					downP = ((x + 1 + (y + 1) * zn)) * 100 / pz;
					// ---------------------------------------------
					BBKSoft.myLays.laytmp.line.get(0).add(downW, dongJ);
					MapFlash_Handler.post(MapFlash_Runnable);
					delay(50);
					// ---------------------------------------------
					do {
						// ----------------------------------------
						info_Handler.post(info_Runnable);
						delay(200);
						// ----------------------------------------
					} while (BBKMap.DownLasts > 3);
					// ---------------------------------------------
				}
			}
			// ---------------------------------------------
		}
		// ---------------------------------------------
		info_Handler.post(info_Runnable);
		downW = cw;
		dongJ = cj;
		downZ = m0;
		MapFlash_Handler.post(MapFlash_Runnable);
		// ---------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	private static Handler MapFlash_Handler = new Handler();
	private static Runnable MapFlash_Runnable = new Runnable() {
		public void run() {
			// ----------------------------------------------
			BBKSoft.myMaps.MapSetWJZ(downW, dongJ, 0);
			BBKMap.mapzm = downZ;
			BBKSoft.MapFlash(true);
			// ----------------------------------------------
		}
	};

	private static Handler info_Handler = new Handler();
	private static Runnable info_Runnable = new Runnable() {
		@SuppressWarnings("static-access")
		public void run() {
			// ----------------------------------------------
			BBKSoft.myBoxs.LabInf.setText(BBKSoft.myMaps.DownInfos);
			BBKSoft.myMsg.tShow(downZ + "- " + (int) downP + "%");
			// ----------------------------------------------
		}
	};

	private static void delay(int micsec) {
		try {
			Thread.sleep(micsec);
		} catch (InterruptedException e) {
		}
	}
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
}