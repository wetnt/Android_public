package bbk.map.abc;

import bbk.map.abc.BBKMap.MapPoiXY;

public class BBKMapMath {

	// =================比例尺==============================================================
	public final static String BLCKMM[] = { "8000km(0)", "4000km(1)", "2000km(2)", "1000km(3)", "500km(4)", "250km(5)", "120km(6)", "60km(7)", "30km(8)", "15km(9)", "7.5km(10)", "3km(11)", "2km(12)", "800m(13)", "400m(14)", "200m(15)", "100m(16)", "50m(17)", "25m(18)", "12m(19)", "6m(20)", "3m(21)", "1m(22)", "0.5m(22)" };
	public final static long BLCLONG[] = { 1, 1, 20000000, 10000000, 5000000, 2500000, 120000, 60000, 16000, 15500, 8000, 4000, 2000, 1000, 500, 250, 120, 53, 30, 15, 8, 4, 2, 1 };
	// =================比例尺==============================================================
	public static double pow2n[] = new double[21];

	public static void pow2nInt() {
		// ----------------------------------------------------
		for (int n = 0; n <= 20; n++) {
			pow2n[n] = Math.pow(2, n);
		}
		// ----------------------------------------------------
	}

	// =================墨卡托投影经纬到像素换算===========
	public static double GetPixelByLon(double dbLon, double nLayer) {// 经度到像素X值
		return (dbLon + 180) * Math.pow(2, nLayer + 8) / 360 * BBKMap.TileZoom;
	}

	public static double GetPixelByLat(double dbLat, double nLayer) {// 纬度到像素Y值
		double dbSinY = Math.sin(dbLat * Math.PI / 180);
		double dbTemp = Math.log((1 + dbSinY) / (1 - dbSinY));
		double dbRet = Math.pow(2, nLayer + 7) * (1 - dbTemp / (2 * Math.PI));
		return dbRet * BBKMap.TileZoom;
	}

	// =================墨卡托投影经纬到像素换算==========
	public static double GetLonByPixel(double dbPixel, double nLayer) {// 像素X到经度
		return dbPixel / BBKMap.TileZoom * 360 / Math.pow(2, nLayer + 8) - 180;
	}

	public static double GetLatByPixel(double dbPixel, int nLayer) {// 像素Y到纬度
		double dbTempY = 2 * Math.PI * (1 - dbPixel / BBKMap.TileZoom / Math.pow(2, nLayer + 7));
		double dbTempZ = Math.pow(Math.E, dbTempY);
		double dbRatio = (dbTempZ - 1) / (dbTempZ + 1);
		double dbRet = Math.asin(dbRatio) * 180 / Math.PI;
		return dbRet;
	}

	// =================计算矢量夹角======================================================================
	// 由起点到末点的矢量，与正北方向的顺时针夹角
	public static double GetCWAngleByPoint(point ptStart, point ptEnd) {
		double dbRet = 0;
		if (ptEnd.X == ptStart.X) {
			if (ptEnd.Y <= ptStart.Y) {
				dbRet = 0;
			} else {
				dbRet = 180;
			}
		} else {
			dbRet = Math.atan((ptEnd.Y - ptStart.Y) / (ptStart.X - ptEnd.X)) * 180 / Math.PI;
			if (ptStart.X <= ptEnd.X) {
				dbRet = 90 - dbRet;
			} else {
				dbRet = 270 - dbRet;
			}
		}
		return dbRet;
	}

	// =================P点包含关系计算函数=============================================================
	private static double PinA, PinB, Pinc, xinters;
	private static int PinNum, PinM;
	private static point PinPA, PinPB;

	public static boolean InsidePolygon(point[] polygon, int polnum, point p, boolean Bod) {
		try {
			// ------------------------------------------------------------
			PinNum = 0;
			PinM = 0;
			PinPA = polygon[0];
			// ------------------------------------------------------------
			for (int i = 0; i <= polnum; i++) {
				PinM = i + 1;
				if (PinM > polnum) {
					PinM = 0;
				}
				PinPB = polygon[PinM];
				if (p.Y > Math.min(PinPA.Y, PinPB.Y) && p.Y <= Math.max(PinPA.Y, PinPB.Y) && PinPA.Y != PinPB.Y && p.X <= Math.max(PinPA.X, PinPB.X)) {
					PinA = p.Y - PinPA.Y;
					PinB = PinPB.X - PinPA.X;
					Pinc = PinPB.Y - PinPA.Y;
					xinters = PinA * PinB / Pinc + PinPA.X;
					if (PinPA.X == PinPB.X || p.X != xinters) {
						PinNum = PinNum + 1;
					}
				}
				PinPA = PinPB;
			}
			// ------------------------------------------------------------
			if (PinNum / 2 == (int) (PinNum / 2)) {
				if (Bod) {
					// ----------------------------------------------------
					for (int i = 0; i <= polnum; i++) {
						if (polygon[i] == p) {
							return true;
						}
					}
					return false;
					// ----------------------------------------------------
				} else {
					return false;
				}
			} else {
				return true;
			}
			// ------------------------------------------------------------
		} catch (Exception e) {
			return false;
		}
		// ================================================================
	}

	// ================= 地球基础信息 ==========================================
	public static double EARTH_RADIUS = 6378137; // 米，地球大概半径6371.004千米

