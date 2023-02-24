package com.kh.test;

import java.util.Arrays;

public class Write {
	
	public void method1() {
		
		String[] sArr = {"빨강", "노랑", "파랑"};
		
		for(int i=0; i<sArr.length; i++) {
			System.out.println(sArr[i]);
		}
		
	}
	
	public void method2() {
		
		int[] arr = {1,2,3};
		int[] copy = Arrays.copyOf(arr, 6);
		//arr[1] = 1;
		
		for(int i=0; i<arr.length; i++) {
			System.out.println(arr[i]);
		}
		
		for(int i=0; i<copy.length; i++) {
			System.out.println(copy[i]);
		}
	}
	
	public void method3() {
		String[] sArr = new String[3];

		System.out.println(sArr);

		System.out.println(sArr[0]);
	}

}
