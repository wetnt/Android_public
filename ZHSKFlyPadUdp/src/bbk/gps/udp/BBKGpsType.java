package bbk.gps.udp;

public class BBKGpsType {

	public long i = 0; // 8 唯一标识
	public double w = 0; // 8 纬度
	public double j = 0; // 8 经度
	public double h = 0; // 8 高度
	public double v = 0; // 4 速度
	public double f = 0; // 8 方位

	byte[] data = new byte[125];// 8*10+4*10+1*5=125
	private static final String rn = "\r\n";

	public BBKGpsType() {
	}

	public byte[] toBytes() {
		putLong(data, i, 60); // long4唯一标识
		putDouble(data, w, 0); // double8 纬度
		putDouble(data, j, 16);// double8 经度	
		putDouble(data, h, 8); // double8 高度	
		putDouble(data, f, 40);// double8 方位
		putDouble(data, v, 48); // float4 速度
		return data;
	}

	public String toString() {
		// --------------------------------------------------------
		String s = "";
		// --------------------------------------------------------
		s += "标识\t" + i + rn; // 唯一标识
		// --------------------------------------------------------
		s += "纬度\t" + w + rn;// 纬度
		s += "经度\t" + j + rn;// 经度
		s += "高度\t" + h + rn;// 高度
		s += "速度\t" + v + rn; // 速度
		s += "方向\t" + f + rn; // 方向
		// --------------------------------------------------------
		return s;
	}

	// =============================================================
	// =============================================================
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
	public void putInt(byte[] bb, int num, int index) {
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

	public void putLong(byte[] bb, long num, int index) {
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

	public void putFloat(byte[] bb, float x, int index) {
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

	public void putDouble(byte[] bb, double x, int index) {
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
