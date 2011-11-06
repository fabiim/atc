package atc.atc;

import java.util.Map;
import java.util.HashMap;

public class GameState {

	private Map<Character,Plane> planes;
	private int successfulExits;
	private int unsucessfulExits;
	private int epoch;
	
	public GameState(GameState gameState) {
		// TODO Auto-generated constructor stub
	}

	public void processMsg(){
		
	}
	
	public GameState clone(){ return new GameState(this); }  
}
