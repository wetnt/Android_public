package com.boboking.bbkgpsudp;

import com.zhsk.bbktool.BBK_Tool_Byte;
import com.zhsk.bbktool.d;

import bbk.map.gps.BBKGpsMath;
import bbk.map.gps.BBKGpsMath.GPS;

public class BBKNetUDP {

	private static long tp = 0;// 0gps,1MDU6050,

	public static byte[] toBytes(long id, GPS g) {
		// -----------------------------------------
		byte[] data = new byte[64];// 20+8*5+4=64
		// -----------------------------------------
		BBK_Tool_Byte.putLong4(data, id, 0);// long4//唯一标识
		BBK_Tool_Byte.putLong4(data, tp, 4);// long4//类型
		// -----------------------------------------
		BBK_Tool_Byte.putBoolean(data, g.K, 8);// boolean1//是否定位
		BBK_Tool_Byte.putLong8(data, g.t.getTime(), 12);// long4//GPS时间
		// -----------------------------------------
		BBK_Tool_Byte.putDouble(data, g.w, 20 + 0);// double8 纬度
		BBK_Tool_Byte.putDouble(data, g.j, 20 + 8);// double8 经度
		BBK_Tool_Byte.putDouble(data, g.h, 20 + 16);// double8 高度
		BBK_Tool_Byte.putDouble(data, g.v, 20 + 24);// double8 速度
		BBK_Tool_Byte.putDouble(data, g.f, 20 + 32);// double8 方向
		// -----------------------------------------
		BBK_Tool_Byte.putInt(data, g.r, 60 + 0); // int4定位精度
		// -----------------------------------------
		return data;
	}

	public static String toUdpString(long id, GPS g) {
		String s = "";
		s += id + ",";
		s += tp + ",";
		s += g.K ? "1" + "," : "0" + ",";
		s += g.w + ",";
		s += g.j + ",";
		s += g.h + ",";
		s += g.v + ",";
		s += g.f + ",";
		s += g.r + ",";
		s += g.t.getTime();
		return s;
	}

	public static String toLogString(long id, GPS g) {
		String s = "";
		s += "id=" + id + ",";//+ "\r\n";
		s += "tp=" + tp + ",";//+ "\r\n";
		s += "k=" + g.K + "\r\n";
		s += "w= " + g.w + "\r\n";
		s += "j=" + g.j + "\r\n";
		s += "h=" + g.h + "\r\n";
		s += "v=" + g.v + "\r\n";
		s += "f=" + g.f + "\r\n";
		s += "r=" + g.r + "\r\n";
		s += "t=" + g.t.getTime() + "\r\n";
		s += "m=" + g.m + "\r\n";
		return s;
	}

	// -----------------------------------------
	public static void bufExp(byte[] b) {
		// d.s(b.toString());
		GPS g = new BBKGpsMath.GPS();
		// -----------------------------------------
		long id = BBK_Tool_Byte.getLong4(b, 0);
		long tp = BBK_Tool_Byte.getLong4(b, 4);
		g.K = BBK_Tool_Byte.getBoolean(b, 8);
		long tm = BBK_Tool_Byte.getLong8(b, 12);
		// -----------------------------------------
		g.w = BBK_Tool_Byte.getDouble(b, 20 + 0);
		g.j = BBK_Tool_Byte.getDouble(b, 20 + 8);
		g.h = BBK_Tool_Byte.getDouble(b, 20 + 16);
		g.v = BBK_Tool_Byte.getDouble(b, 20 + 24);
		g.f = BBK_Tool_Byte.getDouble(b, 20 + 32);
		// -----------------------------------------
		g.r = BBK_Tool_Byte.getInt(b, 60 + 0);
		// -----------------------------------------
		d.s(id + "," + tp + "," + g.K + "," + tm);
		d.s(g.w + "," + g.j + "," + g.h + "," + g.v + "," + g.f);
		// -----------------------------------------
	}
}
