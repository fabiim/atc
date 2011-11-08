package atc.atc;

import java.util.Random;
import java.util.TimerTask;

import atc.dispatchers.SendDispatcher;

public class NewPlaneTimer extends TimerTask{

	private final static int _newPlaneProbability = 100;
	private GameState gs;
	private SendDispatcher sd;
	
	public NewPlaneTimer(GameState gs, SendDispatcher sd){
		this.gs=gs;
		this.sd=sd;
	}
	
	public void run(){
		// Dice roll para adicionar avi›es
		Random rand = new Random();
		int roll = rand.nextInt(100);
		// If the dice roll is favorable to adding a new plane
		if(roll<_newPlaneProbability)
			sd.send(gs.createRandomPlane());
	}
	
}
