package bbk.abc.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import bbk.bbk.box.BBKSoft;
import bbk.hrd.abc.BBKScreen;

public class BBKMain extends Activity {

	public static BBKSoft mybbksoft = new BBKSoft();

	// ====================================================================================
	// ####################################################################################
	// ##############################������洴��##########################################
	// ####################################################################################
	// ====================================================================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// ----------------------------------------------------
		super.onCreate(savedInstanceState);
		// ----------------------------------------------------
		BBKScreen.BBKActivitySet(BBKMain.this);
		setContentView(R.layout.main);
		// ----------------------------------------------------
		mybbksoft.BBKInt(BBKMain.this);
		mybbksoft.BBKSoftInt();
		// ----------------------------------------------------
		BBKScreen.BBKActivityMenu(BBKMain.this);// ǿ����ʾMenu���ⰴ����
		// ----------------------------------------------------
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.bbk, menu);
	// return true;
	// }
	// ====================================================================================
	// ####################################################################################
	// #############################��������###############################################
	// ####################################################################################
	// ====================================================================================
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ------------------------------------------------------------------
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (BBKSoft.BBKLayBack()) {
				return true;
			}
		}
		// ------------------------------------------------------------------
		// TabHost�а�back�����˳����򣬶�����С������Ľ���취
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// ------------------------------------------------------------------
			Intent i = new Intent(Intent.ACTION_MAIN);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.addCategory(Intent.CATEGORY_HOME);
			startActivity(i);
			// ------------------------------------------------------------------
		}
		// ------------------------------------------------------------------
		return super.onKeyDown(keyCode, event);
		// ====================================================================================
		// ����home���ͱ�ļ���һ��
		// public void onAttachedToWindow() {
		// this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		// super.onAttachedToWindow();
		// }
		// ====================================================================================
		// ��������ʵ�尴��
		// switch (keyCode) {
		// case KeyEvent.KEYCODE_HOME:
		// return true;
		// case KeyEvent.KEYCODE_BACK:
		// return true;
		// case KeyEvent.KEYCODE_CALL:
		// return true;
		// case KeyEvent.KEYCODE_SYM:
		// return true;
		// case KeyEvent.KEYCODE_VOLUME_DOWN:
		// return true;
		// case KeyEvent.KEYCODE_VOLUME_UP:
		// return true;
		// case KeyEvent.KEYCODE_STAR:
		// return true;
		// }
		// ====================================================================================
	}

	// ====================================================================================
	// ####################################################################################
	// #############################�˵�����###############################################
	// ####################################################################################
	// ====================================================================================
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// ---------------------------------------------
		BBKSoft.myMenu.onCreateMenu(this, menu);
		// BBKSoft.myMenux.onCreateMenu(this, menu);
		return super.onCreateOptionsMenu(menu);
		// ---------------------------------------------
		// super.onPrepareOptionsMenu(menu);
		// menu.add("menu");// ���봴��һ��
		// myMenu.onCreateMenu(this, menu);
		// return super.onCreateOptionsMenu(menu);
		// ---------------------------------------------
	}

	@Override
	public void openOptionsMenu() {
		super.openOptionsMenu();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// ---------------------------------------------
		BBKSoft.myMenu.onItemSelected(item);
		return super.onOptionsItemSelected(item);
		// ---------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##########################�豸���÷����仯ʱ�ص�#####################################
	// ####################################################################################
	// ====================================================================================
	public int ScreenOrient = 1, HardKeyBoard = 1;

	// ��ܻص����� onConfigurationChanged ���� android.content.res.Configuration ����
	// ���� newConfig - ���豸���䱸��
	// ���豸������Ϣ�иĶ���������Ļ����ĸı䣬ʵ����̵��ƿ�����ϵȣ�ʱ�����������ʱ�� Activity �������У�ϵͳ��������������
	// ע�⣺onConfigurationChangedֻ����ӦӦ�ó�����AnroidMainifest.xml��ͨ��android:configChanges="��������"ָ�����������͵ĸĶ���
	// �������������õĸ��ģ���ϵͳ�������ٵ�ǰ��Ļ�� ActivityȻ�����¿���һ���µ���Ӧ��Ļ�ı�� Activity ʵ����
	public void onConfigurationChanged(Configuration newConfig) {
		// -----------------------------------------------------------------------
		// һ��Ҫ�ȵ��ø����ͬ���������ÿ��Ĭ�Ϻ����ȴ���
		// �������һ������ʡȥ������������android.app.SuperNotCalledException �쳣��
		super.onConfigurationChanged(newConfig);
		// -----------------------------------------------------------------------
		// �����Ļ�ķ�����������
		// ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		// ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		ScreenOrient = this.getResources().getConfiguration().orientation;
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
		ScreenOrient = this.getResources().getConfiguration().orientation;
		// BBKDebug.d(ScreenOrient, false);
		BBKSoft.BBKScreenOrientRun(ScreenOrient);
		// -----------------------------------------------------------------------
	}
}
