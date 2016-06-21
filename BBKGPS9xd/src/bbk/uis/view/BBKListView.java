package bbk.uis.view;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.bbkgps9xd.R;

import android.os.Environment;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import bbk.bbk.box.BBKSoft;
import bbk.map.lay.BBKMapLay;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.map.lay.BBKMapLay.p_point;
import bbk.map.lay.BBKMapLay.poi_type;
import bbk.sys.abc.BBKSYS;

public class BBKListView {

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================

	// ==============================================================================================
	public static String[] fromItem = { "a", "s", "p", "w", "j", "h", "t" };
	public static int[] toItem = { R.id.Item_Title, R.id.Item_Text };// R.id.Item_Image

	// ==============================================================================================
	public static void ListViewLoad(ArrayList<HashMap<String, Object>> list, ListView listview) {
		// -----------------------------------------------------------------------------
		SimpleAdapter adapter = new SimpleAdapter(// 数据装载
				BBKSoft.bbkContext,// 目标
				list,// 数据
				R.layout.list_item,// 样式
				fromItem,// 对应输入
				toItem // 对应输出
		);
		// -----------------------------------------------------------------------------
		listview.setAdapter(adapter);
		// -----------------------------------------------------------------------------
	}

	public static ArrayList<HashMap<String, Object>> BBKLayToArrayList(Lay_type lay, boolean poi, boolean line, boolean ploy) {
		// -----------------------------------------------------------------------------
		ArrayList<HashMap<String, Object>> lt = new ArrayList<HashMap<String, Object>>();
		// -----------------------------------------------------------------------------
		if (poi)
			ListPoisMapAdd(lay.pois, lt);
		// -----------------------------------------------------------------------------
		if (line)
			ListLineMapAdd(lay.line, lt);
		// -----------------------------------------------------------------------------
		if (ploy)
			ListLineMapAdd(lay.poly, lt);
		// -----------------------------------------------------------------------------
		return lt;
		// -----------------------------------------------------------------------------
	}

	public static void ListPoisMapAdd(List<poi_type> pois, ArrayList<HashMap<String, Object>> lt) {
		// -----------------------------------------------------------------------------
		for (int i = 0; i < pois.size(); i++) {
			// -----------------------------------------------------------------------------
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(fromItem[0], pois.get(i).s.a.toString());
			map.put(fromItem[1], pois.get(i).s.s.toString());
			map.put(fromItem[2], pois.get(i).s.p.toString());
			map.put(fromItem[3], pois.get(i).p.w);
			map.put(fromItem[4], pois.get(i).p.j);
			map.put(fromItem[5], pois.get(i).h.h);
			map.put(fromItem[6], pois.get(i).h.t);
			// -----------------------------------------------------------------------------
			lt.add(map);
			// -----------------------------------------------------------------------------
		}
		// -----------------------------------------------------------------------------
	}

	public static void ListLineMapAdd(List<line_type> poly, ArrayList<HashMap<String, Object>> lt) {
		// -----------------------------------------------------------------------------
		for (int i = 0; i < poly.size(); i++) {
			for (int k = 0; k < poly.get(i).p.size(); k++) {
				// -----------------------------------------------------------------------------
				HashMap<String, Object> map = new HashMap<String, Object>();
				String a = "poly_" + i + "_" + k;
				String b = poly.get(i).p.get(k).w + "," + poly.get(i).p.get(k).j;
				map.put(fromItem[0], a);
				map.put(fromItem[1], b);
				map.put(fromItem[2], null);
				map.put(fromItem[3], poly.get(i).p.get(k).w);
				map.put(fromItem[4], poly.get(i).p.get(k).j);
				// -----------------------------------------------------------------------------
				lt.add(map);
				// -----------------------------------------------------------------------------
			}
		}
		// -----------------------------------------------------------------------------
	}

	public static String GetTrackItemName(ArrayList<HashMap<String, Object>> lt, int n) {
		// --------------------------------------------
		if (n == -1 || n >= lt.size())
			return "";
		// --------------------------------------------
		String Title = lt.get(n).get(fromItem[0]).toString();
		String Content = lt.get(n).get(fromItem[1]).toString();
		if (Title.length() != 0) {
			return Title;
		} else {
			return Content;
		}
		// --------------------------------------------
	}

