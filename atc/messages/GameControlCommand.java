package atc.messages;

import java.io.Serializable;

public abstract class GameControlCommand extends Message implements Serializable{
	private char planeId;

	public char getPlaneId() {
		return planeId;
	}

	public void setPlaneId(char planeId) {
		this.planeId = planeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + planeId;
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
		GameControlCommand other = (GameControlCommand) obj;
		if (planeId != other.planeId)
			return false;
		return true;
	}
	
}
