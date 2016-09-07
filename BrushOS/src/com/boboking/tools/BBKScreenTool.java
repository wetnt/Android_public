package com.boboking.tools;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class BBKScreenTool {

	// ====================================================================================
	// ####################################################################################
	// ##############################程序界面设置##########################################
	// ####################################################################################
	// ====================================================================================
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; // 需要自己定义标志

	public static void BBKActivitySet_Hide_Sys_Title(Activity act) {
		// --------------------全屏运行--------------------------------
		// 消除标题栏、菜单栏，全屏运行，隐去电池等图标和一切修饰部分（状态栏部分）
		int flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		int mask = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		act.getWindow().setFlags(flags, mask);// 系统标题栏
		// --------------隐去标题栏--------------------------------------
	}

	public static void BBKActivitySet_Hide_Self_Title(Activity act) {
		// --------------隐去标题栏--------------------------------------
		act.requestWindowFeature(Window.FEATURE_NO_TITLE);// 程序自己标题栏
		act.requestWindowFeature(8); // 全屏抗锯齿
		// --------------隐去标题栏--------------------------------------
	}

	public static void BBKActivitySet_Hide_Home_Button(Activity act) {// 屏蔽Home按键
		// --------------屏蔽Home按键--------------------------------------
		act.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
		// 不同机型会死机，弃之不用
		// --------------隐去标题栏--------------------------------------
	}

	public static void BBKActivitySet_Show_Sys_Menu(Activity act) {// 强制显示Menu虚拟按键！
		// --------------强制显示Menu虚拟按键----------------------------
		// int flags2 = WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY;
		// int mask2 =WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY;
		// getWindow().setFlags(flags2, mask2);
		act.getWindow().setFlags(0x08000000, 0x08000000);
		// 引起虚拟按键不正常
		// --------------强制显示Menu虚拟按键----------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// #############################按键设置###############################################
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
		// TabHost中按back键不退出程序，而是最小化程序的解决办法
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
		// 屏蔽home键和别的键不一样
		// public void onAttachedToWindow() {
		// this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		// super.onAttachedToWindow();
		// }
		// ====================================================================================
		// 屏蔽其他实体按键
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
	// #############################菜单设置###############################################
	// ####################################################################################
	// ====================================================================================
	// public static boolean onCreateOptionsMenu(Menu menu) {
	// // ---------------------------------------------
	// BBKSoft.myMenu.onCreateMenu(this, menu);
	// // BBKSoft.myMenux.onCreateMenu(this, menu);
	// return super.onCreateOptionsMenu(menu);
	// // ---------------------------------------------
	// // super.onPrepareOptionsMenu(menu);
	// // menu.add("menu");// 必须创建一项
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
	// ##########################设备配置发生变化时回调#####################################
	// ####################################################################################
	// ====================================================================================
	public static int ScreenOrient = 1, HardKeyBoard = 1;

	// 框架回调函数 onConfigurationChanged 出自 android.content.res.Configuration 包。
	// 参数 newConfig - 新设备的配备。
	// 当设备配置信息有改动（比如屏幕方向的改变，实体键盘的推开或合上等）时，并且如果此时有 Activity 正在运行，系统会调用这个函数。
	// 注意：onConfigurationChanged只会响应应用程序在AnroidMainifest.xml中通过android:configChanges="配置类型"指定的配置类型的改动；
	// 而对于其他配置的更改，则系统会先销毁当前屏幕的 Activity然后重新开启一个新的适应屏幕改变的 Activity 实例。
	// @Override
	public static void onConfigurationChanged(Activity act, Configuration newConfig) {
		// -----------------------------------------------------------------------
		// 一定要先调用父类的同名函数，让框架默认函数先处理
		// 下面这句一定不能省去，否则将引发：android.app.SuperNotCalledException 异常。
		// super.onConfigurationChanged(newConfig);
		act.onConfigurationChanged(newConfig);
		// -----------------------------------------------------------------------
		// 检测屏幕的方向：纵向或横向
		// ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		// ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		ScreenOrient = act.getResources().getConfiguration().orientation;
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
	 * 获取标题栏的高度
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
	 * 获取状态栏高度
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
	 * 获取屏幕宽高
	 * 
	 * @param activity
	 * @return int[0] 宽，int[1]高
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
