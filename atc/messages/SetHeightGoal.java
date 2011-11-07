package atc.messages;

import java.io.Serializable;

<<<<<<< HEAD
import atc.atc.PlaneHeight;

public class SetHeightGoal extends GameControlCommand implements Serializable{
	private PlaneHeight objectiveHeight; 
=======
public class SetHeightGoal extends GameControl implements Serializable{
	private int objectiveHeight; 
>>>>>>> sadone
	
	public SetHeightGoal() {
		setOpcode(MessageOpCodes.SET_HEIGHT_GOAL);
		
	}
	
	/**
	 * @param objectiveHeight
	 */
	public SetHeightGoal(int objectiveHeight) {
		setOpcode(MessageOpCodes.SET_HEIGHT_GOAL);
		this.objectiveHeight = objectiveHeight;
	}

	public SetHeightGoal(SetHeightGoal shg){
		setOpcode(MessageOpCodes.SET_HEIGHT_GOAL);
		this.objectiveHeight = shg.getObjectiveHeight(); 
	}
	public SetHeightGoal clone(){ return new SetHeightGoal(this); } 
	
	public int getObjectiveHeight() {
		return objectiveHeight;
	}

<<<<<<< HEAD
	public void setObjectiveHeight(PlaneHeight objectiveHeight) {
=======


	public void setObjectiveHeight(int objectiveHeight) {
>>>>>>> sadone
		this.objectiveHeight = objectiveHeight;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + objectiveHeight;
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
		return "SetHeightGoal [Plane="+this.getPlaneId()+" objectiveHeight=" + objectiveHeight + "]";
	}

	
}
