package bbk.sys.abc;

import java.io.DataOutputStream;

import android.os.SystemClock;

public class BBKRoot {

	// ====================================================================================
	/**
	 * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
	 * 
	 * @return 应用程序是/否获取Root权限
	 */
	// ====================================================================================
	// upgradeRootPermission(getPackageCodePath());
	// ====================================================================================
	public boolean upgradeRootPermission(String pkgCodePath) {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su"); // 切换到root帐号
			String cmd = "chmod 777 " + pkgCodePath;
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		return true;
	}

	// ====================================================================================
	// public static void RootAsk() {
	// // upgradeRootPermission(getPackageCodePath());
	// }

	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	public void BBKSysTimeGpsSync(long curMs, boolean gpslock) {
		// ------------------------------------
		boolean isSuc;
		long sysMs, exsMs;
		// ------------------------------------
		// curMs = BBKSoft.myGps.gm.g.t.getTime();
		sysMs = System.currentTimeMillis();
		exsMs = sysMs - curMs;
		exsMs = (long) (exsMs / 1000f);
		// ------------------------------------
		String str = "GPS时间校准" + "\r\n" + "\r\n";
		str += "卫星时间：" + curMs + "\r\n";
		str += "系统时间：" + sysMs + "\r\n";
		if (exsMs > 0) {
			str += "系统走快：" + Math.abs(exsMs) + "秒\r\n";
		} else {
			str += "系统走慢：" + Math.abs(exsMs) + "秒\r\n";
		}
		// -------------------------------------------------------------
		String back = "";
		// if (BBKSoft.myGps.gm.g.Y) {
		if (gpslock) {
			// -------------------------------------------------------------
			isSuc = SystemClock.setCurrentTimeMillis(curMs);// 需要Root权限
			// -------------------------------------------------------------
			if (isSuc) {
				back = "GPS时间校准成功！";
			} else {
				back = "程序未能ROOT！";
			}
			// -------------------------------------------------------------
		} else {
			back = "GPS未能定位！";
		}
		// -------------------------------------------------------------
		str = "	" + back + "\r\n\r\n" + str;
		BBKMsgBox.MsgOK(str);
		// -------------------------------------------------------------
	}
	// ====================================================================================
}
