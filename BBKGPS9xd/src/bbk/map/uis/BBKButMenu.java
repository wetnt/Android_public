package bbk.map.uis;

import com.example.bbkgps9xd.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import bbk.bbk.box.BBKSoft;
import bbk.bbk.box.BBKAbout;
import bbk.map.gps.BBKGpsReport;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.sys.abc.BBKMsgBox;
import bbk.sys.abc.BBKSavePathSelect;
import bbk.sys.file.FileManager;
import bbk.zzz.debug.bd;

public class BBKButMenu {

	// -------------------------------------------------------------------------
	private Activity bbkAct;
	static int BtnDn = Color.MAGENTA;
	static int BtnUp = Color.TRANSPARENT;
	public static final int FILE_RESULT_CODE = 1;

	// -------------------------------------------------------------------------

	// ====================================================================================
	public Menu onCreateMenu(Context context, Menu menu) {
		// ------------------------------------------------------------------------------------------
		bbkAct = (Activity) context;
		// -------------------------------------------------------
		// menu.add方法的参数：
		// 第一个int类型的group ID参数，代表的是组概念。
		// 第二个int类型的item ID参数，代表的是项目编号。
		// 第三个int类型的order ID参数，代表的是菜单项的显示顺序。
		// 第四个String类型的title参数，表示选项中显示的文字。
		// -------------------------------------------------------
		MenuItemAdd(0, menu, "收藏", R.drawable.menu_bookmark);
		MenuItemAdd(0, menu, "查询", R.drawable.menu_search);
		MenuItemAdd(0, menu, "轨迹", R.drawable.menu_refreshtimer);
		MenuItemAdd(0, menu, "列表", R.drawable.menu_select_all);
		MenuItemAdd(0, menu, "退出", R.drawable.menu_quit);
		// ---------------------------------------------
		MenuItemAdd(1, menu, "GPS-开关", R.drawable.menu_checkupdate);
		MenuItemAdd(1, menu, "GPS-校时", R.drawable.menu_refreshtimer);
		MenuItemAdd(1, menu, "里程信息", R.drawable.menu_pagefind);
		// ---------------------------------------------
		MenuItemAdd(2, menu, "切换地图", R.drawable.menu_syssettings);
		MenuItemAdd(2, menu, "图层清空", R.drawable.menu_delete);
		MenuItemAdd(2, menu, "经纬定位", R.drawable.menu_outfullscreen);
		MenuItemAdd(2, menu, "手绘测距", R.drawable.menu_edit);
		MenuItemAdd(2, menu, "查询历史", R.drawable.menu_attr);
		MenuItemAdd(2, menu, "载入资源", R.drawable.menu_open_in_newwindow);
		// ---------------------------------------------
		MenuItemAdd(3, menu, "屏幕亮度", R.drawable.menu_day);
		MenuItemAdd(3, menu, "存储设置", R.drawable.menu_debug);
		MenuItemAdd(3, menu, "地图设置", R.drawable.menu_syssettings);
		MenuItemAdd(3, menu, "网络设置", R.drawable.menu_debug);
		// ---------------------------------------------
		MenuItemAdd(4, menu, "地图火星开关", R.drawable.menu_downmanager);
		MenuItemAdd(4, menu, "地图下载开关", R.drawable.menu_downmanager);
		MenuItemAdd(4, menu, "地图中心补洞", R.drawable.menu_zoommode);
		MenuItemAdd(4, menu, "地图全屏下载", R.drawable.menu_download_image);
		// ---------------------------------------------
		MenuItemAdd(5, menu, "BBKGPS.关于", R.drawable.menu_input_pick_inputmethod);
		MenuItemAdd(5, menu, "BBKGPS.主页", R.drawable.menu_debug);
		// ---------------------------------------------
		return menu;
		// ---------------------------------------------
	}

