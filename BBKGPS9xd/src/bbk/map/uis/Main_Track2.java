package bbk.map.uis;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.bbkgps9xd.R;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import bbk.bbk.box.BBKSoft;
import bbk.map.dat.BBKBBT;
import bbk.map.lay.BBKMapLay;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.sys.abc.BBKMsgBox;
import bbk.uis.view.BBKLayView;
import bbk.uis.view.BBKListView;
import bbk.zzz.debug.BBKDebug;

public class Main_Track2 extends BBKLayView {

	// -------------------------------------------------
	private Button TrackListAllBtn;
	private ImageButton TrackListAllExt;
	private ImageButton TrackListAllSave, TrackListAllDel;
	private EditText TrackListAllEdit, TrackListAllSearch;
	private ImageButton TrackListAllMap, TrackListAllInfo;
	private int ListId = -1;
	private ListView TrackListAllView;
	// -------------------------------------------------
	ArrayList<HashMap<String, Object>> list, temp = new ArrayList<HashMap<String, Object>>();

	// -------------------------------------------------

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void LayInt(final Context ctxt) {
		// ------------------------------------------------------------------------------------------
		int w = FrameLayout.LayoutParams.MATCH_PARENT;// BBK.myBoxs.screenW;
		LayoutInt(ctxt, R.layout.main_track, w, 0, 0, 0);
		// ------------------------------------------------------------------------------------------
		TrackListTitleSet();
		layshow(false);
		BJSAllLoadRun();
		// ------------------------------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void TrackListTitleSet() {
		// ------------------------------------------------------------------------------------------
		TrackListAllBtn = (Button) BBKLay.findViewById(R.id.TrackListAllBtn);
		TrackListAllBtn.setText("轨迹列表");
		TrackListAllExt = (ImageButton) BBKLay.findViewById(R.id.TrackListAllExt);
		// ------------------------------------------------------------------------------------------
		TrackListAllSave = (ImageButton) BBKLay.findViewById(R.id.TrackListAllSave);
		TrackListAllDel = (ImageButton) BBKLay.findViewById(R.id.TrackListAllDel);
		TrackListAllMap = (ImageButton) BBKLay.findViewById(R.id.TrackListAllMap);
		TrackListAllInfo = (ImageButton) BBKLay.findViewById(R.id.TrackListAllInfo);
		// ------------------------------------------------------------------------------------------
		TrackListAllEdit = (EditText) BBKLay.findViewById(R.id.TrackListAllEdit);
		TrackListAllSearch = (EditText) BBKLay.findViewById(R.id.TrackListAllSearch);
		// ------------------------------------------------------------------------------------------
		TrackListAllView = (ListView) BBKLay.findViewById(R.id.TrackListAllView);
		// ------------------------------------------------------------------------------------------
		TrackListAllBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				ListId = -1;
				TrackListAllEdit.setText("");
				TrackListAllSearch.setText("");
				BJSAllLoadRun();
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		TrackListAllExt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				layshow(false);
				BBKSoft.MapFlash(true);
				// --------------------------------------------
			}
		});
		// --------------------------------------------------------------------------------
		TrackListAllSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				ListItemSave();
				// --------------------------------------------
			}
		});
		// --------------------------------------------------------------------------------
		TrackListAllDel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				ListItemDel();
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		TrackListAllInfo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ListSelectLoad();
				BBKSoft.myList.layshow(true);
			}
		});
		TrackListAllMap.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ListSelectLoad();
			}
		});
		// ------------------------------------------------------------------------------------------
		// ------------------------------------------------------------------------------------------
		TrackListAllSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// --------------------------------------------
				ListId = -1;
				TrackListAllEdit.setText("");
				// --------------------------------------------
				String str = TrackListAllSearch.getText().toString();
				temp = BBKListView.TrackListAlSchSelect(str, list);
				BBKListView.ListViewLoad(temp, TrackListAllView);
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
		TrackListAllView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// --------------------------------------------------------------------
				ListId = arg2;
				TrackListAllEdit.setText(BBKListView.GetTrackItemName(temp, ListId));
				// --------------------------------------------------------------------
			}
		});
		TrackListAllView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				return false;// false继续传递;// true终止传递
			}
		});
		// --------------------------------------------------------------------------------
		// ------------------------------------------------------------------------------------------
	}

	private void ListSelectLoad() {
		// -------------------------------------------------------------------------
		layshow(false);
		// -------------------------------------------------------------------------
		String name = BBKListView.GetTrackItemName(temp, ListId);
		if (name.length() <= 0)
			return;
		String path = BBKSoft.PathBbts + name + ".bbt";
		BBKDebug.d(path, false, false);
		// -------------------------------------------------------------------------
		Lay_type lay = BBKBBT.BBTtoLay_type(path, false);
		BBKDebug.d(lay.line.get(0).p.size(), false, false);
		BBKSoft.myLays.laytmp.clone(lay);
		// -------------------------------------------------------------------------
		ArrayList<HashMap<String, Object>> lt;
		lt = BBKListView.BBKLayToArrayList(BBKSoft.myLays.laytmp, true, false, false);
		BBKSoft.myList.ListLayAdd(lt, name);
		// -------------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void ListItemSave() {
		// ---------------------------------------------------------
		String newName = TrackListAllEdit.getText().toString();
		BBKListView.ListReNameSave(temp, ListId, newName);
		BJSAllLoadRun();
		// ---------------------------------------------------------
	}

	public void ListItemDel() {
		// --------------------------------------------------------------------------------
		String Ask = "确定删除？\r\n" + BBKListView.GetTrackItemName(temp, ListId) + BBKMapLay.bbklayExtension;
		// --------------------------------------------------------------------------------
		DialogInterface.OnClickListener YesLs = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == DialogInterface.BUTTON_POSITIVE) {
					// -------------------------------------------------------------------
					BBKListView.ListReNameDel(temp, ListId, BBKSoft.PathBbjs);
					TrackListAllEdit.setText("");
					BJSAllLoadRun();
					// -------------------------------------------------------------------
				}
			}
		};
		// --------------------------------------------------------------------------------
		BBKMsgBox.MsgYesNo(Ask, "Yes", "NO", YesLs);
		// --------------------------------------------------------------------------------
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

	public void BJSAllLoadRun() {
		BJSALLLay_Handler.post(BJSALLLay_Runnable);
	}

	private Handler BJSALLLay_Handler = new Handler();
	private Runnable BJSALLLay_Runnable = new Runnable() {
		public void run() {
			// ------------------------------------------------------
			list = BBKListView.DirFilesToListMap(BBKSoft.PathBbts, ".bbt");
			if (list == null)
				return;
			temp.clear();
			temp.addAll(list);
			BBKListView.ListViewLoad(temp, TrackListAllView);
			// ------------------------------------------------------
		}
	};
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
}
