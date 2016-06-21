package bbk.map.gps;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import bbk.bbk.box.BBKSoft;
import bbk.sys.abc.BBKMsgBox;

public class BBKGpsReport {

	@SuppressLint("SimpleDateFormat")
	private final static SimpleDateFormat gpsTmFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public final static String lrn = "\r\n";
	public static String GpsInfos = "";

	public static String Gps_Statistics() {
		// ------------------------------------------------------------------
		GpsInfos = "行程信息统计 >>" + lrn + lrn;
		GpsInfos += "起始：" + gpsTmFt.format(BBKSoft.myGps.gm.g.ts) + lrn;
		GpsInfos += "当前：" + gpsTmFt.format(BBKSoft.myGps.gm.g.t) + lrn;
		GpsInfos += "耗时：" + BBKSoft.myGps.gm.g.tls + " " + lrn;
		GpsInfos += lrn;
		GpsInfos += "里程：" + BBKSoft.myGps.gm.g.l + " km" + lrn;
		GpsInfos += "均速：" + BBKSoft.myGps.gm.g.va + " km/h" + lrn;
		GpsInfos += "最大：" + BBKSoft.myGps.gm.g.vm + " km/h" + lrn;
		GpsInfos += lrn;
		// ------------------------------------------------------------------
		return GpsInfos;
		// ------------------------------------------------------------------
	}

	// ====================================================================================
	public static void GpsInfoShow() {
		// ---------------------------------------------------------------------
		BBKSoft.myGps.gm.GpsRuns();
		BBKMsgBox.MsgOK(Gps_Statistics());
		// ---------------------------------------------------------------------
	}

	// ====================================================================================
	public static void AlertDialogExit(Context act) {
		// ---------------------------------------------------------------------
		if (BBKSoft.myGps.gm.g.l < 0.5) {
			BBKSoft.SoftExit();
			return;
		}
		// --------------------------------------------------------------------------------
		String Ask = BBKGpsReport.Gps_Statistics() + "是否关闭？ \r\n";
		DialogInterface.OnClickListener YesLs = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == DialogInterface.BUTTON_POSITIVE) {
					// -------------------------------------------------------------------
					BBKSoft.SoftExit();
					// -------------------------------------------------------------------
				}
			}
		};
		BBKMsgBox.MsgYesNo(Ask, "Yes", "NO", YesLs);
		// --------------------------------------------------------------------------------
	}
	// ====================================================================================
}