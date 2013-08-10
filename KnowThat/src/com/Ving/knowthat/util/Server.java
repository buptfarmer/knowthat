package com.Ving.knowthat.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	private int port = 8189;// Ĭ�Ϸ������˿�
	private ServerSocket server;
	private Socket socket;
	public Server() {
	}

	// ����ָ���˿ڵķ�����
	public Server(int port) {
		this.port = port;
	}

	// �ṩ����
	public void service() {
		try {// ��������������
			server = new ServerSocket(port);
			// �ȴ��ͻ�����
			socket = server.accept();
			try {
				// ��ȡ�ͻ��˴�������Ϣ��DataInputStream
				DataInputStream in = new DataInputStream(
						socket.getInputStream());
				// ��ͻ��˷�����Ϣ��DataOutputStream
				DataOutputStream out = new DataOutputStream(
						socket.getOutputStream());
				// ��ȡ����̨�����Scanner
				Scanner scanner = new Scanner(System.in);
				System.out.println("Hello world, this is Server.");
				while (true) {
					// ��ȡ���Կͻ��˵���Ϣ
					String accpet = in.readUTF();
					System.out.println(accpet);
					String send = scanner.nextLine();
					System.out.println("��������" + send);
					// �ѷ������˵����뷢���ͻ���
					out.writeUTF("��������" + send);
				}
			} finally {// ��������ʧ�ܵĻ�����ִ��socket.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void service2() {
		try {
			// ��������������
			server = new ServerSocket(port);
			// �ȴ��ͻ�����
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

			// ��ͻ��˷�����Ϣ��DataOutputStream
			try {
				DataOutputStream out = new DataOutputStream(
						socket.getOutputStream());
//				while(true){
					
//					String send = scanner.nextLine();
//					System.out.println("me��" + "hello");
					// �ѷ������˵����뷢���ͻ���
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
