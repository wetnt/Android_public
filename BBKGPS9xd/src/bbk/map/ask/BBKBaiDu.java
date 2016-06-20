package bbk.map.ask;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import bbk.bbk.box.BBKSoft;
import bbk.map.dat.BBKReg;
import bbk.map.dat.BBKReg.RegWJ;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.map.lay.BBKMapLay.poi_type;
import bbk.net.abc.BBKHttpGet;
import bbk.zzz.debug.BBKDebug;

public class BBKBaiDu {

	// ====================================================================================
	private String AskStr = "";

	public void BaiDuiAskRunThead(final String str, final double cw, final double cj) {
		// -------------------------------------
		AskStr = str;
		// -------------------------------------
		new Thread() {
			public void run() {
				// -----------------------------
				BaiDuiAskRun(str, cw, cj);
				// -----------------------------
			}
		}.start();
		// -------------------------------------
	}

	// ====================================================================================
	// ====================================================================================

	// -------------------------------------
	// http://api.map.baidu.com/place/search?&query=荣上居&region=北京&output=xml&key=9e51c2207bdffb09195c6ca8713977d1
	// http://api.map.baidu.com/place/search?&query=荣上居&location=39.915,116.404&radius=28000&output=xml&key=9e51c2207bdffb09195c6ca8713977d1
	public final String GgUrl = "http://api.map.baidu.com/place/search?key=9e51c2207bdffb09195c6ca8713977d1&output=json&query=";// &output=xml&query=";
	public final String gbCode = "UTF-8";// "GB2312";

	// -------------------------------------
	// http://api.map.baidu.com/direction/v1/routematrix?
	// output=json&origins=天安门|鸟巢
	// &destinations=北京邮电大学|上海南京路
	// &ak=E4805d16520de693a3fe707cdc962045
	// -------------------------------------
	// http://api.map.baidu.com/direction/v1?
	// mode=transit&origin=上地五街
	// &destination=北京大学
	// &region=北京
	// &output=json
	// &ak=E4805d16520de693a3fe707cdc962045
	// -------------------------------------

	// ====================================================================================
	private boolean BaiDuiAskRun(String str, double cw, double cj) {
		// ------------------------------------------------------
		// 接口为火星坐标，查询时需要把真实坐标改为火星坐标
		String tp = BBKReg.Str_WJ_TF(str);
		tp = URLEncoder.encode(tp);
		// ------------------------------------------------------
		final String myjson = GgAsk(tp, cw, cj);
		if (myjson == null || myjson.length() < 10) {
			BBKDebug.d("BaiDuiAskRun.myjson == null", false, false);
			return false;
		}
		// ------------------------------------------------------
		ArrayList<HashMap<String, Object>> tab = JsonToHashMap(myjson, true);
		if (tab != null) {
			// ------------------------------------------------------
			BBKSoft.myLays.layask = JsonToMapLay(tab);
			BBKSoft.myLays.LaySave(BBKSoft.myLays.layask, BBKSoft.PathAsks + AskStr);
			// ------------------------------------------------------
			handlerAsk.post(RunnableAsk);
			// ------------------------------------------------------
		}
		// ------------------------------------------------------
		return true;
		// ------------------------------------------------------
	}

	private Handler handlerAsk = new Handler();
	private Runnable RunnableAsk = new Runnable() {
		public void run() {
			// ------------------------------------------------------
			BBKSoft.myAsk.AskInfoListShowRun("查询: " + AskStr);
			// ------------------------------------------------------
		}
	};

