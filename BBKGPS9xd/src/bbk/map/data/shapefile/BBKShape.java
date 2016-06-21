package bbk.map.data.shapefile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import bbk.zzz.debug.BBKDebug;

public class BBKShape {

	// http://en.wikipedia.org/wiki/Shapefile
	// http://zh.wikipedia.org/wiki/Shapefile
	// ============================================================================
	// ============================================================================
	public static ESRI_Lay ShapefileToESRI_Lay(InputStream shx, InputStream shp) {
		// ========================================================================
		ESRI_Lay elay = new ESRI_Lay();
		// ========================================================================
		int shapecount = ShapeShxCount(shx);
		BBKDebug.d("shapecount====" + shapecount, false, false);
		if (shapecount <= 0)
			return elay;
		// ========================================================================
		boolean oks = ShapebufferLoad(shp);
		if (!oks)
			return elay;
		// ========================================================================
		int shapetype = Shape_Header_Read();
		BBKDebug.d("shapetype=" + shapetype, false, false);
		// ========================================================================
		switch (shapetype) {
		// ----------------------------------------------
		case 1:// single point
			elay.GeomtryPois = Shape_Point_Read(shapecount);
			break;
		// ----------------------------------------------
		case 3:// Polyline layer
			elay.GeomtryLine = Shape_Polyline_Read(shapecount);
			break;
		// ----------------------------------------------
		case 5:// Polygon layer
			elay.GeomtryPoly = Shape_Polygon_Read(shapecount);
			break;
		// ----------------------------------------------
		case 8:// multi points layer
			break;
		// ----------------------------------------------
		case 11:// PointZ X, Y, Z, M
			elay.GeomtryPoiz = Shape_Point_Read_11(shapecount);
			break;
		// ----------------------------------------------
		case 13:// Polygon layer
			elay.GeomtryLine = Shape_Polyline_Read13(shapecount);
			break;
		// ----------------------------------------------
		default:
			break;
		// ----------------------------------------------
		}
		// ----------------------------------------------------------
		buffer = null;
		// ========================================================================
		return elay;
		// ========================================================================
	}

	// ============================================================================
	// ============================================================================

	public static class ESRI_Lay {
		public int n = 0;
		public ArrayList<ESRI_POINT> GeomtryPois = new ArrayList<ESRI_POINT>();
		public ArrayList<ESRI_POINTZ> GeomtryPoiz = new ArrayList<ESRI_POINTZ>();
		public ArrayList<ESRI_POLYLINE> GeomtryLine = new ArrayList<ESRI_POLYLINE>();
		public ArrayList<ESRI_POLYGON> GeomtryPoly = new ArrayList<ESRI_POLYGON>();
	}

	public static class ESRI_POINT {
		public int OID;
		public double x;
		public double y;
	}

	public static class ESRI_POINTZ {
		public int OID;
		public double x;
		public double y;
		public double z;
		public double m;
	}

	public static class ESRI_POLYLINE {
		public int OID;
		public double[] XPOINTS;
		public double[] YPOINTS;
	}

	public static class ESRI_POLYGON {
		public int OID;
		public double[] XPOINTS;
		public double[] YPOINTS;
	}

	public static class ESRI_MBR {
		// ---------------------------------------------
		// 最小外接矩形 (minimum bounding rectangle, MBR)
		public double left;
		public double bottom;
		public double right;
		public double top;
		// ---------------------------------------------
	}

	public static ESRI_MBR ESRI_MBR_Read() {// 获得图形外边框
		// ---------------------------------------------
		ESRI_MBR m = new ESRI_MBR();
		// ---------------------------------------------
		m.left = ReadDouble(0);
		m.bottom = ReadDouble(0);
		m.right = ReadDouble(0);
		m.top = ReadDouble(0);
		// ---------------------------------------------
		return m;
		// ---------------------------------------------
	}

