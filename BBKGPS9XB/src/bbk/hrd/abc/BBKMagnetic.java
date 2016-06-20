package bbk.hrd.abc;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import bbk.bbk.box.BBKSoft;

public class BBKMagnetic {

	// http://geomag.usgs.gov/ 实时磁偏角查询
	// http://www.ngdc.noaa.gov/geomag-web/#declination
	// 6.6° W changing by 0.05° W per year
	// ============== 磁偏角 ============================================
	// 地名 磁偏角
	// 漠河 11°00'
	// 齐齐哈尔 9°54'
	// 哈尔滨 9°39'
	// 长春 8°53'
	// 满洲里 8°40'
	// 沈阳 7°44'
	// 旅大 6°35'
	// 北京 5°50'
	// 天津 5°30'
	// 济南 5°01'
	// 呼和浩特 4°36'
	// 徐州 4°27'
	// 上海 4°26'
	// 太原 4°11'
	// 包头 4°03'
	// 南京 4°00'
	// 合肥 3°52'
	// 郑州 3°50'
	// 杭州 3°50'
	// 许昌 3°40'
	// 九江 3°03'
	// 武汉 2°54'
	// 南昌 2°48'
	// 银川 2°35'
	// 台北 2°32'
	// 西安 2°29'
	// 长沙 2°14'
	// 赣州 2°01'
	// 衡阳 1°56'
	// 厦门 1°50'
	// 兰州 1°44'
	// 重庆 1°34'
	// 遵义 1°26'
	// 西宁 1°22'
	// 桂林 1°22'
	// 贵阳 1°17'
	// 成都 1°16'
	// 广州 1°09'
	// 柳州 1°08'
	// 东沙群岛 1°05'
	// 昆明 1°00'
	// 南宁 0°50'
	// 湛江 0°44'
	// 凭祥 0°39'
	// 海口 0°29'
	// 拉萨 0°21'
	// 珠穆朗玛 0°19'
	// 西沙群岛 0°10'
	// 曾母暗沙 0°24'(东）
	// 南沙群岛 0°35'(东）
	// 乌鲁木齐 2°44'(东）
	// ============== 磁偏角 ============================================

	// ============== BBK Sensor ============================================
	// ============== BBK Sensor ============================================
	// ============== BBK Sensor ============================================
	public int CompassAngle = 0;
	Sensor magnetic;
	SensorManager mySensor;
	SensorEventListener myListener;
	// =======================================================================
	int SensorRate = SensorManager.SENSOR_DELAY_UI;

	// SensorManager.SENSOR_DELAY_NORMAL：默认的获得传感器数据的速度。
	// SensorManager.SENSOR_DELAY_GAME：如果利用传感器开发游戏，建议使用该值。
	// SensorManager.SENSOR_DELAY_UI：如果使用传感器更新UI中的数据，建议使用该值。
	// ========================================================================
	public void SensorInt(final Context pthis) {
		// ----------------------------------------------------
		// 第一步：获得SensorManager对象,返回的就是一个硬件设备的控制器
		String Service = Context.SENSOR_SERVICE;
		mySensor = (SensorManager) pthis.getSystemService(Service);
		// 第二步：获得特定的传感器
		// magnetic = mySensor.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		magnetic = mySensor.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		// 第三步：创建SensorEventListener监听传感器的值改变并且做出相应的动作
		myListener = new SensorEventListener() {
			// 传感器的值改变调用此方法
			public void onSensorChanged(SensorEvent event) {
				// -----------------------------------------------------------
				CompassAngle = SensorFomart(event.values[0]);
				BBKSoft.myCmps.compassFalsh(CompassAngle);
				// BBK.myBoxs.TxtAcc.setText(CompassAngle);
				// BBKDebug.ddd("0=" + curAngle + "==" + CompassAngle);
				// -----------------------------------------------------------
				// 获得值之后，你就可以进行相应的处理啦
				// float x = event.values[0];
				// float y = event.values[1];
				// float z = event.values[2];
				// -----------------------------------------------------------
			}

			// 传感器的精确度改变调用此方法
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
		// ------------------------------------------------------------------
	}

	public void SensorRegister() {
		// ---------------------------------------------------------------
		mySensor.registerListener(myListener, magnetic, SensorRate);
		// ---------------------------------------------------------------
	}

	public void SensorUnRegister() {
		// ---------------------------------------------------------------
		mySensor.unregisterListener(myListener);
		// ---------------------------------------------------------------
	}

	public int SensorFomart(float tpAngle) {
		// ----------------------------------------------
		if (tpAngle > 360) {
			tpAngle = tpAngle - 360;
		}
		if (tpAngle < -360) {
			tpAngle = tpAngle + 360;
		}
		return (int) tpAngle;
		// ----------------------------------------------
	}

	// =======================================================================
	// =======================================================================
	// =======================================================================
	// Date debugTime = new Date(System.currentTimeMillis());
	// public void SensorDebug(int cur) {
	// // -----------------------------------------------------------
	// Date tx = new Date(System.currentTimeMillis());
	// if (tx.getSeconds() != debugTime.getSeconds()) {
	// BBKDebug.ddd("-------");
	// debugTime = new Date(System.currentTimeMillis());
	// }
	// BBKDebug.ddd(cur + "");
	// // -----------------------------------------------------------
	// }
	// =======================================================================
}
