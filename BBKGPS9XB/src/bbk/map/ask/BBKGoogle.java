package bbk.map.ask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import bbk.bbk.box.BBKSoft;
import bbk.map.dat.BBKReg;
import bbk.map.dat.BBKReg.RegWJ;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.map.lay.BBKMapLay.poi_type;
import bbk.net.abc.BBKHttpGet;
import bbk.zzz.debug.BBKDebug;

public class BBKGoogle {

	// =========================================================================================
	// https://developers.google.com/maps/documentation/directions/ //路线请求
	// =========================================================================================
	private String originStr = "", destinationStr = "";

	// =========================================================================================
	public void NaviRunThead(final String origin, final String destination, final int md, final double cw, final double cj) {
		// -------------------------------------
		originStr = origin;
		destinationStr = destination;
		// -------------------------------------
		new Thread() {
			public void run() {
				// -----------------------------
				NaviRun(origin, destination, md, cw, cj);
				// -----------------------------
			}
		}.start();
		// -------------------------------------

	}

	// =========================================================================================
	// --------------------------------------------------------------------------------------------------
	// http://maps.googleapis.com/maps/api/geocode/xml?address=%E8%8D%A3%E4%B8%8A%E5%B1%85&sensor=false
	// http://maps.googleapis.com/maps/api/geocode/json?address=保定华电二区&sensor=false
	// --------------------------------------------------------------------------------------------------
	// http://maps.google.com/maps/api/directions/json?origin=荣上居&destination=锦秋国际大厦&sensor=false&hl=zh-CN&mode=walking
	// --------------------------------------------------------------------------------------------------
	private final String gbCode = "UTF-8";
	private String GgUrl = "http://maps.google.com/maps/api/directions/json?";// directions/xml?";
	private String[] AskMode = { "driving", "walking", "bicycling", "transit" };

	// driving（默认），用于表示使用道路网络的标准行车路线。
	// walking，用于请求经过步行街和人行道（如果有的话）的步行路线。
	// bicycling，用于请求经过骑行道和优先街道（如果有的话）的骑行路线。
	// transit，用于请求经过公交路线（如果有的话）的路线。

	// =======================================================================================================================
	private String setNaviUrl(String origin, String destination, int md, double cw, double cj) {
		// ------------------------------------------------------------------------------
		long cursecond = System.currentTimeMillis() / 1000;
		// ------------------------------------------------------------------------------
		String myGps = "&g=" + cw + "," + cj;
		String NaviWeb = "";
		NaviWeb += GgUrl;
		NaviWeb += "&origin=" + origin;
		NaviWeb += "&destination=" + destination;
		NaviWeb += "&sensor=false&hl=zh-CN";
		NaviWeb += "&mode=" + AskMode[md];
		NaviWeb += "&departure_time=" + cursecond;// "1343605500";
		NaviWeb += myGps;
		// --------------------------------------------------
		return NaviWeb;
		// --------------------------------------------------
	}

	// =========================================================================================

