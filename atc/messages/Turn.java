package atc.messages;


import java.io.Serializable;

public class Turn extends GameControlCommand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -606418388536569309L;
	private int direction;

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
