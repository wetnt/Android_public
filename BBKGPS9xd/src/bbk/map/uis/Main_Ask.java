package bbk.map.uis;

import com.example.bbkgps9xd.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
		LayoutInt(ctxt, R.layout.main_ask, w, h, 3, 0);
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
	private static EditText ListAskStr, ListAskEnd;
	// ------------------------------------------------------
	private static ImageButton ListAskMore;
	// ------------------------------------------------------
	private static TextView ListAskBack;
	// ------------------------------------------------------
	public static int askBackInfoStrInt = 0;
	public static String askBackInfoStr = "";
	// ------------------------------------------------------
	public static ListView ListAskList;

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
		// ---------------------------------------------------------------------
		ListAskBack = (TextView) BBKLay.findViewById(R.id.ListAskBack);
		ListAskList = (ListView) BBKLay.findViewById(R.id.ListAskList);
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
				askBackInfoStrInt = 0;
				askBackInfoStr = "";
				ListAskBack.setText(askBackInfoStr);
				SetButtonEnable(false);
				// --------------------------------------------
				AskBaidu(ListAskEnd.getText().toString());
				// --------------------------------------------
			}
		});
		ListAskNav.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				askBackInfoStrInt = 0;
				askBackInfoStr = "";
				ListAskBack.setText(askBackInfoStr);
				SetButtonEnable(false);
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

	public static void askBackInfoStrAdd(String add) {
		// ----------------------------------------------
		askBackInfoStrInt++;
		askBackInfoStr += askBackInfoStrInt + ":\t" + add + "\r\n";
		// ----------------------------------------------
	}

	public static void NavissLoadRun() {
		// --------------------------------------------------------------------------
		ListAskStr.setText(BBKSoft.myGps.gm.g.w + "," + BBKSoft.myGps.gm.g.j);
		ListAskEnd.setText(BBKSoft.myMaps.mapPt.w + "," + BBKSoft.myMaps.mapPt.j);
		// --------------------------------------------------------------------------
		askBackInfoStrInt = 0;
		askBackInfoStr = "";
		ListAskBack.setText(askBackInfoStr);
		SetButtonEnable(false);
		// --------------------------------------------------------------------------
		String str = ListAskStr.getText().toString();
		String end = ListAskEnd.getText().toString();
		AskNavi(str, end);
		// --------------------------------------------------------------------------
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
		BBKSoft.SoftBaiDuiAsk(str);
		// ----------------------------------------------------
	}

	public static void AskNavi(String strAsk, String strEnd) {
		// ----------------------------------------------------
		if (strAsk.length() == 0 || strEnd.length() == 0) {
			BBKMsgBox.tShow(" strEnd Is Empty! ");
			return;
		}
		// ----------------------------------------------------
		// "driving", "walking", "bicycling", "transit"
		BBKSoft.myGoog.NaviRunThead(strAsk, strEnd, 3, BBKSoft.myMaps.mapPt.w, BBKSoft.myMaps.mapPt.j);
		// ----------------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	public void AskInfoListShowRun(String name) {
		// ---------------------------------------------------------
		BBKSoft.myList.ListLayAdd(//
				BBKListView.BBKLayToArrayList(BBKSoft.myLays.layask, true, false, false), //
				name);
		// ---------------------------------------------------------
		BBKSoft.MapFlash(true);
		AskInfoBackShow(name);
		// ---------------------------------------------------------
	}

	public void AskInfoBackShow(String name) {
		// ---------------------------------------------------------
		SetButtonEnable(true);
		ListAskBack.setText(askBackInfoStr);
		// ---------------------------------------------------------
		BBKSoft.SoftAskHistoryLoad();
		// ---------------------------------------------------------
	}

	private final static int BtnAlpha = 50;

	private static void SetButtonEnable(boolean k) {
		if (k) {
			ListAskMore.getBackground().setAlpha(255);
		} else {
			ListAskMore.getBackground().setAlpha(BtnAlpha);
		}
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public void ListViewBack() {
		BBKListView.ListViewLoad(//
				BBKListView.BBKLayToArrayList(BBKSoft.myLays.layask, true, false, false), //
				ListAskList			);

		// ListAskList.setAdapter(//
		// new ArrayAdapter<String>(BBKSoft.bbkContext, //
		// R.layout.list_item,//
		// getData()//
		// ));

		// ListAskList.setAdapter(//
		// new ArrayAdapter<String>(BBKSoft.bbkContext, //
		// android.R.layout.simple_expandable_list_item_1,//
		// getData()//
		// ));

	}

//	private List<String> getData() {
//		List<String> data = new ArrayList<String>();
//		data.add("测试数据1");
//		data.add("测试数据2");
//		data.add("测试数据3");
//		data.add("测试数据4");
//		return data;
//	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
}