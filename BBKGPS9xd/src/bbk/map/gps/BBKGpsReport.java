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
		GpsInfos = "�г���Ϣͳ�� >>" + lrn + lrn;
		GpsInfos += "��ʼ��" + gpsTmFt.format(BBKSoft.myGps.gm.g.ts) + lrn;
		GpsInfos += "��ǰ��" + gpsTmFt.format(BBKSoft.myGps.gm.g.t) + lrn;
		GpsInfos += "��ʱ��" + BBKSoft.myGps.gm.g.tls + " " + lrn;
		GpsInfos += lrn;
		GpsInfos += "��̣�" + BBKSoft.myGps.gm.g.l + " km" + lrn;
		GpsInfos += "���٣�" + BBKSoft.myGps.gm.g.va + " km/h" + lrn;
		GpsInfos += "���" + BBKSoft.myGps.gm.g.vm + " km/h" + lrn;
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
		String Ask = BBKGpsReport.Gps_Statistics() + "�Ƿ�رգ� \r\n";
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