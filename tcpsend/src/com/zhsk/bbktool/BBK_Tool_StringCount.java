package com.zhsk.bbktool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BBK_Tool_StringCount {

	public static void main() {
		String str = "asdvdfdDERE123ABCD0012587我一二三四五";
		System.out.println("Numbers:" + countNumber(str));
		System.out.println("Letters:" + countLetter(str));
		System.out.println("Chinese:" + countChinese(str));
	}

	public static int countNumber(String str) {
		int count = 0;
		Pattern p = Pattern.compile("\\d");
		Matcher m = p.matcher(str);
		while (m.find()) {
			count++;
		}
		return count;
	}

	public static int countLetter(String str) {
		int count = 0;
		Pattern p = Pattern.compile("[a-zA-Z]");
		Matcher m = p.matcher(str);
		while (m.find()) {
			count++;
		}
		return count;
	}

	public static int countChinese(String str) {
		int count = 0;
		Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = p.matcher(str);
		while (m.find()) {
			count++;
		}
		return count;
	}

	public static int GetBasicStringLenth(String txt) {
		int n = BBK_Tool_StringCount.countNumber(txt);
		int l = BBK_Tool_StringCount.countLetter(txt);
		int z = BBK_Tool_StringCount.countChinese(txt);
		float x = (n + l) * 0.6f + z;
		x += (txt.length() - n - l - z) * 0.4f;
		return (int) x;
	}

}
