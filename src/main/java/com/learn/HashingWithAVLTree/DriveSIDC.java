package com.learn.HashingWithAVLTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DriveSIDC {

	public static void main(String[] args) {
		while(true)
		{
			System.out.println("Please choose option from the menu. Press 0 to quit !! ");
			System.out.println("-----------------------------------------------------------");
			System.out.println("Press 1 for insert bulk data to the system from file.");
			System.out.println("Press 2 to insert a new record to the system.");
			System.out.println("Press 3 to generate a new key.");
			System.out.println("Press 4 to print all the keys in sorted sequence.");
			System.out.println("Press 5 to remove an entry corresponding to the key");
			System.out.println("Press 6 to find the successor key of a given key.");
			System.out.println("Press 7 to find the predecessor of a given key.");
			System.out.println("Press 8 to find the keys within a range of two keys.");
			System.out.println("Press 9 to get details of a student.");
			System.out.println("------------------------------------------------------------");
			
			InputStreamReader r = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(r);
			
			try {
				int choice = Integer.parseInt(br.readLine());
				if(choice == 1)
				{
					System.out.println("Please enter the file name : ");
					String filename = br.readLine();
					IntelligentSIDC.insertAllFromFile(filename);
				}
				
				else if(choice == 2)
				{
					System.out.println("Please enter Id : ");
					String id = br.readLine();
					System.out.println("Please enter stdent details : ");
					String details = br.readLine();
					
					IntelligentSIDC.add(id, details);
				}
				
				else if(choice == 3)
				{
					System.out.println("Enter the length of key you want : ");
					int length = Integer.parseInt(br.readLine());
					
					System.out.println(IntelligentSIDC.generate(length));
				}
				
				else if(choice == 4)
				{
					IntelligentSIDC.allKeys();
					System.out.println("All the keys in sorted sequence are : ");
					for(int key: IntelligentSIDC.keys)
					{
						if(key != 0)
							System.out.print(key+" ");
					}
					System.out.println();
				}
				
				else if(choice == 5)
				{
					System.out.println("Enter the Id : ");
					String id = br.readLine();
					IntelligentSIDC.remove(id);
				}
				
				else if(choice == 6)
				{
					System.out.println("Enter the Key : ");
					String id = br.readLine();
					IntelligentSIDC.nextKey(id);
				}
				
				else if(choice == 7)
				{
					System.out.println("Enter the Key : ");
					String id = br.readLine();
					IntelligentSIDC.prevKey(id);
				}
				
				else if (choice == 8)
				{
					System.out.println("The first key should be smaller than the second key.");
					System.out.println("Enter the first Key : ");
					int key1 = Integer.parseInt(br.readLine());
					System.out.println("Enter the second Key : ");
					int key2 = Integer.parseInt(br.readLine());
					
					if(key1 < key2)
					IntelligentSIDC.rangeKey(key1, key2);
				}
				
				else if(choice == 9)
				{
					System.out.println("Enter the Id : ");
					String id = br.readLine();
					System.out.println(IntelligentSIDC.getvalues(id));
				}
				else if(choice == 0)
					System.exit(0);
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
