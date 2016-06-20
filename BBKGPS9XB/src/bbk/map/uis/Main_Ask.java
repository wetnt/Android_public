package bbk.map.uis;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import bbk.abc.main.R;
import bbk.bbk.box.BBKSoft;
import bbk.sys.abc.BBKMsgBox;
import bbk.uis.view.BBKLayView;
import bbk.uis.view.BBKListView;

public class Main_Ask extends BBKLayView {
	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void LayInt(final Context ctxt) {
		// ------------------------------------------------------------------------------------------
		int w = FrameLayout.LayoutParams.MATCH_PARENT;// BBK.myBoxs.screenW;
		int h = FrameLayout.LayoutParams.WRAP_CONTENT;
		LayoutInt(ctxt, R.layout.main_ask, w, h, 3);
		// ------------------------------------------------------------------------------------------
		ListSet();
		layshow(false);
		// ------------------------------------------------------------------------------------------
	}

	private Button ListAskTit;
	private ImageButton ListAskHis, ListAskExt;
	// ------------------------------------------------------
	private ImageButton ListAskGps, ListAskRun, ListAskSwp;
	private ImageButton ListAskMap, ListAskNav;
	private EditText ListAskStr, ListAskEnd;
	// ------------------------------------------------------
	private ImageButton ListAskMore;

	// ------------------------------------------------------

	public void ListSet() {
		// ------------------------------------------------------------------------------------------
		ListAskTit = (Button) BBKLay.findViewById(R.id.ListAskTit);
		ListAskExt = (ImageButton) BBKLay.findViewById(R.id.ListAskExt);
		ListAskHis = (ImageButton) BBKLay.findViewById(R.id.ListAskHis);
		// ---------------------------------------------------------------------
		ListAskGps = (ImageButton) BBKLay.findViewById(R.id.ListAskGps);
		ListAskStr = (EditText) BBKLay.findViewById(R.id.ListAskStr);
		ListAskRun = (ImageButton) BBKLay.findViewById(R.id.ListAskRun);
		ListAskSwp = (ImageButton) BBKLay.findViewById(R.id.ListAskSwp);
		// ---------------------------------------------------------------------
		ListAskMap = (ImageButton) BBKLay.findViewById(R.id.ListAskMap);
		ListAskEnd = (EditText) BBKLay.findViewById(R.id.ListAskEnd);
		ListAskNav = (ImageButton) BBKLay.findViewById(R.id.ListAskNav);
		// ---------------------------------------------------------------------
		ListAskMore = (ImageButton) BBKLay.findViewById(R.id.ListAskMore);
		// ------------------------------------------------------------------------------------------
		ListAskTit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
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
		ListAskHis.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				layshow(false);
				BBKSoft.SoftAskHistoryShow();
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		ListAskMap.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				ListAskMapClick(BBKSoft.myMaps.mapPt.w + "," + BBKSoft.myMaps.mapPt.j);
				// --------------------------------------------
			}
		});
		ListAskGps.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				ListAskMapClick(BBKSoft.myGps.gm.g.w + "," + BBKSoft.myGps.gm.g.j);
				// --------------------------------------------
			}
		});
		ListAskSwp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				String str = ListAskStr.getText().toString();
				String end = ListAskEnd.getText().toString();
				ListAskStr.setText(end);
				ListAskEnd.setText(str);
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		ListAskRun.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				String str = ListAskStr.getText().toString();
				AskBaidu(str);
				// --------------------------------------------
			}
		});
		ListAskNav.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				String str = ListAskStr.getText().toString();
				String end = ListAskEnd.getText().toString();
				AskNavi(str, end);
				// --------------------------------------------
			}
		});
		// ===========================================================================================
		ListAskMore.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				BBKSoft.myList.layshow(true);
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
	public void ListAskMapClick(String str) {
		// -------------------------------------------------------------
		if (ListAskEnd.hasFocus()) {
			ListAskEnd.setText(str);
		} else {
			ListAskStr.setText(str);
		}
		// -------------------------------------------------------------
	}

	public void AskBaidu(String str) {
		// ----------------------------------------------------
		if (str.length() == 0 || !BBKSoft.SoftHttpCheck()) {
			return;
		}
		// ----------------------------------------------------
		SetButtonEnable(false);
		BBKSoft.SoftBaiDuiAsk(str);
		// ----------------------------------------------------
	}

	public void AskNavi(String strAsk, String strEnd) {
		// ----------------------------------------------------
		if (strAsk.length() == 0 || strEnd.length() == 0) {
			BBKMsgBox.tShow(" strEnd Is Empty! ");
			return;
		}
		// ----------------------------------------------------
		SetButtonEnable(false);
		BBKSoft.myGoog.NaviRunThead(strAsk, strEnd, 3, BBKSoft.myMaps.mapPt.w, BBKSoft.myMaps.mapPt.j);
		// ----------------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	public void AskInfoListShowRun(String name) {
		// ---------------------------------------------------------
		// layshow(false);
		// ---------------------------------------------------------
		ArrayList<HashMap<String, Object>> lt;
		lt = BBKListView.BBKLayToArrayList(BBKSoft.myLays.layask, true, false, false);
		BBKSoft.myList.ListLayAdd(lt, name);
		// BBKSoft.myList.layshow(true);
		// ---------------------------------------------------------
		BBKSoft.MapFlash(true);
		AskInfoBackShow(name);
		// ---------------------------------------------------------
	}

	public void AskInfoBackShow(String name) {
		// ---------------------------------------------------------
		BBKMsgBox.tShow("\" " + name + " \"\r\n ¡ª¡ª¡ª¡ª ³É¹¦£¡");
		// ---------------------------------------------------------
		SetButtonEnable(true);
		BBKSoft.SoftAskHistoryLoad();
		// ---------------------------------------------------------
	}

	final int BtnAlpha = 50;

	private void SetButtonEnable(boolean k) {
		if (k) {
			ListAskMore.getBackground().setAlpha(255);
		} else {
			ListAskMore.getBackground().setAlpha(BtnAlpha);
		}
	}
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
}