	public static poi_type GetTrackItemPoi(ArrayList<HashMap<String, Object>> lt, int n) {
		// --------------------------------------------
		if (n == -1 || n >= lt.size())
			return null;
		// --------------------------------------------
		String Title = lt.get(n).get(fromItem[0]).toString();
		String Content = lt.get(n).get(fromItem[1]).toString();
		// String PicName = listLay.get(n).get(fromItem[2]).toString();
		String ww = lt.get(n).get(fromItem[3]).toString();
		String jj = lt.get(n).get(fromItem[4]).toString();
		// --------------------------------------------
		String hh;
		Object tt;
		Date dt = new Date(System.currentTimeMillis());
		// --------------------------------------------
		try {
			hh = lt.get(n).get(fromItem[5]).toString();
			tt = lt.get(n).get(fromItem[6]);
			dt = (Date) tt;
		} catch (Exception e) {
			hh = "0";
			tt = new Date(System.currentTimeMillis());
			dt = (Date) tt;
		}
		// --------------------------------------------
		if (Title.length() == 0) {
			Title = dt.toString() + " " + ww + "," + jj;
		}
		double w = Double.parseDouble(ww);
		double j = Double.parseDouble(jj);
		double h = Double.parseDouble(hh);
		// --------------------------------------------
		poi_type poi = new poi_type(new StringBuffer(Title), new StringBuffer("0"), new StringBuffer(Content), w, j, h, dt);
		return poi;
		// --------------------------------------------
	}

