package airLinex;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import com.boboking.swt.BBKSwtMath;
import com.boboking.swt.BBKSwtMath.Point;

public class mainG {

	// ===========================================================================
	private static GC g = null;
	public static Point p0 = new Point(100, 100);
	// ===========================================================================
	public final static int lm = 1;
	// ===========================================================================
	public final static Color cm = main.display.getSystemColor(SWT.COLOR_BLACK);
	public final static Color c0 = main.display.getSystemColor(SWT.INHERIT_DEFAULT);
	public final static Color cw = main.display.getSystemColor(SWT.COLOR_WHITE);
	public final static Color cb = main.display.getSystemColor(SWT.COLOR_BLUE);
	public final static Color cg = main.display.getSystemColor(SWT.COLOR_GREEN);
	public final static Color cr = main.display.getSystemColor(SWT.COLOR_RED);
	public final static Color ch = main.display.getSystemColor(SWT.COLOR_GRAY);
	public final static Color cy = main.display.getSystemColor(SWT.COLOR_YELLOW);
	// ===========================================================================

	// ===========================================================================
	public final static int hKedu = 5;
	public final static int wKedu = 50;

	public static void drawAxis(GC _g, int w, int h) {
		//--------------------------------------------------------------------
		g = _g;
		p0 = new Point(w / 2, h / 2);
		//--------------------------------------------------------------------
		draw_point(new Point(0, 0), 1, lm, cm);
		draw_String("(0,0)", new Point(0, 0), cm);
		//--------------------------------------------------------------------
		draw_line(new Point(0, -h / 2), new Point(0, h / 2), lm, cm);//X轴
		draw_line(new Point(-w / 2, 0), new Point(w / 2, 0), lm, cm);//Y轴
		//--------------------------------------------------------------------
		for (int i = 0; i < w / 2; i = i + wKedu) {//X轴刻度
			draw_line_raw(i, 0, i, hKedu, lm, cm);
			draw_line_raw(-i, 0, -i, -hKedu, lm, cm);
			if (i != 0) {
				draw_String_raw(i + "", i - 10, -5, cm);
				draw_String_raw("-" + i, -i - 10, -5, cm);
			}
		}
		for (int i = 0; i < h / 2; i = i + wKedu) {//Y轴刻度
			draw_line_raw(0, i, hKedu, i, lm, cm);
			draw_line_raw(0, -i, -hKedu, -i, lm, cm);
			if (i != 0) {
				draw_String_raw("" + i, 10, i + 10, cm);
				draw_String_raw("-" + i, 10, -i + 10, cm);
			}
		}
		draw_String_raw("X+", w / 2 - 20, -20, cm);
		draw_String_raw("Y+", 50, h / 2, cm);
		//--------------------------------------------------------------------
	}

	// ===========================================================================
	// ===========================================================================
	// ===========================================================================
	public static void draw_point(Point p, double r, double w, Color c, String s) {
		draw_point(p, r, w, c);
		draw_String(s, p, c);
	}

	public static void draw_point(Point p, double r, double w, Color c) {
		draw_point_raw(p.x, p.y, r, w, c);
	}

	public static void draw_line(Point p1, Point p2, double w, Color c) {
		draw_line_raw(p1.x, p1.y, p2.x, p2.y, w, c);
	}

	public static void draw_String(String s, Point p, Color c) {
		draw_String_raw(s, (int) p.x, (int) p.y, c);
	}

	public static void draw_cercle(Point o, double c, double r, int w, Color s, String n) {
		draw_String(n, o, s);
		draw_point(o, c, w, s);
		draw_point(o, r, w, s);
	}

	public static void draw_arc(Point p, double r, int w, double sd, double ed, int turn, Color c) {//正北轴向（X轴向）顺时针,s起始角度，e结束角度
		//--------------------------------------------------------------------		
		//绝对正确不要改了！turn==-1顺时针//turn==1//逆时针
		//--------------------------------------------------------------------	
		turn = turn == 0 ? -1 : turn;
		//--------------------------------------------------------------------	
		double s = BBKSwtMath.fomartDegrees(360 - (sd - 90));
		double e = BBKSwtMath.fomartDegrees(360 - (ed - 90));
		double a = turn == 1 ? s : e;
		double l = turn == 1 ? (360 - (s - e)) : (s - e);
		l = BBKSwtMath.fomartDegrees(l);
		//--------------------------------------------------------------------	
		draw_arc_raw(p, r, w, a, l, c);
		//--------------------------------------------------------------------	
	}

	public static double toMx(double x) {
		return x-p0.x;
	}
	public static double toMy(double y) {
		return p0.y - y;
	}
	// ===========================================================================
	// ===========================================================================
	// ===========================================================================
	private static double tox(double x) {
		return p0.x + x;
	}

	private static double toy(double y) {
		return p0.y - y;
	}
	private static void draw_point_raw(double x, double y, double r, double w, Color c) {
		if (g == null)
			return;
		g.setLineWidth((int) w);
		g.setForeground(c);
		g.drawOval((int) (tox(x) - r), (int) (toy(y) - r), (int) r * 2, (int) r * 2);
		g.setForeground(cm);
		g.setLineWidth(lm);
	}

	private static void draw_line_raw(double x1, double y1, double x2, double y2, double w, Color c) {
		if (g == null)
			return;
		g.setLineWidth((int) w);
		g.setForeground(c);
		g.drawLine((int) tox(x1), (int) toy(y1), (int) tox(x2), (int) toy(y2));
		g.setForeground(cm);
		g.setLineWidth(lm);
	}

	private static void draw_String_raw(String s, double x, double y, Color c) {
		if (g == null)
			return;
		g.setForeground(c);
		g.drawString(s, (int) tox(x), (int) toy(y));
		g.setForeground(cm);
	}

	private static void draw_arc_raw(Point p, double r, double w, double s, double l, Color c) {//X轴向顺时针,s起始角度，l旋转绘制角度
		if (g == null)
			return;
		//--------------------------------------------------------------------
		g.setLineWidth((int) w);
		g.setForeground(c);
		//--------------------------------------------------------------------
		g.drawPoint((int) tox(p.x), (int) toy(p.y));
		g.drawArc((int) (tox(p.x) - r), (int) (toy(p.y) - r), (int) r * 2, (int) r * 2, (int) s, (int) l);//x轴向逆时针
		//--------------------------------------------------------------------
		g.setForeground(cm);
		g.setLineWidth(lm);
		//--------------------------------------------------------------------
	}
	// ===========================================================================
	// ===========================================================================
	// ===========================================================================

}
