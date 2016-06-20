package bbk.bbk.box;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import bbk.hrd.abc.BBKACC;
import bbk.hrd.abc.BBKBody;
import bbk.hrd.abc.BBKMagnetic;
import bbk.map.abc.BBKMap;
import bbk.map.abc.BBKMap.MapPoiWJ;
import bbk.map.ask.BBKBaiDu;
import bbk.map.ask.BBKGoogle;
import bbk.map.gps.BBKGps;
import bbk.map.lay.BBKLay;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.map.lay.BBKMapLayShow;
import bbk.map.lay.BBKMapLay.poi_type;
import bbk.map.set.BBKGpsNetSet;
import bbk.map.set.BBKMapZoomSet;
import bbk.map.uis.BBKButMenu;
import bbk.map.uis.Map_Button;
import bbk.map.uis.BBKMenuView;
import bbk.map.uis.Main_List;
import bbk.map.uis.Main_Ask;
import bbk.map.uis.Main_AskHis;
import bbk.map.uis.Main_Fav;
import bbk.map.uis.Main_ScreenLight;
import bbk.map.uis.Main_Track;
import bbk.map.uis.Map_Measure;
import bbk.map.uis.Map_Move;
import bbk.net.abc.BBKNetCheck;
import bbk.net.set.BBKNetGpsGet;
import bbk.net.set.BBKNetGpsUpd;
import bbk.sys.abc.BBKMsgBox;
import bbk.sys.abc.BBKRoot;
import bbk.sys.abc.BBKSYS;
import bbk.sys.abc.BBKSdCard;
import bbk.sys.abc.BBKTimer;
import bbk.zzz.debug.BBKDebug;

public class BBKSoft {

	public static Context bbkContext;

	public void BBKInt(final Context ctxt) {
		bbkContext = ctxt;
	}

	// ====================================================================================
	public static String PathSD, PathSoft;
	public static String PathSets, PathTemp, PathMaps;
	public static String PathBbjs, PathBbts, PathAsks, PathPics;
	// ====================================================================================
	// ####################################################################################
	// ##############################程序设置##############################################
	// ####################################################################################
	// ====================================================================================
	// ----------------------------------------------------
	public static BBKTimer myTime = new BBKTimer();
	// ----------------------------------------------------
	public static BBKButMenu myMenu = new BBKButMenu();
	public static BBKMenuView myMenux = new BBKMenuView();
	public static Map_Button myBtns = new Map_Button();
	public static BBKMsgBox myMsg = new BBKMsgBox();
	// ----------------------------------------------------
	public static BBKMapBox myBoxs = new BBKMapBox();
	public static BBKCompass myCmps = new BBKCompass();
	public static BBKMap myMaps = new BBKMap();
	public static BBKLay myLays = new BBKLay();
	public static BBKMapView myView = new BBKMapView();
	// ----------------------------------------------------
	public static BBKGps myGps = new BBKGps();
	// ----------------------------------------------------
	public static Map_Measure myMeasure = new Map_Measure();
	public static Map_Move myMove = new Map_Move();
	// ----------------------------------------------------
	public static BBKGoogle myGoog = new BBKGoogle();
	public static BBKBaiDu myBaiD = new BBKBaiDu();
	public static Main_Ask myAsk = new Main_Ask();
	public static Main_AskHis myAsH = new Main_AskHis();
	// ----------------------------------------------------
	public static Main_Fav myFav = new Main_Fav();
	public static Main_Track myTrk = new Main_Track();
	public static Main_List myList = new Main_List();
	// ----------------------------------------------------
	public static Main_ScreenLight myLight = new Main_ScreenLight();
	// ----------------------------------------------------
	public static BBKRoot myRoot = new BBKRoot();
	public static BBKACC myAccs = new BBKACC();
	public static BBKMagnetic mySens = new BBKMagnetic();
	// ----------------------------------------------------
	public static BBKMapZoomSet myZoomSet = new BBKMapZoomSet();
	public static BBKGpsNetSet myNetSet = new BBKGpsNetSet();
	public static BBKNetGpsUpd myGpsUpd = new BBKNetGpsUpd();
	public static BBKNetGpsGet myGpsGet = new BBKNetGpsGet();

