package bbk.sys.abc;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import bbk.bbk.box.BBKSoft;

public class BBKTimer {

	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================

	// ============================================================
	Timer timer = new Timer();
	TimerWork myTask = new TimerWork();// ��ʼ�����ǵ�����

	// ============================================================
	public void TimerStart() {
		timer.schedule(myTask, 0, 1000);
		// timer.schedule(myTask, 500, 3000);//
		// ��5���ִ�д�����,ÿ�μ��60��,�������һ��Data����,�Ϳ�����ĳ���̶���ʱ��ִ���������.
		// timer.cancel();// ʹ����������˳�����
	}

	public void TimerStop() {
		timer.cancel();// ʹ����������˳�����
	}

	// ============================================================
	public class TimerWork extends TimerTask {
		public void run() {
			// ============================================================
			handlerTimer.post(RunnableTimer);
			// ============================================================
		}
	}

	private Handler handlerTimer = new Handler();
	private Runnable RunnableTimer = new Runnable() {
		public void run() {
			// ------------------------------------------------------
			BBKSoft.TxtGpsRuns();
			// ------------------------------------------------------
		}
	};

	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================

}