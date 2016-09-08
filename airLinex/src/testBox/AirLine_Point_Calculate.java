package testBox;

import com.boboking.swt.BBKSwtMath;
import com.boboking.swt.BBKSwtMath.AirLineDrawPoint;
import com.boboking.swt.BBKSwtMath.Point;

import testBox.AirLine_Point.TURN_TYPE;
import testBox.AirLine_Point.airLine_Point;

public class AirLine_Point_Calculate {

	//航线计算步骤：
	//一、首末点赋值处理
	//二、计算转弯圆心(绕点、压点、向点、切点、直飞)
	//三、计算相邻点的转弯出入点//切圆计算
	public static void AirLine_Point_Calculate_set(airLine_Point pl, airLine_Point po, airLine_Point pn, int n) {
		AirLine_Point_Calculate_firstPointAndLasterPoint(pl, po, pn);//首末点赋值处理
		AirLine_Point_Calculate_CircleCenter(pl, po, pn);//计算转弯圆心(绕点、压点、向点、切点、直飞)
		AirLine_Point_Calculate_CircleTangent(pl, po, pn);//切圆计算
	}

	public static void AirLine_Point_Calculate_firstPointAndLasterPoint(airLine_Point pl, airLine_Point po, airLine_Point pn) {//首末点赋值处理//首末点确定圆心
		//当前配置为首末点直连，未来要考虑
		//--------------------------------------------------------------------
		//设定上下相邻航点的关联关系
		if (pl == null) {
			po.isFirst = true;
			po.f = po.p;//入弯点，切点入
			po.t = po.p;//出弯点，切点出
			po.o = po.p;//转弯圆心
			po.r = 1;
		} else if (pn == null) {
			po.isLastp = true;
			po.f = po.p;//入弯点，切点入
			po.t = po.p;//出弯点，切点出
			po.o = po.p;//转弯圆心
			po.r = 1;
		}
		//--------------------------------------------------------------------
	}

	public static void AirLine_Point_Calculate_CircleCenter(airLine_Point pl, airLine_Point po, airLine_Point pn) {//计算转弯圆心(绕点、压点、向点、切点、直飞)
		//--------------------------------------------------------------------
		if (po.type == TURN_TYPE.REEL_AUTO || po.type == TURN_TYPE.PRESS_LEFT || po.type == TURN_TYPE.PRESS_RIGHT) {//
			po.o = po.p;//转弯圆心
		}
		//--------------------------------------------------------------------
	}

	public static void AirLine_Point_Calculate_CircleTangent(airLine_Point pl, airLine_Point po, airLine_Point pn) {//切圆计算
		//--------------------------------------------------------------------
		if (pl == null || po == null || pn == null)
			return;
		if (BBKSwtMath.PointIsOK(pl.o) && BBKSwtMath.PointIsOK(po.o) && BBKSwtMath.PointIsOK(pn.o))
			return;
		//--------------------------------------------------------------------
		double d = BBKSwtMath.point_dist(pl.o, po.o);
		po.lfrt = BBKSwtMath.point_LeftOfLine(pl.o, pn.o, po.o);//(pl.pt, po.po, pn.pf)//1left;-1right;0on;
//		po.lfrt = BBKSwtMath.point_LeftOfLine(pl.t, po.o, pn.f);//1left;-1right;0on;
		po.turn = -po.lfrt;
		AirLineDrawPoint p = BBKSwtMath.CercleTwo_QieXian(pl.o, pl.r, po.o, po.r);
		if (pl.lfrt == po.lfrt) {
			pl.t = pl.lfrt == 1 ? p.awl : p.awr;
			po.f = po.lfrt == 1 ? p.bwl : p.bwr;
		} else {
			if (d < pl.r + po.r) {
				po.lfrt = -1 * po.lfrt;
				po.turn = po.lfrt;
				pl.t = pl.lfrt == 1 ? p.awl : p.awr;
				po.f = po.lfrt == 1 ? p.bwl : p.bwr;
			} else {
				pl.t = pl.lfrt == 1 ? p.anl : p.anr;
				po.f = po.lfrt == 1 ? p.bnl : p.bnr;
			}
		}
		//--------------------------------------------------------------------
		p = BBKSwtMath.CercleTwo_QieXian(po.o, po.r, pn.o, pn.r);
		po.t = po.lfrt == -1 ? p.anl : p.anr;
		pn.f = po.lfrt == -1 ? p.bwl : p.bwr;
		//--------------------------------------------------------------------	
		airLine_Point_set_ftd(pl);
		airLine_Point_set_ftd(po);
		airLine_Point_set_ftd(pn);
		//--------------------------------------------------------------------		
	}

	public static void airLine_Point_set_ftd(airLine_Point p) {//设置相邻点的出入弧点和弧段
		if (!BBKSwtMath.PointIsOK(p.o))
			return;
		if (BBKSwtMath.PointIsOK(p.f))
			p.fd = BBKSwtMath.point_Angle_Deg(p.o, new Point(p.o.x, p.o.y + p.r), p.f);
		if (BBKSwtMath.PointIsOK(p.t))
			p.td = BBKSwtMath.point_Angle_Deg(p.o, new Point(p.o.x, p.o.y + p.r), p.t);
	}

}
