package bbk.map.abc;

import bbk.bbk.box.BBKSoft;
import bbk.map.abc.BBKMap.MapPoiXY;

public class BBKMapGpsArrow {

	// =============================绘制箭头============================================
	private static int gpsd = 10;
	private static final int gpsn = 10;
	private static double gpsw = 0, gpsj = 0, gpsf = 0, gpsr = 0;
	private static MapPoiXY gpsp = new MapPoiXY(0, 0);
	private static boolean gpsy = false;

	public static void MapGpsArrow(int mapzm) {// 绘制箭头
		// ---------------------------------------------------------------------
		gpsw = BBKSoft.myGps.g.w;
		gpsj = BBKSoft.myGps.g.j;
		gpsf = BBKSoft.myGps.g.f;
		gpsr = BBKSoft.myGps.g.r;
		// ---------------------------------------------------------------------
		if (gpsw == 0 && gpsj == 0)
			return;
		// ---------------------------------------------------------------------
		gpsy = !gpsy;
		gpsp = BBKMap.GetPointByWJ(gpsw, gpsj);
		gpsd = (int) (gpsr * 100 / BBKMapMath.BLCLONG[mapzm]);
		// ---------------------------------------------------------------------
		BBKMapImage.DrawGPSArrow(gpsf, gpsp.x, gpsp.y, gpsn, gpsn + gpsn, gpsd, gpsy);
		// ---------------------------------------------------------------------
	}

}