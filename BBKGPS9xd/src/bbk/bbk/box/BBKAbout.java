package bbk.bbk.box;

import bbk.sys.abc.BBKMsgBox;

public class BBKAbout {

	// ====================================================================================
	public final static String SoftTitle = "BBK GPS V9X";
	public final static String SoftYesOKs = "ȷ��";
	public final static String SoftCopyRitht = "@boboking.com";// "@www.boboking.com 2014";
	public static String SoftAbout = "";

	// ====================================================================================
	public static void BBKAboutSet() {
		// ------------------------------------------------------
		SoftAbout = "";
		SoftAbout += SoftTitle + " for Android!" + "\r\n" + "\r\n";
		SoftAbout += "���ߣ� ���ƽ" + "\r\n";
		SoftAbout += "�ǳƣ� BOBOKing  ���Ʒ�" + "\r\n";
		SoftAbout += "���䣺 wetnt@sina.com" + "\r\n";
		SoftAbout += "�绰�� 13911969356" + "\r\n";
		SoftAbout += "���ڣ� 2016.6.6";
		// ------------------------------------------------------
	}

	// ====================================================================================
	public static void SoftAboutShow() {
		BBKMsgBox.tShow(SoftTitle);
		BBKMsgBox.MsgOK(SoftAbout);
	}
	// ====================================================================================
}