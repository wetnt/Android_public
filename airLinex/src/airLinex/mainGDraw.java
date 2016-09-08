package airLinex;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class mainGDraw {

	// ===========================================================================
	// ===========================================================================
	private static int winw, winh;
	// ===========================================================================
	private static boolean mouseDownKey = false;
	private static int lastx, lasty;
	// ===========================================================================
	public static Listener listener = new Listener() {
		public void handleEvent(Event e) {
			switch (e.type) {
			case SWT.MouseDown:
				mouseDownKey = true;
				break;
			case SWT.MouseMove:
				if (mouseDownKey) {
					mainTest.mouse(mainG.toMx(e.x), mainG.toMy(e.y));
					main.newDraw();
				}
				break;
			case SWT.MouseUp:
				if (mouseDownKey) {
					lastx = e.x;
					lasty = e.y;
					main.newDraw();
				}
				mouseDownKey = false;
				break;
			}
		}
	};

	public static void data_init(int w, int h) {
		winw = w;
		winh = h;
		mainTest.init(w, h);
	}

	public static void data_calculate() {
		mainTest.calculate();
	}

	public static void data_draw(GC gc, int w, int h) {
		//--------------------------------------------------------------------
		gc.setAdvanced(true);
		gc.drawRectangle(0, 0, w - 1, h - 1);
		gc.drawRoundRectangle(0, 0, w - 1, h - 1, 5, 5);
		//--------------------------------------------------------------------
		mainG.drawAxis(gc, w, h);
		mainTest.draw();
		//--------------------------------------------------------------------
	}
	// ===========================================================================
	// ===========================================================================
}
