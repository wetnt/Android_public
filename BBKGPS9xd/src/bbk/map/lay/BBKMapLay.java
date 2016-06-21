package bbk.map.lay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import bbk.map.abc.BBKMap;
import bbk.map.abc.BBKMapMath;
import bbk.sys.abc.BBKSYS;

public class BBKMapLay {

	// =============================================================================

	public static String bbklayExtension = ".bjs";

	public static class Lay_type {
		// ------------------------------------------------------------
		public List<poi_type> pois = new ArrayList<poi_type>();
		public List<line_type> line = new ArrayList<line_type>();
		public List<line_type> poly = new ArrayList<line_type>();

		// ------------------------------------------------------------
		// public int epi = -1, eln = -1, elp = -1, eyn = -1, eyp = -1;
		// public boolean edit = false;

		// ------------------------------------------------------------
		public void addlay(Lay_type l) {
			// ------------------------------------------------------------
			for (int i = 0; i < l.poly.size(); i++) {
				poly.add(l.poly.get(i));
			}
			// ------------------------------------------------------------
			for (int i = 0; i < l.line.size(); i++) {
				line.add(l.line.get(i));
			}
			// ------------------------------------------------------------
			for (int i = 0; i < l.pois.size(); i++) {
				pois.add(l.pois.get(i));
			}
			// ------------------------------------------------------------
		}

		public void clone(Lay_type l) {
			// ------------------------------------------------------------
			poly.clear();
			line.clear();
			pois.clear();
			addlay(l);
			// ------------------------------------------------------------
		}

		// ------------------------------------------------------------
		public void clear() {
			pois.clear();
			line.clear();
			poly.clear();
		}
		// ------------------------------------------------------------
	}

	// =================================================================================
	public static void layload(Lay_type lay, String pathname) {
		// ------------------------------------------------------------
		String layjs = BBKSYS.FileRead(pathname + bbklayExtension);
		lay.clone(BBKMapLayJson.BBKMapLayFromJson(layjs));
		layjs = null;
		// ------------------------------------------------------------
	}

	public static void laysave(Lay_type lay, String pathname) {
		// ------------------------------------------------------------
		if (lay.pois == null && lay.line == null && lay.poly == null)
			return;
		// ------------------------------------------------------------
		JSONObject layob = BBKMapLayJson.BBKMapLayToJson(lay);
		BBKSYS.FileSave(pathname + bbklayExtension, layob.toString());
		layob = null;
		// ------------------------------------------------------------
	}

	// =================================================================================

	// =================================================================================
	// =================================================================================
	// =================================================================================
	// =================================================================================
	// =================================================================================

	public static class line_type {

		public StringBuffer name = new StringBuffer("");// String非常耗费内存
		public List<p_point> p = new ArrayList<p_point>();
		public boolean pv = false;

		public void add(double wd, double jd) {
			// ---------------------------------------
			p.add(new p_point(wd, jd));
			// ---------------------------------------
			// if (fst) {
			// pzx = new p_point(wd, jd);
			// pys = new p_point(wd, jd);
			// fst = false;
			// } else {
			// if (wd > pys.w)
			// pys.w = wd;
			// if (jd > pys.j)
			// pys.j = jd;
			// if (wd < pzx.w)
			// pzx.w = wd;
			// if (jd < pzx.j)
			// pzx.j = jd;
			// }
			// ---------------------------------------
		}

		public void newxy() {
			// ---------------------------------------
			pv = false;
			// ---------------------------------------
			for (int i = 0; i < p.size(); i++) {
				p.get(i).newxyp();
				if (p.get(i).v) {
					pv = true;
				}
			}
			// ---------------------------------------
		}

		public void NcutOne() {
			if (p.size() <= 0)
				return;
			p.remove(p.size() - 1);
		}

	}

	public static class pois_type {
		public List<poi_type> poi = new ArrayList<poi_type>();

