package com.zhsk.bbktool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;

public class BBK_Tool_Net {

	// UTF-8、ISO-8859-1、GBK
	public enum charsetNameType {
		UTF8("UTF-8", 1), ISO("ISO-8859-1", 2), GBK("GBK", 3);
		// 成员变量
		private String name;
		private int index;

		// 构造方法
		private charsetNameType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 普通方法
		public static String getName(int index) {
			for (charsetNameType c : charsetNameType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		// get set 方法
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public static void UdpSend(final String _localHost, final int server_port, final String message, charsetNameType t) {
		// --------------------------------------------------------
		byte[] messageByte = null;
		try {
			messageByte = message.getBytes(t.getName());
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return;
		}
		// --------------------------------------------------------
		UdpSend(_localHost, server_port, messageByte);
		// --------------------------------------------------------
	}

	public final static int PORT_Max = 65500;// 65535
	public static Context ctx = null;

	public static void initNet(Context _ctx) {
		ctx = _ctx;
	}

	public static void UdpSend(final String _localHost, final int server_port, final byte[] messageByte) {
		// --------------------------------------------------------
		if (!CheckNetworkState(ctx))
			return;
		// --------------------------------------------------------
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// --------------------------------------------------------
				InetAddress server_ip = null;
				try {
					server_ip = InetAddress.getByName(_localHost);
				} catch (UnknownHostException e) {
					e.printStackTrace();
					return;
				}
				DatagramSocket socket = null;
				try {
					socket = new DatagramSocket();
				} catch (SocketException e1) {
					e1.printStackTrace();
					return;
				}
				// --------------------------------------------------------
				int xPort = server_port > PORT_Max ? PORT_Max : server_port;
				DatagramPacket p = new DatagramPacket(messageByte, messageByte.length, server_ip, xPort);
				try {
					socket.send(p);
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// --------------------------------------------------------
			}
		};
		new Thread(runnable).start();
		// --------------------------------------------------------
	}

	//private static AlertDialog.Builder builder;
	private static boolean dialogIsShow = false;

	private static boolean CheckNetworkState(final Context ctx) {
		boolean flag = false;
		ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		if (!flag) {
			// --------------------------------------------------------
			if (dialogIsShow)
				return flag;
			dialogIsShow = true;
			// --------------------------------------------------------
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setTitle("Network not avaliable");//
			builder.setMessage("Current network is not avaliable, set it?");//
			builder.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ctx.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); // 直接进入手机中的wifi网络设置界面
					dialogIsShow = false;
				}
			});
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					dialogIsShow = false;
				}
			});
			builder.create();
			builder.show();
		}
		return flag;
	}
}
