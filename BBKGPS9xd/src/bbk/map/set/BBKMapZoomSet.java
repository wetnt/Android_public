package bbk.map.set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bbkgps9xd.R;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import bbk.bbk.box.BBKSoft;
import bbk.map.bbd.BBKScreenDown;
import bbk.net.abc.BBKNetCheck;
import bbk.sys.abc.BBKMsgBox;
import bbk.sys.abc.BBKSYS;
import bbk.uis.view.BBKLayView;
import bbk.zzz.debug.bd;

public class BBKMapZoomSet extends BBKLayView {
	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void LayInt(final Context ctxt) {
		// ------------------------------------------------------------------------------------------
		LayoutInt(ctxt, R.layout.main_set_map, 0, 0, 0, 0);
		MapTitleListViewSet();
		MapDownListViewSet();
		MapZoomListViewSet();
		MapTypeListViewSet();
		// ------------------------------------------------------------------------------------------
		layshow(false);
		setpathname = BBKSoft.PathSets + "BBKMapZoom.txt";
		SetLoad();
		// ------------------------------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################MapZoomSetTit#########################################
	// ####################################################################################
	// ====================================================================================
	private Button MapZoomSetTit;
	private ImageButton MapZoomSetExt;

	private void MapTitleListViewSet() {
		// ---------------------------------------------------------------------
		MapZoomSetTit = (Button) BBKLay.findViewById(R.id.ZoomSetTit);
		MapZoomSetExt = (ImageButton) BBKLay.findViewById(R.id.ZoomSetExt);
		// ---------------------------------------------------------------------
		MapZoomSetTit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				SetSave();
				// --------------------------------------------
			}
		});
		MapZoomSetExt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				SetSave();
				layshow(false);
				// --------------------------------------------
			}
		});
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################MapDownSet############################################
	// ####################################################################################
	// ====================================================================================

	ToggleButton MapDownTgbtn;// 下载开关
	Button MapDownCenter;// 中心补洞
	Button MapDownRectangle;// 指定范围下载
	Button MapDownScreen;// 全屏范围下载
	EditText MapDownZXJ, MapDownYSJ;// 左下角、右上角

	private void MapDownListViewSet() {
		// ---------------------------------------------------------------------
		MapDownTgbtn = (ToggleButton) BBKLay.findViewById(R.id.MapDownTgbtn);
		MapDownCenter = (Button) BBKLay.findViewById(R.id.MapDownCenter);
		MapDownScreen = (Button) BBKLay.findViewById(R.id.MapDownScreen);
		MapDownRectangle = (Button) BBKLay.findViewById(R.id.MapDownRectangle);
		MapDownZXJ = (EditText) BBKLay.findViewById(R.id.MapDownZXJ);
		MapDownYSJ = (EditText) BBKLay.findViewById(R.id.MapDownYSJ);
		// ---------------------------------------------------------------------
		MapDownTgbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// --------------------------------------------
				if (isChecked) {
					BBKSoft.myMaps.MapDownKeyClick(1);
				} else {
					BBKSoft.myMaps.MapDownKeyClick(0);
				}
				// --------------------------------------------
			}
		});
		MapDownCenter.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				BBKMsgBox.tShow("地图中心空洞补充下载！");
				BBKSoft.myMaps.BBKDownCenterPic();// 补洞
				// --------------------------------------------
			}
		});
		MapDownScreen.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				BBKScreenDown();
				// --------------------------------------------
			}
		});
		MapDownRectangle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				// --------------------------------------------
			}
		});
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	public void BBKScreenDown() {
		// BBKSoft.myMsg.tShow("全屏地图批量下载请勿操作！");
		String Ask = "";
		Ask += "    全屏地图批量下载说明：\r\n";
		Ask += "	\r\n";
		Ask += "    即将开始自动批量下载，当前屏幕范围内、所有放大层级的地图，下载所需时间与屏幕范围，以及网络速度都有关！\r\n";
		Ask += "    注意：下载时候不要进行任何操作！\r\n";
		Ask += "	\r\n";
		Ask += "    是否开始下载？\r\n";
		// ---------------------------------------------
		DialogInterface.OnClickListener YesLs = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:// -1确定的
					// --------------------------------------------
					if (!BBKNetCheck.HttpTest(bbkAct))
						return;
					// --------------------------------------------
					BBKSoft.GPSCloseRun();
					BBKSoft.myMaps.MapDownKeyClick(1);
					BBKScreenDown.DownScreenMapThead();
					// ---------------------------------------------
					break;
				case DialogInterface.BUTTON_NEGATIVE:// -2否定的
					break;
				case DialogInterface.BUTTON_NEUTRAL:// -3中性的
					break;
				}
			}
		};
		// ---------------------------------------------
		BBKMsgBox.MsgYesNo(Ask, "OK", "NO", YesLs);
		// ---------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################MapZoomCheckBoxLay####################################
	// ####################################################################################
	// ====================================================================================
	private LinearLayout MapZoomCheckBoxLay;
	private CheckBox[] MapZoomCek;

	private void MapZoomListViewSet() {
		// ---------------------------------------------------------------------
		MapZoomCheckBoxLay = (LinearLayout) BBKLay.findViewById(R.id.MapZoomCheckBoxLay);
		MapZoomCek = new CheckBox[21];
		// ---------------------------------------------------------------------
		int i = 0;
		for (int y = 1; y < 5; y++) {
			// ---------------------------------------------------------------------
			LinearLayout lay = new LinearLayout(bbkAct);
			lay.setOrientation(LinearLayout.HORIZONTAL);
			// ---------------------------------------------------------------------
			for (int x = 1; x < 6; x++) {
				// ---------------------------------------------------------------------
				i++;
				MapZoomCek[i] = new CheckBox(bbkAct);
				MapZoomCek[i].setId(i);
				String id = i + "";
				if (i < 10) {
					id = "0" + id;
				}
				MapZoomCek[i].setText(id);
				lay.addView(MapZoomCek[i]);
				// ---------------------------------------------------------------------
			}
			// ---------------------------------------------------------------------
			MapZoomCheckBoxLay.addView(lay);
			// ---------------------------------------------------------------------
		}
		// ---------------------------------------------------------------------
		MapZoomCek[1].setEnabled(false);
		MapZoomCek[1].setChecked(true);
		// ---------------------------------------------------------------------
	}

	private void MapZoomTrueToView() {
		// ---------------------------------------------------------------------
		for (int i = 1; i < 21; i++) {
			MapZoomCek[i].setChecked(false);
		}
		// ---------------------------------------------------------------------
		for (int i = 0; i < BBKSoft.myMaps.mapZoom.length; i++) {
			int n = BBKSoft.myMaps.mapZoom[i];
			MapZoomCek[n].setChecked(true);
		}
		// ---------------------------------------------------------------------
		MapZoomCek[1].setChecked(true);
		// ---------------------------------------------------------------------
	}

	private void MapZoomViewToTrue() {
		// ---------------------------------------------------------------------
		int t = 0;
		for (int i = 1; i < 21; i++) {
			if (MapZoomCek[i].isChecked()) {
				t++;
			}
		}
		// ---------------------------------------------------------------------
		BBKSoft.myMaps.mapZoom = new int[t];
		// ---------------------------------------------------------------------
		t = 0;
		for (int i = 1; i < 21; i++) {
			if (MapZoomCek[i].isChecked()) {
				BBKSoft.myMaps.mapZoom[t] = i;
				t++;
			}
		}
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	public JSONArray MapZoomToJson() {
		// ---------------------------------------------------------------------
		JSONArray mapzoomjs = new JSONArray();
		for (int i = 0; i < BBKSoft.myMaps.mapZoom.length; i++) {
			mapzoomjs.put(BBKSoft.myMaps.mapZoom[i]);
		}
		return mapzoomjs;
		// ---------------------------------------------------------------------
	}

	public void MapZoomByJson(JSONArray mapzoomjs) {
		// ---------------------------------------------------------------------
		int n = mapzoomjs.length();
		BBKSoft.myMaps.mapZoom = new int[n];
		for (int i = 0; i < n; i++) {
			try {
				BBKSoft.myMaps.mapZoom[i] = mapzoomjs.getInt(i);
			} catch (JSONException e) {
			}
		}
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################MapTypeCheckBoxLay####################################
	// ####################################################################################
	// ====================================================================================
	private LinearLayout MapTypeCheckBoxLay;
	private CheckBox[] MapTypeCek;
	private EditText[] MapTypeTxt;

	private void MapTypeListViewSet() {
		// ---------------------------------------------------------------------
		MapTypeCheckBoxLay = (LinearLayout) BBKLay.findViewById(R.id.MapTypeCheckBoxLay);
		MapTypeCek = new CheckBox[6];
		MapTypeTxt = new EditText[6];
		// ---------------------------------------------------------------------
		for (int i = 0; i < MapTypeCek.length; i++) {
			// ---------------------------------------------------------------------
			LinearLayout lay = new LinearLayout(bbkAct);
			lay.setOrientation(LinearLayout.HORIZONTAL);
			// ---------------------------------------------------------------------
			MapTypeCek[i] = new CheckBox(bbkAct);
			MapTypeCek[i].setId(i);
			MapTypeTxt[i] = new EditText(bbkAct);
			MapTypeTxt[i].setId(i);
			MapTypeTxt[i].setText(i + "");
			// ---------------------------------------------------------------------
			lay.addView(MapTypeCek[i]);
			lay.addView(MapTypeTxt[i]);
			// ---------------------------------------------------------------------
			MapTypeCheckBoxLay.addView(lay);
			// ---------------------------------------------------------------------
		}
		// ---------------------------------------------------------------------
		MapTypeCek[0].setEnabled(false);
		MapTypeCek[0].setChecked(true);
		MapTypeCek[0].setText(0 + " 城市地图");
		MapTypeCek[1].setText(1 + " 卫星地图");
		MapTypeCek[2].setText(2 + " 地形地图");
		MapTypeCek[3].setText(3 + " 自定义图");
		MapTypeCek[4].setText(4 + " 自定义图");
		MapTypeCek[5].setText(5 + " 自定义图");
		// ---------------------------------------------------------------------
		MapTypeTxt[0].setEnabled(false);
		MapTypeTxt[1].setEnabled(false);
		MapTypeTxt[2].setEnabled(false);
		// ---------------------------------------------------------------------
	}

	private void MapTypeViewToTrue() {
		// ---------------------------------------------------------------------
		for (int i = 0; i < MapTypeCek.length; i++) {
			if (MapTypeCek[i].isChecked()) {
				BBKSoft.myMaps.mapTypeInt[i] = 1;
			} else {
				BBKSoft.myMaps.mapTypeInt[i] = 0;
			}
		}
		// ---------------------------------------------------------------------
		for (int i = 3; i < MapTypeTxt.length; i++) {
			String s = MapTypeTxt[i].getText().toString();
			BBKSoft.myMaps.mapTypeInt[i + 3] = IntegerparseInt(s);
		}
		// ---------------------------------------------------------------------
	}

	private void MapTypeTrueToView() {
		// ---------------------------------------------------------------------
		for (int i = 0; i < MapTypeCek.length; i++) {
			if (BBKSoft.myMaps.mapTypeInt[i] == 1) {
				MapTypeCek[i].setChecked(true);
			} else {
				MapTypeCek[i].setChecked(false);
			}
		}
		MapTypeCek[0].setChecked(true);
		// ---------------------------------------------------------------------
		MapTypeTxt[0].setText(0 + "");
		MapTypeTxt[1].setText(1 + "");
		MapTypeTxt[2].setText(2 + "");
		MapTypeTxt[3].setText(BBKSoft.myMaps.mapTypeInt[6] + "");
		MapTypeTxt[4].setText(BBKSoft.myMaps.mapTypeInt[7] + "");
		MapTypeTxt[5].setText(BBKSoft.myMaps.mapTypeInt[8] + "");
		// ---------------------------------------------------------------------
	}

	// ====================================================================================

	public JSONArray MapTypeToJson() {
		// ---------------------------------------------------------------------
		JSONArray maptypejs = new JSONArray();
		for (int i = 0; i < BBKSoft.myMaps.mapTypeInt.length; i++) {
			maptypejs.put(BBKSoft.myMaps.mapTypeInt[i]);
		}
		return maptypejs;
		// ---------------------------------------------------------------------
	}

	public void MapTypeByJson(JSONArray maptypejs) {
		// ---------------------------------------------------------------------
		int n = maptypejs.length();
		BBKSoft.myMaps.mapTypeInt = new int[n];
		for (int i = 0; i < n; i++) {
			try {
				BBKSoft.myMaps.mapTypeInt[i] = maptypejs.getInt(i);
			} catch (JSONException e) {
			}
		}
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################MapType###############################################
	// ####################################################################################
	// ====================================================================================

	private String setpathname = BBKSoft.PathSoft + "BBKMapZoom.txt";

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void SetLoad() {
		// ----------------------------------------------
		JSONObject sets = MapZoomTypeReadJson();
		if (sets != null) {
			// ----------------------------------------------
			try {
				MapCentWd = sets.getString("MapCentWd");
				MapCentJd = sets.getString("MapCentJd");
				double mw = DoubleparseInt(MapCentWd);
				double mj = DoubleparseInt(MapCentJd);
				if (mw != 0 && mj != 0) {
					BBKSoft.myMaps.MapSetWJZ(mw, mj, 0);
				}
			} catch (JSONException e1) {
			}
			// ----------------------------------------------
			JSONArray mapzoomjs = null;
			try {
				mapzoomjs = sets.getJSONArray("MapZoomJS");
				MapZoomByJson(mapzoomjs);
			} catch (JSONException e) {
			}
			// ----------------------------------------------
			JSONArray maptypejs = null;
			try {
				maptypejs = sets.getJSONArray("MapTypeJS");
				MapTypeByJson(maptypejs);
			} catch (JSONException e) {
			}
			// ----------------------------------------------
		}
		// ----------------------------------------------
		MapZoomTrueToView();
		MapTypeTrueToView();
		// ----------------------------------------------
	}

	public void SetSave() {
		// ----------------------------------------------
		MapZoomViewToTrue();
		MapTypeViewToTrue();
		// ----------------------------------------------
		JSONArray MapZoomJS = MapZoomToJson();
		JSONArray MapTypeJS = MapTypeToJson();
		// ----------------------------------------------
		MapZoomTypeSaveJson(MapZoomJS, MapTypeJS);
		// ----------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

	public JSONObject MapZoomTypeReadJson() {
		// ---------------------------------------------------------------------
		String softStr = BBKSYS.FileRead(setpathname);
		bd.d("BBKMapZoomSet.MySoftRead.path = " + setpathname, false, true);
		bd.d("BBKMapZoomSet.MySoftRead.strg = " + softStr, false, true);
		// ---------------------------------------------------------------------
		if (softStr == null || softStr.length() < 10)
			return null;
		// ---------------------------------------------------------------------
		JSONObject sets = null;
		try {
			// ----------------------------------------------
			sets = new JSONObject(softStr);
			// ----------------------------------------------
		} catch (JSONException ex) {
			bd.d("BBKMapZoomSet.MySoftRead.JSON = " + setpathname, false, true);
			return null;
		}
		// ---------------------------------------------------------------------
		return sets;
		// ---------------------------------------------------------------------
	}

	public String MapCentWd = "0", MapCentJd = "0";

	public boolean MapZoomTypeSaveJson(JSONArray MapZoomJS, JSONArray MapTypeJS) {
		// ---------------------------------------------------------------------
		bd.d("BBKMapZoomSet.SetSave.path = " + setpathname, false, true);
		// ---------------------------------------------------------------------
		try {
			// ---------------------------------------------------------------------
			JSONObject sets = new JSONObject();
			sets.put("MapZoomJS", MapZoomJS);
			sets.put("MapTypeJS", MapTypeJS);
			sets.put("MapCentWd", BBKSoft.myMaps.mapPt.w);
			sets.put("MapCentJd", BBKSoft.myMaps.mapPt.j);
			// ---------------------------------------------------------------------
			String sss = sets.toString();
			bd.d("BBKMapZoomSet.SetSave.json = " + sss, false, true);
			BBKSYS.FileSave(setpathname, sss);
			return true;
			// ---------------------------------------------------------------------
		} catch (JSONException ex) {
			bd.d("BBKMapZoomSet.SetSave.errs = " + ex.toString(), false, true);
		}
		return false;
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

	private int IntegerparseInt(String key) {
		// -------------------------------------------------------
		if (key.length() <= 0) {
			return 0;
		} else {
			try {
				int kkk = Integer.parseInt(key);
				return kkk;
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		// -------------------------------------------------------
	}

	private double DoubleparseInt(String key) {
		// -------------------------------------------------------
		if (key.length() <= 0) {
			return 0;
		} else {
			try {
				double kkk = Double.parseDouble(key);
				return kkk;
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		// -------------------------------------------------------
	}

	public String setsgetString(JSONObject sets, String key) {
		// ----------------------------------------------
		String s = "";
		try {
			// ----------------------------------------------
			s = sets.getString(key);
			// ----------------------------------------------
		} catch (JSONException e) {
		}
		return s;
		// ----------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

}