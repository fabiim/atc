package atc.messages;

import java.io.Serializable;

import atc.atc.PlaneHeight;

public class SetHeightGoal extends GameControl implements Serializable{
	private PlaneHeight objectiveHeight; 
	
	public SetHeightGoal() {
		setOpcode(MessageOpCodes.SET_HEIGHT_GOAL);
		
	}
	
	/**
	 * @param objectiveHeight
	 */
	public SetHeightGoal(PlaneHeight objectiveHeight) {
		setOpcode(MessageOpCodes.SET_HEIGHT_GOAL);
		this.objectiveHeight = objectiveHeight;
	}

	public SetHeightGoal(SetHeightGoal shg){
		setOpcode(MessageOpCodes.SET_HEIGHT_GOAL);
		this.objectiveHeight = shg.getObjectiveHeight(); 
	}
	public SetHeightGoal clone(){ return new SetHeightGoal(this); } 
	
	public PlaneHeight getObjectiveHeight() {
		return objectiveHeight;
	}



	public void setObjectiveHeight(PlaneHeight objectiveHeight) {
		this.objectiveHeight = objectiveHeight;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((objectiveHeight == null) ? 0 : objectiveHeight.hashCode());
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
		SetHeightGoal other = (SetHeightGoal) obj;
		if (objectiveHeight != other.objectiveHeight)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "SetHeightGoal [objectiveHeight=" + objectiveHeight + "]";
	}

	
}
