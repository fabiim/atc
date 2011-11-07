package atc.messages;


import java.io.Serializable;

import atc.atc.*;

public class Turn extends GameControlCommand implements Serializable{
	private PlaneDirection direction;

	public Turn(){
		setOpcode(MessageOpCodes.TURN); 
	}
	
	public Turn(PlaneDirection direction) {
		setOpcode(MessageOpCodes.TURN); 
		this.direction = direction;
	}

	public Turn(Turn turn) {
		setOpcode(MessageOpCodes.TURN); 
		this.direction = turn.getDirection(); 
	} 
	
	public Turn clone() { return new Turn(this); }


	public PlaneDirection getDirection() {
		return direction;
	}

	public void setDirection(PlaneDirection direction) {
		this.direction = direction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turn other = (Turn) obj;
		if (direction.getKey() != other.getDirection().getKey())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Turn [direction=" + direction + "]";
	}

}
