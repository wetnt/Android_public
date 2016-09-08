package com.boboking.swt;

public class BBKSwtMath {

	//	public static Point Cercle_QD(Point ptCenter, double dbRadious, Point ptOutside) {
	//		Point cp = Cercle_QieDian(ptCenter, dbRadious, ptOutside);
	//		return cp.toPoint();
	//	}

	//http://www.xuebuyuan.com/2179282.html
	public static Point Cercle_QD(Point ptCenter, double dbRadious, Point ptOutside, boolean isRight) {
		Point E = new Point(0, 0);
		Point F = new Point(0, 0);
		Point G = new Point(0, 0);
		Point H = new Point(0, 0);

		double r = dbRadious;
		//1. 坐标平移到圆心ptCenter处,求园外点的新坐标E
		E.x = ptOutside.x - ptCenter.x;
		E.y = ptOutside.y - ptCenter.y; //平移变换到E

		//2. 求园与OE的交点坐标F, 相当于E的缩放变换
		double t = r / Math.sqrt(E.x * E.x + E.y * E.y); //得到缩放比例
		F.x = E.x * t;
		F.y = E.y * t; //缩放变换到F

		//3. 将E旋转变换角度a到切点G，其中cos(a)=r/OF=t, 所以a=arccos(t);
		double a = isRight ? Math.acos(t) : -Math.acos(t); //得到旋转角度
		G.x = F.x * Math.cos(a) - F.y * Math.sin(a);
		G.y = F.x * Math.sin(a) + F.y * Math.cos(a); //旋转变换到G

		//4. 将G平移到原来的坐标下得到新坐标H
		H.x = G.x + ptCenter.x;
		H.y = G.y + ptCenter.y; //平移变换到H

		//5. 返回H
		return new Point(H.x, H.y);
		//6. 实际应用过程中，只要一个中间变量E,其他F,G,H可以不用。
	}

	private static double point_Angle_Pi(Point o, Point s, Point e) { //矢量os在矢量oe的顺时针方向角度
		double cosfi, fi, norm;
		double dsx = s.x - o.x;
		double dsy = s.y - o.y;
		double dex = e.x - o.x;
		double dey = e.y - o.y;
		cosfi = dsx * dex + dsy * dey;
		norm = (dsx * dsx + dsy * dsy) * (dex * dex + dey * dey);
		cosfi /= Math.sqrt(norm);
		if (cosfi >= 1.0)
			return 0;
		if (cosfi <= -1.0)
			return Math.PI;
		fi = Math.acos(cosfi);
		if (dsx * dey - dsy * dex > 0)
			return -fi; // 说明矢量os 在矢量 oe的顺时针方向 
		return fi;
	}

	public static double point_Angle_Deg(Point o, Point s, Point e) {//os顺时针到oe转过的角度
		return toDegrees(point_Angle_Pi(o, s, e));
	}

	// 返回点p以点o为圆心逆时针旋转alpha(单位：弧度)后所在的位置 
	private static Point point_rotate_rad(Point o, Point p, double radians) {
		Point tp = new Point(0, 0);
		Point op = new Point(p.x, p.y);
		op.x -= o.x;
		op.y -= o.y;
		tp.x = op.x * Math.cos(radians) - op.y * Math.sin(radians) + o.x;
		tp.y = op.y * Math.cos(radians) + op.x * Math.sin(radians) + o.y;
		return tp;
	}

	public static Point point_rotate_deg(Point o, Point p, double degrees) {//p以o为圆心逆时针旋转degrees后的位置 
		return point_rotate_rad(o, p, toRadians(degrees));
	}

