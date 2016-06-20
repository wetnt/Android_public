package bbk.map.uis;

import com.example.bbkgps9xd.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import bbk.bbk.box.BBKSoft;
import bbk.bbk.box.BBKAbout;
import bbk.map.gps.BBKGpsReport;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.sys.abc.BBKMsgBox;
import bbk.sys.abc.BBKSavePathSelect;
import bbk.sys.file.FileManager;
import bbk.zzz.debug.bd;

public class BBKButMenu {

	// -------------------------------------------------------------------------
	private Activity bbkAct;
	static int BtnDn = Color.MAGENTA;
	static int BtnUp = Color.TRANSPARENT;
	public static final int FILE_RESULT_CODE = 1;

	// -------------------------------------------------------------------------

	// ====================================================================================
	public Menu onCreateMenu(Context context, Menu menu) {
		// ------------------------------------------------------------------------------------------
		bbkAct = (Activity) context;
		// -------------------------------------------------------
		// menu.add�����Ĳ�����
		// ��һ��int���͵�group ID�����������������
		// �ڶ���int���͵�item ID���������������Ŀ��š�
		// ������int���͵�order ID������������ǲ˵������ʾ˳��
		// ���ĸ�String���͵�title��������ʾѡ������ʾ�����֡�
		// -------------------------------------------------------
		MenuItemAdd(0, menu, "�ղ�", R.drawable.menu_bookmark);
		MenuItemAdd(0, menu, "��ѯ", R.drawable.menu_search);
		MenuItemAdd(0, menu, "�켣", R.drawable.menu_refreshtimer);
		MenuItemAdd(0, menu, "�б�", R.drawable.menu_select_all);
		MenuItemAdd(0, menu, "�˳�", R.drawable.menu_quit);
		// ---------------------------------------------
		MenuItemAdd(1, menu, "GPS-����", R.drawable.menu_checkupdate);
		MenuItemAdd(1, menu, "GPS-Уʱ", R.drawable.menu_refreshtimer);
		MenuItemAdd(1, menu, "�����Ϣ", R.drawable.menu_pagefind);
		// ---------------------------------------------
		MenuItemAdd(2, menu, "�л���ͼ", R.drawable.menu_syssettings);
		MenuItemAdd(2, menu, "ͼ�����", R.drawable.menu_delete);
		MenuItemAdd(2, menu, "��γ��λ", R.drawable.menu_outfullscreen);
		MenuItemAdd(2, menu, "�ֻ���", R.drawable.menu_edit);
		MenuItemAdd(2, menu, "��ѯ��ʷ", R.drawable.menu_attr);
		MenuItemAdd(2, menu, "������Դ", R.drawable.menu_open_in_newwindow);
		// ---------------------------------------------
		MenuItemAdd(3, menu, "��Ļ����", R.drawable.menu_day);
		MenuItemAdd(3, menu, "�洢����", R.drawable.menu_debug);
		MenuItemAdd(3, menu, "��ͼ����", R.drawable.menu_syssettings);
		MenuItemAdd(3, menu, "��������", R.drawable.menu_debug);
		// ---------------------------------------------
		MenuItemAdd(4, menu, "��ͼ���ǿ���", R.drawable.menu_downmanager);
		MenuItemAdd(4, menu, "��ͼ���ؿ���", R.drawable.menu_downmanager);
		MenuItemAdd(4, menu, "��ͼ���Ĳ���", R.drawable.menu_zoommode);
		MenuItemAdd(4, menu, "��ͼȫ������", R.drawable.menu_download_image);
		// ---------------------------------------------
		MenuItemAdd(5, menu, "BBKGPS.����", R.drawable.menu_input_pick_inputmethod);
		MenuItemAdd(5, menu, "BBKGPS.��ҳ", R.drawable.menu_debug);
		// ---------------------------------------------
		return menu;
		// ---------------------------------------------
	}

