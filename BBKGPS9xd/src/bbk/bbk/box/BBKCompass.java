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
		// vPaint.setStyle(Paint.Style.STROKE); // ����
		// vPaint.setAlpha(75); // Bitmap͸����(0 ~ 100)
		// canvas.drawBitmap ( vBitmap , 50, 100, null ); //��͸��
		// canvas.drawBitmap ( vBitmap , 50, 200, vPaint ); //��͸��
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
		p.setColor(color); // ���õ���ɫColor.RED
		// ----------------------------------------------------
		p.setAntiAlias(true);// ȥ���
		p.setStyle(Paint.Style.FILL); // Paint.Style.STROKE����Ϊ����
		p.setStrokeWidth(LineW); // ���������
		p.setFilterBitmap(true); // ��λͼ������˲�
		p.setTextSize(TextSize); // ����ߴ�
		p.setSubpixelText(true); // ���ֿ����
		p.setTextAlign(Align.LEFT); // ������뷽ʽ
		// ----------------------------------------------------
		p.setAlpha(Alpha);// ͸��ͨ��
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