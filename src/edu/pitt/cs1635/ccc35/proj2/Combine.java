package edu.pitt.cs1635.ccc35.proj2;

public class Combine implements java.io.Serializable{
	public boolean combinePoint;
	public boolean sigEnd;
	public float x;
	public float y;
	
	
	public Combine(float i, float j, boolean b, boolean c) {
		 x = i;
		 y = j;
		 combinePoint= b;
		 sigEnd = c;
	}
	public Combine (float i, float j) {
		this(i,j,false, false);
	}
	
	
	
	public void set(float i, float j) {
		 x = i;
		 y = j;
	}
	
	public void clear() {
		set(0,0);
	}
}
