package testeObserver;

import java.util.*;


public class Reader implements Observer{

	public void update(Observable o, Object args){
		System.out.println("O i Ž: "+((String)args));
	}

}
