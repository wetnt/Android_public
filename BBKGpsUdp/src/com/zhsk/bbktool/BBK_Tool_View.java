package com.zhsk.bbktool;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.view.View;
import android.view.View.MeasureSpec;
import android.webkit.WebView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class BBK_Tool_View {

	// http://blog.csdn.net/android_jianbo/article/details/50669364
	public static Bitmap get_View_Bitmap(View view) {
		// -------------------------------------------------
		if (view == null)
			return null;
		// -------------------------------------------------
		Bitmap bitmap = Bitmap.createBitmap(//
				view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
		// -------------------------------------------------
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		bitmap = view.getDrawingCache();
		// view.destroyDrawingCache();
		// -------------------------------------------------

		// Bitmap normalViewScreenshot =
		// ScreenShotUtils.getNormalViewScreenshot(mFrameContent);
		// if (normalViewScreenshot != null) {
		// Bitmap b = Bitmap.createBitmap(normalViewScreenshot);
		// mImageResult.setImageBitmap(b);
		// mFrameContent.destroyDrawingCache();
		// }
		//

		return bitmap;
		// -------------------------------------------------
	}

	public static byte[] get_View_Bitmap_Data(View view) {
		return BBK_Tool_Image.Bitmap2PngBytes(getNormalViewScreenshot(view));
	}

	// =====================================================================================
	// =====================================================================================
	// =====================================================================================
	// 屏幕可见区域的截图
	// 整个屏幕截图的话可以用View view = getWindow().getDecorView();
	public static Bitmap getNormalViewScreenshot(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		return view.getDrawingCache();
	}

	// scrollview的整体截屏
	public static Bitmap getWholeScrollViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	// webview的整体截图
	public static Bitmap getWholeWebViewToBitmap(WebView webView) {
		@SuppressWarnings("deprecation")
		Picture snapShot = webView.capturePicture();
		Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		snapShot.draw(canvas);
		return bmp;
	}

	// listview的整体截图
	public static Bitmap getWholeListViewItemsToBitmap(ListView listview) {

		ListAdapter adapter = listview.getAdapter();
		int itemscount = adapter.getCount();
		int allitemsheight = 0;
		List<Bitmap> bmps = new ArrayList<Bitmap>();

		for (int i = 0; i < itemscount; i++) {

			View childView = adapter.getView(i, null, listview);
			childView.measure(MeasureSpec.makeMeasureSpec(listview.getWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

			childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
			childView.setDrawingCacheEnabled(true);
			childView.buildDrawingCache();
			bmps.add(childView.getDrawingCache());
			allitemsheight += childView.getMeasuredHeight();
		}

		Bitmap bigbitmap = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
		Canvas bigcanvas = new Canvas(bigbitmap);

		Paint paint = new Paint();
		int iHeight = 0;

		for (int i = 0; i < bmps.size(); i++) {
			Bitmap bmp = bmps.get(i);
			bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
			iHeight += bmp.getHeight();

			bmp.recycle();
			bmp = null;
		}
		return bigbitmap;
	}

	// 需要多次截图的话，需要用到 view.destroyDrawingCache();
	public static void adfasdf() {
		// Bitmap bmp = getNormalViewScreenshot(mFrameContent);
		// if (bmp != null) {
		// Bitmap b = Bitmap.createBitmap(bmp);
		// mImageResult.setImageBitmap(b);
		// mFrameContent.destroyDrawingCache();
		// }
	}

	// View相对于屏幕的位置
	public static int[] get_View_Location(View v) {
		int[] loc = new int[4];
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		loc[0] = location[0];
		loc[1] = location[1];
		loc[2] = v.getWidth();
		loc[3] = v.getHeight();
		return loc;
	}

	// public static int[] get_View_Location(View v) {
	// int[] loc = new int[4];
	// int[] location = new int[2];
	// v.getLocationOnScreen(location);
	// loc[0] = location[0];
	// loc[1] = location[1];
	// int w = View.MeasureSpec.makeMeasureSpec(0,
	// View.MeasureSpec.UNSPECIFIED);
	// int h = View.MeasureSpec.makeMeasureSpec(0,
	// View.MeasureSpec.UNSPECIFIED);
	// v.measure(w, h);
	// loc[2] = v.getMeasuredWidth();
	// loc[3] = v.getMeasuredHeight();
	// return loc;
	// }
}
