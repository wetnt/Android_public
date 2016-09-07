package com.boboking.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BBKStringCountTool {

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

}
