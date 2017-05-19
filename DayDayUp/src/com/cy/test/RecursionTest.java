package com.cy.test;

/**
 * @Title: RecursionTest
 * @Description: 递归算法学习练习
 * @Author: cy.guo
 * @Date: 2017年5月19日 上午8:10:49
 */
public class RecursionTest {

    public static void main(String[] args) {
	System.out.println(ride1(3));
	// fun(9);
    }

    // 阶乘递归
    public static long ride(int i) {
	if (i == 1) {
	    System.out.print("1=");
	    return 1;
	} else {
	    System.out.print(i + "*");
	    long a = i * ride(i - 1);
	    return a;
	}

    }

    // 阶乘 普通算法
    public static long ride1(int i) {
	long a = 1;
	for (int k = i; k >= 1; k--) {
	    a = a * k;
	}
	return a;
    }

    // 99乘法表 递归
    public static void fun(int i) {
	if (i == 0) {
	    System.out.println("99乘法表 ");
	} else {
	    fun(i - 1);
	    for (int j = 1; j <= i; j++) {
		System.out.print(j + "*" + i + "=" + j * i + " ");
	    }
	    System.out.println();
	}
    }

    // 1+n 递归
    public static int fun1(int i) {
	if (i == 1) {
	    System.out.print("1=");
	    return 1;
	} else {
	    System.out.print(i + "+");
	    int a = i + fun1(i - 1);
	    return a;
	}

    }
}
