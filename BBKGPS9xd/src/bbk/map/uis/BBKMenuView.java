package bbk.map.uis;

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
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class BBKMenuView {

	// =============================================================
	private boolean isMore = false;// menu�˵���ҳ����
	AlertDialog menuDialog;// menu�˵�Dialog
	GridView menuGrid;
	View menuView;
	private Activity bbkAct;

	// =============================================================
	private final int ITEM_SEARCH = 0;// ����
	private final int ITEM_FILE_MANAGER = 1;// �ļ�����
	private final int ITEM_DOWN_MANAGER = 2;// ���ع���
	private final int ITEM_FULLSCREEN = 3;// ȫ��
	private final int ITEM_MORE = 11;// �˵�

	int[] menu_image_array = { R.drawable.menu_search, R.drawable.menu_filemanager, R.drawable.menu_downmanager, R.drawable.menu_fullscreen, R.drawable.menu_inputurl, R.drawable.menu_bookmark, R.drawable.menu_bookmark_sync_import, R.drawable.menu_sharepage, R.drawable.menu_quit, R.drawable.menu_nightmode, R.drawable.menu_refresh, R.drawable.menu_more };
	String[] menu_name_array = { "����", "�ļ�����", "���ع���", "ȫ��", "��ַ", "��ǩ", "������ǩ", "����ҳ��", "�˳�", "ҹ��ģʽ", "ˢ��", "����" };
	int[] menu_image_array2 = { R.drawable.menu_auto_landscape, R.drawable.menu_penselectmodel, R.drawable.menu_page_attr, R.drawable.menu_novel_mode, R.drawable.menu_page_updown, R.drawable.menu_checkupdate, R.drawable.menu_checknet, R.drawable.menu_refreshtimer, R.drawable.menu_syssettings, R.drawable.menu_help, R.drawable.menu_about, R.drawable.menu_return };
	String[] menu_name_array2 = { "�Զ�����", "��ѡģʽ", "�Ķ�ģʽ", "���ģʽ", "��ݷ�ҳ", "������", "�������", "��ʱˢ��", "����", "����", "����", "����" };

	public void onCreateMenu(Context context, Menu menu) {
		// ---------------------------------------------
		bbkAct = (Activity) context;
		// ---------------------------------------------
		menuView = View.inflate(bbkAct, R.layout.menu_gridview, null);
		// ---------------------------------------------
		// ����AlertDialog
		menuDialog = new AlertDialog.Builder(context).create();
		menuDialog.setView(menuView);
		menuDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU)// ��������
					dialog.dismiss();
				return false;
			}
		});
		// ---------------------------------------------
		menuGrid = (GridView) menuView.findViewById(R.id.gridview);
		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
		// ---------------------------------------------
		/** ����menuѡ�� **/
		menuGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				switch (arg2) {
				case ITEM_SEARCH:// ����
					break;
				case ITEM_FILE_MANAGER:// �ļ�����
					break;
				case ITEM_DOWN_MANAGER:// ���ع���
					break;
				case ITEM_FULLSCREEN:// ȫ��
					break;
				case ITEM_MORE:// ��ҳ
					if (isMore) {
						menuGrid.setAdapter(getMenuAdapter(menu_name_array2, menu_image_array2));
						isMore = false;
					} else {// ��ҳ
						menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
						isMore = true;
					}
					menuGrid.invalidate();// ����menu
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
	// return false;// ����Ϊtrue ����ʾϵͳmenu
	// }

}
