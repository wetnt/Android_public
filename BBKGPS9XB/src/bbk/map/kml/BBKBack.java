package bbk.map.kml;

import java.util.Collection;
import java.util.Iterator;

import android.util.Log;
import bbk.zzz.debug.BBKDebug;

public class BBKBack {

	// public void drawPath(NavigationDataSet navSet, int color, MapView
	// mMapView01) {
	//
	// BBKDebug.ddd("map color before: " + color);
	//
	// // color correction for dining, make it darker
	// if (color == Color.parseColor("#add331"))
	// color = Color.parseColor("#6C8715");
	// BBKDebug.ddd("map color after: " + color);
	//
	// Collection overlaysToAddAgain = new ArrayList();
	// for (Iterator iter = mMapView01.getOverlays().iterator();
	// iter.hasNext();) {
	// Object o = iter.next();
	// BBKDebug.ddd("overlay type: " + o.getClass().getName());
	// if (!RouteOverlay.class.getName().equals(o.getClass().getName())) {
	// // mMapView01.getOverlays().remove(o);
	// overlaysToAddAgain.add(o);
	// }
	// }
	// mMapView01.getOverlays().clear();
	// mMapView01.getOverlays().addAll(overlaysToAddAgain);
	//
	// String path = navSet.getRoutePlacemark().getCoordinates();
	// BBKDebug.ddd("path=" + path);
	// if (path != null && path.trim().length() > 0) {
	// String[] pairs = path.trim().split(" ");
	//
	// BBKDebug.ddd("pairs.length=" + pairs.length);
	//
	// String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude
	// // lngLat[1]=latitude
	// // lngLat[2]=height
	//
	// BBKDebug.ddd("lnglat =" + lngLat + ", length: " + lngLat.length);
	//
	// if (lngLat.length < 3)
	// lngLat = pairs[1].split(","); // if first pair is not
	// // transferred completely, take
	// // seconds pair //TODO
	//
	// try {
	// GeoPoint startGP = new GeoPoint((int) (Double.parseDouble(lngLat[1]) *
	// 1E6), (int) (Double.parseDouble(lngLat[0]) * 1E6));
	// mMapView01.getOverlays().add(new RouteOverlay(startGP, startGP, 1));
	// GeoPoint gp1;
	// GeoPoint gp2 = startGP;
	//
	// for (int i = 1; i < pairs.length; i++) // the last one would be
	// // crash
	// {
	// lngLat = pairs[i].split(",");
	//
	// gp1 = gp2;
	//
	// if (lngLat.length >= 2 && gp1.getLatitudeE6() > 0 && gp1.getLongitudeE6()
	// > 0 && gp2.getLatitudeE6() > 0 && gp2.getLongitudeE6() > 0) {
	//
	// // for GeoPoint, first:latitude, second:longitude
	// gp2 = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6), (int)
	// (Double.parseDouble(lngLat[0]) * 1E6));
	//
	// if (gp2.getLatitudeE6() != 22200000) {
	// mMapView01.getOverlays().add(new RouteOverlay(gp1, gp2, 2, color));
	// Log.d(myapp.APP, "draw:" + gp1.getLatitudeE6() + "/" +
	// gp1.getLongitudeE6() + " TO " + gp2.getLatitudeE6() + "/" +
	// gp2.getLongitudeE6());
	// }
	// }
	// // Log.d(myapp.APP,"pair:" + pairs[i]);
	// }
	// // routeOverlays.add(new RouteOverlay(gp2,gp2, 3));
	// mMapView01.getOverlays().add(new RouteOverlay(gp2, gp2, 3));
	// } catch (NumberFormatException e) {
	// BBKDebug.ddd("Cannot draw route.", e);
	// }
	// }
	// // mMapView01.getOverlays().addAll(routeOverlays); // use the default
	// // color
	// mMapView01.setEnabled(true);
	// }

}