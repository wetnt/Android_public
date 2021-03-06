package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.zhsk.bbktool.d;

/**
 * 服务器端程序
 * 
 * @author ccna_zhang
 * 
 */
public class Receiver {

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

	public static final int PORT = 8080;

	public static void myServer(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Started:" + s);
		try {
			Socket socket = s.accept();
			try {
				System.out.println("连接被接受" + socket);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				while (true) {
					String str = in.readLine();
					if (str.endsWith("END"))
						break;
					System.out.println("Echoing: " + str);
					out.println(str);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
				System.out.println("closing ...");
				socket.close();
			}
		} finally {
			s.close();
		}
	}
}