	@SuppressWarnings("unused")
	public static int Shape_Header_Read() {
		// -------------------------------------------------------------
		// 主文件头100字节长。
		// 表1显示带有字节位置，值，类型和字节顺序的文件头中的域。
		// int head = 100;
		// -------------------------------------------------------------
		int fileKeyCode = ReadInt32(1);// 0字节 代码 9994 整数 大
		bufpNext(24 - 4);
		int fileLength = ReadInt32(1);// 24字节 文件长度 整数 大
		int fileVison = ReadInt32(0);// 28字节 版本 1000整数 小
		int shapetype = ReadInt32(0);// 32字节 类型整数 小
		// -------------------------------------------------------------
		ESRI_MBR mbx = ESRI_MBR_Read();// 读出整个shp图层的边界合
		// 36字节 边界盒 Xmin 双精度 小
		// 44字节 边界盒 Ymin 双精度 小
		// 52字节 边界盒 Xmax 双精度 小
		// 60字节 边界盒 Ymax 双精度 小
		// -------------------------------------------------------------
		// shp中尚未使用的边界盒
		// 68字节* 边界盒 Zmin 双精度 小
		// 76字节* 边界盒 Zmax 双精度 小
		// 84字节* 边界盒 Mmin 双精度 小
		// 92字节* 边界盒 Mmax 双精度 小
		// -------------------------------------------------------------
		return shapetype;
		// =============================================================
		// =============================================================
		// Position Field Value Type Order
		// Byte 0 File Code 9994 Integer Big
		// Byte 4 Unused 0 Integer Big
		// Byte 8 Unused 0 Integer Big
		// Byte 12 Unused 0 Integer Big
		// Byte 16 Unused 0 Integer Big
		// Byte 20 Unused 0 Integer Big
		// Byte 24 File Length File Length Integer Big
		// Byte 28 Version 1000 Integer Little
		// Byte 32 Shape Type Shape Type Integer Little
		// Byte 36 Bounding Box Xmin Double Little
		// Byte 44 Bounding Box Ymin Double Little
		// Byte 52 Bounding Box Xmax Double Little
		// Byte 60 Bounding Box Ymax Double Little
		// Byte 68* Bounding Box Zmin Double Little
		// Byte 76* Bounding Box Zmax Double Little
		// Byte 84* Bounding Box Mmin Double Little
		// Byte 92* Bounding Box Mmax Double Little
		// =============================================================
		// Value Shape Type
		// 0 Null Shape
		// 1 Point
		// 3 PolyLine
		// 5 Polygon
		// 8 MultiPoint
		// 11 PointZ
		// 13 PolyLineZ
		// 15 PolygonZ
		// 18 MultiPointZ
		// 21 PointM
		// 23 PolyLineM
		// 25 PolygonM
		// 28 MultiPointM
		// 31 MultiPatch
		// =============================================================
	}

	// ===================================================================================
	@SuppressWarnings("unused")
	public static ArrayList<ESRI_POINT> Shape_Point_Read(int shapecount) {
		// -------------------------------------------------------------
		ArrayList<ESRI_POINT> g = new ArrayList<ESRI_POINT>();
		// -------------------------------------------------------------
		int stype = -1;
		bufpMove(100);
		// -------------------------------------------------------------
		for (int i = 0; i < shapecount; i++) {
			// ---------------------------------------------------------
			ESRI_POINT shapPoint = new ESRI_POINT();
			// ---------------------------------------------------------
			bufpNext(8);
			stype = ReadInt32(0);
			// ---------------------------------------------------------
			shapPoint.x = ReadDouble(0);
			shapPoint.y = ReadDouble(0);
			// shapPoint.z = 0;
			shapPoint.OID = i;
			// ---------------------------------------------------------
			g.add(shapPoint);
			// ---------------------------------------------------------
		}
		// -------------------------------------------------------------
		return g;
		// =============================================================
		// =============================================================
		// Position Field Value Type Number Order
		// Byte 0 Shape Type 1 Integer 1 Little
		// Byte 4 X X Double 1 Little
		// Byte 12 Y Y Double 1 Little
		// =============================================================
	}

	// ===================================================================================
	@SuppressWarnings("unused")
	public static ArrayList<ESRI_POINTZ> Shape_Point_Read_11(int shapecount) {
		// -------------------------------------------------------------
		ArrayList<ESRI_POINTZ> g = new ArrayList<ESRI_POINTZ>();
		// -------------------------------------------------------------
		int stype = -1;
		bufpMove(100);
		// -------------------------------------------------------------
		for (int i = 0; i < shapecount; i++) {
			// ---------------------------------------------------------
			ESRI_POINTZ shapPoint = new ESRI_POINTZ();
			// ---------------------------------------------------------
			bufpNext(8);
			stype = ReadInt32(0);
			// ---------------------------------------------------------
			shapPoint.x = ReadDouble(0);
			shapPoint.y = ReadDouble(0);
			shapPoint.z = ReadDouble(0);
			shapPoint.m = ReadDouble(0);
			shapPoint.OID = i;
			// ---------------------------------------------------------
			g.add(shapPoint);
			// ---------------------------------------------------------
		}
		// -------------------------------------------------------------
		return g;
		// =============================================================
	}