	public MenuItem onItemSelected(MenuItem item) {
		// ---------------------------------------------
		String s = item.getTitle().toString();
		// ===============================================================
		if (s == "收藏")
			BBKSoft.myFav.layshow(true);
		// ---------------------------------------------
		if (s == "查询")
			BBKSoft.myAsk.layshow(true);
		if (s == "查询历史")
			BBKSoft.myAsH.layshow(true);
		// ---------------------------------------------
		if (s == "轨迹")
			BBKSoft.myTrk.layshow(true);
		// ---------------------------------------------
		if (s == "退出")
			BBKGpsReport.AlertDialogExit(bbkAct);
		// ---------------------------------------------
		if (s == "列表") {
			BBKSoft.myList.layshow(true);
		}
		// ---------------------------------------------
		if (s == "经纬定位") {
			BBKSoft.myMove.layshow(true);
			BBKSoft.myMove.SetEditMapJW(BBKSoft.myMaps.mapPt.w, BBKSoft.myMaps.mapPt.j);
		}
		// ---------------------------------------------
		if (s == "切换地图") {
			BBKMsgBox.tShow("切换地图！");
			BBKSoft.myMaps.MapTypeSet();
			BBKSoft.MapFlash(true);
		}
		// ---------------------------------------------
		if (s == "图层清空") {
			BBKMsgBox.tShow("图层清空！");
			BBKSoft.myLays.LayClears();
			BBKSoft.MapFlash(false);
		}
		// ---------------------------------------------
		if (s == "手绘测距") {
			BBKMsgBox.tShow("点击屏幕量算距离！");
			BBKSoft.myMeasure.layshow(true);
			BBKSoft.myLays.laymes.line.add(new line_type());
			BBKSoft.myMeasure.MeasureLayShow = true;
			BBKSoft.myMeasure.LineMeasureShow();
		}
		// ---------------------------------------------
		if (s == "屏幕亮度")
			BBKSoft.myLight.layshowe();
		// ---------------------------------------------
		if (s == "GPS-开关")
			BBKSoft.GPSOpenCloseRun();
		if (s == "GPS-校时")
			BBKGpsSYSTime();
		if (s == "里程信息")
			BBKGpsReport.GpsInfoShow();
		// ---------------------------------------------
		if (s == "地图设置") {
			BBKSoft.myZoomSet.layshow(true);
			BBKSoft.myZoomSet.SetLoad();
		}
		// ---------------------------------------------
		if (s == "网络设置") {
			BBKSoft.myNetSet.layshow(true);
		}
		// ---------------------------------------------
		if (s == "存储设置") {
			BBKSavePathSelect.SetSoftPathEmpty(bbkAct);
			bd.d("请重新开启软件！", true, false);
		}
		// ---------------------------------------------
		if (s == "地图火星开关") {
			BBKRegKey();
			BBKSoft.MapFlash(true);
		}
		// ---------------------------------------------
		if (s == "地图下载开关") {
			BBKDownKey();
			BBKSoft.MapFlash(true);
		}
		// ---------------------------------------------
		if (s == "地图中心补洞") {
			BBKMsgBox.tShow("地图中心空洞补充下载！");
			BBKSoft.myMaps.BBKDownCenterPic();// 补洞
		}
		// ---------------------------------------------
		if (s == "地图全屏下载") {
			BBKSoft.myZoomSet.BBKScreenDown();
		}
		// =====================================================================
		if (s == "BBKGPS.关于") {
			BBKAbout.SoftAboutShow();
		}
		// ---------------------------------------------
		if (s == "BBKGPS.主页") {
			BBKMsgBox.tShow("正在开启主页！");
			BBKWebHome();
		}
		// =====================================================================
		if (s == "载入资源") {
			// ---------------------------------------------
			Intent intent = new Intent(bbkAct, FileManager.class);
			bbkAct.startActivityForResult(intent, FILE_RESULT_CODE);
			// ---------------------------------------------
		}
		// =====================================================================
		return item;
		// ---------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	int menutid = 1, menuoid = 0;

	public int MenuItemAdd(int GroupId, Menu menu, String title, int ico) {
		menutid++;
		menuoid++;
		menu.add(GroupId, menutid, menuoid, title).setIcon(ico);
		return menutid;
	}

	// ====================================================================================
	// ====================================================================================

	// ====================================================================================
	public void BBKDownKey() {
		// --------------------------------------------
		if (!BBKSoft.SoftHttpCheck())
			return;
		// --------------------------------------------
		boolean ys = BBKSoft.myMaps.MapDownKeyClick(-1);
		BBKMsgBox.tShow("MapDown = " + ys);
		// ---------------------------------------------
	}

	// ====================================================================================
	public void BBKRegKey() {
		// --------------------------------------------
		BBKSoft.myMaps.MapRegKey = !BBKSoft.myMaps.MapRegKey;
		BBKMsgBox.tShow("BBKRegKey = " + BBKSoft.myMaps.MapRegKey);
		// ---------------------------------------------
	}

	// ====================================================================================
	public void BBKWebHome() {
		// --------------------------------------------
		if (!BBKSoft.SoftHttpCheck())
			return;
		// ---------------------------------------------
		String url = "http://www.bbkgps.com";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		bbkAct.startActivity(i);
		// ---------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	public void BBKGpsSYSTime() {
		// ---------------------------------------------
		BBKSoft.myRoot.BBKSysTimeGpsSync(BBKSoft.myGps.gm.g.t.getTime(), BBKSoft.myGps.gm.g.Y);
		// ---------------------------------------------
	}
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================

}
