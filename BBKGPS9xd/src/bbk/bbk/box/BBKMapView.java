package bbk.bbk.box;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import bbk.map.abc.BBKMap;
import bbk.map.abc.BBKMap.MapPoiXY;
import bbk.map.abc.BBKMapMath;

public class BBKMapView {

	// ------------------------------------------------------
	private final int NONE = 0;// ��ʼ״̬
	private final int DRAG = 1;// �϶�
	private final int ZOOM = 2;// ����
	private int mode = NONE;
	// ------------------------------------------------------
	private Matrix mapVwMatrix = new Matrix();
	private Matrix savedMatrix = new Matrix();
	private PointF mapVwStart = new PointF();
	private PointF mapVwMid = new PointF();
	// ------------------------------------------------------
	private float mapVwOldDist;
	private float newDist;
	private float mapscale;

	// ====================================================================================
	public void ImgViewTouchMainSet() {
		ImgViewTouchSet(BBKSoft.myBoxs.MapImg);
	}

	@SuppressLint("ClickableViewAccessibility")
	public void ImgViewTouchSet(ImageView myImageView) {
		myImageView.setOnTouchListener(new OnTouchListener() {
			// ====================================================================================
			public boolean onTouch(View v, MotionEvent event) {
				// ====================================================================================
				ImageView view = (ImageView) v;
				// ====================================================================================
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				// ��������ģʽ
				case MotionEvent.ACTION_DOWN:
					// ------------------------------------------------------
					mode = DRAG;
					mapscale = 1;
					MouseDOWN(view, event);
					break;
				// ------------------------------------------------------
				case MotionEvent.ACTION_UP:
					// ------------------------------------------------------
					pdx = (int) event.getX();
					pdy = (int) event.getY();
					MouseUP(view, event);
					break;
				// ------------------------------------------------------
				case MotionEvent.ACTION_POINTER_UP:
					// ------------------------------------------------------
					mode = NONE;
					break;
				// ���ö�㴥��ģʽ
				// ------------------------------------------------------
				case MotionEvent.ACTION_POINTER_DOWN:
					// ------------------------------------------------------
					mapVwOldDist = spacing(event);
					if (mapVwOldDist > 10f) {
						savedMatrix.set(mapVwMatrix);
						midPoint(mapVwMid, event);
						mode = ZOOM;
					}
					break;
				// ��ΪDRAGģʽ�������ƶ�ͼƬ
				// ------------------------------------------------------
				case MotionEvent.ACTION_MOVE:
					// ------------------------------------------------------
					// LabInf.setText("ACTION_MOVE");
					if (mode == DRAG) {
						mapVwMatrix.set(savedMatrix);
						// ����λ��
						mapVwMatrix.postTranslate(event.getX() - mapVwStart.x, event.getY() - mapVwStart.y);
					}
					// ��ΪZOOMģʽ�����㴥������
					else if (mode == ZOOM) {
						newDist = spacing(event);
						if (newDist > 10f) {
							mapVwMatrix.set(savedMatrix);
							mapscale = newDist / mapVwOldDist;
							// �������ű�����ͼƬ�е�λ��
							// ----------------------------------------------------
							if (BBKMap.mapzm == 3 && mapscale < 1) {
								mapscale = 1;
							}
							// ----------------------------------------------------
							mapVwMatrix.postScale(mapscale, mapscale, mapVwMid.x, mapVwMid.y);
							// ----------------------------------------------------
						}
					}
					break;
				// ------------------------------------------------------
				}
				// ------------------------------------------------------
				view.setImageMatrix(mapVwMatrix);
				// ------------------------------------------------------
				return true; // indicate event was handled
				// ------------------------------------------------------
			}

			// ====================================================================================
			private float spacing(MotionEvent event) {// �����ƶ�����
				// -----------------------------------------------------------
				float x = event.getX(0) - event.getX(1);
				float y = event.getY(0) - event.getY(1);
				// -----------------------------------------------------------
				return (float) Math.sqrt(x * x + y * y);
				// -----------------------------------------------------------
			}

			private void midPoint(PointF point, MotionEvent event) {// �����е�λ��
				float x = event.getX(0) + event.getX(1);
				float y = event.getY(0) + event.getY(1);
				point.set(x / 2, y / 2);
			}
			// ====================================================================================
		});
	}

	// ====================================================================================
	private void MouseDOWN(ImageView view, MotionEvent event) {
		// -----------------------------------------------------------
		mapVwMatrix.set(view.getImageMatrix());
		savedMatrix.set(mapVwMatrix);
		mapVwStart.set(event.getX(), event.getY());
		// -----------------------------------------------------------
	}

	// ====================================================================================
	private int pdx = 0, pdy = 0;

	private void MouseUP(ImageView view, MotionEvent event) {
		// ============================================================================
		if (DoubleTapCheck()) {// �ж�ִ���Ƿ�˫������
			DoubleTapWork();
			return;
		}
		// ============================================================================
		float mapangle = 0;// BBK.myMaps.MapAngle;
		// ----------------------------------------------------------------------------
		float[] mapMx9f = new float[9];
		view.getImageMatrix().getValues(mapMx9f);
		// ----------------------------------------------------------------------------
		int maprx = (int) (BBKMap.MapWx * mapscale);
		int mapry = (int) (BBKMap.MapHy * mapscale);
		MapPoiXY pc = new MapPoiXY(BBKMap.MapWx, BBKMap.MapHy);
		MapPoiXY pz = new MapPoiXY((int) mapMx9f[2], (int) mapMx9f[5]);
		MapPoiXY ph = new MapPoiXY(pz.x + maprx, pz.y + mapry);
		// -----------------------------------------------------------------------------
		MapPoiXY pm = BBKMapMath.PointRotate(pz, ph, mapangle);
		MapPoiXY px = BBKMapMath.PointRotate(pm, pc, -mapangle);
		// -----------------------------------------------------------------------------
		int rz = GetZoomScale(mapscale);
		double mx = (px.x - pm.x) / mapscale;
		double my = (px.y - pm.y) / mapscale;
		// ============================================================================
		MouseWork(mx, my, rz);
		// ============================================================================
	}

	// ====================================================================================
	private void MouseWork(double mx, double my, int rz) {
		// -----------------------------------------------------------------------------
		if (Math.abs(mx) < 6 && Math.abs(my) < 6) {// ��Ӳ����µ�
			BBKSoft.MapTouch(pdx, pdy);
		} else {// ��ͼ�϶�
			// ----------------------------------------------------------------------------
			int z = BBKMap.mapzm;
			double x = BBKMap.PixJXT + mx;
			double y = BBKMap.PixWYT + my;
			double w = BBKMapMath.GetLatByPixel(y, z);
			double j = BBKMapMath.GetLonByPixel(x, z);
			// ----------------------------------------------------------------------------
			mapVwMatrix.reset();
			BBKSoft.myMaps.MapSetWJZ(w, j, rz);
			BBKSoft.MapFlash(true);
			// ----------------------------------------------------------------------------
			BBKSoft.MapMove();
			// ----------------------------------------------------------------------------
		}
		// -----------------------------------------------------------------------------
	}

	// ====================================================================================
	private long firClick, secClick;
	private int doubleclicktimex = 400;

	private boolean DoubleTapCheck() {// ˫���¼�
		secClick = System.currentTimeMillis();
		if (secClick - firClick < doubleclicktimex) {
			return true;
		} else {
			firClick = System.currentTimeMillis();
			return false;
		}
	}

	// ====================================================================================

	private void DoubleTapWork() {// ˫���¼�
		// ----------------------------------------------------------------------------
		BBKSoft.myMaps.BBKViewCenterTemp(pdx, pdy);
		handlerTimer.post(RunnableTimer);
		// ----------------------------------------------------------------------------
	}

	private Handler handlerTimer = new Handler();
	private Runnable RunnableTimer = new Runnable() {
		public void run() {
			MapZoomNewRun();
		}
	};

	private void MapZoomNewRun() {// ��ͼ��ת����ˢ��
		// ----------------------------------------------------------------------------
		float angle = 0;// BBK.myMaps.MapAngle;// ��ͼ��ת�Ƕ�
		// ����ڵ�ͼ���ĵ������λ��XYֵ
		int pcx = pdx - BBKMap.MapWx;
		int pcy = pdy - BBKMap.MapHy;
		// ----------------------------------------------------------------------------
		MapPoiXY pc = new MapPoiXY(0, 0);// ����ԭ��=��ͼ����
		MapPoiXY pz = new MapPoiXY(pcx, pcy);// �Ե�ͼ����Ϊ����ԭ���˫��λ��
		// �Ե�ͼ����Ϊԭ�㣬˫��λ�ã�������ת��ͼ��ת�ǶȺ󣬻�õ�����ֵ
		MapPoiXY pm = BBKMapMath.PointRotate(pc, pz, -angle);
		// ----------------------------------------------------------------------------
		double px = BBKMap.PixJXT + pm.x;// ī����ͶӰ����ֵY
		double py = BBKMap.PixWYT + pm.y;// ī����ͶӰ����ֵX
		// ----------------------------------------------------------------------------
		int z = BBKMap.mapzm;
		double w = BBKMapMath.GetLatByPixel(py, z);// ˫��λ��γ��
		double j = BBKMapMath.GetLonByPixel(px, z);// ˫��λ�þ���
		BBKSoft.myMaps.MapSetWJZ(w, j, 1);// ��˫��λ��Ϊ��ͼ���ģ��Ŵ��ͼ1��
		// ----------------------------------------------------------------------------
		BBKSoft.MapFlash(true);
		// ----------------------------------------------------------------------------
	}
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	public int GetZoomScale(double scale) {
		// --------------------------------------------------------------------
		double mapVmZoom = Math.log(scale) / Math.log(2);
		if (mapVmZoom > 0)
			mapVmZoom = mapVmZoom + 0.5;
		if (mapVmZoom < 0)
			mapVmZoom = mapVmZoom - 0.5;
		// --------------------------------------------------------------------
		return (int) mapVmZoom;
		// --------------------------------------------------------------------
	}
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================
	// ====================================================================================

}