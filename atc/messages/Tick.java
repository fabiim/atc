package atc.messages;

import java.io.Serializable;

public class Tick extends Message implements Serializable{
	
	public Tick(){
		setOpcode(MessageOpCodes.TICK); 
	}

	public Tick(Tick tick){
		setOpcode(MessageOpCodes.TICK); 
	}
	
	public Tick clone() {
		return new Tick(this);
	}

	@Override
	public String toString() {
		return "Tick";
	}

	@Override
	public boolean equals(Object m){
		if (!super.equals(m) ) return false; 
		if (m == this) return true; 
		if (m == null) return false;
		if (m.getClass() != Tick.class){
			return false;
		}
		return true; 
	}	
}

	
