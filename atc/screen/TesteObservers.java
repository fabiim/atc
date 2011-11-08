package atc.screen;

import java.util.Observable;

public class TesteObservers extends Observable{
	
	public static void main(String[] args) {
		Screen orly2 = new Screen("teste.map");
		Thread screen = new Thread(orly2);
		TesteThread orly = new TesteThread();
		orly.addObserver(orly2);
		Thread console = new Thread(new Console());
		console.start();
		screen.start();
		Thread teste = new Thread(orly);
		teste.start();
	}

}
