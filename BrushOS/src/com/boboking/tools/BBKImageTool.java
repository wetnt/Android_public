package com.boboking.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.View;

public class BBKImageTool {

	// ===============================================================================================
	// ===============================================================================================
	// ===============================================================================================
	public static byte[] Bitmap2PngBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static byte[] Bitmap2JpgBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b != null && b.length > 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	// ===============================================================================================
	// ===============================================================================================
	// ===============================================================================================

	public static void savePngOrJpg(Bitmap bitmap, String filename, String type) {
		try {// 存储路径
			FileOutputStream fOut = new FileOutputStream(filename);
			if (type.toUpperCase(Locale.ENGLISH).indexOf("JPEG") < 0)
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			else
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			d.s(e.getMessage());
			e.printStackTrace();
		}
	}

	// ===============================================================================================
	// ===============================================================================================
	// ===============================================================================================

	public static Bitmap drawable2Bitmap(Drawable drawable) {
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 把一个View的对象转换成bitmap
	 */
	public static Bitmap view2Bitmap(View v) {
		v.clearFocus();
		v.setPressed(false);

		// 能画缓存就返回false
		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);
		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			// Log.e("BtPrinter", "failed getViewBitmap(" + v + ")", new
			// RuntimeException());
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);
		return bitmap;
	}

	// ===============================================================================================
	// ===============================================================================================
	// ===============================================================================================
}
