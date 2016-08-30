package com.zhsk.flypadudp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class zzzzz {
	
	public static byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	public static Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}



	public static byte[] longToByteArray(long num) {
		byte[] result = new byte[8];
		result[0] = (byte) (num >>> 56);// 取最高8位放到0下标
		result[1] = (byte) (num >>> 48);// 取最高8位放到0下标
		result[2] = (byte) (num >>> 40);// 取最高8位放到0下标
		result[3] = (byte) (num >>> 32);// 取最高8位放到0下标
		result[4] = (byte) (num >>> 24);// 取最高8位放到0下标
		result[5] = (byte) (num >>> 16);// 取次高8为放到1下标
		result[6] = (byte) (num >>> 8); // 取次低8位放到2下标
		result[7] = (byte) (num); // 取最低8位放到3下标
		return result;
	}

	public static double getDouble(byte[] b, int index) {
		long l;
		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffl;
		l |= ((long) b[4] << 32);
		l &= 0xffffffffffl;
		l |= ((long) b[5] << 40);
		l &= 0xffffffffffffl;
		l |= ((long) b[6] << 48);
		l &= 0xffffffffffffffl;
		l |= ((long) b[7] << 56);
		return Double.longBitsToDouble(l);
	}
	// ========================================================================================
	// ========================================================================================
	// ========================================================================================

}
