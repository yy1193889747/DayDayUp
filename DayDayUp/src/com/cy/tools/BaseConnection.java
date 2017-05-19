package com.cy.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaseConnection {

    public static Connection getConnection() {
	Connection conn = null;
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/aaa?characterEncoding=UTF-8", "root", "0.0001");

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return conn;
    }

    public static void closeRes(ResultSet rs, PreparedStatement ps, Connection conn) {
	try {
	    if (rs != null) {
		rs.close();
	    }
	    if (ps != null) {
		ps.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void closeRes(PreparedStatement ps, Connection conn) {
	try {
	    if (ps != null) {
		ps.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	Connection conn = BaseConnection.getConnection();
	System.out.println(conn);
    }

}
