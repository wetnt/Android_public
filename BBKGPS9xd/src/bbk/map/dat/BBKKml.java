package bbk.map.dat;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.util.Log;
import bbk.map.data.kml.NavigationDataSet;
import bbk.map.data.kml.NavigationSaxHandler;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.sys.abc.BBKSYS;

public class BBKKml {

	public void adfsaf() {

		String PathName = "D:/!项目/512/实际测试数据/333/689.kml";
		String strDoc = BBKSYS.FileRead(PathName);

		NavigationDataSet navigationDataSet;
		try {
			NavigationSaxHandler navSax2Handler = new NavigationSaxHandler();
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(navSax2Handler);
			xr.parse(strDoc);

			navigationDataSet = null;
			navigationDataSet = navSax2Handler.getParsedData();

			Log.d("TAB", "navigationDataSet: " + navigationDataSet.toString());

		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}

	}

	public static NavigationDataSet getNavigationDataSet(String str) {
		NavigationDataSet navigationDataSet = null;
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			NavigationSaxHandler navSax2Handler = new NavigationSaxHandler();
			xr.setContentHandler(navSax2Handler);
			xr.parse(str);
			navigationDataSet = navSax2Handler.getParsedData();
		} catch (Exception e) {
			// Log.e(myapp.APP, "error with kml xml", e);
			navigationDataSet = null;
		}
		return navigationDataSet;
	}

	// =====================================================================================
	@SuppressWarnings("unused")
	public boolean DocumentToLays(Document doc, Lay_type lay) {
		try {
			// ----------------------------------------------------------------------------------
			Element Document, Folder, Placemark, Point, coordinates;
			String RootName, p = "", lats = "", lngs = "", htmls = "";
			// double wd = 0, jd = 0;
			// MapPoiWJ wj = new MapPoiWJ(0, 0);
			// ----------------------------------------------------------------------------------
			// Log.d("TAB", "==========" + doc.toString());
			// Node Documentx = doc.getElementsByTagName("Document").item(0);
			Element eRoot = doc.getDocumentElement();
			RootName = eRoot.getAttribute("name");
			Log.d("TAB", "==========" + RootName);

			// route = (Element) doc.getElementsByTagName("route").item(0);
			// legs = route.getElementsByTagName("step");
			// //
			// ----------------------------------------------------------------------------------
			// int legsLength = legs.getLength();
			// for (int i = 0; i < legsLength; i++) {
			// //
			// ------------------------------------------------------------------------------
			// steps = (Element) legs.item(i);
			// polyline = ElementToElement(steps, "polyline");
			// points = ElementToElement(polyline, "points");
			// p = points.getTextContent();
			// PointsToLine(p, lay);
			// //
			// ------------------------------------------------------------------------------
			// start = ElementToElement(steps, "start_location");
			// lat = ElementToElement(start, "lat");
			// lng = ElementToElement(start, "lng");
			// lats = lat.getTextContent();
			// lngs = lng.getTextContent();
			// wd = Double.parseDouble(lats);
			// jd = Double.parseDouble(lngs);
			// //
			// ------------------------------------------------------------------------------
			// wj = BBKReg.WJ_FtoT(wd, jd);
			// wd = wj.w;
			// jd = wj.j;
			// //
			// ------------------------------------------------------------------------------
			// html = ElementToElement(steps, "html_instructions");
			// htmls = html.getTextContent();
			// htmls = splitAndFilterString(htmls, htmls.length());
			// //
			// ------------------------------------------------------------------------------
			// lay.pois.add(htmls, "0", htmls, wd, jd, 0, null);
			// //
			// ------------------------------------------------------------------------------
			// }
			// ----------------------------------------------------------------------------------
			return true;
			// --------------------------------------------------
		} catch (DOMException e) {
			return false;
		}
		// --------------------------------------------------
	}

	public Element ElementToElement(Element element, String str) {
		// ----------------------------------------------------------------------------------
		return (Element) element.getElementsByTagName(str).item(0);
		// ----------------------------------------------------------------------------------
	}

	// =====================================================================================
	// public boolean DocumentToPois(Document doc, Lay_type lay) {
	// try {
	// //
	// ----------------------------------------------------------------------------------
	// Element route, steps, html_instructions, start_location, lat, lng;
	// NodeList legs;
	// Date dt = new Date(System.currentTimeMillis());
	// double w, j, h = 0;
	// String s = "";
	// RegWJ wj = new RegWJ(0, 0);
	// //
	// ----------------------------------------------------------------------------------
	// route = (Element) doc.getElementsByTagName("route").item(0);
	// legs = route.getElementsByTagName("step");
	// //
	// ----------------------------------------------------------------------------------
	// int legsLength = legs.getLength();
	// for (int i = 0; i < legsLength; i++) {
	// //
	// ----------------------------------------------------------------------------------
	// steps = (Element) legs.item(i);
	// html_instructions = (Element)
	// steps.getElementsByTagName("html_instructions").item(0);
	// start_location = (Element)
	// steps.getElementsByTagName("start_location").item(0);
	// lat = (Element) start_location.getElementsByTagName("lat").item(0);
	// lng = (Element) start_location.getElementsByTagName("lng").item(0);
	// //
	// ----------------------------------------------------------------------------------
	// w = Double.parseDouble(lat.getTextContent());
	// j = Double.parseDouble(lng.getTextContent());
	// s = i + "." + html_instructions.getTextContent();
	// //
	// ----------------------------------------------------------------------------------
	// wj = BBK.myRegs.F2T_AV(w, j);
	// w = wj.w;
	// j = wj.j;
	// //
	// ----------------------------------------------------------------------------------
	// lay.pois.add(s, "0", s, w, j, h, dt);
	// //
	// ----------------------------------------------------------------------------------
	// }
	// //
	// --------------------------------------------------------------------------------------
	// // Log.e("legs=", legs.getLength() + "," );
	// return true;
	// //
	// --------------------------------------------------------------------------------------
	// } catch (DOMException e) {
	// return false;
	// }
	// //
	// ------------------------------------------------------------------------------------------
	// }
	//
	// //
	// =====================================================================================
	// public void PointsToLine(String strPoints, BBKMapLay.Lay_type lay) {
	// // --------------------------------------------------
	// GeoPoint p;
	// double w, j;
	// RegWJ wj = new RegWJ(0, 0);
	// // --------------------------------------------------
	// List<GeoPoint> l = decodePoly(strPoints);
	// // --------------------------------------------------
	// int lsize = l.size();
	// for (int i = 0; i < lsize; i++) {
	// // -----------------------------------------
	// p = l.get(i);
	// // -----------------------------------------
	// w = p.lat / 1E6;
	// j = p.lng / 1E6;
	// // -----------------------------------------
	// wj = BBKReg.WJ_FtoT(w, j);
	// w = wj.w;
	// j = wj.j;
	// // -----------------------------------------
	// lay.line.add(w, j, 0, null);
	// // -----------------------------------------
	// }
	// // --------------------------------------------------
	// }

	// =====================================================================================
	public class GeoPoint {
		double lat;
		double lng;

		GeoPoint(int latx, int lngx) {
			lat = latx;
			lng = lngx;
		}
	}
	// =====================================================================================

}