package com.zhsk.bbktool;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.net.Uri;

public class BBK_Tool_App {

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

	public static void copyAssetsFileToPath(Activity act, String fileName, String filePathName) {
		try {
			OutputStream myOutput = new FileOutputStream(filePathName);
			InputStream myInput = act.getAssets().open(fileName);
			byte[] buffer = new byte[1024];
			int length = myInput.read(buffer);
			while (length > 0) {
				myOutput.write(buffer, 0, length);
				length = myInput.read(buffer);
			}
			myOutput.flush();
			myInput.close();
			myOutput.close();
		} catch (FileNotFoundException e) {
			d.s("sample.pdf --- error");
			e.printStackTrace();
		} catch (IOException e) {
			d.s("sample.pdf --- error");
			e.printStackTrace();
		}
	}
}
