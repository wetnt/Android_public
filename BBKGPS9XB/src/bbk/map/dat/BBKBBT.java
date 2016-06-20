package bbk.map.dat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.map.lay.BBKMapLay.poi_type;
import bbk.sys.abc.BBKSYS;

public class BBKBBT {

	// =========================================================================================
	// public static Lay_type lay = new Lay_type();
	// =========================================================================================
	public static Lay_type BBTtoLay_type(String PathName, boolean PoiOrLine) {
		// -------------------------------------------------
		Lay_type lay = new Lay_type();
		String str = BBKSYS.FileRead(PathName);
		StrtoLay_type(str, lay, PoiOrLine);
		return lay;
		// -------------------------------------------------
	}

	private static void SetLay_type(Lay_type lay, poi_type p, boolean PoiOrLine) {
		// --------------------------------------------------------
		lay.line.get(0).add(p.p.w, p.p.j);
		if (PoiOrLine)
			lay.pois.add(p);
		// --------------------------------------------------------
	}

	// =========================================================================================
	// =========================================================================================
	// =========================================================================================
	private static void StrtoLay_type(String str, Lay_type lay, boolean PoiOrLine) {
		// -------------------------------------------------
		if (str == null || str.length() < 10) {
			return;
		}
		// --------------------------------------------------
		lay.line.add(new line_type());
		// --------------------------------------------------
		int index = 0, offset = 0;
		String line = "";
		while ((index = str.indexOf("\r\n", index + 2)) != -1) {
			// --------------------------------------------------------
			line = str.substring(offset, index);
			offset = index + 2;
			// --------------------------------------------------------
			SetLay_type(lay, StrToPoi_type(line), PoiOrLine);
			// --------------------------------------------------------
		}
		// ---------------------------------------------------------------
	}

	// =========================================================================================
	private static final String bbtchar = ","; // ¼ÇÂ¼·Ö¸ô·û

	// =========================================================================================
	private static poi_type StrToPoi_type(String bbtItemLine) {
		// ---------------------------------------------------------
		// 116.34501,39.96746,47.0,20130216154850
		// ---------------------------------------------------------
		if (bbtItemLine.length() < 10) {
			return null;
		}
		// ---------------------------------------------------------
		String[] array = splitString(bbtItemLine, bbtchar);
		if (array == null)
			return null;
		// ---------------------------------------------------------
		double j = Double.parseDouble(array[0]);
		double w = Double.parseDouble(array[1]);
		double h = Double.parseDouble(array[2]);
		Date t = StrToDate(array[3]);
		// ---------------------------------------------------------
		StringBuffer bf = new StringBuffer(t.toString());
		poi_type p = new poi_type(bf, bf, bf, w, j, h, null);
		// ---------------------------------------------------------
		return p;
		// ---------------------------------------------------------
	}

	// =========================================================================================
	private static String[] splitString(String bbtItemLine, String str) {
		// ---------------------------------------------------------
		// 116.34501,39.96746,47.0,20130216154850
		// ---------------------------------------------------------
		if (bbtItemLine.length() < 10)
			return null;
		// ---------------------------------------------------------
		String[] array = new String[5];
		int index = 0, offset = 0, i = 0;
		while ((index = bbtItemLine.indexOf(str, index + 1)) != -1) {
			array[i] = bbtItemLine.substring(offset, index);
			offset = index + 1;
			i++;
		}
		array[i] = bbtItemLine.substring(offset);
		// ---------------------------------------------------------
		return array;
		// ---------------------------------------------------------
	}

	// =========================================================================================
	private static String timefmt = "yyyyMMddHHmmss";
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat tmft = new SimpleDateFormat(timefmt);

	private static Date StrToDate(String str) {
		// --------------------------------------------
		// 116.34501,39.96746,47.0,20130216154850
		// --------------------------------------------
		Date tt = new Date(System.currentTimeMillis());
		try {
			tt = tmft.parse(str);
		} catch (ParseException e) {
		}
		return tt;
	}
	// =========================================================================================
}