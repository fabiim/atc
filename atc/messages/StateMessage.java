package atc.messages;

import java.io.Serializable;

import atc.atc.DelegatorGameState;
import atc.atc.GameState;

public class StateMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 840000550822132018L;
	private atc.atc.GameState game;

	public StateMessage(){
		setOpcode(MessageOpCodes.STATEMESSAGE) ; 
	}
	
	public StateMessage(GameState game){
		setOpcode(MessageOpCodes.STATEMESSAGE); 
		this.game = game.clone(); 
	}
	
	public StateMessage(StateMessage m){
		setOpcode(MessageOpCodes.STATEMESSAGE);  
		game = m.getGame(); 
	}
	
	
	public GameState getGame() {
		return game.clone();
	}
	
	public void setGame(atc.atc.DelegatorGameState game) {
		this.game = game.clone();
	}

	@Override
	public StateMessage clone(){
		return new StateMessage(this); 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((game == null) ? 0 : game.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj) == false) return false; 
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		StateMessage other = (StateMessage) obj;
		
		if (game == null){
				return false;
		} else if (!game.equals(other.game))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StateMessage [game=" + game + "]";
	}
}
