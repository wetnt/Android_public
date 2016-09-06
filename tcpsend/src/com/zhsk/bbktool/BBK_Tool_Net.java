package com.zhsk.bbktool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

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

	public static void UdpSend(final String _localHost, final int server_port, final String message,
			charsetNameType t) {
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

	public final static int PORT_Max = 65500;//65535
	public static void UdpSend(final String _localHost, final int server_port, final byte[] messageByte) {
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
				}
				DatagramSocket socket = null;
				try {
					socket = new DatagramSocket();
				} catch (SocketException e1) {
					e1.printStackTrace();
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
}
