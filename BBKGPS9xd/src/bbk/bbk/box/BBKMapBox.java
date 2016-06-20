package bbk.bbk.box;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import bbk.map.abc.BBKMapImage;
import bbk.uis.view.BBKLayView;
import bbk.zzz.debug.BBKDebug;

import com.example.bbkgps9xd.MainActivity;
import com.example.bbkgps9xd.R;

public class BBKMapBox extends BBKLayView {
	// ====================================================================================
	public void LayInt(final Context context) {
		LayoutInt(context, R.layout.map_box, 0, 0, 0, 0);
		BMBInt(context);
	}

	public void LayReInt() {
		// ----------------------------------------------------		
		FrameLayout MainLay = (FrameLayout) MainActivity.act.findViewById(R.id.MainBox);
		screenW = MainLay.getWidth();
		screenH = MainLay.getHeight();
		// ----------------------------------------------------		
		BBKDebug.d("WxH = " + screenW + "x" + screenH, true, true);
		// ----------------------------------------------------
	}

	// =================–≈œ¢¥∞==============================================================
	public ImageView MapImg;
	public TextView LabTit, LabSpd, LabInf, LabBlc;
	public TextView TxtCrs;
	public int screenW, screenH;

	// ====================================================================================
	public void BMBInt(Context context) {
		// ----------------------------------------------------
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
		MapImg.setImageBitmap(BBKMapImage.Map);
	}
	// ====================================================================================
	// ====================================================================================
	// =====================================================================================
	// =====================================================================================
}
