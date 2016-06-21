package bbk.map.gps;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;
import bbk.bbk.box.BBKSoft;

public class BBKGps {

	// ---------------------------------------------------------------
	Activity bbkAct;
	private LocationManager lm;
	public BBKGpsMath gm = new BBKGpsMath();
	public boolean GpsIsRun = false;

	// ====================================================================================
	// ####################################################################################
	// ##############################GPS更新设置###########################################
	// ####################################################################################
	// ====================================================================================
	private void updateGPS(Location loc) {// 实时更新
		if (loc != null) {
			// -----------------------------------------------------------
			gm.GpsUpdata(loc);
			BBKSoft.MapGpsRuns();
			// -----------------------------------------------------------
		} else {
			// -----------------------------------------------------------
			gm.g.Y = false;// 失效时触发
			gm.g.r = 0;
			gm.g.v = 0;
			// -----------------------------------------------------------
		}
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################GPS开关###############################################
	// ####################################################################################
	// ====================================================================================
	public boolean GpsInt(final Activity pthis) {
		// ---------------------------------------------------------------------
		bbkAct = pthis;
		gm.GpsFirst();
		GpsIsRun = false;
		return GpsStart();
		// ---------------------------------------------------------------------
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
	// ####################################################################################
	// ##############################位置监听##############################################
	// ####################################################################################
	// ====================================================================================
	private LocationListener lg = new LocationListener() {
		public void onLocationChanged(Location location) {// 位置信息变化时触发
			updateGPS(location);
		}

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

		public void onProviderEnabled(String provider) {// GPS开启时触发
			Location location = lm.getLastKnownLocation(provider);
			updateGPS(location);
		}

		public void onProviderDisabled(String provider) {// GPS禁用时触发
			updateGPS(null);
		}
	};
	// ====================================================================================
	// ####################################################################################
	// ###########################GPS状态监听##############################################
	// ####################################################################################
	// ====================================================================================
	GpsStatus.Listener ls = new GpsStatus.Listener() {
		public void onGpsStatusChanged(int event) {
			switch (event) {
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_FIRST_FIX:// 第一次定位
				// BBKDebug.ddd("第一次定位");
				break;
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:// 卫星状态改变
				// BBKDebug.ddd("卫星状态改变");
				// ----------------------------------------------------
				GpsStatus status = lm.getGpsStatus(null); // 取当前状态
				gm.updateGpsStatus(event, status);
				// ----------------------------------------------------
				break;
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_STARTED:// 定位启动
				gm.GpsClose();
				// BBKDebug.ddd("定位启动");
				break;
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_STOPPED:// 定位结束
				gm.GpsClose();
				// BBKDebug.ddd("定位结束");
				break;
			// ----------------------------------------------------
			}
			// ----------------------------------------------------
		};
	};

	// ====================================================================================
	// ####################################################################################
	// ###########################GPS查询条件##############################################
	// ####################################################################################
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
	// ###############################消息显示#############################################
	// ====================================================================================
	public void ToastShow(String str) {
		Toast.makeText(bbkAct, str, Toast.LENGTH_SHORT).show();
	}
	// ====================================================================================

}