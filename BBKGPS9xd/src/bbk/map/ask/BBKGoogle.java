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
import bbk.map.uis.Main_Ask;
import bbk.net.abc.BBKHttpGet;
import bbk.sys.abc.BBKString;
import bbk.zzz.debug.bd;

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
	private String[] AskMode = { "driving", "walking", "bicycling", "transit" };
	private String GgUrl = "http://maps.google.cn/maps/api/directions/json?";// directions/xml?";//maps.google.com

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
		bd.d(NaviWeb, false, true);
		final String myjson = BBKHttpGet.BBKHttpGetUrl(NaviWeb, gbCode, false);
		// ------------------------------------------------------
		// JSON转换为结构化数据
		ArrayList<HashMap<String, Object>> tab = JsonToHashMap(myjson, true);
		if (tab == null)
			return;
		// ------------------------------------------------------
		BBKSoft.myLays.layask = JsonToMapLay(tab);// 结构化数据变图层
		// ------------------------------------------------------
		// Google都改为了经纬度起止点查询，记忆无意义了
		// String pathname = BBKSoft.PathAsks + originStr + "_" +
		// destinationStr;
		// BBKSoft.myLays.LaySave(BBKSoft.myLays.layask, pathname);
		// ------------------------------------------------------
		handlerNav.post(RunnableNav);// 返回结果图层、表单显示
		// ------------------------------------------------------
	}

	private Handler handlerNav = new Handler();
	private Runnable RunnableNav = new Runnable() {
		public void run() {// 返回结果图层、表单显示
			// ------------------------------------------------------
			String name = originStr + "_" + destinationStr;
			BBKSoft.myAsk.AskInfoListShowRun("导航: " + name);
			BBKSoft.myAsk.ListViewBack();
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
			nn = new StringBuffer(map.get("NAME") + "");
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
	private String Yzbm = "邮政编码:";

	@SuppressWarnings("unused")
	private ArrayList<HashMap<String, Object>> JsonToHashMap(String dataJson, boolean reg) {
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
			if (routes == null)
				return null;
			// ----------------------------------------------
			JSONObject routeJSObj, legJSObj;
			JSONArray legsJy, stepsJy;
			ge_leg l;
			ge_stepA s;
			// ----------------------------------------------
			String homeCity = "";
			String sname;
			// ----------------------------------------------
			ArrayList<HashMap<String, Object>> tab = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// ----------------------------------------------
			for (int rti = 0; rti < routes.length(); rti++) {
				// ----------------------------------------------
				routeJSObj = routes.getJSONObject(rti);
				// ----------------------------------------------
				legsJy = routeJSObj.getJSONArray("legs");
				for (int lgi = 0; lgi < legsJy.length(); lgi++) {
					// ----------------------------------------------
					legJSObj = legsJy.getJSONObject(lgi);
					l = jsonToGeLeg(legJSObj, reg);
					sname = l.distance.txt + "/" + l.duration.txt;
					// ----------------------------------------------
					homeCity = BBKString.theSameHead(l.start_address, l.end_address);
					l.start_address = l.start_address.replace(homeCity, "");
					l.start_address = l.start_address.replace(Yzbm, "");
					l.end_address = l.end_address.replace(homeCity, "");
					l.end_address = l.end_address.replace(Yzbm, "");
					// ----------------------------------------------
					Main_Ask.askBackInfoStrAdd(//
					"范围：\t" + homeCity + "\r\n" + //
							"起点：\t" + l.start_address + "\r\n" + //
							"终点：\t" + l.end_address + "\r\n" + //
							"里程：\t" + sname //
					);
					LegsABtoTab(map, tab, 0, l.start_address, "All", l.distance.txt, l.duration.txt, l.start_location.lat, l.start_location.lng, l.start_address, "");
					// ----------------------------------------------
					stepsJy = legJSObj.getJSONArray("steps");
					for (int sti = 0; sti < stepsJy.length(); sti++) {
						// ----------------------------------------------
						s = jsoStepA(stepsJy.getJSONObject(sti), reg);
						// ----------------------------------------------
						s.html_instructions = splitAndFilterString(s.html_instructions, 400);
						s.html_instructions = s.html_instructions.replace(homeCity, "");
						s.html_instructions = s.html_instructions.replace(Yzbm, "");
						// ----------------------------------------------
						sname = splitAndFilterString(s.html_instructions, 50);
						sname = EnglishToChinese(sname);
						// ----------------------------------------------
						sname = Main_Ask_askBackInfoStrAdd_build(s);
						// ----------------------------------------------
						Main_Ask.askBackInfoStrAdd(sname + " " + s.distance.txt + "/" + s.duration.txt);
						// ----------------------------------------------
						LegsABtoTab(//
								map, tab, sti, sname, s.travel_mode, s.distance.val / 1000 + "km", s.duration.txt, //
								s.start_location.lat, s.start_location.lng, s.html_instructions, s.points//
						);
						// ----------------------------------------------
					}
					// ----------------------------------------------
					LegsABtoTab(map, tab, 0, l.end_address, "All", l.distance.txt, l.duration.txt, l.end_location.lat, l.end_location.lng, l.end_address, "");
					// ----------------------------------------------
				}
				// ----------------------------------------------
				jsonObj = null;
				status = null;
				routes = legsJy = stepsJy = null;
				routeJSObj = legJSObj = null;
				s = null;
				map = null;
				sname = null;
				// ----------------------------------------------
				return tab;
				// ----------------------------------------------
			}
			// ------------------------------------------------------------------
		} catch (JSONException ex) {
			bd.d("BBKGoogle.JsonToHashMap = " + ex.toString(), true, false);
		}
		// ------------------------------------------------------------------
		return null;
		// ------------------------------------------------------------------
	}

	private void LegsABtoTab(//
			HashMap<String, Object> map, ArrayList<HashMap<String, Object>> tab,//
			int id, String name, String type, String dis, String add, double w, double j, String h, String p) {
		// ----------------------------------------------
		map = new HashMap<String, Object>();
		// ----------------------------------------------
		map.put(tabitems[0], id);// ID
		map.put(tabitems[1], name);// NAME
		map.put(tabitems[2], type);// Mode
		map.put(tabitems[3], dis);// Distance
		map.put(tabitems[4], add);// ADD
		map.put(tabitems[5], w);// LAT
		map.put(tabitems[6], j);// LON
		map.put(tabitems[7], h);// HTML
		map.put(tabitems[8], p);// Points
		// ----------------------------------------------
		tab.add(map);
		// ----------------------------------------------
	}

	private String Main_Ask_askBackInfoStrAdd_build(ge_stepA s) {
		// ----------------------------------------------
		String str = "";
		// ----------------------------------------------
		if (s == null)
			return str;
		if (s.transit_details == null)
			return str;
		// ----------------------------------------------
		if (s.transit_details.num_stops > 0) {
			str = s.html_instructions + "[";
			str += s.transit_details.departure_stop.name + "->";
			str += s.transit_details.line.short_name + "(";
			str += s.transit_details.num_stops + "站)->";
			str += s.transit_details.arrival_stop.name + "]";
		} else {
			if (s.html_instructions == null)
				return str;
			str = s.html_instructions;
		}
		// ----------------------------------------------
		return str;
		// ----------------------------------------------
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

	private ge_stepA jsoStepA(JSONObject lg, boolean reg) {
		// ----------------------------------------------
		ge_stepA s = new ge_stepA();
		// ----------------------------------------------
		s.distance = jsoTxtVal(lg, "distance");
		s.duration = jsoTxtVal(lg, "duration");
		s.start_location = jsoGeoPoint(lg, "start_location");
		s.end_location = jsoGeoPoint(lg, "end_location");
		s.html_instructions = jsoString(lg, "html_instructions");
		s.travel_mode = jsoString(lg, "travel_mode");
		s.points = jsoPoints(lg, "polyline", "points");
		// ----------------------------------------------
		s.transit_details = jsoTotransit_details(lg, "transit_details");
		s.steps = jsonAyToSteps(lg, "steps", reg);
		// ----------------------------------------------
		if (reg) {
			RegWJ ra, rb;
			ra = BBKReg.WJ_FtoT(s.start_location.lat, s.start_location.lng);
			s.start_location.lat = ra.w;
			s.start_location.lng = ra.j;
			rb = BBKReg.WJ_FtoT(s.end_location.lat, s.end_location.lng);
			s.end_location.lat = rb.w;
			s.end_location.lng = rb.j;
		}
		return s;
		// ----------------------------------------------
	}

	private ge_stepB jsoStepB(JSONObject lg, boolean reg) {
		// ----------------------------------------------
		ge_stepB s = new ge_stepB();
		// ----------------------------------------------
		s.distance = jsoTxtVal(lg, "distance");
		s.duration = jsoTxtVal(lg, "duration");
		s.start_location = jsoGeoPoint(lg, "start_location");
		s.end_location = jsoGeoPoint(lg, "end_location");
		s.html_instructions = jsoString(lg, "html_instructions");
		s.travel_mode = jsoString(lg, "travel_mode");
		s.points = jsoPoints(lg, "polyline", "points");
		// ----------------------------------------------
		s.transit_details = jsoTotransit_details(lg, "transit_details");
		// ----------------------------------------------
		if (reg) {
			RegWJ ra, rb;
			ra = BBKReg.WJ_FtoT(s.start_location.lat, s.start_location.lng);
			s.start_location.lat = ra.w;
			s.start_location.lng = ra.j;
			rb = BBKReg.WJ_FtoT(s.end_location.lat, s.end_location.lng);
			s.end_location.lat = rb.w;
			s.end_location.lng = rb.j;
		}
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

	private ge_TxtVal jsoTxtVal(JSONObject lg, String key) {
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
		return new ge_TxtVal(text, value);
		// ----------------------------------------------
	}

	private ge_line_type jsoGe_line(JSONObject lg, String key) {
		// ----------------------------------------------
		String n = "";
		try {
			JSONObject tv = lg.getJSONObject(key);
			n = tv.getString("short_name");
		} catch (JSONException e) {
		}
		return new ge_line_type(n);
		// ----------------------------------------------
	}

	private ge_leg jsonToGeLeg(JSONObject legObj, boolean reg) {
		// ----------------------------------------------
		ge_leg l = new ge_leg();
		try {
			// ----------------------------------------------
			l.distance = jsoTxtVal(legObj, "distance");
			l.duration = jsoTxtVal(legObj, "duration");
			// ----------------------------------------------
			l.departure_time = jsoTimes(legObj, "departure_time");
			l.arrival_time = jsoTimes(legObj, "arrival_time");
			// ----------------------------------------------
			l.start_address = jsoString(legObj, "start_address");
			l.end_address = jsoString(legObj, "end_address");
			l.start_location = jsoGeoPoint(legObj, "start_location");
			l.end_location = jsoGeoPoint(legObj, "end_location");
			// ----------------------------------------------
			if (reg) {
				RegWJ ra, rb;
				ra = BBKReg.WJ_FtoT(l.start_location.lat, l.start_location.lng);
				l.start_location.lat = ra.w;
				l.start_location.lng = ra.j;
				rb = BBKReg.WJ_FtoT(l.end_location.lat, l.end_location.lng);
				l.end_location.lat = rb.w;
				l.end_location.lng = rb.j;
				ra = rb = null;
			}
			// ----------------------------------------------
		} catch (Exception e) {
		}
		return l;
		// ----------------------------------------------
	}

	private ge_stepB[] jsonAyToSteps(JSONObject lg, String key, boolean reg) {
		// ----------------------------------------------
		JSONArray ss;
		try {
			ss = lg.getJSONArray(key);
		} catch (JSONException e1) {
			return null;
		}
		// ----------------------------------------------
		ge_stepB[] steps;
		int n = ss.length();
		if (n <= 0)
			return null;
		else
			steps = new ge_stepB[n];
		// ----------------------------------------------
		for (int i = 0; i < n; i++) {
			try {
				steps[i] = jsoStepB(ss.getJSONObject(i), reg);
			} catch (JSONException e) {
			}
		}
		return steps;
	}

	// =====================================================================================
	// =====================================================================================

	@SuppressWarnings("unused")
	private class ge_bounds {
		public GeoPoint northeast, southwest;

		ge_bounds() {
			northeast = southwest = new GeoPoint(0, 0);
		}
	}

	@SuppressWarnings("unused")
	private class ge_leg {
		public ge_times departure_time, arrival_time;
		public ge_TxtVal distance, duration;
		public GeoPoint start_location, end_location;
		public String start_address, end_address;

		ge_leg() {
			departure_time = arrival_time = new ge_times();
			distance = duration = new ge_TxtVal("", 0);
			start_location = end_location = new GeoPoint(0, 0);
			start_address = end_address = "";
		}
	}

	@SuppressWarnings("unused")
	private class ge_stepA {
		public ge_TxtVal distance, duration;
		public GeoPoint start_location, end_location;
		public String html_instructions, travel_mode, points;
		public transit_details_type transit_details;
		public ge_stepB steps[];

		ge_stepA() {
			distance = new ge_TxtVal("", 0);
			duration = new ge_TxtVal("", 0);
			start_location = new GeoPoint(0, 0);
			end_location = new GeoPoint(0, 0);
			html_instructions = "";
			travel_mode = "";
			points = "";
			transit_details = new transit_details_type();
			steps = null;
		}
	}

	@SuppressWarnings("unused")
	private class ge_stepB {
		public ge_TxtVal distance, duration;
		public GeoPoint start_location, end_location;
		public String html_instructions, travel_mode, points;
		public transit_details_type transit_details;

		ge_stepB() {
			distance = new ge_TxtVal("", 0);
			duration = new ge_TxtVal("", 0);
			start_location = new GeoPoint(0, 0);
			end_location = new GeoPoint(0, 0);
			html_instructions = "";
			travel_mode = "";
			points = "";
			transit_details = new transit_details_type();
		}
	}

	private class ge_TxtVal {
		String txt = "";
		double val = 0;

		ge_TxtVal(String _txt, double _val) {
			txt = _txt;
			val = _val;
		}
	}

	@SuppressWarnings("unused")
	private class ge_stops {
		public GeoPoint location;
		public String name;

		ge_stops() {
			location = new GeoPoint(0, 0);
			name = "";
		}

		ge_stops(GeoPoint a, String b) {
			location = a;
			name = b;
		}
	}

	@SuppressWarnings("unused")
	private class ge_times {
		public String text, time_zone;
		public long value;

		ge_times() {
			text = "";
			time_zone = "";
			value = 0;
		}
	}

	private class ge_line_type {

		public String short_name;

		ge_line_type() {
			short_name = "";
		}

		ge_line_type(String n) {
			short_name = n;
		}
	}

	// =====================================================================================
	// =====================================================================================

	private transit_details_type jsoTotransit_details(JSONObject step, String key) {
		// ----------------------------------------------
		transit_details_type s = new transit_details_type();
		try {
			// ----------------------------------------------
			JSONObject j = step.getJSONObject(key);
			// ----------------------------------------------
			s.arrival_stop = jsoTostops(j, "arrival_stop");
			s.arrival_time = jsoTimes(j, "arrival_time");
			s.departure_stop = jsoTostops(j, "departure_stop");
			s.departure_time = jsoTimes(j, "departure_time");
			s.headsign = j.getString("headsign");
			s.num_stops = j.getInt("num_stops");
			s.line = jsoGe_line(j, "line");
			// ----------------------------------------------
		} catch (JSONException e) {
		}
		return s;
		// ----------------------------------------------
	}

	private ge_times jsoTimes(JSONObject obj, String key) {
		// ----------------------------------------------
		ge_times s = new ge_times();
		try {
			s.text = jsoString(obj, "text");
			s.time_zone = jsoString(obj, "time_zone");
			s.value = obj.getLong("value");
			// ----------------------------------------------
		} catch (JSONException e) {
		}
		return s;
		// ----------------------------------------------
	}

	@SuppressWarnings("unused")
	private class transit_details_type {
		public ge_stops arrival_stop, departure_stop;
		public ge_times arrival_time, departure_time;
		public String headsign;
		public int num_stops;
		public ge_line_type line;

		transit_details_type() {
			arrival_stop = new ge_stops();
			arrival_time = new ge_times();
			departure_stop = new ge_stops();
			departure_time = new ge_times();
			headsign = "";
			num_stops = 0;
			line = new ge_line_type();
		}
	}

	private ge_stops jsoTostops(JSONObject lg, String key) {
		// ----------------------------------------------
		GeoPoint g = new GeoPoint(0, 0);
		String n = "";
		JSONObject tv;
		// ----------------------------------------------
		try {
			// ----------------------------------------------
			tv = lg.getJSONObject(key);
			n = tv.getString("name");
			g = jsoGeoPoint(tv, "location");
			// ----------------------------------------------
		} catch (JSONException e) {
		}
		return new ge_stops(g, n);
		// ----------------------------------------------
	}

	// =====================================================================================
	// =====================================================================================

	// 解析返回XML中overview_polyline的路线编码
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

	// 删除input字符串中的html格式
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
			str += "...";
		}
		return str;
	}

	// =====================================================================================
	@SuppressWarnings("unused")
	private ArrayList<HashMap<String, Object>> JsonToHashMap_ZZZZZZZZZZZZZZZZZZZ(String dataJson, boolean reg) {
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
			if (routes == null)
				return null;
			// ----------------------------------------------
			JSONObject routeJSObj, legJSObj;
			JSONArray legsJy, stepsJy;
			ge_leg l;
			ge_stepA s;
			// ----------------------------------------------
			String sname;
			// ----------------------------------------------
			// JSONObject overview_polyline;
			// String copyrights, summary;
			// JSONObject bounds;
			// List<GeoPoint> overview_poi;
			// JSONArray warnings;
			// ----------------------------------------------
			ArrayList<HashMap<String, Object>> tab = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// ----------------------------------------------
			for (int rti = 0; rti < routes.length(); rti++) {
				// ----------------------------------------------
				routeJSObj = routes.getJSONObject(rti);
				// ----------------------------------------------
				// bounds = routeJSObj.getJSONObject("bounds");
				// ----------------------------------------------
				// overview_polyline =
				// routeJSObj.getJSONObject("overview_polyline");
				// overview_poi =
				// decodePoly(overview_polyline.getString("points"));
				// ----------------------------------------------
				// copyrights = routeJSObj.getString("copyrights");
				// summary = routeJSObj.getString("summary");
				// warnings = routeJSObj.getJSONArray("warnings");
				// ----------------------------------------------
				legsJy = routeJSObj.getJSONArray("legs");
				for (int lgi = 0; lgi < legsJy.length(); lgi++) {
					// ----------------------------------------------
					legJSObj = legsJy.getJSONObject(lgi);
					l = jsonToGeLeg(legJSObj, reg);
					sname = l.distance.txt + "/" + l.duration.txt;
					// ----------------------------------------------
					String homeCity = BBKString.theSameHead(l.start_address, l.end_address);
					l.start_address = l.start_address.replace(homeCity, "");
					l.end_address = l.end_address.replace(homeCity, "");
					// ----------------------------------------------
					Main_Ask.askBackInfoStrAdd(//
					"城市：\t" + homeCity + "\r\n" + //
							"起点：\t" + l.start_address + "\r\n" + //
							"终点：\t" + l.end_address + "\r\n" + //
							"里程：\t" + sname //
					);
					LegsABtoTab(map, tab, 0, l.start_address, "All", l.distance.txt, l.duration.txt, l.start_location.lat, l.start_location.lng, l.start_address, "");
					// ----------------------------------------------
					stepsJy = legJSObj.getJSONArray("steps");
					for (int sti = 0; sti < stepsJy.length(); sti++) {
						// ----------------------------------------------
						s = jsoStepA(stepsJy.getJSONObject(sti), reg);
						// ----------------------------------------------
						s.html_instructions = splitAndFilterString(s.html_instructions, 400);
						sname = splitAndFilterString(s.html_instructions, 200);
						sname = EnglishToChinese(sname);
						// ----------------------------------------------
						sname = Main_Ask_askBackInfoStrAdd_build(s);
						// ----------------------------------------------
						// if (s.steps != null) {
						// for (int ssi = 0; ssi < s.steps.length; ssi++) {
						// Main_Ask_askBackInfoStrAdd_build(s.steps[ssi]);
						// }
						// }
						// ----------------------------------------------
						Main_Ask.askBackInfoStrAdd(sname);
						// ----------------------------------------------
						LegsABtoTab(//
								map, tab, sti, sname, s.travel_mode, s.distance.val / 1000 + "km", s.duration.txt, //
								s.start_location.lat, s.start_location.lng, s.html_instructions, s.points//
						);
						// LegsABtoTab(//
						// map, tab, sti, sname, s.travel_mode, s.distance.val /
						// 1000 + "km", s.duration.txt, //
						// s.end_location.lat, s.end_location.lng,
						// s.html_instructions, s.points//
						// );
						// ----------------------------------------------
					}
					// ----------------------------------------------
					LegsABtoTab(map, tab, 0, l.end_address + sname, "All", l.distance.txt, l.duration.txt, l.end_location.lat, l.end_location.lng, l.end_address, "");
					// ----------------------------------------------
				}
				// ----------------------------------------------
				jsonObj = null;
				status = null;
				routes = legsJy = stepsJy = null;
				routeJSObj = legJSObj = null;
				// overview_polyline = null;
				// overview_poi = null;
				s = null;
				map = null;
				sname = null;
				// ----------------------------------------------
				return tab;
				// ----------------------------------------------
			}
			// ------------------------------------------------------------------
		} catch (JSONException ex) {
			bd.d("BBKGoogle.JsonToHashMap = " + ex.toString(), true, false);
		}
		// ------------------------------------------------------------------
		return null;
		// ------------------------------------------------------------------
	}

}
