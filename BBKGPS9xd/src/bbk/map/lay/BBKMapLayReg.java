package bbk.map.lay;

import bbk.map.dat.BBKReg;
import bbk.map.dat.BBKReg.RegWJ;
import bbk.map.lay.BBKMapLay.Lay_type;

public class BBKMapLayReg {

	// ====================================================================================
	// ====================================================================================
	public static void Lay_Reg_FtoJ(Lay_type lay) {
		// --------------------------------------------------------------------
		double w = 0, j = 0;
		RegWJ r = new RegWJ(0, 0);
		// --------------------------------------------------------------------
		if (lay.pois != null && lay.pois.size() > 1) {
			for (int i = 0; i < lay.pois.size(); i++) {
				w = lay.pois.get(i).p.w;
				j = lay.pois.get(i).p.j;
				r = BBKReg.WJ_FtoT(w, j);
				lay.pois.get(i).p.set(r.w, r.j);
			}
		}
		// --------------------------------------------------------------------
		if (lay.line != null && lay.line.size() > 0) {
			for (int i = 0; i < lay.line.size(); i++) {
				if (lay.line.get(i) != null && lay.line.get(i).p.size() > 1) {
					for (int k = 0; k < lay.line.get(i).p.size(); k++) {
						w = lay.line.get(i).p.get(k).w;
						j = lay.line.get(i).p.get(k).j;
						r = BBKReg.WJ_FtoT(w, j);
						lay.line.get(i).p.get(k).set(r.w, r.j);
					}
				}
			}
		}
		// --------------------------------------------------------------------
		if (lay.poly != null && lay.poly.size() > 0) {
			for (int i = 0; i < lay.poly.size(); i++) {
				if (lay.poly.get(i) != null && lay.poly.get(i).p.size() > 1) {
					for (int k = 0; k < lay.poly.get(i).p.size(); k++) {
						w = lay.poly.get(i).p.get(k).w;
						j = lay.poly.get(i).p.get(k).j;
						r = BBKReg.WJ_FtoT(w, j);
						lay.poly.get(i).p.get(k).set(r.w, r.j);
					}
				}
			}
		}
		// --------------------------------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================

}