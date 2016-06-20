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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import bbk.bbk.box.BBKSoft;
import bbk.map.lay.BBKMapLay;
import bbk.sys.abc.BBKMsgBox;
import bbk.uis.view.BBKLayView;
import bbk.uis.view.BBKListView;

public class Main_AskHis extends BBKLayView {

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void LayInt(final Context ctxt) {
		// ------------------------------------------------------------------------------------------
		int w = FrameLayout.LayoutParams.MATCH_PARENT;// BBK.myBoxs.screenW;
		LayoutInt(ctxt, R.layout.main_askhis, w, 0, 0, 0);
		// ------------------------------------------------------------------------------------------
		ListSet();
		layshow(false);
		BJSAllLoadRun();
		// ------------------------------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	private Button ListAskTit;
	private ImageButton ListAskExt;
	// ------------------------------------------------------
	private EditText ListAskEdit;
	private ImageButton ListAskDel, ListAskSave;
	// ------------------------------------------------------
	private EditText ListAskSch;
	private ImageButton ListAskInfo, ListAskShow;
	// ------------------------------------------------------
	private ListView ListAskView;
	private int ListId = -1;

	// ------------------------------------------------------

	public void ListSet() {
		// ------------------------------------------------------------------------------------------
		ListAskTit = (Button) BBKLay.findViewById(R.id.ListAskTit);
		ListAskExt = (ImageButton) BBKLay.findViewById(R.id.ListAskExt);
		// ---------------------------------------------------------------------
		ListAskEdit = (EditText) BBKLay.findViewById(R.id.ListAskEdit);
		ListAskDel = (ImageButton) BBKLay.findViewById(R.id.ListAskDel);
		ListAskSave = (ImageButton) BBKLay.findViewById(R.id.ListAskSave);
		// ---------------------------------------------------------------------
		ListAskSch = (EditText) BBKLay.findViewById(R.id.ListAskSch);
		ListAskInfo = (ImageButton) BBKLay.findViewById(R.id.ListAskInfo);
		ListAskShow = (ImageButton) BBKLay.findViewById(R.id.ListAskShow);
		ListAskView = (ListView) BBKLay.findViewById(R.id.ListAskView);
		// ------------------------------------------------------------------------------------------
		ListAskTit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				ListId = -1;
				ListAskEdit.setText("");
				ListAskSch.setText("");
				BJSAllLoadRun();
				// --------------------------------------------
			}
		});
		ListAskExt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				layshow(false);
				// --------------------------------------------
			}
		});
		// ===========================================================================================
		ListAskDel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				ListAskReNameDel();
				// --------------------------------------------
			}
		});
		ListAskSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				ListAskReNameSave();
				// --------------------------------------------
			}
		});
		// ===========================================================================================
		ListAskSch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// --------------------------------------------
				ListId = -1;
				ListAskEdit.setText("");
				// --------------------------------------------
				String str = ListAskSch.getText().toString();
				temp = BBKListView.TrackListAlSchSelect(str, list);
				BBKListView.ListViewLoad(temp, ListAskView);
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
		ListAskView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// --------------------------------------------
				ListId = arg2;
				String name = BBKListView.GetTrackItemName(temp, ListId);
				ListAskEdit.setText(name);
				// --------------------------------------------
			}
		});
		// ===========================================================================================
		ListAskInfo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				layshow(false);
				BBKListView.ListInfoListRun(temp, ListId, BBKSoft.PathAsks, "查询结果：");
				BBKSoft.myList.layshow(true);
				// --------------------------------------------
			}
		});
		ListAskShow.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				layshow(false);
				BBKListView.ListInfoMapRun(temp, ListId, BBKSoft.PathAsks);
				// --------------------------------------------
			}
		});
		// ===========================================================================================
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	ArrayList<HashMap<String, Object>> list, temp = new ArrayList<HashMap<String, Object>>();

	// ====================================================================================
	public void ListAskReNameDel() {
		// --------------------------------------------------------------------------------
		String Ask = "确定删除？\r\n" + BBKListView.GetTrackItemName(temp, ListId) + BBKMapLay.bbklayExtension;
		// --------------------------------------------------------------------------------
		DialogInterface.OnClickListener YesLs = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == DialogInterface.BUTTON_POSITIVE) {
					// -------------------------------------------------------------------
					BBKListView.ListReNameDel(temp, ListId, BBKSoft.PathAsks);
					ListAskEdit.setText("");
					BJSAllLoadRun();
					// -------------------------------------------------------------------
				}
			}
		};
		// --------------------------------------------------------------------------------
		BBKMsgBox.MsgYesNo(Ask, "OK", "NO", YesLs);
		// --------------------------------------------------------------------------------
	}

	public void ListAskReNameSave() {
		// ---------------------------------------------------------
		String newName = ListAskEdit.getText().toString();
		BBKListView.ListReNameSave(temp, ListId, newName);
		BJSAllLoadRun();
		// ---------------------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	public void AskInfoListShowRun(String name) {
		// ---------------------------------------------------------
		layshow(false);
		// ---------------------------------------------------------
		ArrayList<HashMap<String, Object>> lt;
		lt = BBKListView.BBKLayToArrayList(BBKSoft.myLays.layask, true, false, false);
		// ---------------------------------------------------------
		BBKSoft.myList.ListLayAdd(lt, name);
		BBKSoft.myList.layshow(true);
		// ---------------------------------------------------------
		BJSAllLoadRun();
		BBKSoft.MapFlash(true);
		// ---------------------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	public void BJSAllLoadRun() {
		BJSALLLay_Handler.post(BJSALLLay_Runnable);
	}

	// =====================================================================================
	// BJSALLLay_Handler.post(BJSALLLay_Runnable);
	// =====================================================================================
	private Handler BJSALLLay_Handler = new Handler();
	private Runnable BJSALLLay_Runnable = new Runnable() {
		public void run() {
			// ------------------------------------------------------
			list = BBKListView.DirFilesToListMap(BBKSoft.PathAsks, BBKMapLay.bbklayExtension);
			if (list == null)
				return;
			temp.clear();
			temp.addAll(list);
			BBKListView.ListViewLoad(temp, ListAskView);
			// BBKList.DirFilesAddListView(list, temp, BBKSoft.baskPath,
			// ListAskView);
			// ------------------------------------------------------
		}
	};
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
}