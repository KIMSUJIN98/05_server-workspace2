package com.kh.test;

import java.util.Scanner;

public class Test {

	public void quiz2() {
		
		for(int i =1; i<=20; i++) {
			switch(i % 5) {
			case 0:
				System.out.println(i + ": 5의 배수입니다.");
				break;
			default:
				System.out.println(i + ": 5의 배수가 아닙니다.");
				break;
			}
		}
	}
		
	public void quiz3() {
		
		int[] arr = new int[4];
		
		for(int i = 0; i < arr.length; i++) {
			arr[i] += (10 + i);
		}
		
		for(int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}

	}
}

