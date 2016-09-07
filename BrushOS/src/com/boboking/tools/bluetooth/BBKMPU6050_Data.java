package com.boboking.tools.bluetooth;

import com.boboking.tools.d;

public class BBKMPU6050_Data {

	// ---------------------------------------------------------
	private static byte Re_buf[] = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };// 11
	// -------------------实时的原始记录--------------------------------------
	private static BBKMPU6050_Type acc = new BBKMPU6050_Type();
	private static BBKMPU6050_Type wGravity = new BBKMPU6050_Type();
	private static BBKMPU6050_Type angle = new BBKMPU6050_Type();
	@SuppressWarnings("unused")
	private static float T = 0f;
	// ---------------------------------------------------------
	private static BBKMPU6050_Type angle_Last = new BBKMPU6050_Type();// 每秒刷新N次，上一次的原始记录
	private static BBKMPU6050_Type angle_Sums = new BBKMPU6050_Type();// 每秒刷新N次，振幅差值记录
	@SuppressWarnings("unused")
	private static int angleSumInt_X, angleSumInt_Y, angleSumInt_Z;
	public static int angleSumInt_XYZ;
	// ---------------------------------------------------------
	private static boolean mFirst = true;

	public static void BufExp(byte[] buf) {// 原始帧数据解析
		try {
			// ---------------------------------------------------------
			// d.s("buf.length == " + buf.length);
			// ---------------------------------------------------------
			int k = GetStartIndex(buf);
			if (k < 0)
				return;
			for (int i = 0; i < 11; i++) {
				Re_buf[i] = buf[k + i];
			}
			// ---------------------------------------------------------
			switch (Re_buf[1]) {
			case 0x51:
				acc.x = ((short) (Re_buf[3] << 8 | Re_buf[2])) / 32768.0f * 16f;
				acc.y = ((short) (Re_buf[5] << 8 | Re_buf[4])) / 32768.0f * 16f;
				acc.z = ((short) (Re_buf[7] << 8 | Re_buf[6])) / 32768.0f * 16f;
				T = ((short) (Re_buf[9] << 8 | Re_buf[8])) / 340.0f + 36.25f;
				break;
			case 0x52:
				wGravity.x = ((short) (Re_buf[3] << 8 | Re_buf[2])) / 32768.0f * 2000f;
				wGravity.y = ((short) (Re_buf[5] << 8 | Re_buf[4])) / 32768.0f * 2000f;
				wGravity.z = ((short) (Re_buf[7] << 8 | Re_buf[6])) / 32768.0f * 2000f;
				T = ((short) (Re_buf[9] << 8 | Re_buf[8])) / 340.0f + 36.25f;
				break;
			case 0x53:
				angle.x = ((short) (Re_buf[3] << 8 | Re_buf[2])) / 32768.0f * 180f;
				angle.y = ((short) (Re_buf[5] << 8 | Re_buf[4])) / 32768.0f * 180f;
				angle.z = ((short) (Re_buf[7] << 8 | Re_buf[6])) / 32768.0f * 180f;
				T = ((short) (Re_buf[9] << 8 | Re_buf[8])) / 340.0f + 36.25f;
				break;
			}
			// ---------------------------------------------------------
			// d.s("a="+a[0]+","+a[1]+","+a[2]+"\t w="+w[0]+","+w[1]+","+a[2]+"\t angle="+angle[0]+","+angle[1]+","+angle[2]+"\t T="+T);
			// d.s("\t angle="+ag[0]+",\t"+ag[1]+",\t"+ag[2]+"\t T="+T);
			// ---------------------------------------------------------
			AddAngleSum(angle);// 刷新角度数据
			// ---------------------------------------------------------
		} catch (Exception e) {
			d.s(e.getMessage());
		}
	}

	private static int GetStartIndex(byte[] buf) {
		for (int i = 0; i < 100; i++) {
			if (buf[i] == 0x55 && buf[i + 1] == 0x53) {
				return i;
			}
		}
		return -1;
	}

	// ===============================================================================================
	// ===============================================================================================
	// ===============================================================================================

	private static void AddAngleSum(BBKMPU6050_Type k) {// 刷新角度数据
		if (mFirst) {
			angle_Last = k.clone();
			mFirst = false;
		}
		angle_Sums.x += Math.abs(k.x - angle_Last.x);
		angle_Sums.y += Math.abs(k.y - angle_Last.y);
		angle_Sums.z += Math.abs(k.z - angle_Last.z);
		angle_Last = k.clone();
	}

	public static void Update_Sum_Data() {
		angleSumInt_X = (int) angle_Sums.x;
		angleSumInt_Y = (int) angle_Sums.y;
		angleSumInt_Z = (int) angle_Sums.z;
		angleSumInt_XYZ = (int) angle_Sums.sum();
		angle_Sums.clear();// 清空进入下一秒的记录
		// d.s("BBKMPU6050_Data = " + angleSumInt_X + ",\t" + angleSumInt_Y +
		// ",\t" + angleSumInt_Z + ",\t== " + angleSumInt_XYZ);
	}

}

class BBKMPU6050_Type {
	float x, y, z;

	public BBKMPU6050_Type() {
		x = 0f;
		y = 0f;
		z = 0f;
	}

	public float sum() {
		return x + y + z;
	}

	public void clear() {
		x = 0f;
		y = 0f;
		z = 0f;
	}

	public BBKMPU6050_Type clone() {
		BBKMPU6050_Type m = new BBKMPU6050_Type();
		m.x = x;
		m.y = y;
		m.z = z;
		return m;
	}
}
