package bbk.uis.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import bbk.abc.main.R;

public class BBKLayView {

	// ---------------------------------------------------------------
	public LinearLayout BBKLay;
	public Activity bbkAct;

	// ------------------------------------------------------------------------------------------

	public void LayoutInt(Context context, int resource, int w, int h, int g) {
		// ------------------------------------------------------------------------------------------
		bbkAct = (Activity) context;
		// ------------------------------------------------------------------------------------------
		BBKLay = (LinearLayout) LayoutInflater.from(bbkAct).inflate(resource, null);
		// final LayoutInflater inflater = bbkAct.getLayoutInflater();
		// BBKLay = (LinearLayout) inflater.inflate(resource, null);
		// ------------------------------------------------------------------------------------------
		if (w == 0) {
			w = FrameLayout.LayoutParams.MATCH_PARENT;
		}
		if (h == 0) {
			h = FrameLayout.LayoutParams.MATCH_PARENT;
		} else if (h == 1) {
			h = FrameLayout.LayoutParams.WRAP_CONTENT;
		}
		if (g == 0) {
			g = Gravity.TOP | Gravity.LEFT;
		}
		// -----------------------------------------------------------------------
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(w, h, g);
		// -----------------------------------------------------------------------
		params.topMargin = 100;
		params.rightMargin = 0;
		params.leftMargin = 0;
		params.bottomMargin = 80;
		// -----------------------------------------------------------------------
		FrameLayout FrameLay = (FrameLayout) bbkAct.findViewById(R.id.FrameLay);
		FrameLay.addView(BBKLay, params);
		// ------------------------------------------------------------------------------------------
	}

	public void layshow(boolean show) {
		// ------------------------------------------------------------------------------------------
		if (show) {
			BBKLay.setVisibility(View.VISIBLE);
		} else {
			BBKLay.setVisibility(View.GONE);
		}
		// ------------------------------------------------------------------------------------------
	}

	public void layshowe() {
		// ------------------------------------------------------------------------------------------
		if (BBKLay.isShown()) {
			BBKLay.setVisibility(View.GONE);
		} else {
			BBKLay.setVisibility(View.VISIBLE);
		}
		// ------------------------------------------------------------------------------------------
	}

	public boolean islayshow() {
		// -----------------------------------------------
		if (BBKLay.getVisibility() == View.VISIBLE) {
			return true;
		} else {
			return false;
		}
		// -----------------------------------------------
	}

	public boolean layback() {
		// -----------------------------------------------
		if (BBKLay.getVisibility() == View.VISIBLE) {
			layshow(false);
			return true;
		} else {
			return false;
		}
		// -----------------------------------------------
	}
	// ====================================================================================

}
