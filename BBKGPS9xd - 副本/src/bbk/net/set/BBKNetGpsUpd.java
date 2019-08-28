﻿package bbk.net.set;

import java.util.Date;

import android.os.Handler;
import bbk.bbk.box.BBKSoft;
import bbk.map.gps.BBK_Tool_GPS.GPS;
import bbk.net.abc.BBKHttpGet;
import bbk.sys.abc.BBKMsgBox;
import bbk.zzz.debug.bd;

public class BBKNetGpsUpd {

	// ===============================================================
	private String myAsk = "", myjson = "";
	public GpsSql[] myG = new GpsSql[300];

	// ===============================================================

	public void BBKNetGpsUpdInt() {
		// ---------------------------------------------
		for (int i = 0; i < myG.length; i++) {
			GpsLogInt(i);
		}
		// ---------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################GpsUpload#############################################
	// ####################################################################################
	// ====================================================================================
	public void GpsUpRunThread() {
		// --------------------------------------------------
		new Thread() {
			public void run() {
				// ------------------------------------------
				try {
					// --------------------------------------
					GpsUpRun();
					// --------------------------------------
				} catch (Exception e) {
				}
				// ------------------------------------------
			}
		}.start();
		// --------------------------------------------------
	}

	private void GpsUpRun() {
		// --------------------------------------------------
		long tkey = System.currentTimeMillis();
		String gpss = GetGpsStr(tkey);
		// --------------------------------------------------
		if (gpss.length() < 10) {
			myjson = "GpsNoDate";
		} else {
			// --------------------------------------------------
			myAsk = GpsUpUrl(gpss, tkey);
			myjson = BBKHttpGet.BBKHttpGetUrl(myAsk, BBKSoft.myNetSet.gbCode, false);
			// ------------------------------------------------------
			if (myjson.length() > 10) {
				ClsGps(myjson);
			}
			// ------------------------------------------------------
			handlerAsk.post(RunnableAsk);
			// ------------------------------------------------------
		}
		// ------------------------------------------------------
		bd.d("GpsBack= " + myjson, false, false);
		ShowGps();
		// ------------------------------------------------------
	}

	private String GpsUpUrl(String gpss, long tkey) {
		String myAsk = "";
		myAsk = "http://";
		myAsk += BBKSoft.myNetSet.GpsWHost + "/";
		myAsk += BBKSoft.myNetSet.GpsWPath;
		myAsk += "?n=" + BBKSoft.myNetSet.UserName;
		myAsk += "&p=" + BBKSoft.myNetSet.UserPass;
		myAsk += "&t=" + tkey;
		myAsk += "&g=" + gpss;
		bd.d(myAsk, false, false);
		return myAsk;
	}

	// ====================================================================================
	// ####################################################################################
	// #############################GpsSql#################################################
	// ####################################################################################
	// ====================================================================================

	private long GpsAddLastTime = 0;
	private long GpsAddSqlsTime = 5 * 1000;// 5s
	private long GpsUpClearTime = 60 * 1000;// 1分钟

	public boolean AddGps(GPS g) {
		// ---------------------------------------------
		if (System.currentTimeMillis() - GpsAddLastTime < GpsAddSqlsTime) {
			return false;
		}
		// ---------------------------------------------
		ClsGpsTimeOut();
		// ---------------------------------------------
		for (int i = 0; i < myG.length; i++) {
			if (myG[i].p == 0) {
				myG[i] = new GpsSql(g.w, g.j, g.h, g.v, g.f, g.r, g.t);
				myG[i].p = 1;
				GpsAddLastTime = System.currentTimeMillis();
				// BBKDebug.ddd("AddGps= " + i + " = " + g.t.getTime());
				return true;
			}
		}
		return false;
		// ---------------------------------------------
	}

	public void ClsGps(String bk) {
		// ---------------------------------------------
		long px = Long.valueOf(bk).longValue();
		// ---------------------------------------------
		for (int i = 0; i < myG.length; i++) {
			if (myG[i].p == px) {
				GpsLogInt(i);
			}
		}
		// ---------------------------------------------
	}

	public boolean ClsGpsTimeOut() {
		// ---------------------------------------------
		for (int i = 0; i < myG.length; i++) {
			// ---------------------------------------------
			if (myG[i].p > 1) {
				// ---------------------------------------------
				if (System.currentTimeMillis() - myG[i].p > GpsUpClearTime) {
					myG[i].p = 1;
					// BBKDebug.ddd("ReUpLoad = " + i);
				}
				// ---------------------------------------------
			}
			// ---------------------------------------------
		}
		return true;
		// ---------------------------------------------
	}

	public String GetGpsStr(long t) {
		// ---------------------------------------------
		int n = 0;
		String s = "";
		// ---------------------------------------------
		for (int i = 0; i < myG.length; i++) {
			// ---------------------------------------------
			if (myG[i].p == 1) {
				// ---------------------------------------------
				myG[i].p = t;
				s += myG[i].w + "," + myG[i].j + "," + myG[i].h + "," + myG[i].v + ",";
				s += myG[i].f + "," + +myG[i].r + "," + myG[i].t.getTime() + ";";
				// ---------------------------------------------
				n++;
				// ---------------------------------------------
			}
			if (n >= 10) {
				return s;
			}
			// ---------------------------------------------
		}
		return s;
		// ---------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################GpsSql#################################################
	// ####################################################################################
	// ====================================================================================

	// ====================================================================================
	// ####################################################################################
	// #############################GpsUpload#############################################
	// ####################################################################################
	// ====================================================================================

	private Handler handlerAsk = new Handler();
	private Runnable RunnableAsk = new Runnable() {
		public void run() {
			// ------------------------------------------------------
			BBKMsgBox.tShow(myjson);
			BBKSoft.myNetSet.GpsUpdBack.setText(myjson);
			// ------------------------------------------------------
		}
	};

	public String ShowGps() {
		// ---------------------------------------------
		String s = "";
		// ---------------------------------------------
		for (int i = 0; i < myG.length; i++) {
			if (myG[i].p != 0) {
				// ---------------------------------------------
				s += myG[i].w + "," + myG[i].j + "," + myG[i].h + ",";
				s += myG[i].v + "," + myG[i].f + "," + myG[i].t.getTime() + ",";
				s += myG[i].p + ";\r\n";
				// ---------------------------------------------
			}
		}
		// BBKDebug.ddd(s);
		BBKSoft.myBoxs.LabInf.setText(s);
		return s;
		// ---------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################GpsSql#################################################
	// ####################################################################################
	// ====================================================================================
	class GpsSql {
		public double w, j, h, v, f, r;
		public Date t;
		public long p;

		public GpsSql(double _w, double _j, double _h, double _v, double _f, double _r, Date _t) {
			w = _w;
			j = _j;
			h = _h;
			v = _v;
			f = _f;
			r = _r;
			t = _t;
			p = 0;
		}
	}

	private void GpsLogInt(int i) {
		// ---------------------------------------------
		myG[i] = new GpsSql(0, 0, 0, 0, 0, 0, new Date(0));
		// ---------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################GpsSql#################################################
	// ####################################################################################
	// ====================================================================================

}
