package com.learn.HashingWithAVLTree;

import org.apache.log4j.Logger;

class Node 
{ 
    Student student;
    int height;
    Node left, right; 
  
    Node(Student s) 
    { 
    	student = s; 
        height = 1; 
    }
    
}
   
public class AVLTree 
{
	 Node root; 
	 Node highestNode;
	 Node lowestNode;
	 int count = 0;
	 Node [] nodes;
	 
	public void setNodes(Node[] nodes) 
	{
		this.nodes = nodes;
	}


	static int counter = 0;
	 
	 final static Logger logger = Logger.getLogger(AVLTree.class);
	    int height(Node N) 
	    { 
	        if (N == null) 
	            return 0; 
	  
	        return N.height; 
	    } 
		public void setHighestNode(Node highestNode) 
		{
			this.highestNode = highestNode;
		}
		public void setLowestNode(Node lowestNode) 
		{
			this.lowestNode = lowestNode;
		}
	    int max(int a, int b) { 
	        return (a > b) ? a : b; 
	    } 
	  
	    Node rightRotate(Node y) 
	    { 
	        Node x = y.left; 
	        Node T2 = x.right; 
	  
	        x.right = y; 
	        y.left = T2; 
	  
	        y.height = max(height(y.left), height(y.right)) + 1; 
	        x.height = max(height(x.left), height(x.right)) + 1; 
	  
	        return x; 
	    } 
	  
	    Node leftRotate(Node x) 
	    { 
	        Node y = x.right; 
	        Node T2 = y.left; 
	  
	        y.left = x; 
	        x.right = T2; 
	  
	        x.height = max(height(x.left), height(x.right)) + 1; 
	        y.height = max(height(y.left), height(y.right)) + 1; 
	  
	        return y; 
	    } 
	  
	    int getBalance(Node N) 
	    { 
	        if (N == null) 
	            return 0; 
	  
	        return height(N.left) - height(N.right); 
	    } 
	  
	    Node insert(Node node, Student student) 
	    { 
	  
	        if (node == null) 
	        {
	        	Node newNode = new Node(student);
	        	if(highestNode == null)
	        	{
	        		highestNode = newNode;
	        		lowestNode = newNode;
	        	}
	        	
	        	if(student.getId() > highestNode.student.getId())
	        		highestNode = newNode;
	        	
	        	if(student.getId() < lowestNode.student.getId())
	        		lowestNode = newNode;
	        	
	        	count++;
	        	
	            return newNode; 
	        }
	  
	        if (student.getId() < node.student.getId()) 
	            node.left = insert(node.left, student); 
	        else if (student.getId() > node.student.getId()) 
	            node.right = insert(node.right, student); 
	        else // Duplicate keys not allowed 
	            return node; 
	  
	        /* 2. Update height of this ancestor node */
	        node.height = 1 + max(height(node.left), 
	                              height(node.right)); 
	  
	        /* 3. Get the balance factor of this ancestor 
	              node to check whether this node became 
	              unbalanced */
	        int balance = getBalance(node); 
	  
	        // If this node becomes unbalanced, then there are 4 cases. The first one is Left Left Case 
	        if (balance > 1 && student.getId() < node.left.student.getId()) 
	            return rightRotate(node); 
	  
	        // Right Right Case 
	        if (balance < -1 && student.getId() > node.right.student.getId()) 
	            return leftRotate(node); 
	  
	        // Left Right Case 
	        if (balance > 1 && student.getId() > node.left.student.getId()) { 
	            node.left = leftRotate(node.left); 
	            return rightRotate(node); 
	        } 
	  
	        // Right Left Case 
	        if (balance < -1 && student.getId() < node.right.student.getId()) { 
	            node.right = rightRotate(node.right); 
	            return leftRotate(node); 
	        } 
	  
	        /* return the (unchanged) node pointer */
	        return node; 
	    } 
	    
	   public void preOrder(Node node) { 
	        if (node != null) { 
	        	logger.debug(node.student.getId() + " ");
	            preOrder(node.left); 
	            preOrder(node.right); 
	        } 
	    } 
	    
	    public void inOrder(Node node) { 
	        if (node != null) { 
	        	inOrder(node.left);
	        	System.out.println(node.student.getId() + " ");
	        	logger.debug(node.student.getId() + " ");
	            inOrder(node.right); 
	        } 
	    }
	    
	    void inOrderInsertToArray(Node node) 
	    { 
	        if (node != null) 
	        { 
	        	inOrderInsertToArray(node.left); 
	        	nodes[counter] = node; 
		        counter++;
	            inOrderInsertToArray(node.right); 
	        }
	    } 
	    
	    public Node getNode(Node tree, int val) 
	    {
	    	if(tree == null)
	    		return null;
	    	
	    	if(tree.student.getId() == val)
	    		return tree;
	    	
	    	if(val < tree.student.getId())
	    		tree = getNode(tree.left, val);
	    	if(val > tree.student.getId())
	    		tree = getNode(tree.right, val);
	    	
			return tree;
	    }
	    
	    public Node [] checkandPassForInorderInsertion(Node tree)
	    {
	    	counter = 0;
	    	Node [] returnNodes = new Node [count];
	    	if(nodes == null)
	    	{
	    		nodes = new Node[count];
	    		inOrderInsertToArray(tree);
	    		returnNodes = nodes;
	    	}
	    	
	    	else if(nodes.length == count)
	    	{
	    		returnNodes = nodes;
	    	}
	    	
	    	else if(nodes.length < count)
	    	{
	    		nodes = new Node[count];
	    		inOrderInsertToArray(tree);
	    		returnNodes = nodes;
	    	}
			return returnNodes;
	    }
	    public static void main(String[] args) 
	    { 
	        AVLTree tree = new AVLTree(); 
	  
	        Student student1 = new Student(101, "Student1");
	        Student student2 = new Student(102, "Student2");
	        Student student3 = new Student(103, "Student3");
	        
	        tree.root = tree.insert(tree.root, student1); 
	        tree.root = tree.insert(tree.root, student2); 
	        tree.root = tree.insert(tree.root, student3); 
        	
        	System.out.println("Total number of nodes are "+" "+tree.count);
        	
        	tree.checkandPassForInorderInsertion(tree.root);
        	for(int i  =0; i<tree.count; i++)
        	{
        		System.out.print(tree.nodes[i].student.getId() +" "+tree.nodes[i].student.getDetails()+", ");
        	}
        	System.out.println("\n"+"------------------------------------------------------");
	    } 
	} 