	// //
	// ===================================================================================
	// @SuppressWarnings("unused")
	// public static ArrayList<ESRI_POINT> Shape_Point_Read_11(int shapecount) {
	// // -------------------------------------------------------------
	// ArrayList<ESRI_POINTZ> g = new ArrayList<ESRI_POINTZ>();
	// // -------------------------------------------------------------
	// int stype = -1;
	// bufpMove(100);
	// // -------------------------------------------------------------
	// for (int i = 0; i < shapecount; i++) {
	// // ---------------------------------------------------------
	// ESRI_POINTZ shapPoint = new ESRI_POINTZ();
	// // ---------------------------------------------------------
	// bufpNext(8);
	// stype = ReadInt32(0);
	// // ---------------------------------------------------------
	// shapPoint.x = ReadDouble(0);
	// shapPoint.y = ReadDouble(0);
	// shapPoint.z = ReadDouble(0);
	// shapPoint.m = ReadDouble(0);
	// shapPoint.OID = i;
	// // ---------------------------------------------------------
	// g.add(shapPoint);
	// // ---------------------------------------------------------
	// }
	// // -------------------------------------------------------------
	// ArrayList<ESRI_POINT> b = new ArrayList<ESRI_POINT>();
	// for (int i = 0; i < shapecount; i++) {
	// // ---------------------------------------------------------
	// ESRI_POINT bt = new ESRI_POINT();
	// bt.x = g.get(i).x;
	// bt.y = g.get(i).y;
	// // ---------------------------------------------------------
	// b.add(bt);
	// // ---------------------------------------------------------
	// }
	// // -------------------------------------------------------------
	// return b;
	// // =============================================================
	// }

	// ===================================================================================

	public static ArrayList<ESRI_POLYLINE> Shape_Polyline_Read(int shapecount) {// TYPE3
		// -------------------------------------------------------------
		ArrayList<ESRI_POLYLINE> g = new ArrayList<ESRI_POLYLINE>();
		// -------------------------------------------------------------
		bufpMove(100);
		int nn = 0;
		// -------------------------------------------------------------
		for (int i = 0; i < shapecount; i++) {
			// ----------------------------------------------------------
			ESRI_POLYLINE shapePolyline = Shape_Polyline_ForRead(i);
			// ----------------------------------------------------------
			if (shapePolyline != null) {
				nn++;
				g.add(shapePolyline);
			}
			// ----------------------------------------------------------
		}
		BBKDebug.d(nn, false, false);
		// -------------------------------------------------------------
		return g;
		// -------------------------------------------------------------
		// =============================================================
		// MultiPoint Record Contents Byte
		// Position Field Value Type Number Order
		// Byte 0 Shape Type 8 Integer 1 Little
		// Byte 4 Box Box Double 4 Little
		// Byte 36 NumPoints NumPoints Integer 1 Little
		// Byte 40 Points Points Point NumPoints Little
		// =============================================================
		// PolyLine Record Contents Byte
		// Position Field Value Type Number Order
		// Byte 0 Shape Type 3 Integer 1 Little
		// Byte 4 Box Box Double 4 Little
		// Byte 36 NumParts NumParts Integer 1 Little
		// Byte 40 NumPoints NumPoints Integer 1 Little
		// Byte 44 Parts Parts Integer NumParts Little
		// Byte X Points Points Point NumPoints Little
		// Note: X = 44 + 4 * NumParts
		// =============================================================
	}

