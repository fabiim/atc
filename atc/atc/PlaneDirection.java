package atc.atc;

import java.io.Serializable;

public enum PlaneDirection implements Serializable{
	N(8), NE(9), E(6), SE(3), S(2), SW(1),W(4),NW(7);  
	
	private int key; 
	
	private PlaneDirection(int c){ key = c; } 
	public int getKey() { return key; } 
	
}
