package bbk.map.abc;

import bbk.bbk.box.BBKSoft;
import bbk.bbk.box.BBKAbout;
import bbk.map.bbd.BBD;
import bbk.map.bbd.BBD.Pt;
import bbk.map.dat.BBKReg;
import bbk.map.dat.BBKReg.RegWJ;
import bbk.sys.abc.BBKMsgBox;
import bbk.zzz.debug.BBKDebug;

public class BBKMap {

	// ====================================================================================
	// ####################################################################################
	// ##############################MapFlash##############################################
	// ####################################################################################
	// ====================================================================================
	private boolean MapIsFlash = false;
	public boolean MapRegKey = false;//true;//true:国内,false:国外

	public void MapFlashNew() {
		// ------------------------------------------------------------------
		if (MapIsFlash) {
			BBKDebug.d("MapIsFlash false IS WORK!!!!", false, true);
			return;
		}
		// ------------------------------------------------------------------
		MapIsFlash = true;
		// ------------------------------------------------------------------
		MapDraw();
		MapLayShow();
		MapBLCShow();
		MapCopyRightShow();
		BBKMapGpsArrow.MapGpsArrow(mapzm);
		// MapDownInfoShow();
		// ------------------------------------------------------------------
		MapIsFlash = false;
		// BBKDebug.ttt("MapFlash");
		// ------------------------------------------------------------------
		BBKSoft.myBoxs.MapScrn();
		// ------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################MapSet################################################
	// ####################################################################################
	// ====================================================================================
	public void MapSetWJZ(double w, double j, int z) {
		MapCenterSet(w, j);
		MapZoomSet(z);
	}

	public void MapCenterSet(double w, double j) {
		// ----------------------------------------------------
		mapPt.w = w;
		mapPt.j = j;
		mapPt.FormatWJ();
		// ----------------------------------------------------
	}

	public void MapTypeSet() {
		// ----------------------------------------------------
		maptypeN++;
		if (maptypeN > 5) {
			maptypeN = 0;
		}
		// ----------------------------------------------------
		if (mapTypeInt[maptypeN] == 0) {
			MapTypeSet();
		}
		// ----------------------------------------------------
		// ----------------------------------------------------
		if (maptypeN < 3) {
			maptype = maptypeN;
		} else {
			maptype = mapTypeInt[maptypeN + 3];
		}
		// ----------------------------------------------------
	}

	public void MapZoomSet(int moveZN) {
		// ----------------------------------------------------
		ZoomN = ZoomN + moveZN;
		// ----------------------------------------------------
		if (ZoomN < 0) {
			ZoomN = 0;
		}
		if (ZoomN > mapZoom.length - 1) {
			ZoomN = mapZoom.length - 1;
		}
		// ----------------------------------------------------
		mapzm = mapZoom[ZoomN];
		// ----------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################MapInt################################################
	// ####################################################################################
	// ====================================================================================
	public void MapInt(int width, int height, String mapPath) {
		// ----------------------------------------------------
		BBKMapMath.pow2nInt();
		// ----------------------------------------------------
		myBBD.BBDInt(mapPath);
		// ----------------------------------------------------
		mapZoom = new int[] { 1, 3, 5, 8, 11, 14, 17, 19 };
		// ----------------------------------------------------
		MapW = width;
		MapH = height;
		MapWx = MapW / 2;
		MapHy = MapH / 2;
		// ----------------------------------------------------
		mapPt = new MapPoiWJ(39.977, 116.332);
		oldPt = new MapPoiWJ(0, 0);
		mapPf = new MapPoiWJ(0, 0);
		ZoomN = 0;
		mapzm = mapZoom[ZoomN];
		maptype = 0;
		// ----------------------------------------------------
		BBKMapImage.PaintSet();
		BBKMapImage.BitmapSet(MapW, MapH);
		// ----------------------------------------------------
	}

	public void MapReInt(int width, int height) {
		// ------------------------------------------------------------------
		MapW = width;
		MapH = height;
		MapWx = MapW / 2;
		MapHy = MapH / 2;
		// ------------------------------------------------------------------
		BBKMapImage.BitmapSet(MapW, MapH);
		// ------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################下载刷新心跳###########################################
	// ####################################################################################
	// ====================================================================================
	private BBD myBBD = new BBD();

	public boolean MapDownKeyClick(int key) {
		return myBBD.MapDownKeyClick(key);
	}

	public void MapExit() {
		myBBD.BBDExit();
	}

	public static int DownLasts = 0;// 剩余下载地图量
	public static String DownInfos = "";// 下载统计信息
	public static boolean DownNews = true;// 地图跟新标记

	// ====================================================================================
	// ####################################################################################
	// ##############################地图补洞下载##########################################
	// ####################################################################################
	// ====================================================================================
	public void BBKDownCenterPic() {
		// ----------------------------------------------------------------
		pix = BBKMapMath.MapMaxXCheck(PicX, mapzm);
		piy = PicY;
		boolean ptu = BBKMapMath.MapMaxYCheck(piy, mapzm);
		// ----------------------------------------------------------------
		BBKMsgBox.tShow("BBKDownCenterPic= " + pix + "---" + piy);
		// ----------------------------------------------------------------
		if (!ptu)
			return;
		// ----------------------------------------------------------------
		mapPts.SetPt(pix, piy, mapzm, maptype);
		myBBD.MapPicDownPt(mapPts);
		// ----------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################地图刷新显示##########################################
	// ####################################################################################
	// ====================================================================================

	private boolean MapBmpsNeeded(boolean flash) {
		// -------------------------------------------------------------------------
		if (flash) {
			oldPt.w = mapPt.w;
			oldPt.j = mapPt.j;
			oldzoom = mapzm;
			oldtype = maptype;
			return true;
		}
		if (mapPt.w == oldPt.w && mapPt.j == oldPt.j && oldzoom == mapzm && oldtype == maptype && newdown == 0) {
			return false;
		} else {
			oldPt.w = mapPt.w;
			oldPt.j = mapPt.j;
			oldzoom = mapzm;
			oldtype = maptype;
			newdown = 0;
			return true;
		}
		// -------------------------------------------------------------------------
	}

	private void MapBmpsReged() {
		// --------------------------------------------------
		mapPt.FormatWJ();
		mapPf.w = mapPt.w;
		mapPf.j = mapPt.j;
		// ----------------------------------------------
		if (maptype == 0)
			MapRegRun();
		// if (maptype == 1)
		// MapRegRun();
		if (maptype == 2)
			MapRegRun();
		// --------------------------------------------------
	}

	private void MapRegRun() {
		if (MapRegKey) {
			RegWJ wj = BBKReg.WJ_TtoF(mapPf.w, mapPf.j);
			mapPf.w = wj.w;
			mapPf.j = wj.j;
			mapPf.FormatWJ();
		}
	}

	private void MapBmpsCalcs() {
		// ---------------------------------------------------------
		zoompow = BBKMapMath.pow2n[20 - mapzm];
		// ---------------------------------------------------------
		PixJXT = (int) BBKMapMath.GetPixelByLon(mapPt.j, mapzm);
		PixWYT = (int) BBKMapMath.GetPixelByLat(mapPt.w, mapzm);
		// ---------------------------------------------------------
		PixJXF = (int) BBKMapMath.GetPixelByLon(mapPf.j, mapzm);
		PixWYF = (int) BBKMapMath.GetPixelByLat(mapPf.w, mapzm);
		// ---------------------------------------------------------
		PicX = Math.round(PixJXF / 256);
		ModX = PixJXF % 256;
		PicY = Math.round(PixWYF / 256);
		ModY = PixWYF % 256;
		// ---------------------------------------------------------
	}

	// ====================================================================================
	private void MapDraw() {
		// ------------------------------------------------------------------------
		MapBmpsNeeded(true);
		MapBmpsReged();
		MapBmpsCalcs();
		// ------------------------------------------------------------------------
		BBKMapImage.BitmapClr();
		// ------------------------------------------------------------------------
		int leftR = (MapWx - ModX) / 256 + 1;
		int rigtR = (MapWx - (256 - ModX)) / 256 + 1;
		int topsR = (MapHy - ModY) / 256 + 1;
		int botmR = (MapHy - (256 - ModY)) / 256 + 1;
		boolean ptu = false;
		// ------------------------------------------------------------------------
		for (int y = -topsR; y <= botmR; y++) {
			for (int x = -leftR; x <= rigtR; x++) {
				// ----------------------------------------------------------------
				pix = PicX + x;
				pix = BBKMapMath.MapMaxXCheck(pix, mapzm);
				piy = PicY + y;
				ptu = BBKMapMath.MapMaxYCheck(piy, mapzm);
				// ----------------------------------------------------------------
				if (ptu) {
					// ------------------------------------------------------------
					mapPts.SetPt(pix, piy, mapzm, maptype);
					// --------------------------------------------------------
					pixx = MapWx + x * 256 - ModX;
					piyy = MapHy + y * 256 - ModY;
					// --------------------------------------------------------
					// BBKDebug.d(pix + "_" + piy + "_" + mapzm + "_"+maptype,
					// false, false);
					BBKMapImage.Pic = myBBD.ReadAllDirsPic(mapPts, maptype < 3);
					// --------------------------------------------------------
					if (BBKMapImage.Pic != null) {
						BBKMapImage.BBKMapDrawImage(BBKMapImage.Pic, pixx, piyy, true);
					} else {
						BBKMapImage.BBKMapDrawImage(BBKMapImage.Lgo, pixx, piyy, false);
					}
					// ------------------------------------------------------------
				}
			}
		}
		// ------------------------------------------------------------------------

	}

	// ====================================================================================
	public void BBKViewCenterTemp(int cx, int cy) {
		// -------------------------------------------------------------------------
		int aw = 10, ah = 10, an = 0;
		// -------------------------------------------------------------------------
		if (ZoomN + 1 < mapZoom.length) {
			an = mapZoom[ZoomN + 1] - mapZoom[ZoomN] + 1;
			aw = (int) (MapW / BBKMapMath.pow2n[an]);
			ah = (int) (MapH / BBKMapMath.pow2n[an]);
		}
		// -------------------------------------------------------------------------
		int xx = 12;
		int x0 = cx - aw;
		int y0 = cy - ah;
		int x1 = cx + aw;
		int y1 = cy - ah;
		int x2 = cx + aw;
		int y2 = cy + ah;
		int x3 = cx - aw;
		int y3 = cy + ah;
		// -------------------------------------------------------------------------
		BBKMapImage.DrawLine(x0, y0, x0 + xx, y0);
		BBKMapImage.DrawLine(x0, y0, x0, y0 + xx);
		BBKMapImage.DrawLine(x1, y1, x1 - xx, y1);
		BBKMapImage.DrawLine(x1, y1, x1, y1 + xx);
		BBKMapImage.DrawLine(x2, y2, x2 - xx, y2);
		BBKMapImage.DrawLine(x2, y2, x2, y2 - xx);
		BBKMapImage.DrawLine(x3, y3, x3 + xx, y3);
		BBKMapImage.DrawLine(x3, y3, x3, y3 - xx);
		// --------------------------------------------------------------------------
		BBKSoft.myBoxs.MapScrn();
		// --------------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################绘制地图元素##########################################
	// ####################################################################################
	// ====================================================================================
	// private void MapDownInfoShow() {
	// BBKMapImage.DrawText(myBBD.BBDDownInfos(), 20, 100);
	// }

	private void MapLayShow() {
		BBKSoft.myLays.LayShow(mapzm);
	}

	private void MapBLCShow() {
		BBKSoft.myBoxs.LabBlc.setText(BBKAbout.SoftCopyRitht + "   " + BBKMapMath.BLCKMM[mapzm]);
		// BBKMapImage.DrawText(BLCKMM[mapzm], MapW - 120, MapH - 100);
	}

	private void MapCopyRightShow() {
		// BBKMapImage.DrawText(BBK.strCopyRitht, 10, MapH - 100);
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################地图常量变量##########################################
	// ####################################################################################
	// ====================================================================================
	// =================比例尺============================================================
	public int mapZoom[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	public static int mapzm = 0;
	public int ZoomN = 1;
	// =================地图类型===========================================================
	public int maptype = 0, maptypeN = 0;
	public int mapTypeInt[] = { 1, 1, 1, 0, 0, 0, 3, 4, 5 };
	// ====================================================================================
	public static int MapW, MapH, MapWx, MapHy;
	private int pix, piy, pixx, piyy;
	private Pt mapPts = new Pt(0, 0, 0, 0);
	// ====================================================================================
	private int oldzoom = 999, oldtype = 999, newdown = 0;
	public MapPoiWJ mapPt;
	private MapPoiWJ mapPf, oldPt;
	// ------------------------------------------------------------------------------------
	private static double zoompow = 1;
	private static int PixWYF, PixJXF;
	public static int PixWYT, PixJXT;
	private int PicX, PicY, ModX, ModY;

	// ====================================================================================
	// ####################################################################################
	// ##############################地图定位函数##########################################
	// ####################################################################################
	// ====================================================================================
	public static MapPoiXY GetPointByWJ(double w, double j) {
		// ------------------------------------------------
		MapPoiXY mp = new MapPoiXY(0, 0);
		// ------------------------------------------------
		mp.x = (int) BBKMapMath.GetPixelByLon(j, mapzm);
		mp.y = (int) BBKMapMath.GetPixelByLat(w, mapzm);
		mp.x = MapWx + mp.x - PixJXT;
		mp.y = MapHy + mp.y - PixWYT;
		// ------------------------------------------------
		return mp;
		// ------------------------------------------------
	}

	public static MapPoiWJ GetWJByPoint(double x, double y) {
		// --------------------------------------------
		double w = 0, j = 0;
		// --------------------------------------------
		x = x + PixJXT - MapWx;
		y = y + PixWYT - MapHy;
		w = BBKMapMath.GetLatByPixel(y, mapzm);
		j = BBKMapMath.GetLonByPixel(x, mapzm);
		MapPoiWJ mwj = new MapPoiWJ(w, j);
		mwj.FormatWJ();
		return mwj;
		// --------------------------------------------
	}

	// ==============================================================================
	public static int GetPointXby20(int x20) {
		return (int) (MapWx + x20 / zoompow - PixJXT);
	}

	public static int GetPointYby20(int y20) {
		return (int) (MapHy + y20 / zoompow - PixWYT);
	}

	public static boolean GetPointV(int x, int y) {
		return (x > 0 && y > 0 && x < MapW && y < MapH);
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################地图基础类型##########################################
	// ####################################################################################
	// ====================================================================================
	public static class MapPoiWJ {
		public double w = 0, j = 0;

		public MapPoiWJ(double ww, double jj) {
			w = ww;
			j = jj;
		}

		public void FormatWJ() {
			w = BBKMapMath.LatFormat(w);
			j = BBKMapMath.LonFormat(j);
			w = BBKMapMath.getDouble(w);
			j = BBKMapMath.getDouble(j);
		}
	}

	public static class MapPoiXY {
		public int x = 0, y = 0;

		public MapPoiXY(int xx, int yy) {
			x = xx;
			y = yy;
		}

	}
	// ====================================================================================
	// ####################################################################################
	// ##############################地图基础类型##########################################
	// ####################################################################################
	// ====================================================================================
}