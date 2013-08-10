package com.Ving.knowthat.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	private int port = 8189;// 默认服务器端口
	private ServerSocket server;
	private Socket socket;
	public Server() {
	}

	// 创建指定端口的服务器
	public Server(int port) {
		this.port = port;
	}

	// 提供服务
	public void service() {
		try {// 建立服务器连接
			server = new ServerSocket(port);
			// 等待客户连接
			socket = server.accept();
			try {
				// 读取客户端传过来信息的DataInputStream
				DataInputStream in = new DataInputStream(
						socket.getInputStream());
				// 向客户端发送信息的DataOutputStream
				DataOutputStream out = new DataOutputStream(
						socket.getOutputStream());
				// 获取控制台输入的Scanner
				Scanner scanner = new Scanner(System.in);
				System.out.println("Hello world, this is Server.");
				while (true) {
					// 读取来自客户端的信息
					String accpet = in.readUTF();
					System.out.println(accpet);
					String send = scanner.nextLine();
					System.out.println("服务器：" + send);
					// 把服务器端的输入发给客户端
					out.writeUTF("服务器：" + send);
				}
			} finally {// 建立连接失败的话不会执行socket.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void service2() {
		try {
			// 建立服务器连接
			server = new ServerSocket(port);
			// 等待客户连接
			socket = server.accept();
//			System.out.println("Hello world, this is Server.");
			new Thread(sender).start();
			new Thread(receiver).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			socket.close();
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
					socket = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (server != null) {
					server.close();
					server = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private Runnable receiver = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				DataInputStream in = new DataInputStream(
						socket.getInputStream());
				while (true) {
					String accpet = in.readUTF();
//					System.out.println("other:" + accpet);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};
	
	private Runnable sender = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
//			Scanner scanner = new Scanner(System.in);

			// 向客户端发送信息的DataOutputStream
			try {
				DataOutputStream out = new DataOutputStream(
						socket.getOutputStream());
//				while(true){
					
//					String send = scanner.nextLine();
//					System.out.println("me：" + "hello");
					// 把服务器端的输入发给客户端
					out.writeUTF("hello");
//				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	public static void main(String[] args) {
		new Server().service2();
	}
}
