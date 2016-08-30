package com.zhsk.flypadudp;

public class zhskflypaddata {

	private static final String rn = "\r\n";

	public static class OSGPLANEINFO {

		// 1.整型
		// 类型 存储需求 bit数 取值范围 备注
		// int 4字节 4*8
		// short 2字节 2*8 －32768～32767
		// long 8字节 8*8
		// byte 1字节 1*8 －128～127
		// 2.浮点型
		// 类型 存储需求 bit数 取值范围 备注
		// float 4字节 4*8 float类型的数值有一个后缀F(例如：3.14F)
		// double 8字节 8*8 没有后缀F的浮点数值(如3.14)默认为double类型
		// 3.char类型
		// 类型 存储需求 bit数 取值范围 备注
		// char 2字节 2*8
		// 4.boolean类型
		// 类型 存储需求 bit数 取值范围 备注
		// boolean 1字节 1*8 false、true

		double B = 0; // 8 纬度
		double Z = 0; // 8 高度
		double L = 0; // 8 经度
		double phi = 0; // 8 俯仰
		double gamma = 0; // 8 滚转
		double psi = 0; // 8 方位
		float Speed = 0; // 4 速度
		int Part = 0; // 8 敌我识别 0:我(红) 1:敌(蓝) 2:第三方 3:友 4:不明 5:导弹
		long CocpitID = 0; // 8 座舱标识
		long ID = 0; // 8 唯一标识
		long jx = 0; // 8 飞机型号
		float Health = 0; // 4 飞机健康状态 0~100
		// 旋转矢量
		double Rx = 0; // 8 Quat-x
		double Ry = 0; // 8 Quat-y
		double Rz = 0; // 8 Quat-z
		double Rw = 0; // 8 Quat-w
		// 控制变量
		int ResetTail = 0;// 4 是否需要重置尾迹
		int OriType = 0; // 4 姿态类型：0:使用pgp 1:使用Quat 2:使用calc
		// 显示变量
		int ShowID = 0; // 4 批号

		int FlyCount = 0; // 4 架次

		char BK[] = new char[5]; // 2*5 保留位

		byte[] data = new byte[125];// 8*10+4*10+1*5=125

		public OSGPLANEINFO() {
		}

		public byte[] toBytes() {
			putDouble(data, B, 0); // double8 纬度
			putDouble(data, Z, 8); // double8 高度
			putDouble(data, L, 16);// double8 经度
			putDouble(data, phi, 24);// double8 俯仰
			putDouble(data, gamma, 32);// double8 滚转
			putDouble(data, psi, 40);// double8 方位

			putFloat(data, Speed, 48); // float4 速度
			putInt(data, Part, 52); // int4 敌我识别 0:我(红)1:敌(蓝)2:第三方3:友4:不明5:导弹
			putLong(data, CocpitID, 56); // long4 座舱标识
			putLong(data, ID, 60); // long4唯一标识
			putLong(data, jx, 64); // long4飞机型号
			putFloat(data, Health, 68); // float4 飞机健康状态 0~100

			putDouble(data, Rx, 72); // double8 Quat-x
			putDouble(data, Ry, 80); // double8 Quat-y
			putDouble(data, Rz, 88); // double8 Quat-z
			putDouble(data, Rw, 96); // double8 Quat-w

			putInt(data, ResetTail, 104); // int4 是否需要重置尾迹
			putInt(data, OriType, 108); // int4 姿态类型：0:使用pgp1:使用Quat2:使用calc
			putInt(data, ShowID, 112); // int4 批号
			putInt(data, FlyCount, 116); // int4 架次
			// putDouble(data , BK[5] , 120); // char5 保留位
			return data;
		}

		public String toString() {
			// --------------------------------------------------------
			String s = "";
			// --------------------------------------------------------
			s += "标识\t" + ID + rn; // 唯一标识
			// --------------------------------------------------------
			s += "纬度\t" + B + rn;// 纬度
			s += "经度\t" + L + rn;// 经度
			s += "高度\t" + Z + rn;// 高度
			s += "速度\t" + Speed + rn; // 速度
			// --------------------------------------------------------
			// s += "机型\t" + jx + rn; // 飞机型号
			// s += "健康\t" + Health + rn; // 飞机健康状态 0~100
			// s += "座舱\t" + CocpitID + rn; // 座舱标识
			// --------------------------------------------------------
			// s += "俯仰\t" + phi + rn;// 俯仰
			// s += "滚转\t" + gamma + rn; // 滚转
			s += "方位\t" + psi + rn; // 方位
			// --------------------------------------------------------
			// s += "敌我\t" + Part + rn; // 敌我识别 0:我(红) 1:敌(蓝) 2:第三方 3:友 4:不明
			// 5:导弹
			// 旋转矢量--以下不需要填写
			// s += "Quat-x\t" + Rx +rn; // Quat-x
			// s += "Quat-y\t" + Ry +rn; // Quat-y
			// s += "Quat-z\t" + Rz +rn; // Quat-z
			// s += "Quat-w\t" + Rw +rn; // Quat-w
			// --------------------------------------------------------
			// 控制变量
			// s += "尾迹\t" + ResetTail + rn;// 是否需要重置尾迹
			// s += "姿态\t" + OriType + rn; // 姿态类型：0:使用pgp 1:使用Quat 2:使用calc
			// 显示变量
			// s += "批号\t" + ShowID + rn; // 批号
			// --------------------------------------------------------
			return s;
		}
	};

	// =============================================================
	// =============================================================
	// =============================================================
	public static void putInt(byte[] bb, int num, int index) {
		byte[] result = new byte[4];
		result[0] = (byte) (num >>> 24);
		result[1] = (byte) (num >>> 16);
		result[2] = (byte) (num >>> 8);
		result[3] = (byte) (num);
		bb[index + 0] = result[3];
		bb[index + 1] = result[2];
		bb[index + 2] = result[1];
		bb[index + 3] = result[0];
	}

	public static void putLong(byte[] bb, long num, int index) {
		byte[] result = new byte[4];
		result[0] = (byte) (num >>> 24);
		result[1] = (byte) (num >>> 16);
		result[2] = (byte) (num >>> 8);
		result[3] = (byte) (num);
		bb[index + 0] = result[3];
		bb[index + 1] = result[2];
		bb[index + 2] = result[1];
		bb[index + 3] = result[0];
	}

	public static void putFloat(byte[] bb, float x, int index) {
		byte[] result = new byte[4];
		int l = Float.floatToIntBits(x);
		for (int i = 0; i < 4; i++) {
			result[i] = new Integer(l).byteValue();
			l = l >> 8;
		}
		bb[index + 0] = result[3];
		bb[index + 1] = result[2];
		bb[index + 2] = result[1];
		bb[index + 3] = result[0];
	}

	public static void putDouble(byte[] bb, double x, int index) {
		byte[] output = new byte[8];
		long lng = Double.doubleToLongBits(x);
		for (int i = 0; i < 8; i++)
			output[i] = (byte) ((lng >> ((7 - i) * 8)) & 0xff);

		bb[index + 0] = output[7];
		bb[index + 1] = output[6];
		bb[index + 2] = output[5];
		bb[index + 3] = output[4];
		bb[index + 4] = output[3];
		bb[index + 5] = output[2];
		bb[index + 6] = output[1];
		bb[index + 7] = output[0];
	}
	// =============================================================
	// =============================================================
	// =============================================================
}
