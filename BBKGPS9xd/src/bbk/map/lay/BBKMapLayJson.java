package bbk.map.lay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.map.lay.BBKMapLay.p_point;
import bbk.map.lay.BBKMapLay.poi_type;
import bbk.zzz.debug.bd;

public class BBKMapLayJson {

	// =================================================================================
	// http://blog.sina.com.cn/s/blog_7ffb8dd501013q5c.html
	// http://blog.csdn.net/liugenghao3048/article/details/8814807
	// 文件存储_解析JSON文档
	// =============================================================================
	public static Lay_type BBKMapLayFromJson(String dataJson) {
		// ----------------------------------------------------------------------------
		Lay_type lay = new Lay_type();
		// ----------------------------------------------------------------------------
		if (dataJson == null || dataJson.length() < 10)
			return lay;
		// ----------------------------------------------------------------------------
		JSONObject JsonLay = null;
		try {
			JsonLay = new JSONObject(dataJson);
		} catch (JSONException e1) {
			bd.d("BBKMapLayJson.BBKMapLayFromJson = " + e1.toString(), false, false);
			return lay;
		}
		// ----------------------------------------------------------------------------
		dataJson = null;
		System.gc();
		// ----------------------------------------------------------------------------
		try {
			// ------------------------------------------------------------------------
			lay.pois = BBKMapLayPoisFromJSONObj(JsonLay.getJSONArray("points"));
			lay.line = BBKMapLayLineFromJSONObj(JsonLay.getJSONArray("lines"));
			lay.poly = BBKMapLayLineFromJSONObj(JsonLay.getJSONArray("polys"));
			// ------------------------------------------------------------------------
		} catch (JSONException e) {
			bd.d("BBKMapLayJson.BBKMapLayFromJson = " + e.toString(), false, false);
			// e.printStackTrace();
		}
		// ----------------------------------------------------------------------------
		JsonLay = null;
		System.gc();
		// ----------------------------------------------------------------------------
		// lay.path = path;
		// lay.name = name;
		return lay;
		// ----------------------------------------------------------------------------
	}

	public static List<poi_type> BBKMapLayPoisFromJSONObj(JSONArray points) {
		// ----------------------------------------------------------------------------
		List<poi_type> pois = new ArrayList<poi_type>();
		// ----------------------------------------------------------------------------
		double w, j, h;
		Long t;
		StringBuffer a = null, p = null, s = null;
		// ----------------------------------------------------------------------------
		for (int i = 0; i < points.length(); i++) {
			// ------------------------------------------------------------
			try {
				JSONObject jsonItem = points.getJSONObject(i);
				// ------------------------------------------------------------
				w = jsonItem.getDouble("w");
				j = jsonItem.getDouble("j");
				h = jsonItem.getDouble("h");
				t = jsonItem.getLong("t");
				a = new StringBuffer(jsonItem.getString("a"));
				p = new StringBuffer(jsonItem.getString("p"));
				s = new StringBuffer(jsonItem.getString("s"));
				// ------------------------------------------------------------
				pois.add(new poi_type(a, p, s, w, j, h, new Date(t)));
				// ------------------------------------------------------------
				jsonItem = null;
				// ------------------------------------------------------------
			} catch (JSONException e) {
				bd.d("BBKMapLayJson.BBKMapLayPoisFromJSONObj = " + e.toString(), false, false);
			}
			// ------------------------------------------------------------
		}
		// ------------------------------------------------------------
		t = null;
		a = null;
		p = null;
		s = null;
		points = null;
		System.gc();
		// ------------------------------------------------------------
		return pois;
		// ----------------------------------------------------------------------------
	}

	public static List<line_type> BBKMapLayLineFromJSONObj(JSONArray lines) {
		// ----------------------------------------------------------------------------
		List<line_type> polyline = new ArrayList<line_type>();
		double w, j;
		// ----------------------------------------------------------------------------
		try {
			// ------------------------------------------------------------
			for (int i = 0; i < lines.length(); i++) {
				polyline.add(new line_type());
			}
			// ------------------------------------------------------------
			for (int i = 0; i < lines.length(); i++) {
				// ------------------------------------------------------------
				JSONObject line = lines.getJSONObject(i);
				int id = line.getInt("id");
				JSONArray data = line.getJSONArray("line");
				polyline.get(id).name.append(line.getString("name"));
				// ------------------------------------------------------------
				for (int k = 0; k < data.length(); k++) {
					JSONObject jsonP = data.getJSONObject(k);
					// ------------------------------------------------------------
					w = jsonP.getDouble("w");
					j = jsonP.getDouble("j");
					polyline.get(id).p.add(new p_point(w, j));
					// ------------------------------------------------------------
					jsonP = null;
					// ------------------------------------------------------------
				}
				// ------------------------------------------------------------
				data = null;
				line = null;
				// ------------------------------------------------------------
			}
			// ------------------------------------------------------------
			lines = null;
			// ------------------------------------------------------------
		} catch (JSONException e) {
			bd.d("BBKMapLayJson.BBKMapLayLineFromJSONObj = " + e.toString(), false, false);
		}
		// ----------------------------------------------------------------------------
		System.gc();
		return polyline;
		// ----------------------------------------------------------------------------
	}