	@SuppressWarnings("unused")
	public static ESRI_POLYLINE Shape_Polyline_ForRead(int i) {
		// ----------------------------------------------------------
		ESRI_POLYLINE shapePolyline = new ESRI_POLYLINE();
		// ----------------------------------------------------------
		bufpNext(8);// 记录头8个字节
		int stype = ReadInt32(0);// 一个int(4个字节)的shapetype
		// ----------------------------------------------------------
		ESRI_MBR mbx = ESRI_MBR_Read();// 获得图形外边框
		// BBKDebug.ddd( "Left====" + left);
		// BBKDebug.ddd( "Bottom====" + bottom);
		// BBKDebug.ddd( "Right====" + right);
		// BBKDebug.ddd( "Top====" + top);
		// ----------------------------------------------------------
		int partcount = ReadInt32(0);// 线段数
		int pointcount = ReadInt32(0);// 线段点数目
		if (partcount > 100000000 || partcount < 0) {
			// BBKDebug.ddd("partcount====" + partcount);
			return null;
		}
		if (pointcount > 100000000 || pointcount < 0) {
			// BBKDebug.ddd("pointcount====" + pointcount);
			return null;
		}
		// ----------------------------------------------------------
		int[] parts = new int[partcount];
		int[] partspos = new int[partcount];
		double[] xpoints = new double[pointcount];
		double[] ypoints = new double[pointcount];
		double[] zpoints = new double[pointcount];
		// ----------------------------------------------------------
		// 节点开始位置
		for (int j = 0; j < partcount; j++) {
			parts[j] = ReadInt32(0);
		}
		// ----------------------------------------------------------
		// shift them to be points count included in parts
		if (partcount > 0)
			partspos[0] = 0;
		// ----------------------------------------------------------
		int newpos = 0;
		for (int j = 0; j < partcount - 1; j++) {
			parts[j] = parts[j + 1] - parts[j];
			newpos += parts[j];
			partspos[j + 1] = newpos;
		}
		// ----------------------------------------------------------
		parts[partcount - 1] = pointcount - parts[partcount - 1];
		// ----------------------------------------------------------
		for (int j = 0; j < pointcount; j++) {// 读坐标
			xpoints[j] = ReadDouble(0);
			ypoints[j] = ReadDouble(0);
			zpoints[j] = 0;
		}
		// ----------------------------------------------------------
		if (pointcount > 1) {
			shapePolyline.OID = i;
			shapePolyline.XPOINTS = xpoints;
			shapePolyline.YPOINTS = ypoints;
		}
		// ----------------------------------------------------------
		parts = partspos = null;
		xpoints = ypoints = zpoints = null;
		// ----------------------------------------------------------
		return shapePolyline;
		// ----------------------------------------------------------
	}

	// ===================================================================================

	public static ArrayList<ESRI_POLYGON> Shape_Polygon_Read(int shapecount) {
		// -------------------------------------------------------------
		ArrayList<ESRI_POLYGON> g = new ArrayList<ESRI_POLYGON>();
		// -------------------------------------------------------------
		bufpMove(100);
		// -------------------------------------------------------------
		for (int i = 0; i < shapecount; i++) {
			// ---------------------------------------------------------
			ESRI_POLYGON shapePolygon = Shape_Polygon_ForRead(i);
			// ---------------------------------------------------------
			if (shapePolygon != null) {
				g.add(shapePolygon);
			}
			// ---------------------------------------------------------
		}
		// -------------------------------------------------------------
		return g;
		// -------------------------------------------------------------
	}

	@SuppressWarnings("unused")
	public static ESRI_POLYGON Shape_Polygon_ForRead(int i) {
		// -------------------------------------------------------------
		ESRI_POLYGON shapePolygon = new ESRI_POLYGON();
		// -------------------------------------------------------------
		bufpNext(8);// 记录头8个字节
		int shapetype = ReadInt32(0); // 和一个int(4个字节)的shapetype
		// -------------------------------------------------------------
		ESRI_MBR mbx = ESRI_MBR_Read();// 获得图形外边框
		// -------------------------------------------------------------
		int partcount = ReadInt32(0);// 线段数
		int pointcount = ReadInt32(0);// 线段点数目
		if (partcount > 100000000 || partcount < 0) {
			// BBKDebug.ddd("partcount====" + partcount);
			return null;
		}
		if (pointcount > 100000000 || pointcount < 0) {
			// BBKDebug.ddd("pointcount====" + pointcount);
			return null;
		}
		// -------------------------------------------------------------
		int[] parts = new int[partcount];
		int[] partspos = new int[partcount];
		// -------------------------------------------------------------
		double[] xpoints = new double[pointcount];
		double[] ypoints = new double[pointcount];
		double[] zpoints = new double[pointcount];
		// -------------------------------------------------------------
		// firstly read out parts begin pos in file
		for (int j = 0; j < partcount; j++) {
			parts[j] = ReadInt32(0);
		}
		// -------------------------------------------------------------
		// shift them to be points count included in parts
		if (partcount > 0)
			partspos[0] = 0;
		// -------------------------------------------------------------
		int newpos = 0;
		for (int j = 0; j <= partcount - 2; j++) {
			parts[j] = parts[j + 1] - parts[j];
			newpos += parts[j];
			partspos[j + 1] = newpos;
		}
		// -------------------------------------------------------------
		parts[partcount - 1] = pointcount - parts[partcount - 1];
		// -------------------------------------------------------------
		for (int j = 0; j < pointcount; j++) {// 读坐标
			xpoints[j] = ReadDouble(0);
			ypoints[j] = ReadDouble(0);
			zpoints[j] = 0;
		}
		// -------------------------------------------------------------
		if (pointcount > 1) {
			shapePolygon.OID = i;
			shapePolygon.XPOINTS = xpoints;
			shapePolygon.YPOINTS = ypoints;
		}
		// -------------------------------------------------------------
		parts = partspos = null;
		xpoints = ypoints = zpoints = null;
		// ----------------------------------------------------------
		return shapePolygon;
		// -------------------------------------------------------------

	}

