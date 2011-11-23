package atc.atc;

import java.util.Comparator;

public class PlaneComparator implements Comparator<Plane>{

	@Override
	public int compare(Plane o1, Plane o2) {
		return o1.getID().compareTo(o2.getID());
	}

}
