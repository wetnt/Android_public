package testBox;

import com.boboking.swt.BBKSwtMath;
import com.boboking.swt.BBKSwtMath.AirLineDrawPoint;
import com.boboking.swt.BBKSwtMath.Point;

import airLinex.mainG;
import airLinex.main;

public class Test_MathGeo {

	static Point po, pa, pb, pc, pd;
	static double ra = 80;
	static double rb = 80;
	static double ro = 80;

	// ===========================================================================
	static double wMax = 600, hMax = 300;

	public static Point RandomPoint() {
		return new Point(BBKSwtMath.random(-wMax, wMax), -BBKSwtMath.random(-hMax, hMax));
	}
	// ===========================================================================

	public static void testRandom(int w, int h) {
		//--------------------------------------------------------------------
		wMax = w / 2;
		hMax = h / 2;
		//--------------------------------------------------------------------
		ro = 180;// (int) BBKSwtMath.random(50, 200);
		ra = (int) BBKSwtMath.random(50, 200);
		rb = (int) BBKSwtMath.random(50, 200);
		//--------------------------------------------------------------------
		po = new Point(0, 0);
		pa = new Point(292, -69);
		pb = new Point(-497, 160);
		pc = new Point(-422.0, -268.0);
		pd = new Point(0, 0);
		//--------------------------------------------------------------------
		pa = RandomPoint();
		pb = RandomPoint();
		//--------------------------------------------------------------------
	}

	public static int MouseR = 15;

	public static void testMove(double x, double y) {
		//d.s("testMove=" + x + "," + y);
		if (Math.abs(pa.x - x) < MouseR && Math.abs(pa.y - y) < MouseR) {
			pa.x = x;
			pa.y = y;
		}
		if (Math.abs(pb.x - x) < MouseR && Math.abs(pb.y - y) < MouseR) {
			pb.x = x;
			pb.y = y;
		}
		if (Math.abs(pc.x - x) < MouseR && Math.abs(pc.y - y) < MouseR) {
			pc.x = x;
			pc.y = y;
		}
	}

	@SuppressWarnings("unused")
	public static void testDraw() {
		//d.s("testDraw=" + pb.x + "," + pb.y);
		if (true) {
			//--------------------------------------------------------------------
			mainG.draw_point(po, MouseR, 1, mainG.cr, "po");
			mainG.draw_point(pa, MouseR, 1, mainG.cr, "pa");
			mainG.draw_point(pb, MouseR, 1, mainG.cr, "pb");
			mainG.draw_point(pc, MouseR, 1, mainG.cr, "pc");
			mainG.draw_point(pd, MouseR, 1, mainG.cr, "pd");
			//--------------------------------------------------------------------
			pc = BBKSwtMath.point_rotate_deg(po, pb, 10);//逆时针旋转后的位置		
			mainG.draw_line(pb, pc, 2, mainG.cr);
			//--------------------------------------------------------------------		
			int k = BBKSwtMath.point_LeftOfLine(pa, pb, pc);//pc在AB左侧
			double a = BBKSwtMath.point_Angle_Deg(po, pa, pb);//a->b顺时针角度
			//--------------------------------------------------------------------
			mainG.draw_cercle(po, 2, ro, 1, mainG.cm, "");
			pd = BBKSwtMath.Cercle_QD(po, ro, pa, true);
			mainG.draw_line(pa, pd, 2, mainG.cr);
			//--------------------------------------------------------------------
			mainG.draw_point(pc, MouseR, 2, mainG.cr, "pc");
			mainG.draw_point(pd, MouseR, 2, mainG.cr, "pd");
			//--------------------------------------------------------------------		
			mainG.draw_line(pa, po, 2, mainG.cr);
			mainG.draw_line(pb, po, 2, mainG.cr);
			mainG.draw_line(pc, po, 2, mainG.cr);
			//--------------------------------------------------------------------
			main.txtShow("k(c/ab)=" + k + "\t v(a-o-b)=" + (int) a);
			//--------------------------------------------------------------------
		}
		if (false) {
			//--------------------------------------------------------------------
			mainG.draw_cercle(pa, 2, ra, 1, mainG.cm, "a");
			mainG.draw_cercle(pb, 2, rb, 1, mainG.cm, "b");
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

}
