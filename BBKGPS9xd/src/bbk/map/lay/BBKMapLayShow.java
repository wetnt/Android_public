package bbk.map.lay;

import android.graphics.Paint;
import android.graphics.Path;
import bbk.map.abc.BBKMap;
import bbk.map.abc.BBKMap.MapPoiWJ;
import bbk.map.abc.BBKMap.MapPoiXY;
import bbk.map.abc.BBKMapImage;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.map.lay.BBKMapLay.poi_type;

public class BBKMapLayShow {

	// ==========================================================================================
	// public static int lineR = 2;//
	// ------------------------------------------------------------
	public static int poiCircleRadius = 3;// POI原点半径
	public static int maxNameLength = 12; // poi名称显示长度限值
	// ------------------------------------------------------------
	public static int maxLineLength = 100;// 抽稀限值高于此值则抽稀
	public static int sampleMapZoom = 16;// 抽稀地图级别限值
	public static int sampleInterval = 8;// 抽稀间隔采样值

	// ------------------------------------------------------------

	// ==========================================================================================
	public static void LayShow(Lay_type lay, Paint p) {
		// ------------------------------------------------------------
		for (int i = 0; i < lay.poly.size(); i++) {
			Line_Type_Show(lay.poly.get(i), true, p);
		}
		// ------------------------------------------------------------
		for (int i = 0; i < lay.line.size(); i++) {
			Line_Type_Show(lay.line.get(i), false, p);
		}
		// ------------------------------------------------------------
		for (int i = 0; i < lay.pois.size(); i++) {
			PoisShow(lay.pois.get(i), p);
		}
		// ------------------------------------------------------------
	}

	// =================================================================================
	public static void PoisShow(poi_type poi, Paint p) {
		// -------------------------------------------------------------------------
		if (poi == null)
			return;
		// -------------------------------------------------------------------------
		poi.p.newxyp();
		// -------------------------------------------------------------------------
		if (!poi.p.v)
			return;
		// -------------------------------------------------------------------------
		BBKMapImage.DrawCircleP(poi.p.x, poi.p.y, poiCircleRadius, p);
		if (BBKMap.mapzm >= 14 && poi.s.a.length() > 0) {
			PoisTextDraw(poi.p.x, poi.p.y, poi.s.a.toString(), p);
		}
		// -------------------------------------------------------------------------
		// -------------------------------------------------------------------------
	}

	// =============================================================================
	public static void PoisTextDraw(int x, int y, String name, Paint pr) {
		// -------------------------------------------------------------------------
		if (name.length() > maxNameLength)
			name = name.substring(0, maxNameLength);
		// -------------------------------------------------------------------------
		int n = 10, m = 90, k = 36;
		m = 12 * name.length();
		// -------------------------------------------------------------------------
		Path pt = new Path();
		pt.moveTo(x, y);
		pt.lineTo(x - n, y - n);
		pt.lineTo(x - m, y - n);
		pt.lineTo(x - m, y - k);
		pt.lineTo(x + m, y - k);
		pt.lineTo(x + m, y - n);
		pt.lineTo(x + n, y - n);
		pt.close();
		BBKMapImage.DrawTextPath(pt);
		// -------------------------------------------------------------------------
		MapPoiXY[] p = new MapPoiXY[7];
		p[0] = new MapPoiXY(x - n, y - n);
		p[1] = new MapPoiXY(x - m, y - n);
		p[2] = new MapPoiXY(x - m, y - k);
		p[3] = new MapPoiXY(x + m, y - k);
		p[4] = new MapPoiXY(x + m, y - n);
		p[5] = new MapPoiXY(x + n, y - n);
		p[6] = new MapPoiXY(x, y);
		DrawLineP(p, pr);
		BBKMapImage.DrawTextP(" " + name, x - m, y - n - 4, pr);
		// -------------------------------------------------------------------------
	}

	public static void DrawLineP(MapPoiXY[] p, Paint pt) {
		// -------------------------------------------------------------------------
		int x = p[0].x, y = p[0].y;
		for (int i = 1; i < p.length; i++) {
			BBKMapImage.DrawLineP(x, y, p[i].x, p[i].y, pt);
			x = p[i].x;
			y = p[i].y;
		}
		BBKMapImage.DrawLineP(x, y, p[0].x, p[0].y, pt);
		// -------------------------------------------------------------------------
	}

	// =============================================================================

	public static void Line_Type_Show(line_type poly, boolean PolyKey, Paint p) {
		// ------------------------------------------------------------------
		if (poly == null || poly.p.size() < 2) {
			return;
		}
		// ------------------------------------------------------------------
		poly.newxy();
		if (!poly.pv)
			return;
		// ------------------------------------------------------------------
		int n = sampleInterval;
		if (BBKMap.mapzm > sampleMapZoom || poly.p.size() < maxLineLength)
			n = 1;
		// ------------------------------------------------------------------
		int x = poly.p.get(0).x, y = poly.p.get(0).y;
		for (int i = 1; i < poly.p.size(); i = i + n) {
			// --------------------------------------------------------------
			BBKMapImage.DrawLineP(x, y, poly.p.get(i).x, poly.p.get(i).y, p);
			// BBKMapImage.DrawCircleP(x, y, lineR, p);
			// --------------------------------------------------------------
			x = poly.p.get(i).x;
			y = poly.p.get(i).y;
			// --------------------------------------------------------------
		}
		if (PolyKey) {
			BBKMapImage.DrawLineP(x, y, poly.p.get(0).x, poly.p.get(0).y, p);
			// BBKMapImage.DrawCircleP(x, y, lineR, p);
		}
		// ------------------------------------------------------------------
	}

	// =============================================================================
	// =============================================================================
	public static MapPoiWJ GetLayCenter(Lay_type lay) {
		// -----------------------------------------------------------
		double ww = 0, jj = 0;
		int n = 0, k = 0, m = 0;
		// -------------------------------------------------
		n = lay.pois.size();
		for (int i = 0; i < n; i++) {
			ww += lay.pois.get(i).p.w;
			jj += lay.pois.get(i).p.j;
			m++;
		}
		// -------------------------------------------------
		n = lay.line.size();
		for (int i = 0; i < n; i++) {
			k = lay.line.get(i).p.size();
			for (int j = 0; j < k; j++) {
				ww += lay.line.get(i).p.get(j).w;
				jj += lay.line.get(i).p.get(j).j;
				m++;
			}
		}
		// -------------------------------------------------
		n = lay.poly.size();
		for (int i = 0; i < n; i++) {
			k = lay.poly.get(i).p.size();
			for (int j = 0; j < k; j++) {
				ww += lay.poly.get(i).p.get(j).w;
				jj += lay.poly.get(i).p.get(j).j;
				m++;
			}
		}
		// -----------------------------------------------------------
		ww = ww / m;
		jj = jj / m;
		// -----------------------------------------------------------
		MapPoiWJ r = new MapPoiWJ(ww, jj);
		r.FormatWJ();
		return r;
		// -----------------------------------------------------------
	}

	// =============================================================================
	// =============================================================================
	// =============================================================================

}
