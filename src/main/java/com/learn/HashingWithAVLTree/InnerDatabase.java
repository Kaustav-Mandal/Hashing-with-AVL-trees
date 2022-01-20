package com.learn.HashingWithAVLTree;

import org.apache.log4j.Logger;

public class InnerDatabase 
{
	final static Logger logger = Logger.getLogger(InnerDatabase.class);
	private AVLTree [] avltrees = new AVLTree[9];
	
	public InnerDatabase()
	{
		avltrees = new AVLTree[9];
		for(int i = 0; i<9; i++)
		{
			AVLTree tree = new AVLTree();
			avltrees[i] = tree;
		}
	}

	public AVLTree[] getAvltrees() {
		return avltrees;
	}

	public void setAvltrees(AVLTree[] avltrees) {
		this.avltrees = avltrees;
	}
	
	public void displayEachTreeWithTreeNumber()
	{
		for(int i =0; i<9; i++)
		{
			logger.debug("AVL Tree # "+i+"-------->");
			avltrees[i].inOrder(avltrees[i].root);
			logger.debug("\n");
		}
	}
	
	public void displayEachTree()
	{
		for(int i =0; i<9; i++)
		{
			avltrees[i].inOrder(avltrees[i].root);
			logger.debug("\n");
		}
	}
}
