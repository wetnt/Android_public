package com.zhsk.flypadudp;

import java.io.DataOutputStream;
import java.net.Socket;

import com.zhsk.bbktool.d;

import android.os.Handler;

public class BBKTcpIp {

	//====================================================================
	//====================================================================
	//====================================================================
	public String IP = "192.168.10.163";
	public int Port = 9527;// udpPort;
	public void set_IP_Port(String IP, int port) {
		this.IP = IP;
		this.Port = port;
	}

	public void send_Date(byte[] data, String str) {
		Thread thread = new MyThread(data, str);
		thread.start();
	}
	//====================================================================
	//====================================================================
	//====================================================================

	private class MyThread extends Thread {

		Handler hd = new Handler();
		Socket s = null;
		byte[] data;
		String str;

		public MyThread(byte[] data, String str) {
			this.data = data;
			this.str = str;
		}

		public void run() {

			try {
				s = new Socket(IP, Port);
				// ��ȡҪ���͵��ַ���
				d.s("s=" + s);

				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				// ���ַ�������UTF-8�ֽ�����ʽ���䡣�ȴ��䳤�ȣ��ٴ����ֽ����ݡ�
				
				if(data!=null){
					dos.write(data);
				}else{
					dos.writeUTF(str);	
				}
				dos.flush();
				dos.close();

				// DataInputStream dis = new
				// DataInputStream(s.getInputStream());
				// int id = dis.readInt();
				// d.s("id=" + id);

				s.close();
				hd.post(new Runnable() {
					@Override
					public void run() {
						d.s("���ͳɹ�!");
					}
				});

			} catch (Exception e1) {
				hd.post(new Runnable() {
					@Override
					public void run() {
						d.s("���ͳ�ʧ�ܣ�");
					}
				});

			} // try

		}// run

	}// MyThread
	
	//====================================================================
	//====================================================================
	//====================================================================

	// public void connectServer(String _IP, int port) {
	// this.IP = _IP;
	// Socket s = null;
	// try {
	// s = new Socket(IP, port);
	// System.out.println("s=" + s);
	//
	// DataInputStream dis = new DataInputStream(s.getInputStream());
	// int id = dis.readInt();
	//
	// System.out.println("id=" + id);
	//
	// } catch (UnknownHostException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// if (s != null) {
	// try {
	// s.close();
	// s = null;
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

}
