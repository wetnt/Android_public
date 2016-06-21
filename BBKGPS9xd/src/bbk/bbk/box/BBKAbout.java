package bbk.bbk.box;

import bbk.sys.abc.BBKMsgBox;

public class BBKAbout {

	// ====================================================================================
	public final static String SoftTitle = "BBK GPS V9X";
	public final static String SoftYesOKs = "确定";
	public final static String SoftCopyRitht = "@boboking.com";// "@www.boboking.com 2014";
	public static String SoftAbout = "";

	// ====================================================================================
	public static void BBKAboutSet() {
		// ------------------------------------------------------
		SoftAbout = "";
		SoftAbout += SoftTitle + " for Android!" + "\r\n" + "\r\n";
		SoftAbout += "作者： 许晋平" + "\r\n";
		SoftAbout += "昵称： BOBOKing  海云飞" + "\r\n";
		SoftAbout += "邮箱： wetnt@sina.com" + "\r\n";
		SoftAbout += "电话： 13911969356" + "\r\n";
		SoftAbout += "日期： 2016.6.21";
		// ------------------------------------------------------
	}

	// ====================================================================================
	public static void SoftAboutShow() {
		BBKMsgBox.tShow(SoftTitle);
		BBKMsgBox.MsgOK(SoftAbout);
	}
	// ====================================================================================
}