package bbk.map.kml;

import java.awt.Color;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

import bbk.bbk.main.BBK;
import bbk.map.main.BBKMap;

public class BBKKmlx {
	
	
	public void drawPath(NavigationDataSet navSet, int color, MapView mMapView01) {

	    Log.d(myapp.APP, "map color before: " + color);        

	    // color correction for dining, make it darker
	    if (color == Color.parseColor("#add331")) color = Color.parseColor("#6C8715");
	    Log.d(myapp.APP, "map color after: " + color);

	    Collection overlaysToAddAgain = new ArrayList();
	    for (Iterator iter = mMapView01.getOverlays().iterator(); iter.hasNext();) {
	        Object o = iter.next();
	        Log.d(myapp.APP, "overlay type: " + o.getClass().getName());
	        if (!RouteOverlay.class.getName().equals(o.getClass().getName())) {
	            // mMapView01.getOverlays().remove(o);
	            overlaysToAddAgain.add(o);
	        }
	    }
	    mMapView01.getOverlays().clear();
	    mMapView01.getOverlays().addAll(overlaysToAddAgain);

	    String path = navSet.getRoutePlacemark().getCoordinates();
	    Log.d(myapp.APP, "path=" + path);
	    if (path != null && path.trim().length() > 0) {
	        String[] pairs = path.trim().split(" ");

	        Log.d(myapp.APP, "pairs.length=" + pairs.length);

	        String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height

	        Log.d(myapp.APP, "lnglat =" + lngLat + ", length: " + lngLat.length);

	        if (lngLat.length<3) lngLat = pairs[1].split(","); // if first pair is not transferred completely, take seconds pair //TODO 

	        try {
	            GeoPoint startGP = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6), (int) (Double.parseDouble(lngLat[0]) * 1E6));
	            mMapView01.getOverlays().add(new RouteOverlay(startGP, startGP, 1));
	            GeoPoint gp1;
	            GeoPoint gp2 = startGP;

	            for (int i = 1; i < pairs.length; i++) // the last one would be crash
	            {
	                lngLat = pairs[i].split(",");

	                gp1 = gp2;

	                if (lngLat.length >= 2 && gp1.getLatitudeE6() > 0 && gp1.getLongitudeE6() > 0
	                        && gp2.getLatitudeE6() > 0 && gp2.getLongitudeE6() > 0) {

	                    // for GeoPoint, first:latitude, second:longitude
	                    gp2 = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6), (int) (Double.parseDouble(lngLat[0]) * 1E6));

	                    if (gp2.getLatitudeE6() != 22200000) { 
	                        mMapView01.getOverlays().add(new RouteOverlay(gp1, gp2, 2, color));
	                        Log.d(myapp.APP, "draw:" + gp1.getLatitudeE6() + "/" + gp1.getLongitudeE6() + " TO " + gp2.getLatitudeE6() + "/" + gp2.getLongitudeE6());
	                    }
	                }
	                // Log.d(myapp.APP,"pair:" + pairs[i]);
	            }
	            //routeOverlays.add(new RouteOverlay(gp2,gp2, 3));
	            mMapView01.getOverlays().add(new RouteOverlay(gp2, gp2, 3));
	        } catch (NumberFormatException e) {
	            Log.e(myapp.APP, "Cannot draw route.", e);
	        }
	    }
	    // mMapView01.getOverlays().addAll(routeOverlays); // use the default color
	    mMapView01.setEnabled(true);
	}

	public static void runx() {
		String PathName = "D:/!项目/512/实际测试数据/333/678.kml";
		NavigationDataSet ss;
		String str = null;
		try {
			ss = BBKKmlx.getPerson(PathName);
			str = ss.getPlacemarks().get(0).title;
		} catch (Exception e) {
			e.printStackTrace();
		}
		BBK.ddd(str);
		BBKMap.MapFlash();
	}

	public static NavigationDataSet getPerson(String filestr) throws Exception {

		// NavigationDataSet navigationDataSet = null;
		// SAXParserFactory spf = SAXParserFactory.newInstance();
		// SAXParser sp = spf.newSAXParser();
		// XMLReader xr = sp.getXMLReader();
		// NavigationSaxHandler navSax2Handler = new NavigationSaxHandler();
		// xr.setContentHandler(navSax2Handler);
		// xr.parse(filestr);
		// navigationDataSet = navSax2Handler.getParsedData();
		// return navSax2Handler.getParsedData();

		
		NavigationSaxHandler navSax2Handler = new NavigationSaxHandler();
		navSax2Handler.startDocument();
		
		
		


		return navSax2Handler.getParsedData();

	}

	// public static void main(String[] args) throws Exception {
	// System.out.println("dddddddddddd:" + getPerson().size());
	// for (int i = 0; i < getPerson().size(); i++) {
	// Placemark person = getPerson().get(i);
	// System.out.println("name:" + person.getAddress() + " Address:" +
	// person.getCoordinates());
	// }
	// }
}