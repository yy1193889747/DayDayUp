package com.cy.test;

import java.time.Instant;

/**
 * @Title: SortingTest
 * @Description: 排序算法学习
 * @Author: cy.guo
 * @Date: 2017年5月19日 下午2:51:55
 */
public class SortingTest {

    public static void main(String[] args) {
	int a[] = { 1, 54, 6, 3, 33, 31, 233, 332, 53, 313, 334, 362, 313 };
	long start = System.currentTimeMillis();
	long sss = System.nanoTime();
	Instant now = Instant.now().plusNanos(3);
	Instant later = now.plusSeconds(3);
	Instant earlier = now.plusNanos(3);

	System.out.println(atoi("+"));
	long time = System.currentTimeMillis() - start;
	long ssss = System.nanoTime() - sss;
	System.out.println(time);
	System.out.println(ssss);
	System.out.println(now);
	System.out.println(later);
	System.out.println(earlier);
    }

    // 简单选择排序
    public static void selectSort(int a[]) {
	int position = 0;
	for (int i = 0; i < a.length; i++) {
	    position = i;
	    int temp = a[i];
	    for (int j = i + 1; j < a.length; j++) {
		if (a[j] < temp) {
		    temp = a[j];
		    position = j;
		}
	    }
	    a[position] = a[i];
	    a[i] = temp;
	}

	for (int i = 0; i < a.length; i++) {
	    System.out.println(a[i]);
	}
    }

    // 冒泡排序
    public static void bubbleSort(int a[]) {
	int temp = 0;

	for (int i = 0; i < a.length - 1; i++) {
	    for (int j = 0; j < a.length - 1 - i; j++) {
		System.out.println(a[j]);
		System.out.println(a[j + 1]);
		if (a[j] > a[j + 1]) {
		    temp = a[j];
		    a[j] = a[j + 1];
		    a[j + 1] = temp;
		}
	    }
	}
	for (int i = 0; i < a.length; i++) {
	    System.out.print(a[i] + "-");
	}
    }

    // 我自己的找最大值
    public static int maxProfit(int[] A) {

	int tmp = 0;
	for (int i = 0; i < A.length - 1; i++) {

	    if (tmp <= A[i + 1]) {

		tmp = A[i + 1];
	    }

	}
	return tmp;
    }

    // 转换字符串到整数
    public static int atoi(String str) {
	// write your code here
	String s = "";
	try {
	    str = str.trim();
	    System.out.println(str);

	    for (int i = 0; i < str.length(); i++) {

		if (i == 0) {
		    if (str.substring(i, i + 1).matches("[0-9]|[-|+]")) {
			s = s.concat(str.substring(i, i + 1));
		    } else {
			return 0;
		    }
		} else {
		    if (str.substring(i, i + 1).matches("[0-9]")) {
			s = s.concat(str.substring(i, i + 1));
		    } else {
			break;
		    }
		}
	    }
	    if ((s.startsWith("-") || s.startsWith("+")) && s.length() <= 1) {
		return 0;
	    }
	    long a = Long.valueOf(s);
	    if (a > 2147483647) {
		return 2147483647;
	    }
	    if (a < -2147483648) {
		return -2147483648;
	    }
	    return Integer.valueOf(s);

	} catch (Exception e) {
	    if (s.startsWith("-")) {
		return -2147483648;
	    }
	    return 2147483647;
	}
    }
}
