package bbk.hrd.abc;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class BBKScreen {

	// ====================================================================================
	// ####################################################################################
	// ##############################�����������##########################################
	// ####################################################################################
	// ====================================================================================
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; // ��Ҫ�Լ������־

	public static void BBKActivitySet_Hide_Sys_Title(Activity act) {
		// --------------------ȫ������--------------------------------
		// �������������˵�����ȫ�����У���ȥ��ص�ͼ���һ�����β��֣�״̬�����֣�
		int flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		int mask = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		act.getWindow().setFlags(flags, mask);// ϵͳ������
		// --------------��ȥ������--------------------------------------
	}
	
	public static void BBKActivitySet_Hide_Self_Title(Activity act) {
		// --------------��ȥ������--------------------------------------
		act.requestWindowFeature(Window.FEATURE_NO_TITLE);// �����Լ�������
		act.requestWindowFeature(8); // ȫ�������
		// --------------��ȥ������--------------------------------------
	}
	
	public static void BBKActivitySet_Hide_Home_Button(Activity act) {//����Home����
		// --------------����Home����--------------------------------------
		act.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED,FLAG_HOMEKEY_DISPATCHED);
		//��ͬ���ͻ���������֮����
		// --------------��ȥ������--------------------------------------
	}

	public static void BBKActivitySet_Show_Sys_Menu(Activity act) {// ǿ����ʾMenu���ⰴ����
		// --------------ǿ����ʾMenu���ⰴ��----------------------------
		// int flags2 = WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY;
		// int mask2 =WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY;
		// getWindow().setFlags(flags2, mask2);
		act.getWindow().setFlags(0x08000000, 0x08000000);
		//�������ⰴ��������
		// --------------ǿ����ʾMenu���ⰴ��----------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################��������###############################################
	// ####################################################################################
	// ====================================================================================
	public static boolean onKeyDown(String PageName, int keyCode, KeyEvent event) {
		// ------------------------------------------------------------------
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// if (BBKSoft.BBKLayBack()) {
			// return true;
			// }
		}
		// ------------------------------------------------------------------
		// TabHost�а�back�����˳����򣬶�����С������Ľ���취
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// ------------------------------------------------------------------
			// Intent i = new Intent(Intent.ACTION_MAIN);
			// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// i.addCategory(Intent.CATEGORY_HOME);
			// startActivity(i);
			// ------------------------------------------------------------------
		}
		// ------------------------------------------------------------------
		// return onKeyDown(keyCode, event);
		// ====================================================================================
		// ����home���ͱ�ļ���һ��
		// public void onAttachedToWindow() {
		// this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		// super.onAttachedToWindow();
		// }
		// ====================================================================================
		// ��������ʵ�尴��
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			return true;
		case KeyEvent.KEYCODE_BACK:
			return true;
		case KeyEvent.KEYCODE_CALL:
			return true;
		case KeyEvent.KEYCODE_SYM:
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			return true;
		case KeyEvent.KEYCODE_STAR:
			return true;
		}
		// ====================================================================================
		return true;
		// ------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################�˵�����###############################################
	// ####################################################################################
	// ====================================================================================
	// public static boolean onCreateOptionsMenu(Menu menu) {
	// // ---------------------------------------------
	// BBKSoft.myMenu.onCreateMenu(this, menu);
	// // BBKSoft.myMenux.onCreateMenu(this, menu);
	// return super.onCreateOptionsMenu(menu);
	// // ---------------------------------------------
	// // super.onPrepareOptionsMenu(menu);
	// // menu.add("menu");// ���봴��һ��
	// // myMenu.onCreateMenu(this, menu);
	// // return super.onCreateOptionsMenu(menu);
	// // ---------------------------------------------
	// }
	//
	// public static boolean onOptionsItemSelected(MenuItem item) {
	// // ---------------------------------------------
	// BBKSoft.myMenu.onItemSelected(item);
	// return super.onOptionsItemSelected(item);
	// // ---------------------------------------------
	// }

	// ====================================================================================
	// ####################################################################################
	// ##########################�豸���÷����仯ʱ�ص�#####################################
	// ####################################################################################
	// ====================================================================================
	public static int ScreenOrient = 1, HardKeyBoard = 1;

	// ��ܻص����� onConfigurationChanged ���� android.content.res.Configuration ����
	// ���� newConfig - ���豸���䱸��
	// ���豸������Ϣ�иĶ���������Ļ����ĸı䣬ʵ����̵��ƿ�����ϵȣ�ʱ�����������ʱ�� Activity �������У�ϵͳ��������������
	// ע�⣺onConfigurationChangedֻ����ӦӦ�ó�����AnroidMainifest.xml��ͨ��android:configChanges="��������"ָ�����������͵ĸĶ���
	// �������������õĸ��ģ���ϵͳ�������ٵ�ǰ��Ļ�� ActivityȻ�����¿���һ���µ���Ӧ��Ļ�ı�� Activity ʵ����
	// @Override
	public static void onConfigurationChanged(Activity act, Configuration newConfig) {
		// -----------------------------------------------------------------------
		// һ��Ҫ�ȵ��ø����ͬ���������ÿ��Ĭ�Ϻ����ȴ���
		// �������һ������ʡȥ������������android.app.SuperNotCalledException �쳣��
		// super.onConfigurationChanged(newConfig);
		act.onConfigurationChanged(newConfig);
		// -----------------------------------------------------------------------
		// �����Ļ�ķ�����������
		// ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		// ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		ScreenOrient = act.getResources().getConfiguration().orientation;
		if (ScreenOrient == Configuration.ORIENTATION_PORTRAIT) {// ����(portrait����)=1
		} else if (ScreenOrient == Configuration.ORIENTATION_LANDSCAPE) {// ����(landscape�羰)=2
		}
		// -----------------------------------------------------------------------
		// ���ʵ����̵�״̬���Ƴ����ߺ���
		HardKeyBoard = newConfig.hardKeyboardHidden;
		if (HardKeyBoard == Configuration.HARDKEYBOARDHIDDEN_NO) {// ʵ����̴����Ƴ�״̬=1
		} else if (HardKeyBoard == Configuration.HARDKEYBOARDHIDDEN_YES) {// ʵ����̴��ں���״̬=2
		}
		// -----------------------------------------------------------------------
		ScreenOrient = act.getResources().getConfiguration().orientation;
		// BBKDebug.d(ScreenOrient, false);
		// BBKSoft.BBKScreenOrientRun(ScreenOrient);
		// -----------------------------------------------------------------------
	}

	// ========================================================================
	// ========================================================================
	// ========================================================================
	// ========================================================================
	// ========================================================================
	/**
	 * ��ȡ�������ĸ߶�
	 * 
	 * @param activity
	 * @return
	 */
	public static int getTitleHeight(Activity activity) {
		Rect rect = new Rect();
		Window window = activity.getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top;
		int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		int titleBarHeight = contentViewTop - statusBarHeight;
		return titleBarHeight;
	}

	/**
	 * 
	 * ��ȡ״̬���߶�
	 * 
	 * @param activity
	 * @return
	 */
	public static int getStateHeight(Activity activity) {
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		return rect.top;
	}

	/**
	 * ��ȡ��Ļ���
	 * 
	 * @param activity
	 * @return int[0] ��int[1]��
	 */
	public static int[] getScreenWidthAndSizeInPx(Activity activity) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int[] size = new int[2];
		size[0] = displayMetrics.widthPixels;
		size[1] = displayMetrics.heightPixels;
		return size;
	}
	// ========================================================================
	// ========================================================================
	// ========================================================================
	// ========================================================================
	// ========================================================================
}