	public static p_point GetTrackItemPt(ArrayList<HashMap<String, Object>> lt, int n) {
		// --------------------------------------------
		// --------------------------------------------
		if (n == -1 || n >= lt.size())
			return null;
		// --------------------------------------------
		String ww = lt.get(n).get(fromItem[3]).toString();
		String jj = lt.get(n).get(fromItem[4]).toString();
		// --------------------------------------------
		double w = Double.parseDouble(ww);
		double j = Double.parseDouble(jj);
		// --------------------------------------------
		p_point poi = new p_point(w, j);
		return poi;
		// --------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ###############################数据设置#############################################
	// ####################################################################################
	// ====================================================================================
	public static ArrayList<HashMap<String, Object>> TrackListAlSchSelect(String str, ArrayList<HashMap<String, Object>> lt) {// 过滤算法
		// ------------------------------------------------------------------------------------------
		ArrayList<HashMap<String, Object>> lout = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String n = "", s = "";
		// ------------------------------------------------------------------------------------------
		if (lt == null)
			return lout;
		// ------------------------------------------------------------------------------------------
		// 生成动态数组，加入数据
		for (int i = 0; i < lt.size(); i++) {
			// --------------------------------------------
			map = lt.get(i);
			n = map.get(fromItem[0]).toString();
			s = map.get(fromItem[1]).toString();
			// --------------------------------------------
			if (n.indexOf(str) > -1 || s.indexOf(str) > -1) {
				lout.add(map);
			}
			// --------------------------------------------
		}
		// ------------------------------------------------------------------------------------------
		map = null;
		return lout;
		// ------------------------------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public static ArrayList<HashMap<String, Object>> DirFilesToListMap(String dirPath, String ext) {
		// -----------------------------------------------------------------------------
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// -----------------------------------------------------------------------------
		double filesize = 0;
		String strFileSize = "";
		File[] files;
		// -----------------------------------------------------------------------------
		String sDStateString = Environment.getExternalStorageState();
		// -----------------------------------------------------------------------------
		if (sDStateString.equals(Environment.MEDIA_MOUNTED)) {
			try {
				// -----------------------------------------------------------------------------
				// Lay_type bbp = new Lay_type();
				// -----------------------------------------------------------------------------
				File sdPath = new File(dirPath);
				files = sdPath.listFiles(getFileExtensionFilter(ext));
				// -----------------------------------------------------------------------------
				Arrays.sort(files);
				// Arrays.sort(files, new FileUtils.CompratorBySize());
				// Arrays.sort(files, new FileUtils.CompratorByLastModified());
				// -----------------------------------------------------------------------------
				int fl = files.length;
				File[] filet = new File[fl];
				for (int i = 0; i < fl; i++) {
					filet[fl - i - 1] = files[i];
				}
				files = filet;
				// Log.d("BBKSYS", "===41");
				// -----------------------------------------------------------------------------
				if (files.length > 0) {
					// -----------------------------------------------------------------------------
					for (File file : files) {
						// -----------------------------------------------------------------------------
						HashMap<String, Object> map = new HashMap<String, Object>();
						if (file.length() > 0) {
							// -----------------------------------------------------------------
							filesize = (int) file.length() * 100 / 1024;
							strFileSize = filesize / 100 + " KB";
							map.put(fromItem[0], file.getName().replace(ext, ""));
							map.put(fromItem[1], strFileSize);
							map.put(fromItem[2], null);// R.drawable.ic_launcher);图像资源的ID
							// -----------------------------------------------------------------
							listItem.add(map);
							// -----------------------------------------------------------------
						} else {
							// -----------------------------------------------------------------
							try {
								file.delete();
							} catch (Exception e) {
							}
							// -----------------------------------------------------------------
						}
					}
					// -------------------------------------------------------------------------
				}
				// -------------------------------------------------------------------------
				return listItem;
				// -------------------------------------------------------------------------
			} catch (Exception e) {
				return null;
			}
		}
		// -----------------------------------------------------------------------------
		return null;
		// -----------------------------------------------------------------------------
	}

	// -----------------------------------------------------------------------------
	// File[] zipFiles = sdPath.listFiles(getFileExtensionFilter(".zip"));
	// Collections.sort(zipFiles);
	public static FilenameFilter getFileExtensionFilter(String extension) {
		final String _extension = extension;
		return new FilenameFilter() {
			public boolean accept(File file, String name) {
				boolean ret = name.endsWith(_extension);
				return ret;
			}
		};
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public static void BBKMapMovePoi(ArrayList<HashMap<String, Object>> lt, int n) {
		// -----------------------------------------------------------------------------
		poi_type poi = GetTrackItemPoi(lt, n);
		if (poi != null) {
			BBKSoft.myMaps.MapSetWJZ(poi.p.w, poi.p.j, 0);
			BBKSoft.MapFlash(true);
		}
		// -----------------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public static void ListReNameDel(ArrayList<HashMap<String, Object>> temp, int id, final String path) {
		// --------------------------------------------------------------------------------
		String name = GetTrackItemName(temp, id);
		if (name.length() == 0)
			return;
		// --------------------------------------------------------------------------------
		final String oldName = name + BBKMapLay.bbklayExtension;
		BBKSYS.FileDel(path, oldName);
		// --------------------------------------------------------------------------------
	}

	public static void ListReNameSave(ArrayList<HashMap<String, Object>> temp, int id, String newName) {
		// --------------------------------------------------------------------------------
		newName = newName + BBKMapLay.bbklayExtension;
		if (newName == null || newName.length() == 0)
			return;
		String name = GetTrackItemName(temp, id);
		if (name.length() == 0)
			return;
		String oldName = name + BBKMapLay.bbklayExtension;
		// --------------------------------------------------------------------------------
		BBKSYS.FileReName(BBKSoft.PathAsks, oldName, newName);
		// --------------------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
	public static void ListInfoListRun(ArrayList<HashMap<String, Object>> temp, int id, String path, String types) {
		// ---------------------------------------------------------
		String name = GetTrackItemName(temp, id);
		if (name.length() <= 0)
			return;
		// ---------------------------------------------------------
		ListInfoMapRun(temp, id, path);
		// ---------------------------------------------------------
		ArrayList<HashMap<String, Object>> lt;
		lt = BBKLayToArrayList(BBKSoft.myLays.laytmp, true, false, false);
		// ---------------------------------------------------------
		BBKSoft.myList.ListLayAdd(lt, types + name);
		// ---------------------------------------------------------
	}

	public static void ListInfoMapRun(ArrayList<HashMap<String, Object>> temp, int id, String path) {
		// ---------------------------------------------------------
		String name = BBKListView.GetTrackItemName(temp, id);
		if (name.length() <= 0)
			return;
		// ---------------------------------------------------------
		BBKSoft.myLays.LayLoad(BBKSoft.myLays.laytmp, path + name);
		BBKSoft.MapToLayCenter(BBKSoft.myLays.laytmp);
		// ---------------------------------------------------------
	}

	public static void ListInfoMapRun(String pathname) {
		// ---------------------------------------------------------
		BBKSoft.myLays.LayLoad(BBKSoft.myLays.laytmp, pathname);
		BBKSoft.MapToLayCenter(BBKSoft.myLays.laytmp);
		// ---------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ####################################################################################
	// ####################################################################################
	// ====================================================================================
}