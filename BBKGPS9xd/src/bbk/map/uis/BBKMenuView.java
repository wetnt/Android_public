﻿package bbk.map.uis;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.bbkgps9xd.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class BBKMenuView {

	// =============================================================
	private boolean isMore = false;// menu菜单翻页控制
	AlertDialog menuDialog;// menu菜单Dialog
	GridView menuGrid;
	View menuView;
	private Activity bbkAct;

	// =============================================================
	private final int ITEM_SEARCH = 0;// 搜索
	private final int ITEM_FILE_MANAGER = 1;// 文件管理
	private final int ITEM_DOWN_MANAGER = 2;// 下载管理
	private final int ITEM_FULLSCREEN = 3;// 全屏
	private final int ITEM_MORE = 11;// 菜单

	int[] menu_image_array = { R.drawable.menu_search, R.drawable.menu_filemanager, R.drawable.menu_downmanager, R.drawable.menu_fullscreen, R.drawable.menu_inputurl, R.drawable.menu_bookmark, R.drawable.menu_bookmark_sync_import, R.drawable.menu_sharepage, R.drawable.menu_quit, R.drawable.menu_nightmode, R.drawable.menu_refresh, R.drawable.menu_more };
	String[] menu_name_array = { "搜索", "文件管理", "下载管理", "全屏", "网址", "书签", "加入书签", "分享页面", "退出", "夜间模式", "刷新", "更多" };
	int[] menu_image_array2 = { R.drawable.menu_auto_landscape, R.drawable.menu_penselectmodel, R.drawable.menu_page_attr, R.drawable.menu_novel_mode, R.drawable.menu_page_updown, R.drawable.menu_checkupdate, R.drawable.menu_checknet, R.drawable.menu_refreshtimer, R.drawable.menu_syssettings, R.drawable.menu_help, R.drawable.menu_about, R.drawable.menu_return };
	String[] menu_name_array2 = { "自动横屏", "笔选模式", "阅读模式", "浏览模式", "快捷翻页", "检查更新", "检查网络", "定时刷新", "设置", "帮助", "关于", "返回" };

	public void onCreateMenu(Context context, Menu menu) {
		// ---------------------------------------------
		bbkAct = (Activity) context;
		// ---------------------------------------------
		menuView = View.inflate(bbkAct, R.layout.menu_gridview, null);
		// ---------------------------------------------
		// 创建AlertDialog
		menuDialog = new AlertDialog.Builder(context).create();
		menuDialog.setView(menuView);
		menuDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU)// 监听按键
					dialog.dismiss();
				return false;
			}
		});
		// ---------------------------------------------
		menuGrid = (GridView) menuView.findViewById(R.id.gridview);
		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
		// ---------------------------------------------
		/** 监听menu选项 **/
		menuGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				switch (arg2) {
				case ITEM_SEARCH:// 搜索
					break;
				case ITEM_FILE_MANAGER:// 文件管理
					break;
				case ITEM_DOWN_MANAGER:// 下载管理
					break;
				case ITEM_FULLSCREEN:// 全屏
					break;
				case ITEM_MORE:// 翻页
					if (isMore) {
						menuGrid.setAdapter(getMenuAdapter(menu_name_array2, menu_image_array2));
						isMore = false;
					} else {// 首页
						menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
						isMore = true;
					}
					menuGrid.invalidate();// 更新menu
					menuGrid.setSelection(ITEM_MORE);
					break;
				}
			}
		});
		// ---------------------------------------------
		if (menuDialog == null) {
			menuDialog = new AlertDialog.Builder(bbkAct).setView(menuView).show();
		} else {
			menuDialog.show();
		}
		// ---------------------------------------------
	}

	private SimpleAdapter getMenuAdapter(String[] menuNameArray, int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(bbkAct, data, R.layout.menu_item, new String[] { "itemImage", "itemText" }, new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
	}

	// public boolean onMenuOpened(int featureId, Menu menu) {
	// if (menuDialog == null) {
	// menuDialog = new AlertDialog.Builder(bbkAct).setView(menuView).show();
	// } else {
	// menuDialog.show();
	// }
	// return false;// 返回为true 则显示系统menu
	// }

}
