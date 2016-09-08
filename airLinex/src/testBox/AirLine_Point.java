package testBox;

import com.boboking.swt.BBKSwtMath.Point;

public class AirLine_Point {

	public static class airLine_Point {
		//------------------------------------------------
		public TURN_TYPE type = TURN_TYPE.STRAIGHT;
		//------------------------------------------------
		public double r = 0;
		public Point p = new Point(0, 0);//航点
		//------------------------------------------------		
		public Point f = new Point(0, 0);//入弯点，切点入
		public Point o = new Point(0, 0);//转弯圆心
		public Point t = new Point(0, 0);//出弯点，切点出
		//------------------------------------------------	
		public double fd = 0;//入弯点与正北向的夹角//x轴向
		public double td = 0;//出弯点与正北向的夹角//x轴向
		//public boolean turns = true;//绕点方向，true顺时针，false逆时针
		public int turn = 0;//1逆时针，-1顺时针，0未知；
		public int lfrt = 2;//0在航线上，1右侧，-1左侧，2未知
		//------------------------------------------------
		public boolean isFirst = false;
		public boolean isLastp = false;
		//------------------------------------------------

		public airLine_Point(double x, double y, double _r, TURN_TYPE _t) {
			//------------------------------------------------
			p = new Point(x, y);//航点
			//------------------------------------------------
			f = new Point(0, 0);//入弯点，切点入
			o = new Point(0, 0);//转弯圆心
			t = new Point(0, 0);//出弯点，切点出
			r = _r;
			//------------------------------------------------
			type = _t;//TURN_TYPE.STRAIGHT;//转弯类型
			//------------------------------------------------
		}

		public void clear() {
			//------------------------------------------------		
			f = new Point(0, 0);//入弯点，切点入
			o = new Point(0, 0);//转弯圆心
			t = new Point(0, 0);//出弯点，切点出
			//------------------------------------------------	
			fd = 0;//入弯点与正北向的夹角//x轴向
			td = 0;//出弯点与正北向的夹角//x轴向
			turn = 0;//1逆时针，-1顺时针，0未知；
			lfrt = 2;//0在航线上，1右侧，-1左侧，2未知
			//------------------------------------------------
		}
	}

	// ===========================================================================
	// 定义了经过航点的转弯方式：1切线转弯、2压点向左、3压点向右、4压点自动、5绕点向左、6绕点向右、7绕点自动、8向点向左、9向点向右、10向点自动、11直飞
	public enum TURN_TYPE {
		TANGENT_LINE, // 1切线转弯
		PRESS_LEFT, // 2压点向左
		PRESS_RIGHT, // 3压点向右
		PRESS_AUTO, // 4压点自动
		REEL_LEFT, // 5绕点向左
		REEL_RIGHT, // 6绕点向右
		REEL_AUTO, // 7绕点自动
		TOWARDS_LEFT, // 8向点向左
		TOWARDS_RIGHT, // 9向点向右
		TOWARDS_AUTO, // 10向点自动
		STRAIGHT // 11直飞
	}

	//--------------------------------------------------------------------
	public TURN_TYPE getRandom_TurnType() {
		int t = (int) (1 + Math.random() * 10);
		if (t == 0)
			return TURN_TYPE.STRAIGHT;
		else if (t == 1)
			return TURN_TYPE.TANGENT_LINE;
		else if (t == 2)
			return TURN_TYPE.PRESS_LEFT;
		else if (t == 3)
			return TURN_TYPE.PRESS_RIGHT;
		else if (t == 4)
			return TURN_TYPE.PRESS_AUTO;
		else if (t == 5)
			return TURN_TYPE.REEL_LEFT;
		else if (t == 6)
			return TURN_TYPE.REEL_RIGHT;
		else if (t == 7)
			return TURN_TYPE.REEL_AUTO;
		else if (t == 8)
			return TURN_TYPE.TOWARDS_LEFT;
		else if (t == 9)
			return TURN_TYPE.TOWARDS_RIGHT;
		else if (t == 10)
			return TURN_TYPE.TOWARDS_AUTO;
		else
			return TURN_TYPE.STRAIGHT;
	}
	// ===========================================================================
	// ===========================================================================
	// ===========================================================================

}
