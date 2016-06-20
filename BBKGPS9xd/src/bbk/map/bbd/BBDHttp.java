package bbk.map.bbd;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import bbk.map.abc.BBKMap;
import bbk.map.bbd.BBD.Pt;
import bbk.sys.abc.BBKSYS;
import bbk.zzz.debug.BBKDebug;

public class BBDHttp {

	// ============ 下载函数 ===========================================
	private String tempPath = "";
	private BBDWrite myBBDW = new BBDWrite();

	// ============ 下载函数 ===========================================

	public void BBDHInt(String path) {
		// ---------------------------------------------------
		tempPath = path;
		// ---------------------------------------------------
		for (int i = 0; i < DownFile.length; i++) {
			DownFile[i] = new DownFile_type();
		}
		// ---------------------------------------------------
		TimerStart();
		// ---------------------------------------------------
	}

	public static void BBDHProxy(String proxyIP, int proxySort, int downTimeOut) {
		httpProxy = proxyIP;
		httpSort = proxySort;
		httpTime = downTimeOut;
	}

	public void BBDExit() {
		// ---------------------------------------------------
		TimerStop();
		// ---------------------------------------------------
	}

	public void MapTempFileDown(Pt p) {
		// -------------------------------------------------------
		if (file_is_down(p)) {
			return;
		} else {
			file_ad_down(p);
		}
		// -------------------------------------------------------
		return;
		// -------------------------------------------------------
	}

	// ============ 下载心跳 ===========================================
	private Timer timer = new Timer();
	private NewTask myTask = new NewTask();

	private void TimerStart() {
		timer.schedule(myTask, 0, 200);
	}

	private void TimerStop() {
		timer.cancel();// 使用这个方法退出任务
	}

	private class NewTask extends TimerTask {
		public void run() {
			DownFile_TimerRun();
		}
	}

	// --------------下载心跳----------------------------------------
	private void DownFile_TimerRun() {
		// -------------------------------------------------------
		for (int i = 0; i < DownFile.length; i++) {
			// ---------------------------------------------------
			if (DownFile[i].st == 1) {
				// -----------------------------------------------
				DownFile[i].st = 2;
				final int id = i;
				new Thread() {
					public void run() {
						down_file(id);
					}
				}.start();
				// -----------------------------------------------
			} else if (DownFile[i].st == 3) {
				// -----------------------------------------------
				file_down_write(i);
				file_down_end(i);
				// -----------------------------------------------
			}
			// ---------------------------------------------------
		}
		// -------------------------------------------------------
	}

	// ============ 下载心跳 ===========================================

	// ============ 下载基类===========================================
	private class DownFile_type {
		Pt p;
		int st;// 0 空闲;1需要启动;2正在下载;3等待处理;8;9出错
		byte fd[];// Sm As MemoryStream

		DownFile_type() {
			st = 0;
			p = new Pt(0, 0, 0, 0);
			fd = null;
		}

		void set(Pt pp) {
			p = new Pt(pp.x, pp.y, pp.z, pp.t);
			fd = null;
			st = 1;
		}
	}

	// ============ 下载基类===========================================

	// ============ 下载函数 ===========================================
	private int fileSize = 0;
	private int mapdownpics = 0;
	private int mapdownpicn = 0;
	private long downLoadFileSize = 0;
	private double downFileSize = 0.0;
	private DownFile_type DownFile[] = new DownFile_type[40];

	// ---------------------------------------------------------------

	// ============ 下载函数 ===========================================
	private boolean file_is_down(Pt p) {
		// ----------------------------------------------------
		for (int i = 0; i < DownFile.length; i++) {
			if (p.x == DownFile[i].p.x && p.y == DownFile[i].p.y && p.z == DownFile[i].p.z && p.t == DownFile[i].p.t) {
				return true;
			}
		}
		// ----------------------------------------------------
		return false;
		// ----------------------------------------------------
	}

	private boolean file_ad_down(Pt p) {
		// ----------------------------------------------------
		for (int i = 0; i < DownFile.length; i++) {
			if (DownFile[i].st == 0) {
				DownFile[i].set(p);
				DownFile[i].st = 1;
				mapdownpics++;
				return true;
			}
		}
		return false;
		// ----------------------------------------------------
	}

