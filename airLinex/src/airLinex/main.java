package airLinex;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class main extends ApplicationWindow {

	public main() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		ViewLoad(container);
		return container;
	}

	private void createActions() {
		// Create the actions
	}

	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	public static void main(String args[]) {
		try {
			main window = new main();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Application");
	}

	@Override
	protected Point getInitialSize() {
		return new Point(winw, winh);
	}

	// ===========================================================================
	// ===========================================================================
	private int screenW, screenH, winw, winh;
	private static Text text;
	private DateTime dateTime;
	private Label lblNewLabel;
	private Button btnA, btnB, btnC;
	private ProgressBar progressBar;
	private static Canvas canvas;

	public final static Display display = Display.getDefault();
	//	public static Image refreshImage = new Image(display, "E:\\1.jpg");
	//	public static Image nextIamge = new Image(display, "E:\\2.jpg");
	private void ViewLoad(Composite container) {
		//--------------------------------------------------------------------
		Rectangle area = Display.getDefault().getClientArea();
		screenW = area.width;
		screenH = area.height;
		winw = (int) (screenW * 0.7f);
		winh = (int) (screenH * 0.7f);
		//--------------------------------------------------------------------
		mainGDraw.data_init(winw, winh);
		//--------------------------------------------------------------------
		DateTimeView(container);
		ButtonView(container);
		LabelView(container);
		CanvasView(container);
		//--------------------------------------------------------------------
	}

	// ===========================================================================
	private void DateTimeView(Composite container) {
		//--------------------------------------------------------------------
		dateTime = new DateTime(container, SWT.BORDER);
		dateTime.setBounds(10, 10, 140, 24);
		//--------------------------------------------------------------------
	}

	private void ButtonView(Composite container) {
		//--------------------------------------------------------------------
		btnA = new Button(container, SWT.NONE);
		btnA.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mainGDraw.data_init(winw, winh);
				canvas.redraw();
			}
		});
		btnA.setBounds(160, 7, 80, 27);
		btnA.setText("Random");
		//--------------------------------------------------------------------
		btnB = new Button(container, SWT.NONE);
		btnB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mainGDraw.data_calculate();
				canvas.redraw();
			}
		});
		btnB.setBounds(250, 7, 80, 27);
		btnB.setText("Calculate()");
		//--------------------------------------------------------------------
		btnC = new Button(container, SWT.NONE);
		btnC.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				canvas.setData(null);
				canvas.redraw();
			}
		});
		btnC.setBounds(340, 7, 80, 27);
		btnC.setText("C");
		//--------------------------------------------------------------------
	}

	private void LabelView(Composite container) {
		//--------------------------------------------------------------------
		lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(434, 10, 414, 24);
		lblNewLabel.setText("ABCDEFDGAGD");
		//--------------------------------------------------------------------
		text = new Text(container, SWT.BORDER);
		text.setBounds(10, 37, 410, 23);
		text.setText("ABCDEFDGAGD^");
		//--------------------------------------------------------------------
		progressBar = new ProgressBar(container, SWT.NONE);
		progressBar.setBounds(434, 37, 398, 23);
		progressBar.setMaximum(200);
		//--------------------------------------------------------------------
	}

	public static void txtShow(String s) {
		text.setText(s);
	}

	//--------------------------------------------------------------------
	private void CanvasView(Composite container) {
		//--------------------------------------------------------------------
		canvas = new Canvas(container, SWT.NONE);
		canvas.setBounds(10, 66, winw - 20, winh - 40);
		canvas.redraw();
		//--------------------------------------------------------------------
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				mainGDraw.data_draw(event.gc, event.width, event.height);
			}
		});
		//--------------------------------------------------------------------
		canvas.addListener(SWT.MouseDown, mainGDraw.listener);
		canvas.addListener(SWT.MouseUp, mainGDraw.listener);
		canvas.addListener(SWT.MouseMove, mainGDraw.listener);
		//--------------------------------------------------------------------
	}
	// ===========================================================================

	public static void newDraw() {
		canvas.redraw();
	}
	// ===========================================================================
	// ===========================================================================
	// ===========================================================================

}
