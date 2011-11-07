package atc.messages;

import java.io.Serializable;

import atc.atc.PlaneDirection;
import atc.atc.PlaneHeight;

public class NewPlane extends GameControlCommand implements Serializable {
	private char entranceGateId; 
	private char exitGateID; 
	private PlaneDirection direction; 
	private PlaneHeight height;
	private PlaneHeight heighGoal;
	
	
	
	
	/**
	 * @param entranceGateId
	 * @param exitGateID
	 * @param direction
	 * @param height
	 * @param heighGoal
	 */
	public NewPlane(char entranceGateId, char exitGateID,
			PlaneDirection direction, PlaneHeight height, PlaneHeight heighGoal){
		setOpcode(MessageOpCodes.NEW_PLANE); 
		this.entranceGateId = entranceGateId;
		this.exitGateID = exitGateID;
		this.direction = direction;
		this.height = height;
		this.heighGoal = heighGoal;
	}
	
	public NewPlane(NewPlane np){
		setOpcode(MessageOpCodes.NEW_PLANE); 
		this.entranceGateId = np.getEntranceGateId();
		this.exitGateID = np.getExitGateID();
		this.direction = np.getDirection();
		this.height = np.getHeight();
		this.heighGoal = np.getHeighGoal(); 
		
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
	public PlaneDirection getDirection() {
		return direction;
	}
	public void setDirection(PlaneDirection direction) {
		this.direction = direction;
	}
	public PlaneHeight getHeight() {
		return height;
	}
	public void setHeight(PlaneHeight height) {
		this.height = height;
	}
	public PlaneHeight getHeighGoal() {
		return heighGoal;
	}
	public void setHeighGoal(PlaneHeight heighGoal) {
		this.heighGoal = heighGoal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + entranceGateId;
		result = prime * result + exitGateID;
		result = prime * result
				+ ((heighGoal == null) ? 0 : heighGoal.hashCode());
		result = prime * result + ((height == null) ? 0 : height.hashCode());
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
		if (heighGoal != other.heighGoal)
			return false;
		if (height != other.height)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NewPlane [entranceGateId=" + entranceGateId + ", exitGateID="
				+ exitGateID + ", direction=" + direction + ", height="
				+ height + ", heighGoal=" + heighGoal + "]";
	} 
	
	
}
