package airLinex;

import testBox.BBKWorkDraw;
import testBox.Test_LineSeg;
import testBox.Test_MathGeo;

public class mainTest {

	//	private static int winw, winh;
	private static int workType = 2;

	public static void init(int w, int h) {
		//d.s("init()");
		//		winw = w;
		//		winh = h;
		//--------------------------------------------------------------------
		switch (workType) {
		case 0:
			Test_MathGeo.testRandom(w, h);
			break;
		case 1:
			Test_LineSeg.testInit();
			break;
		case 2:
			BBKWorkDraw.testInit();
			break;
		}
		//--------------------------------------------------------------------
	}

	public static void mouse(double x, double y) {
		//d.s(x + "," + y);
		//--------------------------------------------------------------------
		switch (workType) {
		case 0:
			Test_MathGeo.testMove(x, y);
			break;
		case 1:
			Test_LineSeg.testMove(x, y);
			break;
		case 2:
			BBKWorkDraw.testMove(x, y);
			break;
		}
		//--------------------------------------------------------------------
	}

	public static void calculate() {
		//d.s("calculate()");
		//--------------------------------------------------------------------
		switch (workType) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			BBKWorkDraw.calculate();			
			break;
		}
		//--------------------------------------------------------------------
	}

	public static void draw() {
		//--------------------------------------------------------------------
		switch (workType) {
		case 0:
			Test_MathGeo.testDraw();
			break;
		case 1:
			Test_LineSeg.testDraw();
			break;
		case 2:
			BBKWorkDraw.testDraw();
			break;
		}
		//--------------------------------------------------------------------
	}

}
