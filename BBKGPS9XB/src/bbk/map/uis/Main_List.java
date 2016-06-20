package bbk.map.uis;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import bbk.abc.main.R;
import bbk.bbk.box.BBKSoft;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.map.lay.BBKMapLay.p_point;
import bbk.map.lay.BBKMapLay.poi_type;
import bbk.uis.view.BBKLayView;
import bbk.uis.view.BBKListView;

public class Main_List extends BBKLayView {

	private Button ListLayTitle;
	private ImageButton ListLayExit;
	private EditText ListLayEdit, ListLaySearch;
	private ImageButton ListLaySave, ListLayMap;
	private ListView ListLayView;
	private int ListLayId = -1;
	public ArrayList<HashMap<String, Object>> list;
	private ArrayList<HashMap<String, Object>> temp = new ArrayList<HashMap<String, Object>>();

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void LayInt(final Context ctxt) {
		// ------------------------------------------------------------------------------------------
		int w = FrameLayout.LayoutParams.MATCH_PARENT;// BBK.myBoxs.screenW;
		LayoutInt(ctxt, R.layout.list_lay, w, 0, 0);
		// ------------------------------------------------------------------------------------------
		ListLaySet();
		layshow(false);
		// ------------------------------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void ListLaySet() {
		// --------------------------------------------------------------------------------
		ListLayTitle = (Button) BBKLay.findViewById(R.id.ListLayTitle);
		ListLayExit = (ImageButton) BBKLay.findViewById(R.id.ListLayExit);
		// --------------------------------------------------------------------------------
		ListLayEdit = (EditText) BBKLay.findViewById(R.id.ListLayEdit);
		ListLaySearch = (EditText) BBKLay.findViewById(R.id.ListLaySearch);
		// --------------------------------------------------------------------------------
		ListLaySave = (ImageButton) BBKLay.findViewById(R.id.ListLaySave);
		ListLayMap = (ImageButton) BBKLay.findViewById(R.id.ListLayMap);
		// --------------------------------------------------------------------------------
		ListLayView = (ListView) BBKLay.findViewById(R.id.ListLayView);
		// --------------------------------------------------------------------------------
		ListLayTitle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				ListLayLoad();
				// --------------------------------------------
			}
		});
		ListLayExit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				layshow(false);
				// --------------------------------------------
			}
		});
		// --------------------------------------------------------------------------------
		ListLaySave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				String str = ListLayEdit.getText().toString();
				poi_type poi = BBKListView.GetTrackItemPoi(temp, ListLayId);
				BBKSoft.myFav.FavPoiBuildPois(poi, str);
				// --------------------------------------------
			}
		});
		ListLayMap.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				ListLayMapRun();
				// --------------------------------------------
			}
		});
		// --------------------------------------------------------------------------------
		ListLaySearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// --------------------------------------------
				ListLayId = -1;
				ListLayEdit.setText("");
				// --------------------------------------------
				String str = ListLaySearch.getText().toString();
				temp = BBKListView.TrackListAlSchSelect(str, list);// ¹ýÂËËã·¨
				BBKListView.ListViewLoad(temp, ListLayView);
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
		ListLayView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// --------------------------------------------
				ListLayId = arg2;
				ListLayEdit.setText(BBKListView.GetTrackItemName(temp, ListLayId));
				BBKListView.BBKMapMovePoi(temp, ListLayId);
				// --------------------------------------------
			}
		});
		// --------------------------------------------------------------------------------
	}

	// public String GetTrackItemName() {
	// // ---------------------------------------------------------
	// if (ListLayId == -1 || ListLayId >= temp.size())
	// return "";
	// return BBKList.GetTrackItemName(temp, ListLayId);
	// // ---------------------------------------------------------
	// }
	//
	// public poi_type GetTrackItemPoi() {
	// // ---------------------------------------------------------
	// if (ListLayId == -1 || ListLayId >= temp.size())
	// return null;
	// return BBKList.GetTrackItemPoi(temp, ListLayId);
	// // ---------------------------------------------------------
	// }

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void ListLayAdd(ArrayList<HashMap<String, Object>> lt, String layname) {
		// ---------------------------------------------------------
		list = new ArrayList<HashMap<String, Object>>();
		list.addAll(lt);
		ListLayLoad();
		// ---------------------------------------------------------
		ListLayTitle.setText(layname);
		// ---------------------------------------------------------
	}

	private void ListLayLoad() {
		// ---------------------------------------------------------
		temp.clear();
		temp.addAll(list);
		BBKListView.ListViewLoad(temp, ListLayView);
		// ---------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void ListLayMapRun() {
		// ---------------------------------------------------------
		BBKSoft.myLays.laytmp.clear();
		line_type l = new line_type();
		// ---------------------------------------------------------
		for (int i = 0; i < temp.size(); i++) {
			p_point p = BBKListView.GetTrackItemPt(temp, i);
			l.add(p.w, p.j);
		}
		BBKSoft.myLays.laytmp.line.add(0, l);
		// ---------------------------------------------------------
		BBKSoft.MapFlash(true);
		// ---------------------------------------------------------
		layshow(false);
		// ---------------------------------------------------------
	}
}
