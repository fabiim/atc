package atc.atc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import atc.messages.Message;
import atc.messages.NewPlane;
import atc.messages.SetHeightGoal;
import atc.messages.StateMessage;
import atc.messages.Tick;
import atc.messages.Turn;

public class GameState extends Observable {

	private Map<Character,Plane> frontBuffer; // The planes being shown on the screen.
	private Map<Character,Plane> backBuffer; // The new state of the game, still being processed.
	private int successfulExits;
	private int unsucessfulExits;
	private int epoch;
	private Board board;
	
	public Board getBoard() {
		return board.clone();
	}

	public void setBoard(Board board) {
		this.board = board.clone();
	}

	public GameState(Map<Character, Plane> frontBuffer,
			Map<Character, Plane> backBuffer, int successfulExits,
			int unsucessfulExits, int epoch, Board board) {
		this.frontBuffer = new HashMap<Character,Plane>();
		for(Map.Entry<Character, Plane> e : frontBuffer.entrySet())
			this.frontBuffer.put(e.getKey(), e.getValue());
		
		this.backBuffer = new HashMap<Character,Plane>();
		for(Map.Entry<Character, Plane> e : backBuffer.entrySet())
			this.backBuffer.put(e.getKey(), e.getValue());
		this.successfulExits = successfulExits;
		this.unsucessfulExits = unsucessfulExits;
		this.epoch = epoch;
		this.board = board.clone();
	}

	public GameState(GameState gameState) {
		frontBuffer = new HashMap<Character,Plane>();
		for(Map.Entry<Character, Plane> e : gameState.getFrontBuffer().entrySet())
			frontBuffer.put(e.getKey(), e.getValue());
		
		backBuffer = new HashMap<Character,Plane>();
		for(Map.Entry<Character, Plane> e : gameState.getBackBuffer().entrySet())
			backBuffer.put(e.getKey(), e.getValue());
		
		this.successfulExits = gameState.getSuccessfulExits();
		this.unsucessfulExits = gameState.getUnsucessfulExits();
		this.epoch = gameState.getEpoch();
		this.board = gameState.getBoard();
	}

	public Map<Character, Plane> getFrontBuffer() {
		Map<Character, Plane> ret = new HashMap<Character,Plane>();
		for(Map.Entry<Character, Plane> e : frontBuffer.entrySet())
			ret.put(e.getKey().charValue(), e.getValue().clone());
		return ret;
	}

	public void setFrontBuffer(Map<Character, Plane> frontBuffer) {
		this.frontBuffer = new HashMap<Character,Plane>();
		for(Map.Entry<Character, Plane> e : frontBuffer.entrySet())
			this.frontBuffer.put(e.getKey().charValue(), e.getValue().clone());
	}

	public Map<Character, Plane> getBackBuffer() {
		Map<Character, Plane> ret = new HashMap<Character,Plane>();
		for(Map.Entry<Character, Plane> e : backBuffer.entrySet())
			ret.put(e.getKey().charValue(), e.getValue().clone());
		return ret;
	}

	public void setBackBuffer(Map<Character, Plane> backBuffer) {
		this.backBuffer = new HashMap<Character,Plane>();
		for(Map.Entry<Character, Plane> e : backBuffer.entrySet())
			this.backBuffer.put(e.getKey().charValue(), e.getValue().clone());
	}

	public int getSuccessfulExits() {
		return successfulExits;
	}

	public void setSuccessfulExits(int successfulExits) {
		this.successfulExits = successfulExits;
	}

	public int getUnsucessfulExits() {
		return unsucessfulExits;
	}

	public void setUnsucessfulExits(int unsucessfulExits) {
		this.unsucessfulExits = unsucessfulExits;
	}

	public int getEpoch() {
		return epoch;
	}

	public void setEpoch(int epoch) {
		this.epoch = epoch;
	}

	public <T extends Message> void processMsg(T msg){
		switch(msg.getOpcode()){
			case TICK:
				processTick((Tick) msg);
				break;
			case STATEMESSAGE:
				processStateMsg((StateMessage) msg);
				break;
			case TURN:
				processTurn((Turn) msg);
				break;
			case SET_HEIGHT_GOAL:
				processHeightGoal((SetHeightGoal) msg);
				break;
			case NEW_PLANE:
				processNewPlane((NewPlane) msg);
				break;
			default:
				break;
		}
	}
	
	private void processTick(Tick tick){
		int x,y;
		
		// Fazer as opera��es primeiro por causa dos observers no swapBuffers()
		// Por os avi�es a marchar
		for(Plane p : backBuffer.values())
			p.move();
		
		// Remover os avi�es que saem do mapa
		boolean succ = false;
		for(Plane p : backBuffer.values()){ // A diferen�a entre tirar logo quando ele chega na porta ou tirar depois de ter passado um epoch na porta
			x = p.getxCoord();
			y = p.getyCoord();
			
			if(x==0 || y==0 || y==board.getHeight() || x==board.getWidth()){
				for(Gate g : board.getPorts().values())
					if(g.getxCoord()==x && g.getyCoord()==y && p.getExitAltitude()==p.getAltitude()){
						this.successfulExits++;
						succ=true;
						break;
					}
				if(succ)
					succ=false;
				else
					this.unsucessfulExits++;	
				backBuffer.remove(p.getID());
			}
		}
		
		// Remover avi�es que colidiram no epoch anterior - est�o marcados com um '+'
		for(Plane p : frontBuffer.values())
			if(p.getSymbol()=='+'){
				backBuffer.remove(p.getID());
				this.unsucessfulExits++;
			}
			
		// Testar colis�es
		for(Plane p : backBuffer.values()){
			x = p.getxCoord();
			y = p.getyCoord();
			for(Plane p2 : backBuffer.values()){
				int x2 = p2.getxCoord();
				int y2 = p2.getyCoord();
				if(p!=p2 && Math.abs(x-x2)<=1 && Math.abs(y-y2)<=1 && Math.abs(p.getAltitude().getHeight()-p2.getAltitude().getHeight())<=1000){
					p.setSymbol('+');
					p2.setSymbol('+');
				}// N�o fazer 'continue;' pq pode haver 3 ou mais avi�es a colidirem
			}
		}
		
		// Trocar os buffers
		swapBuffers();
	}