		public void add(StringBuffer nm, StringBuffer pp, StringBuffer ss, double wd, double jd, double hb, Date dt) {
			poi_type p = new poi_type(nm, pp, ss, wd, jd, hb, dt);
			poi.add(p);
		}

		public void reSet(int i, StringBuffer nm, StringBuffer pp, StringBuffer ss, double wd, double jd, double hb, Date dt) {
			poi_type p = new poi_type(nm, pp, ss, wd, jd, hb, dt);
			poi.set(i, p);
		}

		public void del(int i) {
			poi.remove(i);
		}

		public void newxy() {
			for (int i = 0; i < poi.size(); i++) {
				poi.get(i).p.newxyp();
			}
		}

		public void NcutOne() {
			int n = poi.size();
			poi.remove(n);
		}

	}

	public static class poi_type {
		public p_point p;
		public p_hbdate h;
		public p_string s;

		public poi_type(StringBuffer nm, StringBuffer pp, StringBuffer ss, double wd, double jd, double hb, Date dt) {
			set(nm, pp, ss, wd, jd, hb, dt);
		}

		public void set(StringBuffer nm, StringBuffer pp, StringBuffer ss, double wd, double jd, double hb, Date dt) {
			p = new p_point(wd, jd);
			h = new p_hbdate(hb, dt);
			s = new p_string(nm, pp, ss);
		}
	}

	// =============================================================================
	public static class p_string {
		public StringBuffer a, p, s;

		public p_string(StringBuffer nm, StringBuffer pp, StringBuffer ss) {
			set(nm, pp, ss);
		}

		public void set(StringBuffer nm, StringBuffer pp, StringBuffer ss) {
			a = new StringBuffer();
			a.append(nm);
			p = new StringBuffer();
			p.append(pp);
			s = new StringBuffer();
			s.append(ss);
		}

	}

	public static class p_hbdate {
		public double h = 0;
		public Date t = null;

		public p_hbdate(double hb, Date dt) {
			set(hb, dt);
		}

		public void set(double hb, Date dt) {
			h = hb;
			if (dt == null)
				dt = new Date(System.currentTimeMillis());
			t = dt;
		}

	}

	public static class p_point {

		public double w = 0, j = 0;
		public int x = 0, y = 0;
		int x20 = 0;
		int y20 = 0;
		public boolean v = false;

		public p_point(double wd, double jd) {
			set(wd, jd);
		}

		public void set(double wd, double jd) {
			w = wd;
			j = jd;
			x20 = (int) BBKMapMath.GetPixelByLon(j, 20);
			y20 = (int) BBKMapMath.GetPixelByLat(w, 20);
		}

		public void newxyp() {
			// ------------------------------------------------------------------
			// x = (int) (BBKSoft.myMaps.MapWx + x20 / BBKSoft.myMaps.zoompow -
			// BBKSoft.myMaps.PixJXT);
			// y = (int) (BBKSoft.myMaps.MapHy + y20 / BBKSoft.myMaps.zoompow -
			// BBKSoft.myMaps.PixWYT);
			// v = (x > 0 && y > 0 && x < BBKSoft.myMaps.MapW && y <
			// BBKSoft.myMaps.MapH);
			// ------------------------------------------------------------------
			x = BBKMap.GetPointXby20(x20);
			y = BBKMap.GetPointYby20(y20);
			v = BBKMap.GetPointV(x, y);
			// ------------------------------------------------------------------
		}
	}

	// =============================================================================
	public static double LineMeasure(line_type line) {
		// ------------------------------------------------------------------
		if (line.p.size() < 2)
			return 0;
		// ------------------------------------------------------------------
		double D = 0;
		for (int i = 0; i < line.p.size() - 1; i++) {
			D += BBKMapMath.GetDistance(line.p.get(i).w, line.p.get(i).j, line.p.get(i + 1).w, line.p.get(i + 1).j);
		}
		D = BBKSYS.getDouble(D, 3);
		return D;
		// ------------------------------------------------------------------
	}

	// =============================================================================

}
