package com.cy.test;

/**
 * @Title: SortingTest
 * @Description: 排序算法学习
 * @Author: cy.guo
 * @Date: 2017年5月19日 下午2:51:55
 */
public class SortingTest {

    public static void main(String[] args) {
	int a[] = { 1, 54, 6, 3 };
	selectSort(a);
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

}
