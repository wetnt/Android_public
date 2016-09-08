package testBox;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;

import com.boboking.swt.BBKSwtMath;
import com.boboking.swt.BBKSwtMath.Point;
import com.zhsk.bbktool.d;

import airLinex.mainG;
import testBox.AirLine_Point.TURN_TYPE;
import testBox.AirLine_Point.airLine_Point;

public class BBKWorkDraw {

	// ===========================================================================
	private static int winw, winh;

	public static void testInit() {
		Random_AirLine();
		AirLine_Point_Calculate();
	}

	public static void testMove(double x, double y) {
		pDragIds = getDragId((int) x, (int) y);
		if (pDragIds != -999) {
			al.get(pDragIds).p.x = x;
			al.get(pDragIds).p.y = y;
		}
		AirLine_Point_Calculate();
	}

	public static void calculate() {
		AirLine_Point_Calculate();
	}

	public static void testDraw() {
		//--------------------------------------------------------------------
		//d.s("win=" + w + "x" + h);
		//--------------------------------------------------------------------
		draw_AirLine();
		//--------------------------------------------------------------------
	}

	// ===========================================================================
	private static List<airLine_Point> al = new ArrayList<airLine_Point>();
	private final static int AirLine_Length = 6;
	private final static int pTurnR_Max = 100;
	private final static int pTurnR_Min = 30;

	@SuppressWarnings("unused")
	public static void Random_AirLine() {
		al.clear();
		if (false) {
			new_airLine_Point(87, 248, pTurnR, TURN_TYPE.REEL_AUTO);
			new_airLine_Point(359, 308, pTurnR, TURN_TYPE.REEL_AUTO);
			new_airLine_Point(595, 253, pTurnR, TURN_TYPE.REEL_AUTO);
			new_airLine_Point(604, 102, pTurnR, TURN_TYPE.REEL_AUTO);
			new_airLine_Point(386, 37, pTurnR, TURN_TYPE.REEL_AUTO);
		}
		if (false) {
			new_airLine_Point(65.0, 205.0, pTurnR, TURN_TYPE.REEL_AUTO);
			new_airLine_Point(402.0, 483.0, pTurnR, TURN_TYPE.REEL_AUTO);
			new_airLine_Point(592.0, 296.0, pTurnR, TURN_TYPE.REEL_AUTO);
			new_airLine_Point(1143.0, 380.0, pTurnR, TURN_TYPE.REEL_AUTO);
			new_airLine_Point(885.0, 103.0, pTurnR, TURN_TYPE.REEL_AUTO);
		}
		if (false) {
			new_airLine_Point(129.0, 237.0, BBKSwtMath.random(pTurnR_Min, pTurnR_Max), TURN_TYPE.REEL_AUTO);
			new_airLine_Point(276.0, 142.0, BBKSwtMath.random(pTurnR_Min, pTurnR_Max), TURN_TYPE.REEL_AUTO);
			new_airLine_Point(566.0, 181.0, BBKSwtMath.random(pTurnR_Min, pTurnR_Max), TURN_TYPE.REEL_AUTO);
			new_airLine_Point(762.0, 286.0, BBKSwtMath.random(pTurnR_Min, pTurnR_Max), TURN_TYPE.REEL_AUTO);
			new_airLine_Point(930.0, 449.0, BBKSwtMath.random(pTurnR_Min, pTurnR_Max), TURN_TYPE.REEL_AUTO);
			new_airLine_Point(1111.0, 186.0, BBKSwtMath.random(pTurnR_Min, pTurnR_Max), TURN_TYPE.REEL_AUTO);
		}

		if (false) {

			int w = winw - 100;
			int h = winh - 200;

			for (int i = 0; i < AirLine_Length; i++) {
				double x = BBKSwtMath.random(pTurnR, w - pTurnR);
				double y = BBKSwtMath.random(pTurnR, h - pTurnR);
				double r = pTurnR;
				//int r = (int) BBKSwtMath.random(pTurnR_Min, pTurnR_Max);//.random(pTurnR_Max, pTurnR_Min);
				//new_airLine_Point(x, y, getRandom_TurnType());
				new_airLine_Point(x, y, r, TURN_TYPE.REEL_AUTO);
				//d.s("ran=" + x + "," + y);
				//d.s(p.t.toString());
			}
		}

		new_airLine_Point(-503.0, 94.0, pTurnR, TURN_TYPE.REEL_AUTO);
		new_airLine_Point(64.0, 166.0, pTurnR, TURN_TYPE.REEL_AUTO);
		new_airLine_Point(-168.0, -136.0, pTurnR, TURN_TYPE.REEL_AUTO);
		new_airLine_Point(-194.0, 122.0, pTurnR, TURN_TYPE.REEL_AUTO);
		new_airLine_Point(354.0, 59.0, 100.0, TURN_TYPE.REEL_AUTO);
//		new_airLine_Point(476.0, -209.0, 100.0, TURN_TYPE.REEL_AUTO);

	}

