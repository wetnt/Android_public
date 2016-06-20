package bbk.map.uis;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import bbk.abc.main.R;
import bbk.bbk.box.BBKSoft;
import bbk.map.abc.BBKMap;
import bbk.map.abc.BBKMap.MapPoiWJ;
import bbk.map.abc.BBKMapMath;
import bbk.map.lay.BBKMapLay;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.map.lay.BBKMapLay.poi_type;
import bbk.uis.view.BBKLayView;

public class Map_Measure extends BBKLayView {

	// ---------------------------------------------------------------
	ImageButton MeasureB, MeasureS, MeasureX;
	EditText MeasureT;
	public double LineMeasureD = 0;

	// ---------------------------------------------------------------
	public void LayInt(Context pthis) {
		// ------------------------------------------------------------------------------------------
		int w = FrameLayout.LayoutParams.MATCH_PARENT;// BBKSoft.myBoxs.screenW;
		int h = FrameLayout.LayoutParams.WRAP_CONTENT;
		LayoutInt(pthis, R.layout.map_measure, w, h, 0);
		// ------------------------------------------------------------------------------------------
		MeasureB = (ImageButton) BBKLay.findViewById(R.id.MeasureBack);
		MeasureS = (ImageButton) BBKLay.findViewById(R.id.MeasureSave);
		MeasureX = (ImageButton) BBKLay.findViewById(R.id.MeasureExit);
		MeasureT = (EditText) BBKLay.findViewById(R.id.MeasureText);
		// ------------------------------------------------------------------------------------------
		MeasureB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				BBKSoft.myLays.laymes.line.get(0).NcutOne();
				int n = BBKSoft.myLays.laymes.pois.size() - 1;
				if (n >= 0)
					BBKSoft.myLays.laymes.pois.remove(n);
				LineMeasureShow();
				BBKSoft.MapFlash(true);
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		MeasureS.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				LineMeasureSave();
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		MeasureX.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				MeasureLayShow = false;
				BBKSoft.myLays.laymes.clear();
				LineMeasureD = 0;
				BBKSoft.MapFlash(false);
				layshow(false);
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		BBKLay.setVisibility(View.INVISIBLE);
		// ------------------------------------------------------------------------------------------
	}

	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat TmFmt = new SimpleDateFormat("yyyyMMdd_HHmmss");

	// ===========================================================================================
	public void LineMeasureSave() {
		// --------------------------------------------
		Date LineDate = new Date(System.currentTimeMillis());
		String nm = TmFmt.format(LineDate) + "_" + LineMeasureD + "km";
		BBKSoft.myLays.LaySave(BBKSoft.myLays.laymes, BBKSoft.PathAsks + nm);
		// --------------------------------------------
		// BBKMapBBT.BBL_type.line_save(paths,
		// BBKSoft.myLays.laymes.line.get(0));
		// --------------------------------------------
	}

	public void LineMeasureShow() {
		// ----------------------------------------------------
		if (BBKSoft.myLays.laymes.line == null || BBKSoft.myLays.laymes.line.size() <= 0) {
			MeasureT.setText("0 KM");
			return;
		}
		// ----------------------------------------------------
		LineMeasureD = BBKMapLay.LineMeasure(BBKSoft.myLays.laymes.line.get(0));
		MeasureT.setText(LineMeasureD + " KM");
		// ----------------------------------------------------
	}

	// ===========================================================================================

	// ====================================================================================
	// ##############################MeasureLayShow########################################
	// ====================================================================================
	public boolean MeasureLayShow = false;

	public boolean MeasureRunTouch(float x, float y) {
		// --------------------------------------------
		if (!MeasureLayShow)
			return false;
		// --------------------------------------------
		if (BBKSoft.myLays.laymes.line.get(0) == null)
			BBKSoft.myLays.laymes.line.add(new line_type());
		// --------------------------------------------
		MapPoiWJ r = BBKMap.GetWJByPoint(x, y);
		BBKSoft.myLays.laymes.line.get(0).add(r.w, r.j);
		// --------------------------------------------
		double ld = 0;
		int n = BBKSoft.myLays.laymes.line.get(0).p.size() - 1;
		if (n > 0) {
			double w0 = BBKSoft.myLays.laymes.line.get(0).p.get(n).w;
			double j0 = BBKSoft.myLays.laymes.line.get(0).p.get(n).j;
			double ww = BBKSoft.myLays.laymes.line.get(0).p.get(n - 1).w;
			double jj = BBKSoft.myLays.laymes.line.get(0).p.get(n - 1).j;
			ld = BBKMapMath.GetDistance(w0, j0, ww, jj);
		}
		int i = BBKSoft.myLays.laymes.pois.size();
		StringBuffer d = new StringBuffer(i + ") " + ld + "km");
		StringBuffer f = new StringBuffer("0");
		poi_type p = new poi_type(d, f, d, r.w, r.j, 0, null);
		BBKSoft.myLays.laymes.pois.add(p);
		// --------------------------------------------
		LineMeasureShow();
		BBKSoft.MapFlash(false);
		// --------------------------------------------
		return true;
		// --------------------------------------------
	}

}
