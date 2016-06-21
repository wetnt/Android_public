package bbk.map.data.shapefile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import bbk.bbk.box.BBKSoft;
import bbk.map.abc.BBKMap.MapPoiWJ;
import bbk.map.data.shapefile.BBKShape.ESRI_Lay;
import bbk.map.data.shapefile.BBKShape.ESRI_POINT;
import bbk.map.data.shapefile.BBKShape.ESRI_POINTZ;
import bbk.map.data.shapefile.BBKShape.ESRI_POLYGON;
import bbk.map.data.shapefile.BBKShape.ESRI_POLYLINE;
import bbk.map.lay.BBKMapLayShow;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.map.lay.BBKMapLay.poi_type;
import bbk.zzz.debug.BBKDebug;

public class BBKShapeToBBKLay {

	// ============================================================================
	public static Lay_type BBKShapeFileLoad(String fpn, boolean centerkey) {
		// -----------------------------------------------------------
		Lay_type lay = new Lay_type();
		// -----------------------------------------------------------
		String shxfilename = fpn + ".shx", shpfilename = fpn + ".shp";
		// -----------------------------------------------------------
		InputStream shp = null, shx = null;
		// -----------------------------------------------------------
		try {
			shx = new FileInputStream(shxfilename);
			shp = new FileInputStream(shpfilename);
		} catch (IOException e) {
			BBKDebug.d(fpn + " = File Error!", false, false);
			return lay;
		}
		// -----------------------------------------------------------
		lay = ShapefileToBBKLay(shx, shp);
		// -----------------------------------------------------------
		if (!centerkey)
			return lay;
		// -----------------------------------------------------------
		MapPoiWJ r = BBKMapLayShow.GetLayCenter(lay);
		BBKSoft.myMaps.MapCenterSet(r.w, r.j);
		BBKSoft.MapFlash(true);
		// -----------------------------------------------------------
		return lay;
		// -----------------------------------------------------------
	}

	// ============================================================================
	public static Lay_type ShapefileToBBKLay(InputStream shx, InputStream shp) {
		// -----------------------------------------------------------------------------
		Lay_type lay = new Lay_type();
		ESRI_Lay elay = BBKShape.ShapefileToESRI_Lay(shx, shp);
		GeomtryPoisToLay(elay, lay.pois);
		GeomtryPoizToLay(elay, lay.pois);
		GeomtryLineToLay(elay, lay.line);
		GeomtryPolyToLay(elay, lay.poly);
		// -----------------------------------------------------------------------------
		return lay;
		// -----------------------------------------------------------------------------
	}

	// ===================================================================================
	public static void GeomtryPoisToLay(ESRI_Lay e, List<poi_type> pois) {
		// --------------------------------------------------------
		poi_type poi;
		// --------------------------------------------------------
		int m = e.GeomtryPois.size();
		for (int i = 0; i < m; i++) {
			// --------------------------------------------------------
			ESRI_POINT pt = e.GeomtryPois.get(i);
			StringBuffer a = new StringBuffer(i + "");
			StringBuffer p = new StringBuffer("0");
			StringBuffer s = new StringBuffer(pt.OID + "");
			poi = new poi_type(a, p, s, pt.y, pt.x, 0, null);
			pois.add(poi);
			// --------------------------------------------------------
		}
		// --------------------------------------------------------
	}

	public static void GeomtryPoizToLay(ESRI_Lay e, List<poi_type> pois) {
		// --------------------------------------------------------
		poi_type poi;
		// --------------------------------------------------------
		int m = e.GeomtryPoiz.size();
		for (int i = 0; i < m; i++) {
			// --------------------------------------------------------
			ESRI_POINTZ pt = e.GeomtryPoiz.get(i);
			StringBuffer a = new StringBuffer(i + "");
			StringBuffer p = new StringBuffer("0");
			StringBuffer s = new StringBuffer(pt.OID + "," + pt.z + "," + pt.m);
			poi = new poi_type(a, p, s, pt.y, pt.x, 0, null);
			pois.add(poi);
			// --------------------------------------------------------
		}
		// --------------------------------------------------------
	}

	public static void GeomtryLineToLay(ESRI_Lay e, List<line_type> lines) {
		// --------------------------------------------------------
		int m = e.GeomtryLine.size();
		// --------------------------------------------------------
		for (int i = 0; i < m; i++) {
			// --------------------------------------------------------
			ESRI_POLYLINE pt = e.GeomtryLine.get(i);
			// --------------------------------------------------------
			lines.add(new line_type());
			// --------------------------------------------------------
			int n = pt.XPOINTS.length;
			for (int j = 0; j < n; j++) {
				lines.get(i).add(pt.YPOINTS[j], pt.XPOINTS[j]);
			}
			// --------------------------------------------------------
		}
		// --------------------------------------------------------
	}

	public static void GeomtryPolyToLay(ESRI_Lay e, List<line_type> polys) {
		// --------------------------------------------------------
		int m = e.GeomtryPoly.size();
		// --------------------------------------------------------
		for (int i = 0; i < m; i++) {
			// --------------------------------------------------------
			ESRI_POLYGON pt = e.GeomtryPoly.get(i);
			polys.add(new line_type());
			// --------------------------------------------------------
			int n = pt.XPOINTS.length;
			for (int j = 0; j < n; j++) {
				polys.get(i).add(pt.YPOINTS[j], pt.XPOINTS[j]);
			}
			// --------------------------------------------------------
		}
		// --------------------------------------------------------
	}
	// ===================================================================================

}