	private void processStateMsg(StateMessage statemsg){

	}

	private void processTurn(Turn turn){
		backBuffer.get(turn.getPlaneId()).setDirection(turn.getDirection());
	}

	private void processHeightGoal(SetHeightGoal goal){
		backBuffer.get(goal.getPlaneId()).setAltitudeObjectivo(goal.getObjectiveHeight());
	}

	private void processNewPlane(NewPlane plane){
		char id = plane.getEntranceGateId();
		
		// Verificar se h� "vaga" para um novo avi�o
		if(backBuffer.keySet().size()>=26) // S� podem haver no m�ximo 26 avi�es
			return;
		
		// Onde est� a porta?
		int x = board.getPorts().get(id).getxCoord();
		int y = board.getPorts().get(id).getyCoord();
		
		// Verificar se j� n�o foi adicionado um avi�o aquela porta neste epoch
		for(Plane p : backBuffer.values())
			if(p.getxCoord()==x && p.getyCoord()==y)
				return;
		
		// Verificar se n�o foi adicionado um avi�o naquela porta com aquela altitude no epoch anterior
		for(Plane p : frontBuffer.values())
			if(p.getxCoord()==x && p.getyCoord()==y && p.getAltitude()==plane.getHeight())
				return;
		
		// OK, bora l� adicionar um avi�o ent�o!
		char newID;
		for(newID='A'; newID<='Z'; newID++)
			if(!backBuffer.containsKey(newID))
				break;
		
		Plane p = new Plane(newID, board.getPorts().get(id).clone(), plane.getHeighGoal(),
				plane.getHeight(), plane.getHeight(), x, y,
				plane.getDirection(), newID, 1);
		
		backBuffer.put(newID, p);		
	}
	
	synchronized public void swapBuffers(){
		Map<Character,Plane> ret = new HashMap<Character,Plane>();
		frontBuffer = new HashMap<Character,Plane>();
		for(Map.Entry<Character, Plane> e : backBuffer.entrySet()){
			char key = e.getKey().charValue();
			Plane p = e.getValue();
			frontBuffer.put(key,p.clone());
			ret.put(key, p.clone());
		}
		setChanged();
		notifyObservers(ret); 	
	}
	
	@Override
	public String toString() {
		return "GameState [frontBuffer=" + frontBuffer + ", backBuffer="
				+ backBuffer + ", successfulExits=" + successfulExits
				+ ", unsucessfulExits=" + unsucessfulExits + ", epoch=" + epoch
				+ ", board=" + board + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((backBuffer == null) ? 0 : backBuffer.hashCode());
		result = prime * result + epoch;
		result = prime * result
				+ ((frontBuffer == null) ? 0 : frontBuffer.hashCode());
		result = prime * result + successfulExits;
		result = prime * result + unsucessfulExits;
		result = prime * result + board.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameState other = (GameState) obj;
		if (backBuffer == null) {
			if (other.backBuffer != null)
				return false;
		} else if (!backBuffer.equals(other.backBuffer))
			return false;
		if (epoch != other.epoch)
			return false;
		if (frontBuffer == null) {
			if (other.frontBuffer != null)
				return false;
		} else if (!frontBuffer.equals(other.frontBuffer))
			return false;
		if (successfulExits != other.successfulExits)
			return false;
		if (unsucessfulExits != other.unsucessfulExits)
			return false;
		
		// Compare frontBuffer
		Map<Character, Plane> o = other.getFrontBuffer();
		if(frontBuffer.size()!=o.size()) return(false);
		Iterator<Character> it = frontBuffer.keySet().iterator();
		Iterator<Character> it2 = o.keySet().iterator();
		char c1,c2;
		while(it.hasNext()){
			c1 = it.next();
			c2 = it2.next();
			if(c1!=c2 || !frontBuffer.get(c1).equals(o.get(c2)))
				return(false);
		}
		
		// Compare backBuffer	
		o = other.getBackBuffer();
		if(backBuffer.size()!=o.size()) return(false);
		it = backBuffer.keySet().iterator();
		it2 = o.keySet().iterator();
		while(it.hasNext()){
			c1 = it.next().charValue();
			c2 = it2.next().charValue();
			if(c1!=c2 || !backBuffer.get(c1).equals(o.get(c2)))
				return(false);
		}
		
		// Compare board
		Board b = other.getBoard();
		if(b.getHeight()!=board.getHeight() || 
				b.getWidth()!=board.getWidth() ||
				b.getPorts().size()!=board.getPorts().size())
			return(false);
		it = board.getPorts().keySet().iterator();
		it2 = b.getPorts().keySet().iterator();
		while(it.hasNext()){
			c1 = it.next().charValue();
			c2 = it.next().charValue();
			if(c1!=c2 || !b.getPorts().get(c1).equals(board.getPorts().get(c2)))
				return(false);
		}
		
		return true;
	}

	public GameState clone(){ return new GameState(this); }  
}