	public MenuItem onItemSelected(MenuItem item) {
		// ---------------------------------------------
		String s = item.getTitle().toString();
		// ===============================================================
		if (s == "�ղ�")
			BBKSoft.myFav.layshow(true);
		// ---------------------------------------------
		if (s == "��ѯ")
			BBKSoft.myAsk.layshow(true);
		if (s == "��ѯ��ʷ")
			BBKSoft.myAsH.layshow(true);
		// ---------------------------------------------
		if (s == "�켣")
			BBKSoft.myTrk.layshow(true);
		// ---------------------------------------------
		if (s == "�˳�")
			BBKGpsReport.AlertDialogExit(bbkAct);
		// ---------------------------------------------
		if (s == "�б�") {
			BBKSoft.myList.layshow(true);
		}
		// ---------------------------------------------
		if (s == "��γ��λ") {
			BBKSoft.myMove.layshow(true);
			BBKSoft.myMove.SetEditMapJW(BBKSoft.myMaps.mapPt.w, BBKSoft.myMaps.mapPt.j);
		}
		// ---------------------------------------------
		if (s == "�л���ͼ") {
			BBKMsgBox.tShow("�л���ͼ��");
			BBKSoft.myMaps.MapTypeSet();
			BBKSoft.MapFlash(true);
		}
		// ---------------------------------------------
		if (s == "ͼ�����") {
			BBKMsgBox.tShow("ͼ����գ�");
			BBKSoft.myLays.LayClears();
			BBKSoft.MapFlash(false);
		}
		// ---------------------------------------------
		if (s == "�ֻ���") {
			BBKMsgBox.tShow("�����Ļ������룡");
			BBKSoft.myMeasure.layshow(true);
			BBKSoft.myLays.laymes.line.add(new line_type());
			BBKSoft.myMeasure.MeasureLayShow = true;
			BBKSoft.myMeasure.LineMeasureShow();
		}
		// ---------------------------------------------
		if (s == "��Ļ����")
			BBKSoft.myLight.layshowe();
		// ---------------------------------------------
		if (s == "GPS-����")
			BBKSoft.GPSOpenCloseRun();
		if (s == "GPS-Уʱ")
			BBKGpsSYSTime();
		if (s == "�����Ϣ")
			BBKGpsReport.GpsInfoShow();
		// ---------------------------------------------
		if (s == "��ͼ����") {
			BBKSoft.myZoomSet.layshow(true);
			BBKSoft.myZoomSet.SetLoad();
		}
		// ---------------------------------------------
		if (s == "��������") {
			BBKSoft.myNetSet.layshow(true);
		}
		// ---------------------------------------------
		if (s == "�洢����") {
			BBKSavePathSelect.SetSoftPathEmpty(bbkAct);
			bd.d("�����¿��������", true, false);
		}
		// ---------------------------------------------
		if (s == "��ͼ���ǿ���") {
			BBKRegKey();
			BBKSoft.MapFlash(true);
		}
		// ---------------------------------------------
		if (s == "��ͼ���ؿ���") {
			BBKDownKey();
			BBKSoft.MapFlash(true);
		}
		// ---------------------------------------------
		if (s == "��ͼ���Ĳ���") {
			BBKMsgBox.tShow("��ͼ���Ŀն��������أ�");
			BBKSoft.myMaps.BBKDownCenterPic();// ����
		}
		// ---------------------------------------------
		if (s == "��ͼȫ������") {
			BBKSoft.myZoomSet.BBKScreenDown();
		}
		// =====================================================================
		if (s == "BBKGPS.����") {
			BBKAbout.SoftAboutShow();
		}
		// ---------------------------------------------
		if (s == "BBKGPS.��ҳ") {
			BBKMsgBox.tShow("���ڿ�����ҳ��");
			BBKWebHome();
		}
		// =====================================================================
		if (s == "������Դ") {
			// ---------------------------------------------
			Intent intent = new Intent(bbkAct, FileManager.class);
			bbkAct.startActivityForResult(intent, FILE_RESULT_CODE);
			// ---------------------------------------------
		}
		// =====================================================================
		return item;
		// ---------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	int menutid = 1, menuoid = 0;

	public int MenuItemAdd(int GroupId, Menu menu, String title, int ico) {
		menutid++;
		menuoid++;
		menu.add(GroupId, menutid, menuoid, title).setIcon(ico);
		return menutid;
	}

	// ====================================================================================
	// ====================================================================================

	// ====================================================================================
	public void BBKDownKey() {
		// --------------------------------------------
		if (!BBKSoft.SoftHttpCheck())
			return;
		// --------------------------------------------
		boolean ys = BBKSoft.myMaps.MapDownKeyClick(-1);
		BBKMsgBox.tShow("MapDown = " + ys);
		// ---------------------------------------------
	}

	// ====================================================================================
	public void BBKRegKey() {
		// --------------------------------------------
		BBKSoft.myMaps.MapRegKey = !BBKSoft.myMaps.MapRegKey;
		BBKMsgBox.tShow("BBKRegKey = " + BBKSoft.myMaps.MapRegKey);
		// ---------------------------------------------
	}

	// ====================================================================================
	public void BBKWebHome() {
		// --------------------------------------------
		if (!BBKSoft.SoftHttpCheck())
			return;
		// ---------------------------------------------
		String url = "http://www.bbkgps.com";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		bbkAct.startActivity(i);
		// ---------------------------------------------
	}

	// ====================================================================================
	// ====================================================================================
	public void BBKGpsSYSTime() {
		// ---------------------------------------------
		BBKSoft.myRoot.BBKSysTimeGpsSync(BBKSoft.myGps.gm.g.t.getTime(), BBKSoft.myGps.gm.g.Y);
		// ---------------------------------------------
	}
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================

}
