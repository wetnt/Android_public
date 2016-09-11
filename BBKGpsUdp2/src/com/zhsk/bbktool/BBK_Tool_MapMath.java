package com.zhsk.bbktool;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.graphics.Matrix;
import android.graphics.Point;

public class BBK_Tool_MapMath {

	public static float getDistanceOfTwoPoints(Point p1, Point p2) {// 计算两个点之间的距离
		return (float) (Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)));
	}

	public static float getDistanceOfTwoPoints(float x1, float y1, float x2, float y2) {// 计算两个点之间的距离
		return (float) (Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
	}

	public static boolean isOnPic(float x, float y, float w, float h, Matrix m) {// 判断点是不是在位图上
		float[] bp = new float[8];
		bp[0] = 0;
		bp[1] = 0;
		bp[2] = 0;
		bp[3] = h;
		bp[4] = w;
		bp[5] = h;
		bp[6] = w;
		bp[7] = 0;
		m.mapPoints(bp);

		float A, B, C;
		for (int i = 0; i < 4; i++) {
			A = -(bp[(3 + 2 * i) % 8] - bp[1 + 2 * i]);
			B = bp[2 * (i + 1) % 8] - bp[2 * i];
			C = -(A * bp[2 * i] + B * bp[1 + 2 * i]);
			if (A * x + B * y + C > 0)
				return false;
		}
		return true;
	}

	public static boolean isOnPic(float x, float y, float xx, float yy, float w, float h) {// 判断点是不是在位图上
		float[] bp = new float[8];

		bp[0] = xx;
		bp[1] = yy;
		bp[2] = xx;
		bp[3] = yy + h;
		bp[4] = xx + w;
		bp[5] = yy + h;
		bp[6] = xx + w;
		bp[7] = yy;

		float A, B, C;
		for (int i = 0; i < 4; i++) {
			A = -(bp[(3 + 2 * i) % 8] - bp[1 + 2 * i]);
			B = bp[2 * (i + 1) % 8] - bp[2 * i];
			C = -(A * bp[2 * i] + B * bp[1 + 2 * i]);
			if (A * x + B * y + C > 0)
				return false;
		}
		return true;
	}

	public static float computeDegree(Point p1, Point p2) {// 计算两点与垂直方向夹角
		float tran_x = p1.x - p2.x;
		float tran_y = p1.y - p2.y;
		float degree = 0.0f;
		float angle = (float) (Math.asin(tran_x / Math.sqrt(tran_x * tran_x + tran_y * tran_y)) * 180 / Math.PI);
		if (!Float.isNaN(angle)) {
			if (tran_x >= 0 && tran_y <= 0) {// 第一象限
				degree = angle;
			} else if (tran_x <= 0 && tran_y <= 0) {// 第二象限
				degree = angle;
			} else if (tran_x <= 0 && tran_y >= 0) {// 第三象限
				degree = -180 - angle;
			} else if (tran_x >= 0 && tran_y >= 0) {// 第四象限
				degree = 180 - angle;
			}
		}
		return degree;
	}

	public static int getMaxValue(Integer... array) {// 获取变长参数最大的值
		List<Integer> list = Arrays.asList(array);
		Collections.sort(list);
		return list.get(list.size() - 1);
	}

	public static int getMinValue(Integer... array) {// 获取变长参数最的值
		List<Integer> list = Arrays.asList(array);
		Collections.sort(list);
		return list.get(0);
	}

	public static Float getMaxValue(Float... array) {// 获取变长参数最大的值
		List<Float> list = Arrays.asList(array);
		Collections.sort(list);
		return list.get(list.size() - 1);
	}

	public static Float getMinValue(Float... array) {// 获取变长参数最的值
		List<Float> list = Arrays.asList(array);
		Collections.sort(list);
		return list.get(0);
	}
}
