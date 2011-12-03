package atc.atc;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import atc.messages.Message;
import atc.messages.NewPlane;

public class DelegatorGameState {

	private GameState game; 
	
	public DelegatorGameState(GameState gameState) {
		// TODO Auto-generated constructor stub
	}
	
	public void addObserver(Observer arg0) {
		game.addObserver(arg0);
	}
	public Board getBoard() {
		return game.getBoard();
	}
	public Map<Gate, Integer> getPreviousAdds() {
		return game.getPreviousAdds();
	}
	public HashMap<Gate, Integer> getActualAdds() {
		return game.getActualAdds();
	}
	public Map<Character, Plane> getFrontBuffer() {
		return game.getFrontBuffer();
	}
	public Map<Character, Plane> getBackBuffer() {
		return game.getBackBuffer();
	}
	public int getSuccessfulExits() {
		return game.getSuccessfulExits();
	}
	public int getUnsucessfulExits() {
		return game.getUnsucessfulExits();
	}
	public int getEpoch() {
		return game.getEpoch();
	}
	public GameState clone() {
		return game.clone();
	}
	public int countObservers() {
		return game.countObservers();
	}
	public NewPlane createRandomPlane() {
		return game.createRandomPlane();
	}
	public void deleteObserver(Observer arg0) {
		game.deleteObserver(arg0);
	}
	public void deleteObservers() {
		game.deleteObservers();
	}
	public boolean equals(Object obj) {
		return game.equals(obj);
	}
	public boolean hasChanged() {
		return game.hasChanged();
	}
	public void setBoard(Board board) {
		game.setBoard(board);
	}
	public void setPreviousAdds(Map<Gate, Integer> prevAdds) {
		game.setPreviousAdds(prevAdds);
	}
	public void setFrontBuffer(Map<Character, Plane> frontBuffer) {
		game.setFrontBuffer(frontBuffer);
	}
	public void setSuccessfulExits(int successfulExits) {
		game.setSuccessfulExits(successfulExits);
	}
	public void setUnsucessfulExits(int unsucessfulExits) {
		game.setUnsucessfulExits(unsucessfulExits);
	}
	public void setEpoch(int epoch) {
		game.setEpoch(epoch);
	}
	public void swapBuffers() {
		game.swapBuffers();
	}
	public String toString() {
		return game.toString();
	}
	public int hashCode() {
		return game.hashCode();
	}
	public void notifyObservers() {
		game.notifyObservers();
	}
	public void notifyObservers(Object arg0) {
		game.notifyObservers(arg0);
	}
	public void setActualAdds(HashMap<Gate, Integer> actAdds) {
		game.setActualAdds(actAdds);
	}
	public void setBackBuffer(Map<Character, Plane> backBuffer) {
		game.setBackBuffer(backBuffer);
	}
	public void processMsg(Message msg) {
		game.processMsg(msg);
	}
	public synchronized void setGame(GameState g ){ this.game = game; }
	public synchronized GameState getGame(){ return game; }
	
}
