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
	// ##############################程序界面创建##########################################
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
		BBKScreen.BBKActivityMenu(BBKMain.this);// 强制显示Menu虚拟按键！
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
	// #############################按键设置###############################################
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
		// TabHost中按back键不退出程序，而是最小化程序的解决办法
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
		// 屏蔽home键和别的键不一样
		// public void onAttachedToWindow() {
		// this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		// super.onAttachedToWindow();
		// }
		// ====================================================================================
		// 屏蔽其他实体按键
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
	// #############################菜单设置###############################################
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
		// menu.add("menu");// 必须创建一项
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
	// ##########################设备配置发生变化时回调#####################################
	// ####################################################################################
	// ====================================================================================
	public int ScreenOrient = 1, HardKeyBoard = 1;

	// 框架回调函数 onConfigurationChanged 出自 android.content.res.Configuration 包。
	// 参数 newConfig - 新设备的配备。
	// 当设备配置信息有改动（比如屏幕方向的改变，实体键盘的推开或合上等）时，并且如果此时有 Activity 正在运行，系统会调用这个函数。
	// 注意：onConfigurationChanged只会响应应用程序在AnroidMainifest.xml中通过android:configChanges="配置类型"指定的配置类型的改动；
	// 而对于其他配置的更改，则系统会先销毁当前屏幕的 Activity然后重新开启一个新的适应屏幕改变的 Activity 实例。
	public void onConfigurationChanged(Configuration newConfig) {
		// -----------------------------------------------------------------------
		// 一定要先调用父类的同名函数，让框架默认函数先处理
		// 下面这句一定不能省去，否则将引发：android.app.SuperNotCalledException 异常。
		super.onConfigurationChanged(newConfig);
		// -----------------------------------------------------------------------
		// 检测屏幕的方向：纵向或横向
		// ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		// ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		ScreenOrient = this.getResources().getConfiguration().orientation;
		if (ScreenOrient == Configuration.ORIENTATION_PORTRAIT) {// 竖屏(portrait人像)=1
		} else if (ScreenOrient == Configuration.ORIENTATION_LANDSCAPE) {// 横屏(landscape风景)=2
		}
		// -----------------------------------------------------------------------
		// 检测实体键盘的状态：推出或者合上
		HardKeyBoard = newConfig.hardKeyboardHidden;
		if (HardKeyBoard == Configuration.HARDKEYBOARDHIDDEN_NO) {// 实体键盘处于推出状态=1
		} else if (HardKeyBoard == Configuration.HARDKEYBOARDHIDDEN_YES) {// 实体键盘处于合上状态=2
		}
		// -----------------------------------------------------------------------
		ScreenOrient = this.getResources().getConfiguration().orientation;
		// BBKDebug.d(ScreenOrient, false);
		BBKSoft.BBKScreenOrientRun(ScreenOrient);
		// -----------------------------------------------------------------------
	}
}
