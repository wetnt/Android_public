package bbk.map.abc;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

public class BBKMapImage {

	// =====================================================================================
	public static Bitmap Map, Pic, Lgo;
	private static Canvas mapgc;
	public static Paint Reds, Blue;
	public static Paint RedEI, BluEI;
	public static Paint YlowA;

	private static Config bmpCfg = Config.RGB_565;
	private static int whiteColor = 0xFFFFFFFF;
	private static int mapTextS = 24, mapLineW = 3;

	// =====================================================================================
	public static void PaintSet() {
		// ------------------------------------------------------------------
		Reds = colorSet(Color.RED, mapTextS, mapLineW, 255);
		Blue = colorSet(Color.BLUE, mapTextS, mapLineW, 255);
		RedEI = colorSet(Color.RED, mapTextS, 1, 255);
		RedEI.setStyle(Paint.Style.STROKE);
		BluEI = colorSet(Color.BLUE, mapTextS, 1, 255);
		BluEI.setStyle(Paint.Style.STROKE);
		YlowA = colorSet(Color.YELLOW, mapTextS, mapLineW, 180);
		// ------------------------------------------------------------------
	}

	public static Paint colorSet(int color, int TextSize, int LineW, int Alpha) {
		// ----------------------------------------------------
		Paint p = new Paint();
		// ----------------------------------------------------
		p.setColor(color); // 设置的颜色Color.RED
		// ----------------------------------------------------
		p.setAntiAlias(true);// 去锯齿
		p.setStyle(Paint.Style.FILL); // Paint.Style.STROKE设置为空心
		p.setStrokeWidth(LineW); // 设置外框宽度
		p.setFilterBitmap(true); // 对位图抗锯齿滤波
		p.setTextSize(TextSize); // 字体尺寸
		p.setSubpixelText(true); // 文字抗锯齿
		p.setTextAlign(Align.LEFT); // 字体对齐方式
		// ----------------------------------------------------
		p.setAlpha(Alpha);// 透明通道
		// ----------------------------------------------------
		return p;
		// ----------------------------------------------------
	}

	public static void BitmapSet(int w, int h) {
		// ------------------------------------------------------------------
		Lgo = SetLogPic();
		Pic = CreateBitmap(256, 256);
		Map = CreateBitmap(w, h);
		// ------------------------------------------------------------------
		mapgc = new Canvas();
		mapgc.setBitmap(Map);
		mapgc.drawColor(whiteColor);
		// ------------------------------------------------------------------
		BitmapClr();
		// ------------------------------------------------------------------
	}

	public static void BitmapClr() {
		mapgc.drawColor(whiteColor);
	}

	public static void BBKMapDrawImage256(Bitmap p, int x, int y, float zoom, boolean del) {
		// ----------------------------------------------------
		Rect src = new Rect(0, 0, 256, 256);
		RectF dst = new RectF(x, y, x + 256 * zoom, y + 256 * zoom);
		mapgc.drawBitmap(p, src, dst, null);
		// mapgc.drawBitmap(p, x, y, null);
		if (del)
			p = null;
		// ----------------------------------------------------
	}

	// ==========================================================================================
	public static void DrawPoint(int x, int y) {
		mapgc.drawPoint(x, y, Reds);
	}

	public static void DrawLine(int x, int y, int x1, int y1) {
		mapgc.drawLine(x, y, x1, y1, Reds);
	}

	public static void DrawLineRedI(int x, int y, int x1, int y1) {
		mapgc.drawLine(x, y, x1, y1, RedEI);
	}

	public static void DrawLineBluI(int x, int y, int x1, int y1) {
		mapgc.drawLine(x, y, x1, y1, BluEI);
	}

	public static void DrawCircleRedEI(int x, int y, int r) {
		mapgc.drawCircle(x, y, r, RedEI);
	}

	public static void DrawCircleBluEI(int x, int y, int r) {
		mapgc.drawCircle(x, y, r, BluEI);
	}

	public static void DrawText(String str, int x, int y) {
		mapgc.drawText(str, x, y, Blue);
	}

	public static void DrawGPSArrow(double g, int x, int y, int n, int m, int r, boolean b) {
		// -----------------------------------------------------------------
		mapgc.save();
		mapgc.rotate((float) g, x, y);
		// -----------------------------------------------------------------
		int t = n + m;
		DrawLine(x - n, y + n, x, y - t);
		DrawLine(x + n, y + n, x, y - t);
		DrawLine(x + n, y + n, x - n, y + n);
		DrawCircleBluEI(x, y, r);
		if (b)
			DrawCircleRedEI(x, y, 4);
		// -----------------------------------------------------------------
		mapgc.restore();
		// -----------------------------------------------------------------
	}

	// ==========================================================================================
	public static Bitmap CreateBitmap(int w, int h) {
		return Bitmap.createBitmap(w, h, bmpCfg);
	}

	public static Bitmap SetLogPic() {
		Lgo = CreateBitmap(256, 256);
		Canvas lggc = new Canvas();
		lggc.setBitmap(Lgo);
		lggc.drawColor(whiteColor);
		Paint p = colorSet(Color.GRAY, mapTextS, mapLineW, 50);
		lggc.drawText("BOBOKing Map ...", 10, 100, p);
		lggc = null;
		p = null;
		return Lgo;
	}

	// ==========================================================================================
	public static void DrawLineP(int x, int y, int x1, int y1, Paint p) {
		mapgc.drawLine(x, y, x1, y1, p);
	}

	public static void DrawTextP(String str, int x, int y, Paint p) {
		mapgc.drawText(str, x, y, p);
	}

	public static void DrawCircleP(int x, int y, int r, Paint p) {
		mapgc.drawCircle(x, y, r, p);
	}

	public static void DrawPathP(Path pt, Paint p) {
		mapgc.drawPath(pt, p);
	}

	// ==========================================================================================
	public static void DrawTextPath(Path pt) {
		mapgc.drawPath(pt, YlowA);
	}
	// ==========================================================================================

}