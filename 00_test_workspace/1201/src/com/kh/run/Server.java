/*
package com.kh.run;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public void client() {
		int port = 3000;
		String serverIP = null; // null 값으로 초기화
		try { 
			serverIP = "192.168.20.34"; // InetAddress.getLocalHost()는 클라이언트인 내 PC의 이름과 아이피를 구하는 메소드이므로 잘못됨. 서버의 아이피로 작성해줘야 한다.
			
			Socket socket = new Socket(serverIP, port);
			
			// 이하 코드는 중간 생략...

	
	public void server() {
		int port = 3000;
		
		ServerSocket server = null; // null 값으로 초기화
		try {
			server = new ServerSocket(port);
			
			while(server != null) { // 무한반복이 아닌 server 소켓에 값이 있을때만 동작하도록 조건 설정
				 Socket client = server.accept();
			}
		
			// 이하 코드는 중간 생략...
	
	/*
	// 클라이언트 [원인]
	1) String serverIP;
	2) serverIP = InetAddress.getLocalHost().getHostAddress();
	*/
	
	/*
	// 클라이언트 [조치내용]
	1) String serverIP = null; => null 값으로 초기화
	2) serverIP = "192.168.20.34"; => InetAddress.getLocalHost()는 클라이언트인 내 PC의 이름과 아이피를 구하는 메소드이므로 잘못됨. 서버의 아이피로 작성해줘야 한다.
	*/
	
	/*
	// 서버 [원인]
	1) ServerSocket server;
	2) while(true)
	*/
	
	/*
	// 클라이언트 [조치내용]
	1) ServerSocket server = null; => null 값으로 초기화
	2) while(server != null) => server 소켓에 값이 있을 때만 동작하도록 설정한다.
	*/
	
	
	/*
	public void output() {
		
		FileWriter fw = null;
		try {
			
			fw = new FileWriter("test.txt", true);
			// true가 있어야만 기존 파일에 데이터를 이어 쓸 수 있다.
			fw.write(97);
			fw.write(65);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close(); // 스트림을 다 쓰고 나면 닫아줘야 한다. 그래야 오류가 발생하지 않는다.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	[원인]
	1) fw = new FileWriter("test.txt");
	2) 아래의 스트림을 반납하는 구문이 빠졌다.
	 
	} finally {
		 try {
		  	 fw.close();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}
	*/
	
	/*
	[조치내용]
	1) fw = new FileWriter("test.txt", true); => 파일명 뒤에 true가 명시되어 있어야만 기존 파일에 데이터를 이어 쓸 수 있다.
	2) 아래의 구문 추가 => 스트림을 다 쓰고 나면 close() 메소드를 통해 finally구문에서 try-catch로 처리하여 스트림을 닫아줘야 한다. 그래야 오류가 발생하지 않는다.
	
	} finally {
		 try {
		  	 fw.close();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}
	*/

//}

