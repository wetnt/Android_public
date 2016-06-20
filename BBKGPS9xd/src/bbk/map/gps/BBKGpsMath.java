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
		g.t = new Date(loc.getTime());// ʱ��
		g.r = loc.getAccuracy();// ����
		g.w = getDouble(loc.getLatitude(), 100000);// γ��
		g.j = getDouble(loc.getLongitude(), 100000);// ����
		g.h = getDouble(loc.getAltitude(), 10);// ����
		g.f = getDouble(loc.getBearing(), 10);// ��λ
		g.v = getDouble(loc.getSpeed() * 36 / 10, 10);// �ٶ�
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
	private final double GpsMinR = 0.00001;// ��¼�켣�����С��Χ
	public GPS g = new GPS();

	// ---------------------------------------------------------------
	public class GPS {
		// -----------------------------------------
		public boolean Y, R;
		// -----------------------------------------
		public Date t, ts;// GPSʱ��/����ʱ��
		public double tl;// ����ʱ��
		public String tr;// ����ʱ���ַ���ʽ
		// -----------------------------------------
		public double r;// ����
		// -----------------------------------------
		public double w, lw;// γ�ȡ���һ��γ��
		public double j, lj;// ���ȡ���һ�ξ���
		public double h;// ����
		public double f;// ����
		// -----------------------------------------
		public double v;// �ٶ�
		public double va;// ƽ���ٶ�
		public double vm;// ����ٶ�
		public double l;// ���
		// -----------------------------------------
		public String a;// T;// ����
		public String i;// ��Ϣ
		public String vs;// �ٶ�
		// -----------------------------------------
		public int m;// ������Ŀ
		public int u;// �������������Ŀ
		public int snr;// ����ƽ���ź�ֵ
		public int usr;// �������������Ŀ
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
	// ##############################����״̬������########################################
	// ####################################################################################
	// ====================================================================================
	private List<GpsSatellite> numSatelliteList = new ArrayList<GpsSatellite>(); // �����ź�
	private Iterator<GpsSatellite> it;
	private int GpsCount = 0, GpsUseds = 0;
	private int GpsMax = 0, GpsAllSnr = 0, GpsUseSnr = 0;

	public void updateGpsStatus(int event, GpsStatus status) {
		if (status == null) {
			// -----------------------------------------------------------------------
			g.m = 0;// ������Ŀ
			g.u = 0;// �������������Ŀ
			g.snr = 0;// ����ƽ���ź�ֵ
			g.usr = 0;// �������������Ŀ
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
		// ���� ���ǵĸ߶Ƚǡ���λ�ǡ�����ȡ���α����ţ������Ǳ�ţ�
		// satellite.getElevation(); //��������
		// satellite.getAzimuth(); //���Ƿ�λ��
		// satellite.getSnr(); //�����
		// satellite.getPrn(); //α�������������Ϊ���������ǵı��
		// satellite.hasAlmanac(); //��������
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
		s += d.d + "ʱ ";
		s += d.f + "�� ";
		s += (int) d.s + "��";
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
		public int d = 0, f = 0;// �ȷ�
		public double s = 0.0;// ��
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

	// ================= ���������Ϣ ==========================================
	public static double EARTH_RADIUS = 6378137; // �ף������Ű뾶6371.004ǧ��

	// =================��̼��㺯��============================================
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {// ����ǹ���
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

	// =================�Ƕ�ת���Ⱥ���============================================
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

	// =========�Ƕ����ֻ���========================================================
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
			return "����";
		} else if (degree >= 5 && degree < 85) {
			return "����";
		} else if (degree >= 85 && degree <= 95) {
			return "����";
		} else if (degree >= 95 && degree < 175) {
			return "����";
		} else if ((degree >= 175 && degree <= 180) || (degree) >= -180 && degree < -175) {
			return "����";
		} else if (degree >= -175 && degree < -95) {
			return "����";
		} else if (degree >= -95 && degree < -85) {
			return "����";
		} else if (degree >= -85 && degree < -5) {
			return "����";
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