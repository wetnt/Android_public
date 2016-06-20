package bbk.hrd.abc;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

public class BBKBody {
	// ---------------------------------------------------------------
	Activity bbkAct;

	public void BBKBodyInt(final Activity pthis) {
		// ----------------------------------------
		bbkAct = pthis;
		// ----------------------------------------
	}

	public static void BeepBeep(final Activity pthis) {// »úÉíÕð¶¯
		Vibrator mV = (Vibrator) pthis.getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		mV.vibrate(new long[] { 100, 10, 100, 1000 }, -1);
	}

}