	private static void new_airLine_Point(double x, double y, double r, TURN_TYPE t) {
		airLine_Point p = new airLine_Point(x, y, r, t);
		al.add(p);
	}

	// ===========================================================================
	// ===========================================================================
	// ===========================================================================
	//http://blog.csdn.net/aaabendan/article/details/5070327
	@SuppressWarnings("unused")
	private static void draw_AirLine() {
		//--------------------------------------------------------------------
		for (int i = 0; i < al.size(); i++)
			draw_AirLine_Point(al.get(i), i);
		//--------------------------------------------------------------------
		for (int i = 0; i < al.size() - 1; i++)
			draw_AirLine_Point_Segment(al.get(i), al.get(i + 1));
		//--------------------------------------------------------------------
		for (int i = 0; i < al.size() - 1; i++)
			draw_line(al.get(i).t, al.get(i + 1).f, pLineR, mainG.cr);
		//--------------------------------------------------------------------
	}

	private final static int pLineR = 5;
	private final static int pTurnR = 100;
	private final static int pDrawR = 10;

	private static void draw_AirLine_Point(airLine_Point p, int i) {
		draw_AirLine_Point_p(p, i);
		draw_AirLine_Point_po(p, i);
	}

	private static void draw_AirLine_Point_p(airLine_Point p, int i) {
		//--------------------------------------------------------------------
		draw_point(p.p, pDrawR, 1, mainG.cm, i + "");
		//draw_line(p.f, p.p, 1, mainG.cm);
		//--------------------------------------------------------------------
		if (p.isFirst)//首点标记
			draw_point(p.p, pDrawR * 2, 1, mainG.cm);
		//--------------------------------------------------------------------
		//mainG.draw_String( p.type.toString(), p.p, mainG.cm);//绘制：转弯方式
		//--------------------------------------------------------------------
		draw_line(p.f, p.o, 1, mainG.cm);//绘制：入弯点到圆心
		draw_line(p.t, p.o, 1, mainG.cm);//绘制：出弯点到圆心
		//--------------------------------------------------------------------
	}

	private static void draw_AirLine_Point_po(airLine_Point p, int i) {
		//--------------------------------------------------------------------
		if (!BBKSwtMath.PointIsOK(p.o))
			return;
		//--------------------------------------------------------------------
		draw_point(p.o, 3, 1, mainG.cm);//p.pot==1?"left":"right"//, "======" + p.pft
		mainG.draw_cercle(p.o, 1, p.r, 1, mainG.cm, "");
		//d.s(1+" "+"p.r="+p.r);
		//--------------------------------------------------------------------
		//draw_point(p.f, 3, 1, mainG.cm, "1");
		//draw_point(p.t, 3, 1, mainG.cm, "2");
		//--------------------------------------------------------------------		
		mainG.draw_arc(p.o, p.r, pLineR, p.fd, p.td, p.turn, mainG.cr);
		//--------------------------------------------------------------------
		mainG.draw_String(p.turn == -1 ? (i + "顺") : (i + "逆"), new Point(p.o.x, p.o.y - 30), mainG.cr);

		//		draw_point( p.pof, 3, 1, mainG.color_p);
		//		//draw_point( new Point(p.po.x, p.po.y - 100), 3, 1, color_p);
		//
		//		int mAngle = (int) BBKSwtMath.fomartDegrees(BBKSwtMath.point_Angle_Deg(p.poo, p.plo, p.pno));
		//		mAngle = Math.min(mAngle, 360 - mAngle);
		//
		//		int arc_starts = (int) BBKSwtMath.fomartDegrees(-BBKSwtMath.point_Angle_Deg(p.poo, p.pof, new Point(p.poo.x + 100, p.poo.y)));
		//		int arc_length = (int) BBKSwtMath.fomartDegrees(BBKSwtMath.point_Angle_Deg(p.poo, p.pof, p.pno));
		//		//arc_length=Math.min(arc_length, 360-arc_length);
		//
		//		if (mAngle < 90) {
		//			arc_length = 360 - arc_length;
		//			arc_length = -arc_length;
		//		}
		//
		//		mainG.draw_arc( p.poo, pTurnR, 6, arc_starts, arc_length, mainG.color_yl);
		//		main.txtShow(arc_starts + "," + arc_length);

		mainG.draw_String((int) p.fd + "", p.f, mainG.cr);
		mainG.draw_String((int) p.td + "", p.t, mainG.cr);

	}

