package bbk.hrd.abc;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import bbk.bbk.box.BBKSoft;

public class BBKMagnetic {

	// http://geomag.usgs.gov/ ʵʱ��ƫ�ǲ�ѯ
	// http://www.ngdc.noaa.gov/geomag-web/#declination
	// 6.6�� W changing by 0.05�� W per year
	// ============== ��ƫ�� ============================================
	// ���� ��ƫ��
	// Į�� 11��00'
	// ������� 9��54'
	// ������ 9��39'
	// ���� 8��53'
	// ������ 8��40'
	// ���� 7��44'
	// �ô� 6��35'
	// ���� 5��50'
	// ��� 5��30'
	// ���� 5��01'
	// ���ͺ��� 4��36'
	// ���� 4��27'
	// �Ϻ� 4��26'
	// ̫ԭ 4��11'
	// ��ͷ 4��03'
	// �Ͼ� 4��00'
	// �Ϸ� 3��52'
	// ֣�� 3��50'
	// ���� 3��50'
	// ��� 3��40'
	// �Ž� 3��03'
	// �人 2��54'
	// �ϲ� 2��48'
	// ���� 2��35'
	// ̨�� 2��32'
	// ���� 2��29'
	// ��ɳ 2��14'
	// ���� 2��01'
	// ���� 1��56'
	// ���� 1��50'
	// ���� 1��44'
	// ���� 1��34'
	// ���� 1��26'
	// ���� 1��22'
	// ���� 1��22'
	// ���� 1��17'
	// �ɶ� 1��16'
	// ���� 1��09'
	// ���� 1��08'
	// ��ɳȺ�� 1��05'
	// ���� 1��00'
	// ���� 0��50'
	// տ�� 0��44'
	// ƾ�� 0��39'
	// ���� 0��29'
	// ���� 0��21'
	// �������� 0��19'
	// ��ɳȺ�� 0��10'
	// ��ĸ��ɳ 0��24'(����
	// ��ɳȺ�� 0��35'(����
	// ��³ľ�� 2��44'(����
	// ============== ��ƫ�� ============================================

	// ============== BBK Sensor ============================================
	// ============== BBK Sensor ============================================
	// ============== BBK Sensor ============================================
	public int CompassAngle = 0;
	Sensor magnetic;
	SensorManager mySensor;
	SensorEventListener myListener;
	// =======================================================================
	int SensorRate = SensorManager.SENSOR_DELAY_UI;

	// SensorManager.SENSOR_DELAY_NORMAL��Ĭ�ϵĻ�ô��������ݵ��ٶȡ�
	// SensorManager.SENSOR_DELAY_GAME��������ô�����������Ϸ������ʹ�ø�ֵ��
	// SensorManager.SENSOR_DELAY_UI�����ʹ�ô���������UI�е����ݣ�����ʹ�ø�ֵ��
	// ========================================================================
	public void SensorInt(final Context pthis) {
		// ----------------------------------------------------
		// ��һ�������SensorManager����,���صľ���һ��Ӳ���豸�Ŀ�����
		String Service = Context.SENSOR_SERVICE;
		mySensor = (SensorManager) pthis.getSystemService(Service);
		// �ڶ���������ض��Ĵ�����
		// magnetic = mySensor.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		magnetic = mySensor.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		// ������������SensorEventListener������������ֵ�ı䲢��������Ӧ�Ķ���
		myListener = new SensorEventListener() {
			// ��������ֵ�ı���ô˷���
			public void onSensorChanged(SensorEvent event) {
				// -----------------------------------------------------------
				CompassAngle = SensorFomart(event.values[0]);
				BBKSoft.myCmps.compassFalsh(CompassAngle);
				// BBK.myBoxs.TxtAcc.setText(CompassAngle);
				// BBKDebug.ddd("0=" + curAngle + "==" + CompassAngle);
				// -----------------------------------------------------------
				// ���ֵ֮����Ϳ��Խ�����Ӧ�Ĵ�����
				// float x = event.values[0];
				// float y = event.values[1];
				// float z = event.values[2];
				// -----------------------------------------------------------
			}

			// �������ľ�ȷ�ȸı���ô˷���
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
