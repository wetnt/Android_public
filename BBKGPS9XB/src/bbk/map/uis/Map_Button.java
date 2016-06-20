package bbk.map.uis;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import bbk.abc.main.R;
import bbk.bbk.box.BBKSoft;
import bbk.uis.view.BBKLayView;

public class Map_Button extends BBKLayView {

	// ====================================================================================
	public Button BtnOut, BtnInt;
	private Drawable GpsLock, GpsFree;
	public ImageButton BtnMap, BtnIMH, BtnPOI, BtnMenu;// , BtnExt;
	private int BtnAlpha = 100;

	// ====================================================================================
	public void ButtonSet(Context context) {
		// ================================================================
		int w = FrameLayout.LayoutParams.FILL_PARENT;// BBKSoft.myBoxs.screenW;
		int h = FrameLayout.LayoutParams.WRAP_CONTENT;
		int g = Gravity.BOTTOM;
		LayoutInt(context, R.layout.map_btn, w, h, g);
		// ================================================================
		BtnOut = (Button) BBKLay.findViewById(R.id.OUT);
		BtnInt = (Button) BBKLay.findViewById(R.id.INZ);
		// ================================================================
		BtnMap = (ImageButton) BBKLay.findViewById(R.id.MAP);
		BtnIMH = (ImageButton) BBKLay.findViewById(R.id.IMH);
		BtnPOI = (ImageButton) BBKLay.findViewById(R.id.POI);
		// ================================================================
		BtnMap.getBackground().setAlpha(BtnAlpha);
		BtnOut.getBackground().setAlpha(BtnAlpha);
		BtnInt.getBackground().setAlpha(BtnAlpha);
		BtnIMH.getBackground().setAlpha(BtnAlpha);
		BtnPOI.getBackground().setAlpha(BtnAlpha);
		// ================================================================
		BtnOut.setText("-");
		BtnInt.setText("+");
		// BtnMap.setText("Map");
		// BtnIMH.setText("ImH");
		// BtnPOI.setText("Poi");
		// BtnMenu.setText("Mu");
		// ================================================================
		BtnMap.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				BBKSoft.myMaps.MapTypeSet();
				BBKSoft.MapFlash(true);
				// --------------------------------------------
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
		BtnIMH.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ----------------------------------------------------
				BBKGpsFollow();
				// ----------------------------------------------------
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
		// ================================================================
		BtnMenu = (ImageButton) BBKLay.findViewById(R.id.Menu);
		BtnMenu.getBackground().setAlpha(BtnAlpha);
		// ----------------------------------------------------------------
		BtnMenu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------------------
				bbkAct.openOptionsMenu();// °´¼üµ¯³ö²Ëµ¥
				// --------------------------------------------------------
			}
		});
		// ----------------------------------------------------------------
		BtnMenu.setVisibility(View.GONE);
		// ================================================================
		GpsLock = bbkAct.getResources().getDrawable(android.R.drawable.ic_menu_mylocation);
		GpsFree = bbkAct.getResources().getDrawable(android.R.drawable.ic_menu_send);
		// ================================================================
	}

	// ====================================================================================
	public void BBKGpsFollow() {
		// ----------------------------------------------------------------------
		BBKSoft.GpsIsFollow = !BBKSoft.GpsIsFollow;
		// ----------------------------------------------------------------------
		if (BBKSoft.GpsIsFollow && BBKSoft.myGps.gm.g.w != 0 && BBKSoft.myGps.gm.g.j != 0) {
			// ------------------------------------------------------------------
			BBKSoft.myMaps.MapCenterSet(BBKSoft.myGps.gm.g.w, BBKSoft.myGps.gm.g.j);
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
	// ====================================================================================
	// ====================================================================================

}