	// ====================================================================================
	private String GgAsk(String ask, double cw, double cj) {
		// --------------------------------------------------
		String myGps = "";
		myGps += "&radius=30000&location=";
		myGps += cw + "," + cj;
		// --------------------------------------------------
		String myAsk = GgUrl + ask + myGps;
		// --------------------------------------------------
		String myjson = BBKHttpGet.BBKHttpGetUrl(myAsk, gbCode, false);
		// --------------------------------------------------
		return myjson;
		// --------------------------------------------------
	}

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
			ps = new StringBuffer(map.get("Mode") + "");
			sz = new StringBuffer(map.get("ADD").toString());
			w = (Double) map.get("LAT");
			j = (Double) map.get("LON");
			// ------------------------------------------------------------------
			lay.pois.add(new poi_type(nn, ps, sz, w, j, 0, null));
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
	private ArrayList<HashMap<String, Object>> JsonToHashMap(String dataJson, boolean Reg) {
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
			JSONObject jsonItem, location;
			String nam, add, tel, uid, tag, url;
			double w, j;
			RegWJ r;
			// ----------------------------------------------
			JSONArray points = jsonObj.getJSONArray("results");
			// ----------------------------------------------
			ArrayList<HashMap<String, Object>> tab = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map;
			// ----------------------------------------------
			for (int i = 0; i < points.length(); i++) {
				// ----------------------------------------------
				jsonItem = points.getJSONObject(i);
				// ----------------------------------------------
				nam = add = tel = tag = uid = url = "";
				// ----------------------------------------------
				nam = jsonItem_getString(jsonItem, "name");
				add = jsonItem_getString(jsonItem, "address");
				tel = jsonItem_getString(jsonItem, "telephone");
				tag = jsonItem_getString(jsonItem, "tag");
				uid = jsonItem_getString(jsonItem, "uid");
				url = jsonItem_getString(jsonItem, "detail_url");
				// ----------------------------------------------
				location = jsonItem.getJSONObject("location");
				w = location.getDouble("lat");
				j = location.getDouble("lng");
				if (Reg) {
					r = WJ_FtoT(w, j);
					w = r.w;
					j = r.j;
				}
				// ----------------------------------------------
				map = new HashMap<String, Object>();
				// ----------------------------------------------
				map.put(tabitems[0], i);
				map.put(tabitems[1], nam);
				map.put(tabitems[2], tag);
				map.put(tabitems[3], tel);
				map.put(tabitems[4], add);
				map.put(tabitems[5], w);
				map.put(tabitems[6], j);
				map.put(tabitems[7], url);
				map.put(tabitems[8], "");
				// ----------------------------------------------
				tab.add(map);
				// ----------------------------------------------
			}
			// ----------------------------------------------
			nam = add = tel = uid = tag = url = null;
			// ----------------------------------------------
			return tab;
			// ----------------------------------------------
		} catch (JSONException ex) {
			BBKDebug.d("BBKBaiDu.JsonToHashMap = " + ex.toString(), true, false);
		}
		// ----------------------------------------------
		return null;
		// ----------------------------------------------
	}

	// ========================================================================
	@SuppressWarnings("unused")
	private void Lay_Reg_FtoJ(Lay_type lay) {
		// --------------------------------------------------------------------
		double w = 0, j = 0;
		RegWJ r = new RegWJ(0, 0);
		// --------------------------------------------------------------------
		if (lay.pois != null && lay.pois.size() > 1) {
			for (int i = 0; i < lay.pois.size(); i++) {
				w = lay.pois.get(i).p.w;
				j = lay.pois.get(i).p.j;
				r = WJ_FtoT(w, j);
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
						r = WJ_FtoT(w, j);
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
						r = WJ_FtoT(w, j);
						lay.poly.get(i).p.get(k).set(r.w, r.j);
					}
				}
			}
		}
		// --------------------------------------------------------------------
	}

	// ========================================================================
	private RegWJ WJ_FtoT(double wf, double jf) {
		// --------------------------------------------------------------------
		RegWJ wj = BBKReg.WJ_FtoT(wf, jf);
		double wd = wj.w - 0.00561491;
		double jd = wj.j - 0.00645157;
		return new RegWJ(wd, jd);
		// --------------------------------------------------------------------
		// 百度返回：39.985226 116.345018
		// 火星修正：39.98397491 116.33896157 //初步修正
		// 火星修正：39.98396487 116.33891994 //精修
		// 百度修正：-0.00561491 -0.00645157
		// 百度精修：-0.00560487 -0.00640994
		// 真是坐标：39.97836,116.33251
		// --------------------------------------------------------------------
		// --------------------------------------------------------------------
		// wj = BBKMapReg.F2T(wd, jd);
		// String log = "";
		// log += i + "===" + names + "=" + ww + "=" + jj + "=";
		// log += www + "=" + jjj + "=" + wd + "=" + jd + "=";
		// Log.d("SSS", log);
		// --------------------------------------------------------------------
	}

	// ========================================================================
	private String jsonItem_getString(JSONObject jo, String str) {
		// --------------------------------------------------------------------
		String s = "";
		// --------------------------------------------------------------------
		try {
			s = jo.getString(str);
		} catch (Exception e) {
		}
		// --------------------------------------------------------------------
		return s;
		// --------------------------------------------------------------------
	}
	// ========================================================================

}
