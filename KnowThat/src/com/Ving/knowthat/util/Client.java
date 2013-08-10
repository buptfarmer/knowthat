package com.Ving.knowthat.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String host = "localhost";// Ĭ�����ӵ�����  
    private int port = 8189;// Ĭ�����ӵ��˿�8189  
    private Socket socket ;
    public Client() {  
  
    }  
  
    // ���ӵ�ָ���������Ͷ˿�  
    public Client(String host, int port) {  
        this.host = host;  
        this.port = port;  
    }  
  
    public void chat() {  
        try {  
            // ���ӵ�������  
            socket = new Socket(host, port);  
  
            try {  
                // ��ȡ�������˴�������Ϣ��DataInputStream  
                DataInputStream in = new DataInputStream(socket  
                        .getInputStream());  
                // ��������˷�����Ϣ��DataOutputStream  
                DataOutputStream out = new DataOutputStream(socket  
                        .getOutputStream());  
  
                // װ�α�׼�����������ڴӿ���̨����  
                Scanner scanner = new Scanner(System.in);  
                System.out.println("Hello world, this is client");
                while (true) {  
                    String send = scanner.nextLine();  
                    System.out.println("�ͻ��ˣ�" + send);  
                    // �Ѵӿ���̨�õ�����Ϣ���͸�������  
                    out.writeUTF("�ͻ��ˣ�" + send);  
                    // ��ȡ���Է���������Ϣ  
                    String accpet = in.readUTF();  
                    System.out.println(accpet);  
                }  
  
            } finally {  
                socket.close();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	public void chat2() {
		try {
            // ���ӵ�������  
            socket = new Socket(host, port);  
//			System.out.println("Hello world, this is Client2.");
			new Thread(sender).start();
			new Thread(receiver).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stop(){
		try {
			if(socket != null){
				socket.close();
				socket = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
//					System.out.println("me��" + "world");
					// �ѷ������˵����뷢���ͻ���
					out.writeUTF( "world");
//				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	
    public static void main(String[] args) {  
        new Client().chat2();  
    }  
}