	private void file_down_ok(int id, byte fd[]) {
		// ----------------------------------------------------
		DownFile[id].fd = fd;
		DownFile[id].st = 3;
		// ----------------------------------------------------
	}

	private void file_down_out(int id) {
		// ----------------------------------------------------
		BBKDebug.d("TimeOut=" + DownFile[id].p.nme, false, false);
		file_down_end(id);
		// ----------------------------------------------------
	}

	private void file_down_end(int id) {
		// ----------------------------------------------------
		DownFile[id].st = 0;
		DownFile[id].fd = null;
		// ----------------------------------------------------
		mapdownpicn++;
		// ----------------------------------------------------
		MapDownInfoSets();
		// ----------------------------------------------------
	}

	private void file_down_write(int id) {
		// ----------------------------------------------------
		myBBDW.WritePicBBD(tempPath + DownFile[id].p.bbd, DownFile[id].fd, DownFile[id].p.f);
		// ----------------------------------------------------
	}

	// ============ down_file =========================================
	private static String httpProxy = "";
	private static int httpSort = 0;
	private static int httpTime = 300;

	private void down_file(int id) {
		// -------------------------------------------------------------------
		try {
			String url = BBDHead.maphttphead + BBDHead.mappicsurls[DownFile[id].p.t] + DownFile[id].p.url;
			// -------------------------------------------------------------------
			BBKDebug.d(url, false, false);
			URLConnection conn;
			URL myURL = new URL(url);
			// -------------------------------------------------------------------
			if (httpProxy.length() > 10 && httpSort > 0) {
				// ---------------------------------------------------------------
				SocketAddress addr = new InetSocketAddress(httpProxy, httpSort);
				Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);
				conn = myURL.openConnection(typeProxy);
				// ---------------------------------------------------------------
			} else {
				// ---------------------------------------------------------------
				conn = myURL.openConnection();
				// ---------------------------------------------------------------
			}
			// -------------------------------------------------------------------
			if (conn == null) {
				BBKDebug.d("down_file conn == null", false, false);
				return;
			}
			// -------------------------------------------------------------------
			conn.setConnectTimeout(httpTime * 1000);
			conn.setDefaultUseCaches(true);
			conn.setReadTimeout(httpTime * 1000);
			conn.setDoInput(true);
			conn.connect();
			// -------------------------------------------------------------------
			InputStream is = conn.getInputStream();
			fileSize = conn.getContentLength();
			// -------------------------------------------------------------------
			if (fileSize <= 0) {
				file_down_out(id);
				throw new RuntimeException("size is null");
			}
			if (is == null) {
				file_down_out(id);
				throw new RuntimeException("stream is null");
			}
			// ------- 把数据存入路径+文件名 ---------------------------------------
			byte pic[] = new byte[fileSize];
			int s = 0, l = 0;
			byte buf[] = new byte[1024];
			do // 循环读取
			{
				l = is.read(buf);
				if (l == -1) {
					break;
				}
				try {
					System.arraycopy(buf, 0, pic, s, l);
				} catch (Exception e) {
				}
				s += l;
				downLoadFileSize += l;
			} while (true);
			// -------------------------------------------------------------------
			is.close();
			// -------------------------------------------------------------------
			file_down_ok(id, pic);
			// -------------------------------------------------------------------
		} catch (IOException e) {
			// -------------------------------------------------------------------
			BBKDebug.d("BBDHttp.down_file(" + id + ")=" + e.toString(), false, false);
			file_down_out(id);
			// -------------------------------------------------------------------
		}
		// -------------------------------------------------------------------
	}

	// ============ 下载统计信息 ===========================================
	private String DownInfos = "";// 下载统计信息

	private void MapDownInfoSets() {
		// -------------------------------------------------------------------
		downFileSize = BBKSYS.getDouble(downLoadFileSize / 1048576f, 3);
		// -------------------------------------------------------------------
		DownInfos = "d=" + downFileSize + " mb ";
		DownInfos += mapdownpics + "/" + (int) (mapdownpics - mapdownpicn);
		// ----------------------------------------------------
		BBKMap.DownNews = true;
		BBKMap.DownInfos = DownInfos;
		BBKMap.DownLasts = mapdownpics - mapdownpicn;
		// -------------------------------------------------------------------
	}

	// ============ 下载统计信息 ===========================================

}
