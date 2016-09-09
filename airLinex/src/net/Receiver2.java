package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.zhsk.bbktool.d;

public class Receiver2 {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//-----------------------------------------------
		DatagramSocket socket = null;
		int port = 38888;
		InetAddress address = null;
		SocketAddress sendAddress;
		byte[] buf = new byte[1024];
		DatagramPacket packet;
		//-----------------------------------------------
		int clientPort;
		InetAddress clientAddress;
		String feedback;
		byte[] backbuf;
		DatagramPacket sendPacket;
		//-----------------------------------------------
		try {
			//-----------------------------------------------
			address = InetAddress.getLocalHost();
			socket = new DatagramSocket(port, address);
			packet = new DatagramPacket(buf, buf.length); //创建DatagramPacket对象  
			//-----------------------------------------------
			while (true) {
				//-----------------------------------------------
				socket.receive(packet); //通过套接字接收数据
				String getMsg = new String(buf, 0, packet.getLength());
				System.out.println("客户端发送的数据为：" + getMsg);
				BBKNetUDP.bufExp(buf);
				//-----------------------------------------------
				//从服务器返回给客户端数据  
				sendAddress = packet.getSocketAddress();
				clientAddress = packet.getAddress(); //获得客户端的IP地址  
				clientPort = packet.getPort(); //获得客户端的端口号  
				feedback = "Received";
				backbuf = feedback.getBytes();
				sendPacket = new DatagramPacket(backbuf, backbuf.length, sendAddress); //封装返回给客户端的数据  
				socket.send(sendPacket); //通过套接字反馈服务器数据  
				//-----------------------------------------------
				if (getMsg.indexOf("###&&&###__#@>---") >= 0){
					socket.close(); //关闭套接字
					d.s("socket.close()");
					break;
				}
				//-----------------------------------------------
				d.s(//
						clientAddress.getHostAddress() + "," + //
								clientAddress.getHostName() + "," + //
								clientPort + "," + //
								feedback//
				);
				//-----------------------------------------------
			}
			//-----------------------------------------------
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	
}
