package com.zhsk.bbktool;

public class BBK_Tool_Byte {

	// =============================================================
	// =============================================================
	//http://blog.csdn.net/tungkee/article/details/7549285
	//新的回头改
	// =============================================================
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
	// =============================================================
	// =============================================================
	// =============================================================
	public static void putBoolean(byte[] bb, boolean num, int index) {
		bb[index + 0] = (byte) (num ? 1 : 0);
		//		bb[index + 1] = 0;
		//		bb[index + 2] = 0;
		//		bb[index + 3] = 0;
	}

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

	public static void putLong4(byte[] bb, long num, int index) {
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
	public static boolean getBoolean(byte[] b, int s) {
		return b[s + 0] == 1;
	}

	public static int getInt(byte[] b, int s) {
		return (int) (//
		(((b[s + 3] & 0xff) << 24)//
				| ((b[s + 2] & 0xff) << 16) //
				| ((b[s + 1] & 0xff) << 8) //
				| ((b[s + 0] & 0xff) << 0))//
		);
	}

	public static double getDouble(byte[] arr, int s) {
		long value = 0;
		for (int i = 0; i < 8; i++) {
			value |= ((long) (arr[s + i] & 0xff)) << (8 * i);
		}
		return Double.longBitsToDouble(value);
	}

	public static long getLong4(byte[] b, int s) {
		long l = 0;
		l = b[s + 0];
		l |= ((long) b[s + 1] << 8);
		l |= ((long) b[s + 2] << 16);
		l |= ((long) b[s + 3] << 24);
		return l;
	}

	public static void putLong8(byte[] bb, long x, int index) {
		bb[index + 7] = (byte) (x >> 56);
		bb[index + 6] = (byte) (x >> 48);
		bb[index + 5] = (byte) (x >> 40);
		bb[index + 4] = (byte) (x >> 32);
		bb[index + 3] = (byte) (x >> 24);
		bb[index + 2] = (byte) (x >> 16);
		bb[index + 1] = (byte) (x >> 8);
		bb[index + 0] = (byte) (x >> 0);
	}

	public static long getLong8(byte[] bb, int index) {
		return ((((long) bb[index + 7] & 0xff) << 56) //
				| (((long) bb[index + 6] & 0xff) << 48) //
				| (((long) bb[index + 5] & 0xff) << 40) //
				| (((long) bb[index + 4] & 0xff) << 32) //
				| (((long) bb[index + 3] & 0xff) << 24) //
				| (((long) bb[index + 2] & 0xff) << 16) //
				| (((long) bb[index + 1] & 0xff) << 8) //
				| (((long) bb[index + 0] & 0xff) << 0)//
		);
	}
	// =============================================================
	// =============================================================
	// =============================================================

}
