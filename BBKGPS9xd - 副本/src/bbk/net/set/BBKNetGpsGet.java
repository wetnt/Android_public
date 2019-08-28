package bbk.net.set;

import java.util.Random;

import bbk.bbk.box.BBKSoft;
import bbk.map.lay.BBKMapLayJson;
import bbk.net.abc.BBKHttpGet;
import bbk.zzz.debug.bd;

public class BBKNetGpsGet {
	// ====================================================================================
	// ####################################################################################
	// #############################地图刷新###############################################
	// ####################################################################################
	// ====================================================================================

	// public boolean GpsGetRunKey = false;
	// // ===============================================================
	// public String WebHost = "www.boboking.com/v";
	// public String WebPath = "gps.php?";
	// // ===============================================================
	// public final String gbCode = "UTF-8";// "GB2312";

	public String GetUerID = "10000";
	public String GetUerLn = "10";

	private String myAsk = "", myjson = "";

	public void GpsLogGetThread() {
		// --------------------------------------------------
		new Thread() {
			public void run() {
				// ------------------------------------------
				try {
					// --------------------------------------
					GpsLogGetRun();
					// --------------------------------------
				} catch (Exception e) {
				}
				// ------------------------------------------
			}
		}.start();
		// --------------------------------------------------
	}

	private void GpsLogGetRun() {
		// ----------------------------------------------------------------
		// bbk_php/bbk_get_v9lg_bjs.php?&id=10000&ln=10&ta=0&tb=0&rnd=156
		// ----------------------------------------------------------------
		myAsk = "http://";
		myAsk += BBKSoft.myNetSet.GpsWHost + "/";
		myAsk += "bbk_php/bbk_get_v9lg_bjs.php?";
		myAsk += "&id=" + GetUerID;
		myAsk += "&ln=" + GetUerLn;
		myAsk += "&ta=0&tb=0";
		myAsk += "rnd=" + GetRandInt();
		bd.d(myAsk, false, false);
		// --------------------------------------------------
		myjson = BBKHttpGet.BBKHttpGetUrl(myAsk, BBKSoft.myNetSet.gbCode, false);
		// ------------------------------------------------------
		bd.d(myjson, false, false);
		BBKSoft.myLays.laytmp.clone(BBKMapLayJson.BBKMapLayFromJson(myjson));
		// ------------------------------------------------------
		// handlerAsk.post(RunnableAsk);
		// ------------------------------------------------------
	}

	// ====================================================================================
	private Random random = new Random();

	private int GetRandInt() {
		return Math.abs(random.nextInt() % 100);
	}

	// ====================================================================================
	// ####################################################################################
	// #############################地图刷新###############################################
	// ####################################################################################
	// ====================================================================================
}
