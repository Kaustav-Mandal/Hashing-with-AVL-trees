package com.learn.HashingWithAVLTree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class IntelligentSIDC 
{
	final static Logger logger = Logger.getLogger(IntelligentSIDC.class);
	
	private static OuterDatabase IntelligentSidc = new OuterDatabase();
	private static InnerDatabase [] innerdatabases = IntelligentSidc.getInnerdatabases();
	static int [] keys;
	
	/*
	 * Returns the Key in String format after removing leading zeros.
	 * @param str is the key passed as an argument. 
	 */
	public static String removeLeadingZeroes(String str) 
	{
		 if(str.charAt(0) == '0')
		 {
	     String strPattern = "^0+(?!$)";
	     str = str.replaceAll(strPattern, "");
		 }
	     return str;
	}
	/*
	 * Returns the correct AVL tree into which the @param studentId will be inserted. 
	 */
	public static AVLTree findAVLTreeForKey(String studentId)
	{
		studentId = removeLeadingZeroes(studentId);
		int lengthOfId = studentId.length();
		int firstDigitOfId = Character.getNumericValue(studentId.charAt(0));
		
		AVLTree [] avltree = innerdatabases[lengthOfId-1].getAvltrees();
		AVLTree avl = avltree[firstDigitOfId-1];
		
		return avl;
	}
	
	/*
	 * Adds the student into the correct AVL tree. 
	 */
	public static void add(String studentId, String info)
	{
		AVLTree avl = findAVLTreeForKey(studentId);
		studentId = removeLeadingZeroes(studentId);
		
		Student student = new Student(Integer.parseInt(studentId), info);
		avl.root = avl.insert(avl.root, student);
	}
	
	
	/*
	 * Bulk upload of student data to the data structure
	 */
	public static void insertAllFromFile(String fileName) throws FileNotFoundException
	{
		Scanner sc = null;
		sc = new Scanner(new FileInputStream(fileName));
		
		String str = null;
		while(sc.hasNextLine())
		{
			str = sc.nextLine();
			String [] arr = str.split(" ");
			AVLTree avl = findAVLTreeForKey(arr[0]);
			String id  = removeLeadingZeroes(arr[0]);
			Student student = new Student(Integer.parseInt(id), arr[1]);
			avl.root = avl.insert(avl.root, student);
		}
		
		sc.close();
	}
	
	/*
	 * Removes the student with id equals to @param studentId from the data structure. 
	 */
	public static void remove(String studentId)
	{
		AVLTree avl = findAVLTreeForKey(studentId);
		studentId = removeLeadingZeroes(studentId);
		int id = Integer.parseInt(studentId);
		Node node = avl.getNode(avl.root, id);
		if(node != null)
		node.student.setDetails(null);
	}
	
	/*
	 * Retrieve the inforation of the student given the id equals to @param studentId
	 */
	public static String getvalues(String studentId)
	{
		AVLTree avl = findAVLTreeForKey(studentId);
		studentId = removeLeadingZeroes(studentId);
		int id = Integer.parseInt(studentId);
		Node node = avl.getNode(avl.root, id);
		String details = "";
		if(node.student.getDetails() != null)
		details = node.student.getDetails();
		return details;
	}
	
	/*
	 * Generates a unique new key.
	 * @param lengthOfId is the length of the generated key. 
	 */
	public static int generate(int lengthOfId)
	{
		AVLTree [] avltree = innerdatabases[lengthOfId-1].getAvltrees();
		int id = 0;
		Node highest = null;
		for(int i =0; i<9; i++)
		{
			AVLTree avl = avltree[i];
			highest = avl.highestNode;
			if(highest != null)
			{
				id = highest.student.getId() + 1;
				break;
			}
		}
		
		return id;
	}
	
	/*
	 * Returns the successor of a given key equals to @param studentId. 
	 */
	public static int nextKey(String studentId)
	{
		int nextId = 0;
		AVLTree avl = findAVLTreeForKey(studentId);
		studentId = removeLeadingZeroes(studentId);
		AVLTree [] avltree = innerdatabases[studentId.length()-1].getAvltrees();
		int firstDigitOfId = Character.getNumericValue(studentId.charAt(0));
		avl.checkandPassForInorderInsertion(avl.root);
		
//		System.out.println("Size of tree"+" "+avl.count);
//		for(int i  =0; i<avl.count; i++)
//    	{
//    		System.out.print(avl.nodes[i].student.getId() +" "+avl.nodes[i].student.getDetails()+" ");
//    	}
//		if(avl.highestNode != null)
//			System.out.println("highest node is :"+" "+avl.highestNode.student.getId());
		
		
		//Finds the position of the given key in the AVL tree where it should be. 
		int position = BinarySearch.bs(0, (avl.count-1), avl.nodes, Integer.parseInt(studentId));
		boolean flag = false;
		if(position != -1)
		{
			// If the key is found then the successor key will be the next one iff the key was not the last node of that AVL tree.
			if((position+1) < avl.count)
			{
				nextId = avl.nodes[position + 1].student.getId();
				flag = true;
			}
			// If the given key was the last node of the AVL tree then the successor will be the lowest node of the next AVL tree which 
			//is not empty. 
			else
			{
				for(int i = firstDigitOfId; i<9; i++)
				{
					AVLTree nextAvl = avltree[i];
					if(nextAvl.lowestNode != null)
					{
						nextId = nextAvl.lowestNode.student.getId();
						flag = true;
						break;
					}
				}
			}
			
			// If all the AVL trees corresponding to the length of given key are empty then the search will be continued
			//to the next AVL trees in the outer data structure. 
			if(flag == false)
			{
				for(int i =studentId.length(); i<8; i++)
				{
					AVLTree [] otherAvltrees = innerdatabases[i].getAvltrees();
					for(AVLTree Avl : otherAvltrees)
					{
						if(Avl.lowestNode != null)
						{
							nextId = Avl.lowestNode.student.getId();
							flag = true;
							break;
						}
					}
					if(flag)
						break;
				}
			}
		}
		return nextId;
	}
	
	/*
	 * Finds the previous key of a given key value equals to @param studentId. 
	 * Works same way the method nextKey(String studentId) works but search continues in backward direction. 
	 */
	public static int prevKey(String studentId)
	{
		int nextId = 0;
		AVLTree avl = findAVLTreeForKey(studentId);
		studentId = removeLeadingZeroes(studentId);
		AVLTree [] avltree = innerdatabases[studentId.length()-1].getAvltrees();
		int firstDigitOfId = Character.getNumericValue(studentId.charAt(0));
		int begin = firstDigitOfId - 2;
		avl.checkandPassForInorderInsertion(avl.root);
		int position = BinarySearch.bs(0, (avl.count-1), avl.nodes, Integer.parseInt(studentId));
		boolean flag = false;
		if(position != -1)
		{
			if((position-1) >= 0)
			{
				nextId = avl.nodes[position - 1].student.getId();
				flag = true;
			}
			else
			{
				for(int i = begin; i>= 0; i--)
				{
					AVLTree nextAvl = avltree[i];
					if(nextAvl.highestNode != null)
					{
						nextId = nextAvl.highestNode.student.getId();
						flag = true;
						break;
					}
				}
			}
			
			if(flag == false)
			{
				for(int i =(studentId.length()-2); i>=0; i--)
				{
					AVLTree [] otherAvltrees = innerdatabases[i].getAvltrees();
					for(int k = (otherAvltrees.length-1); k>=0; k--)
					{
						if(otherAvltrees[k].highestNode != null)
						{
							nextId = otherAvltrees[k].highestNode.student.getId();
							flag = true;
							break;
						}
					}
					if(flag)
						break;
				}
			}
		}
		return nextId;
	}
	/*
	 * Returns an array of all the keys in the system. 
	 */
	public static int[] allKeys()
	{
		int totalNumOfKeys = 0;
		for(int i =0; i<8; i++)
		{
			AVLTree [] avltrees = innerdatabases[i].getAvltrees();
			for(int k =0; k<9; k++)
			{
				AVLTree avl = avltrees[k];
				totalNumOfKeys = totalNumOfKeys+avl.count;
			}
		}
		keys = new int[totalNumOfKeys];
		int counter = 0;
		for(int i =0; i<8; i++)
		{
			AVLTree [] avltrees = innerdatabases[i].getAvltrees();
			for(int k =0; k<9; k++)
			{
				int size = avltrees[k].count;
				if(size > 0)
				{
					int a = 0;
					AVLTree avl = avltrees[k];
					avl.checkandPassForInorderInsertion(avl.root);
					while(a < size)
					{
						keys[counter] = avl.nodes[a].student.getId();
						a++;
						counter++;
					}
				}
			}
			
		}
		return keys;
	}
	
	/*
	 * Returns all the keys which fall between @param key1 and @param key2. 
	 */
	public static int [] rangeKey(int key1, int key2)
	{
		int [] allKeys = new int [allKeys().length];
		allKeys = allKeys();
		int position1 = BinarySearch.bsForInt(0, allKeys.length-1, allKeys, key1);
		int position2 = BinarySearch.bsForInt(0, allKeys.length-1, allKeys, key2);
		int [] rangeKeys = new int [position2-position1];
		int count = 0;
		for(int i =position1+1; i< position2; i++)
		{
			rangeKeys[count] = allKeys[i] ;
			count++;
		}
		for(int key: rangeKeys)
		{
			if(key != 0)
				System.out.print(key+" ");
		}
		System.out.println();
		return rangeKeys;
 	}		
	
	/*
	 * Print all the keys in the system. 
	 */
	public static void printAllKeys()
	{
		for(int i =0; i<8; i++)
		{
			logger.debug("-------------------------------------------------------");
			logger.debug("Outer Box "+" "+i);
			innerdatabases[i].displayEachTreeWithTreeNumber();
		}
	}
	public static void main(String[] args) throws FileNotFoundException 
	{
//		insertAllFromFile("op1Record.txt");
//		printAllKeys();
//		System.out.println(nextKey("9476"));
		while(true)
		{
			System.out.println();
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
					System.out.println("All records have been uploaded to the system");
//					printAllKeys();
				}
				
				else if(choice == 2)
				{
					System.out.println("Please enter Id : ");
					String id = br.readLine();
					System.out.println("Please enter stdent details : ");
					String details = br.readLine();
					
					IntelligentSIDC.add(id, details);
					System.out.println("New record has been added");
				}
				
				else if(choice == 3)
				{
					System.out.println("Enter the length of key you want : ");
					int length = Integer.parseInt(br.readLine());
					String newKey = String.valueOf(IntelligentSIDC.generate(length));
					
					int diff = 8 - newKey.length();
					String prefix = "";
					if(diff>0)
					{
						for(int k=0; k<diff; k++)
						{
							prefix = prefix+"0";
						}
					}
					
					System.out.println("Key generated: "+prefix+newKey);
				}
				
				else if(choice == 4)
				{
					IntelligentSIDC.allKeys();
					System.out.println("All the keys in sorted sequence are : ");
					for(int key: IntelligentSIDC.keys)
					{
						if(key != 0)
//							System.out.print(key+" ");
							logger.debug(key);
					}
					System.out.println();
					System.out.println("Please check the log");
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
					System.out.println("The next key is: "+IntelligentSIDC.nextKey(id));
				}
				
				else if(choice == 7)
				{
					System.out.println("Enter the Key : ");
					String id = br.readLine();
					System.out.println("The previous key is: "+IntelligentSIDC.prevKey(id));
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
					System.out.println("Student details: "+IntelligentSIDC.getvalues(id));
				}
				else if(choice == 0)
					System.exit(0);
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}			
	
	}
}
