package com.boboking.tools.bluetooth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

import com.boboking.brushos.MainBox;
import com.boboking.tools.DigitalTrans;
import com.boboking.tools.d;

public class BBKBlueTooth {

	private Activity act;
	// ========================================================================
	// 消息处理器使用的常量
	private static final int FOUND_DEVICE = 1; // 发现设备
	private static final int START_DISCOVERY = 2; // 开始查找设备
	private static final int FINISH_DISCOVERY = 3; // 结束查找设备
	private static final int CONNECT_FAIL = 4; // 连接失败
	private static final int CONNECT_SUCCEED_P = 5; // 主动连接成功
	private static final int CONNECT_SUCCEED_N = 6; // 收到连接成功
	private static final int RECEIVE_MSG = 7; // 收到消息
	private static final int SEND_MSG = 8; // 发送消息
	// ========================================================================
	private BluetoothAdapter bluetoothAdapter = null;
	private BluetoothSocket socket;
	// ========================================================================
	private BBKBlueToothConnectedThread connectedThread; // 与远程蓝牙连接成功时启动
	private BBKBlueToothConnectThread connectThread; // 用户点击列表中某一项，要与远程蓝牙连接时启动
	private List<BluetoothDevice> foundDevices;
	private BluetoothDevice nowDev = null;
	private String DevAddress = "00:13:03:28:11:31";

	// ========================================================================
	public BBKBlueTooth(Activity _act) {
		// -----------------------------------------------------------------
		this.act = _act;
		// -----------------------------------------------------------------
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		BBKBlueTooth_IsHasDevices();// 判断本机是否有蓝牙
		BBKBlueTooth_SetCanFind();// 使蓝牙可被发现
		// -----------------------------------------------------------------
		foundDevices = new ArrayList<BluetoothDevice>();
		BBKBlueTooth_FindDevices();// 查找设备
		BBKBlueTooth_ConnectDev(nowDev);// 连接设备
		// BBKBlueTooth_SendString("abc");// 发送消息
		BBKBlueTooth_BroadcastSYS();// 注册广播接收器
		// -----------------------------------------------------------------
		// BBKBlueTooth_DestroySYS();// 注销广播接收器
		// -----------------------------------------------------------------
	}

	public void BBKBlueTooth_IsHasDevices() {// 判断本机是否有蓝牙和是否处于可用状态
		if (bluetoothAdapter == null) {
			d.s("本机没有蓝牙设备！");
		}
		if (!bluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			act.startActivityForResult(enableIntent, 1);
		} else {
			// chooseMode();
		}
	}

	public void BBKBlueTooth_SetCanFind() {// 使蓝牙可被发现
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		act.startActivity(intent);
	}

	public void BBKBlueTooth_FindDevices() {// 查找设备
		// -----------------------------------------------------------------
		Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();
		final List<BluetoothDevice> bondedDevices = new ArrayList<BluetoothDevice>();
		if (deviceSet.size() > 0) {
			for (Iterator<BluetoothDevice> it = deviceSet.iterator(); it.hasNext();) {
				BluetoothDevice device = (BluetoothDevice) it.next();
				bondedDevices.add(device);
			}
		}
		d.s(bondedDevices.toString());
		for (int i = 0; i < bondedDevices.size(); i++) {
			d.s(bondedDevices.get(i).getName() + "==" + bondedDevices.get(i).getAddress());
			if (bondedDevices.get(i).getAddress().indexOf(DevAddress) >= 0)
				nowDev = bondedDevices.get(i);
		}
	}

	public void BBKBlueTooth_ConnectDev(BluetoothDevice device) {// 连接设备
		if (device == null) {
			d.s("device==null");
			return;
		}
		bluetoothAdapter.cancelDiscovery();
		d.s("正在与 " + device.getName() + " 连接 .... ");
		connectThread = new BBKBlueToothConnectThread(device, mHandler, false);
		connectThread.start();
		MainBox.BlueTooth_OnOffline_Set(false);
	}

	public void BBKBlueTooth_SendString(String sendStr) {// 发送内容
		if (socket == null) {
			d.s("请先连接蓝牙设备");
		} else if (sendStr.equals("")) {
			d.s("发送内容不能为空");
		} else {
			byte[] z = DigitalTrans.hex2byte(sendStr);
			connectedThread.write(z);
		}
	}

	// ========================================================================
	// ========================================================================
	// ========================================================================
	public void BBKBlueTooth_BroadcastSYS() {// 注册广播接收器
		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				String actionStr = arg1.getAction();
				if (actionStr.equals(BluetoothDevice.ACTION_FOUND)) {
					BluetoothDevice device = arg1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					foundDevices.add(device);
					d.s("找到蓝牙设备：" + device.getName());
					mHandler.sendEmptyMessage(FOUND_DEVICE);
				} else if (actionStr.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
					mHandler.sendEmptyMessage(START_DISCOVERY);
				} else if (actionStr.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
					mHandler.sendEmptyMessage(FINISH_DISCOVERY);
				}
			}
		};
		IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		IntentFilter filter3 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		act.registerReceiver(mReceiver, filter1);
		act.registerReceiver(mReceiver, filter2);
		act.registerReceiver(mReceiver, filter3);
	}

	public void BBKBlueTooth_DestroySYS() {// 注销广播接收器，中止线程关闭socket
		try {
			act.unregisterReceiver(mReceiver);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		connectedThread.stopNow();

		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (connectThread != null) {
			connectThread.interrupt();
		}
		if (bluetoothAdapter.isEnabled()) {
			d.s("请手动关闭蓝牙");
		}
	}

	private BroadcastReceiver mReceiver;// 广播接收器，主要是接收蓝牙状态改变时发出的广播
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {// 消息处理器..日理万鸡的赶脚...
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FOUND_DEVICE:
				d.s(foundDevices.toString());
				break;
			case START_DISCOVERY:
				d.s("Start Finding Dev");
				break;
			case FINISH_DISCOVERY:
				d.s("Finish Finding Dev");
				break;
			case CONNECT_FAIL:
				d.s("连接失败");
				MainBox.BlueTooth_OnOffline_Set(false);
				break;
			case CONNECT_SUCCEED_P:
			case CONNECT_SUCCEED_N:
				String stateStr = msg.getData().getString("name");
				if (msg.what == CONNECT_SUCCEED_P) {
					socket = connectThread.getSocket();
					connectedThread = new BBKBlueToothConnectedThread(socket, mHandler);
					connectedThread.start();
					d.s("连接成功" + stateStr);
					MainBox.BlueTooth_OnOffline_Set(true);
				} else {
					if (connectThread != null) {
						connectThread.interrupt();
					}
					connectedThread = new BBKBlueToothConnectedThread(socket, mHandler);
					connectedThread.start();
					d.s("连接..." + stateStr);
					MainBox.BlueTooth_OnOffline_Set(false);
				}
				break;
			case RECEIVE_MSG:
			case SEND_MSG:
				String chatStr = msg.getData().getString("str");
				if (msg.what == SEND_MSG) {
					d.s("Send==" + chatStr);
				} else {
					d.s("Gets==" + chatStr);
				}
				break;
			}
		}
	};
	// ========================================================================
	// ========================================================================
	// ========================================================================

}
