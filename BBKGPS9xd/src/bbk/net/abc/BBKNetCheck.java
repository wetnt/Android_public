package bbk.net.abc;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

public class BBKNetCheck {

	// ===============检测网络是否存在======================================================
	/**
	 * 检测网络是否存在
	 */
	public static boolean HttpTest(Context mActivity) {
		if (!isNetworkAvailable(mActivity)) {
			showToast(mActivity);
			return false;
		}
		return true;
	}

	public static void showToast(Context mActivity) {
		// ---------------------------------------------
		Vibrator mVibrator01 = (Vibrator) mActivity.getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		mVibrator01.vibrate(new long[] { 100, 10, 100, 1000 }, -1);
		// ---------------------------------------------
		TextView tv = new TextView(mActivity);
		// ---------------------------------------------
		tv.setText(" - 请开启网路连接！ - ");
		// ---------------------------------------------
		tv.setBackgroundColor(Color.RED);
		tv.setTextSize(24);
		// ---------------------------------------------
		Toast toast = new Toast(mActivity);
		toast.setView(tv);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
		// ---------------------------------------------
	}

	public static boolean isNetworkAvailable(Context mActivity) {
		// ---------------------------------------------------------------
		Context context = mActivity.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// ---------------------------------------------------------------
		if (connectivity == null) {
			return false;
		} else {
			// ---------------------------------------------------------------
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
			// ---------------------------------------------------------------
		}
		// ---------------------------------------------------------------
		return false;
		// ---------------------------------------------------------------
	}

	// ====================================================================================

}