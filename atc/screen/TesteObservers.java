package atc.screen;

import java.util.Observable;

public class TesteObservers extends Observable{
	
	public static void main(String[] args) {
		Screen orly2 = new Screen();
		Thread screen = new Thread(orly2);
		TesteThread orly = new TesteThread();
		orly.addObserver(orly2);
		screen.start();
		Thread teste = new Thread(orly);
		teste.start();
	}

}
