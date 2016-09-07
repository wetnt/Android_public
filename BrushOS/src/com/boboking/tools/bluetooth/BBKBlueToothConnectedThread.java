package com.boboking.tools.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import com.boboking.tools.d;

// 已建立连接后启动的线程，需要传进来两个参数
// socket用来获取输入流，读取远程蓝牙发送过来的消息
// handler用来在收到数据时发送消息
public class BBKBlueToothConnectedThread extends Thread {

	private static final int RECEIVE_MSG = 7;
	private static final int SEND_MSG = 8;
	public boolean isStop;
	private BluetoothSocket socket;
	@SuppressWarnings("unused")
	private Handler handler;
	private InputStream is;
	private OutputStream os;

	public BBKBlueToothConnectedThread(BluetoothSocket s, Handler h) {
		socket = s;
		handler = h;
		isStop = false;
	}

	public void run() {
		d.s("connectedThread.run()");
		byte[] buf;
		int size;
		while (!isStop) {
			size = 0;
			buf = new byte[1024];
			try {
				is = socket.getInputStream();// 等待数据
				size = is.read(buf);
				// d.s("读取了一次数据" + size);
			} catch (IOException e) {
				e.printStackTrace();
				isStop = true;
			}
			if (size > 0) {
				// 把读取到的数据放进Bundle再放进Message，然后发送出去
				sendMessageToHandler(buf, RECEIVE_MSG);
			}
		}
	}

	public void write(byte[] buf) {
		try {
			os = socket.getOutputStream();
			os.write(buf);
		} catch (IOException e) {
		}
		d.s(buf.length + "---");
		sendMessageToHandler(buf, SEND_MSG);
	}

	private void sendMessageToHandler(byte[] buf, int mode) {
		// ----------------------------------------------------
		BBKMPU6050_Data.BufExp(buf);
		// ----------------------------------------------------
		// String msgStr=new String(buf);
		// Bundle bundle=new Bundle();
		// bundle.putString("str", msgStr);
		// Message msg=new Message();
		// msg.setData(bundle);
		// msg.what=mode;
		// handler.sendMessage(msg);
		// ----------------------------------------------------
	}

	public void stopNow() {
		isStop = true;
		try {
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (this != null) {
			this.interrupt();
		}
	}
}
