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
	// ##############################GPS��������###########################################
	// ####################################################################################
	// ====================================================================================
	private void updateGPS(Location loc) {// ʵʱ����
		if (loc != null) {
			// -----------------------------------------------------------
			gm.GpsUpdata(loc);
			BBKSoft.MapGpsRuns();
			// -----------------------------------------------------------
		} else {
			// -----------------------------------------------------------
			gm.g.Y = false;// ʧЧʱ����
			gm.g.r = 0;
			gm.g.v = 0;
			// -----------------------------------------------------------
		}
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################GPS����###############################################
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
		boolean GpsIsOpen = lm.isProviderEnabled(provider);// �ж�GPS�Ƿ���������
		// ----------------------------------------------------------------------
		if (GpsIsOpen) {
			ToastShow("GPS ������");
		} else {
			toggleGPS(bbkAct);// �Զ���ʼGPS���ÿ���
			ToastShow("�Զ�����GPSģ�飡");
		}
		// ----------------------------------------------------------------------
		GpsIsOpen = lm.isProviderEnabled(provider);
		if (!GpsIsOpen) {
			// ���ؿ���GPS�������ý���
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
		String bestProvider = lm.getBestProvider(getCriteria(), true);// ���ò�ѯ����
		lg.onProviderEnabled(bestProvider);// Ĭ��(LocationManager.GPS_PROVIDER);
		// ------------------------------------------------------------------------------------------
		lm.addGpsStatusListener(ls);// ����״̬
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, lg);// �󶨼���4����
		// ------------------------------------------------------------------------------------------
		// ����1���豸����GPS_PROVIDER��NETWORK_PROVIDER����
		// ����2��λ����Ϣ�������ڣ���λ����
		// ����3��λ�ñ仯��С���룺��λ�þ���仯������ֵʱ��������λ����Ϣ
		// ����4������
		// ��ע������2��3���������3��Ϊ0�����Բ���3Ϊ׼������3Ϊ0����ͨ��ʱ������ʱ���£�����Ϊ0������ʱˢ��
		// 1�����һ�Σ�����Сλ�Ʊ仯����1�׸���һ�Σ�----------------------------
		// ע�⣺�˴�����׼ȷ�ȷǳ��ͣ��Ƽ���service��������һ��Thread��
		// ��run��sleep(10000);Ȼ��ִ��handler.sendMessage(),����λ��
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
	// ##############################λ�ü���##############################################
	// ####################################################################################
	// ====================================================================================
	private LocationListener lg = new LocationListener() {
		public void onLocationChanged(Location location) {// λ����Ϣ�仯ʱ����
			updateGPS(location);
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {// GPS״̬�仯ʱ����
			switch (status) {
			case LocationProvider.AVAILABLE:
				// BBKDebug.ddd("��ǰGPS״̬Ϊ�ɼ�״̬");
				break;
			case LocationProvider.OUT_OF_SERVICE:
				// BBKDebug.ddd("��ǰGPS״̬Ϊ��������״̬");
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				// BBKDebug.ddd("��ǰGPS״̬Ϊ��ͣ����״̬");
				break;
			}
		}

		public void onProviderEnabled(String provider) {// GPS����ʱ����
			Location location = lm.getLastKnownLocation(provider);
			updateGPS(location);
		}

		public void onProviderDisabled(String provider) {// GPS����ʱ����
			updateGPS(null);
		}
	};
	// ====================================================================================
	// ####################################################################################
	// ###########################GPS״̬����##############################################
	// ####################################################################################
	// ====================================================================================
	GpsStatus.Listener ls = new GpsStatus.Listener() {
		public void onGpsStatusChanged(int event) {
			switch (event) {
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_FIRST_FIX:// ��һ�ζ�λ
				// BBKDebug.ddd("��һ�ζ�λ");
				break;
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:// ����״̬�ı�
				// BBKDebug.ddd("����״̬�ı�");
				// ----------------------------------------------------
				GpsStatus status = lm.getGpsStatus(null); // ȡ��ǰ״̬
				gm.updateGpsStatus(event, status);
				// ----------------------------------------------------
				break;
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_STARTED:// ��λ����
				gm.GpsClose();
				// BBKDebug.ddd("��λ����");
				break;
			// ----------------------------------------------------
			case GpsStatus.GPS_EVENT_STOPPED:// ��λ����
				gm.GpsClose();
				// BBKDebug.ddd("��λ����");
				break;
			// ----------------------------------------------------
			}
			// ----------------------------------------------------
		};
	};

	// ====================================================================================
	// ####################################################################################
	// ###########################GPS��ѯ����##############################################
	// ####################################################################################
	// ====================================================================================
	private Criteria getCriteria() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// ��λ��ȷ��,Criteria.ACCURACY_COARSE�Ƚϴ���
		criteria.setCostAllowed(false);// ��Ӫ���շ�
		criteria.setSpeedRequired(true);// �ٶ�
		criteria.setBearingRequired(true);// ��λ
		criteria.setAltitudeRequired(true);// ����
		criteria.setPowerRequirement(Criteria.POWER_LOW);// ��Դ
		return criteria;
	}

	// ====================================================================================
	// ###############################�Զ���������GPS����###################################
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
	// ###############################��Ϣ��ʾ#############################################
	// ====================================================================================
	public void ToastShow(String str) {
		Toast.makeText(bbkAct, str, Toast.LENGTH_SHORT).show();
	}
	// ====================================================================================

}