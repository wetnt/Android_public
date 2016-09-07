package com.boboking.tools;

public class zCode {

	// drawBackground(canvas);// ªÊ÷∆±≥æ∞,“‘±„≤‚ ‘æÿ–Œµƒ”≥…‰
	// private void drawBackground(Canvas canvas) {
	// Path x = new Path();
	// x.moveTo(dstPs[0], dstPs[1]);
	// for (int i = 0; i < dstPs.length - 2; i += 2) {
	// x.lineTo(dstPs[i], dstPs[i + 1]);
	// }
	// x.close();
	// canvas.drawPath(x, paintRect);
	// }

	// ----------------------------------------------------------
	// paintRect = new Paint();
	// paintRect.setColor(Color.RED);
	// paintRect.setAlpha(100);
	// paintRect.setAntiAlias(true);
	// paintRect.setStrokeWidth(30);
	// ----------------------------------------------------------
	// paintRects = new Paint();
	// paintRects.setColor(Color.BLACK);
	// paintRects.setStyle(Paint.Style.STROKE); // ÃÓ≥‰
	// paintRects.setAntiAlias(true);
	// paintRects.setStrokeWidth(20);

	abstract class E {
		public abstract void show();
	}

	class F extends E {
		public void show() {
		}
	}

	void test() {
		E e = new F();
		e.show();
	}

}
