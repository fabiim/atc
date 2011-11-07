package atc.messages;

import java.io.Serializable;

<<<<<<< HEAD
import atc.atc.PlaneDirection;
import atc.atc.PlaneHeight;

public class NewPlane extends GameControlCommand implements Serializable {
=======
public class NewPlane extends GameControl implements Serializable {
>>>>>>> sadone
	private char entranceGateId; 
	private char exitGateID; 
	private int direction; 
	private int height;
	private int heightGoal;
	
	
	
	
	/**
	 * @param entranceGateId
	 * @param exitGateID
	 * @param direction
	 * @param height
	 * @param heighGoal
	 */
	public NewPlane(char entranceGateId, char exitGateID,
			int direction, int height, int heighGoal){
		setOpcode(MessageOpCodes.NEW_PLANE); 
		this.entranceGateId = entranceGateId;
		this.exitGateID = exitGateID;
		this.direction = direction;
		this.height = height;
		this.heightGoal = heighGoal;
	}
	
	public NewPlane(NewPlane np){
		setOpcode(MessageOpCodes.NEW_PLANE); 
		this.entranceGateId = np.getEntranceGateId();
		this.exitGateID = np.getExitGateID();
		this.direction = np.getDirection();
		this.height = np.getHeight();
		this.heightGoal = np.getHeightGoal(); 
		
	}
	public char getEntranceGateId() {
		return entranceGateId;
	}
	public void setEntranceGateId(char entranceGateId) {
		this.entranceGateId = entranceGateId;
	}
	public char getExitGateID() {
		return exitGateID;
	}
	public void setExitGateID(char exitGateID) {
		this.exitGateID = exitGateID;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getHeightGoal() {
		return heightGoal;
	}
	public void setHeightGoal(int heighGoal) {
		this.heightGoal = heighGoal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + direction;
		result = prime * result + entranceGateId;
		result = prime * result + exitGateID;
		result = prime * result + heightGoal;
		result = prime * result + height;
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
		NewPlane other = (NewPlane) obj;
		if (direction != other.direction)
			return false;
		if (entranceGateId != other.entranceGateId)
			return false;
		if (exitGateID != other.exitGateID)
			return false;
		if (heightGoal != other.getHeightGoal())
			return false;
		if (height != other.height)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NewPlane [entranceGateId=" + entranceGateId + ", exitGateID="
				+ exitGateID + ", direction=" + direction + ", height="
				+ height + ", heighGoal=" + heightGoal + "]";
	} 
	
	
}