	public static int point_LeftOfLine(Point poiA, Point poiB, Point poiM) {//1left;-1right;0on;
		double ax = poiB.x - poiA.x;
		double ay = poiB.y - poiA.y;
		double bx = poiM.x - poiA.x;
		double by = poiM.y - poiA.y;
		double judge = ax * by - ay * bx;
		if (judge > 0) {
			return 1;
		} else if (judge < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	public static double point_dist(Point p1, Point p2) { // 返回两点之间欧氏距离 
		return point_dist(p1.x, p1.y, p2.x, p2.y);
	}

	public static double point_dist(double x1, double y1, double x2, double y2) { // 返回两点之间欧氏距离 
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	// ===========================================================================
	public static class AirLineDrawPoint {
		public Point da = new Point(0, 0), db = new Point(0, 0);
		public Point dw = new Point(0, 0), dn = new Point(0, 0);
		public Point awl = new Point(0, 0), awr = new Point(0, 0), anl = new Point(0, 0), anr = new Point(0, 0);
		public Point bwl = new Point(0, 0), bwr = new Point(0, 0), bnl = new Point(0, 0), bnr = new Point(0, 0);

		public AirLineDrawPoint() {
		}
	}

	public static AirLineDrawPoint CercleTwo_QieXian(Point oa, double ra, Point ob, double rb) {
		//--------------------------------------------------------------------
		AirLineDrawPoint p = new AirLineDrawPoint();
		//--------------------------------------------------------------------
		double cx, cy;
		double ab = BBKSwtMath.point_dist(oa, ob);
		double ac = ra * ab / (ra + rb);
		double bc = ab - ac;
		//--------------------------------------------------------------------
		if (ra == rb) {
			cx = oa.x + (ob.x - oa.x) * ra / ab;
			cy = oa.y + (ob.y - oa.y) * ra / ab;
			p.da = new Point(cx, cy);
			cx = oa.x + (ob.x - oa.x) * (ab - rb) / ab;
			cy = oa.y + (ob.y - oa.y) * (ab - rb) / ab;
			p.db = new Point(cx, cy);
			cx = (oa.x * bc + ob.x * ac) / ab;
			cy = (oa.y * bc + ob.y * ac) / ab;
			p.dn = new Point(cx, cy);
		} else {
			cx = (ra * ob.x - rb * oa.x) / (ra - rb);
			cy = (ra * ob.y - rb * oa.y) / (ra - rb);
			p.dw = new Point(cx, cy);
			cx = (oa.x * bc + ob.x * ac) / ab;
			cy = (oa.y * bc + ob.y * ac) / ab;
			p.dn = new Point(cx, cy);
		}
		//--------------------------------------------------------------------
		if (ra == rb) {
			p.awr = BBKSwtMath.point_rotate_deg(oa, p.da, 90);
			p.awl = BBKSwtMath.point_rotate_deg(oa, p.da, -90);
			p.bwr = BBKSwtMath.point_rotate_deg(ob, p.db, -90);
			p.bwl = BBKSwtMath.point_rotate_deg(ob, p.db, 90);
		} else {
			p.awl = BBKSwtMath.Cercle_QD(oa, ra, p.dw, ra > rb);
			p.awr = BBKSwtMath.Cercle_QD(oa, ra, p.dw, ra < rb);
			p.bwl = BBKSwtMath.Cercle_QD(ob, rb, p.dw, ra > rb);
			p.bwr = BBKSwtMath.Cercle_QD(ob, rb, p.dw, ra < rb);
		}
		//--------------------------------------------------------------------
		p.anl = BBKSwtMath.Cercle_QD(oa, ra, p.dn, true);
		p.anr = BBKSwtMath.Cercle_QD(oa, ra, p.dn, false);
		p.bnr = BBKSwtMath.Cercle_QD(ob, rb, p.dn, true);
		p.bnl = BBKSwtMath.Cercle_QD(ob, rb, p.dn, false);
		//--------------------------------------------------------------------
		return p;
		//--------------------------------------------------------------------
	}

	// ===========================================================================
	// ===========================================================================
	// ===========================================================================
	private static final double PiOver180 = Math.PI / 180.0;

	public static double toRadians(double degrees) {
		return degrees * PiOver180;
	}

	public static double toDegrees(double radians) {
		return radians / PiOver180;
	}

	public static double fomartDegrees(double degrees) {
		if (degrees < 0)
			degrees += 360;
		if (degrees > 360)
			degrees -= 360;
		return degrees;
	}

	public static double random(double min, double max) {
		return (min + Math.random() * (max - min + 1));
	}

	// ===========================================================================
	// ===========================================================================
	public static class LINESEG {
		Point s;
		Point e;

		LINESEG(Point a, Point b) {
			s = a;
			e = b;
		}

		LINESEG() {
		}
	};

	public static class LINE { // 直线的解析方程 a*x+b*y+c=0  为统一表示，约定 a >= 0 
		double a;
		double b;
		double c;

		LINE(double d1, double d2, double d3) {
			a = d1;
			b = d2;
			c = d3;
		}
	};

	// 根据已知两点坐标，求过这两点的直线解析方程： a*x+b*y+c = 0  (a >= 0)  
	public static LINE makeline(Point p1, Point p2) {
		LINE tl = new LINE(1, 1, 0);
		int sign = 1;
		tl.a = p2.y - p1.y;
		if (tl.a < 0) {
			sign = -1;
			tl.a = sign * tl.a;
		}
		tl.b = sign * (p1.x - p2.x);
		tl.c = sign * (p1.y * p2.x - p1.x * p2.y);
		return tl;
	}

	// 如果两条直线 l1(a1*x+b1*y+c1 = 0), l2(a2*x+b2*y+c2 = 0)相交，返回true，且返回交点p  
	boolean lineintersect(LINE l1, LINE l2, Point p) { // 是 L1，L2 
		double d = l1.a * l2.b - l2.a * l1.b;
		if (Math.abs(d) < Math.E) // 不相交 
			return false;
		p.x = (l2.c * l1.b - l1.c * l2.b) / d;
		p.y = (l2.a * l1.c - l1.a * l2.c) / d;
		return true;
	}

	// ===========================================================================
	// ===========================================================================
	// ===========================================================================
	public static class Point {
		public double x, y;

		public Point() {
			this.x = 0;
			this.y = 0;
		}

		public Point(double _x, double _y) {
			this.x = _x;
			this.y = _y;
		}
	}

	public static boolean PointIsOK(Point p) {
		return (p != null && p.x != 0 && p.y != 0);
	}

	public static boolean PointIsEmpty(Point p) {
		return (p.x == 0 && p.y == 0);
	}

	// ===========================================================================
	// ===========================================================================
	// ===========================================================================
	//
	//	public static double[] getPointBySq(double startX, double startY, double startW, double endX, double endY, double endW) {
	//
	//		double ED = sqr(distance(startX, startY, endX, endY)) - sqr(endW - startW);
	//		double a = 4 * (sqr(startY - endY) + sqr(startX - endX));
	//		double b = 4 * ((sqr(endX - startX) + sqr(startW) - (sqr(endW) + ED) - sqr(endY) + sqr(startY)) * (endY - startY) - 2 * sqr(endX - startX) * startY);
	//		double c = sqr(sqr(endX - startX) + sqr(startW) - sqr(endW) - ED - sqr(endY) + sqr(startY)) - 4 * sqr(endX - startX) * (sqr(startW) - sqr(startY));
	//		double y1 = (-b - Math.sqrt(sqr(b) - 4 * a * c)) / (2 * a);
	//		double y2 = (-b + Math.sqrt(sqr(b) - 4 * a * c)) / (2 * a);
	//		double x1 = Math.sqrt(sqr(startW) - sqr(y1 - startY)) + startX;
	//		double x2 = Math.sqrt(sqr(startW) - sqr(y2 - startY)) + startX;
	//
	//		double[] d = { x1, y1, x2, y2 };
	//		return d;
	//	}
	//
	//	public static double sqr(double x) {
	//		return x * x;
	//	}
	//
	//	public static double distance(double ax, double ay, double bx, double by) {
	//		return Math.sqrt(sqr(ax - bx) + sqr(ay - by));
	//	}
	//
	// ===========================================================================
	// ===========================================================================
	// ===========================================================================

}
