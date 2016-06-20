package bbk.map.uis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import bbk.abc.main.R;
import bbk.bbk.box.BBKSoft;
import bbk.map.lay.BBKMapLay.poi_type;
import bbk.sys.abc.BBKMsgBox;
import bbk.uis.view.BBKLayView;
import bbk.uis.view.BBKListView;

public class Main_Fav extends BBKLayView {
	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void LayInt(final Context ctxt) {
		// ------------------------------------------------------------------------------------------
		int w = FrameLayout.LayoutParams.MATCH_PARENT;// BBK.myBoxs.screenW;
		LayoutInt(ctxt, R.layout.main_favorite, w, 0, 0);
		// ------------------------------------------------------------------------------------------
		ListSet();
		layshow(false);
		FavPoiListLoad();
		// ------------------------------------------------------------------------------------------
	}

	private ImageButton ListFavTit, ListFavNew, ListFavExt;
	private ImageButton ReNameSave, FavDelete;
	private EditText FavReName, ListFavSch;
	private ListView ListFavView;
	private int ListId = -1;

	public void ListSet() {
		// ------------------------------------------------------------------------------------------
		ListFavTit = (ImageButton) BBKLay.findViewById(R.id.ListFavTit);
		ListFavNew = (ImageButton) BBKLay.findViewById(R.id.ListFavNew);
		ListFavExt = (ImageButton) BBKLay.findViewById(R.id.ListFavExt);
		// ---------------------------------------------------------------------
		FavReName = (EditText) BBKLay.findViewById(R.id.FavReName);
		FavDelete = (ImageButton) BBKLay.findViewById(R.id.FavDelete);
		ReNameSave = (ImageButton) BBKLay.findViewById(R.id.ReNameSave);
		// ---------------------------------------------------------------------
		ListFavSch = (EditText) BBKLay.findViewById(R.id.ListFavSch);
		ListFavView = (ListView) BBKLay.findViewById(R.id.ListFavView);
		// ------------------------------------------------------------------------------------------
		ListFavTit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				FavPoiListLoad();
				// --------------------------------------------
			}
		});
		ListFavExt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				layshow(false);
				BBKSoft.myLays.LaysFavSave();
				BBKSoft.MapFlash(false);
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		ListFavNew.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				// BBK.myCams.CameraOpen(BBK.MapImg);
				FavPoiBuild("0");
				// --------------------------------------------
			}
		});
		// ---------------------------------------------------------------
		FavDelete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				FavReNameDel();
				// --------------------------------------------
			}
		});
		ReNameSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				FavReNameSave();
				// --------------------------------------------
			}
		});
		// ---------------------------------------------------------------
		// ------------------------------------------------------------------------------------------
		ListFavSch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// --------------------------------------------
				ListId = -1;
				FavReName.setText("");
				// --------------------------------------------
				String str = ListFavSch.getText().toString();
				temp = BBKListView.TrackListAlSchSelect(str, list);
				BBKListView.ListViewLoad(temp, ListFavView);
				// --------------------------------------------
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		// --------------------------------------------------------------------------------
		ListFavView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// --------------------------------------------
				ListId = arg2;
				FavReName.setText(BBKListView.GetTrackItemName(temp, ListId));
				BBKListView.BBKMapMovePoi(temp, ListId);
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
	}

	// ====================================================================================
	ArrayList<HashMap<String, Object>> list, temp = new ArrayList<HashMap<String, Object>>();

	private void FavPoiListLoad() {
		// ------------------------------------------------------
		list = BBKListView.BBKLayToArrayList(BBKSoft.myLays.layfav, true, false, false);
		temp.clear();
		temp.addAll(list);
		BBKListView.ListViewLoad(temp, ListFavView);
		// ------------------------------------------------------
	}

	// ====================================================================================
	public void FavReNameDel() {
		// --------------------------------------------
		if (ListId <= -1 || ListId > BBKSoft.myLays.layfav.pois.size())
			return;
		// --------------------------------------------
		// listlay.pois.remove(n);
		// --------------------------------------------------------------------------------
		String Ask = "确定删除？\r\n" + BBKSoft.myLays.layfav.pois.get(ListId).s.a;
		// --------------------------------------------------------------------------------
		DialogInterface.OnClickListener YesLs = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == DialogInterface.BUTTON_POSITIVE) {
					// -------------------------------------------------------------------
					BBKSoft.myLays.layfav.pois.remove(ListId);
					FavPoiListLoad();
					FavReName.setText("");
					ListId = -1;
					// -------------------------------------------------------------------
				}
			}
		};
		// --------------------------------------------------------------------------------
		BBKMsgBox.MsgYesNo(Ask, "Yes", "NO", YesLs);
		// --------------------------------------------------------------------------------
	}

	public void FavReNameSave() {
		// --------------------------------------------
		if (ListId == -1)
			return;
		// --------------------------------------------
		String s = FavReName.getText().toString();
		if (s.length() == 0)
			return;
		// --------------------------------------------
		StringBuffer ps = new StringBuffer(s);
		BBKSoft.myLays.layfav.pois.get(ListId).s.a = ps;
		// --------------------------------------------
		FavPoiListLoad();
		// --------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	@SuppressLint("SimpleDateFormat")
	public void FavPoiBuild(String pp) {
		// -------------------------------------------------------------
		SimpleDateFormat ft = new SimpleDateFormat("MMdd_HHmmss");
		Date dt = new Date(System.currentTimeMillis());
		// -------------------------------------------------------------
		StringBuffer nm = new StringBuffer(ft.format(dt));
		// -------------------------------------------------------------
		double wd = BBKSoft.myMaps.mapPt.w;
		double jd = BBKSoft.myMaps.mapPt.j;
		double hb = 0;
		StringBuffer ss = new StringBuffer(wd + "," + jd);
		StringBuffer ps = new StringBuffer(pp);
		// -------------------------------------------------------------
		FavPoiBuildPois(new poi_type(nm, ps, ss, wd, jd, hb, dt), nm.toString());
		// -------------------------------------------------------------
	}

	// ==============================================================================================
	@SuppressLint("SimpleDateFormat")
	public void FavPoiBuildPois(poi_type p, String name) {
		// -------------------------------------------------------------
		poi_type pt;
		if (name == null) {
			pt = new poi_type(p.s.a, p.s.p, p.s.s, p.p.w, p.p.j, p.h.h, p.h.t);
		} else {
			pt = new poi_type(new StringBuffer(name), p.s.p, p.s.s, p.p.w, p.p.j, p.h.h, p.h.t);
		}
		// -------------------------------------------------------------
		BBKSoft.myLays.layfav.pois.add(pt);
		FavPoiListLoad();
		FavReName.setText(name);
		ListId = BBKSoft.myLays.layfav.pois.size() - 1;
		// -------------------------------------------------------------
		BBKMsgBox.tShow("<" + name + ">" + "\r\n      收藏成功！");
		BBKSoft.myLays.LaysFavSave();
		// -------------------------------------------------------------
	}
	// ==============================================================================================
	// ====================================================================================
	// ====================================================================================

}