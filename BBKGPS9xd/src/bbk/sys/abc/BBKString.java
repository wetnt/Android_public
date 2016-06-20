package bbk.sys.abc;

import java.util.ArrayList;

public class BBKString {

	public static void maisssdfn() {
		String str1 = "452";
		String str2 = "594";
		String same = theSame(str1, str2);
		System.out.println(same);
	}

	public static String theSame(String str1, String str2) {
		String s = "";
		for (int i = 0; i < str1.length(); i++)
			for (int j = 0; j < str2.length(); j++) {
				if (str1.charAt(i) == str2.charAt(j))
					s = s + str1.charAt(i);
			}
		return s;
	}

	public static String theSameHead(String str1, String str2) {
		String s = "";
		int n = Math.min(str1.length(), str2.length());
		for (int i = 0; i < n; i++) {
			if (str1.charAt(i) == str2.charAt(i)) {
				s = s + str1.charAt(i);
			} else {
				return s;
			}
		}
		return s;
	}

	// ArrayList<String> ת String[]
	String[] trans(ArrayList<String> als) {
		String[] sa = new String[als.size()];
		als.toArray(sa);
		return sa;
	}

	// String[] ת ArrayList<String>
	ArrayList<String> trans(String[] sa) {
		ArrayList<String> als = new ArrayList<String>(0);
		for (int i = 0; i < sa.length; i++) {
			als.add(sa[i]);
		}
		return als;
	}
}
