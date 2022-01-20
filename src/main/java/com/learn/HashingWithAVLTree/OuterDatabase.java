package com.learn.HashingWithAVLTree;

public class OuterDatabase {

	private InnerDatabase [] innerdatabases = new InnerDatabase [8];
	
	public OuterDatabase()
	{
		for(int i =0; i<8; i++)
		{
			InnerDatabase innerdatabase = new InnerDatabase();
			innerdatabases[i] = innerdatabase;
		}
	}

	public InnerDatabase[] getInnerdatabases() {
		return innerdatabases;
	}

	public void setInnerdatabases(InnerDatabase[] innerdatabases) {
		this.innerdatabases = innerdatabases;
	}
	
}
