package testBox;

import org.eclipse.swt.graphics.Rectangle;

import com.boboking.swt.BBKSwtMath;
import com.boboking.swt.BBKSwtMath.AirLineDrawPoint;
import com.boboking.swt.BBKSwtMath.Point;
import com.zhsk.bbktool.d;

import airLinex.main;
import airLinex.mainG;

public class Test_LineSeg {

	private static Point p[] = new Point[4];
	private static int ra = 140;
	private static int rb = 260;

	private static Point pat, paf, pbt, pbf;

	public static void testInit() {
		p[0] = new Point(-621.0, 191.0);
		p[1] = new Point(-289.0, -30.0);
		p[2] = new Point(157.0, 24.0);
		p[3] = new Point(597.0, -148.0);
	}

	public static void testMove(double x, double y) {
		pDragIds = getDragId((int) x, (int) y);
		if (pDragIds >= 0 && pDragIds < p.length) {
			p[pDragIds].x = x;
			p[pDragIds].y = y;
		}
	}

	// ===========================================================================
	// ===========================================================================
	private final static int pDrawR = 15;
	private static int pDragIds = -999;

	private static int getDragId(int x, int y) {
		Rectangle r = new Rectangle(x - pDrawR, y - pDrawR, x + pDrawR, y + pDrawR);
		for (int i = 0; i < p.length; i++) {
			if (Math.abs(p[i].x - x) < pDrawR && Math.abs(p[i].y - y) < pDrawR) {

				d.s("pDragKey = " + i + "\t new_airLine_Point(" + p[i].x + "," + p[i].y + "); \t==" + r.toString());

				return i;
			}
		}
		return -999;
	}

	@SuppressWarnings("unused")
	public static void testDraw() {
		int w = 3;
		String s;
		//--------------------------------------------------------------------
		for (int i = 0; i < p.length; i++) {
			mainG.draw_point(p[i], pDrawR, 1, mainG.cm, i + "");
		}
		//--------------------------------------------------------------------
		double d = BBKSwtMath.point_dist(p[1], p[2]);
		int lfrt1 = BBKSwtMath.point_LeftOfLine(p[0], p[2], p[1]);//1left;-1right;0on;
		int lfrt2 = BBKSwtMath.point_LeftOfLine(p[1], p[3], p[2]);
		int turn1 = -1 * lfrt1;
		int turn2 = -1 * lfrt2;
		main.txtShow("lfrt1=" + lfrt1);
		//--------------------------------------------------------------------
		paf = BBKSwtMath.Cercle_QD(p[1], ra, p[0], lfrt1 == -1);
		AirLineDrawPoint px = BBKSwtMath.CercleTwo_QieXian(p[1], ra, p[2], rb);
		//--------------------------------------------------------------------
		if (lfrt1 == lfrt2) {
			pat = turn1 == -1 ? px.awl : px.awr;
			pbf = turn2 == -1 ? px.bwl : px.bwr;
		} else {
			pat = turn1 == -1 ? px.anl : px.anr;
			pbf = turn2 == -1 ? px.bnl : px.bnr;
		}
		if (d < ra + rb) {
			turn2 = turn1;
			pat = turn1 == -1 ? px.awl : px.awr;
			pbf = turn2 == 1 ? px.bwr : px.bwl;
		}
		//--------------------------------------------------------------------
		s = (lfrt1 == 1 ? "左侧" : "右侧") + "," + (turn1 == 1 ? "逆时针" : "顺时针");
		mainG.draw_cercle(p[1], ra, 5, 1, mainG.cm, "a= " + s);
		mainG.draw_cercle(p[1], ra, pDrawR, 1, mainG.cm, "");
		s = (lfrt2 == 1 ? "左侧" : "右侧") + "," + (turn2 == 1 ? "逆时针" : "顺时针");
		mainG.draw_cercle(p[2], rb, 5, 1, mainG.cm, "b= " + s);
		mainG.draw_cercle(p[2], rb, pDrawR, 1, mainG.cm, "");
		pbt = BBKSwtMath.Cercle_QD(p[2], rb, p[3], turn2 == -1);
		//--------------------------------------------------------------------
		mainG.draw_line(p[0], p[1], 1, mainG.cm);
		mainG.draw_line(p[1], p[2], 1, mainG.cm);
		mainG.draw_line(p[2], p[3], 1, mainG.cm);
		//--------------------------------------------------------------------
		mainG.draw_line(p[0], paf, w, mainG.cr);
		mainG.draw_line(pat, pbf, w, mainG.cr);
		mainG.draw_line(pbt, p[3], w, mainG.cr);
		//--------------------------------------------------------------------
		double afd = BBKSwtMath.point_Angle_Deg(p[1], new Point(p[1].x, p[1].y + ra), paf); //afd = BBKSwtMath.fomartDegrees(afd);
		double atd = BBKSwtMath.point_Angle_Deg(p[1], new Point(p[1].x, p[1].y + ra), pat); //atd = BBKSwtMath.fomartDegrees(atd);
		mainG.draw_arc(p[1], ra, w, afd, atd, turn1, mainG.cr);
		double bfd = BBKSwtMath.point_Angle_Deg(p[2], new Point(p[2].x, p[2].y + rb), pbf); //bfd = BBKSwtMath.fomartDegrees(bfd);
		double btd = BBKSwtMath.point_Angle_Deg(p[2], new Point(p[2].x, p[2].y + rb), pbt); //btd = BBKSwtMath.fomartDegrees(btd);
		mainG.draw_arc(p[2], rb, w, bfd, btd, turn2, mainG.cr);
		//--------------------------------------------------------------------
		if (false) {
			mainG.draw_point(paf, 5, 1, mainG.cm, "a1=" + (int) afd);
			mainG.draw_point(pat, 5, 1, mainG.cm, "a2=" + (int) atd);
			mainG.draw_point(pbf, 5, 1, mainG.cm, "b1=" + (int) bfd);
			mainG.draw_point(pbt, 5, 1, mainG.cm, "b2=" + (int) btd);
		}
		//--------------------------------------------------------------------
	}

}
