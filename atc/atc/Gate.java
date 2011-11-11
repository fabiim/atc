package atc.atc;

import java.io.Serializable;

public class Gate implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xCoord;
	private int yCoord;
	private Character symbol;
	
	public Gate(char s, int x,int y){
		xCoord = x;
		yCoord = y;
		symbol = s;
		
	}
	
	public char getSymbol(){
		return symbol;
	}
	public void setSymbol(Character symbol) {
		this.symbol = symbol;
	}
	public int getxCoord() {
		return xCoord;
	}
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	public int getyCoord() {
		return yCoord;
	}
	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}
	
	@Override
	public String toString() {
		return "Gate [symbol="+symbol+", xCoord=" + xCoord + ", yCoord=" + yCoord + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xCoord;
		result = prime * result + yCoord;
		result = prime * result + symbol;
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gate other = (Gate) obj;
		if (xCoord != other.getxCoord())
			return false;
		if (yCoord != other.getyCoord())
			return false;
		if (symbol != other.getSymbol())
			return false;
		return true;
	}
	
	public Gate clone(){
		return(new Gate(symbol,xCoord,yCoord));
	}
}
