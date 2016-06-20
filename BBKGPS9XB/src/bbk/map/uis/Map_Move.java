package bbk.map.uis;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import bbk.abc.main.R;
import bbk.bbk.box.BBKSoft;
import bbk.sys.abc.BBKSYS;
import bbk.sys.abc.BBKSYS.DFSX_type;
import bbk.uis.view.BBKLayView;

public class Map_Move extends BBKLayView {

	// ---------------------------------------------------------------
	EditText txtMoveWW, txtMoveWD, txtMoveWF, txtMoveWS;
	EditText txtMoveJJ, txtMoveJD, txtMoveJF, txtMoveJS;
	ImageButton btntMoveRun, btntMoveExt;
	TextView txtMoveInfo;

	// ---------------------------------------------------------------

	public void LayInt(final Context ctxt) {
		// ------------------------------------------------------------------------------------------
		int w = FrameLayout.LayoutParams.MATCH_PARENT;// BBK.myBoxs.screenW;
		int h = FrameLayout.LayoutParams.WRAP_CONTENT;
		int g = Gravity.TOP | Gravity.CENTER;// Gravity.TOP | Gravity.RIGHT;
		LayoutInt(ctxt, R.layout.map_move, w, h, g);
		// ------------------------------------------------------------------------------------------
		txtMoveWW = (EditText) BBKLay.findViewById(R.id.txtMoveWW);
		txtMoveWD = (EditText) BBKLay.findViewById(R.id.txtMoveWD);
		txtMoveWF = (EditText) BBKLay.findViewById(R.id.txtMoveWF);
		txtMoveWS = (EditText) BBKLay.findViewById(R.id.txtMoveWS);
		// ------------------------------------------------------------------
		txtMoveJJ = (EditText) BBKLay.findViewById(R.id.txtMoveJJ);
		txtMoveJD = (EditText) BBKLay.findViewById(R.id.txtMoveJD);
		txtMoveJF = (EditText) BBKLay.findViewById(R.id.txtMoveJF);
		txtMoveJS = (EditText) BBKLay.findViewById(R.id.txtMoveJS);
		// ------------------------------------------------------------------
		btntMoveRun = (ImageButton) BBKLay.findViewById(R.id.btntMoveRun);
		btntMoveExt = (ImageButton) BBKLay.findViewById(R.id.btntMoveExt);
		// ------------------------------------------------------------------
		txtMoveInfo = (TextView) BBKLay.findViewById(R.id.txtMoveInfo);
		// ===========================================================================================
		btntMoveRun.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				String ws = txtMoveWW.getText().toString();
				String js = txtMoveJJ.getText().toString();
				if (ws.length() == 0 || js.length() == 0) {
					return;
				}
				double w = Double.parseDouble(ws);
				double j = Double.parseDouble(js);
				BBKSoft.myMaps.MapCenterSet(w, j);
				BBKSoft.MapFlash(true);
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		btntMoveExt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				layshow(false);
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		// ------------------------------------------------------------------------------------------
		txtMoveWW.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// --------------------------------------------

				// --------------------------------------------

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// --------------------------------------------
				// --------------------------------------------
			}

