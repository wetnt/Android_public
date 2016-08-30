package com.zhsk.bbktool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.media.ThumbnailUtils;

public class BBK_Tool_Bmp {

	// ===============================================================================================
	// ===============================================================================================
	// ===============================================================================================
	public static byte[] Bitmap2BmpBytes(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		// 位图大小
		int nBmpWidth = bitmap.getWidth();
		int nBmpHeight = bitmap.getHeight();
		// 图像数据大小
		int bufferSize = nBmpHeight * (nBmpWidth * 3 + nBmpWidth % 4);
		try {
			ByteArrayOutputStream fileos = new ByteArrayOutputStream();
			// bmp文件头
			int bfType = 0x4d42;
			long bfSize = 14 + 40 + bufferSize;
			int bfReserved1 = 0;
			int bfReserved2 = 0;
			long bfOffBits = 14 + 40;
			// 保存bmp文件头
			writeWord(fileos, bfType);
			writeDword(fileos, bfSize);
			writeWord(fileos, bfReserved1);
			writeWord(fileos, bfReserved2);
			writeDword(fileos, bfOffBits);
			// bmp信息头
			long biSize = 40L;
			long biWidth = nBmpWidth;
			long biHeight = nBmpHeight;
			int biPlanes = 1;
			int biBitCount = 24;
			long biCompression = 0L;
			long biSizeImage = 0L;
			long biXpelsPerMeter = 0L;
			long biYPelsPerMeter = 0L;
			long biClrUsed = 0L;
			long biClrImportant = 0L;
			// 保存bmp信息头
			writeDword(fileos, biSize);
			writeLong(fileos, biWidth);
			writeLong(fileos, biHeight);
			writeWord(fileos, biPlanes);
			writeWord(fileos, biBitCount);
			writeDword(fileos, biCompression);
			writeDword(fileos, biSizeImage);
			writeLong(fileos, biXpelsPerMeter);
			writeLong(fileos, biYPelsPerMeter);
			writeDword(fileos, biClrUsed);
			writeDword(fileos, biClrImportant);
			// 像素扫描
			byte bmpData[] = new byte[bufferSize];
			int wWidth = (nBmpWidth * 3 + nBmpWidth % 4);
			for (int nCol = 0, nRealCol = nBmpHeight - 1; nCol < nBmpHeight; ++nCol, --nRealCol)
				for (int wRow = 0, wByteIdex = 0; wRow < nBmpWidth; wRow++, wByteIdex += 3) {
					int clr = bitmap.getPixel(wRow, nCol);
					bmpData[nRealCol * wWidth + wByteIdex] = (byte) Color.blue(clr);
					bmpData[nRealCol * wWidth + wByteIdex + 1] = (byte) Color.green(clr);
					bmpData[nRealCol * wWidth + wByteIdex + 2] = (byte) Color.red(clr);
				}

			fileos.write(bmpData);
			fileos.flush();
			fileos.close();

			return fileos.toByteArray();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ===============================================================================================
	// ===============================================================================================
	// ===============================================================================================
	/**
	 * 将彩色图转换为黑白图
	 * 
	 * @param 位图
	 * @return 返回转换好的位图
	 */
	public static Bitmap convertToBlackWhite(Bitmap bmp) {
		int width = bmp.getWidth(); // 获取位图的宽
		int height = bmp.getHeight(); // 获取位图的高
		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
		newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
		Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, 380, 460);
		return resizeBmp;
	}

	// ===============================================================================================
	// ===============================================================================================
	/**
	 * 将Bitmap存为 .bmp格式图片
	 * 
	 * @param bitmap
	 */
	public static void saveBmp(Bitmap bitmap, String filename) {
		if (bitmap == null)
			return;
		// 位图大小
		int nBmpWidth = bitmap.getWidth();
		int nBmpHeight = bitmap.getHeight();
		// 图像数据大小
		int bufferSize = nBmpHeight * (nBmpWidth * 3 + nBmpWidth % 4);
		try {
			// 存储文件名
			// String filename = "/sdcard/test.bmp";
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fileos = new FileOutputStream(filename);
			// bmp文件头
			int bfType = 0x4d42;
			long bfSize = 14 + 40 + bufferSize;
			int bfReserved1 = 0;
			int bfReserved2 = 0;
			long bfOffBits = 14 + 40;
			// 保存bmp文件头
			writeWord(fileos, bfType);
			writeDword(fileos, bfSize);
			writeWord(fileos, bfReserved1);
			writeWord(fileos, bfReserved2);
			writeDword(fileos, bfOffBits);
			// bmp信息头
			long biSize = 40L;
			long biWidth = nBmpWidth;
			long biHeight = nBmpHeight;
			int biPlanes = 1;
			int biBitCount = 24;
			long biCompression = 0L;
			long biSizeImage = 0L;
			long biXpelsPerMeter = 0L;
			long biYPelsPerMeter = 0L;
			long biClrUsed = 0L;
			long biClrImportant = 0L;
			// 保存bmp信息头
			writeDword(fileos, biSize);
			writeLong(fileos, biWidth);
			writeLong(fileos, biHeight);
			writeWord(fileos, biPlanes);
			writeWord(fileos, biBitCount);
			writeDword(fileos, biCompression);
			writeDword(fileos, biSizeImage);
			writeLong(fileos, biXpelsPerMeter);
			writeLong(fileos, biYPelsPerMeter);
			writeDword(fileos, biClrUsed);
			writeDword(fileos, biClrImportant);
			// 像素扫描
			byte bmpData[] = new byte[bufferSize];
			int wWidth = (nBmpWidth * 3 + nBmpWidth % 4);
			for (int nCol = 0, nRealCol = nBmpHeight - 1; nCol < nBmpHeight; ++nCol, --nRealCol)
				for (int wRow = 0, wByteIdex = 0; wRow < nBmpWidth; wRow++, wByteIdex += 3) {
					int clr = bitmap.getPixel(wRow, nCol);
					bmpData[nRealCol * wWidth + wByteIdex] = (byte) Color.blue(clr);
					bmpData[nRealCol * wWidth + wByteIdex + 1] = (byte) Color.green(clr);
					bmpData[nRealCol * wWidth + wByteIdex + 2] = (byte) Color.red(clr);
				}

			fileos.write(bmpData);
			fileos.flush();
			fileos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static void writeWord(OutputStream stream, int value) throws IOException {
		byte[] b = new byte[2];
		b[0] = (byte) (value & 0xff);
		b[1] = (byte) (value >> 8 & 0xff);
		stream.write(b);
	}

	protected static void writeDword(OutputStream stream, long value) throws IOException {
		byte[] b = new byte[4];
		b[0] = (byte) (value & 0xff);
		b[1] = (byte) (value >> 8 & 0xff);
		b[2] = (byte) (value >> 16 & 0xff);
		b[3] = (byte) (value >> 24 & 0xff);
		stream.write(b);
	}

	protected static void writeLong(OutputStream stream, long value) throws IOException {
		byte[] b = new byte[4];
		b[0] = (byte) (value & 0xff);
		b[1] = (byte) (value >> 8 & 0xff);
		b[2] = (byte) (value >> 16 & 0xff);
		b[3] = (byte) (value >> 24 & 0xff);
		stream.write(b);
	}

}