	// ===================================================================================

	public static ArrayList<ESRI_POLYLINE> Shape_Polyline_Read13(int shapecount) {
		// http://www.doc88.com/p-670126032455.html
		// http://zh.wikipedia.org/wiki/Shapefile
		// -------------------------------------------------------------
		// Double[4] Box // Bounding Box
		// Integer NumParts // Number of Parts
		// Integer NumPoints // Total Number of Points
		// Integer[NumParts] Parts // Index to First Point in Part
		// Point[NumPoints] Points // Points for All Parts
		// Double[2] Z Range // Bounding Z Range
		// Double[NumPoints] Z Array // Z Values for All Points
		// Double[2] M Range // Bounding Measure Range
		// Double[NumPoints] M Array // Measures
		// -------------------------------------------------------------
		ArrayList<ESRI_POLYLINE> g = new ArrayList<ESRI_POLYLINE>();
		// -------------------------------------------------------------
		bufpMove(100);
		// -------------------------------------------------------------
		for (int i = 0; i < shapecount; i++) {
			// ----------------------------------------------------------
			ESRI_POLYLINE shapePolyline = Shape_Polyline_ForRead13(i);
			// ----------------------------------------------------------
			if (shapePolyline != null)
				g.add(shapePolyline);
			// ----------------------------------------------------------
		}
		// -------------------------------------------------------------
		return g;
		// -------------------------------------------------------------
	}

	@SuppressWarnings("unused")
	public static ESRI_POLYLINE Shape_Polyline_ForRead13(int i) {
		// ----------------------------------------------------------
		bufpNext(8);// 记录头8个字节
		// ----------------------------------------------------------
		int stype = ReadInt32(0);// 一个int(4个字节)的shapetype
		// ----------------------------------------------------------
		ESRI_MBR mbx = ESRI_MBR_Read();// 获得图形外边框
		// ----------------------------------------------------------
		int NumParts = ReadInt32(0);
		int NumPoints = ReadInt32(0);
		if (NumParts > 100000000 || NumParts < 0) {
			// BBKDebug.ddd("NumParts=" + NumParts);
			return null;
		}
		if (NumPoints > 100000000 || NumPoints < 0) {
			// BBKDebug.ddd("NumPoints=" + NumPoints);
			return null;
		}
		// ----------------------------------------------------------N
		int[] Parts = new int[NumParts];
		for (int j = 0; j < NumParts; j++) {
			Parts[j] = ReadInt32(0);
		}
		// ----------------------------------------------------------XYZ
		double[] xpoints = new double[NumPoints];
		double[] ypoints = new double[NumPoints];
		// ----------------------------------------------------------XYZ
		for (int j = 0; j < NumPoints; j++) {
			xpoints[j] = ReadDouble(0);
			ypoints[j] = ReadDouble(0);
		}
		// ----------------------------------------------------------Z
		double Zmin = ReadDouble(0);
		double Zmax = ReadDouble(0);
		double[] ZArray = new double[NumPoints];
		for (int j = 0; j < NumPoints; j++) {
			ZArray[j] = ReadDouble(0);
		}
		// ----------------------------------------------------------M
		double Mmin = ReadDouble(0);
		double Mmax = ReadDouble(0);
		double[] MArray = new double[NumPoints];
		for (int j = 0; j < NumPoints; j++) {
			MArray[j] = ReadDouble(0);
		}
		// ----------------------------------------------------------
		// ----------------------------------------------------------
		ESRI_POLYLINE shapePolyline = new ESRI_POLYLINE();
		if (NumPoints > 1) {
			shapePolyline.OID = i;
			shapePolyline.XPOINTS = xpoints;
			shapePolyline.YPOINTS = ypoints;
		}
		// ----------------------------------------------------------
		return shapePolyline;
		// ----------------------------------------------------------
	}

