package com.learn.HashingWithAVLTree;

import java.util.Arrays;

public class BinarySearch {

	public static int bs(int s, int e, Node [] arr, int num)
	{
		int index = -1;
		
		if(s > e)
			return -1;
		
		int mid = (s+e)/2;
		
		if(arr[mid].student.getId() == num)
			return mid;
		
		else
		{
			if(num < arr[mid].student.getId())
				index = bs(s, mid, arr, num);
				
			else if(num > arr[mid].student.getId())
				index = bs((mid+1), e, arr, num);
		}
		
		return index;
	}
	
	public static int bsForInt(int s, int e, int [] arr, int num)
	{
		int index = -1;
		
		if(s > e)
			return -1;
		
		int mid = (s+e)/2;
		
		if(arr[mid] == num)
			return mid;
		
		else
		{
			if(num < arr[mid])
				index = bsForInt(s, mid, arr, num);
				
			else if(num > arr[mid])
				index = bsForInt((mid+1), e, arr, num);
		}
		
		return index;
	}
	public static void main(String[] args) {
		int [] arr = new int [10];
		for(int i =0; i<10; i++)
		{
			arr[i] = i+1;
		}
		System.out.println(Arrays.toString(arr));
		System.out.println();
	}
}
