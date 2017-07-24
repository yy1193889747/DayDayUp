package com.cy.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPSend {
    public static void main(String[] args) throws Exception {
	Scanner sc = new Scanner(System.in); // 创建键盘录入对象
	System.out.println("请输入要发送的内容：");
	while (true) {
	    String post = sc.nextLine(); // 获取键盘录入的字符串
	    if ("quit".equals(post)) {
		break;
	    }
	    DatagramSocket socket = new DatagramSocket();
	    DatagramPacket packet = new DatagramPacket(post.getBytes(), post.getBytes().length, InetAddress.getByName("127.0.0.1"), 9999);
	    socket.send(packet);
	    socket.close();
	}
    }
}
