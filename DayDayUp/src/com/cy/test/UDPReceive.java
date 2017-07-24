package com.cy.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @Title: UDP接收方
 * @Description:
 * @Author: cy.guo
 * @Date: 2017年7月24日 上午10:37:59
 */
public class UDPReceive {

    public static void main(String[] args) throws Exception {
	DatagramSocket socket = new DatagramSocket(9999);
	DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

	while (true) { // 相当于监听事件
	    socket.receive(packet); // 接货,接收数据
	    byte[] arr = packet.getData(); // 获取数据
	    int len = packet.getLength(); // 获取有效的字节个数
	    String ip = packet.getAddress().getHostAddress();// 获取ip地址
	    int port = packet.getPort(); // 获取端口号
	    System.out.println(ip + ":" + port + ":" + new String(arr, 0, len)); // 为了看起来明显点
										 // 加上ip地址和端口号
	}
    }
}
