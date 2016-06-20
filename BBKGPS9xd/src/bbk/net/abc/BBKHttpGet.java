package bbk.net.abc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

import bbk.zzz.debug.bd;

public class BBKHttpGet {

	// ===============================================================
	// http://blog.csdn.net/longerandlonger/article/details/7255357
	// Java异步HTTP请求
	// ===============================================================
	public static String NetProxy = "";
	public static int NetPorts = 0;
	public static int NetTimes = 300;

	public static String BBKHttpGetUrl(String strUrl, String strCode, boolean debugKey) {
		return BBKHttpGetUrlNew(strUrl, strCode, NetProxy, NetPorts, NetTimes, debugKey);
	}

	// ===============================================================
	// see81192154:
	// 此过程必须放在新线程中：
	// new Thread() {
	// public void run() {
	// // -----------------------------
	// // -----------------------------
	// }
	// }.start();
	private static String BBKHttpGetUrlNew(String strUrl, String strCode, String proxy, int sort, int timeout, boolean debugKey)
	// 此过程必须放在新线程中：see81192154:
	{
		try {
			// -------------------------------------------------------------------
			if (debugKey) {
				bd.d("BBKHttpGet.BBKHttpGetUrlNew.BackStrCode = " + strCode, false, false);
				bd.d("BBKHttpGet.BBKHttpGetUrlNew.proxy.sort.out = " + proxy + "," + sort + "," + timeout, false, false);
				bd.d("BBKHttpGet.BBKHttpGetUrlNew.strUrl == " + strUrl, false, false);
			}
			// -------------------------------------------------------------------
			URL url = new URL(strUrl);
			URLConnection conn;
			// -------------------------------------------------------------------
			if (proxy.length() > 10 && sort > 0) {
				// -------------------------------------------------------------------
				SocketAddress addr = new InetSocketAddress(proxy, sort);
				Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);
				conn = url.openConnection(typeProxy);
				// -------------------------------------------------------------------
			} else {
				// -------------------------------------------------------------------
				conn = url.openConnection();
				// -------------------------------------------------------------------
			}
			// -------------------------------------------------------------------
			if (conn == null) {
				if (debugKey)
					bd.d("BBKHttpGet.BBKHttpGetUrlNew.conn == null", false, false);
				return null;
			}
			// -------------------------------------------------------------------
			conn.setConnectTimeout(timeout * 1000);
			conn.setDefaultUseCaches(true);
			conn.setReadTimeout(timeout * 1000);
			conn.setDoInput(true);
			conn.connect(); // 此过程必须放在新线程中：see81192154:
			// -------------------------------------------------------------------
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			// -------------------------------------------------------------------
			String str = null, allstr = "";
			InputStream ins = conn.getInputStream();
			Reader inr = new InputStreamReader(ins, strCode);
			// -----------------------------------------------
			BufferedReader buf = new BufferedReader(inr);
			while ((str = buf.readLine()) != null) {
				allstr = allstr + str;
			}
			ins.close();
			inr.close();
			// -------------------------------------------------------------------
			conn = null;
			// -------------------------------------------------------------------
			if (debugKey)
				bd.d("BBKHttpGet.BBKHttpGetUrlNew.strResult =" + allstr, false, false);
			// -------------------------------------------------------------------
			return allstr;
			// -------------------------------------------------------------------
		} catch (IOException e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------------
		return "NetError";
		// -------------------------------------------------------------------
	}
}