	// =================里程计算函数==================================================================
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {// 输出是公里
		double r1, r2, a, b, s;
		// ----------------------------------------------------------------------
		try {
			// ----------------------------------------------------------------------
			r1 = rad(lat1);
			r2 = rad(lat2);
			a = r1 - r2;
			b = rad(lng1) - rad(lng2);
			s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(r1) * Math.cos(r2) * Math.pow(Math.sin(b / 2), 2)));
			s = Math.round(s * EARTH_RADIUS) / 1000.0;
			// ----------------------------------------------------------------------
		} catch (Exception e) {
			s = 0;
		}
		// ----------------------------------------------------------------------
		return s;
		// ----------------------------------------------------------------------
	}

	// =================角度转弧度函数========================================================
	public static double TempFxPI = Math.PI / 180.0;

	public static double rad(double d) {
		double dbRet;
		try {
			dbRet = d * TempFxPI;
		} catch (Exception e) {
			dbRet = 0;
		}
		return dbRet;
	}

	// =========一个坐标点按照一个中心进行旋转，注意：原点为左上角，坐标方向为右下======================
	public static MapPoiXY PointRotate(MapPoiXY center, MapPoiXY p, double jaodu) {
		// ----------------------------------------------------------
		double angleHude = jaodu * Math.PI / 180;/* 角度变成弧度 */
		double sina = Math.sin(angleHude);
		double cosa = Math.cos(angleHude);
		// ----------------------------------------------------------
		double xx = p.x - center.x;
		double yy = p.y - center.y;
		double x = center.x + xx * cosa - yy * sina;
		double y = center.y + xx * sina + yy * cosa;
		// ----------------------------------------------------------
		return new MapPoiXY((int) x, (int) y);
		// ----------------------------------------------------------
		// 二维的很简单,
		// 假设点(x,y)绕(x0,y0)逆时针旋转a角后变成(x',y')，则
		// x'-x0=(x-x0)cosa-(y-y0)sina
		// y'-y0=(x-x0)sina+(y-y0)cosa
		// ----------------------------------------------------------
	}

	// =========平面坐标系两点间距离=================================================
	public static double PointDs(double x1, double y1, double x2, double y2) {
		double ds = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return ds;
	}

	// =========平面坐标系点========================================================
	public static class point {
		double X;
		double Y;

		point(int x, int y) {
			X = x;
			Y = y;
		}
	}

	// =========角度文字换算========================================================
	public static String OrientationToStr(float degree) {
		// ----------------------------------------------------
		while (degree > 180) {
			degree = degree - 360;
		}
		while (degree < -180) {
			degree = degree + 360;
		}
		// ----------------------------------------------------
		if (degree >= -5 && degree < 5) {
			return "正北";
		} else if (degree >= 5 && degree < 85) {
			return "东北";
		} else if (degree >= 85 && degree <= 95) {
			return "正东";
		} else if (degree >= 95 && degree < 175) {
			return "东南";
		} else if ((degree >= 175 && degree <= 180) || (degree) >= -180 && degree < -175) {
			return "正南";
		} else if (degree >= -175 && degree < -95) {
			return "西南";
		} else if (degree >= -95 && degree < -85) {
			return "正西";
		} else if (degree >= -85 && degree < -5) {
			return "西北";
		}
		// ----------------------------------------------------
		return "XX";
		// ----------------------------------------------------
	}

	// =================Map_Max_X/Y_Check==============================================================
	public static int[] MapMaxX = { 0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575 };
	public static int[] MapMaxY = { 0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1022, 2044, 4089, 8178, 16357, 32714, 65428, 130857, 261714, 523429, 1046858 };

	public static int MapMaxXCheck(int Mapx, int Zm) {
		if (Mapx > MapMaxX[Zm]) {
			Mapx = Mapx - MapMaxX[Zm] - 1;
		}
		if (Mapx < 0) {
			Mapx = MapMaxX[Zm] + Mapx + 1;
		}
		return Mapx;
	}

	public static boolean MapMaxYCheck(int Mapy, int Zm) {
		if (Mapy > MapMaxY[Zm]) {
			return false;
		}
		if (Mapy < 0) {
			return false;
		}
		return true;
	}

	// =================Map_Max_X/Y_Check==============================================================

	// ===============墨卡托投影的范围限定==================================
	final static double LatLimit = 85;

	public static double LatFormat(double maplat) {
		// -----------------------------------
		if (maplat > LatLimit) {
			maplat = LatLimit;
		}
		if (maplat < -LatLimit) {
			maplat = -LatLimit;
		}
		// -----------------------------------
		return maplat;
		// -----------------------------------
	}

	public static double LonFormat(double maplon) {
		// -----------------------------------
		if (maplon > 180) {
			maplon = maplon - 360;
		}
		if (maplon < -180) {
			maplon = 360 + maplon;
		}
		// -----------------------------------
		return maplon;
		// -----------------------------------
	}

	// ====================================================================================
	final static int Decimals = 1000000; // (int)Math.pow(10,N);

	public static double getDouble(double a) {
		int b = (int) (a * Decimals);
		double d = (double) b / Decimals;
		return d;
	}

	// ====================================================================================
	public static double GetAngleByLatLon(double w1, double j1, double w2, double j2) {
		// ---------------------------------------------------------------
		int x1 = (int) GetPixelByLon(j1, 15);
		int y1 = (int) GetPixelByLat(w1, 15);
		int x2 = (int) GetPixelByLon(j2, 15);
		int y2 = (int) GetPixelByLat(w2, 15);
		// ---------------------------------------------------------------
		point p1 = new point(x1, y1);
		point p2 = new point(x2, y2);
		// ---------------------------------------------------------------
		double ag = GetCWAngleByPoint(p1, p2);
		return ag;
	}
	// =====================================================================================
}