			@Override
			public void afterTextChanged(Editable s) {
				// --------------------------------------------
				WWtoDFS();
				// --------------------------------------------
			}

		});
		// ------------------------------------------------------------------------------------------
		// ------------------------------------------------------------------------------------------
		txtMoveJJ.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// --------------------------------------------
				JJtoDFS();
				// --------------------------------------------

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// --------------------------------------------
				// --------------------------------------------
			}

			@Override
			public void afterTextChanged(Editable s) {
				// --------------------------------------------
				// --------------------------------------------
			}

		});
		// ------------------------------------------------------------------------------------------
		txtMoveWD.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				DFStoWW();
			}
		});
		// ------------------------------------------------------------------------------------------
		txtMoveWF.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				DFStoWW();
			}
		});
		// ------------------------------------------------------------------------------------------
		txtMoveWS.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				DFStoWW();
			}
		});
		// ------------------------------------------------------------------------------------------
		txtMoveJD.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				DFStoJJ();
			}
		});
		// ------------------------------------------------------------------------------------------
		txtMoveJF.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				DFStoJJ();
			}
		});
		// ------------------------------------------------------------------------------------------
		txtMoveJS.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				DFStoJJ();
			}
		});
		// ------------------------------------------------------------------------------------------
		SetEditMapJW(BBKSoft.myMaps.mapPt.w, BBKSoft.myMaps.mapPt.j);
		btntMoveExt.requestFocus();
		layshow(false);
		// ------------------------------------------------------------------------------------------
	}

	// //
	// ===========================================================================================
	// public void LayShow(boolean show) {
	// // BBKLoginLay.isShown()
	// // --------------------------------------------
	// if (show) {
	// BBKLay.setVisibility(View.VISIBLE);
	// } else {
	// BBKLay.setVisibility(View.GONE);
	// }
	// // --------------------------------------------
	// }

	// ===========================================================================================
	public void MapMove(double w, double j) {
		// --------------------------------------------
		if (BBKLay.isShown()) {
			SetEditMapJW(w, j);
		}
		// --------------------------------------------
	}

	public void SetEditMapJW(double w, double j) {
		// --------------------------------------------
		txtMoveWW.setFocusable(true);
		txtMoveWW.setFocusableInTouchMode(true);
		txtMoveWW.requestFocus();
		txtMoveWW.setText(w + "");
		// --------------------------------------------
		txtMoveJJ.setFocusable(true);
		txtMoveJJ.setFocusableInTouchMode(true);
		txtMoveJJ.requestFocus();
		txtMoveJJ.setText(j + "");
		// --------------------------------------------
	}

	// ===========================================================================================
	public void DFStoWW() {
		// --------------------------------------------
		if (txtMoveWW.isFocused()) {
			return;
		}
		// --------------------------------------------
		String wd = txtMoveWD.getText().toString();
		String wf = txtMoveWF.getText().toString();
		String ws = txtMoveWS.getText().toString();
		// --------------------------------------------
		double wdd = 0, wff = 0, wss = 0;
		if (wd.length() != 0) {
			wdd = Double.parseDouble(wd);
		}
		if (wf.length() != 0) {
			wff = Double.parseDouble(wf);
		}
		if (ws.length() != 0) {
			wss = Double.parseDouble(ws);
		}
		// --------------------------------------------
		BBKSYS.DFSX_type www = new BBKSYS.DFSX_type(0);
		www.DFStoDD(wdd, wff, wss);
		// --------------------------------------------
		txtMoveWW.setText(www.ddd + "");
		// --------------------------------------------
	}

	// ===========================================================================================
	public void DFStoJJ() {
		// --------------------------------------------
		if (txtMoveJJ.isFocused()) {
			return;
		}
		// --------------------------------------------
		String jd = txtMoveJD.getText().toString();
		String jf = txtMoveJF.getText().toString();
		String js = txtMoveJS.getText().toString();
		// --------------------------------------------
		double jdd = 0, jff = 0, jss = 0;
		if (jd.length() != 0) {
			jdd = Double.parseDouble(jd);
		}
		if (jf.length() != 0) {
			jff = Double.parseDouble(jf);
		}
		if (js.length() != 0) {
			jss = Double.parseDouble(js);
		}
		// --------------------------------------------
		BBKSYS.DFSX_type jjj = new BBKSYS.DFSX_type(0);
		jjj.DFStoDD(jdd, jff, jss);
		// --------------------------------------------
		txtMoveJJ.setText(jjj.ddd + "");
		// --------------------------------------------
	}

	// ===========================================================================================
	public void WWtoDFS() {
		// --------------------------------------------
		if (!txtMoveWW.isFocused()) {
			return;
		}
		// --------------------------------------------
		String ws = txtMoveWW.getText().toString();
		double ww = 0;
		if (ws.length() != 0) {
			ww = Double.parseDouble(ws);
		}
		DFSX_type wd = new DFSX_type(ww);
		// --------------------------------------------
		txtMoveWD.setText(wd.d + "");
		txtMoveWF.setText(wd.f + "");
		txtMoveWS.setText(wd.s + "");
		// --------------------------------------------
	}

	public void JJtoDFS() {
		// --------------------------------------------
		if (!txtMoveJJ.isFocused()) {
			return;
		}
		// --------------------------------------------
		String js = txtMoveJJ.getText().toString();
		double jj = 0;
		if (js.length() != 0) {
			jj = Double.parseDouble(js);
		}
		DFSX_type jd = new DFSX_type(jj);
		// --------------------------------------------
		txtMoveJD.setText(jd.d + "");
		txtMoveJF.setText(jd.f + "");
		txtMoveJS.setText(jd.s + "");
		// --------------------------------------------
	}
	// ===========================================================================================
}
