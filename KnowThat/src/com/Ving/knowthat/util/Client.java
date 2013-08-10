package com.Ving.knowthat.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String host = "localhost";// 默认连接到本机  
    private int port = 8189;// 默认连接到端口8189  
    private Socket socket ;
    public Client() {  
  
    }  
  
    // 连接到指定的主机和端口  
    public Client(String host, int port) {  
        this.host = host;  
        this.port = port;  
    }  
  
    public void chat() {  
        try {  
            // 连接到服务器  
            socket = new Socket(host, port);  
  
            try {  
                // 读取服务器端传过来信息的DataInputStream  
                DataInputStream in = new DataInputStream(socket  
                        .getInputStream());  
                // 向服务器端发送信息的DataOutputStream  
                DataOutputStream out = new DataOutputStream(socket  
                        .getOutputStream());  
  
                // 装饰标准输入流，用于从控制台输入  
                Scanner scanner = new Scanner(System.in);  
                System.out.println("Hello world, this is client");
                while (true) {  
                    String send = scanner.nextLine();  
                    System.out.println("客户端：" + send);  
                    // 把从控制台得到的信息传送给服务器  
                    out.writeUTF("客户端：" + send);  
                    // 读取来自服务器的信息  
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
            // 连接到服务器  
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

			// 向客户端发送信息的DataOutputStream
			try {
				DataOutputStream out = new DataOutputStream(
						socket.getOutputStream());
//				while(true){
					
//					String send = scanner.nextLine();
//					System.out.println("me：" + "world");
					// 把服务器端的输入发给客户端
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