	private static void draw_AirLine_Point_Segment(airLine_Point pa, airLine_Point pb) {
		draw_line(pa.p, pb.p, 1, mainG.cm);
	}

	// ===========================================================================
	// ===========================================================================
	public static int Calculate_outTime = 1;

	public static void AirLine_Point_Calculate() {
		AirLine_Point_Clear();
		int k = 0;
		long t = System.currentTimeMillis();
		long f = 0;
		do {
			k++;
			//d.s("AirLine_Point_Calculate() run = "+k);
			//----------------------------------------------------------------------------------------------------
			int n = al.size();
			AirLine_Point_Calculate.AirLine_Point_Calculate_set(null, al.get(0), al.get(1), 0);
			AirLine_Point_Calculate.AirLine_Point_Calculate_set(al.get(n - 2), al.get(n - 1), null, n - 2);
			for (int i = 1; i < n - 1; i++) {
				AirLine_Point_Calculate.AirLine_Point_Calculate_set(al.get(i - 1), al.get(i), al.get(i + 1), i);
			}
			//----------------------------------------------------------------------------------------------------
			if (k > 100)
				break;
			//----------------------------------------------------------------------------------------------------
			//d.s((System.currentTimeMillis() - t) / 1000 + "");
			f = (System.currentTimeMillis() - t) / 1000;
			if (f >= Calculate_outTime)
				break;
			//----------------------------------------------------------------------------------------------------		
		} while (!AirLine_Is_Calculate_OK());
		//----------------------------------------------------------------------------------------------------	
		d.s(f + "\t===\t" + t + "--" + System.currentTimeMillis());
		//----------------------------------------------------------------------------------------------------	
		//----------------------------------------------------------------------------------------------------	
	}

	public static void AirLine_Point_Clear() {
		//----------------------------------------------------------------------------------------------------	
		for (int i = 0; i < al.size(); i++) {
			al.get(i).clear();
		}
		//----------------------------------------------------------------------------------------------------	
	}

	public static boolean AirLine_Is_Calculate_OK() {
		int n = al.size();
		for (int i = 0; i < n; i++) {
			boolean k = AirLine_Point_Is_Calculate_OK(al.get(i));
			if (!k)
				return false;
		}
		return true;
	}

	public static boolean AirLine_Point_Is_Calculate_OK(airLine_Point p) {
		if (p == null)
			return false;
		if (p.o == null || !BBKSwtMath.PointIsOK(p.o))
			return false;
		if (p.f == null || !BBKSwtMath.PointIsOK(p.f))
			return false;
		if (p.t == null || !BBKSwtMath.PointIsOK(p.t))
			return false;
		if (p.fd == 0 && p.td == 0)
			return false;
		return true;
	}

	// ===========================================================================
	// ===========================================================================
	private static int pDragIds = -999;

	private static int getDragId(int x, int y) {
		Rectangle r = new Rectangle(x - pDrawR, y - pDrawR, x + pDrawR, y + pDrawR);
		for (int i = 0; i < al.size(); i++) {
			if (Math.abs(al.get(i).p.x - x) < pDrawR && Math.abs(al.get(i).p.y - y) < pDrawR) {
				d.s("pDragKey = " + i + "\t new_airLine_Point(" + al.get(i).p.x + "," + al.get(i).p.y + "," + al.get(i).r + "," + al.get(i).type.toString() + "); \t==" + r.toString());
				return i;
			}
		}
		return -999;
	}

	// ===========================================================================
	// ===========================================================================
	// ===========================================================================
	private static void draw_line(Point a, Point b, int w, Color c) {
		if (BBKSwtMath.PointIsOK(a) && BBKSwtMath.PointIsOK(b))
			mainG.draw_line(a, b, w, c);
	}

	private static void draw_point(Point p, double r, int w, Color c) {
		if (BBKSwtMath.PointIsOK(p))
			mainG.draw_point(p, r, w, c);
	}

	private static void draw_point(Point p, double r, int w, Color c, String s) {
		if (BBKSwtMath.PointIsOK(p))
			mainG.draw_point(p, r, w, c, s);
	}
	// ===========================================================================
	// ===========================================================================
	// ===========================================================================

}
