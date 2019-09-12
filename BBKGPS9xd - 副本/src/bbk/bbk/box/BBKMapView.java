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
	private final int NONE = 0;// 初始状态
	private final int DRAG = 1;// 拖动
	private final int ZOOM = 2;// 缩放
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
				// 设置拖拉模式
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
				// 设置多点触摸模式
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
				// 若为DRAG模式，则点击移动图片
				// ------------------------------------------------------
				case MotionEvent.ACTION_MOVE:
					// ------------------------------------------------------
					// LabInf.setText("ACTION_MOVE");
					if (mode == DRAG) {
						mapVwMatrix.set(savedMatrix);
						// 设置位移
						mapVwMatrix.postTranslate(event.getX() - mapVwStart.x, event.getY() - mapVwStart.y);
					}
					// 若为ZOOM模式，则多点触摸缩放
					else if (mode == ZOOM) {
						newDist = spacing(event);
						if (newDist > 10f) {
							mapVwMatrix.set(savedMatrix);
							mapscale = newDist / mapVwOldDist;
							// 设置缩放比例和图片中点位置
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
			private float spacing(MotionEvent event) {// 计算移动距离
				// -----------------------------------------------------------
				float x = event.getX(0) - event.getX(1);
				float y = event.getY(0) - event.getY(1);
				// -----------------------------------------------------------
				return (float) Math.sqrt(x * x + y * y);
				// -----------------------------------------------------------
			}

			private void midPoint(PointF point, MotionEvent event) {// 计算中点位置
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
		if (DoubleTapCheck()) {// 判断执行是否双击操作
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
		if (Math.abs(mx) < 6 && Math.abs(my) < 6) {// 添加测量新点
			BBKSoft.MapTouch(pdx, pdy);
		} else {// 地图拖动
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

	private boolean DoubleTapCheck() {// 双击事件
		secClick = System.currentTimeMillis();
		if (secClick - firClick < doubleclicktimex) {
			return true;
		} else {
			firClick = System.currentTimeMillis();
			return false;
		}
	}

	// ====================================================================================

	private void DoubleTapWork() {// 双击事件
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

	private void MapZoomNewRun() {// 地图旋转缩放刷新
		// ----------------------------------------------------------------------------
		float angle = 0;// BBK.myMaps.MapAngle;// 地图旋转角度
		// 相对于地图中心点的坐标位置XY值
		int pcx = pdx - BBKMap.MapWx;
		int pcy = pdy - BBKMap.MapHy;
		// ----------------------------------------------------------------------------
		MapPoiXY pc = new MapPoiXY(0, 0);// 坐标原点=地图中心
		MapPoiXY pz = new MapPoiXY(pcx, pcy);// 以地图中心为坐标原点的双击位置
		// 以地图中心为原点，双击位置，逆向旋转地图旋转角度后，获得的坐标值
		MapPoiXY pm = BBKMapMath.PointRotate(pc, pz, -angle);
		// ----------------------------------------------------------------------------
		double px = BBKMap.PixJXT + pm.x;// 墨卡托投影像素值Y
		double py = BBKMap.PixWYT + pm.y;// 墨卡托投影像素值X
		// ----------------------------------------------------------------------------
		int z = BBKMap.mapzm;
		double w = BBKMapMath.GetLatByPixel(py, z);// 双击位置纬度
		double j = BBKMapMath.GetLonByPixel(px, z);// 双击位置精度
		BBKSoft.myMaps.MapSetWJZ(w, j, 1);// 以双击位置为地图中心，放大地图1级
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