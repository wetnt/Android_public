package bbk.sys.abc;

import java.io.DataOutputStream;

import android.os.SystemClock;

public class BBKRoot {

	// ====================================================================================
	/**
	 * Ӧ�ó������������ȡ RootȨ�ޣ��豸�������ƽ�(���ROOTȨ��)
	 * 
	 * @return Ӧ�ó�����/���ȡRootȨ��
	 */
	// ====================================================================================
	// upgradeRootPermission(getPackageCodePath());
	// ====================================================================================
	public boolean upgradeRootPermission(String pkgCodePath) {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su"); // �л���root�ʺ�
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
		String str = "GPSʱ��У׼" + "\r\n" + "\r\n";
		str += "����ʱ�䣺" + curMs + "\r\n";
		str += "ϵͳʱ�䣺" + sysMs + "\r\n";
		if (exsMs > 0) {
			str += "ϵͳ�߿죺" + Math.abs(exsMs) + "��\r\n";
		} else {
			str += "ϵͳ������" + Math.abs(exsMs) + "��\r\n";
		}
		// -------------------------------------------------------------
		String back = "";
		// if (BBKSoft.myGps.gm.g.Y) {
		if (gpslock) {
			// -------------------------------------------------------------
			isSuc = SystemClock.setCurrentTimeMillis(curMs);// ��ҪRootȨ��
			// -------------------------------------------------------------
			if (isSuc) {
				back = "GPSʱ��У׼�ɹ���";
			} else {
				back = "����δ��ROOT��";
			}
			// -------------------------------------------------------------
		} else {
			back = "GPSδ�ܶ�λ��";
		}
		// -------------------------------------------------------------
		str = "	" + back + "\r\n\r\n" + str;
		BBKMsgBox.MsgOK(str);
		// -------------------------------------------------------------
	}
	// ====================================================================================
}