	// =============================================================================
	public static JSONObject BBKMapLayToJson(Lay_type lay) {
		// ------------------------------------------------------------
		JSONObject JsonLay = new JSONObject();
		// ------------------------------------------------------------
		try {
			JSONArray JsonPolys = BBKMapLinesToJson(lay.poly);
			JsonLay.putOpt("polys", JsonPolys);
		} catch (JSONException e) {
			bd.d("BBKMapLayJson.BBKMapLayToJson.poly = " + e.toString(), false, false);
		}
		// ------------------------------------------------------------
		try {
			JSONArray JsonLines = BBKMapLinesToJson(lay.line);
			JsonLay.putOpt("lines", JsonLines);
		} catch (JSONException e) {
			bd.d("BBKMapLayJson.BBKMapLayToJson.lines = " + e.toString(), false, false);
		}
		// ------------------------------------------------------------
		try {
			JSONArray JsonPois = BBKMapPoisToJson(lay.pois);
			JsonLay.put("points", JsonPois);
		} catch (JSONException e) {
			bd.d("BBKMapLayJson.BBKMapLayToJson.lines.points = " + e.toString(), false, false);
		}
		// ------------------------------------------------------------
		return JsonLay;
		// ------------------------------------------------------------
	}

	public static JSONArray BBKMapPoisToJson(List<poi_type> pois) {
		// ------------------------------------------------------------
		JSONArray JsonPois = new JSONArray();
		for (int i = 0; i < pois.size(); i++) {
			// --------------------------------------
			JSONObject poi = BBKMapPointToJson(pois.get(i), i);
			JsonPois.put(poi);
			// --------------------------------------
		}
		// ------------------------------------------------------------
		return JsonPois;
		// ------------------------------------------------------------
	}

	public static JSONObject BBKMapPointToJson(poi_type point, int id) {
		// --------------------------------------
		try {
			// --------------------------------------
			JSONObject poi = new JSONObject();
			// -------------.-----------------
			poi.put("i", id + "");
			// -------------.-----------------
			poi.put("w", point.p.w + "");
			poi.put("j", point.p.j + "");
			// -------------.-----------------
			poi.put("h", point.h.h + "");
			poi.put("t", point.h.t.getTime() + "");
			// -------------.-----------------
			poi.put("a", point.s.a + "");
			poi.put("p", point.s.p + "");
			poi.put("s", point.s.s + "");
			// --------------------------------------
			return poi;
			// --------------------------------------
		} catch (JSONException e) {
			bd.d("BBKMapLayJson.BBKMapPointToJson = " + e.toString(), false, false);
			return null;
		}
		// --------------------------------------
	}

	public static JSONArray BBKMapLinesToJson(List<line_type> lines) {
		// ------------------------------------------------------------
		JSONArray JsonLines = new JSONArray();
		for (int i = 0; i < lines.size(); i++) {
			// --------------------------------------------------------
			JSONObject line = BBKMapLineToJson(lines.get(i), i);
			JsonLines.put(line);
			// --------------------------------------------------------
		}
		// ------------------------------------------------------------
		return JsonLines;
		// ------------------------------------------------------------
	}

	public static JSONObject BBKMapLineToJson(line_type line, int id) {
		// ------------------------------------------------------------
		JSONArray JsonData = new JSONArray();
		for (int i = 0; i < line.p.size(); i++) {
			JSONObject poi = BBKMapPoiToJson(line.p.get(i));
			JsonData.put(poi);
		}
		// ------------------------------------------------------------
		JSONObject JsonLine = new JSONObject();
		try {
			JsonLine.put("id", id + "");
			JsonLine.put("name", id);
			JsonLine.put("line", JsonData);
		} catch (JSONException e) {
		}
		// ------------------------------------------------------------
		return JsonLine;
		// ------------------------------------------------------------
	}

	public static JSONObject BBKMapPoiToJson(p_point p) {
		// --------------------------------------
		try {
			// --------------------------------------
			JSONObject poi = new JSONObject();
			poi.put("w", p.w + "");
			poi.put("j", p.j + "");
			// --------------------------------------
			return poi;
			// --------------------------------------
		} catch (JSONException e) {
			bd.d("BBKMapLayJson.BBKMapPoiToJson = " + e.toString(), false, false);
			return null;
		}
		// --------------------------------------
	}

	// =================================================================================

}
