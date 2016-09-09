package net;

import java.util.Date;

import com.zhsk.bbktool.BBK_Tool_Byte;
import com.zhsk.bbktool.d;

public class BBKNetUDP2 {

	public static class GPS {
		// -----------------------------------------
		public boolean K;// 是否定位
		public boolean R;// 是否合理移动
		// -----------------------------------------
		public Date t;// GPS时间
		// -----------------------------------------
		public double w;// 纬度
		public double j;// 经度
		public double h;// 海拔
		public double v;// 速度
		public double f;// 方向
		// -----------------------------------------
		public int r;// 定位精度
		public int s;// 卫星数目
		public int u;// 参与解算卫星数目
		public int snr;// 卫星平均信号值
		public int usr;// 参与解算卫星数目
		// -----------------------------------------
		public double lw;// 上一次纬度
		public double lj;// 上一次经度
		// -----------------------------------------
		public double l;// 里程
		public double va;// 平均速度
		public double vm;// 最大速度
		// -----------------------------------------
		public Date ts;// 启动时间
		public double tl;// 持续时间
		public String tls;// 持续时间字符形式
		// -----------------------------------------
		public String a;// 标题
		public String i;// 信息
		public String vs;// 速度
		// -----------------------------------------
	}

	private static long tp = 0;//0gps,1MDU6050,

	public static byte[] toBytes(long id, GPS g) {
		// -----------------------------------------
		byte[] data = new byte[100];// 20+8*5+4*5=100
		// -----------------------------------------
		BBK_Tool_Byte.putLong4(data, id, 0);//long4//唯一标识
		BBK_Tool_Byte.putLong4(data, tp, 4);//long4//类型
		// -----------------------------------------
		BBK_Tool_Byte.putLong8(data, g.t.getTime(), 8);//long4//GPS时间
		BBK_Tool_Byte.putBoolean(data, g.K, 16);//boolean1//是否定位
		// -----------------------------------------
		BBK_Tool_Byte.putDouble(data, g.w, 20 + 0);//double8 纬度
		BBK_Tool_Byte.putDouble(data, g.j, 20 + 8);//double8 经度	
		BBK_Tool_Byte.putDouble(data, g.h, 20 + 16);//double8 高度	
		BBK_Tool_Byte.putDouble(data, g.v, 20 + 24);//double8 速度
		BBK_Tool_Byte.putDouble(data, g.f, 20 + 32);//double8 方向
		// -----------------------------------------
		BBK_Tool_Byte.putInt(data, g.r, 60 + 0); //int4定位精度
		BBK_Tool_Byte.putInt(data, g.s, 60 + 4); //int4卫星数目
		BBK_Tool_Byte.putInt(data, g.u, 60 + 8); //int4参与解算卫星数目
		BBK_Tool_Byte.putInt(data, g.snr, 60 + 12);//int4卫星平均信号值
		BBK_Tool_Byte.putInt(data, g.usr, 60 + 16);//int4参与解算卫星数目
		// -----------------------------------------
		return data;
	}
	// -----------------------------------------

	//	// -----------------------------------------
	//	10 27 00 00 //long4//唯一标识
	//	00 00 00 00 //long4//类型
	//	CF B3 D4 0D //long4//GPS时间
	//	01 00 00 00 //boolean1//是否定位
	//	00 00 00 00 
	//	// -----------------------------------------
	//	6E A3 01 BC 05 FE 43 40 //double8 纬度
	//	28 D7 14 C8 EC 15 5D 40 //double8 经度	
	//	00 00 00 00 00 00 47 40 //double8 高度	
	//	CD CC CC CC CC CC 00 40 //double8 速度
	//	00 00 00 00 00 00 5F 40 //double8 方向
	//	// -----------------------------------------
	//	10 00 00 00 //int4定位精度
	//	0A 00 00 00 //int4卫星数目
	//	00 00 00 00 //int4参与解算卫星数目
	//	11 00 00 00 //int4卫星平均信号值
	//	00 00 00 00 //int4参与解算卫星数目
	//	// -----------------------------------------
	public static GPS g = new GPS();

	public static void bufExp(byte[] b) {
		//d.s(b.toString());
		// -----------------------------------------
		long id = BBK_Tool_Byte.getLong4(b, 0);
		long tp = BBK_Tool_Byte.getLong4(b, 4);
		long tm = BBK_Tool_Byte.getLong8(b, 8);
		boolean by = BBK_Tool_Byte.getBoolean(b, 16);
		// -----------------------------------------		
		g.w = BBK_Tool_Byte.getDouble(b, 20 + 0);
		g.j = BBK_Tool_Byte.getDouble(b, 20 + 8);
		g.h = BBK_Tool_Byte.getDouble(b, 20 + 16);
		g.v = BBK_Tool_Byte.getDouble(b, 20 + 24);
		g.f = BBK_Tool_Byte.getDouble(b, 20 + 32);
		// -----------------------------------------	
		g.r = BBK_Tool_Byte.getInt(b, 60 + 0);
		g.s = BBK_Tool_Byte.getInt(b, 60 + 4);
		g.u = BBK_Tool_Byte.getInt(b, 60 + 8);
		g.snr = BBK_Tool_Byte.getInt(b, 60 + 12);
		g.usr = BBK_Tool_Byte.getInt(b, 60 + 16);
		// -----------------------------------------	
		// -----------------------------------------	
		// -----------------------------------------	
		d.s(id + "," + tp + "," + tm + "," + by);
		d.s(g.w + "," + g.j + "," + g.h + "," + g.v + "," + g.f);
		d.s(g.r + "," + g.s + "," + g.u + "," + g.snr + "," + g.usr);
	}
	
}