	// ----------------------------------------------------
	// ----------------------------------------------------

	public void BBKSoftInt() {
		// ----------------------------------------------------
		BBKAbout.BBKAboutSet();
		BBKSoftPathSet();
		BBKDebug.MyInt();
		// ----------------------------------------------------
		BBKSYS.bbtName(PathBbts);
		// ----------------------------------------------------
		myBoxs.LayInt(bbkContext);
		myCmps.LayInt(bbkContext);
		myMaps.MapInt(myBoxs.screenW, myBoxs.screenH, PathMaps);
		BBKDebug.d("WxH = " + myBoxs.screenW + "x" + myBoxs.screenH, true, true);
		myLays.LayInt();
		myView.ImgViewTouchMainSet();
		// ----------------------------------------------------
		mySens.SensorInt(bbkContext);
		mySens.SensorRegister();
		// ----------------------------------------------------
		GPSOpenRun();
		// ----------------------------------------------------
		myLays.LaysFavLoad();
		// ----------------------------------------------------
		myBtns.ButtonSet(bbkContext);
		myBtns.layshow(true);
		// ----------------------------------------------------
		myMove.LayInt(bbkContext);
		myLight.LayInt(bbkContext);
		myMeasure.LayInt(bbkContext);
		// ----------------------------------------------------
		myFav.LayInt(bbkContext);
		myAsk.LayInt(bbkContext);
		myAsH.LayInt(bbkContext);
		myTrk.LayInt(bbkContext);
		myList.LayInt(bbkContext);
		// ----------------------------------------------------
		myMsg.MsgBoxInt(bbkContext);
		// ----------------------------------------------------
		myZoomSet.LayInt(bbkContext);
		myNetSet.LayInt(bbkContext);
		myGpsUpd.BBKNetGpsUpdInt();
		// ----------------------------------------------------
		// FavLayLoad.post(FavLayRun);
		// BBKMap.MapFlash();
		// ----------------------------------------------------
		myTime.TimerStart();
		myAccs.ACCInt(bbkContext);
		// ----------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################心跳启动##############################################
	// ####################################################################################
	// ====================================================================================

	public static boolean BBKLayBack() {
		boolean back = false;
		// ----------------------------------------------------
		if (myMove.layback())
			back = true;
		if (myLight.layback())
			back = true;
		if (myMeasure.layback())
			back = true;
		// ----------------------------------------------------
		if (myFav.layback())
			back = true;
		if (myAsk.layback())
			back = true;
		if (myAsH.layback())
			back = true;
		if (myTrk.layback())
			back = true;
		if (myList.layback())
			back = true;
		// ----------------------------------------------------
		if (myMsg.layback())
			back = true;
		// ----------------------------------------------------
		if (myZoomSet.layback())
			back = true;
		if (myNetSet.layback())
			back = true;
		// ----------------------------------------------------
		return back;
		// ----------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################屏幕方向变换##########################################
	// ####################################################################################
	// ====================================================================================
	public static void BBKScreenOrientRun(int ScreenOrient) {
		// ----------------------------------------------------
		myBoxs.LayReInt();
		myMaps.MapReInt(myBoxs.screenW, myBoxs.screenH);
		MapFlash(true);
		// ----------------------------------------------------
		if (ScreenOrient == 1) {// 竖屏(portrait人像)=1
			myBtns.BtnMenu.setVisibility(View.GONE);
		} else if (ScreenOrient == 2) {// 横屏(landscape风景)=2
			myBtns.BtnMenu.setVisibility(View.VISIBLE);
		}
		// ----------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################心跳启动##############################################
	// ####################################################################################
	// ====================================================================================

	// ====================================================================================
	// ####################################################################################
	// #################################心跳设置###########################################
	// ####################################################################################
	// ====================================================================================
	public static boolean GpsIsFollow = false;
	private static StringBuffer em = new StringBuffer("");
	private static StringBuffer gpsn = new StringBuffer("");
	private static poi_type gpsp = new poi_type(em, em, em, 0, 0, 0, null);

	@SuppressWarnings("static-access")
	public static void TxtGpsRuns() {
		// -------------------------------------------------------------------
		if (true) {// 界面GPS信息显示
			myGps.gm.GpsInfos(myMaps.mapPt.w, myMaps.mapPt.j, mySens.CompassAngle, true);
			myBoxs.LabTit.setText(myGps.gm.g.a);
			myBoxs.LabSpd.setText(myGps.gm.g.vs);
			if (myGps.gm.g.Y) {
				myBoxs.LabInf.setText(myGps.gm.g.i);
			}
		}
		// ------------------------------------------------------------------
		if (myMaps.DownNews) {
			// --------------------------------------------------------------
			myMaps.DownNews = false;
			myBoxs.LabInf.setText(myMaps.DownInfos);
			MapFlash(false);
			// --------------------------------------------------------------
		}
		// -------------------------------------------------------------------
	}

	public static void MapGpsRuns() {
		// ------------------------------------------------------------------
		if (myGps.gm.g.R) {// 轨迹记录
			// ----------------------lay_bjp---------------------------------
			gpsn = new StringBuffer(myGps.gm.g.v + "," + myGps.gm.g.f + "," + myGps.gm.g.t.toString());
			gpsp = new poi_type(em, em, gpsn, myGps.gm.g.w, myGps.gm.g.j, myGps.gm.g.h, myGps.gm.g.t);
			// ----------------------lay_bjp---------------------------------
			myLays.laygps.pois.add(gpsp);
			myLays.laygps.line.get(0).add(myGps.gm.g.w, myGps.gm.g.j);
			// -----------------------bbt------------------------------------
			BBKSYS.appendSaveBBT(myGps.gm.g.w, myGps.gm.g.j, myGps.gm.g.h, myGps.gm.g.t);
			// -----------------------Sql------------------------------------
			// mySql.insertRecord(UserIDid, myGps.gps.w, myGps.gps.j,
			// myGps.gps.h,myGps.gps.v, myGps.gps.f, "G", "0", "0", 0,
			// myGps.gps.t.toString(), "0");
			// --------------------------------------------------------------
		}
		// ------------------------------------------------------------------
		if (myGps.gm.g.R && myGps.gm.g.w != 0 && myGps.gm.g.j != 0) {
			// --------------------------------------------------------------
			// mySqlGps.AddGps(myGps.gm.g.w, myGps.gm.g.j, myGps.gm.g.h,
			// myGps.gm.g.v, myGps.gm.g.f, myGps.gm.g.t);
			// --------------------------------------------------------------
			if (GpsIsFollow) {
				myMaps.MapCenterSet(myGps.gm.g.w, myGps.gm.g.j);
			}
			MapFlash(false);
			// --------------------------------------------------------------
		}
		// ------------------------------------------------------------------
		if (myGps.gm.g.R) {// 信息上传
			// --------------------------------------------------------------
			if (BBKSoft.myNetSet.GpsUpdRunKey) {
				myGpsUpd.AddGps(myGps.gm.g);
			}
			// myNetGps.GpsUpRunThead();
			// mySync.gpsUp.GpsListAdd();
			// mySqlGps.ShowGps();
			// --------------------------------------------------------------
		}
		// ------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################程序路径设置##########################################
	// ####################################################################################
	// ====================================================================================
	public void BBKSoftPathSet() {
		// ----------------------------------------------------
		PathSD = BBKSdCard.BBKSdCardGet();
		// PathSD = BBKSdCard.BBKSdCardGet(bbkContext);
		BBKDebug.d("BBKSoft.BBKSoftPathSet.SdPath = " + PathSD, false, false);
		// ----------------------------------------------------
		PathSoft = PathSD + "/!BBK/";
		BBKSdCard.CheckMakeDir(PathSoft);
		// ----------------------------------------------------
		PathSets = PathSoft + "sets/";
		PathMaps = PathSoft + "maps/";
		PathTemp = PathSoft + "btmp/";
		PathBbts = PathSoft + "bbts/";
		PathBbjs = PathSoft + "bbjs/";
		PathAsks = PathSoft + "bask/";
		PathPics = PathSoft + "bpic/";
		// ----------------------------------------------------
		String[] softpathbox = { PathSets, PathMaps, PathTemp, PathBbts, PathBbjs, PathAsks, PathPics };
		// ----------------------------------------------------
		for (int i = 0; i < softpathbox.length; i++) {
			BBKSdCard.CheckMakeDir(softpathbox[i]);
		}
		// ----------------------------------------------------
		softpathbox = null;
		// ----------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################地图刷新###############################################
	// ####################################################################################
	// ====================================================================================
	private static boolean MapFlashIsRun = false;

	public static void MapFlash(boolean run) {
		if (run) {
			myMaps.MapFlashNew();
			return;
		}
		// ----------------------------------
		if (MapFlashIsRun)
			return;
		MapFlashIsRun = true;
		// ----------------------------------
		new Thread() {// 新刷新线程
			public void run() {
				// --------------------------------------
				MapFlash_Handler.post(MapFlash_Runnable);
				// --------------------------------------
			}
		}.start();
		// ----------------------------------------------------
	}

	private static Handler MapFlash_Handler = new Handler();
	private static Runnable MapFlash_Runnable = new Runnable() {
		public void run() {
			// ----------------------------------
			myMove.MapMove(myMaps.mapPt.w, myMaps.mapPt.j);
			myMaps.MapFlashNew();
			MapFlashIsRun = false;
			// ----------------------------------
		}
	};

	// ====================================================================================
	// ####################################################################################
	// #############################GPS开关################################################
	// ####################################################################################
	// ====================================================================================
	public void GPS_Click() {
		// ----------------------------------
		if (myGps.GpsIsRun) {
			GPS_Click(false);
		} else {
			GPS_Click(true);
		}
		// ----------------------------------
	}

	public void GPS_Click(boolean key) {
		// ----------------------------------
		if (key) {
			myGps.GpsOpen();
		} else {
			myGps.GpsClose();
		}
		// ----------------------------------
		if (myGps.GpsIsRun) {
			BBKMsgBox.tShow("GPS 已开启！");
		} else {
			BBKMsgBox.tShow("GPS 已关闭！");
		}
		// ----------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################震动开关################################################
	// ####################################################################################
	// ====================================================================================
	public void Acc_Click() {
		myAccs.Acc_Click();
	}

	public void Acc_Click(boolean key) {
		// ----------------------------------
		myAccs.Acc_Click(key);
		// ----------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################控件刷新###############################################
	// ####################################################################################
	// ====================================================================================

	// ====================================================================================
	// ####################################################################################
	// #############################程序关闭###############################################
	// ####################################################################################
	// ====================================================================================
	public static void SoftExit() {
		// ----------------------------------------------------
		myMsg.WaitShow(true);
		// ----------------------------------------------------
		new Thread() {
			public void run() {
				// --------------------------------------------
				SoftThreadExit();
				// --------------------------------------------
			}
		}.start();
		// ----------------------------------------------------
	}

	public static void SoftThreadExit() {
		// ------------------------------------------------------------------
		mySens.SensorUnRegister();
		myLays.LaysSave();
		myZoomSet.SetSave();
		myNetSet.SetSave();
		// ------------------------------------------------------------------
		myMsg.WaitShow(false);
		System.exit(0);// 正常退出App
		// ------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################程序关闭###############################################
	// ####################################################################################
	// ====================================================================================
	// ====================================================================================
	// ####################################################################################
	// #############################GPS_OPEN###############################################
	// ####################################################################################
	// ====================================================================================
	public static void GPSOpenCloseRun() {
		if (myGps.GpsIsRun) {
			GPSCloseRun();
		} else {
			GPSOpenRun();
		}
	}

	public static void GPSOpenRun() {
		// -----------------------------------------------------
		myGps.GpsInt((Activity) bbkContext);
		boolean gpsyes = myGps.GpsIsRun;
		if (gpsyes)
			return;
		// -----------------------------------------------------
		String exx = "";
		exx += "GPS未能自动开启！！！ \r\n ";
		exx += "请在设置中手动开启！！！ \r\n ";
		exx += "当前程序将自动关闭！！！";
		BBKMsgBox.tShow(exx);
		// -----------------------------------------------------
		BBKBody.BeepBeep((Activity) bbkContext);
		ExitDlay();
		// -----------------------------------------------------
	}

	public static void GPSCloseRun() {
		// -----------------------------------------------------
		myGps.GpsClose();
		String exx = "GPS关闭";
		BBKMsgBox.tShow(exx);
		// -----------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################程序延时器#############################################
	// ####################################################################################
	// ====================================================================================
	public static Handler handler = new Handler();
	public static Runnable runnable = new Runnable() {
		public void run() {
			// -------------------------------------------------------------------
			System.exit(0);// 正常退出App
			// -------------------------------------------------------------------
		}
	};

	public static void ExitDlay() {
		handler.postDelayed(runnable, 3000); // 开始Timer
	}

	// ====================================================================================
	// ####################################################################################
	// #############################程序延时器#############################################
	// ####################################################################################
	// ====================================================================================

	// ====================================================================================
	// ####################################################################################
	// #############################查找文件返回###########################################
	// ####################################################################################
	// ====================================================================================
	// @Override
	public static final int FILE_RESULT_CODE = 1;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (FILE_RESULT_CODE == requestCode) {
			Bundle bundle = null;
			if (data != null && (bundle = data.getExtras()) != null) {
				// textView.setText("选择文件夹为：\r\n"+bundle.getString("file"));
				BBKDebug.d("选择文件夹为：\r\n" + bundle.getString("file"), true, false);
			}
		}
	}

	// ====================================================================================
	// ####################################################################################
	// #############################协助函数###############################################
	// ####################################################################################
	// ====================================================================================
	public static boolean SoftHttpCheck() {
		return BBKNetCheck.HttpTest(BBKSoft.bbkContext);
	}

	public static void SoftBaiDuiAsk(String str) {
		myBaiD.BaiDuiAskRunThead(str, myMaps.mapPt.w, myMaps.mapPt.j);
	}

	public static void SoftAskHistoryShow() {
		myAsH.layshow(true);
	}

	public static void SoftAskHistoryLoad() {
		myAsH.BJSAllLoadRun();
	}

	public static void MapMove() {
		myMove.MapMove(myMaps.mapPt.w, myMaps.mapPt.j);
		if (!myGps.gm.g.R)
			myBoxs.LabInf.setText(myMaps.mapPt.w + "," + myMaps.mapPt.j);
	}

	public static void MapTouch(int x, int y) {
		myMeasure.MeasureRunTouch(x, y);
	}

	public static void MapToLayCenter(Lay_type lay) {
		// ---------------------------------------------------------
		MapPoiWJ r = BBKMapLayShow.GetLayCenter(lay);
		myMaps.MapCenterSet(r.w, r.j);
		MapFlash(true);
		// ---------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################协助函数###############################################
	// ####################################################################################
	// ====================================================================================

}