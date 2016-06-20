package bbk.map.gps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;

public class BBKGpsMath {

	public void GpsUpdata(Location loc) {
		// -------------------------------------------------------------------
		g.Y = loc.hasAccuracy();// loc.hasAltitude()/loc.hasBearing()/loc.hasSpeed();
		// -------------------------------------------------------------------
		g.t = new Date(loc.getTime());// 时间
		g.r = loc.getAccuracy();// 精度
		g.w = getDouble(loc.getLatitude(), 100000);// 纬度
		g.j = getDouble(loc.getLongitude(), 100000);// 经度
		g.h = getDouble(loc.getAltitude(), 10);// 海拔
		g.f = getDouble(loc.getBearing(), 10);// 方位
		g.v = getDouble(loc.getSpeed() * 36 / 10, 10);// 速度
		// -------------------------------------------------------------------
		g.R = GpsRuns();
		// -------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################GpsMath_A#############################################
	// ####################################################################################
	// ====================================================================================
	@SuppressLint("SimpleDateFormat")
	private final SimpleDateFormat gpsTmFt = new SimpleDateFormat("HH:mm:ss");
	// ---------------------------------------------------------------
	private final double GpsMinR = 0.00001;// 记录轨迹点的最小范围
	public GPS g = new GPS();

	// ---------------------------------------------------------------
	public class GPS {
		// -----------------------------------------
		public boolean Y, R;
		// -----------------------------------------
		public Date t, ts;// GPS时间/启动时间
		public double tl;// 持续时间
		public String tr;// 持续时间字符形式
		// -----------------------------------------
		public double r;// 精度
		// -----------------------------------------
		public double w, lw;// 纬度、上一次纬度
		public double j, lj;// 经度、上一次经度
		public double h;// 海拔
		public double f;// 方向
		// -----------------------------------------
		public double v;// 速度
		public double va;// 平均速度
		public double vm;// 最大速度
		public double l;// 里程
		// -----------------------------------------
		public String a;// T;// 标题
		public String i;// 信息
		public String vs;// 速度
		// -----------------------------------------
		public int m;// 卫星数目
		public int u;// 参与解算卫星数目
		public int snr;// 卫星平均信号值
		public int usr;// 参与解算卫星数目
		// -----------------------------------------
	}

	public void GpsFirst() {
		// ----------------------------------------------------
		g.Y = false;
		g.R = false;
		// ----------------------------------------------------
		g.t = new Date(0);
		g.ts = new Date(System.currentTimeMillis());
		g.r = 0;
		// ----------------------------------------------------
		g.w = 0;
		g.j = 0;
		g.lw = 0;
		g.lj = 0;
		// ----------------------------------------------------
		g.h = 0;
		g.f = 0;
		// ----------------------------------------------------
		g.v = 0;
		g.vm = 0;
		g.va = 0;
		g.l = 0;
		// ----------------------------------------------------
	}

	public void GpsClose() {
		// ----------------------------------------------------
		g.Y = false;
		g.R = false;
		// ----------------------------------------------------
	}

	public void GpsInfos(double mapw, double mapj, float compass, boolean more) {
		// ----------------------------------------------------
		g.a = gpsTmFt.format(g.t);
		g.a += " " + (int) g.r + "A" + g.u + "/" + g.m;
		if (g.Y) {
			g.a += "=" + g.usr;
		} else {
			g.a += "=" + g.snr;
		}
		g.a += " " + g.l + "km";
		g.a += " " + (int) g.h + "m";
		g.a += " F" + compass + "/" + (int) g.f;
		// ----------------------------------------------------
		g.vs = " " + g.v; // + "km/h";
		// ---------------------------------------------------------------------
		g.i = mapw + "," + mapj + "\r\n";
		if (g.Y || more) {
			g.i += g.w + "," + g.j + "," + g.h + "\r\n";
			g.i += OrientationToStr(compass);
			g.i += " " + (int) compass;
			g.i += "/" + (int) g.f + "\r\n";
			g.i += "s=" + g.l + " km";
			g.i += "/" + g.tr + " " + "\r\n";
			g.i += "v=" + g.va + " / " + g.vm + " km/h" + "\r\n";
		}
		// ---------------------------------------------------------------------
	}

	public boolean GpsRuns() {
		// -------------------------------------------------------------------
		if (g.vm < g.v) {
			g.vm = g.v;
		}
		// -------------------------------------------------------------------
		if (g.ts.getTime() > g.t.getTime())
			g.ts = new Date(g.t.getTime());
		// -------------------------------------------------------------------
		g.tl = (g.t.getTime() - g.ts.getTime()) / 3600000f;
		// -------------------------------------------------------------------
		g.va = g.l / g.tl;
		g.tr = getDFM(g.tl);
		g.tl = getDouble(g.tl, 1000);
		g.va = getDouble(g.va, 1000);
		// -----------------------------------------------------------------------
		if (g.w == 0 && g.j == 0) {
			return false;
		}
		// -----------------------------------------------------------------------
		if (g.lw == 0 && g.lj == 0) {
			g.lw = g.w;
			g.lj = g.j;
		}
		// -----------------------------------------------------------------------
		if (Math.abs(g.lw - g.w) < GpsMinR && Math.abs(g.lj - g.j) < GpsMinR)
			return false;
		// -----------------------------------------------------------------------
		g.l += GetDistance(g.w, g.j, g.lw, g.lj);
		g.l = getDouble(g.l, 1000);
		// -----------------------------------------------------------------------
		g.lw = g.w;
		g.lj = g.j;
		// -----------------------------------------------------------------------
		// System.out.println("w=" + gps.w + " j=" + gps.j);
		return true;
		// -----------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################GpsMath_B#############################################
	// ##############################卫星状态监听器########################################
	// ####################################################################################
	// ====================================================================================
	private List<GpsSatellite> numSatelliteList = new ArrayList<GpsSatellite>(); // 卫星信号
	private Iterator<GpsSatellite> it;
	private int GpsCount = 0, GpsUseds = 0;
	private int GpsMax = 0, GpsAllSnr = 0, GpsUseSnr = 0;

	public void updateGpsStatus(int event, GpsStatus status) {
		if (status == null) {
			// -----------------------------------------------------------------------
			g.m = 0;// 卫星数目
			g.u = 0;// 参与解算卫星数目
			g.snr = 0;// 卫星平均信号值
			g.usr = 0;// 参与解算卫星数目
			// -----------------------------------------------------------------------
		} else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
			// -----------------------------------------------------------------------
			numSatelliteList.clear();
			GpsMax = status.getMaxSatellites();
			it = status.getSatellites().iterator();
			GpsAllSnr = GpsUseSnr = GpsCount = GpsUseds = 0;
			// -----------------------------------------------------------------------
			while (it.hasNext() && GpsCount <= GpsMax) {
				GpsSatellite s = it.next();
				numSatelliteList.add(s);
				GpsAllSnr += s.getSnr();
				if (s.usedInFix()) {
					GpsUseSnr += s.getSnr();
					GpsUseds++;
				}
				GpsCount++;
			}
			// -----------------------------------------------------------------------
			g.m = numSatelliteList.size();
			if (g.m != 0) {
				g.snr = GpsAllSnr / g.m;
			} else {
				g.snr = 0;
			}
			g.u = GpsUseds;
			if (g.u != 0) {
				g.usr = GpsUseSnr / g.u;
			} else {
				g.usr = 0;
			}
			// -----------------------------------------------------------------------
		}
		// -----------------------------------------------------------
		// 包括 卫星的高度角、方位角、信噪比、和伪随机号（及卫星编号）
		// satellite.getElevation(); //卫星仰角
		// satellite.getAzimuth(); //卫星方位角
		// satellite.getSnr(); //信噪比
		// satellite.getPrn(); //伪随机数，可以认为他就是卫星的编号
		// satellite.hasAlmanac(); //卫星历书
		// satellite.hasEphemeris();
		// satellite.usedInFix();
		// -----------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################GpsMath_B#############################################
	// ####################################################################################
	// ====================================================================================
	public String getDFM(double t) {
		DFSX_type d = new DFSX_type(t);
		String s = "";
		s += d.d + "时 ";
		s += d.f + "分 ";
		s += (int) d.s + "秒";
		return s;
	}

	// ====================================================================================
	public double getDouble(double a, int n) {
		double a2 = a * n;
		int b = (int) a2;
		double c = (double) b;
		double d = c / n;
		return d;
	}

	// ===========================================================================================
	public class DFSX_type {
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
			ddd = getDouble(ddd, 100000);
		}
		// --------------------------------------------
	}

	// ================= 地球基础信息 ==========================================
	public static double EARTH_RADIUS = 6378137; // 米，地球大概半径6371.004千米

	// =================里程计算函数============================================
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {// 输出是公里
		double r1, r2, a, b, s;
		// ----------------------------------------------------------------------
		try {
			// ------------------------------------------------------------------
			r1 = rad(lat1);
			r2 = rad(lat2);
			a = r1 - r2;
			b = rad(lng1) - rad(lng2);
			s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(r1) * Math.cos(r2) * Math.pow(Math.sin(b / 2), 2)));
			s = Math.round(s * EARTH_RADIUS) / 1000.0;
			// ------------------------------------------------------------------
		} catch (Exception e) {
			s = 0;
		}
		// ----------------------------------------------------------------------
		return s;
		// ----------------------------------------------------------------------
	}

	// =================角度转弧度函数============================================
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
	// ============================================================================
	// ============================================================================
	// ============================================================================
	// ============================================================================
	// ============================================================================
}