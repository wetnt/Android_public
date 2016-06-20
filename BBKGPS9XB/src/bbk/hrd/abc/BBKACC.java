package bbk.hrd.abc;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import bbk.bbk.box.BBKSoft;
import bbk.sys.abc.BBKMsgBox;

public class BBKACC {

	// ====================================================================================
	// ####################################################################################
	// #############################�𶯿���################################################
	// ####################################################################################
	// ====================================================================================
	public void Acc_Click() {
		// ----------------------------------
		if (AccIsRun) {
			Acc_Click(false);
		} else {
			Acc_Click(true);
		}
		// ----------------------------------
	}

	public void Acc_Click(boolean key) {
		// ----------------------------------
		if (key) {
			ACC_Start();
		} else {
			ACC_Stop();
		}
		// ----------------------------------
		if (AccIsRun) {
			BBKMsgBox.tShow("ACC �ѿ�����");
		} else {
			BBKMsgBox.tShow("ACC �ѹرգ�");
		}
		// ----------------------------------
	}

	// ---------------------------------------------------------------
	// private static final int ELEMENT_COUNT = 30;
	// ---------------------------------------------------------------
	// private float[] samplingX = new float[ELEMENT_COUNT];
	// private float[] samplingY = new float[ELEMENT_COUNT];
	// private float[] samplingZ = new float[ELEMENT_COUNT];
	// private int position;
	// ---------------------------------------------------------------
	public int AccFloat = 0;
	// ---------------------------------------------------------------
	// private Activity bbkAct;
	// ---------------------------------------------------------------
	private Sensor AccSensor;// ������
	private SensorManager sensorManager;
	private int AccRate = SensorManager.SENSOR_DELAY_NORMAL;
	public boolean AccIsRun = false;

	// ---------------------------------------------------------------

	public void ACCInt(final Context ctxt) {
		// ---------------------------------------------------------------
		String Service = Context.SENSOR_SERVICE;
		sensorManager = (SensorManager) ctxt.getSystemService(Service);// ��ô�����������
		// ---------------------------------------------------------------
		if (sensorManager != null) {
			AccSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// �������������
		}
		// ---------------------------------------------------------------
		if (AccSensor != null) {
			// ����SENSOR_DELAY_UI��SENSOR_DELAY_FASTEST��SENSOR_DELAY_GAME�ȣ�
			// ���ݲ�ͬӦ�ã���Ҫ�ķ�Ӧ���ʲ�ͬ���������ʵ������趨
			sensorManager.registerListener(AccListener, AccSensor, AccRate);// ע��
		}
		// ---------------------------------------------------------------
		// ACC_Start();
		// ---------------------------------------------------------------
	}

	public void ACC_Start() {
		// ---------------------------------------------------------------
		if (AccSensor == null)
			return;
		// ---------------------------------------------------------------
		sensorManager.registerListener(AccListener, AccSensor, AccRate);// ע��
		AccIsRun = true;
		// ---------------------------------------------------------------
	}

	public void ACC_Stop() {
		// ---------------------------------------------------------------
		sensorManager.unregisterListener(AccListener);
		AccIsRun = false;
		// ---------------------------------------------------------------
	}

	private SensorEventListener AccListener = new SensorEventListener() {
		// ---------------------------------------------------------------
		public void onSensorChanged(SensorEvent e) {
			if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				// ---------------------------------------------------------------
				// event.values; //float[]������x,y,z
				// ---------------------------------------------------------------
				float x = e.values[SensorManager.DATA_X];
				float y = e.values[SensorManager.DATA_Y];
				float z = e.values[SensorManager.DATA_Z];
				// ---------------------------------------------------------------
				// samplingX[position] = x;
				// samplingY[position] = y;
				// samplingZ[position] = z;
				// ---------------------------------------------------------------
				AccFloat = (int) (Math.abs(x) + Math.abs(y) + Math.abs(z));
				// BBKDebug.ddd(AccFloat + "===" + x + " " + y + " " + z);
				BBKSoft.myCmps.TxtAcc.setText(AccFloat + "");
				// ---------------------------------------------------------------
			}
		}

		// ---------------------------------------------------------------

		// ---------------------------------------------------------------
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
		// ---------------------------------------------------------------
	};

	// ---------------------------------------------------------------
	// ---------------------------------------------------------------
	// ---------------------------------------------------------------
	// ҡ�μ����ӿ�
	public interface OnShakeListener {
		// ---------------------------------------------------------------
		public void onShake();
		// ---------------------------------------------------------------
	}
	// ---------------------------------------------------------------
	// ---------------------------------------------------------------
	// ---------------------------------------------------------------

}