package atc.messages;


import java.io.Serializable;

import atc.atc.*;

<<<<<<< HEAD
<<<<<<< HEAD
public class Turn extends GameControlCommand implements Serializable{
	private PlaneDirection direction;
=======
public class Turn extends GameControl implements Serializable{
	private int direction;
>>>>>>> sadone
=======
public class Turn extends GameControl implements Serializable{
	private int direction;
=======
public class Turn extends GameControlCommand implements Serializable{
	private PlaneDirection direction;
>>>>>>> 56735061e250d78a0dcc88ffef162799e3414eee
>>>>>>> jkj

	public Turn(){
		setOpcode(MessageOpCodes.TURN); 
	}
	
	public Turn(int direction) {
		setOpcode(MessageOpCodes.TURN); 
		this.direction = direction;
	}

	public Turn(Turn turn) {
		setOpcode(MessageOpCodes.TURN); 
		this.direction = turn.getDirection(); 
	} 
	
	public Turn clone() { return new Turn(this); }


	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + direction;
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
		if (direction!=other.getDirection()) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Turn [Plane="+this.getPlaneId()+" direction=" + direction + "]";
	}

}
