package bbk.map.dat;

public class BBKReg {

	// ====================================================================================
	// ####################################################################################
	// ##############################参考椭球体############################################
	// ####################################################################################
	// ====================================================================================
	// http://baike.so.com/doc/1040151.html
	// 参考椭球体，椭圆绕其短轴旋转所成的形体，并近似于地球大地水准面。
	// 大地水准面的形状即用相对于参考椭球体的偏离来表示。
	// 通常所说地球的形状和大小，实际上就是以参考椭球体的半长径、半短径和扁率来表示。
	// 1975年国际大地测量与地球物理联合会推荐的数据为：半长径6378140米，半短径6356755米，扁率1∶298.257。
	// 1980年国际大地测量与地球物理联合会推荐数据为：长半轴a=6378137，短半轴b=6356752，扁率α=（a-b）/a=1:298.257。
	// 1978年中国推测的数据为：a=6378143，b=6356758，α=1:298.255。
	// 测量中取大小和形状与大地体最为密合的旋转椭球体代替大地体，定位後的旋转椭球体，成为参考椭球体。
	// 一个国家和地区为处理测量成果而采用的一种与地球大小、形状最接近并具有一定参数的地球椭球。
	// 参考椭球体的元素 　　
	// 年代|国__家|推算者|长半轴a|短半轴b|扁率α=（a-b）/a
	// 1800|德意志|徳布尔|6375653|6356567|1:334
	// 1841|德意志|贝塞尔|6377397|6356079|1:299.2
	// 1880|不列颠|克拉克|6378249|6356515|1:293.5
	// 1909|美利坚|海福特|6378383|6356912|1:297.0
	// 1940|苏维埃|克拉索|6378245|6356863|1:298.3
	// 1975|国大地|地球理|6378140|6356755|1:298.257
	// 1978|大中华|大中华|6378143|6356758|1:298.255
	// 1980|美利坚|WGS-84|6378137|6356752|1:298.257
	// ====================================================================================
	// ####################################################################################
	// ##############################参考椭球体############################################
	// ####################################################################################
	// ====================================================================================

	private final static double mathpi = 3.14159265358979324;
	// Krasovsky 1940
	// a = 6378245.0, 1/f = 298.3
	// b = a * (1 - f)
	// ee = (a^2 - b^2) / a^2;
	private final static double earth_radius = 6378245.0;
	private final static double ee = 0.00669342162296594323;

	public static class RegWJ {
		public double w = 0;
		public double j = 0;

		public RegWJ(double ww, double jj) {
			w = ww;
			j = jj;
		}
	}

	// ====================================================================================
	// World Geodetic System ==> Mars Geodetic System
	public static RegWJ WJ_TtoF(double wgLat, double wgLon) {
		double mgLat = wgLat;
		double mgLon = wgLon;

		if (outOfChina(wgLat, wgLon)) {
			mgLat = wgLat;
			mgLon = wgLon;
			return new RegWJ(mgLat, mgLon);
		}

		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat / 180.0 * mathpi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((earth_radius * (1 - ee)) / (magic * sqrtMagic) * mathpi);
		dLon = (dLon * 180.0) / (earth_radius / sqrtMagic * Math.cos(radLat) * mathpi);
		mgLat = wgLat + dLat;
		mgLon = wgLon + dLon;
		return new RegWJ(mgLat, mgLon);
	}

	static boolean outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * mathpi) + 20.0 * Math.sin(2.0 * x * mathpi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * mathpi) + 40.0 * Math.sin(y / 3.0 * mathpi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * mathpi) + 320 * Math.sin(y * mathpi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * mathpi) + 20.0 * Math.sin(2.0 * x * mathpi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * mathpi) + 40.0 * Math.sin(x / 3.0 * mathpi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * mathpi) + 300.0 * Math.sin(x / 30.0 * mathpi)) * 2.0 / 3.0;
		return ret;
	}

	// ====================================================================================
	// World Geodetic System ==> Mars Geodetic System
	public static RegWJ WJ_FtoT(double wf, double jf) {
		// ---------------------------------------------
		RegWJ f = WJ_TtoF(wf, jf);
		double W = wf - (f.w - wf);
		double J = jf - (f.j - jf);
		return new RegWJ(W, J);
	}

	// ====================================================================================
	public static String Str_WJ_TF(String ask) {
		// ----------------------------------------------------
		int a = ask.indexOf(",");
		// -------------------------------------------------
		if (a == -1) {
			return ask;
		}
		// ----------------------------------------------------
		int b = ask.length();
		double wf = 0, jf = 0;
		String wt = "", jt = "";
		String reg = ask;
		// ----------------------------------------------------
		wt = ask.substring(0, a - 1);
		jt = ask.substring(a + 1, b - 1);
		// ----------------------------------------------------
		wf = Double.valueOf(wt);
		jf = Double.valueOf(jt);
		// =========================================================
		RegWJ wj = WJ_TtoF(wf, jf);
		wf = getDouble(wj.w);
		jf = getDouble(wj.j);
		// ----------------------------------------------------
		reg = wf + "," + jf;
		// ----------------------------------------------------
		return reg;
		// ----------------------------------------------------
	}

	// ====================================================================================
	final static int Decimals = 1000000;

	public static double getDouble(double a) {
		// -----------------------------------
		double a2 = a * Decimals;
		int b = (int) a2;
		double c = (double) b;
		double d = c / Decimals;
		// -----------------------------------
		return d;
		// -----------------------------------
	}
	// ====================================================================================

}
