package com.zhsk.bbktool;

import java.io.UnsupportedEncodingException;

public class BBK_Tool_String {

	public static byte[] getBytes_UTF8(String s) {
		byte[] b = null;
		try {
			b = s.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return b;
	}

	public static String getString_UTF8(byte[] b) {
		String s = null;
		try {
			s = new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	// byte[] midbytes=isoString.getBytes("UTF8");
	// byte[] isoret = srt2.getBytes("ISO-8859-1");
	// String isoString = new String(bytes,"ISO-8859-1");//其中ISO-8859-1为单字节的编码
	// String srt2=new String(midbytes,"UTF-8");

}
