package com.learn.HashingWithAVLTree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Read {

	public static void readKeys(String filename)
	{
		String opFile = "op3Record.txt";
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			sc = new Scanner(new FileInputStream(filename));
			pw = new PrintWriter(new FileOutputStream(opFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String str;
		int count = 0;
		while(sc.hasNextLine())
		{
			count ++;
			str = sc.nextLine();
			str = str + " " + "Student"+count;
			
			pw.println(str);
		}
		
		sc.close();
		pw.close();
	}
	
	public static void main(String[] args) {
		readKeys("CSTA_test_file3.txt");
	}
}
