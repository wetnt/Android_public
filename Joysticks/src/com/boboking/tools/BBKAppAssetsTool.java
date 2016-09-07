package com.boboking.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.net.Uri;

public class BBKAppAssetsTool {

	final static int BUFFER_SIZE = 4096;

	public static byte[] InputStream_TO_Bytes(InputStream in) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		try {
			while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
		} catch (IOException e) {
			d.s(e.getMessage());
			e.printStackTrace();
		}
		data = null;
		return outStream.toByteArray();
	}

	public static byte[] getFromAssets(Activity act, int ResId) {
		InputStream in = act.getResources().openRawResource(ResId);
		return InputStream_TO_Bytes(in);
	}

	public static byte[] getFromUri(Activity act, Uri uri) {
		try {
			InputStream in = act.getContentResolver().openInputStream(uri);
			return InputStream_TO_Bytes(in);
		} catch (FileNotFoundException e) {
			d.s(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
