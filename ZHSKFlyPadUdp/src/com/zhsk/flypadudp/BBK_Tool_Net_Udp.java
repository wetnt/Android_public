package com.zhsk.flypadudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.zhsk.bbktool.BBK_Tool_String;

public class BBK_Tool_Net_Udp {

	private int server_port;
	private InetAddress server_ip;
	private DatagramSocket socket;

	public BBK_Tool_Net_Udp(String _localHost, int _server_port) {
		init(_localHost, _server_port);
	}

	public boolean init(String _localHost, int _server_port) {
		// --------------------------------------------------------
		this.server_port = _server_port;
		// --------------------------------------------------------
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}
		// --------------------------------------------------------
		try {
			this.server_ip = InetAddress.getByName(_localHost);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		// --------------------------------------------------------
	}

	public void send(final String message) {
		// --------------------------------------------------------
		final int msg_length = message.length();
		if (msg_length == 0)
			return;
		// --------------------------------------------------------
		final byte[] messageByte = BBK_Tool_String.getBytes_UTF8(message);
		if (messageByte == null)
			return;
		// --------------------------------------------------------
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				DatagramPacket p = new DatagramPacket(messageByte, msg_length, server_ip, server_port);
				try {
					socket.send(p);
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