	// ===================================================================================
	// ===================================================================================
	// ===================================================================================
	// ===================================================================================
	public static int ShapeShxCount(InputStream shxfile) {// 打开.shx文件
		// -------------------------------------------------------------
		if (shxfile == null)
			return -1;
		// -------------------------------------------------------------
		try {
			// -------------------------------------------------------------
			int lenght = shxfile.available();// 文件大小
			BBKDebug.d("shxfile.lenght = " + lenght, false, false);
			return (int) (lenght - 100) / 8; // 总记录数
			// -------------------------------------------------------------
		} catch (IOException e1) {
			return -1;
		}
		// -------------------------------------------------------------
	}

	// ===================================================================================
	static byte[] buffer = new byte[1];
	private static int bufposition = 0;

	public static boolean ShapebufferLoad(InputStream shpfile) {// 打开.shp文件
		// --------------------------------------------------------
		if (shpfile == null)
			return false;
		// --------------------------------------------------------
		try {
			// --------------------------------------------------------
			int FileLength = shpfile.available();
			buffer = new byte[FileLength];
			shpfile.read(buffer, 0, FileLength);
			bufposition = 0;
			shpfile.close();
			// --------------------------------------------------------
			return true;
			// --------------------------------------------------------
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		// --------------------------------------------------------
		return false;
		// --------------------------------------------------------
	}

	// ===================================================================================
	// ===================================================================================
	// ===================================================================================
	// ===================================================================================
	public static void bufpNext(int pn) {
		bufposition += pn;
	}

	public static void bufpMove(int pn) {
		bufposition = pn;
	}

	public static double ReadDouble(int type) {
		// --------------------------------------------------------
		int poi = bufposition;
		long reg = 0;
		// --------------------------------------------------------
		if (type == 1) {// 1双精度大 0双精度小
			// ----------------------------------------------------
			reg = ((long) buffer[poi + 0] << 56) + ((long) (buffer[poi + 1] & 255) << 48) + ((long) (buffer[poi + 2] & 255) << 40) + ((long) (buffer[poi + 3] & 255) << 32) + ((long) (buffer[poi + 4] & 255) << 24) + ((buffer[poi + 5] & 255) << 16) + ((buffer[poi + 6] & 255) << 8) + ((buffer[poi + 7] & 255) << 0);
			// ----------------------------------------------------
		} else {
			// ----------------------------------------------------
			reg = ((long) buffer[poi + 7] << 56) + ((long) (buffer[poi + 6] & 255) << 48) + ((long) (buffer[poi + 5] & 255) << 40) + ((long) (buffer[poi + 4] & 255) << 32) + ((long) (buffer[poi + 3] & 255) << 24) + ((buffer[poi + 2] & 255) << 16) + ((buffer[poi + 1] & 255) << 8) + ((buffer[poi + 0] & 255) << 0);
			// ----------------------------------------------------
		}
		// --------------------------------------------------------
		bufposition += 8;
		return Double.longBitsToDouble(reg);
		// --------------------------------------------------------
	}

	public static int ReadInt32(int type) {
		// --------------------------------------------------------
		int poi = bufposition;
		int reg = 0;
		// --------------------------------------------------------
		if (type == 1) {// 1整数大 0整数小
			// --------------------------------------------------------
			reg = ((buffer[poi + 0] & 255) << 24) + ((buffer[poi + 1] & 255) << 16) + ((buffer[poi + 2] & 255) << 8) + ((buffer[poi + 3] & 255) << 0);
			// --------------------------------------------------------
		} else {
			// --------------------------------------------------------
			reg = ((buffer[poi + 3] & 255) << 24) + ((buffer[poi + 2] & 255) << 16) + ((buffer[poi + 1] & 255) << 8) + ((buffer[poi + 0] & 255) << 0);
			// --------------------------------------------------------
		}
		// --------------------------------------------------------
		bufposition += 4;
		return reg;
		// --------------------------------------------------------
	}

	// ============================================================
	// public int ReadInt16() {
	// // --------------------------------------------------------
	// int poi = bufposition;
	// int reg = ((buffer[poi + 1] & 255) << 8)
	// + ((buffer[poi + 0] & 255) << 0);
	// bufposition += 2;
	// return reg;
	// // --------------------------------------------------------
	// }

	// ============================================================
	// public int ReadInt8() {
	// // --------------------------------------------------------
	// int poi = bufposition;
	// int reg = ((buffer[poi + 0] & 255) << 0);
	// bufposition += 1;
	// return reg;
	// // --------------------------------------------------------
	// }
	// ===================================================================================
}
