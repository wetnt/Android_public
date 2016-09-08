package com.boboking.map;

import org.eclipse.swt.graphics.GC;

import com.boboking.swt.BBKSwtMath;
import com.boboking.swt.BBKSwtMath.AirLineDrawPoint;
import com.boboking.swt.BBKSwtMath.Point;

import airLinex.mainG;

public class TestDraw3 {

	static Point pa, pb;
	static double ra = 80;
	static double rb = 80;

	public static void testRandom() {

		//		ra = (int) BBKSwtMath.random(50, 200);
		//		rb = (int) BBKSwtMath.random(50, 200);
		//		pa = new Point(400, 300);
		//		pb = new Point(900, 400);

		ra = 37.46;
		rb = 37.46;
		pa = new Point(200 + 0, 200 + 0);
		pb = new Point(200 - 83.04, 200 + 274.08);

	}

	public static void testMove(double x, double y) {
		if (Math.abs(pa.x - x) < 10 && Math.abs(pa.y - y) < 10) {
			pa.x = x;
			pa.y = y;
		}
		if (Math.abs(pb.x - x) < 10 && Math.abs(pb.y - y) < 10) {
			pb.x = x;
			pb.y = y;
		}
	}

	public static void testDraw(GC g) {
		//--------------------------------------------------------------------
		mainG.draw_point(pa, 2, 2, mainG.cr, "pa");
		mainG.draw_point(pb, 2, 2, mainG.cr, "pb");
		mainG.draw_cercle(pa, 2, ra, 1, mainG.cm, "");
		mainG.draw_cercle(pb, 2, rb, 1, mainG.cm, "");
		//--------------------------------------------------------------------
		AirLineDrawPoint p = BBKSwtMath.CercleTwo_QieXian(pa, ra, pb, rb);
		//--------------------------------------------------------------------
		mainG.draw_point(p.anl, 2, 2, mainG.cr, "anl");
		mainG.draw_point(p.anr, 2, 2, mainG.cr, "anr");
		mainG.draw_point(p.awl, 2, 2, mainG.cr, "awl");
		mainG.draw_point(p.awr, 2, 2, mainG.cr, "awr");
		mainG.draw_point(p.bnl, 2, 2, mainG.cr, "bnl");
		mainG.draw_point(p.bnr, 2, 2, mainG.cr, "bnr");
		mainG.draw_point(p.bwl, 2, 2, mainG.cr, "bwl");
		mainG.draw_point(p.bwr, 2, 2, mainG.cr, "bwr");
		//--------------------------------------------------------------------
		mainG.draw_line(p.anl, p.bnr, 2, mainG.cr);
		mainG.draw_line(p.anr, p.bnl, 2, mainG.cr);
		mainG.draw_line(p.awl, p.bwl, 2, mainG.cr);
		mainG.draw_line(p.awr, p.bwr, 2, mainG.cr);
		//--------------------------------------------------------------------
	}

}
