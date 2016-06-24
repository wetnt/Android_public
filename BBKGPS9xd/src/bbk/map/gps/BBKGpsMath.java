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
		// g.Y = loc.hasAccuracy();//
		// loc.hasAltitude()/loc.hasBearing()/loc.hasSpeed();
		// -------------------------------------------------------------------
		g.t = new Date(loc.getTime());// 时间
		g.w = getDouble(loc.getLatitude(), 6);// 纬度
		g.j = getDouble(loc.getLongitude(), 6);// 经度
		g.h = getDouble(loc.getAltitude(), 0);// 海拔
		g.r = g.K ? loc.getAccuracy() : 0;// 精度
		g.f = g.K ? getDouble(loc.getBearing(), 0) : 0;// 方位
		g.v = g.K ? getDouble(loc.getSpeed() * 3.6, 1) : 0;// 速度
		// -------------------------------------------------------------------
		g.R = g.K ? GpsRuns() : false;
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
		public boolean K;// 是否定位
		public boolean R;// 是否合理移动
		// -----------------------------------------
		public Date t;// GPS时间
		public Date ts;// 启动时间
		public double tl;// 持续时间
		public String tls;// 持续时间字符形式
		// -----------------------------------------
		public float r;// 精度
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
		public int s;// 卫星数目
		public int u;// 参与解算卫星数目
		public int snr;// 卫星平均信号值
		public int usr;// 参与解算卫星数目
		// -----------------------------------------
	}

	public void GpsFirst() {
		// ----------------------------------------------------
		g.K = false;
		g.R = false;
		// ----------------------------------------------------
		g.t = new Date(System.currentTimeMillis());
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

	public void GpsSetFalse() {
		// ----------------------------------------------------
		g.K = false;
		g.R = false;
		g.v = 0;
		g.r = 0;
		// ----------------------------------------------------
	}

	public void GpsInfos(double mapw, double mapj, float compass, boolean more) {
		// ----------------------------------------------------
		g.a = gpsTmFt.format(g.t);
		g.a += " " + (int) g.r;
		g.a += g.K ? "A" : "N";
		g.a += g.u + "/" + g.s;
		g.a += "=" + (g.K ? g.usr : g.snr);
		g.a += " " + g.l + "km";
		g.a += " " + (int) g.h + "m";
		g.a += " F" + compass + "/" + (int) g.f;
		// ----------------------------------------------------
		g.vs = " " + g.v; // + "km/h";
		// ---------------------------------------------------------------------
		g.i = "[+]" + mapw + "," + mapj + "\r\n";
		if (g.K || more) {
			g.i += "[.]" + g.w + "," + g.j + "," + g.h + "\r\n";
			g.i += OrientationToStr(compass) + " " + (int) compass;
			g.i += "/" + (int) g.f + "\r\n";
			g.i += "s=" + g.l + " km" + "/" + g.tls + " " + "\r\n";
			g.i += "v=" + g.va + " / " + g.vm + " km/h" + "\r\n";
		}
		// ---------------------------------------------------------------------
	}

	public boolean GpsRuns() {
		// -------------------------------------------------------------------
		if (g.vm < g.v) {
			g.vm = getDouble(g.v, 1);
		}
		// -------------------------------------------------------------------
		if (g.K && g.ts.getTime() > g.t.getTime())
			g.ts = new Date(g.t.getTime());
		// -------------------------------------------------------------------
		if (g.t.getTime() >= g.ts.getTime()) {
			g.tl = g.t.getTime() - g.ts.getTime();
		} else {
			g.tl = g.ts.getTime() - g.t.getTime();
		}
		g.tl = getDouble(g.tl / 3600000f, 3);
		g.tls = g.t.getTime() > g.ts.getTime() ? "" : "-";
		g.tls += getDFM(g.tl);
		// -------------------------------------------------------------------
		g.va = getDouble(g.l / g.tl, 1);
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
		g.l = getDouble(g.l, 3);
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
			g.s = 0;// 卫星数目
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
			g.s = numSatelliteList.size();
			g.u = GpsUseds;
			g.snr = g.s == 0 ? 0 : GpsAllSnr / g.s;
			g.usr = g.u == 0 ? 0 : GpsUseSnr / g.u;
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
	public double getDouble(double a, int Decimals) {
		double n = 1;
		if (Decimals == 0)
			n = 1;
		else if (Decimals == 1)
			n = 10;
		else if (Decimals == 2)
			n = 100;
		else if (Decimals == 3)
			n = 1000;
		else if (Decimals == 4)
			n = 10000;
		else if (Decimals == 5)
			n = 100000;
		else if (Decimals == 6)
			n = 1000000;
		else if (Decimals == 7)
			n = 10000000;
		else if (Decimals == 8)
			n = 100000000;
		else if (Decimals == 9)
			n = 1000000000;
		int b = (int) (a * n);
		double r = (double) b / n;
		return r;
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
			ddd = getDouble(ddd, 6);
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
		} else if ((degree >= 175 && degree <= 180) || degree >= -180 && degree < -175) {
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