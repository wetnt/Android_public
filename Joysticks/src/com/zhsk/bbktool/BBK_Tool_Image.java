package com.zhsk.bbktool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

public class BBK_Tool_Image {

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

	public static byte[] Uri2Bytes(Uri uri) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(uri.getPath()));
			byte[] buf = new byte[1024];
			int n;
			while (-1 != (n = fis.read(buf)))
				baos.write(buf, 0, n);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	public static byte[] FilePath2Bytes(String filePathName) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(filePathName));
			byte[] buf = new byte[1024];
			int n;
			while (-1 != (n = fis.read(buf)))
				baos.write(buf, 0, n);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	public static Bitmap BitmapCut(Bitmap b, int w, int h) {
		if (b == null)
			return null;

		int bw = b.getWidth();
		int bh = b.getHeight();
		float z = 1f;
		if (bw > w || bh > h)
			z = Math.min(w / bw, h / bh);
		else
			return b;

		Matrix matrix = new Matrix();
		matrix.postScale(z, z);
		Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w, h, matrix, true);

		return resizeBmp;

	}

	public static Bitmap BitmapCut(String pathName, int w, int h) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		// 这个isjustdecodebounds很重要
		opt.inJustDecodeBounds = true;
		Bitmap bm = BitmapFactory.decodeFile(pathName, opt);
		// 获取到这个图片的原始宽度和高度
		int picWidth = opt.outWidth;
		int picHeight = opt.outHeight;
		// 获取屏的宽度和高度
		// WindowManager windowManager = getWindowManager();
		// Display display = windowManager.getDefaultDisplay();
		// int screenWidth = display.getWidth();
		// int screenHeight = display.getHeight();

		// isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
		opt.inSampleSize = 1;
		// 根据屏的大小和图片大小计算出缩放比例
		if (picWidth > picHeight) {
			if (picWidth > w)
				opt.inSampleSize = picWidth / w;
		} else {
			if (picHeight > h)
				opt.inSampleSize = picHeight / h;
		}
		// 这次再真正地生成一个有像素的，经过缩放了的bitmap
		opt.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(pathName, opt);
		return bm;
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
	public static Bitmap decodeThumbBitmapForFile(String path, int viewWidth, int viewHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 设置为true,表示解析Bitmap对象，该对象不占内存
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// 设置缩放比例
		options.inSampleSize = computeScale(options, viewWidth, viewHeight);
		// 设置为false,解析Bitmap对象加入到内存中
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放
	 * 
	 * @param options
	 * @param width
	 * @param height
	 */
	public static int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight) {
		int inSampleSize = 1;
		if (viewWidth == 0 || viewHeight == 0) {
			return inSampleSize;
		}
		int bitmapWidth = options.outWidth;
		int bitmapHeight = options.outHeight;

		// 假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例
		if (bitmapWidth > viewWidth || bitmapHeight > viewWidth) {
			int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
			int heightScale = Math.round((float) bitmapHeight / (float) viewWidth);

			// 为了保证图片不缩放变形，我们取宽高比例最小的那个
			inSampleSize = widthScale < heightScale ? widthScale : heightScale;
		}
		return inSampleSize;
	}

	// ===============================================================================================
	// ===============================================================================================
	// ===============================================================================================
	public static int[] getImageWH(String filePathName) {
		int[] wh = new int[2];
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;// 不加载bitmap到内存中
		BitmapFactory.decodeFile(filePathName, options);
		wh[0] = options.outWidth;
		wh[1] = options.outHeight;		
		return wh;		
	}
	// ===============================================================================================
	// ===============================================================================================
	// ===============================================================================================
}
