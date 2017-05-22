package bbk.map.gps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;
import bbk.map.abc.BBKMapMath;

public class BBK_Tool_GPS {

	// ====================================================================================
	// ####################################################################################
	// ##############################GPS开关###############################################
	// ####################################################################################
	// ====================================================================================
	public boolean GpsIsRun = false;
	public GPS g = new GPS();
	public GpsUpdateWork myGpsUpdateWork = new GpsUpdateWork() {
		@Override
		public void work() {
			// d.s("myGpsUpdateWork==Default_New_Work");
		}
	};

	public boolean GpsInt(final Activity pthis, GpsUpdateWork work) {
		// ---------------------------------------------------------------------
		bbkAct = pthis;
		GpsFirst();
		GpsIsRun = false;
		myGpsUpdateWork = work;
		return GpsStart();
		// ---------------------------------------------------------------------
	}

	public void GpsOpen() {
		// ------------------------------------------------------------------------------------------
		String bestProvider = lm.getBestProvider(getCriteria(), true);// 设置查询条件
		lg.onProviderEnabled(bestProvider);// 默认(LocationManager.GPS_PROVIDER);
		// ------------------------------------------------------------------------------------------
		lm.addGpsStatusListener(ls);// 监听状态
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, lg);// 绑定监听4参数
		// ------------------------------------------------------------------------------------------
		// 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
		// 参数2，位置信息更新周期，单位毫秒
		// 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
		// 参数4，监听
		// 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
		// 1秒更新一次，或最小位移变化超过1米更新一次；----------------------------
		// 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，
		// 在run中sleep(10000);然后执行handler.sendMessage(),更新位置
		// ------------------------------------------------------------------------------------------
		GpsIsRun = true;
		// ------------------------------------------------------------------------------------------
	}

	public void GpsClose() {
		// ------------------------------------------------------------------------------------------
		lm.removeUpdates(lg);
		lm.removeGpsStatusListener(ls);
		GpsIsRun = false;
		// ------------------------------------------------------------------------------------------
	}

	// ====================================================================================
	// ###############################消息显示#############################################
	// ====================================================================================
	public void ToastShow(String str) {
		Toast.makeText(bbkAct, str, Toast.LENGTH_SHORT).show();
	}
	// ====================================================================================
	// ####################################################################################
	// ##############################GPS更新设置###########################################
	// ####################################################################################
	// ====================================================================================

	// ====================================================================================
	// ####################################################################################
	// ##############################GPS开关##############################################
	// ####################################################################################
	// ====================================================================================
	private Activity bbkAct;
	private LocationManager lm;

	public interface GpsUpdateWork {// GPS更新接口
		public void work();
	}

	private boolean GpsStart() {
		// ---------------------------------------------------------------------
		String GpsSev = Context.LOCATION_SERVICE;
		String provider = LocationManager.GPS_PROVIDER;
		String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
		// ---------------------------------------------------------------------
		lm = (LocationManager) bbkAct.getSystemService(GpsSev);
		boolean GpsIsOpen = lm.isProviderEnabled(provider);// 判断GPS是否正常启动
		// ----------------------------------------------------------------------
		if (GpsIsOpen) {
			ToastShow("GPS 启动！");
		} else {
			toggleGPS(bbkAct);// 自动开始GPS设置开关
			ToastShow("自动开启GPS模块！");
		}
		// ----------------------------------------------------------------------
		GpsIsOpen = lm.isProviderEnabled(provider);
		if (!GpsIsOpen) {
			// 返回开启GPS导航设置界面
			Intent intent = new Intent(action);
			bbkAct.startActivityForResult(intent, 0);
			return false;
		}
		// ----------------------------------------------------------------------
		GpsOpen();
		// ----------------------------------------------------------------------
		return true;
		// ----------------------------------------------------------------------
	}

	// ====================================================================================
	// ############################GPS查询条件##############################################
	// ====================================================================================
	private Criteria getCriteria() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// 定位精确度,Criteria.ACCURACY_COARSE比较粗略
		criteria.setCostAllowed(false);// 运营商收费
		criteria.setSpeedRequired(true);// 速度
		criteria.setBearingRequired(true);// 方位
		criteria.setAltitudeRequired(true);// 海拔
		criteria.setPowerRequirement(Criteria.POWER_LOW);// 电源
		return criteria;
	}

	// ====================================================================================
	// ###############################GPS监听设置###########################################
	// ====================================================================================
	private LocationListener lg = new LocationListener() {// 位置监听
		@Override
		public void onLocationChanged(Location location) {// 位置信息变化时触发
			GpsUpdate(location);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {// GPS状态变化时触发
			switch (status) {
			case LocationProvider.AVAILABLE:
				// BBKDebug.ddd("当前GPS状态为可见状态");
				break;
			case LocationProvider.OUT_OF_SERVICE:
				// BBKDebug.ddd("当前GPS状态为服务区外状态");
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				// BBKDebug.ddd("当前GPS状态为暂停服务状态");
				break;
			}
		}

		@Override
		public void onProviderEnabled(String provider) {// GPS开启时触发
			GpsUpdate(lm.getLastKnownLocation(provider));
		}

		@Override
		public void onProviderDisabled(String provider) {// GPS禁用时触发
			GpsUpdate(null);
		}

	};
	private GpsStatus.Listener ls = new GpsStatus.Listener() {// 状态监听
		public void onGpsStatusChanged(int event) {
			switch (event) {
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_FIRST_FIX:// 第一次定位
				// BBKDebug.ddd("第一次定位");
				g.K = true;
				break;
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:// 卫星状态改变
				// BBKDebug.ddd("卫星状态改变");
				// ----------------------------------------------------
				GpsStatus status = lm.getGpsStatus(null); // 取当前状态
				updateGpsStatus(event, status);
				// ----------------------------------------------------
				break;
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_STARTED:// 定位启动
				GpsFalse();// BBKDebug.ddd("定位启动");
				break;
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_STOPPED:// 定位结束
				GpsFalse();// BBKDebug.ddd("定位结束");
				break;
			// ----------------------------------------------------
			}
			// ----------------------------------------------------
		};
	};

	// ====================================================================================
	// ###############################自动开启设置GPS开关###################################
	// ====================================================================================
	private void toggleGPS(Activity pthis) {
		// ---------------------------------------------------------------------
		Intent gps = new Intent();
		gps.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		gps.addCategory("android.intent.category.ALTERNATIVE");
		gps.setData(Uri.parse("custom:3"));
		// ---------------------------------------------------------------------
		try {
			// -----------------------------------------------------------------
			PendingIntent.getBroadcast(pthis, 0, gps, 0).send();
			Thread.sleep(100);
			// -----------------------------------------------------------------
		} catch (CanceledException e) {
		} catch (InterruptedException e) {
		}
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################GPS数学模型###########################################
	// ####################################################################################
	// ====================================================================================
	private void GpsUpdate(Location loc) {
		if (loc != null) {
			// -------------------------------------------------------------------
			// g.Y = loc.hasAccuracy();//
			// loc.hasAltitude()/loc.hasBearing()/loc.hasSpeed();
			// -------------------------------------------------------------------
			g.t = new Date(loc.getTime());// 时间
			g.w = getDouble(loc.getLatitude(), 6);// 纬度
			g.j = getDouble(loc.getLongitude(), 6);// 经度
			g.h = getDouble(loc.getAltitude(), 0);// 海拔
			g.r = g.K ? (int) loc.getAccuracy() : 0;// 精度
			g.f = g.K ? getDouble(loc.getBearing(), 0) : 0;// 方位
			g.v = g.K ? getDouble(loc.getSpeed() * 3.6, 1) : 0;// 速度
			// -------------------------------------------------------------------
			g.R = g.K ? GpsRuns() : false;
			// -------------------------------------------------------------------
			GpsInfos(0, 0, 0, false);
			// -------------------------------------------------------------------
			new Thread(new Runnable() {
				@Override
				public void run() {
					// -----------------------------------
					myGpsUpdateWork.work();
					// -----------------------------------
				}
			}).start();
			// -------------------------------------------------------------------
		} else {
			g.K = false;// 失效时触发
			g.r = 0;
			g.v = 0;
		}
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################Gps_Math_GPS数学模型###################################
	// ####################################################################################
	// ====================================================================================
	@SuppressLint("SimpleDateFormat")
	private final SimpleDateFormat gpsTmFt = new SimpleDateFormat("HH:mm:ss");
	private final double GpsMinR = 0.00001;// 记录轨迹点的最小范围
	// ---------------------------------------------------------------

	public class GPS {
		// -----------------------------------------
		public boolean K;// 是否定位
		public boolean R;// 是否合理移动
		public double m;// 漂移值
		// -----------------------------------------
		public Date t;// GPS时间
		// -----------------------------------------
		public double w;// 纬度
		public double j;// 经度
		public double h;// 海拔
		public double v;// 速度
		public double f;// 方向
		// -----------------------------------------
		public int r;// 定位精度
		public int s;// 卫星数目
		public int u;// 参与解算卫星数目
		public int snr;// 卫星平均信号值
		public int usr;// 参与解算卫星数目
		// -----------------------------------------
		public double lw;// 上一次纬度
		public double lj;// 上一次经度
		// -----------------------------------------
		public double l;// 里程
		public double va;// 平均速度
		public double vm;// 最大速度
		// -----------------------------------------
		public Date ts;// 启动时间
		public double tl;// 持续时间
		public String tls;// 持续时间字符形式
		// -----------------------------------------
		public String a;// 标题
		public String i;// 信息
		public String vs;// 速度
		// -----------------------------------------
	}

	private void GpsFirst() {
		// ----------------------------------------------------
		g.K = false;
		g.R = false;
		g.m = 0;
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

	private void GpsFalse() {
		// ----------------------------------------------------
		g.K = false;
		g.R = false;
		g.v = 0;
		g.r = 0;
		g.m = 0;
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
		// double dks = BBKMapMath.GetDistance(g.w, g.j, mapw, mapj);
		g.i = "[+]" + mapw + "," + mapj + "," + BBKMapMath.GetDistance(g.w, g.j, mapw, mapj) + "km\r\n";
		if (g.K || more) {
			g.i += "[g]" + g.w + "," + g.j + "," + g.h + "m\r\n";
			g.i += Orientation_To_String(compass) + " " + (int) compass;
			g.i += "/" + (int) g.f + "\r\n";
			g.i += "s=" + g.l + " km" + "/" + g.tls + " " + "\r\n";
			g.i += "v=" + g.va + " / " + g.vm + " km/h" + "\r\n";
		}
		// ---------------------------------------------------------------------
	}

	private boolean GpsRuns() {
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
		g.tls += get_DFM_String(g.tl);
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
		// if (Math.abs(g.lw - g.w) < GpsMinR && Math.abs(g.lj - g.j) < GpsMinR)
		// return false;
		// -----------------------------------------------------------------------
		g.m = GetDistance(g.w, g.j, g.lw, g.lj);
		// -----------------------------------------------------------------------
		if (g.m < GpsMinR)
			return false;
		// -----------------------------------------------------------------------
		g.l += g.m;
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
	// #############################GPS卫星状态监听_数学模型#################################
	// ####################################################################################
	// ====================================================================================
	private List<GpsSatellite> numSatelliteList = new ArrayList<GpsSatellite>(); // 卫星信号
	private Iterator<GpsSatellite> it;
	private int GpsCount = 0;
	private int GpsUseds = 0;
	private int GpsMax = 0;
	private int GpsAllSnr = 0;
	private int GpsUseSnr = 0;

	private void updateGpsStatus(int event, GpsStatus status) {
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
	public class DFM_type {// 度_分_秒__模型
		public int d = 0, f = 0;// 度分
		public double s = 0.0;// 秒
		public double ddd = 0.0;// DD.DDDDD

		public DFM_type(double _ddd) {
			this.ddd = _ddd;// dfs2d(d, f, s);
			d = (int) (Math.floor(ddd));
			f = (int) (Math.floor(ddd * 60f - d * 60f));
			s = Math.floor((ddd * 3600f - d * 3600f - f * 60f) * 100) / 100;
			// --------------------------------------------
		}

		public void DFM_to_DDD(double dx, double fx, double sx) {
			ddd = dx + fx / 60f + sx / 3600f;
			ddd = getDouble(ddd, 6);
		}
	}

	public String get_DFM_String(double t) {// 度.度度度__转__度_分_秒
		DFM_type d = new DFM_type(t);
		String s = "";
		s += d.d + "时 ";
		s += d.f + "分 ";
		s += (int) d.s + "秒";
		return s;
	}

	// =================角度文字换算==============================================
	public String Orientation_To_String(float degree) {
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

	// =========================================================================
	public double getDouble(double a, int Decimals) {// 获取小数精度
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

	// ==================地球基础信息============================================
	private double EARTH_RADIUS = 6378137; // 米，地球大概半径6371.004千米
	// =================里程计算函数=============================================

	public double GetDistance(double lat1, double lng1, double lat2, double lng2) {// 输出是公里
		double r1, r2, a, b, s = 0;
		// ----------------------------------------------------------------------
		try {
			// ------------------------------------------------------------------
			r1 = rad(lat1);
			r2 = rad(lat2);
			a = r1 - r2;
			b = rad(lng1) - rad(lng2);
			s = 2 * Math.asin(Math
					.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(r1) * Math.cos(r2) * Math.pow(Math.sin(b / 2), 2)));
			s = Math.round(s * EARTH_RADIUS) / 1000.0;
			// ------------------------------------------------------------------
		} catch (Exception e) {
		}
		// ----------------------------------------------------------------------
		return s;
		// ----------------------------------------------------------------------
	}

	// =================角度转弧度函数============================================
	private double TempFxPI = Math.PI / 180.0;

	private double rad(double d) {
		double dbRet = 0;
		try {
			dbRet = d * TempFxPI;
		} catch (Exception e) {
		}
		return dbRet;
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################GPS数学模型###########################################
	// ####################################################################################
	// ====================================================================================
}
