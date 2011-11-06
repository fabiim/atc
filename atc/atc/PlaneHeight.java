package atc.atc;

import java.io.Serializable;

public enum PlaneHeight implements Serializable{
	H1K(1000), H2K(2000), H3K(3000), H4K(4000), H5K(5000), H6K(6000), H7K(7000), H8K(8000), H9K(9000); 
	
	private int height; 
	
	private  PlaneHeight(int h) { height = h; }
	
	public int getHeight() { return height ;}
	
	
}
