package bbk.map.uis;

import com.example.bbkgps9xd.R;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import bbk.uis.view.BBKLayView;

public class Main_ScreenLight extends BBKLayView {

	// http://blog.csdn.net/w8320273/article/details/7852752 SeekBar样式
	// ---------------------------------------------------------------
	ImageButton sPowerR, sPowerL, sPowerX;
	SeekBar bPower;
	int screenBright = 126;

	// ---------------------------------------------------------------

	public void LayInt(Context ctxt) {
		// ------------------------------------------------------------------------------------------
		LayoutInt(ctxt, R.layout.main_screenlight, 0, 1, 0, 0);
		// ------------------------------------------------------------------------------------------
		sPowerL = (ImageButton) BBKLay.findViewById(R.id.ScreenPowerL);
		sPowerR = (ImageButton) BBKLay.findViewById(R.id.ScreenPowerR);
		sPowerX = (ImageButton) BBKLay.findViewById(R.id.ScreenPowerX);
		bPower = (SeekBar) BBKLay.findViewById(R.id.BarScreenPowerV);
		// ------------------------------------------------------------------------------------------
		sPowerL.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				// ScreenBrightSet(0, -10);
				ScreenBrightSet(1, 0);
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		sPowerR.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				// ScreenBrightSet(0, 10);
				ScreenBrightSet(255, 0);
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		sPowerX.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// --------------------------------------------
				layshow(false);
				// --------------------------------------------
			}
		});
		// ------------------------------------------------------------------------------------------
		bPower.setOnSeekBarChangeListener(new OnSeekBarChangeListener() // 调音监听器
		{
			public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
				// --------------------------------------------
				ScreenBrightSet(progress, 0);
				// --------------------------------------------;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		// ------------------------------------------------------------------------------------------
		GetScreenMode(bbkAct);
		BBKLay.setVisibility(View.INVISIBLE);
		// ------------------------------------------------------------------------------------------
	}

	// ===========================================================================================

	public boolean ScreenModeYes = false;

	public void GetScreenMode(Activity activity) {
		// ----------------------------------------------------
		int x = getScreenMode(activity);
		// ----------------------------------------------------
		if (x == 1) {
			ScreenModeYes = false;
			screenBright = 126;
		} else if (x == 0) {
			ScreenModeYes = true;
			screenBright = getScreenBrightness(activity);
		}
		// ----------------------------------------------------
		bPower.setMax(255);
		bPower.setProgress(screenBright);
		// ----------------------------------------------------
	}

	// ===========================================================================================
	public void ScreenBrightSet(int value, int add) {
		// --------------------------------------------------------
		if (value != 0) {
			screenBright = value;
		} else if (add != 0) {
			// ---------------------------------------------------
			screenBright = screenBright + add;
			if (screenBright < 10)
				screenBright = 10;
			if (screenBright > 255)
				screenBright = 255;
			// ---------------------------------------------------
		}
		setScreenBrightness(bbkAct, screenBright);
		bPower.setProgress(screenBright);
		// --------------------------------------------------------
	}

	// ===========================================================================================
	// ===========================================================================================
	// ===========================================================================================
	/**
	 * 获得当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
	 */
	private int getScreenMode(Activity activity) {
		int screenMode = 0;
		try {
			screenMode = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (Exception localException) {

		}
		return screenMode;
	}

	/**
	 * 获得当前屏幕亮度值 0--255
	 */
	private int getScreenBrightness(Activity activity) {
		int screenBrightness = 255;
		try {
			screenBrightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception localException) {

		}
		return screenBrightness;
	}

	/**
	 * 设置当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
	 */
	@SuppressWarnings("unused")
	private void setScreenMode(Activity activity, int paramInt) {
		try {
			Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	/**
	 * 设置当前屏幕亮度值 0--255
	 */
	private void setScreenBrightness(Activity activity, int paramInt) {
		Window localWindow = activity.getWindow();
		WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
		float f = paramInt / 255.0F;
		localLayoutParams.screenBrightness = f;
		localWindow.setAttributes(localLayoutParams);
	}

	/**
	 * 保存当前的屏幕亮度值，并使之生效
	 */
	@SuppressWarnings("unused")
	private void saveScreenBrightness(Activity activity, int paramInt) {
		try {
			Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
	// ===========================================================================================

}
