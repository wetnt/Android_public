package bbk.map.uis;

import com.example.bbkgps9xd.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import bbk.bbk.box.BBKAbout;
import bbk.bbk.box.BBKSoft;
import bbk.map.gps.BBKGpsReport;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.sys.abc.BBKMsgBox;
import bbk.uis.view.BBKLayView;

public class Map_Buttons extends BBKLayView {

	// ====================================================================================
	public void ButtonSet(Context context) {
		// ================================================================
		int w = FrameLayout.LayoutParams.MATCH_PARENT;// BBKSoft.myBoxs.screenW;
		int h = FrameLayout.LayoutParams.WRAP_CONTENT;
		int g = Gravity.BOTTOM;
		LayoutInt(context, R.layout.map_btn, w, h, g, 0);
		// ================================================================
		Menu_load1();
		MoreMenu_load1();
		MoreMenu_load3();
		// ================================================================
	}

	// ====================================================================================
	private int BtnAlpha = 100;
	private Drawable GpsLock, GpsFree;
	// ====================================================================================
	public Button BtnOut, BtnInt;
	public ImageButton Search, BtnIMH, BtnPOI, BtnMnu;
	public boolean BtnMoreKey = false;

	// ====================================================================================
	private void Menu_load1() {
		// ----------------------------------------------------------------------
		Search = (ImageButton) BBKLay.findViewById(R.id.Search);
		BtnOut = (Button) BBKLay.findViewById(R.id.OUT);
		BtnInt = (Button) BBKLay.findViewById(R.id.INZ);
		BtnIMH = (ImageButton) BBKLay.findViewById(R.id.IMH);
		BtnPOI = (ImageButton) BBKLay.findViewById(R.id.POI);
		BtnMnu = (ImageButton) BBKLay.findViewById(R.id.Menu);
		// ----------------------------------------------------------------------
		Search.getBackground().setAlpha(BtnAlpha);
		BtnOut.getBackground().setAlpha(BtnAlpha);
		BtnInt.getBackground().setAlpha(BtnAlpha);
		BtnIMH.getBackground().setAlpha(BtnAlpha);
		BtnPOI.getBackground().setAlpha(BtnAlpha);
		BtnMnu.getBackground().setAlpha(BtnAlpha);
		// ----------------------------------------------------------------------
		BtnOut.setText("-");
		BtnInt.setText("+");
		// BtnMap.setText("Map");
		// BtnIMH.setText("ImH");
		// BtnPOI.setText("Poi");
		// BtnMenu.setText("Mu");
		// ----------------------------------------------------
		Search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKSoft.myAsk.layshow(true);
			}
		});
		// ----------------------------------------------------
		BtnIMH.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ----------------------------------------------------
				BBKGpsFollow();
				// ----------------------------------------------------
			}
		});
		// ----------------------------------------------------
		BtnInt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				BBKSoft.myMaps.MapZoomSet(1);
				BBKSoft.MapFlash(true);
				// --------------------------------------------
			}
		});
		// ----------------------------------------------------
		BtnOut.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				BBKSoft.myMaps.MapZoomSet(-1);
				BBKSoft.MapFlash(true);
				// --------------------------------------------
			}
		});
		// ----------------------------------------------------
		BtnPOI.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ----------------------------------------------------
				BBKSoft.myFav.FavPoiBuild("0");
				BBKSoft.MapFlash(false);
				// ----------------------------------------------------
			}
		});
		// ----------------------------------------------------------------------
		BtnMnu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKBtnMore();
			}
		});
		// ================================================================
		GpsLock = bbkAct.getResources().getDrawable(android.R.drawable.ic_menu_mylocation);
		GpsFree = bbkAct.getResources().getDrawable(android.R.drawable.ic_menu_send);
		// ================================================================
	}

	public void BBKGpsFollow() {
		// ----------------------------------------------------------------------
		BBKSoft.GpsIsFollow = !BBKSoft.GpsIsFollow;
		// ----------------------------------------------------------------------
		if (BBKSoft.GpsIsFollow && BBKSoft.myGps.g.w != 0 && BBKSoft.myGps.g.j != 0) {
			// ------------------------------------------------------------------
			BBKSoft.myMaps.MapCenterSet(BBKSoft.myGps.g.w, BBKSoft.myGps.g.j);
			BBKSoft.MapFlash(true);
			// ------------------------------------------------------------------
		}
		// ----------------------------------------------------------------------
		if (BBKSoft.GpsIsFollow) {
			BBKSoft.myBoxs.TxtCrs.setVisibility(View.GONE);
			BtnIMH.setImageDrawable(GpsFree);
		} else {
			BBKSoft.myBoxs.TxtCrs.setVisibility(View.VISIBLE);
			BtnIMH.setImageDrawable(GpsLock);
		}
		// ----------------------------------------------------------------------
	}

	public void BBKBtnMore() {
		BtnMoreKey = !BtnMoreKey;
		if (BtnMoreKey)
			MoreMenu.setVisibility(View.VISIBLE);
		else
			MoreMenu.setVisibility(View.GONE);
	}

	// ====================================================================================
	// ====================================================================================
	// ====================================================================================

	public LinearLayout MoreMenu;
	public ImageButton MAPsss, Author, Tripss, Logsss, Clears, Exitss;
	public ImageButton Naviss, Listss, Rulers, Locatn, Favrit, Morexs;

	private void MoreMenu_load1() {
		// ----------------------------------------------------------------------
		MoreMenu = (LinearLayout) BBKLay.findViewById(R.id.MoreMenu);
		// ----------------------------------------------------------------------
		MAPsss = (ImageButton) BBKLay.findViewById(R.id.MAP);
		Author = (ImageButton) BBKLay.findViewById(R.id.Author);
		Tripss = (ImageButton) BBKLay.findViewById(R.id.Trip);
		Logsss = (ImageButton) BBKLay.findViewById(R.id.Log);
		Clears = (ImageButton) BBKLay.findViewById(R.id.Clear);
		Exitss = (ImageButton) BBKLay.findViewById(R.id.Exit);
		// ----------------------------------------------------------------------
		Naviss = (ImageButton) BBKLay.findViewById(R.id.Navi);
		Listss = (ImageButton) BBKLay.findViewById(R.id.List);
		Rulers = (ImageButton) BBKLay.findViewById(R.id.Ruler);
		Locatn = (ImageButton) BBKLay.findViewById(R.id.Location);
		Favrit = (ImageButton) BBKLay.findViewById(R.id.Fav);
		Morexs = (ImageButton) BBKLay.findViewById(R.id.More);
		// ----------------------------------------------------------------------
		MAPsss.getBackground().setAlpha(BtnAlpha);
		Author.getBackground().setAlpha(BtnAlpha);
		Tripss.getBackground().setAlpha(BtnAlpha);
		Logsss.getBackground().setAlpha(BtnAlpha);
		Clears.getBackground().setAlpha(BtnAlpha);
		Exitss.getBackground().setAlpha(BtnAlpha);
		// ----------------------------------------------------------------------
		Naviss.getBackground().setAlpha(BtnAlpha);
		Listss.getBackground().setAlpha(BtnAlpha);
		Rulers.getBackground().setAlpha(BtnAlpha);
		Locatn.getBackground().setAlpha(BtnAlpha);
		Favrit.getBackground().setAlpha(BtnAlpha);
		Morexs.getBackground().setAlpha(BtnAlpha);
		// ----------------------------------------------------------------------
		MAPsss.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("切换地图！");
				BBKSoft.myMaps.MapTypeSet();
				BBKSoft.MapFlash(true);
			}
		});
		Author.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("版本信息！");
				BBKAbout.SoftAboutShow();
			}
		});
		Tripss.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("轨迹记录！");
				BBKSoft.myTrk.layshow(true);
			}
		});
		Logsss.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("行程信息！");
				BBKGpsReport.GpsInfoShow();
			}
		});
		Clears.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("图层清空！");
				BBKSoft.myLays.LayClears();
				BBKSoft.MapFlash(false);
			}
		});
		Exitss.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("关闭程序！");
				BBKGpsReport.AlertDialogExit(bbkAct);
			}
		});
		// ----------------------------------------------------------------------
		Naviss.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("导航路径查询中...");
				Main_Ask.NavissLoadRun();
			}
		});
		Listss.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("导航列表！");
				BBKSoft.myList.layshow(true);
			}
		});
		Rulers.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				BBKMsgBox.tShow("点击屏幕量算距离！");
				BBKSoft.myMeasure.layshow(true);
				BBKSoft.myLays.laymes.line.add(new line_type());
				BBKSoft.myMeasure.MeasureLayShow = true;
				BBKSoft.myMeasure.LineMeasureShow();
				// --------------------------------------------
			}
		});
		Locatn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("自身位置！");
				BBKSoft.myMove.layshow(true);
				BBKSoft.myMove.SetEditMapJW(BBKSoft.myMaps.mapPt.w, BBKSoft.myMaps.mapPt.j);
			}
		});
		Favrit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("收藏点信息！");
				BBKSoft.myFav.layshow(true);
			}
		});
		Morexs.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("更多功能！");
				bbkAct.openOptionsMenu();// 按键弹出菜单
			}
		});
		// ----------------------------------------------------------------------
	}

	public LinearLayout MoreMenu2;
	public ImageButton MAPregs, DownKey, DownCet, DownAll, MapSets;

	private void MoreMenu_load3() {
		// ----------------------------------------------------------------------
		MoreMenu2 = (LinearLayout) BBKLay.findViewById(R.id.MoreMenu2);
		// ----------------------------------------------------------------------
		MAPregs = (ImageButton) BBKLay.findViewById(R.id.MAPreg);
		DownKey = (ImageButton) BBKLay.findViewById(R.id.DownKey);
		DownCet = (ImageButton) BBKLay.findViewById(R.id.DownCet);
		DownAll = (ImageButton) BBKLay.findViewById(R.id.DownAll);
		MapSets = (ImageButton) BBKLay.findViewById(R.id.MapSet);
		// ----------------------------------------------------------------------
		MAPregs.getBackground().setAlpha(BtnAlpha);
		DownKey.getBackground().setAlpha(BtnAlpha);
		DownCet.getBackground().setAlpha(BtnAlpha);
		DownAll.getBackground().setAlpha(BtnAlpha);
		MapSets.getBackground().setAlpha(BtnAlpha);
		// ----------------------------------------------------------------------
		MAPregs.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKSoft.myMaps.MapRegKey = !BBKSoft.myMaps.MapRegKey;
				BBKMsgBox.tShow("BBKRegKey = " + BBKSoft.myMaps.MapRegKey);
				BBKSoft.MapFlash(true);
			}
		});
		DownKey.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				if (!BBKSoft.SoftHttpCheck())
					return;
				// --------------------------------------------
				boolean ys = BBKSoft.myMaps.MapDownKeyClick(-1);
				BBKMsgBox.tShow("MapDown = " + ys);
				// ---------------------------------------------
			}
		});
		DownCet.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKMsgBox.tShow("地图中心空洞补充下载！");
				BBKSoft.myMaps.BBKDownCenterPic();// 补洞
			}
		});
		DownAll.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKSoft.myZoomSet.BBKScreenDown();
			}
		});
		MapSets.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BBKSoft.myZoomSet.layshow(true);
				BBKSoft.myZoomSet.SetLoad();
			}
		});
		// ----------------------------------------------------------------------
	}

	// private void MoreMenu_load4() {
	// ----------------------------------------------------------------------
	// =====================================================================
	// if (s == "载入资源") {
	// // ---------------------------------------------
	// Intent intent = new Intent(bbkAct, FileManager.class);
	// bbkAct.startActivityForResult(intent, FILE_RESULT_CODE);
	// // ---------------------------------------------
	// }
	// =====================================================================
	// BBKSoft.myLight.layshowe();
	// BBKSoft.GPSOpenCloseRun();
	// BBKGpsSYSTime();
	// BBKSoft.myNetSet.layshow(true);
	// BBKAbout.SoftAboutShow();
	// BBKWebHome();//正在开启主页！
	// =====================================================================
	// }
}