	private void NaviRun(String a, String b, int md, double cw, double cj) {
		// ------------------------------------------------------
		// 接口为火星坐标，查询时需要把真实坐标改为火星坐标
		a = BBKReg.Str_WJ_TF(a);
		b = BBKReg.Str_WJ_TF(b);
		// ------------------------------------------------------
		try {
			a = URLEncoder.encode(a, "UTF-8");
			b = URLEncoder.encode(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// ------------------------------------------------------
		String NaviWeb = setNaviUrl(a, b, md, cw, cj);
		BBKDebug.d(NaviWeb, false, true);
		final String myjson = BBKHttpGet.BBKHttpGetUrl(NaviWeb, gbCode, false);
		// ------------------------------------------------------
		ArrayList<HashMap<String, Object>> tab = JsonToHashMap(myjson);
		// ------------------------------------------------------
		if (tab != null) {
			// ------------------------------------------------------
			BBKSoft.myLays.layask = JsonToMapLay(tab);
			String pathname = BBKSoft.PathAsks + originStr + "_" + destinationStr;
			BBKSoft.myLays.LaySave(BBKSoft.myLays.layask, pathname);
			// ------------------------------------------------------
			handlerNav.post(RunnableNav);
			// ------------------------------------------------------
		}
		// ------------------------------------------------------
	}

	private Handler handlerNav = new Handler();
	private Runnable RunnableNav = new Runnable() {
		public void run() {
			// ------------------------------------------------------
			String name = originStr + "_" + destinationStr;
			BBKSoft.myAsk.AskInfoListShowRun("导航: " + name);
			// ------------------------------------------------------

		}
	};

	// ========================================================================
	private Lay_type JsonToMapLay(ArrayList<HashMap<String, Object>> tab) {
		// ------------------------------------------------------------------
		Lay_type lay = new Lay_type();
		double w = 0, j = 0;
		StringBuffer nn, ps, sz;
		ps = new StringBuffer("");
		HashMap<String, Object> map;
		// ------------------------------------------------------------------
		for (int t = 0; t < tab.size(); t++) {
			// ------------------------------------------------------------------
			map = tab.get(t);
			nn = new StringBuffer(map.get("NAME") + " " + map.get("Distance"));
			sz = new StringBuffer(map.get("Distance").toString() + " / " + map.get("ADD").toString());
			w = (Double) map.get("LAT");
			j = (Double) map.get("LON");
			// ------------------------------------------------------------------
			lay.pois.add(new poi_type(nn, ps, sz, w, j, 0, null));
			lay.line.add(StepToLine(map.get("Points").toString(), true));
			// ------------------------------------------------------------------
		}
		// ------------------------------------------------------------------
		nn = ps = sz = null;
		map = null;
		// ------------------------------------------------------------------
		return lay;
		// ------------------------------------------------------------------

	}

	// ========================================================================
	public String[] tabitems = { "ID", "NAME", "Mode", "Distance", "ADD", "LAT", "LON", "HTML", "Points" };

	@SuppressWarnings("unused")
	private ArrayList<HashMap<String, Object>> JsonToHashMap(String dataJson) {
		// ----------------------------------------------
		if (dataJson == null || dataJson.length() < 10)
			return null;
		// ----------------------------------------------
		try {
			// ----------------------------------------------
			JSONObject jsonObj = new JSONObject(dataJson);
			String status = jsonObj.getString("status");
			if (status.indexOf("OK") == -1)
				return null;
			// ----------------------------------------------
			JSONArray routes = jsonObj.getJSONArray("routes");
			// ----------------------------------------------
			JSONObject route, bounds, overview_polyline, leg;
			List<GeoPoint> overview_poi;
			JSONArray legs, steps;
			TxtVal distance, duration;
			String start_address, endpt_address;
			String departure_time, arrivaltm_time;
			GeoPoint start_pointss, endpt_pointss;
			step s;
			RegWJ r;
			String sname;
			double dis = 0;
			// ----------------------------------------------
			ArrayList<HashMap<String, Object>> tab = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map;
			// ----------------------------------------------
			for (int rti = 0; rti < routes.length(); rti++) {
				route = routes.getJSONObject(rti);
				// ----------------------------------------------
				bounds = route.getJSONObject("bounds"); // BBKDebug.ddd(bounds.toString());
				// ----------------------------------------------
				overview_polyline = route.getJSONObject("overview_polyline");
				overview_poi = decodePoly(overview_polyline.getString("points"));
				// ----------------------------------------------
				// BBKDebug.ddd(overview_polyline.toString());
				// BBKDebug.ddd(points);
				// ----------------------------------------------
				// for (int pi = 0; pi < overview_poi.size(); pi++) {
				// mypoi.get(pi).toString();
				// mypoi.get(pi).lat
				// BBKDebug.ddd(mypoi.get(pi).lat + "");
				// }
				// ----------------------------------------------
				legs = route.getJSONArray("legs");
				for (int lgi = 0; lgi < legs.length(); lgi++) {
					// ----------------------------------------------
					leg = legs.getJSONObject(lgi);
					// ----------------------------------------------
					distance = jsoTxtVal(leg, "distance");
					duration = jsoTxtVal(leg, "duration");
					// ----------------------------------------------
					departure_time = jsoString(leg, "departure_time");
					arrivaltm_time = jsoString(leg, "arrival_time");
					// ----------------------------------------------
					start_address = jsoString(leg, "start_address");
					start_pointss = jsoGeoPoint(leg, "start_location");
					endpt_address = jsoString(leg, "end_address");
					endpt_pointss = jsoGeoPoint(leg, "end_location");
					// ----------------------------------------------
					if (true) {
						// ----------------------------------------------
						map = new HashMap<String, Object>();
						// ----------------------------------------------
						sname = distance.txt + " - " + duration.txt;
						// ----------------------------------------------
						r = BBKReg.WJ_FtoT(start_pointss.lat, start_pointss.lng);
						start_pointss.lat = r.w;
						start_pointss.lng = r.j;
						// ----------------------------------------------
						map.put(tabitems[0], 0);
						map.put(tabitems[1], sname);
						map.put(tabitems[2], "All");
						map.put(tabitems[3], distance.txt);
						map.put(tabitems[4], duration.txt);
						map.put(tabitems[5], start_pointss.lat);
						map.put(tabitems[6], start_pointss.lng);
						map.put(tabitems[7], start_address);
						map.put(tabitems[8], "");
						// ----------------------------------------------
						tab.add(map);
						// ----------------------------------------------
					}
					// ----------------------------------------------
					steps = leg.getJSONArray("steps");
					for (int sti = 0; sti < steps.length(); sti++) {
						// ----------------------------------------------
						s = jsoStep(steps.getJSONObject(sti));
						// ----------------------------------------------
						s.html_instructions = splitAndFilterString(s.html_instructions, 400);
						sname = splitAndFilterString(s.html_instructions, 200);
						sname = EnglishToChinese(sname);
						// ----------------------------------------------
						r = BBKReg.WJ_FtoT(s.start_location.lat, s.start_location.lng);
						s.start_location.lat = r.w;
						s.start_location.lng = r.j;
						// ----------------------------------------------
						map = new HashMap<String, Object>();
						dis = s.distance.val / 1000;
						// ----------------------------------------------
						map.put(tabitems[0], sti);
						map.put(tabitems[1], sname);
						map.put(tabitems[2], s.travel_mode);
						map.put(tabitems[3], dis + "km");
						map.put(tabitems[4], s.duration.txt);
						map.put(tabitems[5], s.start_location.lat);
						map.put(tabitems[6], s.start_location.lng);
						map.put(tabitems[7], s.html_instructions);
						map.put(tabitems[8], s.points);
						// ----------------------------------------------
						tab.add(map);
						// ----------------------------------------------
					}
					// ----------------------------------------------
					// ----------------------------------------------
				}
				// ----------------------------------------------
				jsonObj = null;
				status = null;
				routes = legs = steps = null;
				route = bounds = overview_polyline = leg = null;
				overview_poi = null;
				distance = duration = null;
				start_address = endpt_address = sname = null;
				start_pointss = endpt_pointss = null;
				s = null;
				r = null;
				map = null;
				// ----------------------------------------------
				return tab;
				// ----------------------------------------------
			}
			// ------------------------------------------------------------------
		} catch (JSONException ex) {
			BBKDebug.d("BBKGoogle.JsonToHashMap = " + ex.toString(), true, false);
		}
		// ------------------------------------------------------------------
		return null;
		// ------------------------------------------------------------------
	}

	// =====================================================================================
	// =====================================================================================
	// =====================================================================================
	// =====================================================================================
	private final String[] NaviStringItem = {
			// ---------------------------------------------------------------------------------
			"destination", "-目的地",
			// ---------------------------------------------------------------------------------
			"u-turn", "掉头", "fork", "叉路", "roundabout", "环岛", "ramp", "匝道", "exit", "出主路", "toll road", "收费公路", "sharp", "急转",
			// ---------------------------------------------------------------------------------
			"east", "东", "west", "西", "south", "南", "north", "北", "right", "右", "left", "左",
			// ---------------------------------------------------------------------------------
			"toward", "向", "to", "-", "turn", "转", "head", "向", "stay", "留", "keep", "直行", "slight", "靠", "continue", "直行", "merge", "进入",
			// ---------------------------------------------------------------------------------
			"partial", "部分", "road", "路", "make", "", "take", "", "onto", "进入", "will be", "", "the", "", "at", "在", "on", "", "and", "", "a ", "",
			// ---------------------------------------------------------------------------------
			" ", ""
	// ---------------------------------------------------------------------------------
	};

	private String EnglishToChinese(String name) {
		// --------------------------------------------------
		name = name.toLowerCase(Locale.US);
		// --------------------------------------------------
		int n = NaviStringItem.length - 1;
		for (int i = 0; i < n; i = i + 2) {
			name = name.replace(NaviStringItem[i], NaviStringItem[i + 1]);
		}
		// --------------------------------------------------
		return name;
		// --------------------------------------------------
	}

	// =====================================================================================
	// =====================================================================================
	// =====================================================================================
	// =====================================================================================
	private line_type StepToLine(String sp, boolean Reg) {
		// ----------------------------------------------
		line_type line = new line_type();
		List<GeoPoint> p = decodePoly(sp);
		// ----------------------------------------------
		double w = 0, j = 0;
		RegWJ r;
		// ----------------------------------------------
		for (int i = 0; i < p.size(); i++) {
			w = p.get(i).lat;
			j = p.get(i).lng;
			if (Reg) {
				r = BBKReg.WJ_FtoT(w, j);
				w = r.w;
				j = r.j;
			}
			line.add(w, j);
		}
		// ----------------------------------------------
		p = null;
		r = null;
		// ----------------------------------------------
		return line;
		// ----------------------------------------------
	}

	private step jsoStep(JSONObject lg) {
		// ----------------------------------------------
		step s = new step();
		// ----------------------------------------------
		s.distance = jsoTxtVal(lg, "distance");
		s.duration = jsoTxtVal(lg, "duration");
		s.start_location = jsoGeoPoint(lg, "start_location");
		s.end_location = jsoGeoPoint(lg, "end_location");
		s.html_instructions = jsoString(lg, "html_instructions");
		s.travel_mode = jsoString(lg, "travel_mode");
		s.points = jsoPoints(lg, "polyline", "points");
		// ----------------------------------------------
		return s;
		// ----------------------------------------------
	}

	private String jsoString(JSONObject lg, String key) {
		// ----------------------------------------------
		String str = "";
		try {
			str = lg.getString(key);
		} catch (JSONException e) {
		}
		return str;
		// ----------------------------------------------
	}

	private String jsoPoints(JSONObject lg, String polyline, String points) {
		// ----------------------------------------------
		String pois = "";
		// ----------------------------------------------
		try {
			JSONObject ps = lg.getJSONObject(polyline);
			pois = jsoString(ps, points);
		} catch (JSONException e) {
		}
		return pois;
		// ----------------------------------------------
	}

	private GeoPoint jsoGeoPoint(JSONObject lg, String key) {
		// ----------------------------------------------
		// ----------------------------------------------
		String lat = "", lng = "";
		JSONObject lc;
		try {
			lc = lg.getJSONObject(key);
			lat = lc.getString("lat");
			lng = lc.getString("lng");
		} catch (JSONException e) {
		}
		double w = Double.parseDouble(lat);
		double j = Double.parseDouble(lng);
		return new GeoPoint(w, j);
		// ----------------------------------------------
	}

	private TxtVal jsoTxtVal(JSONObject lg, String key) {
		// ----------------------------------------------
		String text = "";
		double value = 0;
		JSONObject tv;
		try {
			tv = lg.getJSONObject(key);
			text = tv.getString("text");
			value = tv.getDouble("value");
		} catch (JSONException e) {
		}
		return new TxtVal(text, value);
		// ----------------------------------------------
	}

	// =====================================================================================
	// =====================================================================================
	@SuppressWarnings("unused")
	private class step {
		public TxtVal distance, duration;
		public GeoPoint start_location, end_location;
		public String html_instructions, travel_mode, points;

		step() {
			distance = new TxtVal("", 0);
			duration = new TxtVal("", 0);
			start_location = new GeoPoint(0, 0);
			end_location = new GeoPoint(0, 0);
			html_instructions = "";
			travel_mode = "";
			points = "";
		}
	}

	private class TxtVal {
		String txt = "";
		double val = 0;

		TxtVal(String _txt, double _val) {
			txt = _txt;
			val = _val;
		}
	}

	// =====================================================================================
	/**
	 * 解析返回XML中overview_polyline的路线编码
	 * 
	 * @param encoded
	 * @return
	 */
	private List<GeoPoint> decodePoly(String encoded) {// 解析返回xml中overview_polyline的路线编码

		List<GeoPoint> poly = new ArrayList<GeoPoint>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			// GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) *
			// 1E6),(int) (((double) lng / 1E5) * 1E6));
			GeoPoint p = new GeoPoint((double) lat / 1E5, (double) lng / 1E5);
			poly.add(p);
		}

		return poly;
	}

	// =====================================================================================
	private class GeoPoint {
		double lat;
		double lng;

		GeoPoint(double latx, double lngx) {
			lat = latx;
			lng = lngx;
		}

		// GeoPoint point2 = new GeoPoint(
		// (int) (location2.getLatitude() * 1E6),
		// (int) (location2.getLongitude() * 1E6));
	}

	// =====================================================================================

	/**
	 * 删除input字符串中的html格式
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	private String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}

	// =====================================================================================

}
