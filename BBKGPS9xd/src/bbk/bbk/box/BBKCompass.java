package bbk.bbk.box;

import com.example.bbkgps9xd.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import bbk.uis.view.BBKLayView;

public class BBKCompass extends BBKLayView {

	// ====================================================================================
	public void LayInt(final Context context) {
		// ================================================================
		int w = FrameLayout.LayoutParams.WRAP_CONTENT;
		int h = FrameLayout.LayoutParams.WRAP_CONTENT;
		int g = Gravity.TOP | Gravity.RIGHT;
		LayoutInt(context, R.layout.map_compass, w, h, g, 0);
		// ================================================================
		compassSet();
		// ================================================================
	}

	public TextView TxtAcc;
	public ImageView ImgCPS;

	// ====================================================================================
	// ####################################################################################
	// ##############################BBK_Sensor############################################
	// ####################################################################################
	// ====================================================================================
	private Bitmap compassPic;
	private Canvas compassCav;
	private Paint Reds, Rede;
	private final int mapTextS = 24, mapLineW = 3;
	private int w, x, n, t;
	Path mPath = new Path();

	public void compassSet() {
		// ------------------------------------------------------------------
		TxtAcc = (TextView) bbkAct.findViewById(R.id.TxtAcc);
		ImgCPS = (ImageView) bbkAct.findViewById(R.id.ImgCPS);
		// ------------------------------------------------------------------
		w = 100;
		x = (int) (w / 2);
		n = 10;
		t = n + n + n;
		// ------------------------------------------------------------------
		mPath.moveTo(-n, 0);
		mPath.lineTo(0, -t);
		mPath.lineTo(n, 0);
		mPath.lineTo(-n, 0);
		mPath.close();
		// ------------------------------------------------------------------
		compassPic = Bitmap.createBitmap(w, w, Config.ARGB_4444);
		// ------------------------------------------------------------------
		Reds = colorSet(Color.RED, mapTextS, mapLineW, 255);
		Rede = colorSet(Color.RED, mapTextS, mapLineW, 255);
		Rede.setStyle(Paint.Style.STROKE);
		// ------------------------------------------------------------------
		compassCav = new Canvas();
		compassCav.setBitmap(compassPic);
		compassCav.drawColor(Color.TRANSPARENT);
		// compassCav.drawColor(Color.WHITE);
		// ------------------------------------------------------------------
	}

	public void compassFalsh(int f) {
		// -----------------------------------------------------------------
		compassPic = Bitmap.createBitmap(w, w, Config.ARGB_4444);
		compassCav.setBitmap(compassPic);
		compassCav.save();
		// -----------------------------------------------------------------
		compassCav.translate(x, x);
		// -----------------------------------------------------------------
		compassCav.rotate(-f, 0, 0);
		compassCav.drawPath(mPath, Reds);
		compassCav.drawPath(mPath, Rede);
		compassCav.rotate(180, 0, 0);
		compassCav.drawPath(mPath, Rede);
		// -----------------------------------------------------------------
		compassCav.restore();
		// ------------------------------------------------------------------
		ImgCPS.setImageBitmap(compassPic);
		// ------------------------------------------------------------------
	}

	// ====================================================================================
	// ####################################################################################
	// ##############################BBK_Sensor############################################
	// ####################################################################################
	// ====================================================================================

	// Paint vPaint;
	// Matrix mt = new Matrix();
	// Bitmap turnBmp, compass;
	// Canvas turnCav;

	public void PicIconSet() {
		// -----------------------------------------------------------------
		// vPaint = new Paint();
		// vPaint.setStyle(Paint.Style.STROKE); // 空心
		// vPaint.setAlpha(75); // Bitmap透明度(0 ~ 100)
		// canvas.drawBitmap ( vBitmap , 50, 100, null ); //无透明
		// canvas.drawBitmap ( vBitmap , 50, 200, vPaint ); //有透明
		// ------------------------------------------------------------------
		// InputStream is =
		// bbkAct.getResources().openRawResource(R.drawable.bbkcompassa);
		// compass = BitmapFactory.decodeStream(is);
		// ------------------------------------------------------------------
		// turnBmp = Bitmap.createBitmap(w, w, Config.ARGB_4444);
		// turnCav = new Canvas();
		// turnCav.setBitmap(turnBmp);
		// ------------------------------------------------------------------
	}

	public void IconFalsh(int f, int n, int m) {
		// ------------------------------------------------------------------
		// int h = compass.getWidth();
		// float z = (w+0f)/h;
		// BBKDebug.ddd(z+"");
		// Matrix matrix = new Matrix();
		// matrix.setTranslate(0,0);
		// matrix.postScale(z,z);
		// matrix.postRotate(-f,x, x);
		// //compassCav.drawColor(Color.TRANSPARENT);
		// compassCav.drawBitmap(compass, matrix, vPaint);
		// compassCav.drawLine(0, 0, w,w, Reds);
		// compassCav.drawLine(0, w, w, 0, Reds);
		// BBK.myBoxs.ImgCPS.setImageBitmap(compassPic);
		// // ------------------------------------------------------------------

		// ------------------------ok---------------------------------------
		// turnBmp = Bitmap.createBitmap(w, w, Config.ARGB_4444);
		// turnCav = new Canvas();
		// turnCav.setBitmap(turnBmp);
		// // -----------------------------------------------------------------
		// mt.setTranslate(0, 0);
		// mt.postScale(z, z);
		// //mt.postRotate(-f +140, x, x);
		// mt.postRotate(-f , x, x);
		// turnCav.drawBitmap(compass, mt, vPaint);
		// BBK.myBoxs.ImgCPS.setImageBitmap(turnBmp);
		// ------------------------ok---------------------------------------

	}

	// public void compassPicDraw(int x, int y) {
	// // -----------------------------------------------------------------
	// Rect src = new Rect(0, 0, compass.getWidth(), compass.getHeight());
	// RectF dst = new RectF(0, 0, x, y);
	// // compassCav.drawBitmap(compass, src, dst, null);
	// compassCav.drawBitmap(compass, src, dst, vPaint);
	// // ------------------------------------------------------------------
	// }
	// ====================================================================================
	// ####################################################################################
	// ##############################BBK_Sensor############################################
	// ####################################################################################
	// ====================================================================================
	public Paint colorSet(int color, int TextSize, int LineW, int Alpha) {
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

	// ====================================================================================
	// ####################################################################################
	// ##############################BBK_Sensor############################################
	// ####################################################################################
	// ====================================================================================
}