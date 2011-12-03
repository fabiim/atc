package atc.screen;

import java.util.Random;
import java.util.TimerTask;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import atc.atc.GameState;
import atc.dispatchers.SendDispatcher;

public class NewPlaneTimer extends TimerTask {

	private final static int _newPlaneProbability = 10;
	private GameState gs;
	private SendDispatcher sd;
	Logger log = Logger.getLogger(NewPlaneTimer.class); 
	public NewPlaneTimer(GameState gs, SendDispatcher sd){
		this.gs=gs;
		this.sd=sd;
		log.setLevel(Level.INFO); 
	}
	
	public void run(){
		// Dice roll para adicionar avi�es
		Random rand = new Random();
		int roll = rand.nextInt(100);
		// If the dice roll is favorable to adding a new plane
		if(roll<_newPlaneProbability){
			sd.send(gs.createRandomPlane());
		}
	}
}
