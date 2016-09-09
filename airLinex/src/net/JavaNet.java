package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class JavaNet {

	//1：客户端程序
	/**
	 * 使用Sender类来代表客户端程序，
	 * 
	 * @author ccna_zhang
	 * 
	 */
	public class Sender {

		public void main(String[] args) {
			String msg = "Hello, World";
			byte[] buf = msg.getBytes();
			try {
				InetAddress address = InetAddress.getByName("192.168.1.80"); //服务器地址  
				int port = 8080; //服务器的端口号  
				//创建发送方的数据报信息  
				DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);

				DatagramSocket socket = new DatagramSocket(); //创建套接字  
				socket.send(dataGramPacket); //通过套接字发送数据  

				//接收服务器反馈数据  
				byte[] backbuf = new byte[1024];
				DatagramPacket backPacket = new DatagramPacket(backbuf, backbuf.length);
				socket.receive(backPacket); //接收返回数据  
				String backMsg = new String(backbuf, 0, backPacket.getLength());
				System.out.println("服务器返回的数据为:" + backMsg);

				socket.close();

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//2：服务器端程序
	/**
	 * 服务器端程序
	 * 
	 * @author ccna_zhang
	 * 
	 */
	public class Receiver {

		public void main(String[] args) {

			try {
				InetAddress address = InetAddress.getLocalHost();
				int port = 8080;

				//创建DatagramSocket对象  
				DatagramSocket socket = new DatagramSocket(port, address);

				byte[] buf = new byte[1024]; //定义byte数组  
				DatagramPacket packet = new DatagramPacket(buf, buf.length); //创建DatagramPacket对象  

				socket.receive(packet); //通过套接字接收数据  

				String getMsg = new String(buf, 0, packet.getLength());
				System.out.println("客户端发送的数据为：" + getMsg);

				//从服务器返回给客户端数据  
				InetAddress clientAddress = packet.getAddress(); //获得客户端的IP地址  
				int clientPort = packet.getPort(); //获得客户端的端口号  
				SocketAddress sendAddress = packet.getSocketAddress();
				String feedback = "Received";
				byte[] backbuf = feedback.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(backbuf, backbuf.length, sendAddress); //封装返回给客户端的数据  
				socket.send(sendPacket); //通过套接字反馈服务器数据  

				socket.close(); //关闭套接字  

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
