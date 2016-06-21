package bbk.map.bbd;

public class BBDHead {
	// =====================================================================================
	public static final String SatVs = "500";
	public static final String MapVs = "500000000";
	// ------------------------------------------------------------------------------------
	public static final String maphttphead = "http://mt0.google.cn/vt";
	public static final String mappicsurls[] = { "/lyrs=m@" + MapVs + "&hl=zh-CN&s=Ga&", "/lyrs=s@" + SatVs + "&s=Gali&", "/lyrs=t@" + MapVs + ",r@" + MapVs + "&hl=zh-CN&s=G&", "/imgtp=png32&lyrs=h@" + MapVs + "&hl=zh-CN&s=G&", "?hl=zh-CN&lyrs=m@" + MapVs + ",traffic|seconds_into_week:-1&" };
	// =====================================================================================

	// http://mt0.google.cn/vt/lyrs=m@500000000&hl=zh-CN&gl=cn&x=6745&y=3102&z=13&s=Galil
}
