package bbk.bbk.box;

import android.content.Context;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.TextView;
import bbk.abc.main.R;
import bbk.hrd.abc.BBKScreen;
import bbk.map.abc.BBKMapImage;
import bbk.uis.view.BBKLayView;
import bbk.zzz.debug.BBKDebug;

public class BBKMapBox extends BBKLayView {
	// ====================================================================================
	public void LayInt(final Context context) {
		// ================================================================
		LayoutInt(context, R.layout.map_box, 0, 0, 0);
		// ================================================================
		BMBInt(context);
		// ================================================================
	}

	public void LayReInt() {
		// ----------------------------------------------------
		// DisplayMetrics dm = new DisplayMetrics();
		// bbkAct.getWindowManager().getDefaultDisplay().getMetrics(dm);
		// screenW = dm.widthPixels;
		// screenH = dm.heightPixels;
		// ----------------------------------------------------
		int[] wh = BBKScreen.getScreenWidthAndSizeInPx(bbkAct);
		int t = BBKScreen.getTitleHeight(bbkAct);
		int s = BBKScreen.getStateHeight(bbkAct);

		BBKDebug.d("t = " + t + " s=" + s, true, true);

		screenW = wh[0];// BBKLay.getWidth();
		screenH = wh[1] - t - s;// BBKLay.getHeight();

		Rect rect = new Rect();
		bbkAct.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

		screenW = rect.width();
		screenH = rect.height() - 100;

		// ----------------------------------------------------
		BBKDebug.d("WxH = " + screenW + "x" + screenH, true, true);
	}

	// =================–≈œ¢¥∞==============================================================
	public ImageView MapImg;
	public TextView LabTit, LabSpd, LabInf, LabBlc;
	public TextView TxtCrs;
	public int screenW, screenH;

	// ====================================================================================
	public void BMBInt(Context context) {
		LayReInt();
		// ----------------------------------------------------
		// screenS = this.getResources().getConfiguration().orientation;
		// setRequesteOrientation(ActivityInfo.SCREEN_ORIENTATION_LADSCAPE);
		// ----------------------------------------------------
		MapImg = (ImageView) bbkAct.findViewById(R.id.ImgMap);
		MapImg.setBackgroundColor(-1);
		// ----------------------------------------------------
		LabTit = (TextView) bbkAct.findViewById(R.id.TxtTitle);
		LabSpd = (TextView) bbkAct.findViewById(R.id.TxtSpeed);
		LabInf = (TextView) bbkAct.findViewById(R.id.TxtInfo);
		LabBlc = (TextView) bbkAct.findViewById(R.id.TxtBlc);
		// ----------------------------------------------------
		LabTit.setText(BBKAbout.SoftTitle);
		LabSpd.setText(" 0.0 km/h");
		LabInf.setText("INFO");
		LabBlc.setText("500KM");
		// ----------------------------------------------------
		TxtCrs = (TextView) bbkAct.findViewById(R.id.TxtCross);
		TxtCrs.setText("+");
		// ----------------------------------------------------
	}

	// ====================================================================================
	public void MapScrn() {
		// ----------------------------------------------------
		// private static Matrix matrix = new Matrix();
		// matrix.reset();
		// matrix.setTranslate(300,500);
		// matrix.setRotate(90);
		// MapImg.setImageMatrix(matrix);
		// ----------------------------------------------------
		MapImg.setImageBitmap(BBKMapImage.Map);
		// ----------------------------------------------------
		// -------------------------------------------------------------------------
		// matrix.reset();
		// matrix.setTranslate(0,0);
		// matrix.preRotate(90, 240,400);
		// MapImg.setImageMatrix(matrix);
		// //
		// -------------------------------------------------------------------------
		// MapImg.setImageBitmap(BBKMapImage.Map);
		// -------------------------------------------------------------------------
	}

	// ====================================================================================
	// public void LabInfShow() {
	// LabInf.setText(BBK.myMaps.myBBD.BBDDownInfos());
	// }
	// ====================================================================================
	// ====================================================================================
	// =====================================================================================
	// =====================================================================================
}
